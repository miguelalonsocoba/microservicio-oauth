package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Se manejan los exitos o fracasos de inicio de sesión.
 */
@Component
public class AuthenticationSuccesHandler implements AuthenticationEventPublisher {

	/**
	 * Log.
	 */
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccesHandler.class);

	/**
	 * Maneja el exito.
	 */
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		System.out.println("Succes Login: " + user.getUsername());
		log.info(String.format("Succes Login: %s", user.getUsername()));

	}

	/**
	 * Maneja el error.
	 */
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.info(String.format("Error en el Login: %s", exception.getMessage()));
		System.out.println("Error en el Login: " + exception.getMessage());

	}

}
