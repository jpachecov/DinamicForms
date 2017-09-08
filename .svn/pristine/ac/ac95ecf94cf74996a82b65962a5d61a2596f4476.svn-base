/**
 * @(#)ServletImage.java 22/03/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 22/03/2017
 * @copyright Direccion de sistemas - INE
 */
@WebServlet("/image/*")
public class ServletImage extends HttpServlet {

    private static final Log LOGGER = LogFactory.getLog(ServletImage.class);
    private static final long serialVersionUID = 3782183978616662320L;
    
    @Resource(mappedName ="java:/util/glusterFS")
    private String rutaGluster;    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String fileName, rutaArchivo, absolutePath;
        Path path;
        try {
            String requestedFile = request.getPathInfo().substring(1);
            if (requestedFile == null) {
                // Do your thing if the image is not supplied to the request URI.
                // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }
            requestedFile = rutaGluster +File.separator +requestedFile;
            
            //Se obtiene ruta del archivo
            path = Paths.get(requestedFile);
            //Se obtiene el nombre del archivo
            fileName = path.getFileName().toString();
            //Se obtiene la ruta absoluta del archivo
            absolutePath = path.toAbsolutePath().toString();
            //Se obtiene la ruta del archivo sin el nombre
            rutaArchivo = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            File file = new File(rutaArchivo, fileName);

            if (!file.exists()) {
                // Do your thing if the file appears to be non-existing.
                // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }
            // Get content type by filename.
            String contentType = getServletContext().getMimeType(file.getName());
            if (contentType == null || !contentType.startsWith("image")) {
                // Do your thing if the file appears not being a real image.
                // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }

            response.reset();
            response.setContentType(contentType);
            response.setHeader("Content-Length", String.valueOf(file.length()));
//            response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
//            response.setHeader("Content-Length", String.valueOf(file.length()));
//            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException ex) {
            LOGGER.error("[Error] ServletImage", ex);
        }
    }

}
