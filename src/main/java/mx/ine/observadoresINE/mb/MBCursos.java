 /**
 * @(#)MBCursos.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.bsd.BSDAgrupacionesInterface;
import mx.ine.observadoresINE.bsd.BSDCursosInterface;
import mx.ine.observadoresINE.bsd.BSDServiciosGeneralesInterface;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsable;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOCursosPK;
import mx.ine.observadoresINE.dto.form.FormCursos;
import mx.ine.observadoresINE.enums.EnumAccion;
import mx.ine.observadoresINE.enums.EnumMismoDomicilio;
import mx.ine.observadoresINE.util.ApplicationContextUtils;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOLocalidad;
import mx.org.ine.servicios.dto.db.DTOMunicipio;
import mx.org.ine.servicios.exception.ApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

 /**
 * * Clase que proporciona la funcionalidad entre la interfaz de usuario
 * con los datos que se estarán almacenando y/o modificando
 * en la BD del módulo de Cursos.
 * 
 * @author Emmanuel García Ysamit
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
public class MBCursos extends MBGeneric implements Serializable {

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta
	 * clase.
	 */
	private static final long serialVersionUID = 1356924909282613273L;
	
	/** 
	 * Objeto para el servicio de bitácora de mensajes de la aplicación. 
	 */
	private static final Log logger = LogFactory.getLog(MBCursos.class);
	
	@Autowired
	@Qualifier("bsdCursos")
	private transient BSDCursosInterface bsdCursos;
	
	@Autowired
	@Qualifier("bsdServiciosGenerales")
	private transient BSDServiciosGeneralesInterface bsdServicios;
	
	@Autowired
	@Qualifier("bsdAgrupaciones")
	private transient BSDAgrupacionesInterface bsdAgrupaciones;
	
	private String nombreAgrupacion;
	
	/**
	 * Administrador sistema
	 */
	private MBAdministradorSistema mbAdmin;
	
	/**
	 * Usuario loggeado en el sistema
	 */
	private DTOUsuarioLogin usuario;
	
	/**
	 * DTO del formulario a ser llenado
	 */
	private FormCursos filtros;
	
	/**
	 * propiedad que habilita la introduccion de una agrupación
	 */
	private boolean habilitaAgrupacion;
	
	/**
	 * propiedad que actualiza el requerido de un campo
	 */
	private boolean requerido;
	
	/**
	 * indica si muestra el panel de datos o de búsqueda en el modifica
	 */
	private boolean buscaModifica;
	
	/**
	 * Atributo que deshabilita el form en la sección de domicilio
	 */
	private boolean deshabilitaDomicilio;
	
	/**
	 * Muestra quien imparte el curso encontrado
	 */
	private String origenCursoEncontrado;
	
	/**
	 * propiedad para mostrar los resultados de la búsqueda
	 */
	private boolean muestraResultados;
	
	/**
	 * Enum de la impartición de cursos
	 */
	private Map<String, Integer> origenesCurso;
	
	/**
	 * Enum para determinar si es el mismo domicilio
	 */
	private EnumMismoDomicilio enumDomicilio;
	
	/**
	 * lista de cargos
	 */
	private List<DTOCCargoResponsable> cargos;
	/**
	 * Entidades
	 */
	private List<DTOEstado> entidades;
	/**
	 * 
	 */
	private String nombreEstado;
	/**
	 * 
	 */
	private String nombreMunicipio;
	
	/**
	 *Municipios 
	 */
	private List<DTOMunicipio> municipios;
	
	/**
	 * Cursos encontrados
	 */
	private List<DTOCursos> resultadosBusqueda;
	
	/**
	 * DTOCurso curso que se va a guardar
	 */
	private DTOCursos dto;
	
	/**
	 * DTOCurso que va a ser usado para la consulta y modifica
	 */
	private DTOCursos cursoEncontrado;
	
	/**
	 * Determina si la dirección no tiene número
	 */
	private boolean sinNumero;
	
	/**
	 * Atributo para definir la accion del usuario
	 */
	private EnumAccion accion;
	
	/**
	 * Agrupaciones del autocomplete
	 */
	List<String> agrupacionesEncontradas;
	
	/**
	 * Todas las agrupaciones del usuario loggeado
	 */
	List<DTOAgrupaciones> listaAgrupaciones;
	
	/**
	 * Localidades
	 */
	private List<DTOLocalidad> localidades;
	
	/**
	 * Proceso electoral
	 */
	private Integer idProceso;
	
	/**
	 * Detalle proceso electoral
	 */
	private Integer idDetalle;
	
	
	/**
	 * Método que obtiene el valor de el atributo dtoForm
	 *
	 * @return FormCursos : valor que tiene el atributo dtoForm
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 03/07/2017
	 */
	public FormCursos getFiltros() {
		return filtros;
	}

	/**
	 * Método que obtiene el valor de el atributo nombreAgrupacion
	 *
	 * @return String : valor que tiene el atributo nombreAgrupacion
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 20/07/2017
	 */
	public String getNombreAgrupacion() {
		return nombreAgrupacion;
	}

	/**
	 * Método que ingresa el valor de el atributo nombreAgrupacion
	 *
	 * @param nombreAgrupacion : valor que ingresa a el atributo nombreAgrupacion
	 *
	 * @author Emmanuel García Ysamit
	 * @since 20/07/2017
	 */
	public void setNombreAgrupacion(String nombreAgrupacion) {
		this.nombreAgrupacion = nombreAgrupacion;
	}

	/**
	 * Método que ingresa el valor de el atributo dtoForm
	 *
	 * @param dtoForm : valor que ingresa a el atributo dtoForm
	 *
	 * @author Emmanuel García Ysamit
	 * @since 03/07/2017
	 */
	public void setFiltros(FormCursos filtros) {
		this.filtros = filtros;
	}

	/**
	 * Método que obtiene el valor de el atributo habilitaAgrupacion
	 *
	 * @return boolean : valor que tiene el atributo habilitaAgrupacion
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 */
	public boolean isHabilitaAgrupacion() {
		return habilitaAgrupacion;
	}

	/**
	 * Método que ingresa el valor de el atributo habilitaAgrupacion
	 *
	 * @param habilitaAgrupacion : valor que ingresa a el atributo habilitaAgrupacion
	 *
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 */
	public void setHabilitaAgrupacion(boolean habilitaAgrupacion) {
		this.habilitaAgrupacion = habilitaAgrupacion;
	}

	/**
	 * Método que obtiene el valor de el atributo requerido
	 *
	 * @return boolean : valor que tiene el atributo requerido
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 */
	public boolean getRequerido() {
		return requerido;
	}

	/**
	 * Método que ingresa el valor de el atributo requerido
	 *
	 * @param requerido : valor que ingresa a el atributo requerido
	 *
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	/**
	 * Método que obtiene el valor de el atributo buscaModifica
	 *
	 * @return boolean : valor que tiene el atributo buscaModifica
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 19/07/2017
	 */
	public boolean isBuscaModifica() {
		return buscaModifica;
	}

	/**
	 * Método que ingresa el valor de el atributo buscaModifica
	 *
	 * @param buscaModifica : valor que ingresa a el atributo buscaModifica
	 *
	 * @author Emmanuel García Ysamit
	 * @since 19/07/2017
	 */
	public void setBuscaModifica(boolean buscaModifica) {
		this.buscaModifica = buscaModifica;
	}

	/**
	 * Método que obtiene el valor de el atributo deshabilitaDomicilio
	 *
	 * @return boolean : valor que tiene el atributo deshabilitaDomicilio
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 18/07/2017
	 */
	public boolean isDeshabilitaDomicilio() {
		return deshabilitaDomicilio;
	}

	/**
	 * Método que ingresa el valor de el atributo deshabilitaDomicilio
	 *
	 * @param deshabilitaDomicilio : valor que ingresa a el atributo deshabilitaDomicilio
	 *
	 * @author Emmanuel García Ysamit
	 * @since 18/07/2017
	 */
	public void setDeshabilitaDomicilio(boolean deshabilitaDomicilio) {
		this.deshabilitaDomicilio = deshabilitaDomicilio;
	}

	/**
	 * Método que obtiene el valor de el atributo origenCurso
	 *
	 * @return String : valor que tiene el atributo origenCurso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public String getOrigenCursoEncontrado() {
		return origenCursoEncontrado;
	}

	/**
	 * Método que ingresa el valor de el atributo origenCurso
	 *
	 * @param origenCurso : valor que ingresa a el atributo origenCurso
	 *
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public void setOrigenCursoEncontrado(String origenCursoEncontrado) {
		this.origenCursoEncontrado = origenCursoEncontrado;
	}

	/**
	 * Método que obtiene el valor de el atributo muestraResultados
	 *
	 * @return boolean : valor que tiene el atributo muestraResultados
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public boolean getMuestraResultados() {
		return muestraResultados;
	}

	/**
	 * Método que ingresa el valor de el atributo muestraResultados
	 *
	 * @param muestraResultados : valor que ingresa a el atributo muestraResultados
	 *
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public void setMuestraResultados(boolean muestraResultados) {
		this.muestraResultados = muestraResultados;
	}

	/**
	 * Método que obtiene el valor de el atributo origenesCurso
	 *
	 * @return Map<String,Integer> : valor que tiene el atributo origenesCurso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 03/07/2017
	 */
	public Map<String,Integer> getOrigenesCurso() {
		return origenesCurso;
	}

	/**
	 * Método que ingresa el valor de el atributo origenesCurso
	 *
	 * @param Map<String,Integer> : valor que ingresa a el atributo enumImparticion
	 *
	 * @author Emmanuel García Ysamit
	 * @since 03/07/2017
	 */
	public void setOrigenesCurso(Map<String,Integer> origenesCurso) {
		this.origenesCurso = origenesCurso;
	}

	/**
	 * Método que obtiene el valor de el atributo enumDomicilio
	 *
	 * @return EnumMismoDomicilio : valor que tiene el atributo enumDomicilio
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 04/07/2017
	 */
	@SuppressWarnings("static-access")
	public EnumMismoDomicilio[] getEnumDomicilio() {
		return enumDomicilio.values();
	}

	/**
	 * Método que ingresa el valor de el atributo enumDomicilio
	 *
	 * @param enumDomicilio : valor que ingresa a el atributo enumDomicilio
	 *
	 * @author Emmanuel García Ysamit
	 * @since 04/07/2017
	 */
	public void setEnumDomicilio(EnumMismoDomicilio enumDomicilio) {
		this.enumDomicilio = enumDomicilio;
	}

	/**
	 * Método que obtiene el valor de el atributo cargos
	 *
	 * @return List<DTOCCargoResponsable> : valor que tiene el atributo cargos
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 04/07/2017
	 */
	public List<DTOCCargoResponsable> getCargos() {
		return cargos;
	}

	/**
	 * Método que ingresa el valor de el atributo cargos
	 *
	 * @param cargos : valor que ingresa a el atributo cargos
	 *
	 * @author Emmanuel García Ysamit
	 * @since 04/07/2017
	 */
	public void setCargos(List<DTOCCargoResponsable> cargos) {
		this.cargos = cargos;
	}

	/**
	 * Método que obtiene el valor de el atributo entidades
	 *
	 * @return List<DTOEstados> : valor que tiene el atributo entidades
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public List<DTOEstado> getEntidades() {
		return entidades;
	}

	/**
	 * Método que ingresa el valor de el atributo entidades
	 *
	 * @param entidades : valor que ingresa a el atributo entidades
	 *
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void setEntidades(List<DTOEstado> entidades) {
		this.entidades = entidades;
	}

	/**
	 * Método que obtiene el valor de el atributo nombreEstado
	 *
	 * @return String : valor que tiene el atributo nombreEstado
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}

	/**
	 * Método que ingresa el valor de el atributo nombreEstado
	 *
	 * @param nombreEstado : valor que ingresa a el atributo nombreEstado
	 *
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	/**
	 * Método que obtiene el valor de el atributo nombreMunicipio
	 *
	 * @return String : valor que tiene el atributo nombreMunicipio
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	/**
	 * Método que ingresa el valor de el atributo nombreMunicipio
	 *
	 * @param nombreMunicipio : valor que ingresa a el atributo nombreMunicipio
	 *
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	/**
	 * Método que obtiene el valor de el atributo municipios
	 *
	 * @return List<DTOMunicipio> : valor que tiene el atributo municipios
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public List<DTOMunicipio> getMunicipios() {
		return municipios;
	}

	/**
	 * Método que ingresa el valor de el atributo municipios
	 *
	 * @param municipios : valor que ingresa a el atributo municipios
	 *
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void setMunicipios(List<DTOMunicipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * Método que obtiene el valor de el atributo resultadosBusqueda
	 *
	 * @return List<DTOCursos> : valor que tiene el atributo resultadosBusqueda
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public List<DTOCursos> getResultadosBusqueda() {
		return resultadosBusqueda;
	}

	/**
	 * Método que ingresa el valor de el atributo resultadosBusqueda
	 *
	 * @param resultadosBusqueda : valor que ingresa a el atributo resultadosBusqueda
	 *
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public void setResultadosBusqueda(List<DTOCursos> resultadosBusqueda) {
		this.resultadosBusqueda = resultadosBusqueda;
	}

	/**
	 * Método que obtiene el valor de el atributo dto
	 *
	 * @return DTOCursos : valor que tiene el atributo dto
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public DTOCursos getDto() {
		return dto;
	}

	/**
	 * Método que ingresa el valor de el atributo dto
	 *
	 * @param dto : valor que ingresa a el atributo dto
	 *
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public void setDto(DTOCursos dto) {
		this.dto = dto;
	}
	
	/**
	 * Método que obtiene el valor de el atributo cursoEncontrado
	 *
	 * @return DTOCursos : valor que tiene el atributo cursoEncontrado
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public DTOCursos getCursoEncontrado() {
		return cursoEncontrado;
	}

	/**
	 * Método que ingresa el valor de el atributo cursoEncontrado
	 *
	 * @param cursoEncontrado : valor que ingresa a el atributo cursoEncontrado
	 *
	 * @author Emmanuel García Ysamit
	 * @since 13/07/2017
	 */
	public void setCursoEncontrado(DTOCursos cursoEncontrado) {
		this.cursoEncontrado = cursoEncontrado;
	}

	/**
	 * Método que obtiene el valor de el atributo accion
	 *
	 * @return EnumAccion : valor que tiene el atributo accion
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 19/07/2017
	 */
	public EnumAccion getAccion() {
		return accion;
	}

	/**
	 * Método que ingresa el valor de el atributo accion
	 *
	 * @param accion : valor que ingresa a el atributo accion
	 *
	 * @author Emmanuel García Ysamit
	 * @since 19/07/2017
	 */
	public void setAccion(EnumAccion accion) {
		this.accion = accion;
	}

	/**
	 * Método que obtiene el valor de el atributo sinNumero
	 *
	 * @return boolean : valor que tiene el atributo sinNumero
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public boolean isSinNumero() {
		return sinNumero;
	}

	/**
	 * Método que ingresa el valor de el atributo sinNumero
	 *
	 * @param sinNumero : valor que ingresa a el atributo sinNumero
	 *
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public void setSinNumero(boolean sinNumero) {
		this.sinNumero = sinNumero;
	}

	/**
	 * Método que obtiene el valor de el atributo idProceso
	 *
	 * @return Integer : valor que tiene el atributo idProceso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public Integer getIdProceso() {
		return idProceso;
	}

	/**
	 * Método que ingresa el valor de el atributo idProceso
	 *
	 * @param idProceso : valor que ingresa a el atributo idProceso
	 *
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	/**
	 * Método que obtiene el valor de el atributo idDetalle
	 *
	 * @return Integer : valor que tiene el atributo idDetalle
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public Integer getIdDetalle() {
		return idDetalle;
	}

	/**
	 * Método que ingresa el valor de el atributo idDetalle
	 *
	 * @param idDetalle : valor que ingresa a el atributo idDetalle
	 *
	 * @author Emmanuel García Ysamit
	 * @since 11/07/2017
	 */
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	/**
	 * Método que obtiene el valor de el atributo localidades
	 *
	 * @return List<DTOLocalidad> : valor que tiene el atributo localidades
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public List<DTOLocalidad> getLocalidades() {
		return localidades;
	}

	/**
	 * Método que ingresa el valor de el atributo localidades
	 *
	 * @param localidades : valor que ingresa a el atributo localidades
	 *
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void setLocalidades(List<DTOLocalidad> localidades) {
		this.localidades = localidades;
	}

	/**
	 * Constructor de la clase
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 28/06/2017
	 */
	public MBCursos(){
		//Obtener administrador
		mbAdmin = (MBAdministradorSistema) ApplicationContextUtils
				.getApplicationContext().getBean(Constantes.MB_ADMIN);
		this.cargos = null;
		this.entidades = null;
		this.localidades = null;
		this.municipios = null;
		this.origenesCurso = new LinkedHashMap<String, Integer>();
		this.origenesCurso.put("INE", 1);
		this.origenesCurso.put("OPLE", 2);
		this.origenesCurso.put("Agrupaciones", 3);
		
	}
	
	/**
	 * Método que inicializa la clase
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 28/06/2017
	 */
	public void initCaptura(){
		setHabilitaAgrupacion(false);
		setRequerido(false);
		this.nombreAgrupacion=null;
		setSinNumero(false);
		setDeshabilitaDomicilio(false);
		this.usuario = mbAdmin.getDto().getUsuario();
		idProceso = usuario.getIdProcesoElectoral();
		idDetalle = usuario.getIdDetalleProceso();
		//idProceso = 9;
		//idDetalle = 38;
		try{
			this.listaAgrupaciones = bsdAgrupaciones.obtenAgrupaciones(idProceso, idDetalle);	
		}catch(Exception e){
			logger.error("Error en el método obtenAgrupaciones: ", e);
		}
		DTOCursosPK dtoId = new DTOCursosPK();
		dtoId.setIdCurso(0);
		dtoId.setIdProcesoElectoral(idProceso);
		dtoId.setIdDetalleProceso(idDetalle);
		dto = new DTOCursos(dtoId);
		dto.setIdDistrito(mbAdmin.getDto().getUsuario().getIdDistritoSeleccionado());
		dto.setIdEstado(mbAdmin.getDto().getUsuario().getIdEstadoSeleccionado());
		//dto.setIdEstado(5);
		//dto.setIdDistrito(1);
		cargaCargos();
		cargaEntidades();
	}
	
	
	/**
	 * Método que inicializa la clase para consultar
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public void initConsulta(){
		setMuestraResultados(false);
		setHabilitaAgrupacion(false);
		setRequerido(false);
		setSinNumero(false);
		setDeshabilitaDomicilio(false);
		this.nombreAgrupacion=null;
		this.usuario = mbAdmin.getDto().getUsuario();
		idProceso = usuario.getIdProcesoElectoral();
		idDetalle = usuario.getIdDetalleProceso();
		resultadosBusqueda = new ArrayList<DTOCursos>();
		nombreEstado = "";
		nombreMunicipio = "";
		//idProceso = 9;
		//idDetalle = 38;
		try{
			this.listaAgrupaciones = bsdAgrupaciones.obtenAgrupaciones(idProceso, idDetalle);	
		}catch(Exception e){
			logger.error("Error en el método obtenAgrupaciones: ", e);
		}
		
		if(this.dto == null){
			DTOCursosPK dtoId = new DTOCursosPK();
			dtoId.setIdCurso(0);
			dtoId.setIdProcesoElectoral(idProceso);
			dtoId.setIdDetalleProceso(idDetalle);
			dto = new DTOCursos(dtoId);
			//dto.setIdEstado(5);
			//dto.setIdDistrito(1);
			dto.setIdDistrito(mbAdmin.getDto().getUsuario().getIdDistritoSeleccionado());
			dto.setIdEstado(mbAdmin.getDto().getUsuario().getIdEstadoSeleccionado());
		}
		
		if(this.filtros == null){
			filtros = new FormCursos();
			filtros.setIdProceso(idProceso);
			filtros.setIdDetalleProceso(idDetalle);
			filtros.setIdEstado(mbAdmin.getDto().getUsuario().getIdEstadoSeleccionado());
			filtros.setIdDistrito(mbAdmin.getDto().getUsuario().getIdDistritoSeleccionado());
		}else{
			buscaCursos();
			if(filtros.getOrigenCurso() == 3){
				setHabilitaAgrupacion(true);
				setRequerido(true);
			}
		}
		cargaEntidades();
		if(accion.equals(EnumAccion.M)){
			cargaCargos();
			try{
				this.listaAgrupaciones = bsdAgrupaciones.obtenAgrupaciones(idProceso, idDetalle);	
			}catch(Exception e){
				logger.error("Error en el método obtenAgrupaciones: ", e);
			}
		}
	}
	

	
	/**
	 * Método que verifica que no haya un observador asignado a un curso para poder borrarlo
	 * 
	 * @author Emmanuel García Ysamit
	 * @param cursoEncontrado
	 * @return
	 * @since 26/07/2017
	 */
	public boolean verificaObservador(DTOCursos cursoEncontrado){
		boolean respuesta = false;
		try{
			respuesta = bsdCursos.verificaObservador(cursoEncontrado);
			if(!respuesta){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", Utilidades.mensajeProperties("mensaje_error_curso_con_observador")));
			}
		}catch(Exception e){
			logger.error("Error en método verificaObservador()", e);
		}
		
		return respuesta;
	}
	
	
	/**
	 * Método que va a buscar los cursos
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 07/07/2017
	 */
	public void buscaCursos(){
		try{
			if(validaConsulta()){
				setResultadosBusqueda(bsdCursos.buscaPorFecha(filtros, obtenNivelOficinas()));
			}else{
				setResultadosBusqueda(bsdCursos.buscaImparte(filtros, obtenNivelOficinas()));
			}
		}catch(Exception e){
			logger.error("Ocurrió un error en el método buscaCursos", e);
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocurrió un error en el sistema."));
		}
		setMuestraResultados(true);
	}
	
	/**
	 * Método que valida si en la consulta
	 * se metieron datos de fecha o solo de quien imparte el curso
	 * 
	 * @return
	 */
	public boolean validaConsulta(){
		boolean hayFecha = false;
		if(filtros.getFecha() != null){
			hayFecha = true;
		}
		return hayFecha;
	}
	
	/**
	 * Método que obtiene los cargos de la persona que imparte el curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 03/07/2017
	 */
	public void cargaCargos(){
		try{
			setCargos(bsdCursos.obtenCargos(idProceso, idDetalle));
		}catch(Exception e){
			logger.error("Ocurrio un error en el metodo obtenCargos(idProceso, idDetalle): ", e);
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ocurrió un error en el sistema"));
		}
		if(this.cargos.isEmpty() || this.cargos == null){
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No hay cargos disponibles"));
		}
	}
	
	/**
	 * Método que obtiene las agrupaciones para el autocomplete
	 * 
	 * @author Emmanuel García Ysamit
	 * @param cadena
	 * @return
	 * @since 20/07/2017
	 */
	public List<String> getAgrupacionesByNombreSiglasLike(String cadena){
		 agrupacionesEncontradas = null;
		try{
			//agrupacionesEncontradas = bsdCursos.getAgrupacionesByNombre(cadena, idProceso, idDetalle);
			String cadenaAMandar = cadena;
			agrupacionesEncontradas = bsdCursos.getAgrupacionesByNombre(cadenaAMandar, idProceso, idDetalle);
		}catch(Exception e){
			logger.error("Ocurrió un error en el método getAgrupacionesByNombreSiglasLike", e);
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocurrió un error en el sistema"));
		}
		if(agrupacionesEncontradas.isEmpty() || agrupacionesEncontradas == null){
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No hay agrupaciones disponibles"));
		}
		return agrupacionesEncontradas;
	}
	
	/**
	 * Método que selecciona la agrupación que va a ser guardada en la base
	 * si el usuario selecciona Agrupacion en la sección "Impartido por"
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 26/07/2017
	 */
	public void seleccionaAgrupacion(){
		boolean bandera = false;
		if(this.getAccion().equals(EnumAccion.C)){
			for(DTOAgrupaciones agrupacion : listaAgrupaciones){
				if(agrupacion.getNombreAgrupacion().equalsIgnoreCase(nombreAgrupacion)){
					dto.setIdAgrupacion(agrupacion.getPk().getIdAgrupacion());
					bandera = true;
					break;
				}
			}
			if(!bandera){
				this.nombreAgrupacion = "";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se encontraron agrupaciones"));
			}
		}else{
			for(DTOAgrupaciones agrupacion : listaAgrupaciones){
				if(agrupacion.getNombreAgrupacion().equalsIgnoreCase(nombreAgrupacion)){
					filtros.setIdAgrupacion(agrupacion.getPk().getIdAgrupacion());
					break;
				}
			}
		}
	}
	
	/**
	 * Método que obtiene los cargos de la persona que imparte el curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void cargaEntidades(){
		try{
				setEntidades(bsdServicios.obtenEstados());
		}catch(Exception e){
			logger.error("Ocurrio un error en el metodo cargaEntidades(): ", e);
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocurrió un error en el sistema"));
		}
		if(this.entidades.isEmpty() || this.entidades == null){
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No hay entidades disponibles"));
		}
	}
	
	/**
	 * Método que obtiene los cargos de la persona que imparte el curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void cargaMunicipios(){
		try{
				limpiaMunicipios();
				setMunicipios(bsdServicios.obtenMunicipios(dto.getIdEstadoDomicilio(), EnumAmbitoSistema.F));
		}catch(Exception e){
			logger.error("Ocurrio un error en el metodo cargaMunicipios(): ", e);
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocurrió un error en el sistema"));
		}
		if(this.entidades.isEmpty() || this.entidades == null){
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No hay municipios disponibles"));
		}
	}
	
	/**
	 * Método que obtiene el nombre de una entidad dado su id
	 * 
	 * @author Emmanuel García Ysamit
	 * @param id
	 * @since 13/07/2017
	 */
	public void obtenNombreEstado(Integer idEstado){
		for(DTOEstado estado : entidades){
			if(estado.getIdEstado().equals(idEstado)){
				setNombreEstado(estado.getNombreEstado());
				break;
			}else{
				setNombreEstado(null);
			}
		}
	}
	
	/**
	 * Método que obtiene el nombre de un municipio dado su id
	 * 
	 * @author Emmanuel García Ysamit
	 * @param id
	 * @since 13/07/2017
	 */
	public void obtenNombreMunicipio(Integer idEstado, Integer idMunicipio){
		try{
			if(idEstado != null && idMunicipio != null){
				DTOMunicipio mun = bsdServicios.obtenMunicipio(idEstado, idMunicipio, EnumAmbitoSistema.F);
				setNombreMunicipio(mun.getNombreMunicipio());
			}
		}catch(Exception e){
			setNombreMunicipio(null);
			logger.error("Error en método obtenNombreMunicipio(): ", e);
		}
	}
	
	/**
	 * Método que se encarga de guardar/actualizar un curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public String guardaCurso(){
		String respuesta = "";
		try{
			if(validaGuardar()){
				bsdCursos.guardarOActualizar(dto);
				respuesta = "guardar";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Curso guardado exitosamente."));
			}
		}catch(Exception e){
			logger.error("Error en método guardaCurso(): ", e);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al guardar el curso."));
		}
		return respuesta;
	}
	
	
	/**
	 * Método que se encarga de validar antes de guardar los campos de apellidos
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 */
	public boolean validaGuardar(){
		boolean respuesta = false;
		try{
			if (bsdCursos.esNombreValido(dto)) {
				if (bsdCursos.sonApellidosInvalidos(dto)) {
					enviaMensajeException(new ApplicationException(Utilidades.mensajeProperties("mensaje_error_cursos_apellidos"), 
		                    Constantes.CODIGO_EXEPTION));
					scrollTop();
					return respuesta;
				}else{
					respuesta = true;
				}
			}
			if(dto.getAgrupaciones() != null){
				dto.setIdAgrupacion(dto.getAgrupaciones().getPk().getIdAgrupacion());
			}
		}catch(Exception e){
			logger.error("Error en metodo validaGuardar()", e);
		}
		return respuesta;
	}
	
	/**
	 * Validador de las horas de fin
	 * 
	 * @author Emmanuel García Ysamit
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 * @since 24/07/2017
	 */
	@SuppressWarnings("deprecation")
	public void validateHoraFin(FacesContext context, UIComponent component, Object value) throws ValidatorException{
		Date horaInicio = new Date();
		Date horaFin = new Date();
		try{
			horaInicio = (Date) ((UIInput) context.getViewRoot().findComponent("forma:horaInicio")).getValue();
			horaFin = (Date) value;
			if(horaInicio != null){
				Calendar horaInicioCalendar = new GregorianCalendar();
				Calendar horaFinCalendar    = new GregorianCalendar();
				horaInicioCalendar.setTime(horaInicio);
				horaFinCalendar.setTime(horaFin);
				Integer horasInicio = horaInicio.getHours();
				Integer horasFin = horaFin.getHours();
				Integer minutosInicio = horaInicio.getMinutes();
				Integer minutosFin = horaFin.getMinutes();
				
				Integer diferenciaHoras = horasFin - horasInicio;
				if(diferenciaHoras < 0){
					//Mensaje
		            FacesMessage message = new FacesMessage();
		            message.setSeverity(FacesMessage.SEVERITY_ERROR);
		            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_termino_menor_a_hora_ini"));
		            context.addMessage(component.getClientId(), message);
		            context.renderResponse();
		            scrollTop();
				}else if( diferenciaHoras == 0){
					Integer diferenciaMinutos = minutosFin - minutosInicio;
					if(diferenciaMinutos <= 0 ){
						//Mensaje
			            FacesMessage message = new FacesMessage();
			            message.setSeverity(FacesMessage.SEVERITY_ERROR);
			            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_termino_menor_a_hora_ini"));
			            context.addMessage(component.getClientId(), message);
			            context.renderResponse();
			            scrollTop();
					}
				}
				
			}else{
				//Mensaje
	            FacesMessage message = new FacesMessage();
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_inicio_vacia"));
	            context.addMessage(component.getClientId(), message);
	            context.renderResponse();
			}	
					
		}catch(ValidatorException e){
			logger.error("Error en validar", e);
		}
		
	}
	
	/**
	 * Validador de las horas de inicio
	 * 
	 * @author Emmanuel García Ysamit
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 * @since 24/07/2017
	 */
	@SuppressWarnings("deprecation")
	public void validateHoraInicio(FacesContext context, UIComponent component, Object value) throws ValidatorException{
		Date horaInicio = new Date();
		Date horaFin = new Date();
		try{
			horaFin = (Date) ((UIInput) context.getViewRoot().findComponent("forma:horaFin")).getValue();
			horaInicio = (Date) value;
			if(horaFin != null){
				Calendar horaInicioCalendar = new GregorianCalendar();
				Calendar horaFinCalendar    = new GregorianCalendar();
				horaInicioCalendar.setTime(horaInicio);
				horaFinCalendar.setTime(horaFin);
				Integer horasInicio = horaInicio.getHours();
				Integer horasFin = horaFin.getHours();
				Integer minutosInicio = horaInicio.getMinutes();
				Integer minutosFin = horaFin.getMinutes();
				
				Integer diferenciaHoras = horasFin - horasInicio;
				if(diferenciaHoras < 0){
					//Mensaje
		            FacesMessage message = new FacesMessage();
		            message.setSeverity(FacesMessage.SEVERITY_ERROR);
		            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_inicio_mayo_a_hora_fin"));
		            context.addMessage(component.getClientId(), message);
		            context.renderResponse();
		            scrollTop();
				}else if( diferenciaHoras == 0){
					Integer diferenciaMinutos = minutosFin - minutosInicio;
					if(diferenciaMinutos <= 0 ){
						//Mensaje
			            FacesMessage message = new FacesMessage();
			            message.setSeverity(FacesMessage.SEVERITY_ERROR);
			            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_inicio_mayo_a_hora_fin"));
			            context.addMessage(component.getClientId(), message);
			            context.renderResponse();
			            scrollTop();
					}
				}
				
			}else{
				//Mensaje de que la fecha de termino es requerida
//	            FacesMessage message = new FacesMessage();
//	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//	            message.setSummary(Utilidades.mensajeProperties("mensaje_error_hora_termino_vacia"));
//	            context.addMessage(component.getClientId(), message);
//	            context.renderResponse();
			}	
					
		}catch(ValidatorException e){
			logger.error("Error en validar", e);
		}
		
	}
	
	/**
	 * Método que limpia el combo de Municipios
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 05/07/2017
	 */
	public void limpiaMunicipios() {
		this.municipios = new ArrayList<DTOMunicipio>();
		dto.setIdMunicipioDomicilio(null);
	}
	
	
	/**
	 * Método habilita el combo de agrupaciones
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 * */
	public void habilitaAutoComplete(){
		if(dto.getOrigenCurso() == 1 || dto.getOrigenCurso() == 2){
			setHabilitaAgrupacion(false);
			setRequerido(false);
			setNombreAgrupacion(null);
		}else{
			setHabilitaAgrupacion(true);
			setRequerido(true);
		}
	}
	
	/**
	 * Método habilita el combo de agrupaciones
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 06/07/2017
	 * */
	public void habilitaAutoCompleteConsulta(){
		if(filtros.getOrigenCurso() == 1 || filtros.getOrigenCurso() == 2){
			setHabilitaAgrupacion(false);
			setRequerido(false);
			setNombreAgrupacion(null);
			filtros.setIdAgrupacion(null);
		}else{
			setHabilitaAgrupacion(true);
			setRequerido(true);
		}
		setMuestraResultados(false);
	}
	
	/**
	 * Método que muestra el popup para la consulta del curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @param curso
	 * @since 13/07/2017
	 */
	public void muestraDetalleCurso(DTOCursos curso){
		limpiaPopup();
		DTOCursos cursoEncontrado = new DTOCursos();
		BeanUtils.copyProperties(curso, cursoEncontrado);
		setCursoEncontrado(cursoEncontrado);
		if(curso.getMismoDomicilio().equals(EnumMismoDomicilio.S)){
			bsdCursos.obtenDomicilioDeLaJunta(this.cursoEncontrado);
		}
		if(curso.getIdEstadoDomicilio() != null && curso.getIdEstadoDomicilio() != 0){
			obtenNombreEstado(curso.getIdEstadoDomicilio());
		}
		if(curso.getIdMunicipioDomicilio() != null && curso.getIdMunicipioDomicilio() != 0){
			obtenNombreMunicipio(curso.getIdEstadoDomicilio(), curso.getIdMunicipioDomicilio());
		}
		if(curso.getOrigenCurso() == 1){
			setOrigenCursoEncontrado("INE");
		}else if(curso.getOrigenCurso() == 2){
			setOrigenCursoEncontrado("OPLE");
		}else{
			setOrigenCursoEncontrado(curso.getAgrupaciones().getNombreAgrupacion());
		}
	}
	
	/**
	 * Método que limpia el popup de la consulta
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 14/07/2017
	 */
	public void limpiaPopup(){
		this.cursoEncontrado = new DTOCursos();
		setNombreEstado(null);
		setNombreMunicipio(null);
	}

	
	/**
	 *Método que limpia los valores de Numero interior y exterior en caso de que se seleccione el checkbox sinNumero 
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 18/07/2017
	 */
	public void seleccionaSinNumero(){
		if(sinNumero){
			dto.setNumeroExterior(null);
			dto.setNumeroInterior(null);
		}
	}
	
	/**
	 * Método que trae el domicilio de la junta en caso de ser seleccionado
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 18/07/2017
	 */
	public void seleccionaDomJunta(){
		if(dto.getMismoDomicilio() == EnumMismoDomicilio.S){
			setDeshabilitaDomicilio(true);
			boolean domicilioJunta = bsdCursos.obtenDomicilioDeLaJunta(dto);
			if(!domicilioJunta){
				setDeshabilitaDomicilio(false);
				dto.setMismoDomicilio(EnumMismoDomicilio.N);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se encontró domicilio de la junta."));
				
			}else{
				setSinNumero(false);
				Integer municipioTemporal = dto.getIdMunicipioDomicilio();
				cargaMunicipios();
				dto.setIdMunicipioDomicilio(municipioTemporal);
			}
		}else{
			limpiaMunicipios();
			setDeshabilitaDomicilio(false);
			dto.setCalle(null);
			dto.setCodigoPostal(null);
			dto.setColonia(null);
			dto.setNumeroInterior(null);
			dto.setNumeroExterior(null);
			dto.setIdMunicipioDomicilio(null);
			dto.setIdEstadoDomicilio(null);
		}
	}
	
	/**
	 * Método encargado de enviar mensaje a la vista dependiendo el codigo de
	 * exception
	 * 
	 * @param e
	 *
	 * @author Emmanuel García Ysamit
	 * @since 19/07/2017
	 */
	private void enviaMensajeException(ApplicationException e) {
		// Error
		if (e.getCodigoError() == Constantes.CODIGO_EXEPTION) {
			agregaMensaje(TipoMensaje.ERROR_MENSAJE, e.getMessage());
			// Advertencia
		} else {
			agregaMensaje(TipoMensaje.ADVERTENCIA_MENSAJE, e.getMessage());
		}
	}
	
	/**
	 * Método para lleva la ventana al tope de la vista.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void scrollTop() {
		RequestContext.getCurrentInstance().scrollTo("forma");
	}
	
	/**
	 * Método que setea el dto con el valor del curso encontrado
	 * 
	 * @author Emmanuel García Ysamit
	 * @param cursoEncontrado
	 * @since 19/07/2017
	 */
	public void modificaCurso(DTOCursos cursoEncontrado){
		this.dto = new DTOCursos();
		BeanUtils.copyProperties(cursoEncontrado, dto);
		setCursoEncontrado(this.dto);
		if(dto.getIdMunicipioDomicilio() != null){
			int auxMunicipio = dto.getIdMunicipioDomicilio();
			cargaMunicipios();
			dto.setIdMunicipioDomicilio(auxMunicipio);
		}
		if(dto.getMismoDomicilio().equals(EnumMismoDomicilio.S)){
			setDeshabilitaDomicilio(true);
		}
		setBuscaModifica(true);
		
	}
	
	public void cancelaForm(){
		setDto(this.cursoEncontrado);
		scrollTop();
	}
	
	/**
	 * Método que elimina el curso encontrado
	 * 
	 * @author Emmanuel García Ysamit
	 * @param cursoEncontrado
	 * @since 19/07/2017
	 */
	public String eliminaCurso(DTOCursos cursoEncontrado){
		String respuesta = "";
		try{
			if(verificaObservador(cursoEncontrado)){
				bsdCursos.eliminaCurso(cursoEncontrado);
				respuesta = "eliminar";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Se eliminó el curso correctamente."));
			}
		}catch(Exception e){
			logger.error("Error en método eliminarCurso", e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al eliminar el curso."));
		}
		return respuesta;
	}
	
	/**
     * Obtiene el nivel en el que se encuentra el usuario 
     * según la grografía seleccionada en el menú.
     * @return
     */
    private Integer obtenNivelOficinas(){
    	
    	Integer nivel = 0;
    	Integer idEstado = mbAdmin.getDto().getUsuario().getIdEstadoSeleccionado();
    	Integer idDistrito = mbAdmin.getDto().getUsuario().getIdDistritoSeleccionado();
    	
    	if(idEstado != null && idEstado.intValue() == 0){
    		nivel = 1;
    	}
    	if(idEstado != null && idEstado.intValue() > 0){
    		nivel = 2;
    		if(idDistrito != null && idDistrito.intValue() > 0){
    			nivel = 3;
    		}
    	}
    	
    	return nivel;
    }
}
