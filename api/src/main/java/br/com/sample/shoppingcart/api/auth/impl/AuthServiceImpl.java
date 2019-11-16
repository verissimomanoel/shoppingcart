/* 	 
 * AuthServiceImpl.java  
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth.impl;

import br.com.sample.shoppingcart.api.auth.*;
import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.exception.ShoppingCartMessageCode;
import br.com.sample.shoppingcart.api.security.ClaimResolve;
import br.com.sample.shoppingcart.api.security.KeyToken;
import br.com.sample.shoppingcart.api.security.Token;
import br.com.sample.shoppingcart.api.security.TokenBuilder;
import br.com.sample.shoppingcart.api.util.Util;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Business class of auth.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@Service
@Scope("prototype")
public class AuthServiceImpl implements AuthService {

	private final Log logger = LogFactory.getLog(getClass());
	public final static String PERFIX_BEARER = "Bearer";

	@Autowired
	private KeyToken keyToken;

	@Autowired
	private UserRepository userRepository;

	/**
	 * @see AuthService#login(AuthTO)
	 */
	@Override
	public CredentialTO login(AuthTO authTO) {
		String password = Util.encrypt(authTO.getPassword());
		Optional<User> userOptional = userRepository.findUserByEmailAndPassword(authTO.getLogin(), password);
		userOptional.orElseThrow(() -> new BusinessException(ShoppingCartMessageCode.ERROR_LOGIN_PASSWORD_INVALID));
		User user = userOptional.get();

		CredentialTO credentialTO = new CredentialTO();
		credentialTO.setLogin(user.getEmail());
		credentialTO.setName(user.getName());
		credentialTO.setUserId(user.getId());

		TokenBuilder builder = new TokenBuilder(keyToken);
		builder.addName(user.getName());
		builder.addLogin(user.getEmail());
		builder.addParam(ClaimResolve.PARAM_USER, user.getId());

		Token accessToken = builder.buildAccess();
		credentialTO.setAccessToken(accessToken.getValue());
		credentialTO.setAccessExpiresIn(accessToken.getExpiry());

		Token refreshToken = builder.buildRefresh();
		credentialTO.setRefreshToken(refreshToken.getValue());
		credentialTO.setRefreshExpiresIn(refreshToken.getExpiry());

		credentialTO.setLogin(authTO.getLogin());

		return credentialTO;
	}

	/**
	 * @see AuthService#refresh(RefreshTO)
	 */
	@Override
	public CredentialTO refresh(final RefreshTO refreshTO) {
		ClaimResolve resolve = getClainResolve(refreshTO.getRefreshToken());

		if (!resolve.isRefreshToken()) {
			throw new BusinessException(ShoppingCartMessageCode.ERROR_TOKEN_INVALID);
		}

		CredentialTO credentialTO = new CredentialTO();
		credentialTO.setUserId(resolve.getUserId());
		credentialTO.setLogin(resolve.getLogin());
		credentialTO.setName(resolve.getName());

		TokenBuilder builder = new TokenBuilder(keyToken);
		builder.addName(resolve.getName());
		builder.addLogin(resolve.getLogin());
		builder.addParam(ClaimResolve.PARAM_USER, resolve.getUserId());

		Token accessToken = builder.buildAccess();
		credentialTO.setAccessToken(accessToken.getValue());
		credentialTO.setAccessExpiresIn(accessToken.getExpiry());

		Token refreshToken = builder.buildRefresh();
		credentialTO.setRefreshToken(refreshToken.getValue());
		credentialTO.setRefreshExpiresIn(refreshToken.getExpiry());

		return credentialTO;
	}


	/**
	 * @see AuthService#getInfoByToken(String)
	 */
	@Override
	public CredentialTO getInfoByToken(String token) {
		ClaimResolve resolve = getClainResolve(token);

		if (!resolve.isAccessToken()) {
			throw new BusinessException(ShoppingCartMessageCode.ERROR_TOKEN_INVALID);
		}

		String userId = resolve.getUserId();
		CredentialTO credentialTO = new CredentialTO();
		credentialTO.setLogin(resolve.getLogin());
		credentialTO.setName(resolve.getName());
		credentialTO.setUserId(userId);

		return credentialTO;
	}

	/**
	 * Returns claims by token.
	 * 
	 * @param token
	 * @return
	 */
	private Map<String, Claim> getClaims(final String token) {
		TokenBuilder builder = new TokenBuilder(keyToken);
		return builder.getClaims(token);
	}

	/**
	 * Returns instance {@link ClaimResolve}.
	 * 
	 * @param token
	 * @return
	 */
	private ClaimResolve getClainResolve(String token) {
		token = token.replaceAll(PERFIX_BEARER, "").trim();
		Map<String, Claim> claims = getClaims(token);

		if (claims == null) {
			throw new BusinessException(ShoppingCartMessageCode.ERROR_TOKEN_INVALID);
		}
		return ClaimResolve.newInstance(claims);
	}
}
