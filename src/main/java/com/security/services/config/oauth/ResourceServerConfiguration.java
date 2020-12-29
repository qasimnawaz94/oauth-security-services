package com.security.services.config.oauth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import com.security.services.config.exceptions.ExceptionTranslator;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ResourceServerConfiguration.class);

    @Value("${config.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    private ExceptionTranslator oauth2ResponseExceptionTranslator;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/swagger-ui.html**", "/webjars/**", "/null/**", "/swagger-resources/**")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        OAuth2AccessDeniedHandler auth2AccessDeniedHandler = new OAuth2AccessDeniedHandler();
        auth2AccessDeniedHandler.setExceptionTranslator(oauth2ResponseExceptionTranslator);
        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionTranslator(oauth2ResponseExceptionTranslator);
      
        config.accessDeniedHandler(auth2AccessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        config.resourceId(resourceId);
        
        
    }



    // @Bean
    // @Primary
    // public DefaultTokenServices tokenServices() {
    // DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    // defaultTokenServices.setTokenStore(tokenStore());
    // defaultTokenServices.setSupportRefreshToken(true);
    // defaultTokenServices.setTokenEnhancer(accessTokenConverter());
    // return defaultTokenServices;
    // }
    //
    // @Bean
    // public JwtAccessTokenConverter accessTokenConverter() {
    //
    // LOGGER.info("Initializing JWT with public key: " + publicKey);
    //
    // JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    // converter.setSigningKey(privateKey);
    //
    // return converter;
    // }
    //
    // @Bean
    // public JwtTokenStore tokenStore() {
    // return new JwtTokenStore(accessTokenConverter());
    // }


}
