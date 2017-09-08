/**
 * @(#)customInvalidSessionStrategy.java 31/Enero/2013
 *
 * Copyright (C) 2012 Instituto Federal Electoral (IFE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.seguridad;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.StringUtils;

/**
 * Esta calse permite detectar cuando una sesión es inválida y redireccionar al usuario a
 * la url especificada <code>invalidSessionUrl</code>.
 * @author  MAVO
 * @since   20 Agosto, 2014
 * @version 1.0
 *
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    /**
     * Objeto par la serialización de esta clase. 
     * 
     */
    private static final Log logger = LogFactory.getLog(CustomInvalidSessionStrategy.class);
    /**
     * URL a la que será redireccionado el usuario cuando se detecta una sesión inválida.
     */
    private String invalidSessionUrl;
    
    
    private static final String FACES_REQUEST_HEADER = "faces-request";
        
    /**
     * Construye un objeto de la clase y especifica la url a la que 
     * será redireccionado el usuario cuando la sesión sea inválida.
     * @param invalidSessionUrl
     */
    public CustomInvalidSessionStrategy(String invalidSessionUrl){
    	this.invalidSessionUrl=invalidSessionUrl;
    	logger.info("invalidSessionUrl" + invalidSessionUrl);
    	
    }
    
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {        
    	
        boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));
      
        if(ajaxRedirect) {
            String contextPath = request.getContextPath();
            String redirectUrl = contextPath + invalidSessionUrl;
            
            logger.info("Session expired due to ajax request, redirecting to :" + redirectUrl);
            
            String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);
        
 
            response.setContentType("text/xml");
            response.getWriter().write(ajaxRedirectXml);
        } else {
        
            String requestURI = getRequestUrl(request);
            logger.info("Session expired due to non-ajax request, starting a new session and redirect to requested url :"+ invalidSessionUrl);
            request.getSession(true);
            response.sendRedirect(request.getContextPath() + invalidSessionUrl);
        }
 
    }
    
    private String getRequestUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
 
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            requestURL.append("?").append(queryString);
        }
 
        return requestURL.toString();
    }
 
    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder()
                        .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                        .append("<partial-response><redirect url=\"")
                        .append(redirectUrl)
                        .append("\"></redirect></partial-response>")
                        .toString();
    }
 
    public void setInvalidSessionUrl(String invalidSessionUrl) {
        this.invalidSessionUrl = invalidSessionUrl;
    }
    

}