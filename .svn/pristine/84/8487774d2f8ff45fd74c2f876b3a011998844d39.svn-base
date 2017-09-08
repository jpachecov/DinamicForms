package mx.ine.observadoresINE.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;





import mx.ine.observadoresINE.dto.DTOUsuarioLogin;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthenticationFilter extends
		AbstractPreAuthenticatedProcessingFilter {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(PreAuthenticationFilter.class);

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		DTOUsuarioLogin user = null;
		try {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("SIN_ROL"));
			user = new DTOUsuarioLogin(request.getRemoteUser(), "", true, true, true, true, authorities);			
		} catch (Exception e) {
			throw new AuthenticationServiceException("Error....", e);
		}

		return user;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "NA";
	}
}
