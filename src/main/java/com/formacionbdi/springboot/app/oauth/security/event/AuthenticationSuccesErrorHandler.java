package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

/**
 * Se manejan los exitos o fracasos de inicio de sesión.
 */
@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

	/**
	 * Log.
	 */
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccesErrorHandler.class);

	/**
	 * Inyeccion de dependencia de IUsuarioService.
	 */
	@Autowired
	private IUsuarioService usuarioService;

	/**
	 * Maneja el exito.
	 */
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		System.out.println("Succes Login: " + user.getUsername());
		log.info(String.format("Succes Login: %s", user.getUsername()));
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}

	}

	/**
	 * Maneja el error.
	 */
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error(String.format("Error en el Login: %s", exception.getMessage()));
		System.out.println("Error en el Login: " + exception.getMessage());

		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			log.info(String.format("Intentos actuales es de: %s", usuario.getIntentos()));
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			log.info(String.format("Intentos después es de: %s", usuario.getIntentos()));

			if (usuario.getIntentos() >= 3) {
				log.error(String.format("El usuario %s des-habilitado por máximos intentos.", usuario.getUsername()));
				usuario.setEnabled(false);
			}

			usuarioService.update(usuario, usuario.getId());

		} catch (FeignException e) {
			log.error(String.format("El usuairo %s no existe en el sistema", authentication.getName()));
		}

	}

}
