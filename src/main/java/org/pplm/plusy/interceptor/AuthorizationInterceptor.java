package org.pplm.plusy.interceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pplm.plusy.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	@Value("${plusy.auth}")
	private boolean auth;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!auth) {
			return true;
		}
		String token = request.getHeader("token");
		String username = tokenService.getUsername(token);
		if (username == null) {
			throw new AuthenticationException(HttpStatus.UNAUTHORIZED.name());
		} else {
			request.setAttribute("username", username);
		}
		return true;
	}

}
