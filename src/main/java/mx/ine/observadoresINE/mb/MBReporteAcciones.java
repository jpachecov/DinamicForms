package mx.ine.observadoresINE.mb;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.observadoresINE.bsd.BSDReporteAccionesInterface;
import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOReportesParametros;
import mx.ine.observadoresINE.util.Utilidades;

/**
 * Clase para el reporte de Acciones de Promoción
 * 
 * @author jpachecov
 *
 */
public class MBReporteAcciones extends MBReportesMenu {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -521137180955103187L;

	/**
	 * Log
	 */
	private static final Log log = LogFactory.getLog(MBReporteAcciones.class);

	/**
	 * BSD
	 */
	@Autowired
	@Qualifier("bsdReporteAcciones")
	private transient BSDReporteAccionesInterface bsdReporte;

	/**
	 * Para los filtros
	 */
	private DTOFiltroReporteAcciones datos;

	/**
	 * Para mostrar la tabla
	 */
	private boolean muestraTabla;

	/**
	 * Método init
	 * 
	 * @author jpachecov
	 */
	@Override
	public void init() {
		try {
			super.init();
			String n_rep = Utilidades.mensajeProperties("etiqueta_reportes_accionesDePromocion");
			datos = new DTOFiltroReporteAcciones();
			datos.setNivel(obtenNivelReporte());
			datos.setUsuario(getUsuario());
			if (datos.esNivel("JD"))
				datos.setTipoReporte("L");
			mbAdmin.getDto().setTituloModulo(n_rep);
			setNombreReporte(n_rep);
			setMuestraTotales(false);
			setMuestraTabla(false);
		} catch (Exception e) {
			addErrorMessage("Error al inicializar los datos");
			scrollTop();
			log.error("Error en MBReporteAcciones - init()");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método que se encarga de consultar los servicios para traer los datos del
	 * reporte e inicializar lo necesario para mostrarlo.
	 * 
	 * @author jpachecov
	 */
	public void consultaReporte() {
		try {
			showOtroTotal();
			asignaParametrosReporte(bsdReporte.obtenReporte(datos));
			setMuestraTabla(true);
		} catch (Exception e) {
			addErrorMessage("Ocurrió un error al consultar reporte");
			scrollTop();
			log.error("Error en MBReporteAcciones - consultaReporte()");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Genera un archivo XLS
	 * 
	 * @author jpachecov
	 */
	public void postProcessXLS(Object document) {
		try {
			dtoParametros.setRutaImgEstado("");
			dtoParametros.crearEncabezadoXLS((HSSFWorkbook) document);
		} catch (Exception e) {
			addErrorMessage("Ocurrió un error al exportar archivo XLS");
			scrollTop();
			log.error("Error en postProcessXLS");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Genera un archivo PDF
	 * 
	 * @author jpachecov
	 */
	public void exportPDF() {
		try {
			setParametros(datos.getDatosPdf(dtoParametros));
			super.exportPDF();
		} catch (Exception e) {
			addErrorMessage("Ocurrió un error al exportar archivo PDF");
			scrollTop();
			log.error("Error en exportPDF");
			log.error(e);
			e.printStackTrace();
			
		}
	}

	/**
	 * OBtiene el nivel de oficinas según el estado actual del menú lateral.
	 * 
	 * @return
	 * @throws Exception
	 * @author jpachecov
	 */
	public String obtenNivelReporte() throws Exception {
		Integer idEstado = getUsuario().getIdEstadoSeleccionado();
		Integer idDistrito = getUsuario().getIdDistritoSeleccionado();
		if (idEstado != null && idEstado.intValue() == 0) {
			return "OC";
		}
		if (idEstado != null && idEstado > 0) {
			if (idDistrito != null && idDistrito.intValue() == 0) {
				return "JL";
			}
			if (idDistrito != null && idDistrito.intValue() > 0) {
				return "JD";
			}
		}
		throw new Exception("No se pudo obtener el nivel de oficinas centrales.");
	}

	/**
	 * Inicilializa las dimensiones del encabezado
	 * 
	 * @author jpachecov
	 */
	private void inicializaDimensionesEncabezado(DTOReportesParametros dto) {
		int cols = dto.getColumnas().intValue();
		if (datos.esNivel("OC") || datos.esNivel("JL")) {
			if(cols % 2 > 0){
				dtoParametros.setAnchoEntidad((cols / 2) + 1);
				dtoParametros.setAnchoFechaHora(cols / 2);				
			} else {
				dtoParametros.setAnchoEntidad(cols / 2);
				dtoParametros.setAnchoFechaHora(cols / 2);
			}
		}
		if(datos.esNivel("JD")){
			if(cols % 3 == 0){
				dtoParametros.setAnchoEntidad(cols / 3);
				dtoParametros.setAnchoDistrito(cols / 3);
				dtoParametros.setAnchoFechaHora(cols / 3);
			} else {
				dtoParametros.setAnchoEntidad(cols / 3);
				dtoParametros.setAnchoDistrito(cols / 3);
				dtoParametros.setAnchoFechaHora(cols % 3);
			}
		}
	}

	/**
	 * Método que asigna en la clase padre los datos para la visualización de la
	 * tabla del reporte.
	 * 
	 * @param dto
	 * 
	 * @author jpachecov
	 */
	public void asignaParametrosReporte(DTOReportesParametros dto) {
		super.inicializaParametrosEncabezado();
		inicializaDimensionesEncabezado(dto);
		dtoParametros.setTituloReporte(dto.getTituloReporte());
		dtoParametros.setEncabezado(dto.getEncabezado());
		dtoParametros.setColumnas(dto.getColumnas());
		dtoParametros.setListaDatos(dto.getListaDatos());
	}

	/**
	 * Resetea los campos de fechas.
	 */
	public void resetFechas() {
		log.info("resetFechas");
		resetField("formContent:fechaInicio");
		resetField("formContent:fechaFin");
		datos.setFechaIni(null);
		datos.setFechaFin(null);
	}

	/**
	 * Método que hace invisible a la tabla.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void hideTable() {
		setMuestraTabla(false);
		resetFechas();
	}

	
	public void showOtroTotal(){
		if(datos.getTipoReporte().equals("C") && datos.getNivel().equals("OC")){
			setOtrosTotales(true);
			return;
		}
		if(datos.getTipoReporte().equals("C") && datos.getNivel().equals("JL")){
			setOtrosTotales(true);
			return;
		}
		setOtrosTotales(false);
	}
	/**
	 * Método que agrega un mensaje al growl.
	 * 
	 * @param mensaje
	 * @author Jean Pierre Pacheco Avila
	 */
	public void addGrowlMessage(String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Éxito", mensaje));
	}

	/**
	 * Método que agrega un mensaje de error (estático, no growl).
	 * 
	 * @param message
	 * @author Jean Pierre Pacheco Avila
	 */
	public void addErrorMessage(String message) {
		log.info("addErrorMessage");
		FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
		FacesContext.getCurrentInstance().addMessage("mensaje", msj);
	}

	/**
	 * Resetea un campo
	 * 
	 * @param id
	 * @author Jean Pierre Pacheco Avila
	 */
	public void resetField(String id) {
		RequestContext.getCurrentInstance().reset(id);
	}

	/**
	 * Método para lleva la ventana al tope de la vista.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void scrollTop() {
		RequestContext.getCurrentInstance().scrollTo("formMenu");
	}

	/**
	 * Método get
	 * 
	 * @return
	 */
	public boolean isMuestraTabla() {
		return muestraTabla;
	}

	/**
	 * Método set
	 * 
	 * @param muestraTabla
	 */
	public void setMuestraTabla(boolean muestraTabla) {
		this.muestraTabla = muestraTabla;
	}

	/**
	 * Método get
	 * 
	 * @return
	 */
	public DTOFiltroReporteAcciones getDatos() {
		return datos;
	}

	/**
	 * Método set
	 * 
	 * @param datos
	 */
	public void setDatos(DTOFiltroReporteAcciones datos) {
		this.datos = datos;
	}

	@Override
	public int getOtroTotal() {
		return sumaTotales(dtoParametros.getListaDatos());
	}
	
	/**
	 * Suma los valores de un arreglo
	 * @param datos
	 * @return
	 */
	public int sumaTotales(List<Object[]> datos) {
		int s = 0;
		String i = "";
		for(Object[] r : datos){
			BigDecimal b = (BigDecimal)r[r.length - 1];
			s += b.intValue();
		}
		return s;
	}
	
}