package mx.ine.observadoresINE.seguridad;

import java.io.IOException;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.sun.faces.util.Util;

/**
 * Clase que sirve para el manejo de los recursos no encontrados
 * que no son filtrados por el springsecurity.
 * http://grepcode.com/file/repo1.maven.org/maven2/com.sun.faces/jsf-api/2.1.6/javax/faces/application/ResourceHandlerWrapper.java
 * 
 */
public class ResourcesFilter extends ResourceHandlerWrapper {

  // Properties
  // -----------------------------------------------------------------------------------------------------

  private final ResourceHandler wrapped;

  public ResourcesFilter (final ResourceHandler wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public ResourceHandler getWrapped() {
    return wrapped;
  }


  @Override
  public boolean isResourceRequest(final FacesContext context) {
    return super.isResourceRequest(context);
  }
  
  /**
   * Provides a simple implementation of ResourceHandler that can be subclassed by developers wishing 
   * to provide specialized behavior to an existing ResourceHandler instance. The default implementation 
   * of all methods is to call through to the wrapped ResourceHandler. 
   */
  
  @Override
  public void handleResourceRequest(FacesContext context) throws IOException {
    
	  String resourceId = normalizeResourceRequest(context);
    
	  if (null != resourceId && resourceId.startsWith(RESOURCE_IDENTIFIER)) {
		  
		  Resource resource = null;
		  String resourceName = null;
		  
		  if (ResourceHandler.RESOURCE_IDENTIFIER.length() < resourceId.length()) {
			  
			  resourceName = resourceId.substring(RESOURCE_IDENTIFIER.length() + 1);
			  
			  if (!StringUtils.isEmpty(resourceName)) {
				  
				  resource = context.getApplication().getResourceHandler()
						  .createResource(resourceName,context.getExternalContext().getRequestParameterMap().get("ln"));
			  }
		  }
      
		  if (resource == null) {
       
			  HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			  response.sendError(404);
			  return;
		  }
		  
		  String[] numeroCarpetas = resourceId.split("/");
		  
		  if (numeroCarpetas.length > 2 && !(numeroCarpetas[numeroCarpetas.length-1]).contains(".")) {
			  
			  HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			  response.sendError(404);
			  return;
		  }
	  }
	  
	  super.handleResourceRequest(context);
  }

  private String normalizeResourceRequest(FacesContext context) {

    String path;
      String facesServletMapping = Util.getFacesMapping(context);
    // If it is extension mapped
    if (!Util.isPrefixMapped(facesServletMapping)) {
      path = context.getExternalContext().getRequestServletPath();
      // strip off the extension
      int i = path.lastIndexOf(".");
      if (0 < i) {
        path = path.substring(0, i);
      }
    } else {
      path = context.getExternalContext().getRequestPathInfo();
    }
    return path;
  }


}