/*
 * AuthSwaggerConfig.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class of Swagger - Documentation API.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SWAGGER_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";

    public static final String SWAGGER_LICENSE = "Apache License 2.0";

    @Value("${app.api.swagger.title}")
    private String title;

    @Value("${app.api.base}")
    private String path;

    @Value("${app.api.version}")
    private String version;

    @Value("${app.api.swagger.base-package}")
    private String basePackage;

    /**
     * Create a instance of {@link Docket} to generate the documentation with SWAGGER 2.
     *
     * @return
     */
    @Bean
    public Docket shoppingCartAPI() {
        ApiInfo apiInfo = new ApiInfoBuilder().title(title).version(version).license(SWAGGER_LICENSE).licenseUrl(SWAGGER_LICENSE_URL).build();

        List<ResponseMessage> responses = getApiResponses();

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.ant("/" + path + "/**"))
                .build().useDefaultResponseMessages(Boolean.FALSE).globalResponseMessage(RequestMethod.GET, responses)
                .globalResponseMessage(RequestMethod.POST, responses)
                .globalResponseMessage(RequestMethod.PUT, responses)
                .globalResponseMessage(RequestMethod.DELETE, responses);
    }

    /**
     * Return the list of instances of {@link ResponseMessage}.
     *
     * @return
     */
    private List<ResponseMessage> getApiResponses() {
        List<ResponseMessage> responses = new ArrayList<ResponseMessage>();

        // Status 401
        responses.add(new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build());

        // Status 403
        responses.add(new ResponseMessageBuilder().code(HttpStatus.FORBIDDEN.value())
                .message(HttpStatus.FORBIDDEN.getReasonPhrase()).build());

        // Status 500
        responses.add(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build());

        return responses;
    }
}
