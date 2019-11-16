/*
 * RestResponseExceptionHandler.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.exception;

import br.com.sample.shoppingcart.api.util.Util;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

/**
 * Intercepts and treats messages to show friendly messages.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Return the code of default message to application internal errors.
	 * 
	 * @return
	 */
	protected MessageCode getCodeInternalServerError() {
		return ShoppingCartMessageCode.ERROR_UNEXPECTED;
	}

	/**
	 * Handle method relative the exception {@link BusinessException}.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleBusinessException(final BusinessException e) {
		ResponseEntity<Object> response = null;

		if (e.getCode() != null) {
			Object[] params = null;

			if (e.hasParameters()) {
				params = e.getParameters();

				if (e.isConcat()) {
					String paramsConcat = Util.getCollectionAsString(Arrays.asList(params));

					params = new Object[1];
					params[0] = paramsConcat;
				}
			}

			String message = getMessage(e.getCode(), params);
			logger.error(message, e);

			response = response(e.getCode(), message, e.getParameters());
		} else if (e.getResponse() != null) {
			MessageResponse messageResponse = e.getResponse();

			if (messageResponse.hasFieldCode()) {
				messageResponse.getAttributes().forEach(field -> {
					String description = getMessage(field.getCode());
					field.setDescription(description);

					// Set the status of the first field.
					if (messageResponse.getStatus() == null) {
						HttpStatus status = HttpStatus.resolve(field.getCode().getStatus());
						messageResponse.setStatus(status.value());
						messageResponse.setError(status.getReasonPhrase());
					}
				});
			}
			response = ResponseEntity.status(messageResponse.getStatus()).body(messageResponse);
		} else if (e.getCause() != null) {
			response = handleException(e.getCause());
		}
		return response;
	}

	/**
	 * Global treatment of exceptions.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class, Exception.class })
	public ResponseEntity<Object> handleException(final Throwable e) {
		MessageCode code = getCodeInternalServerError();
		String message = getMessage(code, e.getMessage());
		logger.error(message, e);
		return response(code, message, null);
	}

	/**
	 * Handle method relative the exception {@link AccessDeniedException}.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<MessageResponse> handleAccessDeniedException(final AccessDeniedException e) {
		MessageResponse response = new MessageResponse();
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
		logger.error(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
	}

	/**
	 * Handle method relative the exception {@link AccessDeniedException}.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<MessageResponse> handleBadCredentialsException(final BadCredentialsException e) {
		MessageResponse response = new MessageResponse();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		logger.error(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException,
	 *      org.springframework.http.HttpHeaders,
	 *      org.springframework.http.HttpStatus,
	 *      org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.error(e.getMessage(), e);

		MessageResponse response = new MessageResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

		BindingResult result = e.getBindingResult();

		result.getAllErrors().forEach(error -> {
			FieldError fieldError = (FieldError) error;
			response.addAttribute(new FieldResponse(fieldError.getField(), error.getDefaultMessage()));
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMissingServletRequestParameter(org.springframework.web.bind.MissingServletRequestParameterException,
	 *      org.springframework.http.HttpHeaders,
	 *      org.springframework.http.HttpStatus,
	 *      org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException e, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
		logger.error(e.getMessage(), e);

		MessageResponse response = new MessageResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

		String msg = getMessage("value.empty");
		response.addAttribute(new FieldResponse(e.getParameterName(), msg));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleTypeMismatch(org.springframework.beans.TypeMismatchException,
	 *      org.springframework.http.HttpHeaders,
	 *      org.springframework.http.HttpStatus,
	 *      org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException e, final HttpHeaders headers,
                                                        final HttpStatus status, final WebRequest request) {
		logger.error(e.getMessage(), e);
		String name = e.getPropertyName();

		if (e instanceof MethodArgumentTypeMismatchException) {
			name = ((MethodArgumentTypeMismatchException) e).getName();
		}
		MessageResponse response = new MessageResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

		String msg = getMessage("value.invalid");
		response.addAttribute(new FieldResponse(name, msg));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleHttpMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException,
	 *      org.springframework.http.HttpHeaders,
	 *      org.springframework.http.HttpStatus,
	 *      org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleException(ex);
	}

	/**
	 * Return the menssage reported with the parameters.
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	private String getMessage(final MessageCode code, final Object... params) {
		return messageSource.getMessage(code.toString(), params, LocaleContextHolder.getLocale());
	}

	/**
	 * Return the menssage reported with the parameters.
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	private String getMessage(final String code, final Object... params) {
		return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
	}

	/**
	 * Return the instance of {@link ResponseEntity} according to the parameters
	 * reported.
	 * 
	 * @param code
	 * @param params
	 * @param message
	 * @return
	 */
	private ResponseEntity<Object> response(final MessageCode code, final String message, final Object[] params) {
		MessageResponse response = new MessageResponse();
		response.setCode(code.toString());
		response.setParameters(params);
		response.setMessage(message);

		HttpStatus status = HttpStatus.resolve(code.getStatus());
		response.setError(status.getReasonPhrase());
		response.setStatus(status.value());

		return ResponseEntity.status(status).body(response);
	}
}
