 /**
 * @(#)CustomGrantedAuthority.java 01/03/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.seguridad;

import org.springframework.security.core.GrantedAuthority;

/**
 * Clase que representa las roles obtenidos en LDAP requeridos
 * para validaciones de spring security
 * 
 * @author Pablo Zuñiga Mata
 * @since 01/03/2017
 * @copyright Direccion de sistemas - INE
 */
public class CustomGrantedAuthority implements GrantedAuthority{

	/**
	 * Elemento para la serialización de los objetos generados por esta clase
	 */
	private static final long serialVersionUID = -7865032081707392346L;
	/**
     * Rol
     */
    private String role;
    
    /**
     * Constructor
     */
    public CustomGrantedAuthority(String role) {
        this.role = role;
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		return role;
	}

}
