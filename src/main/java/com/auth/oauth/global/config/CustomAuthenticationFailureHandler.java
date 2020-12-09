package com.auth.oauth.global.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements ExceptionProcessor {

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {
		log.info("CustomAuthenticationFailureHandler.onAuthenticationFailure ::::");
		super.onAuthenticationFailure(request, response, exception);
	}

	@Override
	public void makeExceptionResponse(
		HttpServletRequest request, HttpServletResponse response, Exception exception
	) throws IOException {

	}
}