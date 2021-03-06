package mx.ine.observadoresINE.mb;

import java.io.File;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.webapp.MultipartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import edu.umd.cs.findbugs.filter.Matcher;
import mx.ine.observadoresINE.bsd.BSDObservadoresInterface;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridades;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacion;
import mx.ine.observadoresINE.dto.db.DTOCJustificaciones;
import mx.ine.observadoresINE.dto.db.DTOCJustificacionesPK;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOCursosPK;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.db.DTOObservadoresPK;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

/**
 * <code>MBObservadores.java</code>
 *
 * @author Carlos Augusto Escaona Navarro
 * @version 0.0.1
 * @since 04/07/2017
 */

public class MBObservadores extends MBGeneric implements Serializable {

	private static final long serialVersionUID = 2387341360059024321L;
	private static final Log LOGGER = LogFactory.getLog(MBObservadores.class);
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	@Qualifier("bsdObservadores")
	private transient BSDObservadoresInterface bsdObservadorInterface;
	
	@Autowired
	@Qualifier("mbAdmin")
	private transient MBAdministradorSistema mbAdmin;
	
	private DTOObservadores observadorCaptura;
	private DTOObservadores observadorConsulta;
	private DTOUsuarioLogin usuario;
	private String parteClaveElector1;
	private String parteClaveElector2;
	private String parteClaveElector3;
	private Boolean regla1;
	private Boolean regla2;
	private Boolean regla3;
	private Boolean bloqueaSN;
	private List<DTOCEscolaridades> listaEscolaridad = new ArrayList<DTOCEscolaridades>();
	private List<DTOEstado> listaEstados = new ArrayList<DTOEstado>();
	private List<DTOMunicipio> listaMunicipios = new ArrayList<DTOMunicipio>();
	private List<DTOAgrupaciones> listaAgrupaciones = new ArrayList<DTOAgrupaciones>();
	private List<DTOCEvaluacion> listaEvaluaciones = new ArrayList<DTOCEvaluacion>();
	private List<DTOCursos> listaCursos = new ArrayList<DTOCursos>();
	private List<DTOCJustificaciones> listaJustificaciones = new ArrayList<DTOCJustificaciones>();
	private List<DTOReglasEvalucaion> listaReglas = new ArrayList<DTOReglasEvalucaion>();
	private Integer tipoSolicitud;
	private Boolean habilitaAgrupacion;
	private DiskFileItem fileItem;
	private String nombreArchivo;
	private Boolean habilitaRatifica;
	private String busqueda;
	private Boolean habilitaComboNombre;
	private Boolean habilitaComboClaveElector;
	private Integer valorBusqueda;
	private List<DTOObservadores> listaObservadores = new ArrayList<DTOObservadores>();
	private Boolean pintaDatosObservador;
	private Integer idObservador;
	private Date fechaNacimiento;
	private static final String ORA_ERROR_CODE_CHILD_RECORD_FOUND = "ORA-02292";
	private String terminacion;
	private boolean habilitaJustificacion;
	private String mensajeFoto = "La imagen se subió correctamente.";
	private boolean pintaMensajeFoto;
	private String rutaFotoDefault;
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private Pattern pattern = null;
	private Matcher matcher = null;
	private String claveVieja = "";

	
	/**
	 * Método Inicializar la vista del modulo de obserbadores
	 */
	public void init() {
		try{
		LOGGER.info("Entre al metodo init");
		this.usuario = mbAdmin.getDto().getUsuario();
		LOGGER.info("El estado o estado Seleccionado es ::  "  + ( usuario.getIdEstado() > 0 ? usuario.getIdEstado() :  
			usuario.getIdEstadoSeleccionado() ));
		LOGGER.info("El Distrito o distrito Seleccionado es :: " + ( usuario.getIdDistrito() > 0 ? usuario.getIdDistrito() :  
			usuario.getIdDistritoSeleccionado() ));
		this.regla1 = false;
		this.regla2 = false;
		this.regla3 = false;
		this.bloqueaSN = true;
		this.habilitaAgrupacion = false;
		this.tipoSolicitud = 0;
		this.parteClaveElector1 = "";
		this.parteClaveElector2 = "";
		this.parteClaveElector3 = "";
		this.habilitaComboNombre = false;
		this.habilitaComboClaveElector = false;
		this.habilitaRatifica = this.pintaRatifica(usuario);
		this.observadorCaptura = new DTOObservadores();
		this.observadorCaptura.setOrigenSolicitud(((Integer) Integer.parseInt("0")).shortValue());
		this.observadorCaptura.setEdad(null);
		this.observadorCaptura.setSeccion(null);
		this.observadorCaptura.setCorreoElectronico("");
		this.observadorCaptura.setDTOObservadoresPK(new DTOObservadoresPK());
		this.observadorCaptura.getDTOObservadoresPK().setIdDetalleProceso(usuario.getIdDetalleProceso().shortValue());
		this.observadorCaptura.getDTOObservadoresPK().setIdProcesoElectoral(usuario.getIdProcesoElectoral().shortValue());
		this.observadorCaptura.setSinNumero(new Short("0"));
		this.listaEscolaridad = obtenEscolaridad(usuario);
		this.listaEstados = obtenEdos(usuario);
		this.listaMunicipios = obtenMunicipios(this.observadorCaptura);
		this.listaAgrupaciones = obtenListaAgrupaciones(usuario);
		this.listaEvaluaciones = obtenListaEvaluaciones(usuario); 
		this.listaJustificaciones = obtenerJustificacion(usuario);
		this.listaObservadores = obtenerObservadores(usuario);
		this.bloqueaSinNumero();
		this.pintaDatosObservador = false;
		this.observadorConsulta = new DTOObservadores();
		this.observadorConsulta.setOrigenSolicitud(((Integer) Integer.parseInt("0")).shortValue());
		this.observadorConsulta.setEdad(null);
		this.observadorCaptura.setSinNumero(new Short("1"));
		this.observadorConsulta.setDTOObservadoresPK(new DTOObservadoresPK());
		this.fechaNacimiento = null;
		this.valorBusqueda = null;
		this.habilitaJustificacion = false;
		this.pintaMensajeFoto = false;
		this.rutaFotoDefault = this.obtenRutaFotoDefault(this.usuario);
		LOGGER.info("Saliendo del logger");
		}catch (Exception e) {
			LOGGER.error("Ups!! se genero un error en :::" , e);
		}
	}
	
	/**
	 * Método para inicializar la vista del modulo de obserbadores en el modifica
	 * cuando el modifica no es exitoso
	 */
	public void initModificaFallo(){
		this.valorBusqueda = null;
		this.habilitaComboNombre = false;
		this.habilitaComboClaveElector = false;
	}

	/**
	 * Método para obtener un observador dado su PK
	 * 
	 * @param tmpPk
	 *            la PK del observador
	 * @return DTOObservadores con los datos de la PK
	 */
	public DTOObservadores obtenObservador(DTOObservadoresPK tmpPk) {
		try {
			DTOObservadores observador = bsdObservadorInterface.consultaObservador(tmpPk);
			return observador;
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en ::", e);
			return null;
		}
	}

	/**
	 * Método que regresa el resultado de la operacion de guardar a un
	 * observador
	 * 
	 * @return un booleano con el resultado de la operacion
	 */
	public boolean guadarObservador() {

		if (!validacionesDatosRequeridos()) {
			return false;
		}
		try {
			this.complementaDatosObservador();
			Integer idObservador = bsdObservadorInterface.obtenIdObservador();
			this.observadorCaptura.getDTOObservadoresPK().setIdObservador(new Short("" + idObservador + ""));
			if (this.terminacion != null && this.terminacion.length() > 1) {
				LOGGER.info("La terminacion es distinta de nula");
				this.nombreArchivo = idObservador + "_" + this.usuario.getIdProcesoElectoral() + "_"
						+ this.usuario.getIdDetalleProceso() + "." + terminacion;
				this.observadorCaptura.setRutaFoto(this.nombreArchivo);
			}
			LOGGER.info("************************************");
			LOGGER.info(this.observadorCaptura.toString());
			LOGGER.info("************************************");
			bsdObservadorInterface.guarda(this.observadorCaptura);
			if (this.observadorCaptura.getRutaFoto() != null) {
				if (!this.observadorCaptura.getRutaFoto().equals("")) {
					LOGGER.info("Escribiendo foto en guardar");
					writeFile(fileItem, nombreArchivo);
				}
			}
			this.agregaMensaje(TipoMensaje.INFO_MENSAJE, "El registro se guardó correctamente.");
			return true;
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en ::", e);
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El registro no se pudo guardar.");
			return false;
		}
	}

	/**
	 * Método encargado de validar que los combos de listas que son requeridos
	 * si contengan algun valor valido para su insercion o actualizacion
	 */
	private boolean validacionesDatosRequeridos() {
	 if(this.observadorCaptura.getCorreoElectronico() != null &&!this.observadorCaptura.getCorreoElectronico().equals("")){
		 java.util.regex.Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(this.observadorCaptura.getCorreoElectronico());
		 
		if(matcher.matches()){
			 
		}else{
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El correo no es válido.");
			return false;
		}
		 }
	
		if (!this.observadorCaptura.getApellidoMaterno().equals("")
				|| !this.observadorCaptura.getApellidoPaterno().equals("")) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "Al menos uno de los dos apellidos es requerido.");
			return false;
		}
//		if (!this.observadorCaptura.getIdEscolaridad().equals(0)) {
//		} else {
//			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Nivel de estudios es requerido.");
//			return false;
//		}
		if (!this.observadorCaptura.getIdEstadoDomicilio().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Entidad Federativa es requerido.");
			return false;
		}
		if (!this.observadorCaptura.getIdMunicipio().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Delegación o Municipio es requerido.");
			return false;
		}
		if (!this.observadorCaptura.getIdEvaluacion().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Evaluación es requerido.");
			return false;
		}
		 this.observadorCaptura.setClaveElector(this.parteClaveElector1 + "" + this.parteClaveElector2 + ""+this.parteClaveElector3);
		if(this.claveElectorExiste(this.usuario, this.observadorCaptura.getClaveElector())){
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "La clave de elector ya se encuentra registrada.");
			return false;
		}
		
		 if(this.tipoSolicitud.equals(1)){
		 List<DTOAgrupaciones> listaAgrup = new ArrayList<DTOAgrupaciones>();
		 listaAgrup = this.obtenListaAgrupaciones(this.usuario);
		 boolean decide = false;
		 for (DTOAgrupaciones dtoAgrupaciones : listaAgrup) {
			 LOGGER.info(" LO que TRAE busqueda es ::: " + this.busqueda );
			if(dtoAgrupaciones.getNombreAgrupacion().equals(this.busqueda)){
				decide = true;
				break;
			}
		}
		 
		 if(decide){
			 
		 }else{
			 this.busqueda = null;
			 this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "Es necesario seleccionar una agrupación válida.");
			 return false; 
		 }
		 }else{
			 this.observadorCaptura.setIdAgupacion(null); 
		 }
		return true;
	}

	
	private boolean validacionesDatosRequeridosModifica() {
		 if(this.observadorCaptura.getCorreoElectronico() != null && !this.observadorCaptura.getCorreoElectronico().equals("")){
			 java.util.regex.Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(this.observadorCaptura.getCorreoElectronico());
			if(matcher.matches()){
			}else{
				this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El correo no es válido.");
				return false;
			}
			 }
	
		if (!this.observadorCaptura.getApellidoMaterno().equals("")
				|| !this.observadorCaptura.getApellidoPaterno().equals("")) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "Al menos uno de los dos apellidos es requerido.");
			return false;
		}
		if (!this.observadorCaptura.getIdEstadoDomicilio().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Entidad Federativa es requerido.");
			return false;
		}
		if (!this.observadorCaptura.getIdMunicipio().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Delegación o Municipio es requerido.");
			return false;
		}
		if (!this.observadorCaptura.getIdEvaluacion().equals(new Short("0"))) {
		} else {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El campo Evaluación es requerido.");
			return false;
		}
		 this.observadorCaptura.setClaveElector(this.parteClaveElector1 + "" + this.parteClaveElector2 + ""+this.parteClaveElector3);
		 if( !this.observadorCaptura.getClaveElector().equals(claveVieja)){
			if(this.claveElectorExiste(this.usuario, this.observadorCaptura.getClaveElector())){
				this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "La clave de elector ya se encuentra registrada.");
				return false;
			}
		 }
		 
		 
		 if(this.tipoSolicitud.equals(1)){
		 List<DTOAgrupaciones> listaAgrup = new ArrayList<DTOAgrupaciones>();
		 listaAgrup = this.obtenListaAgrupaciones(this.usuario);
		 boolean decide = false;
		 for (DTOAgrupaciones dtoAgrupaciones : listaAgrup) {
			 LOGGER.info(" LO que TRAE busqueda es ::: " + this.busqueda );
			if(dtoAgrupaciones.getNombreAgrupacion().equals(this.busqueda)){
				decide = true;
				break;
			}
		}
		 
		 if(decide){
			 
		 }else{
			 this.busqueda = null;
			 this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "Es necesario seleccionar una agrupación válida.");
			 return false; 
		 }
		 
		 }else{
			 this.observadorCaptura.setIdAgupacion(null);
			  
		 }
		 
		return true;
	}
	
	
	/**
	 * Método para acompletar los datos del observador que se intentara guardar
	 */
	
	private void complementaDatosObservador() {	
		String claveCompleta = this.parteClaveElector1 + this.parteClaveElector2 + this.parteClaveElector3;
		DTOObservadoresPK tmpPK = new DTOObservadoresPK(this.usuario.getIdProcesoElectoral().shortValue(),
				this.usuario.getIdDetalleProceso().shortValue(), ((Integer) Integer.parseInt("0")).shortValue());
		this.observadorCaptura.setIdEstado(this.usuario.getIdEstado() > 0 ? this.usuario.getIdEstado().shortValue()
				: this.usuario.getIdEstadoSeleccionado().shortValue());
		this.observadorCaptura.setIdDistrito(this.usuario.getIdDistrito() > 0  
				? this.usuario.getIdDistrito().shortValue() : this.usuario.getIdDistritoSeleccionado().shortValue());
		this.observadorCaptura.setDTOObservadoresPK(tmpPK);
		this.observadorCaptura.setClaveElector(claveCompleta);
		
		if(this.observadorCaptura.getIdEscolaridad() != null && this.observadorCaptura.getIdEscolaridad().equals(0)) {
			this.observadorCaptura.setIdEscolaridad(null);
		}
		
		if (!(this.observadorCaptura.getApellidoMaterno() != null)) {
			this.observadorCaptura.setApellidoMaterno("");
		}
		if (!(this.observadorCaptura.getApellidoPaterno() != null)) {
			this.observadorCaptura.setApellidoPaterno("");
		}
		if (this.observadorCaptura.getSinNumero() != null && !this.observadorCaptura.getSinNumero().equals(new Short("1"))) {
				this.observadorCaptura.setSinNumero(((Integer) Integer.parseInt("0")).shortValue());
			} else {
				this.observadorCaptura.setSinNumero(((Integer) Integer.parseInt("1")).shortValue());
			}
		
		LOGGER.info(" this.observadorCaptura.getSinNumero() = ::: " + this.observadorCaptura.getSinNumero());
	
	if (this.observadorCaptura.getIdCurso() != null && this.observadorCaptura.getIdCurso().equals(0)) {
				this.observadorCaptura.setIdCurso(null);
		} 
 
		if(this.observadorCaptura.getSeccion() != null && this.observadorCaptura.getSeccion().equals(new Short("0"))) {
			this.observadorCaptura.setSeccion(null);
		}
	}

	/**
	 * Método generar la fecha de nacimiento a partir de la clave de elector
	 */
	public void generaDatosComplementario() {
		String fechaCadena = this.generaCadenaFecha(this.parteClaveElector2);
		observadorCaptura.setFechaNacimiento(fechaCadena);
		try {
			this.fechaNacimiento = formatoDelTexto.parse(fechaCadena);  
			observadorCaptura.setEdad(this.generaEdad(fechaCadena));
		} catch (Exception e) {
			LOGGER.info("Ups se genero un error al intentar asiganar la fecha de nacimiento o edad ", e);
		}
		Character sexo = this.generaSexo(this.parteClaveElector3);
		observadorCaptura.setSexo(sexo);
		String claveCompleta = this.parteClaveElector1 + this.parteClaveElector2 + this.parteClaveElector3;
		this.observadorCaptura.setClaveElector(claveCompleta);
		if( !this.observadorCaptura.getClaveElector().equals(this.claveVieja)){
		if (this.claveElectorExiste(this.usuario, claveCompleta)) {
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "La clave de elector ya existe, favor de verificarla.");
		} else {
			this.observadorCaptura.setClaveElector(claveCompleta);
		}
		}
	}
	
	/**
	 * Método que regresa el resultado de la operacion de modificar a un
	 * observador
	 * 
	 * @return un booleano con el resultado de la operacion
	 */
	public boolean modifcarObservador() {
		if (!validacionesDatosRequeridosModifica()) {
			return false;
		}
		try {
			String claveCompleta = this.parteClaveElector1 + this.parteClaveElector2 + this.parteClaveElector3;
			this.observadorCaptura.setClaveElector(claveCompleta);
			if (!(this.observadorCaptura.getApellidoMaterno() != null)) {
				this.observadorCaptura.setApellidoMaterno("");
			}
			if (!(this.observadorCaptura.getApellidoPaterno() != null)) {
				this.observadorCaptura.setApellidoPaterno("");
			}
			if (this.observadorCaptura.getIdCurso() != null && this.observadorCaptura.getIdCurso().equals(0)) {
					this.observadorCaptura.setIdCurso(null);
			}
			if(this.observadorCaptura.getSeccion() != null && this.observadorCaptura.getSeccion().equals(new Short("0"))) {
				this.observadorCaptura.setSeccion(null);
			}
			if (this.observadorCaptura.getNumeroExterior() != null
					&& !this.observadorCaptura.getNumeroExterior().equals("")) {
				this.observadorCaptura.setSinNumero(new Short("0"));
			}
			if (this.observadorCaptura.getNumeroInterior() != null
					&& !this.observadorCaptura.getNumeroInterior().equals("")) {
				this.observadorCaptura.setSinNumero(new Short("0"));
			}
			if (this.tipoSolicitud.equals(0)) {
				this.observadorCaptura.setIdAgupacion(null);
			}
			if(this.observadorCaptura.getIdEscolaridad() != null){
				if(this.observadorCaptura.getIdEscolaridad().equals(0)){
					this.observadorCaptura.setIdEscolaridad(null);
				}
			}
			if (this.terminacion != null && this.terminacion.length() > 1) {
				LOGGER.info("La terminacion es distinta de nula");
				this.nombreArchivo = this.observadorCaptura.getDTOObservadoresPK().getIdObservador() + "_"
						+ this.usuario.getIdProcesoElectoral() + "_" + this.usuario.getIdDetalleProceso() + "."
						+ terminacion;
				this.observadorCaptura.setRutaFoto(this.nombreArchivo);
			} else if (this.observadorCaptura.getRutaFoto() != null) {
				if (!this.observadorCaptura.getRutaFoto().equals("")) {
					LOGGER.info("Al Modificar la ruta es diferente de vacio");
					terminacion = this.observadorCaptura.getRutaFoto()
							.substring(this.observadorCaptura.getRutaFoto().lastIndexOf(".") + 1);
					this.nombreArchivo = this.observadorCaptura.getDTOObservadoresPK().getIdObservador() + "_"
							+ this.usuario.getIdProcesoElectoral() + "_" + this.usuario.getIdDetalleProceso() + "."
							+ terminacion;
					this.observadorCaptura.setRutaFoto(this.nombreArchivo);
				}
			}
			LOGGER.info("****************************************");
			LOGGER.info(this.observadorCaptura.toString());
			LOGGER.info("****************************************");
			bsdObservadorInterface.modifica(this.observadorCaptura);
			if (this.observadorCaptura.getRutaFoto() != null) {
				if (!this.observadorCaptura.getRutaFoto().equals("")) {
					writeFile(fileItem, nombreArchivo);
				}
			}
			this.agregaMensaje(TipoMensaje.INFO_MENSAJE, "El registro se modificó correctamente.");
			return true;
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en ::", e);
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El registro no se pudo modificar.");
			return false;
		}
	}

	/**
	 * Método que regresa el resultado de la operacion de eliminar a un
	 * observador
	 * 
	 * @return un booleano con el resultado de la operacion
	 */
	public boolean eliminaObservador() {
		try {
			bsdObservadorInterface.elimina(this.observadorConsulta);
			this.agregaMensaje(TipoMensaje.INFO_MENSAJE, "El registro se eliminó correctamente.");
			return true;
		} catch (DataIntegrityViolationException e) {
			if (e.getRootCause().getMessage().contains(ORA_ERROR_CODE_CHILD_RECORD_FOUND)) {
				this.agregaMensaje(TipoMensaje.ERROR_MENSAJE,
						"No se puede eliminar el Observador/a porque existen un gafete asociado a esta/e.");
			}
			return false;
		} catch (Exception e) {
			LOGGER.error("Ups! se genero un error en ::", e);
			this.agregaMensaje(TipoMensaje.ERROR_MENSAJE, "El registro no se pudo eliminar");
			return false;
		}
	}

	/**
	 * 
	 * Métodos auxiliares para el manejo de los elementos de los componentes de
	 * las vistas
	 *
	 * @author Carlos Augusto Escalona Navarrp
	 * @since 12/07/2017
	 */
	public void escondeNombreAgrupacion() {
		LOGGER.info("Entre a escondeNombreAgrupacion ");
		this.listaEvaluaciones = this.obtenListaEvaluaciones(this.usuario);  
		this.observadorCaptura.setIdEvaluacion(null); 
		this.obtenReglas();
		this.busqueda = "";
		if (this.tipoSolicitud > 0) {
			this.habilitaAgrupacion = true;
			LOGGER.info("true");
		} else {
			this.habilitaAgrupacion = false;
			LOGGER.info("false");
		}
	}
	
	public void escondeNombreAgrupacionModifica() {
		LOGGER.info("Entre a escondeNombreAgrupacionModifica ");
		 
		if (this.tipoSolicitud > 0) {
			this.habilitaAgrupacion = true;
			LOGGER.info("true");
		} else {
			this.habilitaAgrupacion = false;
			LOGGER.info("false");
		}
	}
	
	/*
	 * Método para bloquer el campo de sin Numero
	 */
	
	public void bloqueaSinNumero() {
			if (this.observadorCaptura.getNumeroExterior() != null
				&& !this.observadorCaptura.getNumeroExterior().equals("")) {
			this.bloqueaSN = true;
			this.observadorCaptura.setSinNumero(new Short("0"));
			LOGGER.info("True");
			return;
		} else {
			this.bloqueaSN = false;
			this.observadorCaptura.setSinNumero(new Short("1"));
			LOGGER.info("False");
		}
		if (this.observadorCaptura.getNumeroInterior() != null
				&& !this.observadorCaptura.getNumeroInterior().equals("")) {
			this.bloqueaSN = true;
			this.observadorCaptura.setSinNumero(new Short("0"));
			LOGGER.info("True");
			return;
		} else {
			this.bloqueaSN = false;
			this.observadorCaptura.setSinNumero(new Short("1"));
			LOGGER.info("False");
		}
	}
	
	/*
	 * Método par aobtener reglas asociadas a una evaluacion, esto ayuda a la generacion
	 * de combos que tenemos en la vista, que estan asociados a una regla en especifico
	 */
	public void obtenReglas() {
		this.regla1 = false;
		this.regla2 = false;
		this.regla3 = false;
		if (this.observadorCaptura.getIdEvaluacion() != null) {
			this.listaReglas = obtenReglasE(this.observadorCaptura);
			Boolean bandera = false;
			DTOReglasEvalucaion reglaMetodo = new DTOReglasEvalucaion();
			for (DTOReglasEvalucaion dtoR : listaReglas) {
				if (dtoR.getDTOReglasEvalucaionPK().getIdRegla().equals(new Integer(1))) {
					bandera = true;
					reglaMetodo = dtoR;
					break;
				}
			}
			if (this.listaEvaluaciones != null && bandera) {
				for (DTOCEvaluacion dto : listaEvaluaciones) {
					if (this.observadorCaptura.getIdEvaluacion().
							equals(new Short("" + dto.getDTOCEvaluacionPK().getIdEvaluacion() + ""))) {
						if(this.observadorCaptura.getIdAgupacion() != null ){
						reglaMetodo.setIdAgrupacionCurso(new Integer (this.observadorCaptura.getIdAgupacion()));
						}else{
							reglaMetodo.setIdAgrupacionCurso(null);
						}
						this.listaCursos = obtenListaCursos(this.usuario, reglaMetodo);
						break; 
					}
				}
			} else {
				this.listaCursos = new ArrayList<DTOCursos>();
			}
			if (!listaReglas.isEmpty()) {
				for (DTOReglasEvalucaion dto : listaReglas) {
					if (dto.getDTOReglasEvalucaionPK().getIdRegla().equals(1)) {
						this.regla1 = true;
					} else if (dto.getDTOReglasEvalucaionPK().getIdRegla().equals(2)) {
						this.regla2 = true;
					} else if (dto.getDTOReglasEvalucaionPK().getIdRegla().equals(3)) {
						this.regla3 = true;
					} else {
						LOGGER.info("No se encuentra esta regla registrada en la vista :: "
								+ dto.getDTOReglasEvalucaionPK().getIdRegla());
					}
				}
				LOGGER.info("Segunda parte");
				if (this.listaEvaluaciones != null) {	 
					for (DTOCEvaluacion dto : listaEvaluaciones) {
						if (this.observadorCaptura.getIdEvaluacion()
								.equals(new Short("" + dto.getDTOCEvaluacionPK().getIdEvaluacion() + ""))) {
							this.listaJustificaciones = this.obtenerJustificacion(this.usuario);
							LOGGER.info(" el id_de la evaluacion   " + dto.getDTOCEvaluacionPK().getIdEvaluacion() );
							LOGGER.info(" el id_de la evaluacion DEL OBSERVADOR  " + this.observadorCaptura.getIdEvaluacion() );
							LOGGER.info("La longitus de la lista es ::: " + listaEvaluaciones.size());
							LOGGER.info("EL ID DEL ULTIMO ELEMENTO ::: " + listaEvaluaciones.get(listaEvaluaciones.size()-1).getDTOCEvaluacionPK().getIdEvaluacion());
							 
							
							
							if(listaEvaluaciones.size() == 
									listaEvaluaciones.get(listaEvaluaciones.size()-1).getDTOCEvaluacionPK().getIdEvaluacion()){
							//
							if (dto.getDTOCEvaluacionPK().getIdEvaluacion().equals(listaEvaluaciones.size() -1)) {
								LOGGER.info("Encontre al No acreditado");
								this.listaJustificaciones = this.obtenJustificacionNA();
								this.observadorCaptura.setIdJustificacion(new Short("0"));
								this.observadorCaptura.setIdCurso(null);
								this.habilitaJustificacion = true;
							} else if (dto.getDTOCEvaluacionPK().getIdEvaluacion().equals(listaEvaluaciones.size()  )) {
								LOGGER.info("Encontre al PENDIENTE");
								this.observadorCaptura.setIdJustificacion(null);
								this.observadorCaptura.setIdCurso(null);
								this.observadorCaptura.setFechaSesion(null);
							} else {
								this.habilitaJustificacion = false;
								if (this.observadorCaptura.getIdJustificacion() != null) {

								} else {
									this.observadorCaptura.setIdJustificacion(new Short("-1"));
								}
							}
							///
							} else{
								
								//
								if (dto.getDTOCEvaluacionPK().getIdEvaluacion().equals(listaEvaluaciones.size() )) {
									LOGGER.info("Encontre al No acreditado del else ");
									this.listaJustificaciones = this.obtenJustificacionNA();
									this.observadorCaptura.setIdJustificacion(new Short("0"));
									this.observadorCaptura.setIdCurso(null);
									this.habilitaJustificacion = true;
								} else if (dto.getDTOCEvaluacionPK().getIdEvaluacion().equals(listaEvaluaciones.size() +1 )) {
									LOGGER.info("Encontre al PENDIENTE del else ");
									this.observadorCaptura.setIdJustificacion(null);
									this.observadorCaptura.setIdCurso(null);
									this.observadorCaptura.setFechaSesion(null);
								} else {
									this.habilitaJustificacion = false;
									if (this.observadorCaptura.getIdJustificacion() != null) {

									} else {
										this.observadorCaptura.setIdJustificacion(new Short("-1"));
									}
								}
								///
								
								
							}
							

						}
					}
				}
			} else {
				LOGGER.info("Encontre al PENDIENTE");
				this.observadorCaptura.setIdJustificacion(null);
				this.observadorCaptura.setIdCurso(null);
				this.observadorCaptura.setFechaSesion(null);
			}
		} 
	}
	
	/**
	 * Métodos complementarios para generar datos de la vista
	 */

	private String obtenRutaFotoDefault(DTOUsuarioLogin user) {
		return bsdObservadorInterface.obtenRutaFotoDefault(user);
	}
	
	private List<DTOCJustificaciones> obtenJustificacionNA() {
		List<DTOCJustificaciones> resultado = bsdObservadorInterface.obtenJustificacionNA(this.usuario);
		DTOCJustificacionesPK tmpPK = new DTOCJustificacionesPK();
		tmpPK.setIdJustificacion(-1);
		DTOCJustificaciones tmp = new DTOCJustificaciones(tmpPK, "Selecciona");
		resultado.add(0, tmp);
		return resultado;
	}

	public boolean isHabilitaJustificacion() {
		return habilitaJustificacion;
	}

	public void setHabilitaJustificacion(boolean habilitaJustificacion) {
		this.habilitaJustificacion = habilitaJustificacion;
	}

	public void obtenMunicipios() {
		this.observadorCaptura.setIdMunicipio(null);
		this.listaMunicipios = obtenMunicipios(this.observadorCaptura);
	}

	private void obtenMunicipiosSinBorrar() {
		this.listaMunicipios = bsdObservadorInterface.obtenMunicipios(this.observadorCaptura);
	}



	public List<String> filtraAgrupaciones(String q) {
		List<String> lista = new ArrayList<String>();
		for (DTOAgrupaciones ag : this.listaAgrupaciones) {
			if (ag.getNombreAgrupacion().toLowerCase().contains(q.toLowerCase())) {
				lista.add(ag.getNombreAgrupacion());
			}
		}
		LOGGER.info("SALIENDO DE FILTRA");
		return lista;
		 
	}
	
	 
	/**
	 * Método que valida si una clave de electos existe en UN PROCESO Y UN DETALLE
	 * @param user
	 * @param claveCompleta
	 * @return
	 */
	private boolean claveElectorExiste(DTOUsuarioLogin user, String claveCompleta) {
		return bsdObservadorInterface.claveElectorExiste(user, claveCompleta);
	}

	private Short generaEdad(String fechaCadena) {
		return bsdObservadorInterface.generaEdad(fechaCadena);
	}

	private Character generaSexo(String parteClaveElector32) {
		char buf1[] = new char[1];
		parteClaveElector32.getChars(2, 3, buf1, 0);
		return (Character) buf1[0];
	}

	private String generaCadenaFecha(String fecha) {
		String cadenaConcatenada = "";
		String ponerAnio = fecha.charAt(0) + "" + fecha.charAt(1);
		Integer anio = Integer.parseInt(ponerAnio);
		if (anio > 10) {
			cadenaConcatenada = "19";
		} else {
			cadenaConcatenada = "20";
		}
			String resultado = fecha.charAt(4) + "" + fecha.charAt(5) + "/" + fecha.charAt(2) + "" + fecha.charAt(3) + "/"
				+ cadenaConcatenada + "" + fecha.charAt(0) + "" + fecha.charAt(1);
		LOGGER.info("La cadena construida es ::" + resultado);
		return resultado;
	}

	private Boolean pintaRatifica(DTOUsuarioLogin user) {
		return bsdObservadorInterface.habilitaRatifica(user);
	}


	public List<DTOObservadores> filtraObservadoresNombres(String q) {
		List<DTOObservadores> lista = new ArrayList<DTOObservadores>();
		if (!this.listaObservadores.isEmpty()) {

			for (DTOObservadores ag : this.listaObservadores) {
				try {
					if (ag.getNombre().toLowerCase().contains(q.toLowerCase())
							|| ag.getApellidoPaterno().toLowerCase().contains(q.toLowerCase())
							|| ag.getApellidoMaterno().toLowerCase().contains(q.toLowerCase())) {
						lista.add(ag);
						LOGGER.info(ag.toString());
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return lista;
	}
	

	public List<DTOObservadores> filtraObservadoresClaves(String q) {
		List<DTOObservadores> lista = new ArrayList<DTOObservadores>();
		if (!this.listaObservadores.isEmpty()) {
			for (DTOObservadores ag : this.listaObservadores) {
				if (ag.getClaveElector().toLowerCase().contains(q.toLowerCase())) {
					lista.add(ag);
					LOGGER.info(ag.toString());
				}
			}
		}
		return lista;
	}

	public void muestraObservadoresNombres() {
		try {
			DTOObservadoresPK tmpPk = new DTOObservadoresPK(usuario.getIdProcesoElectoral().shortValue(),
					usuario.getIdDetalleProceso().shortValue(), getIdObservador().shortValue());
			this.observadorConsulta = this.obtenObservador(tmpPk);
			this.pintaDatosObservador = true;
			this.observadorCaptura = this.observadorConsulta;
			this.claveVieja = this.observadorCaptura.getClaveElector();
			LOGGER.info("Se actualizao la clave :::" + this.claveVieja);
			this.inicializaObservadorModifica();
		} catch (Exception e) {
			LOGGER.error(" Ups ! se genero un error en muestraObservadoresNombres ::  ", e);
		}
	}

	private void inicializaObservadorModifica() {
		this.separaClaveElector(this.observadorCaptura.getClaveElector());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.fechaNacimiento = formatter.parse(this.observadorCaptura.getFechaNacimiento());
		} catch (ParseException e) {
			LOGGER.error("Ups! se genero un erroe en :: inicializaObservadorModifica", e);
		}
		this.bloqueaSinNumero();
		this.tipoSolicitud = this.observadorCaptura.getIdAgupacion() != null ? 1 : 0;
		if (this.observadorCaptura.getIdAgupacion() != null) {
			this.busqueda = this.obtenNombreAgrupacion(this.observadorCaptura.getIdAgupacion());
			this.listaEvaluaciones = this.obtenListaEvaluaciones(this.usuario); 
		}
		this.escondeNombreAgrupacionModifica();
		this.muestraAgrupacionModifica();
		this.obtenReglas();
		this.obtenMunicipiosSinBorrar();
	}

	private String obtenNombreAgrupacion(Short idAgupacion) {
		try {
			this.listaAgrupaciones = this.obtenListaAgrupaciones(this.usuario);
			for (DTOAgrupaciones dto : this.listaAgrupaciones) {
				if (dto.getPk().getIdAgrupacion().equals(new Integer(idAgupacion + ""))) {
					return dto.getNombreAgrupacion();
				}
			}
			return "";
		} catch (Exception e) {
			LOGGER.error(" Ups ! se genero un error en muestraAgrupacion ::  ", e);
			return "";
		}
	}

	private void separaClaveElector(String claveElector) {
		this.parteClaveElector1 = claveElector.substring(0, 6);
		this.parteClaveElector2 = claveElector.substring(6, 12);
		this.parteClaveElector3 = claveElector.substring(12, 18);
	}

	public void muestraAgrupacionModifica() {
		try {
			this.listaAgrupaciones = this.obtenListaAgrupaciones(this.usuario);
			for (DTOAgrupaciones dto : this.listaAgrupaciones) {
				if (dto.getNombreAgrupacion().equals(busqueda)) {
					this.observadorCaptura.setIdAgupacion(dto.getPk().getIdAgrupacion().shortValue());
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error(" Ups ! se genero un error en muestraAgrupacion ::  ", e);
		}
	}

	
	public void muestraAgrupacion() {
		try {
			this.listaAgrupaciones = this.obtenListaAgrupaciones(this.usuario);
			for (DTOAgrupaciones dto : this.listaAgrupaciones) {
				if (dto.getNombreAgrupacion().equals(busqueda)) {
					LOGGER.info("muestraAgrupacion ::  IF :: equals");
					this.observadorCaptura.setIdAgupacion(dto.getPk().getIdAgrupacion().shortValue());
					break;
				}
			}
			this.listaEvaluaciones = this.obtenListaEvaluaciones(this.usuario);  
			this.observadorCaptura.setIdEvaluacion(null);
			this.observadorCaptura.setIdCurso(null);
			this.observadorCaptura.setIdJustificacion(null);
			this.observadorCaptura.setFechaSesion(null);
			this.obtenReglas();
		} catch (Exception e) {
			LOGGER.error(" Ups ! se genero un error en muestraAgrupacion ::  ", e);
		}
	}

	public void pintaCampoBusqueda() {
		if (valorBusqueda.equals(0)) {
			habilitaComboNombre = true;
			habilitaComboClaveElector = false;
		} else if (valorBusqueda.equals(1)) {
			habilitaComboNombre = false;
			habilitaComboClaveElector = true;
		} else {
			habilitaComboNombre = false;
			habilitaComboClaveElector = false;
		}
		this.pintaDatosObservador = false;
		this.observadorConsulta = null;
		this.idObservador = null;

	}

	/**
	 * Métodos privados de la clase para obtener los catalogos que se mostraran
	 * en la vista
	 * 
	 */
	private List<DTOObservadores> obtenerObservadores(DTOUsuarioLogin user) {
		return bsdObservadorInterface.obtenerObservadores(user);
	}

	private List<DTOCJustificaciones> obtenerJustificacion(DTOUsuarioLogin user) {
		List<DTOCJustificaciones> resultado = bsdObservadorInterface.obtenerJustificacion(user);
		DTOCJustificacionesPK tmpPK = new DTOCJustificacionesPK();
		tmpPK.setIdJustificacion(-1);
		DTOCJustificaciones tmp = new DTOCJustificaciones(tmpPK, "Selecciona");
		resultado.add(0, tmp);
		return resultado;
	}

	private List<DTOCursos> obtenListaCursos(DTOUsuarioLogin user, DTOReglasEvalucaion evaluacion) {
		List<DTOCursos> resultado = bsdObservadorInterface.obtenListaCursos(user, evaluacion);
		if (resultado != null) {

		} else {
			resultado = new ArrayList<DTOCursos>();
		}
		DTOCursosPK tmpPK = new DTOCursosPK();
		tmpPK.setIdCurso(0);
		DTOCursos tmp = new DTOCursos(tmpPK);
		tmp.setEtiqueta("Selecciona");
		resultado.add(0, tmp);
		return resultado;
	}

	private List<DTOCEvaluacion> obtenListaEvaluaciones(DTOUsuarioLogin user) {
		List<DTOCEvaluacion> resultado = bsdObservadorInterface.obtenListaEvaluaciones(user);
		if(this.tipoSolicitud.equals(1)){
			if(this.observadorCaptura.getIdAgupacion() != null){
				
			}else{
				this.observadorCaptura.setIdAgupacion(new Short ("0"));
			}
			
			return resultado;
		}else { // tipo solicitud es =
			DTOCEvaluacion tmp = new DTOCEvaluacion();
			for (DTOCEvaluacion dtocEvaluacion : resultado) {
				if(dtocEvaluacion.getDTOCEvaluacionPK().getIdEvaluacion().equals(3)){ // Aqui esta en duro 
					tmp = dtocEvaluacion;
					break;
				}
			}
			LOGGER.info("El resultado de eliminar el elemento es ::" + resultado.remove(tmp));
			return resultado;
		}	
	}

	private List<DTOAgrupaciones> obtenListaAgrupaciones(DTOUsuarioLogin user) {
		return bsdObservadorInterface.obtenListaAgrupaciones(user);
	}

	private List<DTOMunicipio> obtenMunicipios(DTOObservadores observador) {
		List<DTOMunicipio> resultado = new ArrayList<DTOMunicipio>();
		if (observador.getIdEstadoDomicilio() != null) {
			resultado = bsdObservadorInterface.obtenMunicipios(observador);
		}
		this.observadorCaptura.setIdMunicipio(null); 
		return resultado;
	}

	private List<DTOEstado> obtenEdos(DTOUsuarioLogin user) {
		List<DTOEstado> resultado = bsdObservadorInterface.obtenEdos(user);
		return resultado;

	}

	private List<DTOCEscolaridades> obtenEscolaridad(DTOUsuarioLogin user) {
		List<DTOCEscolaridades> resultado = bsdObservadorInterface.obtenEscolaridad(user);
		return resultado;
	}

	private List<DTOReglasEvalucaion> obtenReglasE(DTOObservadores obs) {
		return bsdObservadorInterface.obtenReglasE(obs);
	}

	public void subirArchivoListado(FileUploadEvent event) {
		try {
			LOGGER.info("Subiendo archivo");
			this.fileItem = obtenerNombreArchivoTemporalPrime(event);
			this.terminacion = "";
			String nombreArchivoT = event.getFile().getFileName();
			this.terminacion = nombreArchivoT.substring(nombreArchivoT.lastIndexOf(".") + 1);
			this.pintaMensajeFoto = true;
		} catch (Exception e) {
			LOGGER.error("Error al subir el archivo", e);
			agregaMensajeNotificacion(e.getMessage(), FacesMessage.SEVERITY_WARN);
			RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
		}
	}

	private void writeFile(DiskFileItem uploadedFile, String name) throws Exception {
		try {
			File file = new File(rutaGluster + "/observadoresINE/fotos/" + name);
			if (file.createNewFile()) {
				try {
					uploadedFile.write(file);
				} catch (Exception e) {
					LOGGER.error("Ups! no seleccionaron una imagen a que cargar", e);
				}
				return;
			} else {
				if (uploadedFile != null) {
					Path TO = Paths.get(file.getAbsolutePath());
					CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
					Files.copy(uploadedFile.getInputStream(), TO, options);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error al escribir archivo en gluster.", e);
		}
	}

	private DiskFileItem obtenerNombreArchivoTemporalPrime(FileUploadEvent fileUploadEvent) {
		String idCliente = fileUploadEvent.getComponent().getClientId();
		MultipartRequest httpServletRequest = (MultipartRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return (DiskFileItem) httpServletRequest.getFileItem(idCliente);
	}

	/**
	 * Métodos getter y setter de la clases
	 * 
	 * 
	 */

	public DTOObservadores getObservadorCaptura() {
		return observadorCaptura;
	}

	public void setObservadorCaptura(DTOObservadores observadorCaptura) {
		this.observadorCaptura = observadorCaptura;
	}

	public DTOObservadores getObservadorConsulta() {
		return observadorConsulta;
	}

	public void setObservadorConsulta(DTOObservadores observadorConsulta) {
		this.observadorConsulta = observadorConsulta;
	}

	public List<DTOCEscolaridades> getListaEscolaridad() {
		return listaEscolaridad;
	}

	public void setListaEscolaridad(List<DTOCEscolaridades> listaEscolaridad) {
		this.listaEscolaridad = listaEscolaridad;
	}

	public List<DTOEstado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<DTOEstado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<DTOMunicipio> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<DTOMunicipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public List<DTOAgrupaciones> getListaAgrupaciones() {
		return listaAgrupaciones;
	}

	public void setListaAgrupaciones(List<DTOAgrupaciones> listaAgrupaciones) {
		this.listaAgrupaciones = listaAgrupaciones;
	}

	public List<DTOCursos> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<DTOCursos> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<DTOCJustificaciones> getListaJustificaciones() {
		return listaJustificaciones;
	}

	public void setListaJustificaciones(List<DTOCJustificaciones> listaJustificaciones) {
		this.listaJustificaciones = listaJustificaciones;
	}

	public List<DTOCEvaluacion> getListaEvaluaciones() {
		return listaEvaluaciones;
	}

	public void setListaEvaluaciones(List<DTOCEvaluacion> listaEvaluaciones) {
		this.listaEvaluaciones = listaEvaluaciones;
	}

	public String getParteClaveElector1() {
		return parteClaveElector1;
	}

	public void setParteClaveElector1(String parteClaveElector1) {
		this.parteClaveElector1 = parteClaveElector1;
	}

	public String getParteClaveElector2() {
		return parteClaveElector2;
	}

	public void setParteClaveElector2(String parteClaveElector2) {
		this.parteClaveElector2 = parteClaveElector2;
	}

	public String getParteClaveElector3() {
		return parteClaveElector3;
	}

	public void setParteClaveElector3(String parteClaveElector3) {
		this.parteClaveElector3 = parteClaveElector3;
	}

	public Boolean getRegla1() {
		return regla1;
	}

	public void setRegla1(Boolean regla1) {
		this.regla1 = regla1;
	}

	public Boolean getRegla2() {
		return regla2;
	}

	public void setRegla2(Boolean regla2) {
		this.regla2 = regla2;
	}

	public Boolean getRegla3() {
		return regla3;
	}

	public void setRegla3(Boolean regla3) {
		this.regla3 = regla3;
	}

	public List<DTOReglasEvalucaion> getListaReglas() {
		return listaReglas;
	}

	public void setListaReglas(List<DTOReglasEvalucaion> listaReglas) {
		this.listaReglas = listaReglas;
	}

	public Boolean getBloqueaSN() {
		return bloqueaSN;
	}

	public void setBloqueaSN(Boolean bloqueaSN) {
		this.bloqueaSN = bloqueaSN;
	}

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public Boolean getHabilitaAgrupacion() {
		return habilitaAgrupacion;
	}

	public void setHabilitaAgrupacion(Boolean habilitaAgrupacion) {
		this.habilitaAgrupacion = habilitaAgrupacion;
	}

	public Boolean getHabilitaRatifica() {
		return habilitaRatifica;
	}

	public void setHabilitaRatifica(Boolean habilitaRatifica) {
		this.habilitaRatifica = habilitaRatifica;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public Boolean getHabilitaComboNombre() {
		return habilitaComboNombre;
	}

	public void setHabilitaComboNombre(Boolean habilitaComboNombre) {
		this.habilitaComboNombre = habilitaComboNombre;
	}

	public Boolean getHabilitaComboClaveElector() {
		return habilitaComboClaveElector;
	}

	public void setHabilitaComboClaveElector(Boolean habilitaComboClaveElector) {
		this.habilitaComboClaveElector = habilitaComboClaveElector;
	}

	public List<DTOObservadores> getListaObservadores() {
		return listaObservadores;
	}

	public void setListaObservadores(List<DTOObservadores> listaObservadores) {
		this.listaObservadores = listaObservadores;
	}

	public Integer getIdObservador() {
		return idObservador;
	}

	public void setIdObservador(Integer idObservador) {
		this.idObservador = idObservador;
	}

	public Boolean getPintaDatosObservador() {
		return pintaDatosObservador;
	}

	public void setPintaDatosObservador(Boolean pintaDatosObservador) {
		this.pintaDatosObservador = pintaDatosObservador;
	}

	public Integer getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(Integer valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getMensajeFoto() {
		return mensajeFoto;
	}

	public void setMensajeFoto(String mensajeFoto) {
		this.mensajeFoto = mensajeFoto;
	}

	public boolean isPintaMensajeFoto() {
		return pintaMensajeFoto;
	}

	public void setPintaMensajeFoto(boolean pintaMensajeFoto) {
		this.pintaMensajeFoto = pintaMensajeFoto;
	}

	public String getRutaFotoDefault() {
		return rutaFotoDefault;
	}

	public void setRutaFotoDefault(String rutaFotoDefault) {
		this.rutaFotoDefault = rutaFotoDefault;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

 
}