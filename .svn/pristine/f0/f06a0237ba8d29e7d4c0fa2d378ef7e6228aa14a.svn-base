/** 
 * 
 */
package mx.ine.observadoresINE.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Gerardo López
 * @since 31/08/2017
 */
@WebServlet("/pdf/*")
public class ServletPdf extends HttpServlet {

	private static final Log LOGGER = LogFactory.getLog(ServletPdf.class);
	private static final long serialVersionUID = 8401022908619069931L;

	@Resource(mappedName = "java:/util/glusterFS")
	private String rutaGluster;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName, rutaArchivo, absolutePath;
		Path path;
		try {
			String requestedFile = request.getPathInfo().substring(1);
			if (requestedFile == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				return;
			}
			requestedFile = rutaGluster + File.separator + requestedFile;

			// Se obtiene ruta del archivo
			path = Paths.get(requestedFile);
			// Se obtiene el nombre del archivo
			fileName = path.getFileName().toString();
			// Se obtiene la ruta absoluta del archivo
			absolutePath = path.toAbsolutePath().toString();
			// Se obtiene la ruta del archivo sin el nombre
			rutaArchivo = absolutePath.substring(0,
					absolutePath.lastIndexOf(File.separator));
			File file = new File(rutaArchivo, fileName);

			if (!file.exists()) {
				// Do your thing if the file appears to be non-existing.
				// Throw an exception, or send 404, or show default/warning
				// image, or just ignore it.
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				return;
			}
			// Get content type by filename.
			String contentType = getServletContext()
					.getMimeType(file.getName());
			if (contentType == null || !contentType.startsWith("application")) {
				// Sí el contentType no es un pdf: application/pdf envia un 404
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			response.reset();
			response.setContentType(contentType);
			response.setHeader("Content-Length", String.valueOf(file.length()));

			Files.copy(file.toPath(), response.getOutputStream());
		} catch (IOException ex) {
			LOGGER.error("[Error] ServletImage", ex);
		}
	}

}
