package mx.ine.observadoresINE.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.observadoresINE.bsd.BSDReportesCursosInterface;
import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOReportesParametros;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.helper.HLPReportesEncabezado;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;

public class MBReportesCursos extends MBReportesMenu {
	private static final long serialVersionUID = 3113130095648135798L;
	private static final Log LOGGER = LogFactory.getLog(MBReportesCursos.class);
	@Autowired
	@Qualifier("bsdReportesCursos")
	private transient BSDReportesCursosInterface bsdReportesCursos;

	private DTOFiltroReporteAcciones filtroReportes;
	private boolean muestraTabla;
	private Map<String, Serializable> parametrosPDF;
	private Integer tipoListado;
	private Integer tipoCurso;
	private String[] selectTipoCurso;
	private boolean habilitaFiltroCursos;
	private boolean habilitaFiltroCursos1;
	private boolean habilitaListadoCursos;
	private boolean habilitaListadoIntegrantes;
	private List<DTOCursos> listaCursosMostrar;

	@Override
	public void init() {
		try {
			super.init();
			this.filtroReportes = new DTOFiltroReporteAcciones();
			this.filtroReportes.setUsuario(this.getUsuario());
			this.setMuestraTabla(false);
			this.habilitaFiltroCursos = false;
			this.habilitaFiltroCursos1 = false;
			this.habilitaListadoCursos = false;
			this.habilitaListadoIntegrantes = false;
			this.filtroReportes.setCampoOrdenamiento(null);
			this.filtroReportes.setNivel(obtenNivelReporte());
			LOGGER.info("El nivel es ::: " + this.filtroReportes.getNivel());
			
			this.setMuestraTabla(false);
			
			 
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en :: ", e);
		}
	}

	public void obtenCurso() {
		this.filtroReportes.setOrigenCurso(this.regresaorigenCurso());
		this.listaCursosMostrar = new ArrayList<DTOCursos>();   
		if (this.filtroReportes.getOrigenCurso().length() >= 1) {
			LOGGER.info("El origen curso es ::: " + this.filtroReportes.getOrigenCurso() );
			this.listaCursosMostrar = this.obtenListaCursos(this.filtroReportes);
		}
		LOGGER.info("obtenCurso");
	}

	private String regresaorigenCurso() {
		String resultado = ""; 
		if(this.filtroReportes.getSelectTipoCurso().length == 1){
			resultado += this.filtroReportes.getSelectTipoCurso()[0].equals("1") ? " and  id_agrupacion  is not null " : 
		 		" and id_agrupacion  is null " ;
			} else if (this.filtroReportes.getSelectTipoCurso().length == 2){
				resultado = " ";
			}else{
				resultado = "";
				limpiaTabla();
			}
		
		 
		return resultado;
	}

	public void limpiaTabla(){
		LOGGER.info("En Limpia Tabla");
		this.muestraTabla = false;
		this.dtoParametros = new DTOReportesParametros();
		this.dtoParametros.setListaDatos(new ArrayList<Object[]>());
	}
	
	private List<DTOCursos> obtenListaCursos(DTOFiltroReporteAcciones filtros) {
		this.filtroReportes.setQueryCurso(this.construyeQuery(filtros));
		List<DTOCursos> resultado = bsdReportesCursos.obtenListaCursos(filtros);
		if (resultado != null) {
		} else {
			resultado = new ArrayList<DTOCursos>();
		}
		return resultado;
	}

	public void generaQuery() {
		try {
			this.filtroReportes.setQueryCurso(this.construyeQuery(this.filtroReportes));
			this.consultaReporte();
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en generaQuery  :::", e);
		}
	}

	// TODO
	private String construyeQuery(DTOFiltroReporteAcciones filtros) {
		String queryGeneral = "select  FECHACURSO, ORIGEN, DOMICILIO, HORARIO, IMPARTIDO, CARGO, OBSERVACIONES, totalPersonas , totalAcreditados from ("
				+ "   select TO_CHAR( fecha , 'dd/mm/yyyy') fechaCurso , origen_curso , "
				+ " case   when origen_curso = 1 then 'INE'   when origen_curso = 3  then NOMBRE_AGRUPACION when origen_curso = 2  then 'OPLE'  end origen  "
				+ " ,( case when c.CALLE is not null and c.COLONIA is not null and NOMBRE_MUNICIPIO is not null and c.CODIGO_POSTAL "
				+ " is not null and c.NUMERO_EXTERIOR is not null and c.NUMERO_INTERIOR is not null then c.CALLE ||' '|| ' No. Ext. ' || c.NUMERO_EXTERIOR "
				+ " ||' '|| ' No. Int. ' || c.NUMERO_INTERIOR ||' '||' Col. '|| c.COLONIA ||' '|| ' Del/Mun: ' || NOMBRE_MUNICIPIO || ' C.P '|| "
				+ " c.CODIGO_POSTAL when c.CALLE is not null and c.COLONIA is not null and NOMBRE_MUNICIPIO is not null and c.CODIGO_POSTAL is not null "
				+ " and c.NUMERO_EXTERIOR is not null and c.NUMERO_INTERIOR is null then c.CALLE ||' '|| ' No. Ext. ' || c.NUMERO_EXTERIOR ||' '|| ' No. "
				+ " Int. S/N '||' Col. '|| c.COLONIA ||' '|| ' Del/Mun: ' || NOMBRE_MUNICIPIO || ' C.P '|| c.CODIGO_POSTAL when c.CALLE is not null and "
				+ " c.COLONIA is not null and NOMBRE_MUNICIPIO is not null and c.CODIGO_POSTAL is not null and c.NUMERO_EXTERIOR is null and "
				+ " c.NUMERO_INTERIOR is not null then c.CALLE ||' '|| ' No. Ext. S/N ' || ' No. Int. ' || c.NUMERO_INTERIOR || ' Col. '|| "
				+ " c.COLONIA ||' '|| ' Del/Mun: ' || NOMBRE_MUNICIPIO || ' C.P '|| c.CODIGO_POSTAL else 'S/N' end ) domicilio , ( TO_CHAR(HORA_INICIO, "
				+ "  'HH24:MI') || ' - ' || TO_CHAR(HORA_FIN, 'HH24:MI') || 'hrs.' ) horario , (c.nombre ||' '|| c.apellido_paterno ||' '|| "
				+ " c.apellido_materno ) impartido, r.DESCRIPCION cargo , OBSERVACIONES , c.id_curso , c.id_estado , c.id_distrito , "
				+ " c.id_proceso_electoral , c.id_detalle_proceso from cursos c LEFT join C_cargo_responsable r on r.ID_PROCESO_ELECTORAL = "
				+ " c.ID_PROCESO_ELECTORAL and r.ID_DETALLE_PROCESO = c.ID_DETALLE_PROCESO and r.ID_CARGO = c.ID_CARGO LEFT join geograficoINE.municipios"
				+ "  M on M.ID_ESTADO = c.ID_ESTADO and M.ID_MUNICIPIO = c.ID_MUNICIPIO_domicilio LEFT join geograficoINE.estados E on E.ID_ESTADO ="
				+ "  c.ID_ESTADO  LEFT  join  agrupaciones A   on  A.ID_AGRUPACION =  c.ID_AGRUPACION   and A.id_proceso_electoral =  c.ID_PROCESO_ELECTORAL   "
				+ " and   A.id_detalle_proceso = c.ID_DETALLE_PROCESO   ) T  "
				+ " join (  select s1.id_curso , totalpersonas , nvl( totalAcreditados , 0) totalAcreditados  from ( select c.id_curso , count(o.id_observador) totalPersonas "
				+ " from cursos c LEFT join observadores o on o.ID_PROCESO_ELECTORAL = c.ID_PROCESO_ELECTORAL and o.ID_DETALLE_PROCESO =  c.ID_DETALLE_PROCESO  "
				+ " and o.id_curso = c.id_curso group by c.id_curso   ) S1 LEFT   join ( select c.id_curso , count( o.id_observador) totalAcreditados from cursos c "
				+ "LEFT join observadores o on o.ID_PROCESO_ELECTORAL = c.ID_PROCESO_ELECTORAL and o.ID_DETALLE_PROCESO =  c.ID_DETALLE_PROCESO and o.id_curso = c.id_curso "
				+ " LEFT join c_justificaciones J on o.ID_PROCESO_ELECTORAL = J.ID_PROCESO_ELECTORAL and o.ID_DETALLE_PROCESO =  J.ID_DETALLE_PROCESO and o.ID_JUSTIFICACION =  J.ID_JUSTIFICACION  "
				+ " group by c.id_curso,  J.RESULTADO having J.RESULTADO = 1 ) S2 on s1.id_curso = s2.id_curso ) S on T.id_curso = S.id_curso -filtros- order by -ordenamiento- ";
		
		String whereSubConsulta = " Where id_proceso_electoral = " + this.filtroReportes.getUsuario().getIdProcesoElectoral() + " and  "
				+	 " id_detalle_proceso = " + this.filtroReportes.getUsuario().getIdDetalleProceso() 
				+ "  and id_estado = " +  this.filtroReportes.getUsuario().getIdEstadoSeleccionado() + "  "
						+ " and id_distrito = " +  this.filtroReportes.getUsuario().getIdDistritoSeleccionado();
		if (filtros.getTipoReporte().equals("C")) {
			String cadenaFiltro = "";
			if (filtros.getCampoOrdenamiento().equals(0)) {
				cadenaFiltro += " fechacurso ";
			} else {
				cadenaFiltro += " impartido ";
			}
			if (filtros.getTipoOrdenamiento().equals(1)) {
				cadenaFiltro += " ";
			} else {
				cadenaFiltro += " desc ";
			}
			queryGeneral = queryGeneral.replace("-ordenamiento-", cadenaFiltro);
			queryGeneral = queryGeneral.replace("-filtros-", whereSubConsulta);
			return queryGeneral;
		} else {
			  queryGeneral = "select (o.nombre ||' '|| o.apellido_paterno ||' '|| o.apellido_materno ) nombre , "
					+ " case when origen_curso = 1 then 'INE'  when origen_curso = 2 then 'OPLE'  when origen_curso = 3  then NOMBRE_AGRUPACION end origen , "
					+ " to_char( o.FECHA_SOLICITUDES , 'dd/mm/yyyy' ) fechaSolicitud   , to_char( fecha , 'dd/mm/yyyy' ) fechaCurso , "
					+ " (  TO_CHAR(HORA_INICIO,'HH24:MI') || ' - ' ||  TO_CHAR(HORA_FIN,'HH24:MI') || 'hrs.' ) horario , "
					+ " (c.nombre ||' '|| c.apellido_paterno ||' '|| c.apellido_materno ) impartido , r.DESCRIPCION  cargo , "
					+ " to_char(  o.FECHA_SESION  , 'dd/mm/yyyy' ) fechaAcreditacion from  observadores o  join cursos c on "
					+ " o.ID_PROCESO_ELECTORAL = c.ID_PROCESO_ELECTORAL and o.ID_DETALLE_PROCESO = c.ID_DETALLE_PROCESO and "
					+ " o.id_curso = c.id_curso   join C_cargo_responsable r  on r.ID_PROCESO_ELECTORAL = c.ID_PROCESO_ELECTORAL and "
					+ " r.ID_DETALLE_PROCESO = c.ID_DETALLE_PROCESO and r.ID_CARGO = c.ID_CARGO  LEFT  join  agrupaciones A   on  A.ID_AGRUPACION =  "
					+ " c.ID_AGRUPACION   and A.id_proceso_electoral =  c.ID_PROCESO_ELECTORAL   "
					+ " and   A.id_detalle_proceso = c.ID_DETALLE_PROCESO      -subFiltros- order by -ordenamiento-  ";
			  whereSubConsulta = " Where c.id_proceso_electoral = " + this.filtroReportes.getUsuario().getIdProcesoElectoral() + " and " +
					 " c.id_detalle_proceso = " + this.filtroReportes.getUsuario().getIdDetalleProceso() + 
					"  and c.id_estado = " +  this.filtroReportes.getUsuario().getIdEstadoSeleccionado() + "  "
							+ " and c.id_distrito = " +  this.filtroReportes.getUsuario().getIdDistritoSeleccionado();
			if (filtros.getTipoFiltroEspecifico().equals("C")) {
				 
				String idCurso = "and c.id_curso = " + this.filtroReportes.getIdCursoSeleccionado() + "";
				 
						
				queryGeneral = queryGeneral.replace("-ordenamiento-", " 1 , 2, 3");
				queryGeneral = queryGeneral.replace("-filtros-", idCurso);
				queryGeneral = queryGeneral.replace("-subFiltros-", whereSubConsulta);
				return queryGeneral;
			} else {  
				String tiposCurso = "   ";
//				for (int i = 0; i < this.filtroReportes.getSelectTipoCurso().length; i++) {
//					tiposCurso += this.filtroReportes.getSelectTipoCurso()[i].equals("1") ? "1 , 2" :  this.filtroReportes.getSelectTipoCurso()[i] ;
//					tiposCurso += (i == this.filtroReportes.getSelectTipoCurso().length - 1 ? "" : " , ");
//				}
				if(this.filtroReportes.getSelectTipoCurso().length == 1){
				tiposCurso += this.filtroReportes.getSelectTipoCurso()[0].equals("1") ? " and o.id_agrupacion  is not null " : 
			 		" and o.id_agrupacion  is null " ;
				} 
				
				whereSubConsulta = whereSubConsulta + " " + tiposCurso;
				
				queryGeneral = queryGeneral.replace("-ordenamiento-", " 1 , 2, 3");
//				queryGeneral = queryGeneral.replace("-filtros-", tiposCurso);
				queryGeneral = queryGeneral.replace("-subFiltros-", whereSubConsulta);
				LOGGER.info("El query es :::: " + queryGeneral);
				return queryGeneral;
			}
		}		
	}

	/**
	 * Método para crear reporte
	 */
	public void consultaReporte() {
		try {
			asignaParametrosReporte(bsdReportesCursos.obtenReporteCursos(filtroReportes));
			setMuestraTabla(true);
			setDatosPdf();
		} catch (Exception e) {
			LOGGER.error("Error en MBReporteCurso - consultaReporte()", e);
		}
	}

	private void setDatosPdf() {
		this.parametrosPDF = new LinkedHashMap<>();
		if(this.filtroReportes.getTipoReporte().equals("L") ){
		this.parametrosPDF.put(Constantes.PARAMETRO_INTEGER_COLUMNAS, 8);
		}else{
			this.parametrosPDF.put(Constantes.PARAMETRO_INTEGER_COLUMNAS, 9);
		}
		this.parametrosPDF.put(Constantes.PARAMETRO_STRING_TITULO,"TITULO DEL REPORTE");
		Integer idEstado = this.getUsuario().getIdEstadoSeleccionado() == null ? 0 : this.getUsuario().getIdEstadoSeleccionado();
		Integer idDistrito = this.getUsuario().getIdDistritoSeleccionado() == null ? 0 : this.getUsuario().getIdDistritoSeleccionado();
		this.parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, this.getUsuario().getEstadoSeleccionado() );
		this.parametrosPDF.put(Constantes.PARAMETRO_OBJECT_DISTRITO, this.getUsuario().getDistritoSeleccionado()
				== null ? "Junta Local" : this.getUsuario().getDistritoSeleccionado()  );

	}


	

	private List<HLPReportesEncabezado> obtenEncabezados() {
		List<HLPReportesEncabezado> lista = new ArrayList<HLPReportesEncabezado>();
		HLPReportesEncabezado hlpEncabezadoo = new HLPReportesEncabezado();
		
		if(this.filtroReportes.getTipoReporte().equals("L") ){
		hlpEncabezadoo.ingresarEncabezado(0, 1, 1, "Nombre", 1);
		hlpEncabezadoo.ingresarEncabezado(1, 1, 1, "Curso impartido por Agrupacion / INE / OPLE", 1);
		hlpEncabezadoo.ingresarEncabezado(2, 1, 1, "Fecha Solicitud", 1);
		hlpEncabezadoo.ingresarEncabezado(3, 1, 1, "Fecha Curso", 1);
		hlpEncabezadoo.ingresarEncabezado(4, 1, 1, "Horario", 1);
		hlpEncabezadoo.ingresarEncabezado(5, 1, 1, "Persona que impartio", 1);
		hlpEncabezadoo.ingresarEncabezado(6, 1, 1, "Cargo", 1);
		hlpEncabezadoo.ingresarEncabezado(7, 1, 1, "Fecha de aprobación", 1);
		lista.add(hlpEncabezadoo);
		}else{
			hlpEncabezadoo.ingresarEncabezado(0, 1, 1, "Fecha", 1);
			hlpEncabezadoo.ingresarEncabezado(1, 1, 1, "Impartido por ", 1);
			hlpEncabezadoo.ingresarEncabezado(2, 1, 1, "Domicilio de quien impartio ", 1);
			hlpEncabezadoo.ingresarEncabezado(3, 1, 1, "Horario", 1);
			hlpEncabezadoo.ingresarEncabezado(4, 1, 1, "Persona que lo impartio y/o superviso ", 1);
			hlpEncabezadoo.ingresarEncabezado(5, 1, 1, "Cargo ", 1);
			hlpEncabezadoo.ingresarEncabezado(6, 1, 1, "Nota ", 1);
			hlpEncabezadoo.ingresarEncabezado(7, 1, 1, "No. de ciudadanas/os que tomaron el curso ", 1);
			hlpEncabezadoo.ingresarEncabezado(8, 1, 1, "Personas que acreditaron el curso", 1);
			lista.add(hlpEncabezadoo);
		}
		
		return lista;
	}

 
	
	
	
	
	public void postProcessXLS(Object document) {
		try {
			dtoParametros.setRutaImgEstado("");
			dtoParametros.crearEncabezadoXLS((HSSFWorkbook) document);
		} catch (Exception e) {
//			addErrorMessage("Ocurrió un error al exportar archivo XLS");
//			scrollTop();
			LOGGER.error("Error en postProcessXLS");
			LOGGER.error(e);
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
			setParametros(filtroReportes.getDatosPdf(dtoParametros.getListaDatos() , "cursos"  ));
			super.exportPDF();
		} catch (Exception e) {
//			addErrorMessage("Ocurrió un error al exportar archivo PDF");
//			scrollTop();
			LOGGER.error("Error en exportPDF");
			LOGGER.error(e);
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
		 
		if(this.filtroReportes.getTipoReporte().equals("L") ){
			this.dtoParametros.setAnchoEntidad(2);
			this.dtoParametros.setAnchoDistrito(2);
			this.dtoParametros.setAnchoFechaHora(4);
			}else{
				this.dtoParametros.setAnchoEntidad(2);
				this.dtoParametros.setAnchoDistrito(2);
				this.dtoParametros.setAnchoFechaHora(5);
			}
		
		if ( filtroReportes.esNivel("JL")) {
			LOGGER.info("ES nivel JL");
			 	dtoParametros.setAnchoDistrito(null);
			}
		}
		 
 

  

	
	/**
	 * Método que asigna en la clase padre los datos para la visuzalición de la
	 * tabla del reporte.
	 * 
	 * @param dto
	 */
	public void asignaParametrosReporte(DTOReportesParametros dto) {
		try {
			super.inicializaParametrosEncabezado();
			 
			inicializaDimensionesEncabezado(dto);
			this.dtoParametros.setTituloReporte(dto.getTituloReporte());
			this.dtoParametros.setEncabezado(this.obtenEncabezados());
			if(this.filtroReportes.getTipoReporte().equals("L") ){
			this.dtoParametros.setColumnas(8);
			}else{
			this.dtoParametros.setColumnas(9);	
			}
			this.dtoParametros.setListaDatos(dto.getListaDatos());
			if(this.filtroReportes.getTipoReporte().equals("C") ){
			this.dtoParametros.setTituloReporte(  this.filtroReportes.getUsuario().getIdDistritoSeleccionado() > 0 ?
			"Cursos de capacitación impartidos y/o supervisados por Distrito." :		"Cursos de capacitación impartidos y/o supervisados por junta local.");
			}else{
				this.dtoParametros.setTituloReporte( "Listado de integrantes de los cursos.");	
			}
			super.setNombreReporte("reporteCursos");
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en  asignaParametrosReporte ::::", e);
		}
	}
	

	/**
	 * 
	 * @return
	 */

	public Map<String, Serializable> getParametrosPdf() {
		Map<String, Serializable> parametrosPDF = new LinkedHashMap<>();
		parametrosPDF.put(Constantes.PARAMETRO_INTEGER_COLUMNAS, 3);
		parametrosPDF.put(Constantes.PARAMETRO_STRING_TITULO, "Nombre reporte A");
		parametrosPDF.put(Constantes.PARAMETRO_STRING_FILENAME, "reporteCurso" );
		DTODetalleEstadoProcesoWS estado = getUsuario().getEstadoSeleccionado();
		DTODetalleDistritoProcesoWS distrito = getUsuario().getDistritoSeleccionado();
		parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, estado);
		
		if(distrito != null){
		LOGGER.info(distrito.toString());
		}else{
			 parametrosPDF.put(Constantes.PARAMETRO_OBJECT_DISTRITO, "Junta Local");	
		}
//		 parametrosPDF.put(Constantes.PARAMETRO_OBJECT_DISTRITO, distrito);
		 
		return parametrosPDF;
	}

	public void test() {
		this.habilitaFiltroCursos = !this.habilitaFiltroCursos;
		this.muestraTabla = false;
		if (this.filtroReportes.getTipoReporte() != null) {
			if (this.filtroReportes.getTipoReporte().equals("C")) {
				this.habilitaFiltroCursos = true;
				this.habilitaFiltroCursos1 = false;
				this.filtroReportes.setCampoOrdenamiento(null);
				this.filtroReportes.setTipoFiltroEspecifico(null);
				this.filtroReportes.setSelectTipoCurso(new String[2]);
				this.listaCursosMostrar = new ArrayList<DTOCursos>();
			} else {
				this.habilitaFiltroCursos1 = true;
				this.habilitaFiltroCursos = false;
			}
		} else {
			this.filtroReportes.setTipoFiltroEspecifico(null);
			this.filtroReportes.setSelectTipoCurso(new String[2]);
			this.listaCursosMostrar = new ArrayList<DTOCursos>();
		}
		if (this.filtroReportes.getTipoFiltroEspecifico() != null) {
			if (this.filtroReportes.getTipoFiltroEspecifico().equals("L")) {
				this.habilitaListadoCursos = true;
				this.habilitaListadoIntegrantes = false;
				this.filtroReportes.setSelectTipoCurso(new String[2]); //selectTipoCurso
				this.listaCursosMostrar = new ArrayList<DTOCursos>();
			} else {
				this.habilitaListadoCursos = false;
				this.habilitaListadoIntegrantes = true;
				this.filtroReportes.setSelectTipoCurso(new String[2]);
				this.listaCursosMostrar = new ArrayList<DTOCursos>();
			}
		} else {
			this.habilitaListadoIntegrantes = false;
			this.habilitaListadoCursos = false;
			this.filtroReportes.setSelectTipoCurso(new String[2]);
			this.listaCursosMostrar = new ArrayList<DTOCursos>();
		}
	}

	
	
	public boolean isMuestraTabla() {
		return muestraTabla;
	}
	
	public void setMuestraTabla(boolean muestraTabla) {
		this.muestraTabla = muestraTabla;
	}

	public DTOFiltroReporteAcciones getFiltroReportes() {
		return filtroReportes;
	}

	public void setFiltroReportes(DTOFiltroReporteAcciones datos) {
		this.filtroReportes = datos;
	}

	public Integer getTipoListado() {
		return tipoListado;
	}

	public void setTipoListado(Integer tipoListado) {
		this.tipoListado = tipoListado;
	}

	public Integer getTipoCurso() {
		return tipoCurso;
	}

	public void setTipoCurso(Integer tipoCurso) {
		this.tipoCurso = tipoCurso;
	}

	public String[] getSelectTipoCurso() {
		return selectTipoCurso;
	}

	public void setSelectTipoCurso(String[] selectTipoCurso) {
		this.selectTipoCurso = selectTipoCurso;
	}

	public boolean isHabilitaFiltroCursos() {
		return habilitaFiltroCursos;
	}

	public void setHabilitaFiltroCursos(boolean habilitaFiltroCursos) {
		this.habilitaFiltroCursos = habilitaFiltroCursos;
	}

	public boolean isHabilitaListadoCursos() {
		return habilitaListadoCursos;
	}

	public void setHabilitaListadoCursos(boolean habilitaListadoCursos) {
		this.habilitaListadoCursos = habilitaListadoCursos;
	}

	public boolean isHabilitaListadoIntegrantes() {
		return habilitaListadoIntegrantes;
	}

	public void setHabilitaListadoIntegrantes(boolean habilitaListadoIntegrantes) {
		this.habilitaListadoIntegrantes = habilitaListadoIntegrantes;
	}

	public boolean isHabilitaFiltroCursos1() {
		return habilitaFiltroCursos1;
	}

	public void setHabilitaFiltroCursos1(boolean habilitaFiltroCursos1) {
		this.habilitaFiltroCursos1 = habilitaFiltroCursos1;
	}

	public List<DTOCursos> getListaCursosMostrar() {
		return listaCursosMostrar;
	}

	public void setListaCursosMostrar(List<DTOCursos> listaCursosMostrar) {
		this.listaCursosMostrar = listaCursosMostrar;
	}
}