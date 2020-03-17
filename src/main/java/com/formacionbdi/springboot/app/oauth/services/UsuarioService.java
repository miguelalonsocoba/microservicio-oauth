package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.clients.UsuarioFeignClient;

/**
 * Class UsuarioService. Se implemtane una interface propia de Spring Security.
 */
@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	/**
	 * Variable Log.
	 */
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;

	/**
	 * Este metodo se encarga de autentificar al usuario por el Username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = client.findByUsername(username);

		// En caso de que no existe el usuario.
		if (usuario == null) {
			log.error(String.format("Error en el login, no existe el usuario %s en el sistema.", username));
			throw new UsernameNotFoundException(
					"Error en el login, no existe el usuario '" + username + "' en el sistema.");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info(String.format("Role: %s", authority.getAuthority())))
				.collect(Collectors.toList());

		log.info(String.format("Usuario autenticado: %s", username));

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}
