/**
 * 
 */
package mx.ine.observadoresINE.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;

/**
 * @author Gerardo López
 * @version 1.0.0
 * @since 28/08/2017
 */
public class MBReporteSolicitudAcreditacion extends MBGeneric implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817606290129899111L;

	/**
	 * LOGGER
	 */
	private static final Log log = LogFactory
			.getLog(MBReporteSolicitudAcreditacion.class);

	/**
	 * Carga la ruta del PDF
	 */
	private String rutaPdf;

	/**
	 * Variable para presentar el botón de descarga sí el visor de PDF no se
	 * visualiza
	 */
	private boolean btnDescarga = false;

	/**
	 * Objeto que contiene la ruta del archivo que se encuentra en BD en
	 * C_IMAGENES
	 */
	private DTOCImagenes cImagenes;

	/**
	 * MB que se encarga de la gestión del sistema: Menú lateral, menú de
	 * acciones, usuario... etc.
	 */
	@Autowired
	@Qualifier("mbAdmin")
	private transient MBAdministradorSistema mbAdmin;

	/**
	 * Método inicial, donde se setea la ruta del archivo para poder
	 * visualizarlo en la vista
	 * 
	 * @author Gerardo López
	 * @since 28/08/2017
	 * 
	 */
	public void rutapdf() {

		// Obtenemos informacion de usuario
		DTOUsuarioLogin usuario = mbAdmin.getDto().getUsuario();

		// creamos la lista de idImagenes para utilizar el metodo definido
		List<Integer> listaIdImagenes = new ArrayList<Integer>();

		// Lista que se define para las rutas
		List<DTOCImagenes> rutas = new ArrayList<DTOCImagenes>();

		// Asignamos el valor del id para el archivo en BD = 4
		listaIdImagenes.add(Constantes.ID_RUTA_SOLICITUD_ACREDITACION);

		try {

			// Obtener la ruta del archivo asignandole la lista de idImagenes
			rutas = bsdGeneric.obtenRutasIMG(usuario.getIdProcesoElectoral(),
					0, listaIdImagenes);

			// Validamos que la lista de rutas sea diferente de null
			if (rutas != null && !rutas.isEmpty()) {

				// Obtenemos el index 0 de la lista y lo asignamos al obtejo
				if (rutas.get(0).getDTOCImagenesPK().getIdImagen() == Constantes.ID_RUTA_SOLICITUD_ACREDITACION) {
					cImagenes = rutas.get(0);
				}
			}

			// Asignamos la ruta de pdf obtenida
			this.rutaPdf = validaRutaArchivo(cImagenes.getRuta());

		} catch (Exception e) {
			log.error("Hubo un error al obtener la lista de rutas en rutaPdf()");
			log.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Método que valida ruta de archivo conforme al ServletPdf para visualizar
	 * el pdf
	 * 
	 * @author Gerardo López
	 * @since 31/08/2017
	 * 
	 * @param nombreArchivo
	 * @return ruta
	 */
	protected String validaRutaArchivo(String rutaArchivo) {

		// Construimos la ruta con la rutaGluster + la ruta de BD
		String ruta = rutaGluster + File.separator + rutaArchivo;

		// Creamos el archivo con la ruta completa
		File pdf = new File(ruta);

		// Validamos que exista para pasar la ruta al ServletPdf y asignar las
		// propiedades del archivo fileName, title, etc.
		if (pdf.exists()) {
			ruta = "/pdf//" + rutaArchivo;
		} else {
			btnDescarga = true;
		}

		return ruta;
	}

	/**
	 * Método para descargar el archivo pdf
	 * 
	 * @author Gerardo López
	 * @since 31/08/2017
	 * 
	 */
	public void downloadFile() {
		String ruta = rutaGluster
				+ File.separator
				+ cImagenes.getRuta();
		File file = new File(ruta);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		response.setHeader(
				"Content-Disposition",
				"attachment;filename="
						+ Utilidades
								.mensajeProperties("nombre_pdf_solicitud_de_acreditacion_observadores"));
		response.setContentLength((int) file.length());
		FileInputStream input = null;
		try {
			int i = 0;
			input = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			while ((i = input.read(buffer)) != -1) {
				response.getOutputStream().write(buffer);
				response.getOutputStream().flush();
			}
			facesContext.responseComplete();
			facesContext.renderResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método get de rutaPdf
	 * 
	 * @return rutaPdf
	 */
	public String getRutaPdf() {
		return rutaPdf;
	}

	/**
	 * Método get de btnDescarga
	 * 
	 * @return btnDescarga
	 */
	public boolean isBtnDescarga() {
		return btnDescarga;
	}

}
