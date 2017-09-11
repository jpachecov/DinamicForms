package mx.ine.observadoresINE.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.observadoresINE.mb.MBReporteAcciones;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;

public class DTOFiltroReporteAcciones implements Serializable{


	/**
	 * Serial
	 */
	private static final long serialVersionUID = -1473622999530784879L;
	
	private static final Log log = LogFactory.getLog(DTOFiltroReporteAcciones.class);

	/**
	 * Filtros del reporte
	 */
	private DTOUsuarioLogin usuario;
	private String nivel;
	private String tipoReporte;
	private Date fechaIni;
	private Date fechaFin;
	private String tipoFiltroEspecifico;
	private Integer campoOrdenamiento;
	private Integer tipoOrdenamiento;
	private Integer idCursoSeleccionado;
	private String origenCurso;
	private String queryCurso;
	private String[] selectTipoCurso = new String[2];
	
	
	public Map<String, Serializable> getDatosPdf(List<Object[]> datos , String palabra){
		
		Map<String, Serializable> parametrosPDF = new LinkedHashMap<>();
		String n_rep = "";
		String n_pdf = "";
		if(!palabra.equals("cursos")){
		 n_rep = Utilidades.mensajeProperties("etiqueta_reportes_accionesDePromocion");
		 n_pdf = Utilidades.mensajeProperties("etiqueta_reportes_accionesDePromocion_pdf");
		} else {
			 n_rep = "Reportes de Cursos de Capacitación ";
			 n_pdf = " RepoCursosCapa";
		}
		parametrosPDF.put(Constantes.PARAMETRO_INTEGER_COLUMNAS, 3);
		parametrosPDF.put(Constantes.PARAMETRO_STRING_TITULO, n_rep);
		parametrosPDF.put(Constantes.PARAMETRO_STRING_FILENAME, n_pdf);
		DTODetalleEstadoProcesoWS estado = getUsuario().getEstadoSeleccionado();
		DTODetalleDistritoProcesoWS distrito = getUsuario().getDistritoSeleccionado();
		if (esNivel("OC") || esNivel("JL")) {
			parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, estado);
		}
		if (esNivel("JD")) {
			parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, estado);
			parametrosPDF.put(Constantes.PARAMETRO_OBJECT_DISTRITO, distrito);
		}
		if(getTipoReporte().equals("C")){
			parametrosPDF.put(Constantes.PARAMETRO_STRING_TOTALES, sumaTotales(datos) );
		}
		if(getTipoReporte().equals("L")){
			parametrosPDF.put(Constantes.PARAMETRO_STRING_TOTALES, datos.size() );
		}
		return parametrosPDF;
		
		
	}
	
	
	
	
	@Override
	public String toString() {
		return "DTOFiltroReporteAcciones [usuario=" + usuario + ", nivel=" + nivel + ", tipoReporte=" + tipoReporte
				+ ", fechaIni=" + fechaIni + ", fechaFin=" + fechaFin + ", tipoFiltroEspecifico=" + tipoFiltroEspecifico
				+ ", campoOrdenamiento=" + campoOrdenamiento + ", tipoOrdenamiento=" + tipoOrdenamiento
				+ ", idCursoSeleccionado=" + idCursoSeleccionado + ", origenCurso=" + origenCurso + ", queryCurso="
				+ queryCurso + ", selectTipoCurso=" + Arrays.toString(selectTipoCurso) + "]";
	}
	
	
	/**
	 * 
	 * Decide si el nivel de oficinas coincide.
	 * 
	 * @param nivel
	 *            El nivel de oficinas (depende del estado del menú lateral) OC,
	 *            JD, JL
	 * @return True si el nivel de oficinas coincide con el dado.
	 */
	public boolean esNivel(String nivel) {
		return getNivel().equals(nivel);
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
	
	public String getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public DTOUsuarioLogin getUsuario() {
		return usuario;
	}
	public void setUsuario(DTOUsuarioLogin usuario) {
		this.usuario = usuario;
	}
	public String getTipoFiltroEspecifico() {
		return tipoFiltroEspecifico;
	}
	public void setTipoFiltroEspecifico(String tipoFiltroEspecifico) {
		this.tipoFiltroEspecifico = tipoFiltroEspecifico;
	}
	public Integer getCampoOrdenamiento() {
		return campoOrdenamiento;
	}
	public void setCampoOrdenamiento(Integer campoOrdenamiento) {
		this.campoOrdenamiento = campoOrdenamiento;
	}
	public Integer getTipoOrdenamiento() {
		return tipoOrdenamiento;
	}
	public void setTipoOrdenamiento(Integer tipoOrdenamiento) {
		this.tipoOrdenamiento = tipoOrdenamiento;
	}
	public Integer getIdCursoSeleccionado() {
		return idCursoSeleccionado;
	}
	public void setIdCursoSeleccionado(Integer idCursoSeleccionado) {
		this.idCursoSeleccionado = idCursoSeleccionado;
	}
	public String getOrigenCurso() {
		return origenCurso;
	}
	public void setOrigenCurso(String origenCurso) {
		this.origenCurso = origenCurso;
	}
	public String getQueryCurso() {
		return queryCurso;
	}
	public void setQueryCurso(String queryCurso) {
		this.queryCurso = queryCurso;
	}
	public String[] getSelectTipoCurso() {
		return selectTipoCurso;
	}
	public void setSelectTipoCurso(String[] selectTipoCurso) {
		this.selectTipoCurso = selectTipoCurso;
	}
}
