package com.formacionbdi.springboot.app.oauth.services;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;

/**
 * Interface IUsuarioService.
 */
public interface IUsuarioService {
	
	/**
	 * Obtener los datos del usuario.
	 * 
	 * @param user
	 * @return Usuario
	 */
	Usuario findByUsername(String username);

}
