 /**
 * @(#)CustomGrantedAuthority.java 01/03/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.seguridad;

import mx.ine.servicio.admin.exception.ServiceAdminLdapException;
import mx.ine.observadoresINE.util.Utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author INE
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Log LOGGER = LogFactory .getLog(CustomAuthenticationProvider.class);
    /**
     * Clase encargada de procesas información
     */
    private CustomUserDetailsService userDetailsService;

    /**
    * {@inheritDoc}
    */
    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        UserDetails userDetails = null;
        try{
            userDetails = userDetailsService.cargaUsuario(
                    Integer.valueOf(Utilidades.mensajeProperties("constante_generica_idSistema")),
                    auth.getName(),
                    auth.getCredentials().toString());
            
            LOGGER.info("ID DEL SISTEMA: " + Utilidades.mensajeProperties("constante_generica_idSistema"));
            if(userDetails != null){
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            }else{
                throw new BadCredentialsException("El usuario no tiene permisos para este sistema");
            }
        }catch(ServiceAdminLdapException e){
            LOGGER.error("Ocurrio un error al realizar la autenticacion", e);
            throw new BadCredentialsException(e.getMessage());
        }
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * Método que ingresa el valor de el atributo userDetailsService
     *
     * @param userDetailsService : valor que ingresa a el atributo userDetailsService
     *
     * @author Pablo Zuñiga Mata
     * @since 01/03/2017
     */
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
