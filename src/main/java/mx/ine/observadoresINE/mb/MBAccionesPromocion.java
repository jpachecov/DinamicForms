package mx.ine.observadoresINE.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.ine.observadoresINE.bsd.BSDAccionesPromocionInterface;
import mx.ine.observadoresINE.bsd.BSDConfiguracionCatalogosInterface;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;
import mx.ine.observadoresINE.dto.db.DTOCAcciones;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("mbAccionesPromocion")
@Scope("session")
public class MBAccionesPromocion extends MBGeneric {

	private static final long serialVersionUID = 260620170144L;
	private static final Log logger = LogFactory
			.getLog(MBAccionesPromocion.class);

	private DTOAccionesPromocion filtros;
	private List<DTOCAcciones> lstMediosComunicacion = new ArrayList<>();
	private List<DTOAccionesPromocion> lstAccionPromociones = new ArrayList<DTOAccionesPromocion>();
	private DTOAccionesPromocion selectedAccionPromocion = new DTOAccionesPromocion();
	private String nombreMedioComunicacion = null;
	private DTOUsuarioLogin usuario = null;

	@Autowired
	@Qualifier("bsdAccionesPromocion")
	private transient BSDAccionesPromocionInterface bsdAccionesPromocion;

	@Autowired
	@Qualifier("bsdConfiguracionCatalogos")
	private transient BSDConfiguracionCatalogosInterface bsdConfiguracionCatalogos;

	public boolean esRolParaConsulta() {
		String rolUsuario = getUsuario().getRolUsuario();
		return rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_CONSEJERO_OC)
				|| rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_OC)
//				|| rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_CAU_OC)
				|| rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_JL)
				|| rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_OPLE)
				|| rolUsuario.equalsIgnoreCase(Constantes.CONSULTA_JD);
	}

	private void init() {
		cargaMediosComunicacion();
		limpiaFiltros();
	}

	public void initCapturar() {
		init();
	}

	public void initModificar() {
		init();
		limpiaDatosConsulta();
	}

	public void initConsultar() {
		init();
		limpiaDatosConsulta();
	}

	private void inicializaNombreMedioComunicacion() {
		String nombre = obtenNombreMedioComunicacion(filtros.getIdAccion());
		setNombreMedioComunicacion(nombre);
	}

	private void cargaMediosComunicacion() {
		Integer idProceso = getUsuario().getIdProcesoElectoral();
		Integer idDetalle = getUsuario().getIdDetalleProceso();
		try {
			this.lstMediosComunicacion = bsdConfiguracionCatalogos
					.getCAcciones(idProceso, idDetalle);
		} catch (Exception e) {
			logger.error("Error al consultar medios de comunicación ", e);
		}
	}

	private String obtenNombreMedioComunicacion(Integer idAccion) {
		boolean medioSeleccionado = (idAccion == null || idAccion == 0) ? Boolean.FALSE
				: Boolean.TRUE;
		if (medioSeleccionado && lstMediosComunicacion != null
				&& !lstMediosComunicacion.isEmpty()) {
			for (DTOCAcciones accion : lstMediosComunicacion) {
				if (accion.getdTOCAccionesPK().getIdAccion().equals(idAccion)) {
					return accion.getNombre();
				}
			}
		}
		return null;
	}

	public void realizaBusqueda() {
		logger.info("Se realiza consulta a BD");
		try {
			this.setLstAccionPromociones(bsdAccionesPromocion
					.consultaAccionesPromocion(filtros, getUsuario()));
			inicializaNombreMedioComunicacion();
		} catch (Exception e) {
			logger.error("Error al consultar las acciones de promoción ", e);
		}
	}

	public void guardarPromocion() {
		logger.info("Se guarda promocion");
		try {
			if (esDescripcionValida(getFiltros().getDescripcion(), getFiltros())) {
				bsdAccionesPromocion.guardar(getFiltros(), getUsuario());
				initCapturar();
				agregaMensaje(TipoMensaje.INFO_MENSAJE.getTipo(),
						"mensajesExito",
						"El registro ha sido guardado correctamente.");
			}
		} catch (Exception e) {
			logger.error("Error al guardar la acción de promoción ", e);
		}
	}

	public void eliminarPromocion(DTOAccionesPromocion promocion) {
		logger.info("Se elimina promocion");
		try {
			bsdAccionesPromocion.eliminar(promocion);
			initConsultar();
			agregaMensaje(TipoMensaje.INFO_MENSAJE.getTipo(), "mensajesExito",
					"El registro ha sido eliminado correctamente.");
		} catch (Exception e) {
			logger.error("Error al eliminar el registro ", e);
		}

	}

	public void modificarPromocion() {
		logger.info("Se modifica promocion");
		try {
			if (esDescripcionValida(getSelectedAccionPromocion()
					.getDescripcion(), getSelectedAccionPromocion())) {
				bsdAccionesPromocion.modificar(this.selectedAccionPromocion);
				initModificar();
				agregaMensaje(TipoMensaje.INFO_MENSAJE.getTipo(),
						"mensajesExito",
						"El registro ha sido modificado correctamente.");
			}
		} catch (Exception e) {
			logger.error("Error al modificar el registro ", e);
		}
	}

	/**
	 * Remplaza los saltos de línea \r\n por \n para corregir el conteo de los
	 * caracteres capturados
	 * 
	 * @param descripcion
	 * @param dtoAccionesPromocion
	 */
	private boolean esDescripcionValida(String descripcion,
			DTOAccionesPromocion dtoAccionesPromocion) {
		boolean esValido = Boolean.TRUE;
		// Se reemplazan saltos de linea, y espacios en html
		String desc = descripcion.replaceAll(System.lineSeparator(), "\n").replaceAll("\\xA0", " ");
		dtoAccionesPromocion.setDescripcion(desc);
		// Se validan caracteres permitidos
		Pattern pattern = Pattern
				.compile(Utilidades
						.mensajeProperties("validacion_expresion_caracteresValidos_descripcion_java"));
		Matcher matcher = pattern.matcher(desc);
		if (matcher.find()) {
			agregaMensaje(
					TipoMensaje.ERROR_MENSAJE.getTipo(),
					"validacionDescripcion",
					Utilidades
							.mensajeProperties("validacion_mensajes_caracteresValidos_descripcion"));
			esValido = Boolean.FALSE;
		}
		return esValido;
	}

	private void limpiaFiltros() {
		this.filtros = new DTOAccionesPromocion();
	}

	private void limpiaDatosConsulta() {
		this.setLstAccionPromociones(null);
		limpiaObjetoSeleccionado();
	}

	private void limpiaObjetoSeleccionado() {
		this.setSelectedAccionPromocion(null);
	}

	public List<DTOCAcciones> getLstMediosComunicacion() {
		return lstMediosComunicacion;
	}

	public void setLstMediosComunicacion(
			List<DTOCAcciones> lstMediosComunicacion) {
		this.lstMediosComunicacion = lstMediosComunicacion;
	}

	public DTOAccionesPromocion getSelectedAccionPromocion() {
		return selectedAccionPromocion;
	}

	public void setSelectedAccionPromocion(
			DTOAccionesPromocion selectedAccionPromocion) {
		this.selectedAccionPromocion = selectedAccionPromocion;
	}

	public List<DTOAccionesPromocion> getLstAccionPromociones() {
		return lstAccionPromociones;
	}

	public void setLstAccionPromociones(
			List<DTOAccionesPromocion> lstAccionPromociones) {
		this.lstAccionPromociones = lstAccionPromociones;
	}

	public DTOAccionesPromocion getFiltros() {
		return filtros;
	}

	public void setFiltros(DTOAccionesPromocion filtros) {
		this.filtros = filtros;
	}

	public String getNombreMedioComunicacion() {
		return nombreMedioComunicacion;
	}

	public void setNombreMedioComunicacion(String nombreMedioComunicacion) {
		this.nombreMedioComunicacion = nombreMedioComunicacion;
	}

	public DTOUsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(DTOUsuarioLogin usuario) {
		this.usuario = usuario;
	}

}