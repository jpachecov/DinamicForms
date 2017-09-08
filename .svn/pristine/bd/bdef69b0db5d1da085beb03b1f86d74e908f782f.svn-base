package mx.ine.observadoresINE.seguridad;

/**
 * @(#)CustomLogoutHandler.java 18/09/2014
 *
 * Copyright (C) 2014 Instituto Nacional Electoral (INE).
 * 
 * Todos los derechos reservados.
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * Esta clase permite detectar cuando se realiza un logout en una aplicación web
 * y determinar si el usuario quiere cambiar a otro sistema redireccionándolo a éste
 * o si el usuario cerró su sesión y debe redireccionarlo a la url por defecto.
 * 
 * @author  Claudia M. Ramírez Trejo
 * @copy Roberto Shirásago Domínguez
 * @since   18 Septiembre 2014
 */
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {

	/**
	 * Url por defecto a la que se envía al usuario después de hacer un logout.
	 */
	private String logoutSuccessUrl;
	
	/**
	 * URL a la que será redireccionado el usuario cuando se detecta una
	 * sesión inválida y se está haciendo un logout.
	 */
    private String invalidSessionUrl;
    
    /**
     * Constructor por defecto que inicializa la url de logout y sesión inválida
     * como vacías.
     */
    public CustomLogoutHandler() {

    	logoutSuccessUrl = "";
    	invalidSessionUrl = "";
    }
    
    /* ----------------------------------------------------------------------------------------- */
	/* --------------------------------- INICIO - EVENTOS -------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler#onLogoutSuccess(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    	Authentication authentication) throws IOException, ServletException {
		
    	String url;
		
		//Sesión inválida al hacer un logout
		if (authentication == null) {
			
			url = invalidSessionUrl;
			logger.debug("Logout sin autenticar - Redireccionando al usuario a " + url);
			
		} else {
			
//			String ruta = request.getServletContext().
//					getRealPath(Utilidades.mensajeProperties("etiqueta_observadores_rutaTemporalFotos") + "/" + authentication.getName());
//
//			File carpetaTemporal = new File(ruta);
//			
//			if (carpetaTemporal.exists()) {
//				
//				Utils.borrarCarpeta(carpetaTemporal);
//			}
			
			url = logoutSuccessUrl;
			
			logger.debug("Logout autenticado - Redireccionando al usuario a " + url);
		}
		
		setDefaultTargetUrl(url);
		super.onLogoutSuccess(request, response, authentication);
    }
	/* ----------------------------------------------------------------------------------------- */
	/* ----------------------------------  FIN - EVENTOS --------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */	
    
    
    /* ---------------------------------- GETTERS SETTERS -------------------------------------- */
 
    /**
	 * Establece la url por defecto a la que se debe enviar al usuario
	 * después de hacer un logout.
	 * @param logoutSuccessUrl Valor que será asgiando al atrbituo <code>defaultTargetUrl</code> 
	 */
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
	/**
	 * Establece la url por defecto a la que se debe enviar al usuario
	 * cuando se detecta una sesión inválida y se está haciendo un logout.
	 * @param invalidSessionUrl Valor que será asgiando al atrbituo <code>invalidSessionUrl</code> 
	 */
	public void setInvalidSessionUrl(String invalidSessionUrl) {
		this.invalidSessionUrl = invalidSessionUrl;
	}
	
	/**
	 * Obtiene la url por defecto a la que se debe enviar al usuario
	 * cuando se detecta una sesión inválida y se está haciendo un logout.
	 * @return El valor del atrbituo <code>invalidSessionUrl</code> 
	 */
	public String getInvalidSessionUrl() {
		return this.invalidSessionUrl;
	}
}
