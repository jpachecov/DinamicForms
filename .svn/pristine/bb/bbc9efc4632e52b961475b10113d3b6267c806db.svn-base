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
 * Esta clase permite cachar la excepci&oacute;n arrojada al solicitar un recurso no encontrado 
 * de /app/javax.faces.resource/** y redireccionarla a la pagina de error.
 * @author Mario del &Aacute;ngel V&aacute;zquez R. 
 * @date 16/01/2014
 *
 */
public class SecurityResourceHandler extends ResourceHandlerWrapper {

  // Properties
  // -----------------------------------------------------------------------------------------------------

  private final ResourceHandler wrapped;

  public SecurityResourceHandler (final ResourceHandler wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public ResourceHandler getWrapped() {
    return wrapped;
  }


  /**
   * @see javax.faces.application.ResourceHandlerWrapper#isResourceRequest(javax.faces.context.FacesContext)
   */
  @Override
  public boolean isResourceRequest(final FacesContext context) {
    return super.isResourceRequest(context);
  }

  @Override
  public void handleResourceRequest(FacesContext context) throws IOException {
    String resourceId = normalizeResourceRequest(context);
    if (null != resourceId && resourceId.startsWith(RESOURCE_IDENTIFIER)) {
      Resource resource = null;
      String resourceName = null;
      if (ResourceHandler.RESOURCE_IDENTIFIER.length() < resourceId.length()) {
        resourceName = resourceId.substring(RESOURCE_IDENTIFIER.length() + 1);
        if (!StringUtils.isEmpty(resourceName)) {
          resource =
              context
                  .getApplication()
                  .getResourceHandler()
                  .createResource(resourceName,
                      context.getExternalContext().getRequestParameterMap().get("ln"));
        }
      }
      if (resource == null) {
        HttpServletResponse response =
            (HttpServletResponse) context.getExternalContext().getResponse();
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