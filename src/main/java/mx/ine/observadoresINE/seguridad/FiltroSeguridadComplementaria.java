package mx.ine.observadoresINE.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import nl.captcha.Captcha;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FiltroSeguridadComplementaria extends UsernamePasswordAuthenticationFilter {

    private static final Log LOGGER = LogFactory.getLog(FiltroSeguridadComplementaria.class);
    private String succesUrl = "";
    private String errorUrl = "";
    private String captchaParamName = "";

    private SimpleUrlAuthenticationSuccessHandler simpleUrlSucces = new SimpleUrlAuthenticationSuccessHandler();
    private SimpleUrlAuthenticationFailureHandler simpleUrlFailure = new SimpleUrlAuthenticationFailureHandler();
    
    @Autowired
    private MessageSource mensaje;
    
    /**
     * Constructor por defecto
     */
    public FiltroSeguridadComplementaria() {
        super();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = super.obtainPassword(request);
        if (password == null || password.isEmpty()) {
        	LOGGER.info("El campo contraseña es obligatorio, favor de verificarlo ");
            throw new BadCredentialsException("El campo contraseña es obligatorio, favor de verificarlo");
        }
        return password;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String userName = super.obtainUsername(request);
        if (userName == null || userName.isEmpty()) {
        	LOGGER.info("El campo nombre de usuario es obligatorio, favor de verificarlo ");
            throw new BadCredentialsException("El campo Nombre de usuario es obligatorio, favor de verificarlo");
        }
        return userName;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        Authentication auth = null;
        String ipAddress = request.getRemoteAddr();
        String codigoSeguridad ;        
        Captcha captcha ;
        Boolean validador;	       
       	
        // Validando usuario vacio
        this.obtainUsername(request);
      
        //Validando contrase a vacia
        this.obtainPassword(request);
             
        if (!ipAddress.equalsIgnoreCase("127.0.0.1")) {
            // cuando NO es localhost procede a validar captcha
            codigoSeguridad = this.obtainSecurityCode(request);
            captcha = this.obtainCaptcha(request);
            validador = this.validaCaptcha(captcha, codigoSeguridad);
            if (validador) {
                auth = autenticador(request, response);
            }
        } else {
            // cuando es localhost no valida captcha
            auth = autenticador(request, response);
        }
        return auth;
    }
    
   
  
    public Authentication autenticador (HttpServletRequest request,HttpServletResponse response){
    	Authentication auth;
    	auth = super.attemptAuthentication(request, response);
    	LOGGER.debug("revisando usuario:"+ auth.getPrincipal());
  	  	if (!(auth.getPrincipal() instanceof DTOUsuarioLogin)) {
  	  		LOGGER.error("Credenciales invalidas");
  	  		throw new BadCredentialsException("Credenciales invalidas.");
  	  	}
  	  	return auth;
    }
    
    public Boolean validaCaptcha(Captcha captcha,  String codigoSeguridad) {
    	Boolean validador= false;
    	if (captcha != null && captcha.isCorrect(codigoSeguridad)) {
    		validador=true;
    	}else {
    		validador=false;
    		LOGGER.error("El c\u00f3digo de seguridad es incorrecto, favor de introducirlo nuevamente");
        	throw new BadCredentialsException("El c\u00f3digo de seguridad es incorrecto, favor de introducirlo nuevamente");
        }
    	return validador;
    }
    
    public String obtainSecurityCode(HttpServletRequest request) {
		String codigoSeguridad = request.getParameter(this.captchaParamName);
		if (codigoSeguridad == null || codigoSeguridad.isEmpty()) {
			LOGGER.error("El c\u00f3digo de seguridad es obligatorio, favor de verificarlo");
			throw new BadCredentialsException(
					"El c\u00f3digo de seguridad es obligatorio, favor de verificarlo");
		}
		return codigoSeguridad;
	}
    
    public Captcha obtainCaptcha(HttpServletRequest request) {
    	 Captcha captcha = (Captcha) (request.getSession().getAttribute(Captcha.NAME));
		if (captchaParamName.isEmpty()) {
            LOGGER.error("El c\u00f3digo de seguridad es incorrecto, favor de introducirlo nuevamente");
            throw new BadCredentialsException("El c\u00f3digo de seguridad es incorrecto, favor de introducirlo nuevamente");
        }
		return captcha;
	}
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
    	LOGGER.debug("revisando usuario:"+ authResult.getPrincipal());
        LOGGER.info("La autentificacion fue exitosa ");
        simpleUrlSucces.setDefaultTargetUrl(succesUrl);
        this.setAuthenticationSuccessHandler(simpleUrlSucces);
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
    	LOGGER.info("La autentificacion fue fallida ");
       
        simpleUrlFailure.setDefaultFailureUrl(errorUrl);       
        this.setAuthenticationFailureHandler(simpleUrlFailure);      
        super.unsuccessfulAuthentication(request, response, failed);
       
               
    }

    public String getSuccesUrl() {
        return succesUrl;
    }

    public void setSuccesUrl(String succesUrl) {
        this.succesUrl = succesUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getCaptchaParamName() {
        return captchaParamName;
    }

    public void setCaptchaParamName(String captchaParamName) {
        this.captchaParamName = captchaParamName;
    }

	
	
}
