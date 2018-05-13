package org.pplm.plusy.config;

import org.pplm.plusy.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
	
	@Bean
	public AuthorizationInterceptor authorizationInterceptor(){
        return new AuthorizationInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(authorizationInterceptor());
		interceptorRegistration.excludePathPatterns("/", "/system/login", "/actuator/**");
		interceptorRegistration.addPathPatterns("/**");
	}
	
}
