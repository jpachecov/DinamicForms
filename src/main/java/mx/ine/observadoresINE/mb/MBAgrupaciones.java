package mx.ine.observadoresINE.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.mb.MBAdministradorSistema;
import mx.ine.observadoresINE.bsd.BSDAgrupacionesInterface;
import mx.ine.observadoresINE.bsd.BSDServiciosGeneralesInterface;
import mx.ine.observadoresINE.dto.DTODatosAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK;
import mx.ine.observadoresINE.mb.MBGeneric;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

/**
 * 
 * Clase controlador para el módulo de Agrupaciones
 * 
 * @since 27/06/2017
 * @author Jean Pierre Pacheco Avila
 * @version 1.0
 *
 */
public class MBAgrupaciones extends MBGeneric implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 3925976597160095779L;

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(MBAgrupaciones.class);
	
	/**
	 * Código de error para violacion de integridad
	 */
	private static final String ORA_ERROR_CODE_CHILD_RECORD_FOUND = "ORA-02292";

	/**
	 * Objeto bsd para los servicios generales usados en el módulo
	 */
	@Autowired
	@Qualifier("bsdServiciosGenerales")
	private transient BSDServiciosGeneralesInterface bsdServicios;

	/**
	 * Objeto BSD para los servicios exclusivos del módulo
	 */
	@Autowired
	@Qualifier("bsdAgrupaciones")
	private transient BSDAgrupacionesInterface bsdAgrupaciones;

	/**
	 * Objeto mbAdmin
	 */
	@Autowired
	@Qualifier("mbAdmin")
	private transient MBAdministradorSistema mbAdmin;

	/**
	 * Datos del formulario.
	 */
	private DTODatosAgrupaciones dto;

	/**
	 * Lista de entidades federativas
	 */
	private List<DTOEstado> entidades;

	/**
	 * Lista de municipios
	 */
	private List<DTOMunicipio> municipios;

	/**
	 * Lista de agrupaciones para los módulos de consulta y modifica
	 */
	private List<DTOAgrupaciones> agrupaciones;

	/**
	 * Objeto PK correspondiente a la agrupacion que es resultado de la búsqueda
	 * en los módulos de consulta y modifica.
	 */
	private DTOAgrupacionesPK pkAgrupacionBusqueda;

	/**
	 * Variable que contiene la cadena de búsqueda de los módulos de consulta y
	 * modifica.
	 */
	private String busqueda;

	/**
	 * Variable para mostrar datos en las pantallas de consulta y modifica.
	 */
	private boolean showForm;

	/**
	 * Variable para saber en que vista estamos
	 */
	private String vista;

	/**
	 * Lista de ids de componentes
	 */
	private String[] componentes = { "nombreAgrupacion", "abrevAgrupacion", "aPaterno", "aMaterno", "nombreTitular",
			"calleAgrup", "sinNumero", "numExt", "numInt", "coloniaAgrup", "codigoPostal", "entidadF", "delegacionM",
			"telefono1", "ext_telefono1", "telefono2", "ext_telefono2" };

	/**
	 * Nombre del formulario
	 */
	private String nombreForm = "formCapturaAgrupaciones";

	/**
	 * Variable para saber si el usuario de un CAU
	 */
	private boolean esCau;
	
	/**
	 * Método de inicialización que se llama en el FLOW para la pantalla de
	 * CAPTURA.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void init() {
		try {
			littleInit();
			setEntidades(getEstados());
			setShowForm(true);
			log.info("JSON");
			log.info(mbAdmin.getDto().getJsonMenuLateral());
			log.info("existe en lateral : " + mbAdmin.existInLateralMenu(2));
			
		} catch (Exception e) {
			log.error("Error en mbAgrupaciones - init()");
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al inicializar los datos.");
		}
	}


	
	/**
	 * Método que inicializa el objeto que guarda los datos de captura y el
	 * objeto DTOUsuarioLogin.
	 * 
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public void littleInit() throws Exception {
		dto = new DTODatosAgrupaciones();
		dto.setDtoUsuarioLogin(mbAdmin.getDto().getUsuario());
		log.info("DATOS USUARIO");
		log.info("versión: " + dto.getDtoUsuarioLogin().getVersion());
		log.info("rol    : " + dto.getDtoUsuarioLogin().getRolUsuario());
		setEsCau(false);
		if(dto.getDtoUsuarioLogin().getRolUsuario().equals("OBSERVADORES.CAU.OC")){
			setEsCau(true);
		}
	}

	/**
	 * Método de inicialización para las pantallas de CONSULTA y MODIFICA.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void initConsultar() {
		try {
			littleInit();
			setEntidades(getEstados());
			setAgrupaciones(getTodasAgrupaciones());
			setShowForm(false);
			setBusqueda("");
		} catch (Exception e) {
			log.error("Error en mbAgrupaciones - initConsultar()");
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al inicializar los datos.");
		}
	}

	/**
	 * Método llamado desde el commandButton de GUARDAR en la vista de CAPTURA.
	 * Valida los datos de la agrupación antes de guardarlos.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void validaGuarda() {
		try {
			if (!validaNomAbrev(null))
				return;
			if (bsdAgrupaciones.esNombreTitularValido(dto)) {
				if (bsdAgrupaciones.sonApellidosInvalidos(dto)) {
					addErrorMessage("Debes proporcionar al menos un apellido");
					scrollTop();
					return;
				}
			}
			guardaAgrupacion();
		} catch (Exception e) {
			log.error("Error validando datos de titular");
			e.printStackTrace();
		}
	}

	/**
	 * Método que valida que el nombre y abreviación de la agrupación no exista
	 * en la tabla. Si el objeto pk es null, entonces se valida contra todos los
	 * registros de la tabla ya que los datos son de una agrupación nueva. Si el
	 * objeto pk no es null, entonces se valida contra todos los registros menos
	 * con el que tenga pk.idAgrupacion ya que los datos provienen de una
	 * modificación o actualización.
	 * 
	 * @param pk
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean validaNomAbrev(DTOAgrupacionesPK pk) {
		boolean esValido = true;
		try {
			if (bsdAgrupaciones.existeNombreAgrupacion(pk, dto)) {
				addErrorMessage("El nombre de la agrupación ya existe");
				scrollTop();
				esValido = false;
			}
			if (bsdAgrupaciones.existeAbreviAgrupacion(pk, dto)) {
				addErrorMessage("La abreviatura de la agrupación ya existe");
				scrollTop();
				esValido = false;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return esValido;
	}

	/**
	 * Método encargado de hacer el guardado de datos de la nueva agrupación.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void guardaAgrupacion() {
		try {
			bsdAgrupaciones.guardaActualiza(dto);
			littleInit();
			addGrowlMessage("Los datos se guardaron correctamente");
			scrollTop();
		} catch (Exception e) {
			log.error("Error al guardar la agrupación");
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al guardar los datos.");
			scrollTop();
		}
	}

	/**
	 * Método llamado desde el commandButton de GUARDAR en la vista de
	 * MODIFICACION. Valida los datos de la agrupación antes de actualizarlos.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void validaModifica() {
		try {
			if (!validaNomAbrev(pkAgrupacionBusqueda))
				return;
			if (bsdAgrupaciones.esNombreTitularValido(dto)) {
				if (bsdAgrupaciones.sonApellidosInvalidos(dto)) {
					addErrorMessage("Debes proporcionar al menos un apellido");
					scrollTop();
					return;
				}
			}
			modificaAgrupacion();
		} catch (Exception e) {
			log.error("Error validando datos de titular");
			e.printStackTrace();
		}
	}

	/**
	 * Método encargado de hacer la actualización de la agrupación.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void modificaAgrupacion() {
		try {
			bsdAgrupaciones.actualizaAgrupacion(getPkAgrupacionBusqueda(), getDto());
			setShowForm(false);
			setBusqueda("");
			setAgrupaciones(getTodasAgrupaciones());
			addGrowlMessage("Los datos se actualizaron correctamente");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al actualizar los datos.");
		}
	}

	/**
	 * Médoto que asigna la lista de municipios al objeto municipios.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void obtenMunicipios() {
		try {
			setMunicipios(getMunicipios(dto.getEstado()));
			dto.setMunicipio(null);
		} catch (Exception e) {
			log.error("Error al obtener municipios");
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al obtener municipios.");
		}
	}

	/**
	 * Método usado en las vista de consulta y modifica que se encarga de
	 * obtener las agrupaciones que coinciden con las busqueda que hizo el
	 * usuario.
	 * 
	 * @param q
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public List<String> filtraAgrupaciones(String q) {
		List<String> lista = new ArrayList<String>();
		try {
			setAgrupaciones(getTodasAgrupaciones());
			for (DTOAgrupaciones ag : agrupaciones) {
				if (ag.getNombreAgrupacion().toLowerCase().contains(q.toLowerCase())) {
					lista.add(ag.getNombreAgrupacion());
				}
				if (ag.getAbreviatura() != null) {
					if (ag.getAbreviatura().toLowerCase().contains(q.toLowerCase())) {
						lista.add(ag.getAbreviatura());
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Obtiene los datos de la agrupación correspondiente a la búsqueda hecha
	 * por nombre en a vista consulta y modifica.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void muestraAgrupacion() {
		try {
			for (DTOAgrupaciones dto : agrupaciones) {
				if (dto.getNombreAgrupacion().equals(busqueda) || (dto.getAbreviatura() != null && dto.getAbreviatura().equals(busqueda))) {
					setPkAgrupacionBusqueda(dto.getPk());
					setDto(obtenDatos(dto));
					obtenDatosGeograficos();
					resetFields();
					setShowForm(true);
					break;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			addErrorMessage("Hubo un error al mostrar los datos de la agrupación.");
		}
	}

	/**
	 * Método que elimina una agrupacion.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void eliminaAgrupacion() {
		try {
			bsdAgrupaciones.eliminaAgrupacion(getPkAgrupacionBusqueda(), getDto());
			setAgrupaciones(getTodasAgrupaciones());
			setShowForm(false);
			setBusqueda("");
			addGrowlMessage("Los datos se eliminaron correctamente.");
		} 
		catch(DataIntegrityViolationException e){
			if(e.getRootCause().getMessage().contains(ORA_ERROR_CODE_CHILD_RECORD_FOUND)){
				addErrorMessage("No se puede eliminar la agrupación porque existen registros en la base que dependen de ella.");
				log.error("Error al eliminar agrupacion");
				log.error(e);
				e.printStackTrace();
				scrollTop();
				return;
			}
		}
		catch (Exception e) {
			addErrorMessage("Hubo un error al eliminar la agrupación.");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Mapea un objeto a otro.
	 * 
	 * @param dto
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTODatosAgrupaciones obtenDatos(DTOAgrupaciones dto) {
		return bsdAgrupaciones.mapeaDatos(dto);
	}

	/**
	 * Obtiene datos geograficos basado en los datos que se hayan capturado.
	 * 
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public void obtenDatosGeograficos() throws Exception {
		if (dto.getEstado() != null && !dto.getEstado().equals("") && !dto.getEstado().equals(0)) {
			setMunicipios(getMunicipios(dto.getEstado()));
			dto.setEstadoC(getNombreEntidad(dto.getEstado()));
			if (dto.getMunicipio() != null && !dto.getMunicipio().equals("") && !dto.getMunicipio().equals(0)) {
				dto.setMunicipioC(getNombreMunicipio(dto.getMunicipio()));
			}
		}
	}

	/**
	 * Obtiene el nombre de una entidad federativa dado su ID.
	 * 
	 * @param idEstado
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public String getNombreEntidad(Integer idEstado) {
		String est = "";
		for (DTOEstado edo : entidades) {
			if (edo.getIdEstado().equals(idEstado)) {
				est = edo.getIdEstado() + " .- " + edo.getNombreEstado();
				break;
			}
		}
		return est;
	}

	/**
	 * Obtiene el nombre de un municipio dato su ID.
	 * 
	 * @param idMunicipio
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public String getNombreMunicipio(Integer idMunicipio) throws Exception {
		DTOMunicipio mun = bsdServicios.obtenMunicipio(dto.getEstado(), idMunicipio, EnumAmbitoSistema.F);
		String nom = mun.getIdMunicipio() + " .- " + mun.getNombreMunicipio();
		return nom;
	}

	/**
	 * 
	 * Limpia el formulario
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void clearForm() {
		try {
			setDto(obtenDatos(bsdAgrupaciones.obtenAgrupacion(getPkAgrupacionBusqueda())));
			obtenDatosGeograficos();
			scrollTop();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Oculta el formulario
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void hideForm() {
		setShowForm(false);
	}

	/**
	 * Desahabilita/Habilita los campos de numeroInterior y numeroExterior en
	 * las vistas.
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void disableNumbers() {
		if (dto.isSinNumero()) {
			resetField("formCapturaAgrupaciones:numExt");
			resetField("formCapturaAgrupaciones:numInt");
			dto.setNumExterior(null);
			dto.setNumInterior(null);
		}
	}

	/**
	 * Resetea los inputs del formulario en la vista modificar
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void resetFields() {
		if (getVista().equals("modificar")) {
			for (String id : getComponentes()) {
				resetField(getNombreForm() + ":" + id);
			}
		}
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
	 * Decide si el campo nombreTitular es requerido.
	 * 
	 * @param field
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean esRequerido(String field) {
		boolean esRequerido = false;

		switch (field) {
		case "nombre":
			esRequerido = (dto.getaPaternoTitular() != null && !dto.getaPaternoTitular().equals("")
					|| dto.getaMaternoTitular() != null && !dto.getaMaternoTitular().equals(""));

			break;
		}

		return esRequerido;
	}

	public void test() {
		log.info("JAJAJA");
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
		FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
		FacesContext.getCurrentInstance().addMessage("mensaje", msj);
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
	 * Método que obtiene la lista de estados.
	 * 
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	private List<DTOEstado> getEstados() throws Exception {
		return bsdServicios.obtenEstados();
	}

	/**
	 * Método que obtiene la lista de municpios para un estado.
	 * 
	 * @param idEstado
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	private List<DTOMunicipio> getMunicipios(Integer idEstado) throws Exception {
		return bsdServicios.obtenMunicipios(idEstado, EnumAmbitoSistema.F);
	}

	/**
	 * Método que obtiene todas las agrupaciones registradas para este proceso y
	 * detalle.
	 * 
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	private List<DTOAgrupaciones> getTodasAgrupaciones() throws Exception {
		return bsdAgrupaciones.obtenAgrupaciones(mbAdmin.obtenUsuario().getIdProcesoElectoral(),
				mbAdmin.obtenUsuario().getIdDetalleProceso());
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTODatosAgrupaciones getDto() {
		return dto;
	}

	/**
	 * Método SET
	 * 
	 * @param dto
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setDto(DTODatosAgrupaciones dto) {
		this.dto = dto;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public List<DTOMunicipio> getMunicipios() {
		return municipios;
	}

	/**
	 * Método SET
	 * 
	 * @param municipios
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setMunicipios(List<DTOMunicipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public List<DTOEstado> getEntidades() {
		return entidades;
	}

	/**
	 * Método SET
	 * 
	 * @param entidades
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setEntidades(List<DTOEstado> entidades) {
		this.entidades = entidades;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public List<DTOAgrupaciones> getAgrupaciones() {
		return agrupaciones;
	}

	/**
	 * Método SET
	 * 
	 * @param agrupaciones
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setAgrupaciones(List<DTOAgrupaciones> agrupaciones) {
		this.agrupaciones = agrupaciones;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public String getBusqueda() {
		return busqueda;
	}

	/**
	 * Método SET
	 * 
	 * @param busqueda
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTOAgrupacionesPK getPkAgrupacionBusqueda() {
		return pkAgrupacionBusqueda;
	}

	/**
	 * Método SET
	 * 
	 * @param pkAgrupacionBusqueda
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setPkAgrupacionBusqueda(DTOAgrupacionesPK pkAgrupacionBusqueda) {
		this.pkAgrupacionBusqueda = pkAgrupacionBusqueda;
	}

	/**
	 * Método GET
	 * 
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean isShowForm() {
		return showForm;
	}

	/**
	 * Método SET
	 * 
	 * @param showForm
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	/**
	 * Método GET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public String getVista() {
		return vista;
	}

	/**
	 * Método SET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}

	/**
	 * Método GET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public String[] getComponentes() {
		return componentes;
	}

	/**
	 * Método SET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setComponentes(String[] componentes) {
		this.componentes = componentes;
	}

	/**
	 * Método GET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public String getNombreForm() {
		return nombreForm;
	}

	/**
	 * Método SET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setNombreForm(String nombreForm) {
		this.nombreForm = nombreForm;
	}

	/**
	 * Método GET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean isEsCau() {
		return esCau;
	}

	/**
	 * Método SET
	 * 
	 * @author Jean Pierre Pacheco Avila
	 */
	public void setEsCau(boolean esCau) {
		this.esCau = esCau;
	}
}