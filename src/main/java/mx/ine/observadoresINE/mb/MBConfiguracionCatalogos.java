package mx.ine.observadoresINE.mb;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;
import mx.ine.observadoresINE.util.Utilidades;
import mx.ine.observadoresINE.bsd.BSDAgrupacionesInterface;
import mx.ine.observadoresINE.bsd.BSDConfiguracionCatalogosInterface;
import mx.ine.observadoresINE.dao.impl.DAOConfiguracionCatalogosImpl;
import mx.ine.observadoresINE.dto.DTOReglas;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOCAcciones;
import mx.ine.observadoresINE.dto.db.DTOCAccionesPK;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsable;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsablePK;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridades;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridadesPK;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacion;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacionPK;
import mx.ine.observadoresINE.dto.db.DTOCJustificaciones;
import mx.ine.observadoresINE.dto.db.DTOCJustificacionesPK;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaionPK;
import mx.ine.observadoresINE.util.Constantes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Clase que se encarga de capturar, consultar y modificar los catálogos para el
 * administrador designado
 * 
 * @author Gerardo López
 * @version 1.0
 * @since 27/06/2017
 *
 */
/**
 * @author Gerardo López
 * @since 11/07/2017
 */
@Controller("mbConfigCat")
@Scope("session")
public class MBConfiguracionCatalogos implements Serializable {

	/**
	 * serialVersionUID Generado automaticamente
	 */
	private static final long serialVersionUID = 5569656508153002852L;

	/**
	 * LOGGER
	 */
	private static final Log log = LogFactory
			.getLog(MBConfiguracionCatalogos.class);

	// INYECCION DE DEPENDENCIAS CON MB
	/**
	 * MB que se encarga de la gestión del sistema: Menú lateral, menú de
	 * acciones, usuario... etc.
	 */
	@Autowired
	@Qualifier("mbAdmin")
	private transient MBAdministradorSistema mbAdmin;

	// INYECCION DE DEPENDENCIAS CON BSD

	/**
	 * BSD que se encarga de toda la transacción en BD para los catálogos por
	 * medio del AS y DAO
	 */
	@Autowired
	@Qualifier("bsdConfiguracionCatalogos")
	private transient BSDConfiguracionCatalogosInterface bsdConfiguracionCatalogos;

	/**
	 * Objeto BSD para el módulo de agrupaciones
	 */
	@Autowired
	@Qualifier("bsdAgrupaciones")
	private transient BSDAgrupacionesInterface bsdAgrupaciones;

	// DECLARACION DE VARIABLES A UTILIZAR PARA LA CONFIGURACION DE LOS
	// CATALOGOS

	/**
	 * Datos del usuario logeado
	 */
	private DTOUsuarioLogin usuario;

	/**
	 * Variable para la acción del módulo = CAPTURAR, CONSULTAR, MODIFICAR
	 */
	private String accionModulo;

	/**
	 * Variable que contiene la constante para la acción de CAPTURAR
	 */
	private String accionCapturar = Constantes.ACCION_CAPTURAR;

	/**
	 * Variable que contiene la constante para la acción de CONSULTAR
	 */
	private String accionConsultar = Constantes.ACCION_CONSULTAR;

	/**
	 * Variable que contiene la constante para la acción de MODIFICAR
	 */
	private String accionModificar = Constantes.ACCION_MODIFICAR;

	/**
	 * Variable para conocer el catalogo = CATALOGO DE ACCIONES, CARGO DE
	 * RESPONSABLE, ESCOLARIDADES, JUSTIFICACIONES
	 */
	private String catalogo;

	/**
	 * Variable cuando no se tiene valor para ID_PROCESO_ELECTORAL (carga
	 * inicial de catálogos) se inicializa con la constante definida
	 */
	private Integer sinIdProceso = Constantes.SIN_PROCESO_ELECTORAL;

	/**
	 * Variable cuando no se tiene valor para ID_DETALLE_PROCESO (carga inicial
	 * de catálogos) se inicializa con la constante definida
	 */
	private Integer sinIdDetalle = Constantes.SIN_DETALLE_PROCESO;

	/**
	 * Variable para el detalle seleccionado para la configuración de los
	 * catálogos
	 */
	private Integer idDetalleSeleccionado;

	/**
	 * Lista de detalles de proceso electoral
	 */
	private List<DTODetalleProcesoWS> listaDetalles = new ArrayList<DTODetalleProcesoWS>();

	/**
	 * Objeto de Detalle proceso para obtener la el idDetalle y el idProceso
	 */
	private DTODetalleProcesoWS procesoDetalle;

	/**
	 * Variable para saber sí se configuraran los catálogos para todos los
	 * detalles del proceso electoral
	 */
	private boolean sonTodos;

	/**
	 * Lista de consulta para conocer los detalles-procesos que ya fueron
	 * gurdados y quitarlos de la lista de captura
	 */
	private List<DTODetalleProcesoWS> listaDetallesConsulta;

	/**
	 * Bandera para hacer disable el boton
	 */
	private boolean disableBtnElimina;

	/**
	 * Bandera para mostrar tablas en vista de captura
	 */
	private boolean cargaSeleccionada;

	/**
	 * Bandera para mostrar mensajes, ayuda en la vista a que no se vea el
	 * espacio en blanco destinado a los mensajes
	 */
	private boolean muestraMensaje;

	/**
	 * Bandera que muestra el formulario para agregar acciones, cargos de
	 * represenate, escolaridades o justificaciones en catálogos por el usuario
	 */
	private boolean muestraFormularioAgrega;

	/**
	 * Bandera para mostrar la vista de captura, consulta o modifica de acuerdo
	 * a la condicion
	 */
	private boolean muestraForm;

	// ******* VARIABLES DEL CATÁLOGO DE ACCIONES ************

	/**
	 * Lista para las acciones precargadas en C_ACCIONES de BD
	 */
	private List<DTOCAcciones> accionesPredefinidas;

	/**
	 * Lista para las acciones seleccionadas y agregadas para cada proceso y
	 * detalle en C_ACCIONES de la BD
	 */
	private List<DTOCAcciones> accionesSeleccionadas;

	/**
	 * Variable para una agregar nuevas acciones para cada proceso y detalle en
	 * C_ACCIONES de BD
	 */
	private List<DTOCAcciones> nuevasAcciones;

	/**
	 * Lista para la nuevas acciones seleccionadas a eliminar
	 */
	private List<DTOCAcciones> nuevasAccionesSeleccionadas;

	/**
	 * Lista de acciones a guardar por detalle-proceso
	 */
	private List<DTOCAcciones> accionesAGuardar;

	/**
	 * Lista temporal que sirve para poder mantener la lista de acciones
	 * seleccionadas de la carga inicial
	 */
	private List<DTOCAcciones> accionesSeleccionadasTmp;

	/**
	 * Lista con la que llenaremos la tabla de las acciones seleccionadas
	 * 
	 */
	private List<DTOCAcciones> accionesSeleccionadasLista;

	/**
	 * Lista para la nuevas acciones seleccionadas a eliminar, temporal para
	 * cuando el usuario regrese al catalogo inicial no las encuentre
	 * seleccionadas
	 */
	private List<DTOCAcciones> nuevasAccionesSeleccionadasTmp;

	/**
	 * Lista para eliminar acciones en la vista de modificar
	 */
	private List<DTOCAcciones> accionesAEliminar;

	/**
	 * Variable para escribir una nueva accion
	 */
	private DTOCAcciones nuevaAccion;

	/**
	 * Variable para el nombre de la nueva accion
	 */
	private String accionNombre;

	// ******* VARIABLES DEL CATÁLOGO DE CARGO RESPONSABLE ************

	/**
	 * Lista para los cargos precargados en C_CARGO_RESPONSABLE de BD
	 */
	private List<DTOCCargoResponsable> cargosPredefinidos;

	/**
	 * Lista para los cargos seleccionados y agregados para cada proceso y
	 * detalle en C_CARGO_RESPONSABLE de la BD
	 */
	private List<DTOCCargoResponsable> cargosSeleccionados;

	/**
	 * Lista temporal que sirve para poder mantener la lista de cargos
	 * seleccionados de la carga inicial
	 */
	private List<DTOCCargoResponsable> cargosSeleccionadosTmp;

	/**
	 * Lista con la que llenaremos la tabla de las cargos seleccionados
	 * 
	 */
	private List<DTOCCargoResponsable> cargosSeleccionadosLista;

	/**
	 * Lista para los nuevos cargos seleccionados a eliminar
	 */
	private List<DTOCCargoResponsable> nuevosCargosSeleccionados;

	/**
	 * Lista para los nuevos cargos seleccionados a eliminar, temporal para
	 * cuando el usuario regrese al catalogo inicial no los encuentre
	 * seleccionados
	 */
	private List<DTOCCargoResponsable> nuevosCargosSeleccionadosTmp;

	/**
	 * Variable para una agregar nuevos cargos para cada proceso y detalle en
	 * C_CARGO_RESPONSABLE de BD
	 */
	private List<DTOCCargoResponsable> nuevosCargos;

	/**
	 * Lista para eliminar cargos en la vista de modificar
	 */
	private List<DTOCCargoResponsable> cargosAEliminar;

	/**
	 * Lista para guardar los cargos
	 */
	private List<DTOCCargoResponsable> cargosAGuardar;

	/**
	 * Variable para registrar un nuevo cargo
	 */
	private DTOCCargoResponsable nuevoCargo;

	/**
	 * Variable para la descripcion del cargo en un nuevo registro
	 */
	private String descripcionCargo;

	/**
	 * Variable para las iniciales del cargo en un nuevo registro
	 */
	private String inicialesCargo;

	/**
	 * Variable para el origen del cargo en un nuevo registro
	 */
	private String origenCargo;

	/**
	 * Variable para el id Cargo alterno del cargo en un nuevo registro
	 */
	private String idCargoAlterno;

	// ******* VARIABLES DEL CATÁLOGO DE ESCOLARIDADES ************

	/**
	 * Lista para las escolaridades precargadas en C_ESCOLARIDADES de BD
	 */
	private List<DTOCEscolaridades> escolaridadesPredefinidas;

	/**
	 * Lista para las escolaridades seleccionadas y agregadas para cada proceso
	 * y detalle en C_ESCOLARIDADES de la BD
	 */
	private List<DTOCEscolaridades> escolaridadesSeleccionadas;

	/**
	 * Lista temporal que sirve para poder mantener la lista de escolaridades
	 * seleccionadas de la carga inicial
	 */
	private List<DTOCEscolaridades> escolaridadesSeleccionadasTmp;

	/**
	 * Lista con la que llenaremos la tabla de las escolaridades seleccionadas
	 * 
	 */
	private List<DTOCEscolaridades> escolaridadesSeleccionadasLista;

	/**
	 * Lista para las nuevas escolaridades seleccionadas a eliminar
	 */
	private List<DTOCEscolaridades> nuevasEscolaridadesSeleccionadas;

	/**
	 * Lista para las nuevas escolaridades seleccionadas a eliminar, temporal
	 * para cuando el usuario regrese al catalogo inicial no las encuentre
	 * seleccionadas
	 */
	private List<DTOCEscolaridades> nuevasEscolaridadesSeleccionadasTmp;

	/**
	 * Variable para una agregar nuevas escolaridades para cada proceso y
	 * detalle en C_ESCOLARIDADES de BD
	 */
	private List<DTOCEscolaridades> nuevasEscolaridades;

	/**
	 * Lista para eliminar escolaridades en la vista de modificar
	 */
	private List<DTOCEscolaridades> escolaridadesAEliminar;

	/**
	 * Lista para guardar las escolaridades
	 */
	private List<DTOCEscolaridades> escolaridadesAGuardar;

	/**
	 * Variable para registrar una nueva escolaridad
	 */
	private DTOCEscolaridades nuevaEscolaridad;

	/**
	 * Variable para la descripcion de la escolaridad en un nuevo registro
	 */
	private String descripcionEscolaridad;

	// ******* VARIABLES DEL CATÁLOGO DE JUSTIFICACIONES ************

	/**
	 * Lista para las justificaciones precargadas en C_JUSTIFICACIONES de BD
	 */
	private List<DTOCJustificaciones> justificacionesPredefinidas;

	/**
	 * Lista para las justificaciones seleccionadas y agregadas para cada
	 * proceso y detalle en C_JUSTIFICACIONES de la BD
	 */
	private List<DTOCJustificaciones> justificacionesSeleccionadas;

	/**
	 * Lista temporal que sirve para poder mantener la lista de justificaciones
	 * seleccionadas de la carga inicial
	 */
	private List<DTOCJustificaciones> justificacionesSeleccionadasTmp;

	/**
	 * Lista con la que llenaremos la tabla de las justificaciones seleccionadas
	 * 
	 */
	private List<DTOCJustificaciones> justificacionesSeleccionadasLista;

	/**
	 * Lista para las nuevas justificaciones seleccionadas a eliminar
	 */
	private List<DTOCJustificaciones> nuevasJustificacionesSeleccionadas;

	/**
	 * Lista para las nuevas justificaciones seleccionadas a eliminar, temporal
	 * para cuando el usuario regrese al catalogo inicial no las encuentre
	 * seleccionadas
	 */
	private List<DTOCJustificaciones> nuevasJustificacionesSeleccionadasTmp;

	/**
	 * Variable para una agregar nuevas justificaciones para cada proceso y
	 * detalle en C_JUSTIFICACIONES de BD
	 */
	private List<DTOCJustificaciones> nuevasJustificaciones;

	/**
	 * Justificaciones por default
	 */
	private List<DTOCJustificaciones> justificacionesXDefault;

	/**
	 * Lista para eliminar justificaciones en la vista de modificar
	 */
	private List<DTOCJustificaciones> justificacionesAEliminar;

	/**
	 * Lista para guardar justificaciones
	 */
	private List<DTOCJustificaciones> justificacionesAGuardar;

	/**
	 * Variable para registrar una nueva justificacion
	 */
	private DTOCJustificaciones nuevaJustificacion;

	/**
	 * Variable para la justificación seleccionada por defecto
	 */
	private DTOCJustificaciones justificacionPorDefecto;

	/**
	 * Variable para la descripcion de la justificacion en un nuevo registro
	 */
	private String descripcionJustificacion;

	/**
	 * Variable para descripcion corta de la justificacion en un nuevo registro
	 */
	private String descripcionCortaJustificacion;

	/**
	 * Variable para resultado de la justificacion en un nuevo registro
	 */
	private String resultadoJustificacion;

	// ******* VARIABLES DEL CATÁLOGO DE EVALUACIONES ************

	/**
	 * Lista para las evaluaciones precargadas en C_EVALUACIONES de BD
	 */
	private List<DTOCEvaluacion> evaluacionesPredefinidas;

	/**
	 * Lista para las evaluaciones seleccionadas y agregadas para cada proceso y
	 * detalle en C_EVALUACIONES de la BD
	 */
	private List<DTOCEvaluacion> evaluacionesSeleccionadas;

	/**
	 * Lista temporal que sirve para poder mantener la lista de evaluaciones
	 * seleccionadas de la carga inicial
	 */
	private List<DTOCEvaluacion> evaluacionesSeleccionadasTmp;

	/**
	 * Lista con la que llenaremos la tabla de las evaluaciones seleccionadas
	 * 
	 */
	private List<DTOCEvaluacion> evaluacionesSeleccionadasLista;

	/**
	 * Lista para las nuevas evaluaciones seleccionadas a eliminar
	 */
	private List<DTOCEvaluacion> nuevasEvaluacionesSeleccionadas;

	/**
	 * Lista para las nuevas evaluaciones seleccionadas a eliminar, temporal
	 * para cuando el usuario regrese al catalogo inicial no las encuentre
	 * seleccionadas
	 */
	private List<DTOCEvaluacion> nuevasEvaluacionesSeleccionadasTmp;

	/**
	 * Variable para una agregar nuevas evaluaciones para cada proceso y detalle
	 * en C_EVALUACION de BD
	 */
	private List<DTOCEvaluacion> nuevasEvaluaciones;

	/**
	 * Evaluaciones por default
	 */
	private List<DTOCEvaluacion> evaluacionesXDefault;

	/**
	 * Lista para eliminar evaluaciones en la vista de modificar
	 */
	private List<DTOCEvaluacion> evaluacionesAEliminar;

	/**
	 * Lista para guardar evaluaciones
	 */
	private List<DTOCEvaluacion> evaluacionesAGuardar;

	/**
	 * Variable para registrar una nueva evaluación
	 */
	private DTOCEvaluacion nuevaEvaluacion;

	/**
	 * Variable para la evaluación seleccionada por defecto
	 */
	private List<DTOCEvaluacion> evaluacionesPorDefecto;

	/**
	 * Variable para la descripcion de la evaluacion en un nuevo registro
	 */
	private String descripcionEvaluacion;

	/**
	 * Variable para tipo de evaluacion en un nuevo registro
	 */
	private String tipoEvaluacion;

	/**
	 * Lista de Observadores para las llaves foraneas de la tabla OBSERVADORES
	 * en BD tienen los catálogos de C_ESCOLARIDADES y C_JUSTIFICACIONES
	 */
	private List<DTOObservadores> listaObservadores;

	/**
	 * Variable que detecta sí sólo se cuenta con proceso detalle en el combo
	 */
	private boolean comboDetalleUnico;

	/**
	 * Variable que detecta sí ya existe algún registro en base para los demás
	 * módulos que hacen uso del catálogo y así bloquear el mismo
	 */
	private boolean existenRegistros;

	/**
	 * Bloquea combo cuando sólo sea un proceso y detalle, y quita mensaje de
	 * requerido
	 */
	// private boolean bloqueaComno

	/**
	 * Lista de reglas para evaluaciones
	 */
	private List<DTOReglas> reglasEvaluacion;

	/**
	 * Lista de reglas seleccionadas para la evaluación
	 */
	private List<DTOReglas> reglasSeleccionadas;

	/**
	 * Objeto de evaluacion seleccionada para observar sus reglas asociadas
	 */
	private DTOCEvaluacion selectEvaluacion;

	/**
	 * Lista para obtener las reglas asociadas
	 */
	private List<DTOReglasEvalucaion> reglasAsociadas;

	/**
	 * Lista para obtener las reglas asociadas
	 */
	private List<DTOReglasEvalucaion> reglasPredeterminadas;

	/**
	 * Lista para obtener las reglas asociadas
	 */
	private List<DTOReglasEvalucaion> reglasPredeterminadasDefecto;

	/**
	 * Lista para obtener las reglas asociadas, seleccionadas por el usuario
	 * sobre las predefinidas
	 */
	private List<DTOReglasEvalucaion> reglasAsociadasPredefinidas;

	/**
	 * Lista para obtener las reglas asociadas a las evaluaciones por defecto
	 */
	private List<DTOReglasEvalucaion> reglasAsociadasPorDefecto;

	/**
	 * Lista para mostrar las reglas asociadas
	 */
	private List<DTOReglas> reglasAsociadasEva;

	/**
	 * Lista para seleccionar reglas
	 */
	private List<DTOReglas> nuevasReglasSeleccionadas;

	/**
	 * Lista que guardara las reglas asociadas a una evaluacion
	 */
	private List<DTOReglas> listaReglasConEvaluacion;

	/**
	 * Contador de reglas
	 */
	private Integer contadorIdSiguiente;

	/**
	 * Lista para las reglas predefinidas
	 */
	private List<DTOReglasEvalucaion> reglasEvaluacionesPredefinidas;

	/**
	 * 
	 * Lista para las reglas predefinidas al guardar
	 */
	private List<DTOReglasEvalucaion> reglasEvalPredefinidasGuardar;

	/**
	 * Lista para las reglas de evaluaciones por defecto
	 */
	private List<DTOReglasEvalucaion> reglasEvaluacionesPorDefecto;

	/**
	 * 
	 * Lista para las reglas de las evaluaciones por defecto al guardar
	 */
	private List<DTOReglasEvalucaion> reglasEvalPorDefectoGuardar;

	/**
	 * Variable para visualizar las opciones de origen del curso
	 */
	public boolean esCurso;

	/**
	 * Enum de la impartición de cursos
	 */
	private Map<String, Integer> origenesCurso;

	/**
	 * Variable que almacena el valor del curso seleccionado
	 */
	private Integer origenCurso;

	/**
	 * Objeto de DTOReglas para identificar la regla seleccionada por el usuario
	 * para el origen curso
	 */
	private DTOReglas selectRule;

	/**
	 * Variable para guardar el origen del curso cada vez que es cambiado
	 */
	private Integer numeroCurso;

	// ******************* INICIAN METODOS ****************************

	/**
	 * Metodo para inicializar valores
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void init() {
		// Asignamos al usuario logeado a nuestro objeto usuario
		usuario = mbAdmin.getDto().getUsuario();

		// Inicializamos variables globales del módulo
		accionModulo = null;
		cargaSeleccionada = false;
		muestraMensaje = false;
		muestraFormularioAgrega = false;
		muestraForm = false;
		disableBtnElimina = false;
		idDetalleSeleccionado = null;
		sonTodos = false;
		procesoDetalle = new DTODetalleProcesoWS();
		comboDetalleUnico = false;
		existenRegistros = false;

		// Asignamos la lista de detalles del usuario Administrador, que
		// contiene todos
		listaDetalles = new ArrayList<DTODetalleProcesoWS>();
		listaDetallesConsulta = new ArrayList<DTODetalleProcesoWS>();

		// *** Lista de observadores
		listaObservadores = new ArrayList<DTOObservadores>();

		// *** Catálogo de acciones **************************
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			accionesPredefinidas = new ArrayList<DTOCAcciones>();
			accionesSeleccionadas = new ArrayList<DTOCAcciones>();
			nuevasAcciones = new ArrayList<DTOCAcciones>();
			nuevasAccionesSeleccionadas = new ArrayList<DTOCAcciones>();
			accionesAGuardar = new ArrayList<DTOCAcciones>();
			nuevaAccion = new DTOCAcciones();
			accionNombre = null;
			accionesSeleccionadasLista = new ArrayList<DTOCAcciones>();
			accionesSeleccionadasTmp = new ArrayList<DTOCAcciones>();
			nuevasAccionesSeleccionadasTmp = new ArrayList<DTOCAcciones>();
			accionesAEliminar = new ArrayList<DTOCAcciones>();
		}

		// *** Catálogo de Cargo de responsable ****************
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			cargosPredefinidos = new ArrayList<DTOCCargoResponsable>();
			cargosSeleccionados = new ArrayList<DTOCCargoResponsable>();
			cargosSeleccionadosLista = new ArrayList<DTOCCargoResponsable>();
			cargosSeleccionadosTmp = new ArrayList<DTOCCargoResponsable>();
			nuevosCargosSeleccionados = new ArrayList<DTOCCargoResponsable>();
			nuevosCargosSeleccionadosTmp = new ArrayList<DTOCCargoResponsable>();
			nuevosCargos = new ArrayList<DTOCCargoResponsable>();
			cargosAEliminar = new ArrayList<DTOCCargoResponsable>();
			nuevoCargo = new DTOCCargoResponsable();
			descripcionCargo = null;
			inicialesCargo = null;
			origenCargo = null;
			idCargoAlterno = null;
		}

		// *** Catálogo de Escolaridades ***********************
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			escolaridadesPredefinidas = new ArrayList<DTOCEscolaridades>();
			escolaridadesSeleccionadas = new ArrayList<DTOCEscolaridades>();
			escolaridadesSeleccionadasLista = new ArrayList<DTOCEscolaridades>();
			escolaridadesSeleccionadasTmp = new ArrayList<DTOCEscolaridades>();
			nuevasEscolaridadesSeleccionadas = new ArrayList<DTOCEscolaridades>();
			nuevasEscolaridadesSeleccionadasTmp = new ArrayList<DTOCEscolaridades>();
			nuevasEscolaridades = new ArrayList<DTOCEscolaridades>();
			escolaridadesAEliminar = new ArrayList<DTOCEscolaridades>();
			nuevaEscolaridad = new DTOCEscolaridades();
			descripcionEscolaridad = null;
		}

		// *** Catálogo de Justificacione ***********************
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			justificacionesPredefinidas = new ArrayList<DTOCJustificaciones>();
			justificacionesSeleccionadas = new ArrayList<DTOCJustificaciones>();
			justificacionesSeleccionadasLista = new ArrayList<DTOCJustificaciones>();
			justificacionesSeleccionadasTmp = new ArrayList<DTOCJustificaciones>();
			nuevasJustificacionesSeleccionadas = new ArrayList<DTOCJustificaciones>();
			nuevasJustificacionesSeleccionadasTmp = new ArrayList<DTOCJustificaciones>();
			justificacionesXDefault = new ArrayList<DTOCJustificaciones>();
			nuevasJustificaciones = new ArrayList<DTOCJustificaciones>();
			justificacionesAEliminar = new ArrayList<DTOCJustificaciones>();
			nuevaJustificacion = new DTOCJustificaciones();
			justificacionPorDefecto = new DTOCJustificaciones();
			descripcionJustificacion = null;
			descripcionCortaJustificacion = null;
			resultadoJustificacion = null;
		}

		// *** Catálogo de evaluaciones ***************************
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			evaluacionesPredefinidas = new ArrayList<DTOCEvaluacion>();
			evaluacionesSeleccionadas = new ArrayList<DTOCEvaluacion>();
			evaluacionesSeleccionadasLista = new ArrayList<DTOCEvaluacion>();
			evaluacionesSeleccionadasTmp = new ArrayList<DTOCEvaluacion>();
			nuevasEvaluacionesSeleccionadas = new ArrayList<DTOCEvaluacion>();
			nuevasEvaluacionesSeleccionadasTmp = new ArrayList<DTOCEvaluacion>();
			nuevasEvaluaciones = new ArrayList<DTOCEvaluacion>();
			evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
			nuevaEvaluacion = new DTOCEvaluacion();
			selectEvaluacion = new DTOCEvaluacion();
			evaluacionesPorDefecto = new ArrayList<DTOCEvaluacion>();
			reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
			reglasAsociadasPredefinidas = new ArrayList<DTOReglasEvalucaion>();
			reglasAsociadasPorDefecto = new ArrayList<DTOReglasEvalucaion>();
			reglasEvalPredefinidasGuardar = new ArrayList<DTOReglasEvalucaion>();
			reglasEvalPorDefectoGuardar = new ArrayList<DTOReglasEvalucaion>();
			reglasAsociadasEva = new ArrayList<DTOReglas>();
			nuevasReglasSeleccionadas = new ArrayList<DTOReglas>();
			listaReglasConEvaluacion = new ArrayList<DTOReglas>();
			reglasEvaluacionesPredefinidas = new ArrayList<DTOReglasEvalucaion>();
			reglasEvaluacionesPorDefecto = new ArrayList<DTOReglasEvalucaion>();
			reglasPredeterminadas = new ArrayList<DTOReglasEvalucaion>();
			descripcionEvaluacion = null;
			tipoEvaluacion = null;
			contadorIdSiguiente = null;
			esCurso = false;
			origenCurso = null;
			selectRule = new DTOReglas();
			numeroCurso = null;

			// Inicializar las reglas de evaluacion, con las reglas que se tiene
			// registro
			List<String> listaReglas = new ArrayList<String>();
			listaReglas.add("Fecha y hora del curso");
			listaReglas.add("Resultado de la acreditación");
			listaReglas.add("Fecha de la sesión");

			// Meterlas en una lista de tipo Reglas
			reglasEvaluacion = new ArrayList<DTOReglas>();

			int i = 0;
			for (String regla : listaReglas) {
				DTOReglas reglaInicial = new DTOReglas();
				reglaInicial.setIdRegla(i + 1);
				reglaInicial.setDescripcionRegla(regla);
				reglasEvaluacion.add(reglaInicial);
				i++;
			}

			// Construimos mapa de cursos
			origenesCurso = new LinkedHashMap<String, Integer>();
			origenesCurso.put("INE", 1);
			origenesCurso.put("OPLE", 2);
			origenesCurso.put("Agrupaciones", 3);

		}

	}

	/**
	 * Metodo para la vista de Captura de cada catalogo
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void initCaptura() {
		init();
		accionModulo = Constantes.ACCION_CAPTURAR;
		creaListaDetalles();
		existeRegistro();
		isExistente();
		if (!isExistente()) {
			muestraForm = true;
			// Verificamos el tipo de catalogo que se setea al entrar en cada
			// módulo
			// en el FLOW

			/**
			 * Catalogo de acciones, sólo cuenta con la PK: idProceso,
			 * idDetalle, idAccion y con un campo en el nombre de la acción
			 */
			if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {

				// Obtenemos la carga inicial de acciones para presentarsela al
				// ADMIN
				try {
					accionesPredefinidas = bsdConfiguracionCatalogos
							.getCAcciones(sinIdProceso, sinIdDetalle);

					accionesSeleccionadas.addAll(accionesPredefinidas);

				} catch (Exception e) {
					log.error("Hubo un error al obtener el CATALOGO DE ACCIONES en initCaptura() de MBConfiguracionCatalogos");
					log.error(e);
					e.printStackTrace();
				}
			}

			/**
			 * Catálogo de cargo responsable, cuenta con la PK: idProceso,
			 * idDetalle, idCargo, y los campos de descripcion, iniciales,
			 * origen, idCargoAlterno
			 */
			if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
				// Obtenemos la carga inicial de acciones para presentarsela al
				// ADMIN
				try {
					cargosPredefinidos = bsdConfiguracionCatalogos
							.getCCargoResponsable(sinIdProceso, sinIdDetalle);

					cargosSeleccionados.addAll(cargosPredefinidos);

				} catch (Exception e) {
					log.error("Hubo un error al obtener el CATALOGO DE CARGO DE RESPONSABLE en initCaptura() de MBConfiguracionCatalogos");
					log.error(e);
					e.printStackTrace();
				}
			}

			/**
			 * Catálogo de escolaridades, cuenta con la PK: idProceso,
			 * idDetalle, idCargo, y el campo de descripcion
			 */
			if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
				// Obtenemos la carga inicial de acciones para presentarsela al
				// ADMIN
				try {
					escolaridadesPredefinidas = bsdConfiguracionCatalogos
							.getCEscolaridades(sinIdProceso, sinIdDetalle);

					escolaridadesSeleccionadas
							.addAll(escolaridadesPredefinidas);

				} catch (Exception e) {
					log.error("Hubo un error al obtener el CATALOGO DE ESCOLARIDADES en initCaptura() de MBConfiguracionCatalogos");
					log.error(e);
					e.printStackTrace();
				}
			}

			/**
			 * Catálogo de justificaciones, cuenta con la PK: idProceso,
			 * idDetalle, idCargo, y los campos de descripcion, descripcionCorta
			 * y resultado
			 */
			if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
				// Obtenemos la carga inicial de acciones para presentarsela al
				// ADMIN
				try {
					justificacionesPredefinidas = bsdConfiguracionCatalogos
							.getCJustificaciones(sinIdProceso, sinIdDetalle);

					// justificacionesSeleccionadas
					// .addAll(justificacionesPredefinidas);

					int index = 0;
					boolean esEncontrado = false;
					for (int i = 0; i < justificacionesPredefinidas.size(); i++) {
						// if (justificaciones
						// .getDTOCJustificacionesPK()
						// .getIdJustificacion()
						// .equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO))
						// {
						if (justificacionesPredefinidas
								.get(i)
								.getDTOCJustificacionesPK()
								.getIdJustificacion()
								.equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO)) {

							justificacionesXDefault
									.add(justificacionesPredefinidas.get(i));
							esEncontrado = true;
							index = i;
							break;

						}
					}

					justificacionesPredefinidas = ordenaJustificacion(justificacionesPredefinidas);

					if (esEncontrado) {
						justificacionesSeleccionadas
								.addAll(justificacionesPredefinidas);
					}
				} catch (Exception e) {
					log.error("Hubo un error al obtener el CATALOGO DE JUSTIFICACIONES en initCaptura() de MBConfiguracionCatalogos");
					log.error(e);
					e.printStackTrace();
				}
			}

			/**
			 * Catálogo de Evaluaciones, cuenta con la PK: idProceso, idDetalle,
			 * idEvaluacion, y los campos de descripcion y tipo
			 */
			if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
				// Obtenemos la carga inicial de evaluaciones para presentarsela
				// al
				// ADMIN
				try {
					evaluacionesPredefinidas = bsdConfiguracionCatalogos
							.getCEvaluaciones(sinIdProceso, sinIdDetalle);

					// Obtener la justificacion por default
					boolean esEncontrada = false;
					List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
					evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
					for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {

						if (i == evaluacionesPredefinidas.size() - 2
								|| i == evaluacionesPredefinidas.size() - 1) {

							evaluacionesXDefault.add(evaluacionesPredefinidas
									.get(i));
							indexs.add(evaluacionesPredefinidas.get(i));

						}
					}

					if (indexs != null && !indexs.isEmpty()) {
						esEncontrada = true;
					}

					if (esEncontrada) {
						evaluacionesPredefinidas.removeAll(indexs);
						if (evaluacionesPredefinidas != null
								&& !evaluacionesPredefinidas.isEmpty()) {
							evaluacionesSeleccionadas
									.addAll(evaluacionesPredefinidas);
							muestraForm = true;
						}

						// Obtener las reglas de evaluacione predefinidas
						for (DTOCEvaluacion eval : evaluacionesSeleccionadas) {
							List<DTOReglasEvalucaion> reglasEvaluacion = new ArrayList<DTOReglasEvalucaion>();
							reglasEvaluacion = bsdConfiguracionCatalogos
									.getReglasEvaluacion(sinIdProceso,
											sinIdDetalle, eval
													.getDTOCEvaluacionPK()
													.getIdEvaluacion());

							for (DTOReglasEvalucaion regla : reglasEvaluacion) {
								DTOReglasEvalucaion dtoReglasEvaluacion = new DTOReglasEvalucaion();
								dtoReglasEvaluacion = regla;
								reglasEvaluacionesPredefinidas
										.add(dtoReglasEvaluacion);

								// origenCurso = regla.getOrigenCurso();
								//
								// if (origenCurso != null) {
								// esCurso = true;
								// } else {
								// esCurso = false;
								// }
							}

						}

						// Obtener las reglas de evaluaciones por defecto
						for (DTOCEvaluacion evalDefecto : evaluacionesXDefault) {
							List<DTOReglasEvalucaion> reglasEvaluacionDefecto = new ArrayList<DTOReglasEvalucaion>();
							reglasEvaluacionDefecto = bsdConfiguracionCatalogos
									.getReglasEvaluacion(sinIdProceso,
											sinIdDetalle, evalDefecto
													.getDTOCEvaluacionPK()
													.getIdEvaluacion());

							for (DTOReglasEvalucaion regla : reglasEvaluacionDefecto) {
								DTOReglasEvalucaion dtoReglasEvaluacion = new DTOReglasEvalucaion();
								dtoReglasEvaluacion = regla;
								reglasEvaluacionesPorDefecto
										.add(dtoReglasEvaluacion);
							}
						}

						contadorIdSiguiente = evaluacionesPredefinidas.size() + 1;

						log.info("EVALUACIONES PREDEFINIDAS, SIZE: "
								+ evaluacionesPredefinidas.size());
						log.info("EVALUACIONES SELECCIONADAS, SIZE: "
								+ evaluacionesSeleccionadas.size());
					}
				} catch (Exception e) {
					log.error("Hubo un error al obtener el CATALOGO DE EVALUACIONES en initCaptura() de MBConfiguracionCatalogos");
					log.error(e);
					e.printStackTrace();
				}
			}

		} else {
			// Ya existe registro del catalogo
			muestraForm = false;
			muestraMensaje = true;
			FacesMessage msjYaExiste = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_existe_config"));
			FacesContext.getCurrentInstance()
					.addMessage("mensaje", msjYaExiste);

		}

	}

	/**
	 * Metodo para la vista de Consulta de cada catalogo
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void initConsulta() {
		init();
		accionModulo = Constantes.ACCION_CONSULTAR;
		creaListaDetalles();
		existeRegistro();
		if (!isExistente()) {
			muestraForm = false;
			muestraMensaje = true;
			FacesMessage msjNoExiste = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_no_datos"));
			FacesContext.getCurrentInstance()
					.addMessage("mensaje", msjNoExiste);
		}
	}

	/**
	 * Metodo para la vista de Modificar de cada catalogo
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void initModifica() {
		init();
		accionModulo = Constantes.ACCION_MODIFICAR;
		creaListaDetalles();
		existeRegistro();
		if (!isExistente()) {
			muestraForm = false;
			muestraMensaje = true;
			FacesMessage msjNoExiste = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_no_datos"));
			FacesContext.getCurrentInstance()
					.addMessage("mensaje", msjNoExiste);
		}

	}

	/**
	 * Método que valida si existe algún registro en un módulo que utiliza
	 * catálogos para bloquear la configuración del mismo
	 * 
	 * @author Gerardo López
	 * @since 20/07/2017
	 * 
	 */
	public void existeRegistro() {
		try {

			/**
			 * Validamos en promociones para el catálogo de acciones
			 */
			if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {

				existenRegistros = bsdConfiguracionCatalogos
						.promocionesXCAcciones(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());
			}

			/**
			 * Validamos en cursos para el catálogo de cargo responsable
			 */
			if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {

				existenRegistros = bsdConfiguracionCatalogos
						.cursosXCCargoResponsable(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());

			}

			/**
			 * Validamos en observadores para el catálogo de escolaridades,
			 * justificaciones y evaluaciones
			 */
			if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)
					|| catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)
					|| catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {

				existenRegistros = bsdConfiguracionCatalogos
						.observadoresXCEscolarXCJustificaXCEvaluaciones(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());

				log.info("ENTRA EN CONDICION IGUAL A TRUE, valor de existenRegistros: "
						+ existenRegistros);
			}

		} catch (Exception e) {

		}
	}

	/**
	 * Método que valida sí ya existe registro del catálogo en BD
	 * 
	 * @author Gerardo López
	 * @since 04/07/2017
	 * 
	 */
	public boolean isExistente() {
		boolean bandera = false;
		boolean estaGuardada = false;
		// if (usuario.getIdProcesoElectoral() != null
		// && usuario.getIdDetalleProceso() != null) {
		try {
			/**
			 * Catálogo de acciones
			 */
			if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {

				List<DTOCAcciones> listValida = new ArrayList<DTOCAcciones>();

				int tamañoDetalles = listaDetalles.size() - 1;
				for (int i = 0; i < tamañoDetalles; i++) {
					// TODO AQUI ACCIONES
					listValida = bsdConfiguracionCatalogos.getCAcciones(
							listaDetalles.get(i).getIdProcesoElectoral(),
							listaDetalles.get(i).getIdDetalleProceso());

					// listValida = bsdConfiguracionCatalogos.getCAcciones(9,
					// 21);

					if (listValida.size() > 0) {
						listaDetallesConsulta.add(listaDetalles.get(i));
						estaGuardada = true;
					}

				}

				if (estaGuardada) {
					listaDetalles.removeAll(listaDetallesConsulta);
				}

				if (accionModulo.equals(accionCapturar)) {
					if (tamañoDetalles == 0) {
						bandera = true;
					}

				}

				if (accionModulo.equals(accionConsultar)
						|| accionModulo.equals(accionModificar)) {
					if (listaDetallesConsulta.size() > 0) {
						bandera = true;
					}
				}

				return bandera;
			}

			/**
			 * Catálogo de cargo de responsable
			 */
			if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {

				List<DTOCCargoResponsable> listValida = new ArrayList<DTOCCargoResponsable>();

				int tamañoDetalles = listaDetalles.size() - 1;
				for (int i = 0; i < tamañoDetalles; i++) {

					listValida = bsdConfiguracionCatalogos
							.getCCargoResponsable(listaDetalles.get(i)
									.getIdProcesoElectoral(), listaDetalles
									.get(i).getIdDetalleProceso());

					if (listValida.size() > 0) {
						listaDetallesConsulta.add(listaDetalles.get(i));
						estaGuardada = true;
					}

				}

				if (estaGuardada) {
					listaDetalles.removeAll(listaDetallesConsulta);
				}

				if (accionModulo.equals(accionCapturar)) {
					if (tamañoDetalles == 0) {
						bandera = true;
					}

				}

				if (accionModulo.equals(accionConsultar)
						|| accionModulo.equals(accionModificar)) {
					if (listaDetallesConsulta.size() > 0) {
						bandera = true;
					}
				}

				return bandera;
			}

			/**
			 * Catálogo de escolaridades
			 */
			if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {

				List<DTOCEscolaridades> listValida = new ArrayList<DTOCEscolaridades>();

				int tamañoDetalles = listaDetalles.size() - 1;
				for (int i = 0; i < tamañoDetalles; i++) {

					listValida = bsdConfiguracionCatalogos.getCEscolaridades(
							listaDetalles.get(i).getIdProcesoElectoral(),
							listaDetalles.get(i).getIdDetalleProceso());

					if (listValida.size() > 0) {
						listaDetallesConsulta.add(listaDetalles.get(i));
						estaGuardada = true;
					}

				}

				if (estaGuardada) {
					listaDetalles.removeAll(listaDetallesConsulta);
				}

				if (accionModulo.equals(accionCapturar)) {
					if (tamañoDetalles == 0) {
						bandera = true;
					}

				}

				if (accionModulo.equals(accionConsultar)
						|| accionModulo.equals(accionModificar)) {
					if (listaDetallesConsulta.size() > 0) {
						bandera = true;
					}
				}

				return bandera;
			}

			/**
			 * Catálogo de justificaciones
			 */
			if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {

				List<DTOCJustificaciones> listValida = new ArrayList<DTOCJustificaciones>();

				int tamañoDetalles = listaDetalles.size() - 1;
				for (int i = 0; i < tamañoDetalles; i++) {
					// TODO AQUI TAMBIEN
					listValida = bsdConfiguracionCatalogos.getCJustificaciones(
							listaDetalles.get(i).getIdProcesoElectoral(),
							listaDetalles.get(i).getIdDetalleProceso());

					// listValida =
					// bsdConfiguracionCatalogos.getCJustificaciones(
					// 9, 21);

					if (listValida.size() > 0) {
						listaDetallesConsulta.add(listaDetalles.get(i));
						estaGuardada = true;
					}

				}

				if (estaGuardada) {
					listaDetalles.removeAll(listaDetallesConsulta);
				}

				if (accionModulo.equals(accionCapturar)) {
					if (tamañoDetalles == 0) {
						bandera = true;
					}

				}

				if (accionModulo.equals(accionConsultar)
						|| accionModulo.equals(accionModificar)) {
					if (listaDetallesConsulta.size() > 0) {
						bandera = true;
					}
				}

				return bandera;
			}

			/**
			 * Catálogo de evaluaciones
			 */
			if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {

				List<DTOCEvaluacion> listValida = new ArrayList<DTOCEvaluacion>();

				int tamañoDetalles = listaDetalles.size() - 1;
				for (int i = 0; i < tamañoDetalles; i++) {
					// TODO AQUI TAMBIEN
//					listValida = bsdConfiguracionCatalogos.getCEvaluaciones(
//							listaDetalles.get(i).getIdProcesoElectoral(),
//							listaDetalles.get(i).getIdDetalleProceso());

					listValida = bsdConfiguracionCatalogos.getCEvaluaciones(9,
							38);

					if (listValida.size() > 0) {
						listaDetallesConsulta.add(listaDetalles.get(i));
						estaGuardada = true;
					}

				}

				if (estaGuardada) {
					listaDetalles.removeAll(listaDetallesConsulta);
				}

				if (accionModulo.equals(accionCapturar)) {
					if (tamañoDetalles == 0) {
						bandera = true;
					}

				}

				if (accionModulo.equals(accionConsultar)
						|| accionModulo.equals(accionModificar)) {
					if (listaDetallesConsulta.size() > 0) {
						bandera = true;
					}
				}

				return bandera;
			}

		} catch (Exception e) {

		}
		return bandera;
	}

	// }

	/**
	 * Método para agregar un nuevo registro en los catálogos
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void agregaRegistro() {
		// Variable para mostrar el div del mensaje
		muestraMensaje = false;
		// Variable para detectar sí la nueva acción es igual a una ya
		// seleccionada de la carga inicial
		boolean sonIguales = false;
		// Variables de si faltan datos por capturar
		boolean faltanDatos = false;
		// Variable para las evaluaciones
		boolean noEsAcreditada = false;

		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			log.info("PROCESO: " + procesoDetalle.getIdProcesoElectoral());
			log.info("DETALLE: " + procesoDetalle.getIdDetalleProceso());
			// Validamso si el registro capturado es diferente de null
			if (accionNombre != null && !accionNombre.isEmpty()) {

				accionesSeleccionadasLista.addAll(accionesPredefinidas);
				accionesSeleccionadasLista.addAll(nuevasAcciones);

				// Recorremos la lista de los registros seleccionados de la
				// nueva Tabla en vista
				if (accionesSeleccionadasLista != null
						&& !accionesSeleccionadasLista.isEmpty()) {
					for (DTOCAcciones acc : accionesSeleccionadasLista) {

						String cInicialNAccionNorm = Normalizer.normalize(
								acc.getNombre(), Normalizer.Form.NFD);
						String cNombreAccion = cInicialNAccionNorm.replaceAll(
								"[^\\p{ASCII}]", "");

						String nombreAccionNuevoNorm = Normalizer.normalize(
								accionNombre, Normalizer.Form.NFD);
						String nombreAccionNuevo = nombreAccionNuevoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						// Validamos si son iguales
						// if (acc.getNombre().equals(accionNombre)) {
						if (cNombreAccion.equals(nombreAccionNuevo)) {
							// Inicializamos el registro
							accionNombre = null;
							// Cambiamos la bandera a true en caso de cumplir la
							// condición
							sonIguales = true;
							// Salimos del ciclo para reducir el procesamiento
							break;
						}
					}

				}

				// Validamos si no son iguales para agregar el nuevo registro
				if (!sonIguales) {
					// Se crea un objeto del tipo del catálogo para ser
					// registrado
					nuevaAccion = new DTOCAcciones();
					// Se setea el campo al objeto
					nuevaAccion.setNombre(accionNombre);
					// Validamos que la lista de los registros seleccionados no
					// este vacia para poder agregar directamente el objeto a
					// registrar
					if (nuevasAcciones != null && !nuevasAcciones.isEmpty()) {
						// Agregamos el nuevo registro a los ya seleccionados o
						// agregados
						nuevasAcciones.add(nuevaAccion);
						nuevasAccionesSeleccionadas.add(nuevaAccion);
						cargaSeleccionada = true;
						// accionesSeleccionadasLista.add(nuevaAccion);
					} else {
						// Para guardar en primer plano si no se selecciono nada
						// de la carga inicial
						// accionesSeleccionadasLista = new
						// ArrayList<DTOCAcciones>();
						nuevasAcciones = new ArrayList<DTOCAcciones>();
						nuevasAccionesSeleccionadas = new ArrayList<DTOCAcciones>();
						// Agregamos por primera vez un registro para que la
						// lista se construya
						nuevasAcciones.add(nuevaAccion);
						nuevasAccionesSeleccionadas.add(nuevaAccion);
						cargaSeleccionada = true;
						// accionesSeleccionadasLista.add(nuevaAccion);

					}
					// Inicializamos los registros
					accionNombre = null;
				}

			} else {
				faltanDatos = true;
			}
		}

		/**
		 * Catálogo de cargo responsable
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			// validamos que sus registros no sean nulos
			if (descripcionCargo != null && !descripcionCargo.isEmpty()
					&& inicialesCargo != null && !inicialesCargo.isEmpty()
					&& origenCargo != null && !origenCargo.equals("")
					&& idCargoAlterno != null && !idCargoAlterno.equals("")) {

				cargosSeleccionadosLista.addAll(cargosPredefinidos);
				cargosSeleccionadosLista.addAll(nuevosCargos);

				// Recorremos la lista de los registros seleccionados de la
				// nueva Tabla en vista
				if (cargosSeleccionadosLista != null
						&& !cargosSeleccionadosLista.isEmpty()) {
					for (DTOCCargoResponsable cargo : cargosSeleccionadosLista) {

						String cInicialNCargoNorm = Normalizer.normalize(
								cargo.getDescripcion(), Normalizer.Form.NFD);
						String cDescripcionCargo = cInicialNCargoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						String descripcionCargoNuevoNorm = Normalizer
								.normalize(descripcionCargo,
										Normalizer.Form.NFD);
						String descripcionCargoNuevo = descripcionCargoNuevoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						// Validamos si son iguales
						if (cDescripcionCargo.equals(descripcionCargoNuevo)) {
							// Inicializamos el registro
							descripcionCargo = null;
							inicialesCargo = null;
							origenCargo = null;
							idCargoAlterno = null;
							// Cambiamos la bandera a true en caso de cumplir la
							// condición
							sonIguales = true;
							// Salimos del ciclo para reducir el procesamiento
							break;
						}
					}

				}

				// Validamos si no son iguales para agregar el nuevo registro
				if (!sonIguales) {
					// Se crea un objeto del tipo del catálogo para ser
					// registrado
					nuevoCargo = new DTOCCargoResponsable();
					// Se setea los campos al objeto
					nuevoCargo.setDescripcion(descripcionCargo);
					nuevoCargo.setIniciales(inicialesCargo);
					nuevoCargo.setOrigen(Integer.parseInt(origenCargo));
					nuevoCargo.setIdCargoAlterno(Integer
							.parseInt(idCargoAlterno));
					// Validamos que la lista de los registros seleccionados no
					// este vacia para poder agregar directamente el objeto a
					// registrar
					if (nuevosCargos != null && !nuevosCargos.isEmpty()) {
						// Agregamos el nuevo registro a los ya seleccionados o
						// agregados
						nuevosCargos.add(nuevoCargo);
						nuevosCargosSeleccionados.add(nuevoCargo);
						cargaSeleccionada = true;
					} else {
						// Para guardar en primer plano si no se selecciono nada
						// de la carga inicial
						nuevosCargos = new ArrayList<DTOCCargoResponsable>();
						nuevosCargosSeleccionados = new ArrayList<DTOCCargoResponsable>();
						// Agregamos por primera vez un registro para que la
						// lista se construya
						nuevosCargos.add(nuevoCargo);
						nuevosCargosSeleccionados.add(nuevoCargo);
						cargaSeleccionada = true;

					}
					// Inicializamos los registros
					descripcionCargo = null;
					inicialesCargo = null;
					origenCargo = null;
					idCargoAlterno = null;
				}

			} else {
				faltanDatos = true;
			}
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			// Validamso si el registro capturado es diferente de null
			if (descripcionEscolaridad != null
					&& !descripcionEscolaridad.isEmpty()) {

				escolaridadesSeleccionadasLista
						.addAll(escolaridadesPredefinidas);
				escolaridadesSeleccionadasLista.addAll(nuevasEscolaridades);

				// Recorremos la lista de los registros seleccionados de la
				// nueva Tabla en vista
				if (escolaridadesSeleccionadasLista != null
						&& !escolaridadesSeleccionadasLista.isEmpty()) {
					for (DTOCEscolaridades escolaridad : escolaridadesSeleccionadasLista) {

						String cInicialNEscolarNorm = Normalizer.normalize(
								escolaridad.getDescripcion(),
								Normalizer.Form.NFD);
						String cNombreEscolar = cInicialNEscolarNorm
								.replaceAll("[^\\p{ASCII}]", "");

						String descripcionEscolarNuevoNorm = Normalizer
								.normalize(descripcionEscolaridad,
										Normalizer.Form.NFD);
						String descripcionEscolarNuevo = descripcionEscolarNuevoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						// Validamos si son iguales
						if (cNombreEscolar.equals(descripcionEscolarNuevo)) {
							// Inicializamos el registro
							descripcionEscolaridad = null;
							// Cambiamos la bandera a true en caso de cumplir la
							// condición
							sonIguales = true;
							// Salimos del ciclo para reducir el procesamiento
							break;
						}
					}

				}

				// Validamos si no son iguales para agregar el nuevo registro
				if (!sonIguales) {
					// Se crea un objeto del tipo del catálogo para ser
					// registrado
					nuevaEscolaridad = new DTOCEscolaridades();
					// Se setea el campo al objeto
					nuevaEscolaridad.setDescripcion(descripcionEscolaridad);
					// Validamos que la lista de los registros seleccionados no
					// este vacia para poder agregar directamente el objeto a
					// registrar
					if (nuevasEscolaridades != null
							&& !nuevasEscolaridades.isEmpty()) {
						// Agregamos el nuevo registro a los ya seleccionados o
						// agregados
						nuevasEscolaridades.add(nuevaEscolaridad);
						nuevasEscolaridadesSeleccionadas.add(nuevaEscolaridad);
						// escolaridadesSeleccionadasLista =
						// ordenaEscolaridad(escolaridadesSeleccionadasLista);
						cargaSeleccionada = true;
					} else {
						// Para guardar en primer plano si no se selecciono nada
						// de la carga inicial
						nuevasEscolaridades = new ArrayList<DTOCEscolaridades>();
						nuevasEscolaridadesSeleccionadas = new ArrayList<DTOCEscolaridades>();
						// Agregamos por primera vez un registro para que la
						// lista se construya
						nuevasEscolaridades.add(nuevaEscolaridad);
						nuevasEscolaridadesSeleccionadas.add(nuevaEscolaridad);
						cargaSeleccionada = true;

					}
					// Inicializamos los registros
					descripcionEscolaridad = null;
				}

			} else {
				faltanDatos = true;
			}
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			// Validamso si el registro capturado es diferente de null
			if (descripcionJustificacion != null
					&& !descripcionJustificacion.isEmpty()
					&& descripcionCortaJustificacion != null
					&& !descripcionCortaJustificacion.isEmpty()
					&& resultadoJustificacion != null
					&& !resultadoJustificacion.isEmpty()) {

				log.info("VALOR DE RESULTADO EN JUSTIFICACION: " + "|"
						+ resultadoJustificacion + "|");

				justificacionesSeleccionadasLista
						.addAll(justificacionesPredefinidas);
				justificacionesSeleccionadasLista.addAll(nuevasJustificaciones);
				justificacionesSeleccionadasLista
						.addAll(justificacionesXDefault);

				// Recorremos la lista de los registros seleccionados de la
				// nueva Tabla en vista
				if (justificacionesSeleccionadasLista != null
						&& !justificacionesSeleccionadasLista.isEmpty()) {
					for (DTOCJustificaciones justificacion : justificacionesSeleccionadasLista) {

						String cInicialNJustificacionNorm = Normalizer
								.normalize(justificacion.getDescripcion(),
										Normalizer.Form.NFD);
						String cDescripcionJustificacion = cInicialNJustificacionNorm
								.replaceAll("[^\\p{ASCII}]", "");

						String descripcionJustificacionNuevoNorm = Normalizer
								.normalize(descripcionJustificacion,
										Normalizer.Form.NFD);
						String descripcionJustificacionNuevo = descripcionJustificacionNuevoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						// Validamos si son iguales
						if (cDescripcionJustificacion
								.equals(descripcionJustificacionNuevo)) {
							// Inicializamos el registro
							descripcionJustificacion = null;
							descripcionCortaJustificacion = null;
							resultadoJustificacion = null;
							// Cambiamos la bandera a true en caso de
							// cumplir la
							// condición
							sonIguales = true;
							// Salimos del ciclo para reducir el
							// procesamiento
							break;
						}
					}

				}

				// Validamos si no son iguales para agregar el nuevo
				// registro
				if (!sonIguales) {
					// Se crea un objeto del tipo del catálogo para ser
					// registrado
					nuevaJustificacion = new DTOCJustificaciones();
					// Se setea el campo al objeto
					nuevaJustificacion.setDescripcion(descripcionJustificacion);
					nuevaJustificacion
							.setDescripcionCorta(descripcionCortaJustificacion);
					nuevaJustificacion.setResultado(resultadoJustificacion
							.charAt(0));
					// Validamos que la lista de los registros seleccionados
					// no
					// este vacia para poder agregar directamente el objeto
					// a
					// registrar
					if (nuevasJustificaciones != null
							&& !nuevasJustificaciones.isEmpty()) {
						// Agregamos el nuevo registro a los ya
						// seleccionados o
						// agregados
						nuevasJustificaciones.add(nuevaJustificacion);

						// Ordenamos las nuevas justificaciones agregadas
						nuevasJustificaciones = ordenaJustificacion(nuevasJustificaciones);

						nuevasJustificacionesSeleccionadas
								.add(nuevaJustificacion);
						cargaSeleccionada = true;
					} else {
						// Para guardar en primer plano si no se selecciono
						// nada
						// de la carga inicial
						nuevasJustificaciones = new ArrayList<DTOCJustificaciones>();
						nuevasJustificacionesSeleccionadas = new ArrayList<DTOCJustificaciones>();
						// Agregamos por primera vez un registro para que la
						// lista se construya
						nuevasJustificaciones.add(nuevaJustificacion);
						nuevasJustificacionesSeleccionadas
								.add(nuevaJustificacion);
						cargaSeleccionada = true;

					}
					// Inicializamos los registros
					descripcionJustificacion = null;
					descripcionCortaJustificacion = null;
					resultadoJustificacion = null;
				}

			} else {
				faltanDatos = true;
			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			// Validamso si el registro capturado es diferente de null
			if (descripcionEvaluacion != null
					&& !descripcionEvaluacion.isEmpty()
					&& tipoEvaluacion != null && !tipoEvaluacion.isEmpty()) {

				evaluacionesSeleccionadasLista.addAll(evaluacionesPredefinidas);
				evaluacionesSeleccionadasLista.addAll(nuevasEvaluaciones);
				if (numeroCurso != null) {
					origenCurso = numeroCurso;
				}
				// Recorremos la lista de los registros seleccionados de la
				// nueva Tabla en vista
				if (evaluacionesSeleccionadasLista != null
						&& !evaluacionesSeleccionadasLista.isEmpty()) {
					for (DTOCEvaluacion evaluacion : evaluacionesSeleccionadasLista) {

						String cInicialNEvaluacionNorm = Normalizer.normalize(
								evaluacion.getDescripcion(),
								Normalizer.Form.NFD);
						String cDescripcionEvaluacion = cInicialNEvaluacionNorm
								.replaceAll("[^\\p{ASCII}]", "");

						String descripcionEvaluacionNuevoNorm = Normalizer
								.normalize(descripcionEvaluacion,
										Normalizer.Form.NFD);
						String descripcionEvaluacionNuevo = descripcionEvaluacionNuevoNorm
								.replaceAll("[^\\p{ASCII}]", "");

						// Validamos si son iguales
						if (cDescripcionEvaluacion
								.equals(descripcionEvaluacionNuevo)) {
							// Inicializamos el registro
							descripcionEvaluacion = null;
							tipoEvaluacion = null;
							// Cambiamos la bandera a true en caso de
							// cumplir la
							// condición
							sonIguales = true;
							// Salimos del ciclo para reducir el
							// procesamiento
							break;
						}

						String cEvaluacionNAInicial = Normalizer.normalize(
								Constantes.EVALUACION_POR_DEFECTO_NA,
								Normalizer.Form.NFD);
						String cEvaluacionNA = cEvaluacionNAInicial.replaceAll(
								"[^\\p{ASCII}]", "");

						String cEvaluacionPInicial = Normalizer.normalize(
								Constantes.EVALUACION_POR_DEFECTO_P,
								Normalizer.Form.NFD);
						String cEvaluacionP = cEvaluacionPInicial.replaceAll(
								"[^\\p{ASCII}]", "");

						if (descripcionEvaluacionNuevo.contains(cEvaluacionNA)
								|| descripcionEvaluacionNuevo
										.contains(cEvaluacionP)) {
							descripcionEvaluacion = null;
							tipoEvaluacion = null;
							noEsAcreditada = true;
							break;
						}
					}

				}

				// Validamos si no son iguales para agregar el nuevo
				// registro
				if (!sonIguales && !noEsAcreditada) {
					// Se crea un objeto del tipo del catálogo para ser
					// registrado
					nuevaEvaluacion = new DTOCEvaluacion();

					DTOCEvaluacionPK pk = new DTOCEvaluacionPK(
							procesoDetalle.getIdProcesoElectoral(),
							procesoDetalle.getIdDetalleProceso(),
							contadorIdSiguiente);
					nuevaEvaluacion.setDTOCEvaluacionPK(pk);
					nuevaEvaluacion.setDescripcion(descripcionEvaluacion);
					nuevaEvaluacion.setTipo(tipoEvaluacion.charAt(0));

					List<DTOReglasEvalucaion> guardarReglas = new ArrayList<DTOReglasEvalucaion>();
					for (DTOReglas regla : reglasSeleccionadas) {
						DTOReglasEvalucaion saveRule = new DTOReglasEvalucaion();
						DTOReglasEvalucaionPK PKreglaAGuardar = new DTOReglasEvalucaionPK(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso(),
								contadorIdSiguiente, regla.getIdRegla());

						saveRule.setDTOReglasEvalucaionPK(PKreglaAGuardar);

						if (regla.getIdRegla()
								.equals(Constantes.ID_REGLA_CURSO)) {
							if (origenCurso != null) {
								saveRule.setOrigenCurso(origenCurso);
							}
						}

						guardarReglas.add(saveRule);

					}
					nuevaEvaluacion.setDTOReglasEvalucaionList(guardarReglas);

					for (DTOReglasEvalucaion evaRegla : nuevaEvaluacion
							.getDTOReglasEvalucaionList()) {
						log.info("ID_PROCESO: "
								+ evaRegla.getDTOReglasEvalucaionPK()
										.getIdProcesoElectoral());
						log.info("ID_DETALLE: "
								+ evaRegla.getDTOReglasEvalucaionPK()
										.getIdDetalleProceso());
						log.info("ID_EVALUACION: "
								+ evaRegla.getDTOReglasEvalucaionPK()
										.getIdEvaluacion());
						log.info("ID_REGLA: "
								+ evaRegla.getDTOReglasEvalucaionPK()
										.getIdRegla());
					}

					contadorIdSiguiente++;

					if (nuevasEvaluaciones != null
							&& !nuevasEvaluaciones.isEmpty()) {
						// Agregamos el nuevo registro a los ya
						// seleccionados o
						// agregados
						nuevasEvaluaciones.add(nuevaEvaluacion);
						nuevasEvaluacionesSeleccionadas.add(nuevaEvaluacion);
						cargaSeleccionada = true;
					} else {
						// Para guardar en primer plano si no se selecciono
						// nada
						// de la carga inicial
						nuevasEvaluaciones = new ArrayList<DTOCEvaluacion>();
						nuevasEvaluacionesSeleccionadas = new ArrayList<DTOCEvaluacion>();
						// Agregamos por primera vez un registro para que la
						// lista se construya
						nuevasEvaluaciones.add(nuevaEvaluacion);
						nuevasEvaluacionesSeleccionadas.add(nuevaEvaluacion);
						cargaSeleccionada = true;

					}
					// Inicializamos los registros
					descripcionEvaluacion = null;
					tipoEvaluacion = null;
					reglasSeleccionadas = new ArrayList<DTOReglas>();
					esCurso = false;
					// Origen curso...
					this.origenCurso = null;
					this.numeroCurso = null;
				}

			} else {
				faltanDatos = true;
			}
		}

		/**
		 * Mensaje si el registro a ingresar es igual
		 */
		if (sonIguales && !noEsAcreditada) {
			muestraMensaje = true;
			scrollTop();
			FacesMessage msjRegistroIgual = new FacesMessage(
					Utilidades
							.mensajeProperties("mensaje_registro_insertado_igual_seleccionado"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjRegistroIgual);
		}

		/**
		 * Mensaje si el registro en evaluaciones no es acreditado
		 */
		if (!sonIguales && noEsAcreditada) {
			muestraMensaje = true;
			scrollTop();
			FacesMessage msjRegistroNoAcreditado = new FacesMessage(
					Utilidades
							.mensajeProperties("mensaje_registro_insertado_no_acreditado"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjRegistroNoAcreditado);
		}

		/**
		 * Mensaje si son iguales y ademas si la evaluacion no es acreditadas
		 */
		if (sonIguales && noEsAcreditada) {
			muestraMensaje = true;
			scrollTop();
			FacesMessage msjRegistroIgualYNoAcreditado = new FacesMessage(
					Utilidades
							.mensajeProperties("mensaje_registro_igual_seleccionado_insertado_no_acreditado"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjRegistroIgualYNoAcreditado);
		}

		/**
		 * Mensjae si faltan datos en el guardado
		 */
		if (faltanDatos) {
			muestraForm = true;
			muestraMensaje = true;
			scrollTop();
			FacesMessage msjErrorAlGuardarFaltandDatos = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_guardarFallo_faltan_datos"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjErrorAlGuardarFaltandDatos);
		}

	}

	/**
	 * Cambiar la bandera para que el botón de eliminado se desbloque...
	 * 
	 * @author Gerardo López
	 * @since 03/07/2017
	 * 
	 */
	// public void btnElimina() {
	// disableBtnElimina = true;
	// }

	/**
	 * Método que elimina una acción o varias acciones seleccionadas al
	 * presionar el botón de eliminado la vista de captura
	 * 
	 * @author Gerardo López
	 * @since 29/06/2017
	 * 
	 */
	public void eliminaSeleccionadas() {
		boolean esAgregada = false;
		/**
		 * Catálogo de acciones
		 */
		// if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
		// if (nuevasAccionesSeleccionadas.size() > 0) {
		//
		// // Asignamos las acciones seleccionadas a la lista temporal para
		// // poderlas quitar de la selección del catálogo inicial
		// nuevasAccionesSeleccionadasTmp
		// .addAll(nuevasAccionesSeleccionadas);
		//
		// int indexAction = 0;
		// for (DTOCAcciones eliminaNuevas : nuevasAccionesSeleccionadas) {
		// for (int i = 0; i < nuevasAcciones.size(); i++) {
		// if (eliminaNuevas.getNombre().equals(
		// nuevasAcciones.get(i).getNombre())) {
		// indexAction = i;
		// esAgregada = true;
		// }
		// }
		// }
		//
		// if (esAgregada) {
		// nuevasAcciones.remove(indexAction);
		//
		// accionesSeleccionadasLista
		// .removeAll(nuevasAccionesSeleccionadas);
		//
		// if (nuevasAcciones != null && !nuevasAcciones.isEmpty()) {
		// accionesSeleccionadasTmp = new ArrayList<DTOCAcciones>();
		// accionesSeleccionadasTmp
		// .addAll(accionesSeleccionadasLista);
		// accionesSeleccionadasLista = new ArrayList<DTOCAcciones>();
		// accionesSeleccionadasLista.addAll(nuevasAcciones);
		// accionesSeleccionadasLista
		// .addAll(accionesSeleccionadasTmp);
		// }
		//
		// } else {
		// accionesSeleccionadasLista
		// .removeAll(nuevasAccionesSeleccionadas);
		// }
		//
		// }
		//
		// }

		/**
		 * Catálogo de Cargo de responsables
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			if (nuevosCargosSeleccionados.size() > 0) {

				// Asignamos los cargos seleccionados a la lista temporal para
				// poderlos quitar de la selección de catálogo inicial
				nuevosCargosSeleccionadosTmp.addAll(nuevosCargosSeleccionados);

				int indexCargo = 0;
				for (DTOCCargoResponsable eliminaNuevos : nuevosCargosSeleccionados) {
					for (int i = 0; i < nuevosCargos.size(); i++) {
						if (eliminaNuevos.getDescripcion().equals(
								nuevosCargos.get(i).getDescripcion())) {
							indexCargo = i;
							esAgregada = true;
						}
					}

				}

				if (esAgregada) {
					nuevosCargos.remove(indexCargo);

					cargosSeleccionadosLista
							.removeAll(nuevosCargosSeleccionados);

					if (nuevosCargos != null && !nuevosCargos.isEmpty()) {
						cargosSeleccionadosTmp = new ArrayList<DTOCCargoResponsable>();
						cargosSeleccionadosTmp.addAll(cargosSeleccionadosLista);
						cargosSeleccionadosLista = new ArrayList<DTOCCargoResponsable>();
						cargosSeleccionadosLista.addAll(nuevosCargos);
						cargosSeleccionadosLista.addAll(cargosSeleccionadosTmp);
					}
				} else {
					// Quitamos de la lista seleccionada por el usuario que se
					// ve
					// reflejado en la vista
					cargosSeleccionadosLista
							.removeAll(nuevosCargosSeleccionados);
				}
			}
		}

		/**
		 * Catálogo de Escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			if (nuevasEscolaridadesSeleccionadas.size() > 0) {

				// Asignamos los cargos seleccionados a la lista temporal para
				// poderlos quitar de la selección de catálogo inicial
				nuevasEscolaridadesSeleccionadasTmp
						.addAll(nuevasEscolaridadesSeleccionadas);

				int indexEscolaridad = 0;
				for (DTOCEscolaridades eliminaNuevas : nuevasEscolaridadesSeleccionadas) {
					for (int i = 0; i < nuevasEscolaridades.size(); i++) {
						if (eliminaNuevas.getDescripcion().equals(
								nuevasEscolaridades.get(i).getDescripcion())) {
							indexEscolaridad = i;
							esAgregada = true;
						}
					}
				}

				if (esAgregada) {
					nuevasEscolaridades.remove(indexEscolaridad);

					escolaridadesSeleccionadasLista
							.removeAll(nuevasEscolaridadesSeleccionadas);

					if (nuevasEscolaridades != null
							&& !nuevasEscolaridades.isEmpty()) {
						escolaridadesSeleccionadasTmp = new ArrayList<DTOCEscolaridades>();
						escolaridadesSeleccionadasTmp
								.addAll(escolaridadesSeleccionadasLista);
						escolaridadesSeleccionadasLista = new ArrayList<DTOCEscolaridades>();
						escolaridadesSeleccionadasLista
								.addAll(nuevasEscolaridades);
						escolaridadesSeleccionadasLista
								.addAll(escolaridadesSeleccionadasTmp);
					}
				} else {
					// Quitamos de la lista seleccionada por el usuario que se
					// ve
					// reflejado en la vista
					escolaridadesSeleccionadasLista
							.removeAll(nuevasEscolaridadesSeleccionadas);
				}

			}
		}

		/**
		 * Catálogo de Justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			if (nuevasJustificacionesSeleccionadas.size() > 0) {

				// Asignamos las justificaciones seleccionadas a la lista
				// temporal para
				// poderlos quitar de la selección de catálogo inicial
				nuevasJustificacionesSeleccionadasTmp
						.addAll(nuevasJustificacionesSeleccionadas);

				int indexJustificacion = 0;
				for (DTOCJustificaciones eliminaNuevas : nuevasJustificacionesSeleccionadas) {
					for (int i = 0; i < nuevasJustificaciones.size(); i++) {
						if (eliminaNuevas.getDescripcion().equals(
								nuevasJustificaciones.get(i).getDescripcion())) {
							indexJustificacion = i;
							esAgregada = true;
						}
					}
				}

				if (esAgregada) {
					nuevasJustificaciones.remove(indexJustificacion);

					justificacionesSeleccionadasLista
							.removeAll(nuevasJustificacionesSeleccionadas);

					if (nuevasJustificaciones != null
							&& !nuevasJustificaciones.isEmpty()) {
						justificacionesSeleccionadasTmp = new ArrayList<DTOCJustificaciones>();
						justificacionesSeleccionadasTmp
								.addAll(justificacionesSeleccionadas);
						justificacionesSeleccionadasLista = new ArrayList<DTOCJustificaciones>();
						justificacionesSeleccionadasLista
								.addAll(nuevasJustificaciones);
						justificacionesSeleccionadasLista
								.addAll(justificacionesSeleccionadasTmp);
					}
				}

				// Quitamos de la lista seleccionada por el usuario que se ve
				// reflejado en la vista, excepto la justificacion por defecto
				for (DTOCJustificaciones justificacionElimina : nuevasJustificacionesSeleccionadas) {
					if (!justificacionElimina.equals(justificacionPorDefecto)) {
						justificacionesSeleccionadasLista
								.remove(justificacionElimina);
					}
				}
			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			if (nuevasEvaluacionesSeleccionadas.size() > 0) {

				// Asignamos las justificaciones seleccionadas a la lista
				// temporal para
				// poderlos quitar de la selección de catálogo inicial
				nuevasEvaluacionesSeleccionadasTmp
						.addAll(nuevasEvaluacionesSeleccionadas);

				int indexEvaluacion = 0;
				for (DTOCEvaluacion eliminaNuevas : nuevasEvaluacionesSeleccionadas) {
					for (int i = 0; i < nuevasEvaluaciones.size(); i++) {
						if (eliminaNuevas.getDescripcion().equals(
								nuevasEvaluaciones.get(i).getDescripcion())) {
							indexEvaluacion = i;
							esAgregada = true;
						}
					}
				}

				if (esAgregada) {
					nuevasEvaluaciones.remove(indexEvaluacion);

					evaluacionesSeleccionadasLista
							.removeAll(nuevasEvaluacionesSeleccionadas);

					if (nuevasEvaluaciones != null
							&& !nuevasEvaluaciones.isEmpty()) {
						evaluacionesSeleccionadasTmp = new ArrayList<DTOCEvaluacion>();
						evaluacionesSeleccionadasTmp
								.addAll(evaluacionesSeleccionadas);
						evaluacionesSeleccionadasLista = new ArrayList<DTOCEvaluacion>();
						evaluacionesSeleccionadasLista
								.addAll(nuevasEvaluaciones);
						evaluacionesSeleccionadasLista
								.addAll(evaluacionesSeleccionadasTmp);
					}
				}

				// Quitamos de la lista seleccionada por el usuario que se ve
				// reflejado en la vista, excepto la justificacion por defecto
				for (DTOCEvaluacion evaluacionElimina : nuevasEvaluacionesSeleccionadas) {
					for (int i = 0; i < evaluacionesPorDefecto.size(); i++) {
						if (!evaluacionElimina.equals(evaluacionesPorDefecto
								.get(i))) {
							evaluacionesSeleccionadasLista
									.remove(evaluacionElimina);
						}
					}
				}
			}
		}

		// Volvemos a poner el boton de elimina en disable
		disableBtnElimina = false;
	}

	/**
	 * Método que guarda los catálogos en BD con el proceso y detalle
	 * correspondiente
	 * 
	 * @author Gerardo López
	 * @since 03/07/2017
	 * 
	 */
	public void guardaCatalogos() {
		boolean guardadoExitoso = false;
		boolean faltanDatos = false;
		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			DTOCAcciones accionAGuardar = new DTOCAcciones();
			int i = 0;
			try {

				if (accionModulo.equals(accionModificar)) {
					accionesAEliminar = new ArrayList<DTOCAcciones>();
					accionesAEliminar = accionesPredefinidas;

					// Eliminar acciones seleccionadas en la vista de modificar
					if (accionesAEliminar != null
							&& !accionesAEliminar.isEmpty()) {
						// Eliminando acciones
						bsdConfiguracionCatalogos
								.eliminaCAcciones(accionesAEliminar);
					}
				}

				accionesAGuardar = new ArrayList<DTOCAcciones>();
				if (accionesSeleccionadas != null
						&& !accionesSeleccionadas.isEmpty()) {
					accionesAGuardar.addAll(accionesSeleccionadas);
				}
				if (nuevasAccionesSeleccionadas != null
						&& !nuevasAccionesSeleccionadas.isEmpty()) {
					accionesAGuardar.addAll(nuevasAccionesSeleccionadas);
				}

				if (accionesAGuardar != null && !accionesAGuardar.isEmpty()) {
					// Ordenamos en orden alfacetico por nombre la lista a
					// guardar
					// de Acciones
					Collections.sort(accionesAGuardar,
							new Comparator<DTOCAcciones>() {

								@Override
								public int compare(DTOCAcciones o1,
										DTOCAcciones o2) {
									return o1.getNombre().compareTo(
											o2.getNombre());
								}
							});
					if (procesoDetalle != null) {
						// Si son todos los catálogos
						if (sonTodos) {
							for (int j = 0; j < listaDetalles.size() - 1; j++) {
								int k = 0;
								for (DTOCAcciones accion : accionesAGuardar) {
									// Guardamos las acciones con el idProceso y
									// idDetalle de cada uno de la lista
									DTOCAccionesPK pkAGuardar = new DTOCAccionesPK(
											listaDetalles.get(j)
													.getIdProcesoElectoral(),
											listaDetalles.get(j)
													.getIdDetalleProceso(),
											k + 1);

									accionAGuardar
											.setdTOCAccionesPK(pkAGuardar);
									accionAGuardar
											.setNombre(accion.getNombre());
									log.info("ANTES DE GUARDAR");
									bsdConfiguracionCatalogos
											.guardaActualizaCAcciones(accionAGuardar);
									guardadoExitoso = true;
									log.info("GUARDO " + accion);
									k++;
								}
							}

						} else {
							for (DTOCAcciones accion : accionesAGuardar) {
								// DTOCAccionesPK pkAGuardar = new
								// DTOCAccionesPK(
								// usuario.getIdProcesoElectoral(),
								// usuario.getIdDetalleProceso(), i + 1);
								if (accion.getNombre() != null
										&& !accion.getNombre().isEmpty()) {
									faltanDatos = false;

									// Guardamos las acciones con el idProceso y
									// idDetalle seleccionado por el usuario
									DTOCAccionesPK pkAGuardar = new DTOCAccionesPK(
											procesoDetalle
													.getIdProcesoElectoral(),
											procesoDetalle
													.getIdDetalleProceso(),
											i + 1);

									accionAGuardar
											.setdTOCAccionesPK(pkAGuardar);
									accionAGuardar
											.setNombre(accion.getNombre());
									log.info("ANTES DE GUARDAR");
									bsdConfiguracionCatalogos
											.guardaActualizaCAcciones(accionAGuardar);
									guardadoExitoso = true;
									log.info("GUARDO " + accion);
									i++;
								} else {
									faltanDatos = true;
								}
							}
							// }
						}
					}
				} else {
					// Mensaje de que no ha seleccionado ninguna acción en su
					// guardado
				}
			} catch (Exception e) {
				log.error("Error al guardar catálogo de acciones en guardaCatalogos de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				guardadoExitoso = false;
			}
		}

		/**
		 * Catálogo de cargo responsable
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			DTOCCargoResponsable cargoAGuardar = new DTOCCargoResponsable();
			int i = 0;
			try {

				// Eliminar cargos seleccionados en la vista de modificar
				if (accionModulo.equals(accionModificar)) {
					cargosAEliminar = new ArrayList<DTOCCargoResponsable>();
					cargosAEliminar = cargosPredefinidos;
					if (cargosAEliminar != null && !cargosAEliminar.isEmpty()) {
						// Eliminando acciones
						bsdConfiguracionCatalogos
								.eliminaCCargoResponsable(cargosAEliminar);
					}
				}

				cargosAGuardar = new ArrayList<DTOCCargoResponsable>();
				if (cargosSeleccionados != null
						&& !cargosSeleccionados.isEmpty()) {
					cargosAGuardar.addAll(cargosSeleccionados);
				}
				if (nuevosCargosSeleccionados != null
						&& !nuevosCargosSeleccionados.isEmpty()) {
					cargosAGuardar.addAll(nuevosCargosSeleccionados);
				}

				if (cargosAGuardar != null || !cargosAGuardar.isEmpty()) {

					// Ordenamos la lista de cargos de responsable por oden
					// alfabetico de manera asendente
					Collections.sort(cargosAGuardar,
							new Comparator<DTOCCargoResponsable>() {

								@Override
								public int compare(DTOCCargoResponsable o1,
										DTOCCargoResponsable o2) {
									return o1.getDescripcion().compareTo(
											o2.getDescripcion());
								}
							});

					if (procesoDetalle != null) {
						// Si son todos los catálogos
						if (sonTodos) {
							for (int j = 0; j < listaDetalles.size() - 1; j++) {
								int k = 0;
								for (DTOCCargoResponsable cargo : cargosAGuardar) {
									// Guardamos las acciones con el idProceso y
									// idDetalle de cada uno de la lista
									DTOCCargoResponsablePK pkAGuardar = new DTOCCargoResponsablePK(
											listaDetalles.get(j)
													.getIdProcesoElectoral(),
											listaDetalles.get(j)
													.getIdDetalleProceso(),
											k + 1);

									cargoAGuardar.setId(pkAGuardar);
									cargoAGuardar.setDescripcion(cargo
											.getDescripcion());
									cargoAGuardar.setIniciales(cargo
											.getIniciales());
									cargoAGuardar.setOrigen(cargo.getOrigen());
									cargoAGuardar.setIdCargoAlterno(cargo
											.getIdCargoAlterno());
									log.info("ANTES DE GUARDAR");
									bsdConfiguracionCatalogos
											.guardaActualizaCCargoResponsable(cargoAGuardar);
									guardadoExitoso = true;
									k++;
								}
							}

						} else {
							for (DTOCCargoResponsable cargo : cargosAGuardar) {
								// DTOCCargoResponsablePK pkAGuardar = new
								// DTOCCargoResponsablePK(
								// usuario.getIdProcesoElectoral(),
								// usuario.getIdDetalleProceso(), i + 1);
								if (cargo.getDescripcion() != null
										&& !cargo.getDescripcion().isEmpty()
										&& cargo.getIniciales() != null
										&& !cargo.getIniciales().isEmpty()
										&& cargo.getOrigen() != null
										&& !cargo.getOrigen().equals("")
										&& cargo.getIdCargoAlterno() != null
										&& !cargo.getIdCargoAlterno()
												.equals("")) {
									faltanDatos = false;
									DTOCCargoResponsablePK pkAGuardar = new DTOCCargoResponsablePK(
											procesoDetalle
													.getIdProcesoElectoral(),
											procesoDetalle
													.getIdDetalleProceso(),
											i + 1);
									cargoAGuardar.setId(pkAGuardar);
									cargoAGuardar.setDescripcion(cargo
											.getDescripcion());
									cargoAGuardar.setIniciales(cargo
											.getIniciales());
									cargoAGuardar.setOrigen(cargo.getOrigen());
									cargoAGuardar.setIdCargoAlterno(cargo
											.getIdCargoAlterno());
									bsdConfiguracionCatalogos
											.guardaActualizaCCargoResponsable(cargoAGuardar);
									guardadoExitoso = true;
									i++;
								} else {
									faltanDatos = true;
								}

							}

						}
					}

				}
			} catch (Exception e) {
				log.error("Error al guardar catálogo de cargos de responsable en guardaCatalogos de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				guardadoExitoso = false;
			}
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			DTOCEscolaridades escolaridadAGuardar = new DTOCEscolaridades();
			int i = 0;
			try {

				if (accionModulo.equals(accionModificar)) {
					escolaridadesAEliminar = new ArrayList<DTOCEscolaridades>();
					escolaridadesAEliminar = escolaridadesPredefinidas;

					// Eliminar cargos seleccionados en la vista de modificar
					if (escolaridadesAEliminar != null
							&& !escolaridadesAEliminar.isEmpty()) {
						// Eliminando acciones
						bsdConfiguracionCatalogos
								.eliminaCEscolaridades(escolaridadesAEliminar);
					}
				}

				escolaridadesAGuardar = new ArrayList<DTOCEscolaridades>();
				if (escolaridadesSeleccionadas != null
						&& !escolaridadesSeleccionadas.isEmpty()) {
					escolaridadesAGuardar.addAll(escolaridadesSeleccionadas);
				}
				if (nuevasEscolaridadesSeleccionadas != null
						&& !nuevasEscolaridadesSeleccionadas.isEmpty()) {
					escolaridadesAGuardar
							.addAll(nuevasEscolaridadesSeleccionadas);
				}

				if (escolaridadesAGuardar != null
						|| !escolaridadesAGuardar.isEmpty()) {

					// Ordenamos la lista de escolaridades de acuerdo a
					// agrupaciones: Primaria, Secundaria, Bachillerato... etc.
					escolaridadesAGuardar = ordenaEscolaridad(escolaridadesAGuardar);

					if (procesoDetalle != null) {
						// Si son todos los catálogos
						if (sonTodos) {
							for (int j = 0; j < listaDetalles.size() - 1; j++) {
								int k = 0;
								for (DTOCEscolaridades escolaridad : escolaridadesAGuardar) {
									// Guardamos las acciones con el idProceso y
									// idDetalle de cada uno de la lista
									DTOCEscolaridadesPK pkAGuardar = new DTOCEscolaridadesPK(
											listaDetalles.get(j)
													.getIdProcesoElectoral(),
											listaDetalles.get(j)
													.getIdDetalleProceso(),
											k + 1);

									escolaridadAGuardar
											.setDTOCEscolaridadesPK(pkAGuardar);
									escolaridadAGuardar
											.setDescripcion(escolaridad
													.getDescripcion());
									log.info("ANTES DE GUARDAR");
									bsdConfiguracionCatalogos
											.guardaActualizaCEscolaridades(escolaridadAGuardar);
									guardadoExitoso = true;
									k++;
								}
							}

						} else {
							for (DTOCEscolaridades escolaridad : escolaridadesAGuardar) {

								if (escolaridad.getDescripcion() != null
										&& !escolaridad.getDescripcion()
												.isEmpty()) {
									faltanDatos = false;
									DTOCEscolaridadesPK pkAGuardar = new DTOCEscolaridadesPK(
											procesoDetalle
													.getIdProcesoElectoral(),
											procesoDetalle
													.getIdDetalleProceso(),
											i + 1);
									escolaridadAGuardar
											.setDTOCEscolaridadesPK(pkAGuardar);
									escolaridadAGuardar
											.setDescripcion(escolaridad
													.getDescripcion());
									bsdConfiguracionCatalogos
											.guardaActualizaCEscolaridades(escolaridadAGuardar);
									guardadoExitoso = true;
									i++;
								} else {
									faltanDatos = true;
								}

							}

						}
					}

				}
			} catch (Exception e) {
				log.error("Error al guardar catálogo de escolaridades en guardaCatalogos de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				guardadoExitoso = false;
			}
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			DTOCJustificaciones justificacionAGuardar = new DTOCJustificaciones();
			int i = 0;
			try {

				if (accionModulo.equals(accionModificar)) {
					justificacionesAEliminar = new ArrayList<DTOCJustificaciones>();
					justificacionesAEliminar = justificacionesPredefinidas;

					// Eliminar cargos seleccionados en la vista de modificar
					if (justificacionesAEliminar != null
							&& !justificacionesAEliminar.isEmpty()) {
						// Eliminando acciones
						bsdConfiguracionCatalogos
								.eliminaCJustificaciones(justificacionesAEliminar);
					}
				}

				justificacionesAGuardar = new ArrayList<DTOCJustificaciones>();
				if (justificacionesSeleccionadas != null
						&& !justificacionesSeleccionadas.isEmpty()) {
					justificacionesAGuardar
							.addAll(justificacionesSeleccionadas);
				}
				if (nuevasJustificacionesSeleccionadas != null
						&& !nuevasJustificacionesSeleccionadas.isEmpty()) {
					justificacionesAGuardar
							.addAll(nuevasJustificacionesSeleccionadas);
				}

				// if (justificacionesXDefault != null
				// && !justificacionesXDefault.isEmpty()) {
				// justificacionesAGuardar.addAll(justificacionesXDefault);
				// }

				if (justificacionesAGuardar != null
						|| !justificacionesAGuardar.isEmpty()) {

					// Ordenamos por Resultado las justificaciones,
					// 1.- Aprobada
					// 2.- Denegada
					// 3.- Cancelada
					// 4.- Duplicada
					// Y despues por oden alfabetico
					justificacionesAGuardar = ordenaJustificacion(justificacionesAGuardar);

					if (procesoDetalle != null) {
						// Si son todos los catálogos
						if (sonTodos) {
							for (int j = 0; j < listaDetalles.size() - 1; j++) {
								int k = 0;
								for (DTOCJustificaciones justificacion : justificacionesAGuardar) {
									// Guardamos las acciones con el idProceso y
									// idDetalle de cada uno de la lista
									DTOCJustificacionesPK pkAGuardar = new DTOCJustificacionesPK(
											listaDetalles.get(j)
													.getIdProcesoElectoral(),
											listaDetalles.get(j)
													.getIdDetalleProceso(),
											k + 1);

									justificacionAGuardar
											.setDTOCJustificacionesPK(pkAGuardar);
									justificacionAGuardar
											.setDescripcion(justificacion
													.getDescripcion());
									justificacionAGuardar
											.setDescripcionCorta(justificacion
													.getDescripcionCorta());
									justificacionAGuardar
											.setResultado(justificacion
													.getResultado());

									bsdConfiguracionCatalogos
											.guardaActualizaCJustificaciones(justificacionAGuardar);
									guardadoExitoso = true;
									k++;
								}
							}

						} else {
							log.info("PROCESO EN GUARDAR: "
									+ procesoDetalle.getIdProcesoElectoral());
							log.info("DETALLE EN GUARDAR: "
									+ procesoDetalle.getIdDetalleProceso());

							// Justificacion por defecto, guardar con el ID = 0
							for (DTOCJustificaciones justificacion : justificacionesXDefault) {

								if (justificacion.getDescripcion() != null
										&& !justificacion.getDescripcion()
												.isEmpty()) {
									faltanDatos = false;
									DTOCJustificacionesPK pkAGuardar = new DTOCJustificacionesPK(
											procesoDetalle
													.getIdProcesoElectoral(),
											procesoDetalle
													.getIdDetalleProceso(),
											Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO_GUARDADO);
									justificacionAGuardar
											.setDTOCJustificacionesPK(pkAGuardar);
									justificacionAGuardar
											.setDescripcion(justificacion
													.getDescripcion());
									justificacionAGuardar
											.setDescripcionCorta(justificacion
													.getDescripcionCorta());
									justificacionAGuardar
											.setResultado(justificacion
													.getResultado());

									bsdConfiguracionCatalogos
											.guardaActualizaCJustificaciones(justificacionAGuardar);
									guardadoExitoso = true;
								} else {
									faltanDatos = true;
								}
							}

							for (DTOCJustificaciones justificacion : justificacionesAGuardar) {

								if (justificacion.getDescripcion() != null
										&& !justificacion.getDescripcion()
												.isEmpty()) {
									faltanDatos = false;
									DTOCJustificacionesPK pkAGuardar = new DTOCJustificacionesPK(
											procesoDetalle
													.getIdProcesoElectoral(),
											procesoDetalle
													.getIdDetalleProceso(),
											i + 1);
									justificacionAGuardar
											.setDTOCJustificacionesPK(pkAGuardar);
									justificacionAGuardar
											.setDescripcion(justificacion
													.getDescripcion());
									justificacionAGuardar
											.setDescripcionCorta(justificacion
													.getDescripcionCorta());
									justificacionAGuardar
											.setResultado(justificacion
													.getResultado());

									bsdConfiguracionCatalogos
											.guardaActualizaCJustificaciones(justificacionAGuardar);
									guardadoExitoso = true;
									i++;
								} else {
									faltanDatos = true;
								}
							}

						}
					}
				}
			} catch (Exception e) {
				log.error("Error al guardar catálogo de justificaciones en guardaCatalogos de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				guardadoExitoso = false;
			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			DTOCEvaluacion evaluacionAGuardar = new DTOCEvaluacion();
			DTOReglasEvalucaion reglasAGuardar = new DTOReglasEvalucaion();
			List<DTOReglasEvalucaion> indexEncontrados = new ArrayList<DTOReglasEvalucaion>();
			List<DTOReglasEvalucaion> indexEncontradosPorDefecto = new ArrayList<DTOReglasEvalucaion>();
			int i = 0;
			int contadorNuevasEvaluaciones = 0;
			try {

				// TODO Aquí comienza la captura
				if (accionModulo.equals(accionCapturar)) {
					evaluacionesAGuardar = new ArrayList<DTOCEvaluacion>();
					if (evaluacionesSeleccionadas != null
							&& !evaluacionesSeleccionadas.isEmpty()) {
						contadorNuevasEvaluaciones = evaluacionesSeleccionadas
								.size() + 1;
						evaluacionesAGuardar.addAll(evaluacionesSeleccionadas);

					}

					if (evaluacionesAGuardar != null
							|| !evaluacionesAGuardar.isEmpty()) {

						if (procesoDetalle != null) {
							// Si son todos los catálogos
							if (sonTodos) {
								for (int j = 0; j < listaDetalles.size() - 1; j++) {
									int k = 0;
									for (DTOCEvaluacion evaluacion : evaluacionesAGuardar) {
										// Guardamos las acciones con el
										// idProceso y
										// idDetalle de cada uno de la lista
										DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
												listaDetalles
														.get(j)
														.getIdProcesoElectoral(),
												listaDetalles.get(j)
														.getIdDetalleProceso(),
												k + 1);

										evaluacionAGuardar
												.setDTOCEvaluacionPK(pkAGuardar);
										evaluacionAGuardar
												.setDescripcion(evaluacion
														.getDescripcion());
										evaluacionAGuardar.setTipo(evaluacion
												.getTipo());

										bsdConfiguracionCatalogos
												.guardaActualizaCEvaluaciones(evaluacionAGuardar);

										// TODO Aqui reglas de evaluacion
										guardadoExitoso = true;
										k++;
									}
								}

							} else {

								for (DTOCEvaluacion evaluacion : evaluacionesAGuardar) {

									if (evaluacion.getDescripcion() != null
											&& !evaluacion.getDescripcion()
													.isEmpty()) {
										faltanDatos = false;
										DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
												procesoDetalle
														.getIdProcesoElectoral(),
												procesoDetalle
														.getIdDetalleProceso(),
												evaluacion
														.getDTOCEvaluacionPK()
														.getIdEvaluacion());
										evaluacionAGuardar
												.setDTOCEvaluacionPK(pkAGuardar);
										evaluacionAGuardar
												.setDescripcion(evaluacion
														.getDescripcion());
										evaluacionAGuardar.setTipo(evaluacion
												.getTipo());

										bsdConfiguracionCatalogos
												.guardaActualizaCEvaluaciones(evaluacionAGuardar);

										// TODO Aqui reglas de evaluacion
										if (evaluacionesSeleccionadas != null
												|| !evaluacionesSeleccionadas
														.isEmpty()) {

											if (reglasAsociadasPredefinidas != null
													&& !reglasAsociadasPredefinidas
															.isEmpty()) {
												for (DTOReglasEvalucaion regla : reglasAsociadasPredefinidas) {

													if (evaluacion
															.getDTOCEvaluacionPK()
															.getIdEvaluacion()
															.equals(regla
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion())) {
														DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																procesoDetalle
																		.getIdProcesoElectoral(),
																procesoDetalle
																		.getIdDetalleProceso(),
																evaluacion
																		.getDTOCEvaluacionPK()
																		.getIdEvaluacion(),
																regla.getDTOReglasEvalucaionPK()
																		.getIdRegla());

														reglasAGuardar
																.setDTOReglasEvalucaionPK(pkReglasAGuardar);

														reglasAGuardar
																.setOrigenCurso(regla
																		.getOrigenCurso());

														bsdConfiguracionCatalogos
																.guardaActualizaReglasEvaluacion(reglasAGuardar);

														indexEncontrados
																.add(reglasAGuardar);
													}

												}

											}

											// if (accionModulo
											// .equals(accionCapturar)) {
											if (evaluacion
													.getDTOCEvaluacionPK()
													.getIdEvaluacion() <= evaluacionesSeleccionadas
													.size()) {

												reglasPredeterminadas = bsdConfiguracionCatalogos
														.getReglasEvaluacion(
																sinIdProceso,
																sinIdDetalle,
																evaluacion
																		.getDTOCEvaluacionPK()
																		.getIdEvaluacion());

												// Sí hay index ya guardados
												// que han sido modificados
												// los quitamos de las
												// evaluaciones predefinidas
												boolean seEncontroRegla = false;
												List<DTOReglasEvalucaion> reglasRemover = new ArrayList<DTOReglasEvalucaion>();

												if (indexEncontrados != null
														&& !indexEncontrados
																.isEmpty()) {
													for (DTOReglasEvalucaion reglaPredeterminada : reglasPredeterminadas) {
														for (DTOReglasEvalucaion reglaYaGuardada : indexEncontrados) {
															if (reglaPredeterminada
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion()
																	.equals(reglaYaGuardada
																			.getDTOReglasEvalucaionPK()
																			.getIdEvaluacion())) {
																reglasRemover
																		.add(reglaPredeterminada);
																seEncontroRegla = true;

															}
														}
													}

												}

												if (seEncontroRegla) {
													reglasPredeterminadas
															.removeAll(reglasRemover);
												}

												for (DTOReglasEvalucaion regla : reglasPredeterminadas) {

													if (evaluacion
															.getDTOCEvaluacionPK()
															.getIdEvaluacion()
															.equals(regla
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion())) {
														DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																procesoDetalle
																		.getIdProcesoElectoral(),
																procesoDetalle
																		.getIdDetalleProceso(),
																evaluacion
																		.getDTOCEvaluacionPK()
																		.getIdEvaluacion(),
																regla.getDTOReglasEvalucaionPK()
																		.getIdRegla());

														reglasAGuardar
																.setDTOReglasEvalucaionPK(pkReglasAGuardar);

														reglasAGuardar
																.setOrigenCurso(regla
																		.getOrigenCurso());

														bsdConfiguracionCatalogos
																.guardaActualizaReglasEvaluacion(reglasAGuardar);
													}
												}
											}
											// } else {

											// reglasPredeterminadas =
											// bsdConfiguracionCatalogos
											// .getReglasEvaluacion(
											// procesoDetalle
											// .getIdProcesoElectoral(),
											// procesoDetalle
											// .getIdDetalleProceso(),
											// evaluacion
											// .getDTOCEvaluacionPK()
											// .getIdEvaluacion());
											//
											// for (DTOReglasEvalucaion
											// regla :
											// reglasPredeterminadas) {
											//
											// if (evaluacion
											// .getDTOCEvaluacionPK()
											// .getIdEvaluacion()
											// .equals(regla
											// .getDTOReglasEvalucaionPK()
											// .getIdEvaluacion())) {
											// DTOReglasEvalucaionPK
											// pkReglasAGuardar = new
											// DTOReglasEvalucaionPK(
											// procesoDetalle
											// .getIdProcesoElectoral(),
											// procesoDetalle
											// .getIdDetalleProceso(),
											// evaluacion
											// .getDTOCEvaluacionPK()
											// .getIdEvaluacion(),
											// regla.getDTOReglasEvalucaionPK()
											// .getIdRegla());
											//
											// reglasAGuardar
											// .setDTOReglasEvalucaionPK(pkReglasAGuardar);
											//
											// reglasAGuardar
											// .setOrigenCurso(regla
											// .getOrigenCurso());
											//
											// bsdConfiguracionCatalogos
											// .guardaActualizaReglasEvaluacion(reglasAGuardar);
											// }
											// }
											//
											// }

											// }

										}

										guardadoExitoso = true;
										i++;
									} else {
										faltanDatos = true;
									}
								}

								// Evaluaciones agregadas
								if (nuevasEvaluacionesSeleccionadas != null
										&& !nuevasEvaluacionesSeleccionadas
												.isEmpty()) {
									for (DTOCEvaluacion eva : nuevasEvaluacionesSeleccionadas) {

										// TODO Guardar evaluaciones aquí
										if (eva.getDescripcion() != null
												&& !eva.getDescripcion()
														.isEmpty()) {
											faltanDatos = false;
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													procesoDetalle
															.getIdProcesoElectoral(),
													procesoDetalle
															.getIdDetalleProceso(),
													contadorNuevasEvaluaciones);
											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(eva
															.getDescripcion());
											evaluacionAGuardar.setTipo(eva
													.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											for (DTOReglasEvalucaion regla : eva
													.getDTOReglasEvalucaionList()) {
												if (eva.getDTOCEvaluacionPK()
														.getIdEvaluacion()
														.equals(regla
																.getDTOReglasEvalucaionPK()
																.getIdEvaluacion())) {
													DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
															procesoDetalle
																	.getIdProcesoElectoral(),
															procesoDetalle
																	.getIdDetalleProceso(),
															contadorNuevasEvaluaciones,
															regla.getDTOReglasEvalucaionPK()
																	.getIdRegla());

													reglasAGuardar
															.setDTOReglasEvalucaionPK(pkReglasAGuardar);

													reglasAGuardar
															.setOrigenCurso(regla
																	.getOrigenCurso());

													bsdConfiguracionCatalogos
															.guardaActualizaReglasEvaluacion(reglasAGuardar);
												}

											}
											guardadoExitoso = true;
											contadorNuevasEvaluaciones++;
										} else {
											faltanDatos = true;
										}
									}
								}

								// Guardar reglas por defecto
								if (sonTodos) {
									for (int j = 0; j < listaDetalles.size() - 1; j++) {
										int k = evaluacionesAGuardar.size() + 1;
										for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {
											// Guardamos las acciones con el
											// idProceso y
											// idDetalle de cada uno de la lista
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													listaDetalles
															.get(j)
															.getIdProcesoElectoral(),
													listaDetalles
															.get(j)
															.getIdDetalleProceso(),
													k + 1);

											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(evaluacionPorDefecto
															.getDescripcion());
											evaluacionAGuardar
													.setTipo(evaluacionPorDefecto
															.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											// TODO Aqui reglas de
											// evaluacionPorDefecto
											guardadoExitoso = true;
											k++;
										}
									}

								} else {
									Integer idEvaluacionPorDefecto = 0;

									if (evaluacionesAGuardar != null
											&& !evaluacionesAGuardar.isEmpty()
											&& nuevasEvaluacionesSeleccionadas != null
											&& !nuevasEvaluacionesSeleccionadas
													.isEmpty()) {
										idEvaluacionPorDefecto = ((evaluacionesAGuardar
												.size())
												+ nuevasEvaluacionesSeleccionadas
														.size() + 1);
									} else {
										if (evaluacionesAGuardar != null
												&& !evaluacionesAGuardar
														.isEmpty()) {
											idEvaluacionPorDefecto = evaluacionesAGuardar
													.size() + 1;
										}
										if (nuevasEvaluacionesSeleccionadas != null
												&& !nuevasEvaluacionesSeleccionadas
														.isEmpty()) {
											idEvaluacionPorDefecto = nuevasEvaluacionesSeleccionadas
													.size() + 1;
										}

									}

									for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {

										if (evaluacionPorDefecto
												.getDescripcion() != null
												&& !evaluacionPorDefecto
														.getDescripcion()
														.isEmpty()) {
											faltanDatos = false;
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													procesoDetalle
															.getIdProcesoElectoral(),
													procesoDetalle
															.getIdDetalleProceso(),
													idEvaluacionPorDefecto);
											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(evaluacionPorDefecto
															.getDescripcion());
											evaluacionAGuardar
													.setTipo(evaluacionPorDefecto
															.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											if (evaluacionesXDefault != null
													|| !evaluacionesXDefault
															.isEmpty()) {

												if (reglasAsociadasPorDefecto != null
														&& !reglasAsociadasPorDefecto
																.isEmpty()) {
													for (DTOReglasEvalucaion regla : reglasAsociadasPorDefecto) {
														if (evaluacionPorDefecto
																.getDTOCEvaluacionPK()
																.getIdEvaluacion()
																.equals(regla
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion())) {
															DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																	procesoDetalle
																			.getIdProcesoElectoral(),
																	procesoDetalle
																			.getIdDetalleProceso(),
																	idEvaluacionPorDefecto,
																	regla.getDTOReglasEvalucaionPK()
																			.getIdRegla());

															reglasAGuardar
																	.setDTOReglasEvalucaionPK(pkReglasAGuardar);

															reglasAGuardar
																	.setOrigenCurso(regla
																			.getOrigenCurso());

															bsdConfiguracionCatalogos
																	.guardaActualizaReglasEvaluacion(reglasAGuardar);

															indexEncontradosPorDefecto
																	.add(reglasAGuardar);
														}
													}

												}

												// else {

												boolean esEncontradaReglaPorDefecto = false;
												List<DTOReglasEvalucaion> reglasARemoverPorDefecto = new ArrayList<DTOReglasEvalucaion>();

												reglasPredeterminadasDefecto = bsdConfiguracionCatalogos
														.getReglasEvaluacion(
																sinIdProceso,
																sinIdDetalle,
																evaluacionPorDefecto
																		.getDTOCEvaluacionPK()
																		.getIdEvaluacion());

												if (indexEncontradosPorDefecto != null
														&& !indexEncontradosPorDefecto
																.isEmpty()) {
													for (DTOReglasEvalucaion reglaPredeterminada : reglasPredeterminadasDefecto) {
														for (DTOReglasEvalucaion reglaGuardadaPorDefecto : indexEncontradosPorDefecto) {
															if (nuevasEvaluacionesSeleccionadas != null
																	&& !nuevasEvaluacionesSeleccionadas
																			.isEmpty()) {
																Integer contadorNuevasEval = reglaPredeterminada
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion()
																		+ nuevasEvaluacionesSeleccionadas
																				.size();
																if (contadorNuevasEval
																		.equals(reglaGuardadaPorDefecto
																				.getDTOReglasEvalucaionPK()
																				.getIdEvaluacion())) {
																	reglasARemoverPorDefecto
																			.add(reglaPredeterminada);
																	esEncontradaReglaPorDefecto = true;
																}

															} else {
																if (reglaPredeterminada
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion()
																		.equals(reglaGuardadaPorDefecto
																				.getDTOReglasEvalucaionPK()
																				.getIdEvaluacion())) {
																	reglasARemoverPorDefecto
																			.add(reglaPredeterminada);
																	esEncontradaReglaPorDefecto = true;
																}
															}
														}
													}
												}

												if (esEncontradaReglaPorDefecto) {
													reglasPredeterminadasDefecto
															.removeAll(reglasARemoverPorDefecto);
												}

												for (DTOReglasEvalucaion regla : reglasPredeterminadasDefecto) {

													DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
															procesoDetalle
																	.getIdProcesoElectoral(),
															procesoDetalle
																	.getIdDetalleProceso(),
															idEvaluacionPorDefecto,
															regla.getDTOReglasEvalucaionPK()
																	.getIdRegla());

													reglasAGuardar
															.setDTOReglasEvalucaionPK(pkReglasAGuardar);

													reglasAGuardar
															.setOrigenCurso(regla
																	.getOrigenCurso());

													bsdConfiguracionCatalogos
															.guardaActualizaReglasEvaluacion(reglasAGuardar);

												}

												// }

											}

										}
										idEvaluacionPorDefecto++;
									}
								}
							}
						}

					}
				}

				// TODO Aquí termina Captura

				/********************************************************************
				 * *************************************
				 * 
				 * Aquí Comienza Modificar
				 * 
				 * *************************************************************
				 * ******************************************
				 */
				if (accionModulo.equals(accionModificar)) {

					int contadorSeleccionadas = 0;

					List<DTOCEvaluacion> listaAEliminar = new ArrayList<DTOCEvaluacion>();
					listaAEliminar = bsdConfiguracionCatalogos
							.getCEvaluaciones(
									procesoDetalle.getIdProcesoElectoral(),
									procesoDetalle.getIdDetalleProceso());

					evaluacionesAEliminar = new ArrayList<DTOCEvaluacion>();
					evaluacionesAEliminar.addAll(listaAEliminar);

					// Eliminar cargos seleccionados en la vista de modificar
					if (evaluacionesAEliminar != null
							&& !evaluacionesAEliminar.isEmpty()) {
						// Eliminando acciones
						bsdConfiguracionCatalogos
								.eliminaCEvaluaciones(evaluacionesAEliminar);
					}

					// eliminarCatalogos();

					// ************************************************************************
					evaluacionesAGuardar = new ArrayList<DTOCEvaluacion>();
					if (evaluacionesSeleccionadas != null
							&& !evaluacionesSeleccionadas.isEmpty()) {
						contadorNuevasEvaluaciones = evaluacionesSeleccionadas
								.size() + 1;
						evaluacionesAGuardar.addAll(evaluacionesSeleccionadas);

					}

					if (evaluacionesAGuardar != null
							|| !evaluacionesAGuardar.isEmpty()) {

						if (procesoDetalle != null) {
							// Si son todos los catálogos
							if (sonTodos) {
								for (int j = 0; j < listaDetalles.size() - 1; j++) {
									int k = 0;
									for (DTOCEvaluacion evaluacion : evaluacionesAGuardar) {
										// Guardamos las acciones con el
										// idProceso y
										// idDetalle de cada uno de la lista
										DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
												listaDetalles
														.get(j)
														.getIdProcesoElectoral(),
												listaDetalles.get(j)
														.getIdDetalleProceso(),
												k + 1);

										evaluacionAGuardar
												.setDTOCEvaluacionPK(pkAGuardar);
										evaluacionAGuardar
												.setDescripcion(evaluacion
														.getDescripcion());
										evaluacionAGuardar.setTipo(evaluacion
												.getTipo());

										bsdConfiguracionCatalogos
												.guardaActualizaCEvaluaciones(evaluacionAGuardar);

										// TODO Aqui reglas de evaluacion
										guardadoExitoso = true;
										k++;
									}
								}

							} else {

								for (DTOCEvaluacion evaluacion : evaluacionesAGuardar) {

									if (evaluacion.getDescripcion() != null
											&& !evaluacion.getDescripcion()
													.isEmpty()) {
										faltanDatos = false;
										DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
												procesoDetalle
														.getIdProcesoElectoral(),
												procesoDetalle
														.getIdDetalleProceso(),
												contadorSeleccionadas + 1);
										evaluacionAGuardar
												.setDTOCEvaluacionPK(pkAGuardar);
										evaluacionAGuardar
												.setDescripcion(evaluacion
														.getDescripcion());
										evaluacionAGuardar.setTipo(evaluacion
												.getTipo());

										bsdConfiguracionCatalogos
												.guardaActualizaCEvaluaciones(evaluacionAGuardar);

										// TODO Aqui reglas de evaluacion
										if (evaluacionesSeleccionadas != null
												|| !evaluacionesSeleccionadas
														.isEmpty()) {

											if (reglasAsociadasPredefinidas != null
													&& !reglasAsociadasPredefinidas
															.isEmpty()) {
												for (DTOReglasEvalucaion regla : reglasAsociadasPredefinidas) {

													if (evaluacion
															.getDTOCEvaluacionPK()
															.getIdEvaluacion()
															.equals(regla
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion())) {
														DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																procesoDetalle
																		.getIdProcesoElectoral(),
																procesoDetalle
																		.getIdDetalleProceso(),
																evaluacion
																		.getDTOCEvaluacionPK()
																		.getIdEvaluacion(),
																regla.getDTOReglasEvalucaionPK()
																		.getIdRegla());

														reglasAGuardar
																.setDTOReglasEvalucaionPK(pkReglasAGuardar);

														reglasAGuardar
																.setOrigenCurso(regla
																		.getOrigenCurso());

														bsdConfiguracionCatalogos
																.guardaActualizaReglasEvaluacion(reglasAGuardar);

														indexEncontrados
																.add(reglasAGuardar);
													}

												}
											}

											// else {
											boolean esEncontradaRegla = false;
											List<DTOReglasEvalucaion> reglasARemover = new ArrayList<DTOReglasEvalucaion>();

											if (indexEncontrados != null
													&& !indexEncontrados
															.isEmpty()) {
												for (DTOCEvaluacion evaluacionPre : evaluacionesPredefinidas) {
													for (DTOReglasEvalucaion reglaPredefinida : evaluacionPre
															.getDTOReglasEvalucaionList()) {
														for (DTOReglasEvalucaion reglaYaGuardada : indexEncontrados) {
															if (reglaPredefinida
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion()
																	.equals(reglaYaGuardada
																			.getDTOReglasEvalucaionPK()
																			.getIdEvaluacion())) {

																reglasARemover
																		.add(reglaPredefinida);
																esEncontradaRegla = true;

															}
														}
													}
												}
											}

											if (esEncontradaRegla) {
												for (DTOCEvaluacion evaluacionPre : evaluacionesPredefinidas) {
													evaluacionPre
															.getDTOReglasEvalucaionList()
															.removeAll(
																	reglasARemover);
												}
											}

											for (DTOCEvaluacion eval : evaluacionesPredefinidas) {
												for (DTOReglasEvalucaion regla : eval
														.getDTOReglasEvalucaionList()) {

													if (evaluacion
															.getDTOCEvaluacionPK()
															.getIdEvaluacion()
															.equals(regla
																	.getDTOReglasEvalucaionPK()
																	.getIdEvaluacion())) {
														DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																procesoDetalle
																		.getIdProcesoElectoral(),
																procesoDetalle
																		.getIdDetalleProceso(),
																contadorSeleccionadas + 1,
																regla.getDTOReglasEvalucaionPK()
																		.getIdRegla());

														reglasAGuardar
																.setDTOReglasEvalucaionPK(pkReglasAGuardar);

														reglasAGuardar
																.setOrigenCurso(regla
																		.getOrigenCurso());

														bsdConfiguracionCatalogos
																.guardaActualizaReglasEvaluacion(reglasAGuardar);
													}
												}
												// }

											}

										}

										guardadoExitoso = true;
										contadorSeleccionadas++;
									} else {
										faltanDatos = true;
									}
								}

								// Evaluaciones agregadas
								if (nuevasEvaluacionesSeleccionadas != null
										&& !nuevasEvaluacionesSeleccionadas
												.isEmpty()) {
									for (DTOCEvaluacion eva : nuevasEvaluacionesSeleccionadas) {

										// TODO Guardar evaluaciones aquí
										if (eva.getDescripcion() != null
												&& !eva.getDescripcion()
														.isEmpty()) {
											faltanDatos = false;
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													procesoDetalle
															.getIdProcesoElectoral(),
													procesoDetalle
															.getIdDetalleProceso(),
													contadorNuevasEvaluaciones);
											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(eva
															.getDescripcion());
											evaluacionAGuardar.setTipo(eva
													.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											for (DTOReglasEvalucaion regla : eva
													.getDTOReglasEvalucaionList()) {
												if (eva.getDTOCEvaluacionPK()
														.getIdEvaluacion()
														.equals(regla
																.getDTOReglasEvalucaionPK()
																.getIdEvaluacion())) {
													DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
															procesoDetalle
																	.getIdProcesoElectoral(),
															procesoDetalle
																	.getIdDetalleProceso(),
															contadorNuevasEvaluaciones,
															regla.getDTOReglasEvalucaionPK()
																	.getIdRegla());

													reglasAGuardar
															.setDTOReglasEvalucaionPK(pkReglasAGuardar);

													reglasAGuardar
															.setOrigenCurso(regla
																	.getOrigenCurso());

													bsdConfiguracionCatalogos
															.guardaActualizaReglasEvaluacion(reglasAGuardar);
												}

											}
											guardadoExitoso = true;
											contadorNuevasEvaluaciones++;
										} else {
											faltanDatos = true;
										}
									}
								}

								// Guardar reglas por defecto
								if (sonTodos) {
									for (int j = 0; j < listaDetalles.size() - 1; j++) {
										int k = evaluacionesAGuardar.size() + 1;
										for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {
											// Guardamos las acciones con el
											// idProceso y
											// idDetalle de cada uno de la lista
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													listaDetalles
															.get(j)
															.getIdProcesoElectoral(),
													listaDetalles
															.get(j)
															.getIdDetalleProceso(),
													k + 1);

											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(evaluacionPorDefecto
															.getDescripcion());
											evaluacionAGuardar
													.setTipo(evaluacionPorDefecto
															.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											// TODO Aqui reglas de
											// evaluacionPorDefecto
											guardadoExitoso = true;
											k++;
										}
									}

								} else {
									Integer idEvaluacionPorDefecto = 0;

									if (evaluacionesAGuardar != null
											&& !evaluacionesAGuardar.isEmpty()
											&& nuevasEvaluacionesSeleccionadas != null
											&& !nuevasEvaluacionesSeleccionadas
													.isEmpty()) {
										idEvaluacionPorDefecto = ((evaluacionesAGuardar
												.size())
												+ nuevasEvaluacionesSeleccionadas
														.size() + 1);
									} else {
										if (evaluacionesAGuardar != null
												&& !evaluacionesAGuardar
														.isEmpty()) {
											idEvaluacionPorDefecto = evaluacionesAGuardar
													.size() + 1;
										}
										if (nuevasEvaluacionesSeleccionadas != null
												&& !nuevasEvaluacionesSeleccionadas
														.isEmpty()) {
											idEvaluacionPorDefecto = nuevasEvaluacionesSeleccionadas
													.size() + 1;
										}

									}

									for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {

										if (evaluacionPorDefecto
												.getDescripcion() != null
												&& !evaluacionPorDefecto
														.getDescripcion()
														.isEmpty()) {
											faltanDatos = false;
											DTOCEvaluacionPK pkAGuardar = new DTOCEvaluacionPK(
													procesoDetalle
															.getIdProcesoElectoral(),
													procesoDetalle
															.getIdDetalleProceso(),
													idEvaluacionPorDefecto);
											evaluacionAGuardar
													.setDTOCEvaluacionPK(pkAGuardar);
											evaluacionAGuardar
													.setDescripcion(evaluacionPorDefecto
															.getDescripcion());
											evaluacionAGuardar
													.setTipo(evaluacionPorDefecto
															.getTipo());

											bsdConfiguracionCatalogos
													.guardaActualizaCEvaluaciones(evaluacionAGuardar);

											if (evaluacionesXDefault != null
													|| !evaluacionesXDefault
															.isEmpty()) {

												if (reglasAsociadasPorDefecto != null
														&& !reglasAsociadasPorDefecto
																.isEmpty()) {
													for (DTOReglasEvalucaion regla : reglasAsociadasPorDefecto) {
														if (evaluacionPorDefecto
																.getDTOCEvaluacionPK()
																.getIdEvaluacion()
																.equals(regla
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion())) {
															DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																	procesoDetalle
																			.getIdProcesoElectoral(),
																	procesoDetalle
																			.getIdDetalleProceso(),
																	idEvaluacionPorDefecto,
																	regla.getDTOReglasEvalucaionPK()
																			.getIdRegla());

															reglasAGuardar
																	.setDTOReglasEvalucaionPK(pkReglasAGuardar);

															reglasAGuardar
																	.setOrigenCurso(regla
																			.getOrigenCurso());

															bsdConfiguracionCatalogos
																	.guardaActualizaReglasEvaluacion(reglasAGuardar);

															indexEncontradosPorDefecto
																	.add(reglasAGuardar);
														}
													}

												}

												// else {
												boolean esEncontradaReglaPorDefecto = false;
												List<DTOReglasEvalucaion> reglasARemoverPorDefecto = new ArrayList<DTOReglasEvalucaion>();

												if (indexEncontradosPorDefecto != null
														&& !indexEncontradosPorDefecto
																.isEmpty()) {
													for (DTOCEvaluacion evaluacionPre : evaluacionesPredefinidas) {
														for (DTOReglasEvalucaion reglaPredeterminada : evaluacionPre
																.getDTOReglasEvalucaionList()) {
															for (DTOReglasEvalucaion reglaYaGuardadaDefecto : indexEncontradosPorDefecto) {
																if (reglaPredeterminada
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion()
																		.equals(reglaYaGuardadaDefecto
																				.getDTOReglasEvalucaionPK()
																				.getIdEvaluacion())) {

																	reglasARemoverPorDefecto
																			.add(reglaPredeterminada);
																	esEncontradaReglaPorDefecto = true;

																}

															}

														}
													}
												}

												if (esEncontradaReglaPorDefecto) {
													for (DTOCEvaluacion evaluacionPre : evaluacionesPredefinidas) {
														evaluacionPre
																.getDTOReglasEvalucaionList()
																.removeAll(
																		reglasARemoverPorDefecto);
													}
												}

												for (DTOCEvaluacion eval : evaluacionesPredefinidas) {
													for (DTOReglasEvalucaion regla : eval
															.getDTOReglasEvalucaionList()) {

														if (evaluacionPorDefecto
																.getDTOCEvaluacionPK()
																.getIdEvaluacion()
																.equals(regla
																		.getDTOReglasEvalucaionPK()
																		.getIdEvaluacion())) {

															DTOReglasEvalucaionPK pkReglasAGuardar = new DTOReglasEvalucaionPK(
																	procesoDetalle
																			.getIdProcesoElectoral(),
																	procesoDetalle
																			.getIdDetalleProceso(),
																	idEvaluacionPorDefecto,
																	regla.getDTOReglasEvalucaionPK()
																			.getIdRegla());

															reglasAGuardar
																	.setDTOReglasEvalucaionPK(pkReglasAGuardar);

															reglasAGuardar
																	.setOrigenCurso(regla
																			.getOrigenCurso());

															bsdConfiguracionCatalogos
																	.guardaActualizaReglasEvaluacion(reglasAGuardar);

														}
													}

												}
												// }

											}

										}
										idEvaluacionPorDefecto++;
									}
								}
							}
						}

					}

					// *************************************************************************

				}
				/**
				 * *************************************************************
				 * ******************************************
				 * 
				 * Aquí termina Modificar
				 * 
				 * *************************************************************
				 * ******************************************
				 */

			} catch (Exception e) {
				log.error("Error al guardar catálogo de evaluaciones en guardaCatalogos de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				guardadoExitoso = false;
			}
		}

		/**
		 * Mensajes de guardado exitoso o de un error al guardar
		 */
		if (guardadoExitoso) {
			muestraForm = false;
			muestraMensaje = true;

			// Mensaje de guardados
			if (accionModulo.equals(accionCapturar)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"growl",
								new FacesMessage(
										"",
										Utilidades
												.mensajeProperties("validacion_mensaje_generales_confirmacion_guardados")));
			}

			// Mensaje de modificados
			if (accionModulo.equals(accionModificar)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"growl",
								new FacesMessage(
										"",
										Utilidades
												.mensajeProperties("validacion_mensaje_generales_confirmacion_modificados")));
			}

		} else {
			muestraForm = true;
			muestraMensaje = true;
			FacesMessage msjErrorAlGuardar = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_guardarFallo"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjErrorAlGuardar);
		}

		/**
		 * Mensjae si faltan datos en el guardado
		 */
		if (faltanDatos) {
			muestraForm = true;
			muestraMensaje = true;
			FacesMessage msjErrorAlGuardarFaltandDatos = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_guardarFallo_faltan_datos"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjErrorAlGuardarFaltandDatos);
		}
	}

	/**
	 * Método que elimina el catálogo seleccionado en BD
	 * 
	 * @author Gerardo López
	 * @since 04/07/2017
	 * 
	 */
	public void eliminarCatalogos() {
		boolean eliminoCorrectamente = false;

		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			try {
				bsdConfiguracionCatalogos
						.eliminaCAcciones(accionesPredefinidas);

				listaDetallesConsulta.remove(procesoDetalle);
				eliminoCorrectamente = true;
			} catch (Exception e) {
				log.error("Hubo un error al eliminar las acciones en eliminarCatalogos() en MBCOnfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				eliminoCorrectamente = false;

			}
		}

		/**
		 * Catálogo de cargos responsable
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			try {
				bsdConfiguracionCatalogos
						.eliminaCCargoResponsable(cargosPredefinidos);

				listaDetallesConsulta.remove(procesoDetalle);
				eliminoCorrectamente = true;
			} catch (Exception e) {
				log.error("Hubo un error al eliminar los cargos en eliminarCatalogos() en MBCOnfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				eliminoCorrectamente = false;

			}
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			try {
				bsdConfiguracionCatalogos
						.eliminaCEscolaridades(escolaridadesPredefinidas);

				listaDetallesConsulta.remove(procesoDetalle);
				eliminoCorrectamente = true;
			} catch (Exception e) {
				log.error("Hubo un error al eliminar las escolaridades en eliminarCatalogos() en MBCOnfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				eliminoCorrectamente = false;

			}
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			try {

				justificacionesPredefinidas.addAll(justificacionesXDefault);

				bsdConfiguracionCatalogos
						.eliminaCJustificaciones(justificacionesPredefinidas);

				listaDetallesConsulta.remove(procesoDetalle);
				eliminoCorrectamente = true;
			} catch (Exception e) {
				log.error("Hubo un error al eliminar las justificaciones en eliminarCatalogos() en MBCOnfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				eliminoCorrectamente = false;

			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			try {

				evaluacionesPredefinidas.addAll(evaluacionesXDefault);

				bsdConfiguracionCatalogos
						.eliminaCEvaluaciones(evaluacionesPredefinidas);

				listaDetallesConsulta.remove(procesoDetalle);
				eliminoCorrectamente = true;
			} catch (Exception e) {
				log.error("Hubo un error al eliminar las evaluaciones en eliminarCatalogos() en MBCOnfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
				eliminoCorrectamente = false;

			}
		}

		/**
		 * Mensajes de eliminado exitoso o de un error al guardar
		 */
		if (eliminoCorrectamente) {
			muestraForm = false;
			muestraMensaje = true;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									"",
									Utilidades
											.mensajeProperties("validacion_mensaje_generales_confirmacion_eliminados")));
		} else {
			muestraForm = true;
			muestraMensaje = true;
			FacesMessage msjErrorAlEliminar = new FacesMessage(
					Utilidades
							.mensajeProperties("validacion_mensaje_generales_eliminarFallo"));
			FacesContext.getCurrentInstance().addMessage("mensaje",
					msjErrorAlEliminar);
		}

	}

	/**
	 * Método para renderizar componentes de acuerdo al tipo de accion
	 * 
	 * @author Gerardo López
	 * @since 03/07/2017
	 * 
	 */
	public boolean isRendered(String accion) {
		return accion.equals(accionModulo);
	}

	/**
	 * Método que cambia la bandera despues de haber seleccionado la carga
	 * inicial en la tabla para presentar la tabla nueva, se inicializa la lista
	 * temporal para limpiarla y que no arrastre otras acciones seleccionadas.
	 * Si no se selecciono ninguna accion anteriormente envia mensaje al
	 * usuario.
	 * 
	 * @author Gerardo López
	 * @since 03/07/2017
	 * 
	 */
	public void cargaSeleccionadas() {
		cargaSeleccionada = true;

		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			nuevasAccionesSeleccionadasTmp = new ArrayList<DTOCAcciones>();
			accionesSeleccionadasTmp = new ArrayList<DTOCAcciones>();
			// Limpiamos lista de accionesSeleccionadasLista por si ya se habian
			// creado nuevas acciones y quieren volver a seleccionar de la carga
			// inicial
			if (accionesSeleccionadasLista.size() > 0) {
				accionesSeleccionadasTmp.addAll(nuevasAcciones);
				accionesSeleccionadasTmp.addAll(accionesSeleccionadas);
				// for (DTOCAcciones accionAgregar : accionesSeleccionadas) {
				// if (!accionesSeleccionadasTmp.contains(accionAgregar)) {
				// accionesSeleccionadasTmp.add(accionAgregar);
				// }
				// }
				accionesSeleccionadasLista = new ArrayList<DTOCAcciones>();
				accionesSeleccionadasLista.addAll(accionesSeleccionadasTmp);
			} else {
				accionesSeleccionadasLista = new ArrayList<DTOCAcciones>();
				accionesSeleccionadasTmp.addAll(accionesSeleccionadas);
				accionesSeleccionadasLista.addAll(accionesSeleccionadasTmp);
			}
			if (accionesSeleccionadas.isEmpty()) {
				muestraMensaje = true;
				FacesMessage msjAccionesNoSeleccionadas = new FacesMessage(
						Utilidades
								.mensajeProperties("mensaje_acciones_no_seleccionadas_captura"));
				FacesContext.getCurrentInstance().addMessage("mensaje",
						msjAccionesNoSeleccionadas);
			}

		}

		/**
		 * Catálogo de cargos de responsable
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			nuevosCargosSeleccionadosTmp = new ArrayList<DTOCCargoResponsable>();
			cargosSeleccionadosTmp = new ArrayList<DTOCCargoResponsable>();

			if (cargosSeleccionadosLista.size() > 0) {
				cargosSeleccionadosTmp.addAll(nuevosCargos);
				cargosSeleccionadosTmp.addAll(cargosSeleccionados);
				// for (DTOCCargoResponsable cargoAgregar : cargosSeleccionados)
				// {
				// if (!cargosSeleccionadosTmp.contains(cargoAgregar)) {
				// cargosSeleccionadosTmp.add(cargoAgregar);
				// }
				// }
				cargosSeleccionadosLista = new ArrayList<DTOCCargoResponsable>();
				cargosSeleccionadosLista.addAll(cargosSeleccionadosTmp);
			} else {
				cargosSeleccionadosLista = new ArrayList<DTOCCargoResponsable>();
				cargosSeleccionadosTmp.addAll(cargosSeleccionados);
				cargosSeleccionadosLista.addAll(cargosSeleccionadosTmp);
			}
			if (cargosSeleccionados.isEmpty()) {
				muestraMensaje = true;
				FacesMessage msjCargosNoSeleccionados = new FacesMessage(
						Utilidades
								.mensajeProperties("mensaje_cargos_no_seleccionados_captura"));
				FacesContext.getCurrentInstance().addMessage("mensaje",
						msjCargosNoSeleccionados);
			}
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			nuevasEscolaridadesSeleccionadasTmp = new ArrayList<DTOCEscolaridades>();
			escolaridadesSeleccionadasTmp = new ArrayList<DTOCEscolaridades>();

			if (escolaridadesSeleccionadasLista.size() > 0) {
				escolaridadesSeleccionadasTmp.addAll(nuevasEscolaridades);
				escolaridadesSeleccionadasTmp
						.addAll(escolaridadesSeleccionadas);
				// for (DTOCEscolaridades escolaridadAgregar :
				// escolaridadesSeleccionadas) {
				// if (!escolaridadesSeleccionadasTmp
				// .contains(escolaridadAgregar)) {
				// escolaridadesSeleccionadasTmp.add(escolaridadAgregar);
				// }
				// }
				escolaridadesSeleccionadasLista = new ArrayList<DTOCEscolaridades>();
				escolaridadesSeleccionadasLista
						.addAll(escolaridadesSeleccionadasTmp);
			} else {
				escolaridadesSeleccionadasLista = new ArrayList<DTOCEscolaridades>();
				escolaridadesSeleccionadasTmp
						.addAll(escolaridadesSeleccionadas);
				escolaridadesSeleccionadasLista
						.addAll(escolaridadesSeleccionadasTmp);
			}
			if (escolaridadesSeleccionadas.isEmpty()) {
				muestraMensaje = true;
				FacesMessage msjEscolaridadesNoSeleccionados = new FacesMessage(
						Utilidades
								.mensajeProperties("mensaje_escolaridades_no_seleccionadas_captura"));
				FacesContext.getCurrentInstance().addMessage("mensaje",
						msjEscolaridadesNoSeleccionados);
			}
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			nuevasJustificacionesSeleccionadasTmp = new ArrayList<DTOCJustificaciones>();
			justificacionesSeleccionadasTmp = new ArrayList<DTOCJustificaciones>();

			if (justificacionesSeleccionadasLista.size() > 0) {
				justificacionesSeleccionadasTmp.addAll(nuevasJustificaciones);
				justificacionesSeleccionadasTmp
						.addAll(justificacionesSeleccionadas);
				// for (DTOCJustificaciones justificacionAgregar :
				// justificacionesSeleccionadas) {
				// if (!justificacionesSeleccionadasTmp
				// .contains(justificacionAgregar)) {
				// justificacionesSeleccionadasTmp
				// .add(justificacionAgregar);
				// }
				// }
				justificacionesSeleccionadasLista = new ArrayList<DTOCJustificaciones>();
				justificacionesSeleccionadasLista
						.addAll(justificacionesSeleccionadasTmp);
			} else {
				justificacionesSeleccionadasLista = new ArrayList<DTOCJustificaciones>();
				justificacionesSeleccionadasTmp
						.addAll(justificacionesSeleccionadas);
				justificacionesSeleccionadasLista
						.addAll(justificacionesSeleccionadasTmp);
			}
			if (justificacionesSeleccionadas.isEmpty()) {
				muestraMensaje = true;
				FacesMessage msjJustificacionesNoSeleccionados = new FacesMessage(
						Utilidades
								.mensajeProperties("mensaje_justificaciones_no_seleccionadas_captura"));
				FacesContext.getCurrentInstance().addMessage("mensaje",
						msjJustificacionesNoSeleccionados);
			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			nuevasEvaluacionesSeleccionadasTmp = new ArrayList<DTOCEvaluacion>();
			evaluacionesSeleccionadasTmp = new ArrayList<DTOCEvaluacion>();

			if (evaluacionesSeleccionadasLista.size() > 0) {
				evaluacionesSeleccionadasTmp.addAll(nuevasEvaluaciones);
				evaluacionesSeleccionadasTmp.addAll(evaluacionesSeleccionadas);
				// for (DTOCJustificaciones justificacionAgregar :
				// justificacionesSeleccionadas) {
				// if (!justificacionesSeleccionadasTmp
				// .contains(justificacionAgregar)) {
				// justificacionesSeleccionadasTmp
				// .add(justificacionAgregar);
				// }
				// }
				evaluacionesSeleccionadasLista = new ArrayList<DTOCEvaluacion>();
				evaluacionesSeleccionadasLista
						.addAll(evaluacionesSeleccionadasTmp);
			} else {
				evaluacionesSeleccionadasLista = new ArrayList<DTOCEvaluacion>();
				evaluacionesSeleccionadasTmp.addAll(evaluacionesSeleccionadas);
				evaluacionesSeleccionadasLista
						.addAll(evaluacionesSeleccionadasTmp);
			}
			if (evaluacionesSeleccionadas.isEmpty()) {
				muestraMensaje = true;
				FacesMessage msjEvaluacionesNoSeleccionados = new FacesMessage(
						Utilidades
								.mensajeProperties("mensaje_evaluaciones_no_seleccionadas_captura"));
				FacesContext.getCurrentInstance().addMessage("mensaje",
						msjEvaluacionesNoSeleccionados);
			}
		}

	}

	/**
	 * Método que cambia la bandera despues de haber seleccionado la carga
	 * inicial para volver a seleccionar, manteniendo seleccionadas las acciones
	 * que ya se tenian
	 * 
	 * @author Gerardo López
	 * @since 03/07/2017
	 * 
	 */
	public void volverSeleccionadas() {
		cargaSeleccionada = false;
		muestraFormularioAgrega = false;

		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {

			accionesSeleccionadas = new ArrayList<DTOCAcciones>();
			if (nuevasAccionesSeleccionadasTmp.size() > 0) {
				accionesSeleccionadasTmp
						.removeAll(nuevasAccionesSeleccionadasTmp);
			}
			accionesSeleccionadas.addAll(accionesSeleccionadasTmp);

		}

		/**
		 * Catálogo de cargo responsable
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			cargosSeleccionados = new ArrayList<DTOCCargoResponsable>();
			if (nuevosCargosSeleccionadosTmp.size() > 0) {
				cargosSeleccionadosTmp.removeAll(nuevosCargosSeleccionadosTmp);
			}
			cargosSeleccionados.addAll(cargosSeleccionadosTmp);
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			escolaridadesSeleccionadas = new ArrayList<DTOCEscolaridades>();
			if (nuevasEscolaridadesSeleccionadasTmp.size() > 0) {
				escolaridadesSeleccionadasTmp
						.removeAll(nuevasEscolaridadesSeleccionadasTmp);
			}
			escolaridadesSeleccionadas.addAll(escolaridadesSeleccionadasTmp);
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			log.info("Esta es la justificacion por defecto: "
					+ justificacionPorDefecto.getDescripcion());
			justificacionesSeleccionadas = new ArrayList<DTOCJustificaciones>();
			justificacionesSeleccionadas.add(justificacionPorDefecto);
			if (nuevasJustificacionesSeleccionadasTmp.size() > 0) {
				justificacionesSeleccionadasTmp
						.removeAll(nuevasJustificacionesSeleccionadasTmp);
			}
			justificacionesSeleccionadas
					.addAll(justificacionesSeleccionadasTmp);
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {

			evaluacionesSeleccionadas = new ArrayList<DTOCEvaluacion>();
			for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesPorDefecto) {
				evaluacionesSeleccionadas.add(evaluacionPorDefecto);
			}
			if (nuevasEvaluacionesSeleccionadasTmp.size() > 0) {
				evaluacionesSeleccionadasTmp
						.removeAll(nuevasEvaluacionesSeleccionadasTmp);
			}
			evaluacionesSeleccionadas.addAll(evaluacionesSeleccionadasTmp);
		}
	}

	/**
	 * Método que elimina los registros del catálogo en al vista de modificar al
	 * presiona el botón de eliminar por cada fila
	 * 
	 * @author Gerardo López
	 * @since 05/07/2017
	 */
	public void eliminaModifica(Object elimina) {
		boolean eliminar = false;
		int index = 0;
		/**
		 * Catálogo de acciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {
			if (accionModulo.equals(accionCapturar)
					|| accionModulo.equals(accionModificar)) {
				if (nuevasAcciones != null && !nuevasAcciones.isEmpty()) {
					for (int i = 0; i < nuevasAcciones.size(); i++) {
						if (nuevasAcciones.get(i).getNombre()
								.equals(((DTOCAcciones) elimina).getNombre())) {
							eliminar = true;
							index = i;
							break;
						}
					}

					if (eliminar) {
						nuevasAcciones.remove(index);
					}

				}
				if (nuevasAcciones.isEmpty()) {
					cargaSeleccionada = false;
				}
			}
		}

		/**
		 * Catálogo de cargos
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			if (accionModulo.equals(accionCapturar)
					|| accionModulo.equals(accionModificar)) {
				if (nuevosCargos != null && !nuevosCargos.isEmpty()) {

					for (int i = 0; i < nuevosCargos.size(); i++) {
						if (nuevosCargos
								.get(i)
								.getDescripcion()
								.equals(((DTOCCargoResponsable) elimina)
										.getDescripcion())) {
							eliminar = true;
							index = i;
							break;
						}
					}

					if (eliminar) {
						nuevosCargos.remove(index);
					}

				}
				if (nuevosCargos.isEmpty()) {
					cargaSeleccionada = false;
				}
			}
		}

		/**
		 * Catálogo de escolaridades
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			if (accionModulo.equals(accionCapturar)
					|| accionModulo.equals(accionModificar)) {
				if (nuevasEscolaridades != null
						&& !nuevasEscolaridades.isEmpty()) {

					for (int i = 0; i < nuevasEscolaridades.size(); i++) {
						if (nuevasEscolaridades
								.get(i)
								.getDescripcion()
								.equals(((DTOCEscolaridades) elimina)
										.getDescripcion())) {
							eliminar = true;
							index = i;
							break;
						}
					}

					if (eliminar) {
						nuevasEscolaridades.remove(index);
					}

				}

				if (nuevasEscolaridades.isEmpty()) {
					cargaSeleccionada = false;
				}
			}
		}

		/**
		 * Catálogo de justificaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			if (accionModulo.equals(accionCapturar)
					|| accionModulo.equals(accionModificar)) {
				if (nuevasJustificaciones != null
						&& !nuevasJustificaciones.isEmpty()) {

					for (int i = 0; i < nuevasJustificaciones.size(); i++) {
						if (nuevasJustificaciones
								.get(i)
								.getDescripcion()
								.equals(((DTOCJustificaciones) elimina)
										.getDescripcion())) {
							eliminar = true;
							index = i;
							break;
						}
					}

					if (eliminar) {
						nuevasJustificaciones.remove(index);
					}

				}
				if (nuevasJustificaciones.isEmpty()) {
					cargaSeleccionada = false;
				}
			}
		}

		/**
		 * Catálogo de evaluaciones
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			if (accionModulo.equals(accionCapturar)
					|| accionModulo.equals(accionModificar)) {
				if (nuevasEvaluaciones != null && !nuevasEvaluaciones.isEmpty()) {

					for (int i = 0; i < nuevasEvaluaciones.size(); i++) {
						if (nuevasEvaluaciones
								.get(i)
								.getDescripcion()
								.equals(((DTOCEvaluacion) elimina)
										.getDescripcion())) {
							eliminar = true;
							index = i;
							break;
						}
					}

					if (eliminar) {
						nuevasEvaluaciones.remove(index);
					}

				}
				if (nuevasEvaluaciones.isEmpty()) {
					cargaSeleccionada = false;
				}
			}
		}

	}

	/**
	 * Método para no quitar la justificacion seleccionada por defecto
	 * 
	 * @author Gerardo López
	 * @since 06/07/2017
	 * 
	 */
	public void justificacionSeleccionada() {
		justificacionesSeleccionadas.add(justificacionPorDefecto);
		log.info("TAMAÑO DE LA LISTA: " + justificacionesSeleccionadas.size());
	}

	/**
	 * 
	 */
	public void justificaToggle() {
		if (justificacionesSeleccionadas.size() > 1) {
			for (DTOCJustificaciones just : justificacionesPredefinidas) {
				if (just.getDTOCJustificacionesPK().getIdJustificacion()
						.equals(4)) {
					justificacionesSeleccionadas.add(just);
				}
			}
		} else {
			justificacionSeleccionada();
		}
	}

	/**
	 * Método para ordenar la escolaridad
	 * 
	 * @author Gerardo López
	 * @since 10/07/2017
	 * 
	 */
	public List<DTOCEscolaridades> ordenaEscolaridad(
			List<DTOCEscolaridades> listaEscolaridades) {
		List<DTOCEscolaridades> primarias = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> secundarias = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> prepaBachiller = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> licenciaturasPostgrado = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> especialidadMaestria = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> doctorados = new ArrayList<DTOCEscolaridades>();
		List<DTOCEscolaridades> others = new ArrayList<DTOCEscolaridades>();

		for (DTOCEscolaridades escolar : listaEscolaridades) {
			if (escolar.getDescripcion().contains("PRIMARIA")) {
				primarias.add(escolar);
			}
			if (escolar.getDescripcion().contains("SECUNDARIA")) {
				secundarias.add(escolar);
			}
			if (escolar.getDescripcion().contains("BACHILLERATO")
					|| escolar.getDescripcion().contains("PREPARATORIA")) {
				prepaBachiller.add(escolar);
			}
			if (escolar.getDescripcion().contains("LICENCIATURA")
					|| escolar.getDescripcion().contains("POSTGRADO")) {
				licenciaturasPostgrado.add(escolar);
			}
			if (escolar.getDescripcion().contains("ESPECIALIDAD")
					|| escolar.getDescripcion().contains("MAESTRIA")) {
				especialidadMaestria.add(escolar);
			}
			if (escolar.getDescripcion().contains("DOCTORADO")) {
				doctorados.add(escolar);
			}
			if (!escolar.getDescripcion().contains("PRIMARIA")
					&& !escolar.getDescripcion().contains("SECUNDARIA")
					&& !(escolar.getDescripcion().contains("BACHILLERATO") || escolar
							.getDescripcion().contains("PREPARATORIA"))
					&& !(escolar.getDescripcion().contains("LICENCIATURA") || escolar
							.getDescripcion().contains("POSTGRADO"))
					&& !(escolar.getDescripcion().contains("ESPECIALIDAD") || escolar
							.getDescripcion().contains("MAESTRIA"))
					&& !escolar.getDescripcion().contains("DOCTORADO")) {
				others.add(escolar);
			}

		}

		// Ordenamos primarias
		Collections.sort(primarias, new Comparator<DTOCEscolaridades>() {

			@Override
			public int compare(DTOCEscolaridades o1, DTOCEscolaridades o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos secundarias
		Collections.sort(secundarias, new Comparator<DTOCEscolaridades>() {

			@Override
			public int compare(DTOCEscolaridades o1, DTOCEscolaridades o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos prepas y bachilleratos
		Collections.sort(prepaBachiller, new Comparator<DTOCEscolaridades>() {

			@Override
			public int compare(DTOCEscolaridades o1, DTOCEscolaridades o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos licenciaturas y postgrados
		Collections.sort(licenciaturasPostgrado,
				new Comparator<DTOCEscolaridades>() {

					@Override
					public int compare(DTOCEscolaridades o1,
							DTOCEscolaridades o2) {
						return o1.getDescripcion().compareTo(
								o2.getDescripcion());
					}
				});

		// Ordenamos especialidades y maestrias
		Collections.sort(especialidadMaestria,
				new Comparator<DTOCEscolaridades>() {

					@Override
					public int compare(DTOCEscolaridades o1,
							DTOCEscolaridades o2) {
						return o1.getDescripcion().compareTo(
								o2.getDescripcion());
					}
				});

		// Ordenamos doctorado
		Collections.sort(doctorados, new Comparator<DTOCEscolaridades>() {

			@Override
			public int compare(DTOCEscolaridades o1, DTOCEscolaridades o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos Others
		Collections.sort(others, new Comparator<DTOCEscolaridades>() {

			@Override
			public int compare(DTOCEscolaridades o1, DTOCEscolaridades o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		List<DTOCEscolaridades> listaEscolaridadesOrdenada = new ArrayList<DTOCEscolaridades>();

		listaEscolaridadesOrdenada.addAll(primarias);
		listaEscolaridadesOrdenada.addAll(secundarias);
		listaEscolaridadesOrdenada.addAll(prepaBachiller);
		listaEscolaridadesOrdenada.addAll(licenciaturasPostgrado);
		listaEscolaridadesOrdenada.addAll(especialidadMaestria);
		listaEscolaridadesOrdenada.addAll(doctorados);
		listaEscolaridadesOrdenada.addAll(others);

		return listaEscolaridadesOrdenada;

	}

	/**
	 * Método para ordenar justificaciones
	 * 
	 * @author Gerardo López
	 * @since 10/07/2017
	 * 
	 */
	public List<DTOCJustificaciones> ordenaJustificacion(
			List<DTOCJustificaciones> listaJustificaciones) {
		List<DTOCJustificaciones> aprobadas = new ArrayList<DTOCJustificaciones>();
		List<DTOCJustificaciones> denegadas = new ArrayList<DTOCJustificaciones>();
		List<DTOCJustificaciones> canceladas = new ArrayList<DTOCJustificaciones>();
		List<DTOCJustificaciones> duplicadas = new ArrayList<DTOCJustificaciones>();
		List<DTOCJustificaciones> declinadas = new ArrayList<DTOCJustificaciones>();

		for (DTOCJustificaciones justificacion : listaJustificaciones) {
			if (justificacion.getResultado().equals('1')) {
				aprobadas.add(justificacion);
			}
			if (justificacion.getResultado().equals('2')) {
				denegadas.add(justificacion);
			}
			if (justificacion.getResultado().equals('3')) {
				canceladas.add(justificacion);
			}
			if (justificacion.getResultado().equals('4')) {
				duplicadas.add(justificacion);
			}
			if (justificacion.getResultado().equals('5')) {
				declinadas.add(justificacion);
			}

		}

		// Ordenamos aprobadas
		Collections.sort(aprobadas, new Comparator<DTOCJustificaciones>() {
			@Override
			public int compare(DTOCJustificaciones o1, DTOCJustificaciones o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos denegadas
		Collections.sort(denegadas, new Comparator<DTOCJustificaciones>() {
			@Override
			public int compare(DTOCJustificaciones o1, DTOCJustificaciones o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos canceladas
		Collections.sort(canceladas, new Comparator<DTOCJustificaciones>() {
			@Override
			public int compare(DTOCJustificaciones o1, DTOCJustificaciones o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos duplicadas
		Collections.sort(duplicadas, new Comparator<DTOCJustificaciones>() {
			@Override
			public int compare(DTOCJustificaciones o1, DTOCJustificaciones o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		// Ordenamos declinadas
		Collections.sort(declinadas, new Comparator<DTOCJustificaciones>() {
			@Override
			public int compare(DTOCJustificaciones o1, DTOCJustificaciones o2) {
				return o1.getDescripcion().compareTo(o2.getDescripcion());
			}
		});

		List<DTOCJustificaciones> listaJustificacionesOrdenadas = new ArrayList<DTOCJustificaciones>();

		listaJustificacionesOrdenadas.addAll(aprobadas);
		listaJustificacionesOrdenadas.addAll(denegadas);
		listaJustificacionesOrdenadas.addAll(canceladas);
		listaJustificacionesOrdenadas.addAll(duplicadas);
		listaJustificacionesOrdenadas.addAll(declinadas);

		return listaJustificacionesOrdenadas;

	}

	/**
	 * Método que carga la lista de detalles del proceso
	 * 
	 * @author Gerard López
	 * @since 12/07/2017
	 * 
	 */
	public void creaListaDetalles() {
		listaDetalles = new ArrayList<DTODetalleProcesoWS>();

		for (DTODetalleProcesoWS detallesAgregar : usuario.getListaDetalles()) {
			listaDetalles.add(detallesAgregar);
		}

		// Agregamos la opción de TODOS LOS DETALLES en la lista de Detalles
		// del
		// proceso
		int contadoFinal = listaDetalles.size();

		for (int i = 0; i < listaDetalles.size() + 1; i++) {
			if (i == contadoFinal) {
				DTODetalleProcesoWS todosDetallesProceso = new DTODetalleProcesoWS();
				todosDetallesProceso.setIdDetalleProceso(-3);
				todosDetallesProceso.setDescripcion("TODOS LOS DETALLES");
				listaDetalles.add(todosDetallesProceso);
			}
		}
		// Sí existe un sólo detalle-proceso el combo se bloquea, así que
		// cargamos ese detalle-proceso a nuestro objeto
		if (listaDetalles.size() == 2) {
			// TODO AQUI
//			procesoDetalle = listaDetalles.get(0);
			procesoDetalle.setIdProcesoElectoral(9);
			procesoDetalle.setIdDetalleProceso(38);
			comboDetalleUnico = true;
			cambiaCombo();
		}
		// }
	}

	public void cambiamosDetalle() {
		if (accionModulo.equals(accionCapturar)) {

			// Procesos
			if (idDetalleSeleccionado.equals(-3)) {
				sonTodos = true;
			} else {
				sonTodos = false;
				for (DTODetalleProcesoWS detalle : listaDetalles) {
					if (detalle.getIdDetalleProceso().equals(
							idDetalleSeleccionado)) {
						// TODO AQUI
//						procesoDetalle = detalle;
						 procesoDetalle.setIdProcesoElectoral(9);
						 procesoDetalle.setIdDetalleProceso(38);
					}
				}
			}

		} else {
			for (DTODetalleProcesoWS detalleConsulta : listaDetallesConsulta) {
				if (detalleConsulta.getIdDetalleProceso().equals(
						idDetalleSeleccionado)) {
					procesoDetalle = detalleConsulta;
				}
			}
		}
		log.info("PROCESO SELECCIONADO"
				+ procesoDetalle.getIdProcesoElectoral());
		log.info("DETALLE SELECCIONADO: "
				+ procesoDetalle.getIdDetalleProceso());
	}

	/**
	 * Método para cuando se cambia el combo de los detalles, proceso
	 * 
	 * @author Gerardon López
	 * @since 14/07/2017
	 * 
	 */
	public void cambiaCombo() {
		if (listaDetalles.size() == 2) {
			muestraForm = true;
			muestraConsulta();
		} else {
			if (idDetalleSeleccionado != null) {
				if (idDetalleSeleccionado.equals(-5)) {
					// Agrega mensaje en vista
					muestraForm = false;
				}
			} else {
				cambiamosDetalle();
				muestraConsulta();
			}
		}
	}

	/**
	 * Muestra la consulta cuando se selecciona el proceso detalle del combo
	 * para la vista de CONSULTAR y MODIFICAR
	 * 
	 * @author Gerardo López
	 * @since 14/07/2017
	 * 
	 */
	public void muestraConsulta() {

		log.info("CATALOG EN MUESTRACONSULTA: " + catalogo);
		/**
		 * Catalogo de acciones, sólo cuenta con la PK: idProceso, idDetalle,
		 * idAccion y con un campo en el nombre de la acción
		 */
		if (catalogo.equals(Constantes.CATALOGO_ACCIONES)) {

			// Obtenemos la carga inicial de acciones para presentarsela
			// al ADMIN
			try {
				accionesPredefinidas = bsdConfiguracionCatalogos.getCAcciones(
						procesoDetalle.getIdProcesoElectoral(),
						procesoDetalle.getIdDetalleProceso());
				if (accionesPredefinidas != null
						&& !accionesPredefinidas.isEmpty()) {
					accionesSeleccionadas.addAll(accionesPredefinidas);
					muestraForm = true;

				}
			} catch (Exception e) {
				log.error("Hubo un error al obtener el CATALOGO DE ACCIONES en initConsulta() de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
			}
		}

		/**
		 * Catálogo de cargo responsable, cuenta con la PK: idProceso,
		 * idDetalle, idCargo, y los campos de descripcion, iniciales, origen,
		 * idCargoAlterno
		 */
		if (catalogo.equals(Constantes.CATALOGO_CARGO_RESPONSABLE)) {
			// Obtenemos la carga inicial de acciones para presentarsela al
			// ADMIN
			try {
				cargosPredefinidos = bsdConfiguracionCatalogos
						.getCCargoResponsable(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());

				if (cargosPredefinidos != null && !cargosPredefinidos.isEmpty()) {
					cargosSeleccionados.addAll(cargosPredefinidos);
					muestraForm = true;
				}
			} catch (Exception e) {
				log.error("Hubo un error al obtener el CATALOGO DE CARGO DE RESPONSABLE en initConsulta() de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
			}
		}

		/**
		 * Catálogo de escolaridades, cuenta con la PK: idProceso, idDetalle,
		 * idCargo, y el campo de descripcion
		 */
		if (catalogo.equals(Constantes.CATALOGO_ESCOLARIDADES)) {
			// Obtenemos la carga inicial de acciones para presentarsela al
			// ADMIN
			try {
				escolaridadesPredefinidas = bsdConfiguracionCatalogos
						.getCEscolaridades(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());
				if (escolaridadesPredefinidas != null
						&& !escolaridadesPredefinidas.isEmpty()) {
					escolaridadesSeleccionadas
							.addAll(escolaridadesPredefinidas);
					muestraForm = true;
				}
			} catch (Exception e) {
				log.error("Hubo un error al obtener el CATALOGO DE ESCOLARIDADES en initConsulta() de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
			}
		}

		/**
		 * Catálogo de justificaciones, cuenta con la PK: idProceso, idDetalle,
		 * idCargo, y los campos de descripcion, descripcionCorta y resultado
		 */
		if (catalogo.equals(Constantes.CATALOGO_JUSTIFICACIONES)) {
			// Obtenemos la carga inicial de acciones para presentarsela al
			// ADMIN
			try {
				if (accionModulo.equals(accionConsultar)) {
					justificacionesPredefinidas = bsdConfiguracionCatalogos
							.getCJustificaciones(
									procesoDetalle.getIdProcesoElectoral(),
									procesoDetalle.getIdDetalleProceso());
					int indexConsulta = 0;
					boolean encontrado = false;
					DTOCJustificaciones justificacion = new DTOCJustificaciones();
					for (int j = 0; j < justificacionesPredefinidas.size(); j++) {
						if (justificacionesPredefinidas
								.get(j)
								.getDTOCJustificacionesPK()
								.getIdJustificacion()
								.equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO_GUARDADO)) {
							justificacion = justificacionesPredefinidas.get(j);
							justificacionesXDefault
									.add(justificacionesPredefinidas.get(j));
							indexConsulta = j;
							encontrado = true;
							break;
						}
					}

					if (encontrado) {
						justificacionesPredefinidas.remove(indexConsulta);
					}

					justificacionesPredefinidas = ordenaJustificacion(justificacionesPredefinidas);

				}

				if (accionModulo.equals(accionModificar)) {
					justificacionesPredefinidas = bsdConfiguracionCatalogos
							.getCJustificaciones(
									procesoDetalle.getIdProcesoElectoral(),
									procesoDetalle.getIdDetalleProceso());
					// Obtener la justificacion por default
					boolean esEncontrada = false;
					int index = 0;
					for (int i = 0; i < justificacionesPredefinidas.size(); i++) {
						if (justificacionesPredefinidas
								.get(i)
								.getDTOCJustificacionesPK()
								.getIdJustificacion()
								.equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO_GUARDADO)) {
							justificacionesXDefault
									.add(justificacionesPredefinidas.get(i));
							esEncontrada = true;
							index = i;
							break;
						}
					}

					if (esEncontrada) {
						justificacionesPredefinidas.remove(index);
						if (justificacionesPredefinidas != null
								&& !justificacionesPredefinidas.isEmpty()) {

							justificacionesPredefinidas = ordenaJustificacion(justificacionesPredefinidas);

							justificacionesSeleccionadas
									.addAll(justificacionesPredefinidas);
							muestraForm = true;
						}
					}
				}

			} catch (Exception e) {
				log.error("Hubo un error al obtener el CATALOGO DE JUSTIFICACIONES en initConsulta() de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
			}

			// Si no existe, enviamos mensaje y no mostamos formulario
		}

		/**
		 * Catálogo de evaluaciones, cuenta con la PK: idProceso, idDetalle,
		 * idCargo, y los campos de descripcion y tipo
		 */
		if (catalogo.equals(Constantes.CATALOGO_EVALUACIONES)) {
			// Obtenemos la carga inicial de acciones para presentarsela al
			// ADMIN
			try {

				evaluacionesPredefinidas = new ArrayList<DTOCEvaluacion>();

				evaluacionesPredefinidas = bsdConfiguracionCatalogos
						.getCEvaluaciones(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso());

				for (DTOCEvaluacion evaluacion : evaluacionesPredefinidas) {
					List<DTOReglasEvalucaion> reglas = new ArrayList<DTOReglasEvalucaion>();
					reglas = bsdConfiguracionCatalogos.getReglasEvaluacion(
							procesoDetalle.getIdProcesoElectoral(),
							procesoDetalle.getIdDetalleProceso(), evaluacion
									.getDTOCEvaluacionPK().getIdEvaluacion());

					if (reglas != null && !reglas.isEmpty()) {
						evaluacion.setDTOReglasEvalucaionList(reglas);
					}

				}

				if (accionModulo.equals(accionModificar)) {
					// Obtener la justificacion por default
					boolean esEncontrada = false;
					List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
					evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
					for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {

						if (i == evaluacionesPredefinidas.size() - 2
								|| i == evaluacionesPredefinidas.size() - 1) {

							evaluacionesXDefault.add(evaluacionesPredefinidas
									.get(i));
							indexs.add(evaluacionesPredefinidas.get(i));

						}
					}

					if (indexs != null && !indexs.isEmpty()) {
						esEncontrada = true;
					}

					if (esEncontrada) {
						evaluacionesPredefinidas.removeAll(indexs);
						if (evaluacionesPredefinidas != null
								&& !evaluacionesPredefinidas.isEmpty()) {
							evaluacionesSeleccionadas
									.addAll(evaluacionesPredefinidas);
							muestraForm = true;
						}
					}
				}

				contadorIdSiguiente = evaluacionesPredefinidas.size() + 1;

			} catch (Exception e) {
				log.error("Hubo un error al obtener el CATALOGO DE EVALUACIONES en initConsulta() de MBConfiguracionCatalogos");
				log.error(e);
				e.printStackTrace();
			}

			// Si no existe, enviamos mensaje y no mostamos formulario
		}

	}

	/**
	 * Método que muestra las reglas asociadas a la evaluacion seleccionada de
	 * la tabla de la carga incial
	 * 
	 * @author Gerardo López
	 * @since 10/08/2017
	 */
	public void obtenReglasPorEvaluacionInicial(DTOCEvaluacion evaluacion,
			String modulo) {
		try {
			creaListaDetalles();
			boolean esEncontradoOrigen = false;
			origenCurso = null;
			reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
			if (modulo.equals(accionCapturar)) {
				reglasAsociadas = bsdConfiguracionCatalogos
						.getReglasEvaluacion(sinIdProceso, sinIdDetalle,
								evaluacion.getDTOCEvaluacionPK()
										.getIdEvaluacion());

				esCurso = false;
				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					if (regla.getDTOReglasEvalucaionPK().getIdRegla()
							.equals(Constantes.ID_REGLA_CURSO)) {
						esEncontradoOrigen = true;
						origenCurso = regla.getOrigenCurso();
						break;
					}
				}

				if (esEncontradoOrigen) {
					if (origenCurso != null) {
						esCurso = true;
					} else {
						esCurso = false;
						origenCurso = null;
					}
				}

			}
			if (modulo.equals(accionModificar)) {
				reglasAsociadas = bsdConfiguracionCatalogos
						.getReglasEvaluacion(procesoDetalle
								.getIdProcesoElectoral(), procesoDetalle
								.getIdDetalleProceso(), evaluacion
								.getDTOCEvaluacionPK().getIdEvaluacion());

				esCurso = false;
				// TODO
				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					if (regla.getDTOReglasEvalucaionPK().getIdRegla()
							.equals(Constantes.ID_REGLA_CURSO)) {
						esEncontradoOrigen = true;
						origenCurso = regla.getOrigenCurso();
						break;
					}
				}

				if (esEncontradoOrigen) {
					if (origenCurso != null) {
						esCurso = true;
					} else {
						esCurso = false;
						origenCurso = null;
					}
				}

			}

			boolean esEncontradaReglaCurso = false;
			nuevasReglasSeleccionadas = new ArrayList<DTOReglas>();

			for (DTOCEvaluacion evaluacionPredefinida : evaluacionesSeleccionadas) {
				if (evaluacionPredefinida
						.getDTOCEvaluacionPK()
						.getIdEvaluacion()
						.equals(evaluacion.getDTOCEvaluacionPK()
								.getIdEvaluacion())) {

					if (reglasAsociadasPredefinidas != null
							&& !reglasAsociadasPredefinidas.isEmpty()) {
						for (DTOReglasEvalucaion rule : reglasAsociadasPredefinidas) {
							if (rule.getDTOReglasEvalucaionPK()
									.getIdEvaluacion()
									.equals(evaluacion.getDTOCEvaluacionPK()
											.getIdEvaluacion())) {
								reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
								for (DTOReglasEvalucaion reg : reglasAsociadasPredefinidas) {
									if (reg.getDTOReglasEvalucaionPK()
											.getIdEvaluacion()
											.equals(evaluacion
													.getDTOCEvaluacionPK()
													.getIdEvaluacion())) {
										reglasAsociadas.add(reg);

										if (reg.getDTOReglasEvalucaionPK()
												.getIdRegla()
												.equals(Constantes.ID_REGLA_CURSO)) {
											origenCurso = reg.getOrigenCurso();
											esEncontradaReglaCurso = true;

										}
									}
								}

								if (esEncontradaReglaCurso) {
									esCurso = true;
								} else {
									esCurso = false;
								}
							}
						}
					}

					for (DTOReglasEvalucaion regla : reglasAsociadas) {

						for (int i = 0; i < reglasEvaluacion.size(); i++) {
							if (regla
									.getDTOReglasEvalucaionPK()
									.getIdRegla()
									.equals(reglasEvaluacion.get(i)
											.getIdRegla())) {

								// if (!nuevasReglasSeleccionadas
								// .contains(reglasEvaluacion.get(i))) {

								nuevasReglasSeleccionadas.add(reglasEvaluacion
										.get(i));

								// }
							}
						}
					}
				}
			}

			List<DTOReglasEvalucaion> indexRegla = new ArrayList<DTOReglasEvalucaion>();
			if (reglasEvalPredefinidasGuardar != null
					&& !reglasEvalPredefinidasGuardar.isEmpty()) {
				for (DTOReglasEvalucaion reglas : reglasAsociadas) {
					for (DTOReglasEvalucaion rule : reglasEvalPredefinidasGuardar) {
						if (reglas
								.getDTOReglasEvalucaionPK()
								.getIdEvaluacion()
								.equals(rule.getDTOReglasEvalucaionPK()
										.getIdEvaluacion())) {
							indexRegla.add(reglas);
						}
					}
				}

				if (indexRegla != null && !indexRegla.isEmpty()) {
					for (DTOReglasEvalucaion indexRule : indexRegla) {
						reglasEvalPredefinidasGuardar.remove(indexRule);
						reglasEvalPredefinidasGuardar.add(indexRule);
					}
				}

			} else {
				for (DTOReglasEvalucaion rule : reglasAsociadas) {
					reglasEvalPredefinidasGuardar.add(rule);
				}
			}

			reglasAsociadasEva = new ArrayList<DTOReglas>();
			reglasAsociadasEva.addAll(reglasEvaluacion);

			if (modulo.equals(accionCapturar)) {
				// No perdemos nuestra vista
				evaluacionesPredefinidas = bsdConfiguracionCatalogos
						.getCEvaluaciones(sinIdProceso, sinIdDetalle);

				// Obtener la justificacion por default
				boolean esEncontrada = false;
				List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
				evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
				for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {

					if (i == evaluacionesPredefinidas.size() - 2
							|| i == evaluacionesPredefinidas.size() - 1) {

						evaluacionesXDefault.add(evaluacionesPredefinidas
								.get(i));
						indexs.add(evaluacionesPredefinidas.get(i));

					}
				}

				if (indexs != null && !indexs.isEmpty()) {
					esEncontrada = true;
				}

				if (esEncontrada) {
					evaluacionesPredefinidas.removeAll(indexs);
					if (evaluacionesPredefinidas != null
							&& !evaluacionesPredefinidas.isEmpty()) {
						evaluacionesSeleccionadas
								.addAll(evaluacionesPredefinidas);
						muestraForm = true;
					}
				}
			}

		} catch (Exception e) {
			log.error("Hubo un error al obtener las reglas de la evaluacion seleccionada en obtenReglasPorEvaluacion() de MBConfiguracionCatalogos");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método que muestra las reglas asociadas a la evaluacion seleccionada de
	 * la tabla de la carga incial - Por defecto
	 * 
	 * @author Gerardo López
	 * @since 16/08/2017
	 */
	public void obtenReglasPorEvaluacionDefecto(DTOCEvaluacion evaluacion,
			String modulo) {
		try {
			creaListaDetalles();

			boolean esEncontradoOrigen = false;
			origenCurso = null;
			reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
			if (modulo.equals(accionCapturar)) {
				reglasAsociadas = bsdConfiguracionCatalogos
						.getReglasEvaluacion(sinIdProceso, sinIdDetalle,
								evaluacion.getDTOCEvaluacionPK()
										.getIdEvaluacion());

				esCurso = false;
				// TODO
				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					if (regla.getDTOReglasEvalucaionPK().getIdRegla()
							.equals(Constantes.ID_REGLA_CURSO)) {
						esEncontradoOrigen = true;
						origenCurso = regla.getOrigenCurso();
					}
				}

				if (esEncontradoOrigen) {
					if (origenCurso != null) {
						esCurso = true;
					} else {
						esCurso = false;
						origenCurso = null;
					}
				}
			}

			if (modulo.equals(accionModificar)) {
				reglasAsociadas = bsdConfiguracionCatalogos
						.getReglasEvaluacion(procesoDetalle
								.getIdProcesoElectoral(), procesoDetalle
								.getIdDetalleProceso(), evaluacion
								.getDTOCEvaluacionPK().getIdEvaluacion());

				esCurso = false;
				// TODO
				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					if (regla.getDTOReglasEvalucaionPK().getIdRegla()
							.equals(Constantes.ID_REGLA_CURSO)) {
						esEncontradoOrigen = true;
						origenCurso = regla.getOrigenCurso();
						break;
					}
				}

				if (esEncontradoOrigen) {
					if (origenCurso != null) {
						esCurso = true;
					} else {
						esCurso = false;
						origenCurso = null;
					}
				}

			}

			boolean esEncontradaReglaCurso = false;
			nuevasReglasSeleccionadas = new ArrayList<DTOReglas>();

			// Evaluaciones por defecto
			for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {
				if (evaluacionPorDefecto
						.getDTOCEvaluacionPK()
						.getIdEvaluacion()
						.equals(evaluacion.getDTOCEvaluacionPK()
								.getIdEvaluacion())) {

					if (reglasAsociadasPorDefecto != null
							&& !reglasAsociadasPorDefecto.isEmpty()) {
						for (DTOReglasEvalucaion rule : reglasAsociadasPorDefecto) {
							if (rule.getDTOReglasEvalucaionPK()
									.getIdEvaluacion()
									.equals(evaluacion.getDTOCEvaluacionPK()
											.getIdEvaluacion())) {
								reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
								for (DTOReglasEvalucaion reg : reglasAsociadasPorDefecto) {
									if (reg.getDTOReglasEvalucaionPK()
											.getIdEvaluacion()
											.equals(evaluacion
													.getDTOCEvaluacionPK()
													.getIdEvaluacion())) {
										reglasAsociadas.add(reg);

										if (reg.getDTOReglasEvalucaionPK()
												.getIdRegla()
												.equals(Constantes.ID_REGLA_CURSO)) {
											origenCurso = reg.getOrigenCurso();
											esEncontradaReglaCurso = true;
										}

									}
								}

								if (esEncontradaReglaCurso) {
									esCurso = true;
								} else {
									esCurso = false;
								}
							}
						}
					}

					for (DTOReglasEvalucaion regla : reglasAsociadas) {

						for (int i = 0; i < reglasEvaluacion.size(); i++) {
							if (regla
									.getDTOReglasEvalucaionPK()
									.getIdRegla()
									.equals(reglasEvaluacion.get(i)
											.getIdRegla())) {

								// if (!nuevasReglasSeleccionadas
								// .contains(reglasEvaluacion.get(i))) {

								nuevasReglasSeleccionadas.add(reglasEvaluacion
										.get(i));

								// }
							}
						}
					}
				}
			}

			List<DTOReglasEvalucaion> indexRegla = new ArrayList<DTOReglasEvalucaion>();
			if (reglasEvalPorDefectoGuardar != null
					&& !reglasEvalPorDefectoGuardar.isEmpty()) {
				for (DTOReglasEvalucaion reglas : reglasAsociadas) {
					for (DTOReglasEvalucaion rule : reglasEvalPorDefectoGuardar) {
						if (reglas
								.getDTOReglasEvalucaionPK()
								.getIdEvaluacion()
								.equals(rule.getDTOReglasEvalucaionPK()
										.getIdEvaluacion())) {
							indexRegla.add(reglas);
						}
					}
				}

				if (indexRegla != null && !indexRegla.isEmpty()) {
					for (DTOReglasEvalucaion indexRule : indexRegla) {
						reglasEvalPorDefectoGuardar.remove(indexRule);
						reglasEvalPorDefectoGuardar.add(indexRule);
					}
				}

			} else {
				for (DTOReglasEvalucaion rule : reglasAsociadas) {
					reglasEvalPorDefectoGuardar.add(rule);
				}
			}

			reglasAsociadasEva = new ArrayList<DTOReglas>();
			reglasAsociadasEva.addAll(reglasEvaluacion);

			if (modulo.equals(accionCapturar)) {
				// No perdemos nuestra vista
				evaluacionesPredefinidas = bsdConfiguracionCatalogos
						.getCEvaluaciones(sinIdProceso, sinIdDetalle);

				// Obtener la justificacion por default
				boolean esEncontrada = false;
				List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
				evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
				for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {

					if (i == evaluacionesPredefinidas.size() - 2
							|| i == evaluacionesPredefinidas.size() - 1) {

						evaluacionesXDefault.add(evaluacionesPredefinidas
								.get(i));
						indexs.add(evaluacionesPredefinidas.get(i));

					}
				}

				if (indexs != null && !indexs.isEmpty()) {
					esEncontrada = true;
				}

				if (esEncontrada) {
					evaluacionesPredefinidas.removeAll(indexs);
					if (evaluacionesPredefinidas != null
							&& !evaluacionesPredefinidas.isEmpty()) {
						evaluacionesSeleccionadas
								.addAll(evaluacionesPredefinidas);
						muestraForm = true;
					}
				}
			}

		} catch (Exception e) {
			log.error("Hubo un error al obtener las reglas de la evaluacion seleccionada en obtenReglasPorEvaluacion() de MBConfiguracionCatalogos");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método que muestra las reglas asociadas a la evaluacion seleccionada de
	 * la tabla de evaluaciones agregadas por el usuario
	 * 
	 * @author Gerardo López
	 * @since 10/08/2017
	 */
	public void obtenReglasPorEvaluacionUsuario(DTOCEvaluacion evaluacion,
			String modulo) {
		try {
			creaListaDetalles();
			boolean esEncontradoOrigen = false;
			esCurso = false;
			nuevasReglasSeleccionadas = new ArrayList<DTOReglas>();
			reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();
			if (modulo.equals(accionCapturar)) {
				for (DTOCEvaluacion evaluacionNueva : nuevasEvaluacionesSeleccionadas) {
					for (DTOReglasEvalucaion regla : evaluacionNueva
							.getDTOReglasEvalucaionList()) {
						if (evaluacionNueva
								.getDTOCEvaluacionPK()
								.getIdEvaluacion()
								.equals(evaluacion.getDTOCEvaluacionPK()
										.getIdEvaluacion())) {

							if (regla.getDTOReglasEvalucaionPK().getIdRegla()
									.equals(Constantes.ID_REGLA_CURSO)) {
								esEncontradoOrigen = true;
								origenCurso = regla.getOrigenCurso();
							}

							for (int i = 0; i < reglasEvaluacion.size(); i++) {
								if (regla
										.getDTOReglasEvalucaionPK()
										.getIdRegla()
										.equals(reglasEvaluacion.get(i)
												.getIdRegla())) {

									nuevasReglasSeleccionadas
											.add(reglasEvaluacion.get(i));

								}
							}

						}
					}
				}

				if (esEncontradoOrigen) {
					if (origenCurso != null) {
						esCurso = true;
					} else {
						esCurso = false;
						origenCurso = null;
					}
				}

				reglasAsociadasEva = new ArrayList<DTOReglas>();
				reglasAsociadasEva.addAll(reglasEvaluacion);

				// No perdemos nuestra vista
				evaluacionesPredefinidas = bsdConfiguracionCatalogos
						.getCEvaluaciones(sinIdProceso, sinIdDetalle);

				// Obtener la justificacion por default
				boolean esEncontrada = false;
				List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
				evaluacionesXDefault = new ArrayList<DTOCEvaluacion>();
				for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {

					if (i == evaluacionesPredefinidas.size() - 2
							|| i == evaluacionesPredefinidas.size() - 1) {

						evaluacionesXDefault.add(evaluacionesPredefinidas
								.get(i));
						indexs.add(evaluacionesPredefinidas.get(i));

					}
				}

				if (indexs != null && !indexs.isEmpty()) {
					esEncontrada = true;
				}

				if (esEncontrada) {
					evaluacionesPredefinidas.removeAll(indexs);
					if (evaluacionesPredefinidas != null
							&& !evaluacionesPredefinidas.isEmpty()) {
						evaluacionesSeleccionadas
								.addAll(evaluacionesPredefinidas);
						muestraForm = true;
					}
				}

			} else {
				reglasAsociadas = bsdConfiguracionCatalogos
						.getReglasEvaluacion(procesoDetalle
								.getIdProcesoElectoral(), procesoDetalle
								.getIdDetalleProceso(), evaluacion
								.getDTOCEvaluacionPK().getIdEvaluacion());

				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					for (int i = 0; i < reglasEvaluacion.size(); i++) {
						if (regla.getDTOReglasEvalucaionPK().getIdRegla()
								.equals(reglasEvaluacion.get(i).getIdRegla())) {
							reglasAsociadasEva.add(reglasEvaluacion.get(i));
						}
					}
				}

				nuevasReglasSeleccionadas.addAll(reglasAsociadasEva);

				if (accionModulo.equals(accionCapturar)) {
					// No perdemos nuestra vista
					evaluacionesPredefinidas = bsdConfiguracionCatalogos
							.getCEvaluaciones(sinIdProceso, sinIdDetalle);

					// justificacionesSeleccionadas
					// .addAll(justificacionesPredefinidas);

					List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
					boolean esEncontrado = false;
					for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {
						// if (justificaciones
						// .getDTOCJustificacionesPK()
						// .getIdJustificacion()
						// .equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO))
						// {
						if (evaluacionesPredefinidas
								.get(i)
								.getDTOCEvaluacionPK()
								.getIdEvaluacion()
								.equals(Constantes.ID_EVALUACION_POR_DEFECTO_NA)
								|| evaluacionesPredefinidas
										.get(i)
										.getDTOCEvaluacionPK()
										.getIdEvaluacion()
										.equals(Constantes.ID_EVALUACION_POR_DEFECTO_P)) {

							indexs.add(evaluacionesPredefinidas.get(i));

						}
					}

					if (indexs != null && !indexs.isEmpty()) {
						esEncontrado = true;
					}

					if (esEncontrado) {
						log.info("Tamaño de la lista de indexs: "
								+ indexs.size());
						for (DTOCEvaluacion evaluacionAEliminar : indexs) {
							evaluacionesPredefinidas
									.remove(evaluacionAEliminar);
						}
						evaluacionesSeleccionadas
								.addAll(evaluacionesPredefinidas);
					}
				}
			}

		} catch (Exception e) {
			log.error("Hubo un error al obtener las reglas de la evaluacion seleccionada en obtenReglasPorEvaluacion() de MBConfiguracionCatalogos");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método que muestra las reglas asociadas a la evaluacion seleccionada de
	 * la tabla de consulta
	 * 
	 * @author Gerardo López
	 * @since 10/08/2017
	 */
	public void obtenReglasPorEvaluacionConsulta(DTOCEvaluacion evaluacion,
			String modulo) {
		try {
			creaListaDetalles();

			boolean esReglaCurso = false;

			reglasAsociadas = new ArrayList<DTOReglasEvalucaion>();

			reglasAsociadas = bsdConfiguracionCatalogos.getReglasEvaluacion(
					procesoDetalle.getIdProcesoElectoral(), procesoDetalle
							.getIdDetalleProceso(), evaluacion
							.getDTOCEvaluacionPK().getIdEvaluacion());

			// Verificamos si las reglas asociadas tienen la regla 1 para
			// asociar el origen del curso
			for (DTOReglasEvalucaion regla : reglasAsociadas) {
				if (regla.getDTOReglasEvalucaionPK().getIdRegla()
						.equals(Constantes.ID_REGLA_CURSO)) {
					esReglaCurso = true;
					origenCurso = regla.getOrigenCurso();
					break;
				}
			}

			if (esReglaCurso) {
				esCurso = true;
			} else {
				esCurso = false;
				origenCurso = null;
			}

			if (modulo.equals(accionConsultar)) {
				for (DTOReglasEvalucaion regla : reglasAsociadas) {
					for (int i = 0; i < reglasEvaluacion.size(); i++) {
						if (regla.getDTOReglasEvalucaionPK().getIdRegla()
								.equals(reglasEvaluacion.get(i).getIdRegla())) {
							reglasAsociadasEva.add(reglasEvaluacion.get(i));
						}
					}
				}

				nuevasReglasSeleccionadas.addAll(reglasAsociadasEva);
			} else {
				// No perdemos nuestra vista
				evaluacionesPredefinidas = bsdConfiguracionCatalogos
						.getCEvaluaciones(sinIdProceso, sinIdDetalle);

				// justificacionesSeleccionadas
				// .addAll(justificacionesPredefinidas);

				List<DTOCEvaluacion> indexs = new ArrayList<DTOCEvaluacion>();
				boolean esEncontrado = false;
				for (int i = 0; i < evaluacionesPredefinidas.size(); i++) {
					// if (justificaciones
					// .getDTOCJustificacionesPK()
					// .getIdJustificacion()
					// .equals(Constantes.ID_JUSTIFICACION_POR_DEFECTO_CATALOGO))
					// {
					if (evaluacionesPredefinidas.get(i).getDTOCEvaluacionPK()
							.getIdEvaluacion()
							.equals(Constantes.ID_EVALUACION_POR_DEFECTO_NA)
							|| evaluacionesPredefinidas
									.get(i)
									.getDTOCEvaluacionPK()
									.getIdEvaluacion()
									.equals(Constantes.ID_EVALUACION_POR_DEFECTO_P)) {

						indexs.add(evaluacionesPredefinidas.get(i));

					}
				}

				if (indexs != null && !indexs.isEmpty()) {
					esEncontrado = true;
				}

				if (esEncontrado) {
					log.info("Tamaño de la lista de indexs: " + indexs.size());
					for (DTOCEvaluacion evaluacionAEliminar : indexs) {
						evaluacionesPredefinidas.remove(evaluacionAEliminar);
					}
					evaluacionesSeleccionadas.addAll(evaluacionesPredefinidas);
				}

			}

		} catch (Exception e) {
			log.error("Hubo un error al obtener las reglas de la evaluacion seleccionada en obtenReglasPorEvaluacion() de MBConfiguracionCatalogos");
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método para agregar reglas asociadas seleccionadas
	 * 
	 * @author Gerardo López
	 * @since 15/08/2017
	 * 
	 */
	public void guardaReglasAsociadasSeleccionadas() {

		List<DTOReglasEvalucaion> reglasTmp = new ArrayList<DTOReglasEvalucaion>();
		List<DTOReglasEvalucaion> reglasTmpDefecto = new ArrayList<DTOReglasEvalucaion>();
		// reglasAsociadasPredefinidas = new ArrayList<DTOReglasEvalucaion>();
		// Evaluaciones predefinidas seleccionadas
		HashSet<DTOReglas> limpiaReglas = new HashSet<DTOReglas>();
		limpiaReglas.addAll(nuevasReglasSeleccionadas);
		nuevasReglasSeleccionadas = new ArrayList<DTOReglas>();
		nuevasReglasSeleccionadas.addAll(limpiaReglas);
		if (numeroCurso != null) {
			origenCurso = numeroCurso;
		}
		if (evaluacionesSeleccionadas != null
				&& !evaluacionesSeleccionadas.isEmpty()) {
			for (DTOCEvaluacion evaluacionPredefinida : evaluacionesSeleccionadas) {
				if (evaluacionPredefinida.equals(selectEvaluacion)) {
					for (DTOReglas regla : nuevasReglasSeleccionadas) {

						DTOReglasEvalucaion reglaEva = new DTOReglasEvalucaion();
						DTOReglasEvalucaionPK pkRegla = new DTOReglasEvalucaionPK(
								procesoDetalle.getIdProcesoElectoral(),
								procesoDetalle.getIdDetalleProceso(),
								evaluacionPredefinida.getDTOCEvaluacionPK()
										.getIdEvaluacion(), regla.getIdRegla());

						reglaEva.setDTOReglasEvalucaionPK(pkRegla);

						if (regla.getIdRegla()
								.equals(Constantes.ID_REGLA_CURSO)) {
							if (origenCurso != null && origenCurso > 0) {
								reglaEva.setOrigenCurso(origenCurso);
							}
						}

						reglasTmp.add(reglaEva);
						evaluacionPredefinida.setDTOReglasEvalucaionList(null);
						evaluacionPredefinida
								.setDTOReglasEvalucaionList(reglasTmp);

						reglasAsociadasPredefinidas.add(reglaEva);
					}
				}
			}
		}

		// Para las evaluaciones predefinidas
		if (reglasAsociadasPredefinidas != null
				&& !reglasAsociadasPredefinidas.isEmpty()) {
			List<DTOReglasEvalucaion> reglasIndexs = new ArrayList<DTOReglasEvalucaion>();
			boolean encontrado = false;
			for (DTOReglasEvalucaion rule : reglasTmp) {
				for (DTOReglasEvalucaion reglaAsociada : reglasAsociadasPredefinidas) {
					if (reglaAsociada
							.getDTOReglasEvalucaionPK()
							.getIdEvaluacion()
							.equals(rule.getDTOReglasEvalucaionPK()
									.getIdEvaluacion())) {
						reglasIndexs.add(reglaAsociada);
						encontrado = true;
					}
				}
			}

			if (encontrado) {
				reglasAsociadasPredefinidas.removeAll(reglasIndexs);
			}

			reglasAsociadasPredefinidas.addAll(reglasTmp);

		}

		// Evaluaciones por defecto
		if (evaluacionesXDefault != null && !evaluacionesXDefault.isEmpty()) {
			for (DTOCEvaluacion evaluacionPorDefecto : evaluacionesXDefault) {
				if (evaluacionPorDefecto.equals(selectEvaluacion)) {
					for (DTOReglas regla : nuevasReglasSeleccionadas) {

						DTOReglasEvalucaion reglaEva = new DTOReglasEvalucaion();
						DTOReglasEvalucaionPK pkRegla = new DTOReglasEvalucaionPK(
								evaluacionPorDefecto.getDTOCEvaluacionPK()
										.getIdProcesoElectoral(),
								evaluacionPorDefecto.getDTOCEvaluacionPK()
										.getIdDetalleProceso(),
								evaluacionPorDefecto.getDTOCEvaluacionPK()
										.getIdEvaluacion(), regla.getIdRegla());

						reglaEva.setDTOReglasEvalucaionPK(pkRegla);

						if (regla.getIdRegla()
								.equals(Constantes.ID_REGLA_CURSO)) {
							if (origenCurso != null && origenCurso > 0) {
								reglaEva.setOrigenCurso(origenCurso);
							}
						}

						reglasTmpDefecto.add(reglaEva);
						evaluacionPorDefecto.setDTOReglasEvalucaionList(null);
						evaluacionPorDefecto
								.setDTOReglasEvalucaionList(reglasTmpDefecto);

						reglasAsociadasPorDefecto.add(reglaEva);

					}
				}
			}
		}

		// Para las evaluaciones por defecto
		if (reglasAsociadasPorDefecto != null
				&& !reglasAsociadasPorDefecto.isEmpty()) {
			List<DTOReglasEvalucaion> reglasIndexs = new ArrayList<DTOReglasEvalucaion>();
			boolean encontrado = false;
			for (DTOReglasEvalucaion rule : reglasTmpDefecto) {
				for (DTOReglasEvalucaion reglaAsociada : reglasAsociadasPorDefecto) {
					if (reglaAsociada
							.getDTOReglasEvalucaionPK()
							.getIdEvaluacion()
							.equals(rule.getDTOReglasEvalucaionPK()
									.getIdEvaluacion())) {
						reglasIndexs.add(reglaAsociada);
						encontrado = true;
					}
				}
			}

			if (encontrado) {
				reglasAsociadasPorDefecto.removeAll(reglasIndexs);
			}

			reglasAsociadasPorDefecto.addAll(reglasTmpDefecto);

		}

		// Evaluaciones agregadas seleccionadas
		if (nuevasEvaluacionesSeleccionadas != null
				&& !nuevasEvaluacionesSeleccionadas.isEmpty()) {
			for (DTOCEvaluacion evaluacionAgregada : nuevasEvaluacionesSeleccionadas) {
				if (evaluacionAgregada.equals(selectEvaluacion)) {
					for (DTOReglas regla : nuevasReglasSeleccionadas) {

						DTOReglasEvalucaion reglaEva = new DTOReglasEvalucaion();
						DTOReglasEvalucaionPK pkRegla = new DTOReglasEvalucaionPK(
								evaluacionAgregada.getDTOCEvaluacionPK()
										.getIdProcesoElectoral(),
								evaluacionAgregada.getDTOCEvaluacionPK()
										.getIdDetalleProceso(),
								evaluacionAgregada.getDTOCEvaluacionPK()
										.getIdEvaluacion(), regla.getIdRegla());

						reglaEva.setDTOReglasEvalucaionPK(pkRegla);

						if (regla.getIdRegla()
								.equals(Constantes.ID_REGLA_CURSO)) {
							if (origenCurso != null && origenCurso > 0) {
								reglaEva.setOrigenCurso(origenCurso);
							}
						}

						reglasTmp.add(reglaEva);
						evaluacionAgregada.setDTOReglasEvalucaionList(null);
						evaluacionAgregada
								.setDTOReglasEvalucaionList(reglasTmp);

					}
				}
			}
		}

		reglasSeleccionadas = new ArrayList<DTOReglas>();
		origenCurso = null;
		numeroCurso = null;

	}

	/**
	 * Método que identifica cuando seleccionan la regla 1 para la fecha del
	 * curso y visualizar el combo de origenes del curso
	 * 
	 * @author Gerardo López
	 * @since 22/08/2017
	 * 
	 */
	public void seleccionaRegla() {
		boolean seEncuentra = false;
		if (reglasSeleccionadas != null && !reglasSeleccionadas.isEmpty()) {
			for (DTOReglas regla : reglasSeleccionadas) {
				if (regla.getIdRegla().equals(Constantes.ID_REGLA_CURSO)) {
					seEncuentra = true;
				}
			}
		}

		if (seEncuentra) {
			esCurso = true;
		} else {
			esCurso = false;
			origenCurso = null;
		}

	}

	/**
	 * Método que identifica cuando deseleccionan la regla 1 para la fecha del
	 * curso y quitar el combo de origenes del curso de vista
	 * 
	 * @author Gerardo López
	 * @since 22/08/2017
	 * 
	 */
	public void deseleccionaRegla() {
		boolean seEncuentra = false;
		if (reglasSeleccionadas != null && !reglasSeleccionadas.isEmpty()) {
			for (DTOReglas regla : reglasSeleccionadas) {
				if (regla.getIdRegla().equals(Constantes.ID_REGLA_CURSO)) {
					seEncuentra = true;
				}
			}
		}

		if (!seEncuentra) {
			esCurso = false;
			origenCurso = null;
		}

	}

	/**
	 * Método que identifica cuando seleccionan la regla 1 para la fecha del
	 * curso y visualizar el combo de origenes del curso
	 * 
	 * @author Gerardo López
	 * @since 22/08/2017
	 * 
	 */
	public void seleccionaReglaAsociada() {
		boolean seEncuentra = false;
		if (nuevasReglasSeleccionadas != null
				&& !nuevasReglasSeleccionadas.isEmpty()) {
			for (DTOReglas regla : nuevasReglasSeleccionadas) {
				if (regla.getIdRegla().equals(Constantes.ID_REGLA_CURSO)) {
					seEncuentra = true;
				}
			}
		}

		if (seEncuentra) {
			esCurso = true;
		} else {
			esCurso = false;
			origenCurso = null;
		}

	}

	/**
	 * Método que identifica cuando deseleccionan la regla 1 para la fecha del
	 * curso y quitar el combo de origenes del curso de vista
	 * 
	 * @author Gerardo López
	 * @since 22/08/2017
	 * 
	 */
	public void deseleccionaReglaAsociada() {
		boolean seEncuentra = false;
		if (nuevasReglasSeleccionadas != null
				&& !nuevasReglasSeleccionadas.isEmpty()) {
			for (DTOReglas regla : nuevasReglasSeleccionadas) {
				if (regla.getIdRegla().equals(Constantes.ID_REGLA_CURSO)) {
					seEncuentra = true;
				}
			}
		}

		if (!seEncuentra) {
			esCurso = false;
			origenCurso = null;
		}

	}

	/**
	 * Método para cada vez que exista un evento change en seleccionar el origen
	 * del curso
	 * 
	 * @author Gerardo López
	 * @since 24/08/2017
	 * 
	 */
	public void cambiaCurso() {
		this.numeroCurso = origenCurso;
	}

	/**
	 * 
	 */
	public void limpiaTablaReglas() {
		reglasAsociadasEva = new ArrayList<DTOReglas>();
	}

	/**
	 * Método para subir el scroll en la vista y que el usuario pueda observar
	 * los mensajes de acuerdo a la acción.
	 * 
	 * @author Gerardo López
	 * @since 18/07/2017
	 * 
	 */
	public void scrollTop() {
		RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
	}

	// ****** GET & SET DE LAS VARIABLES A PRESENTAR ************************

	public DTOUsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(DTOUsuarioLogin usuario) {
		this.usuario = usuario;
	}

	public String getAccionModulo() {
		return accionModulo;
	}

	public void setAccionModulo(String accionModulo) {
		this.accionModulo = accionModulo;
	}

	public String getAccionCapturar() {
		return accionCapturar;
	}

	public void setAccionCapturar(String accionCapturar) {
		this.accionCapturar = accionCapturar;
	}

	public String getAccionConsultar() {
		return accionConsultar;
	}

	public void setAccionConsultar(String accionConsultar) {
		this.accionConsultar = accionConsultar;
	}

	public String getAccionModificar() {
		return accionModificar;
	}

	public void setAccionModificar(String accionModificar) {
		this.accionModificar = accionModificar;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public boolean isCargaSeleccionada() {
		return cargaSeleccionada;
	}

	public void setCargaSeleccionada(boolean cargaSeleccionada) {
		this.cargaSeleccionada = cargaSeleccionada;
	}

	public boolean isMuestraMensaje() {
		return muestraMensaje;
	}

	public void setMuestraMensaje(boolean muestraMensaje) {
		this.muestraMensaje = muestraMensaje;
	}

	public boolean isMuestraFormularioAgrega() {
		return muestraFormularioAgrega;
	}

	public void setMuestraFormularioAgrega(boolean muestraFormularioAgrega) {
		this.muestraFormularioAgrega = muestraFormularioAgrega;
	}

	public boolean isMuestraForm() {
		return muestraForm;
	}

	public void setMuestraForm(boolean muestraForm) {
		this.muestraForm = muestraForm;
	}

	public boolean isDisableBtnElimina() {
		return disableBtnElimina;
	}

	public void setDisableBtnElimina(boolean disableBtnElimina) {
		this.disableBtnElimina = disableBtnElimina;
	}

	public Integer getIdDetalleSeleccionado() {
		return idDetalleSeleccionado;
	}

	public void setIdDetalleSeleccionado(Integer idDetalleSeleccionado) {
		this.idDetalleSeleccionado = idDetalleSeleccionado;
	}

	public List<DTODetalleProcesoWS> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DTODetalleProcesoWS> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public DTODetalleProcesoWS getProcesoDetalle() {
		return procesoDetalle;
	}

	public void setProcesoDetalle(DTODetalleProcesoWS procesoDetalle) {
		this.procesoDetalle = procesoDetalle;
	}

	// ************* VARIABLES PARA EL CATALOGO DE ACCIONES ***********

	/**
	 * Sólo Get para accionesPredefinidas (Carga inicial)
	 * 
	 * @return
	 */
	public List<DTOCAcciones> getAccionesPredefinidas() {
		return accionesPredefinidas;
	}

	public List<DTOCAcciones> getAccionesSeleccionadas() {
		return accionesSeleccionadas;
	}

	public void setAccionesSeleccionadas(
			List<DTOCAcciones> accionesSeleccionadas) {
		this.accionesSeleccionadas = accionesSeleccionadas;
	}

	public List<DTOCAcciones> getNuevasAcciones() {
		return nuevasAcciones;
	}

	public void setNuevasAcciones(List<DTOCAcciones> nuevasAcciones) {
		this.nuevasAcciones = nuevasAcciones;
	}

	public List<DTOCAcciones> getAccionesSeleccionadasLista() {
		return accionesSeleccionadasLista;
	}

	public void setAccionesSeleccionadasLista(
			List<DTOCAcciones> accionesSeleccionadasLista) {
		this.accionesSeleccionadasLista = accionesSeleccionadasLista;
	}

	public List<DTOCAcciones> getNuevasAccionesSeleccionadas() {
		return nuevasAccionesSeleccionadas;
	}

	public void setNuevasAccionesSeleccionadas(
			List<DTOCAcciones> nuevasAccionesSeleccionadas) {
		this.nuevasAccionesSeleccionadas = nuevasAccionesSeleccionadas;
	}

	public String getAccionNombre() {
		return accionNombre;
	}

	public void setAccionNombre(String accionNombre) {
		this.accionNombre = accionNombre;
	}

	// ******** VARIABLES PARA EL CATALOGO DE CARGO RESPONSABLE ********

	/**
	 * Sólo Get para cargosPredefinidos (Carga inicial)
	 * 
	 * @return
	 */
	public List<DTOCCargoResponsable> getCargosPredefinidos() {
		return cargosPredefinidos;
	}

	public List<DTOCCargoResponsable> getCargosSeleccionados() {
		return cargosSeleccionados;
	}

	public void setCargosSeleccionados(
			List<DTOCCargoResponsable> cargosSeleccionados) {
		this.cargosSeleccionados = cargosSeleccionados;
	}

	public List<DTOCCargoResponsable> getCargosSeleccionadosLista() {
		return cargosSeleccionadosLista;
	}

	public void setCargosSeleccionadosLista(
			List<DTOCCargoResponsable> cargosSeleccionadosLista) {
		this.cargosSeleccionadosLista = cargosSeleccionadosLista;
	}

	public List<DTOCCargoResponsable> getNuevosCargosSeleccionados() {
		return nuevosCargosSeleccionados;
	}

	public void setNuevosCargosSeleccionados(
			List<DTOCCargoResponsable> nuevosCargosSeleccionados) {
		this.nuevosCargosSeleccionados = nuevosCargosSeleccionados;
	}

	public List<DTOCCargoResponsable> getNuevosCargos() {
		return nuevosCargos;
	}

	public void setNuevosCargos(List<DTOCCargoResponsable> nuevosCargos) {
		this.nuevosCargos = nuevosCargos;
	}

	public String getDescripcionCargo() {
		return descripcionCargo;
	}

	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}

	public String getInicialesCargo() {
		return inicialesCargo;
	}

	public void setInicialesCargo(String inicialesCargo) {
		this.inicialesCargo = inicialesCargo;
	}

	public String getOrigenCargo() {
		return origenCargo;
	}

	public void setOrigenCargo(String origenCargo) {
		this.origenCargo = origenCargo;
	}

	public String getIdCargoAlterno() {
		return idCargoAlterno;
	}

	public void setIdCargoAlterno(String idCargoAlterno) {
		this.idCargoAlterno = idCargoAlterno;
	}

	// ******** VARIABLES PARA EL CATALOGO DE ESCOLARIDADES ********

	/**
	 * Sólo Get para escolaridadesPredefinidas (Carga inicial)
	 * 
	 * @return
	 */
	public List<DTOCEscolaridades> getEscolaridadesPredefinidas() {
		return escolaridadesPredefinidas;
	}

	public List<DTOCEscolaridades> getEscolaridadesSeleccionadas() {
		return escolaridadesSeleccionadas;
	}

	public void setEscolaridadesSeleccionadas(
			List<DTOCEscolaridades> escolaridadesSeleccionadas) {
		this.escolaridadesSeleccionadas = escolaridadesSeleccionadas;
	}

	public List<DTOCEscolaridades> getEscolaridadesSeleccionadasLista() {
		return escolaridadesSeleccionadasLista;
	}

	public void setEscolaridadesSeleccionadasLista(
			List<DTOCEscolaridades> escolaridadesSeleccionadasLista) {
		this.escolaridadesSeleccionadasLista = escolaridadesSeleccionadasLista;
	}

	public List<DTOCEscolaridades> getNuevasEscolaridadesSeleccionadas() {
		return nuevasEscolaridadesSeleccionadas;
	}

	public void setNuevasEscolaridadesSeleccionadas(
			List<DTOCEscolaridades> nuevasEscolaridadesSeleccionadas) {
		this.nuevasEscolaridadesSeleccionadas = nuevasEscolaridadesSeleccionadas;
	}

	public List<DTOCEscolaridades> getNuevasEscolaridades() {
		return nuevasEscolaridades;
	}

	public void setNuevasEscolaridades(
			List<DTOCEscolaridades> nuevasEscolaridades) {
		this.nuevasEscolaridades = nuevasEscolaridades;
	}

	public DTOCEscolaridades getNuevaEscolaridad() {
		return nuevaEscolaridad;
	}

	public void setNuevaEscolaridad(DTOCEscolaridades nuevaEscolaridad) {
		this.nuevaEscolaridad = nuevaEscolaridad;
	}

	public String getDescripcionEscolaridad() {
		return descripcionEscolaridad;
	}

	public void setDescripcionEscolaridad(String descripcionEscolaridad) {
		this.descripcionEscolaridad = descripcionEscolaridad;
	}

	// ******** VARIABLES PARA EL CATALOGO DE JUSTIFICACIONES ********

	/**
	 * Sólo Get para justificacionesPredefinidas (Carga inicial)
	 * 
	 * @return
	 */
	public List<DTOCJustificaciones> getJustificacionesPredefinidas() {
		return justificacionesPredefinidas;
	}

	public List<DTOCJustificaciones> getJustificacionesSeleccionadas() {
		return justificacionesSeleccionadas;
	}

	public void setJustificacionesSeleccionadas(
			List<DTOCJustificaciones> justificacionesSeleccionadas) {
		this.justificacionesSeleccionadas = justificacionesSeleccionadas;
	}

	public List<DTOCJustificaciones> getJustificacionesSeleccionadasLista() {
		return justificacionesSeleccionadasLista;
	}

	public void setJustificacionesSeleccionadasLista(
			List<DTOCJustificaciones> justificacionesSeleccionadasLista) {
		this.justificacionesSeleccionadasLista = justificacionesSeleccionadasLista;
	}

	public List<DTOCJustificaciones> getNuevasJustificacionesSeleccionadas() {
		return nuevasJustificacionesSeleccionadas;
	}

	public void setNuevasJustificacionesSeleccionadas(
			List<DTOCJustificaciones> nuevasJustificacionesSeleccionadas) {
		this.nuevasJustificacionesSeleccionadas = nuevasJustificacionesSeleccionadas;
	}

	public List<DTOCJustificaciones> getNuevasJustificaciones() {
		return nuevasJustificaciones;
	}

	public void setNuevasJustificaciones(
			List<DTOCJustificaciones> nuevasJustificaciones) {
		this.nuevasJustificaciones = nuevasJustificaciones;
	}

	public List<DTOCJustificaciones> getJustificacionesSeleccionadasTmp() {
		return justificacionesSeleccionadasTmp;
	}

	public void setJustificacionesSeleccionadasTmp(
			List<DTOCJustificaciones> justificacionesSeleccionadasTmp) {
		this.justificacionesSeleccionadasTmp = justificacionesSeleccionadasTmp;
	}

	public DTOCJustificaciones getNuevaJustificacion() {
		return nuevaJustificacion;
	}

	public void setNuevaJustificacion(DTOCJustificaciones nuevaJustificacion) {
		this.nuevaJustificacion = nuevaJustificacion;
	}

	public String getDescripcionJustificacion() {
		return descripcionJustificacion;
	}

	public void setDescripcionJustificacion(String descripcionJustificacion) {
		this.descripcionJustificacion = descripcionJustificacion;
	}

	public String getDescripcionCortaJustificacion() {
		return descripcionCortaJustificacion;
	}

	public void setDescripcionCortaJustificacion(
			String descripcionCortaJustificacion) {
		this.descripcionCortaJustificacion = descripcionCortaJustificacion;
	}

	public String getResultadoJustificacion() {
		return resultadoJustificacion;
	}

	public void setResultadoJustificacion(String resultadoJustificacion) {
		this.resultadoJustificacion = resultadoJustificacion;
	}

	public List<DTODetalleProcesoWS> getListaDetallesConsulta() {
		return listaDetallesConsulta;
	}

	public void setListaDetallesConsulta(
			List<DTODetalleProcesoWS> listaDetallesConsulta) {
		this.listaDetallesConsulta = listaDetallesConsulta;
	}

	public boolean isComboDetalleUnico() {
		return comboDetalleUnico;
	}

	public void setComboDetalleUnico(boolean comboDetalleUnico) {
		this.comboDetalleUnico = comboDetalleUnico;
	}

	public boolean isExistenRegistros() {
		return existenRegistros;
	}

	public void setExistenRegistros(boolean existenRegistros) {
		this.existenRegistros = existenRegistros;
	}

	public List<DTOCJustificaciones> getJustificacionesXDefault() {
		return justificacionesXDefault;
	}

	public void setJustificacionesXDefault(
			List<DTOCJustificaciones> justificacionesXDefault) {
		this.justificacionesXDefault = justificacionesXDefault;
	}

	// ******** VARIABLES PARA EL CATALOGO DE EVALUACIONES ********

	public List<DTOCEvaluacion> getEvaluacionesPredefinidas() {
		return evaluacionesPredefinidas;
	}

	public void setEvaluacionesPredefinidas(
			List<DTOCEvaluacion> evaluacionesPredefinidas) {
		this.evaluacionesPredefinidas = evaluacionesPredefinidas;
	}

	public List<DTOCEvaluacion> getEvaluacionesSeleccionadas() {
		return evaluacionesSeleccionadas;
	}

	public void setEvaluacionesSeleccionadas(
			List<DTOCEvaluacion> evaluacionesSeleccionadas) {
		this.evaluacionesSeleccionadas = evaluacionesSeleccionadas;
	}

	public List<DTOCEvaluacion> getEvaluacionesSeleccionadasTmp() {
		return evaluacionesSeleccionadasTmp;
	}

	public void setEvaluacionesSeleccionadasTmp(
			List<DTOCEvaluacion> evaluacionesSeleccionadasTmp) {
		this.evaluacionesSeleccionadasTmp = evaluacionesSeleccionadasTmp;
	}

	public List<DTOCEvaluacion> getEvaluacionesSeleccionadasLista() {
		return evaluacionesSeleccionadasLista;
	}

	public void setEvaluacionesSeleccionadasLista(
			List<DTOCEvaluacion> evaluacionesSeleccionadasLista) {
		this.evaluacionesSeleccionadasLista = evaluacionesSeleccionadasLista;
	}

	public List<DTOCEvaluacion> getNuevasEvaluacionesSeleccionadas() {
		return nuevasEvaluacionesSeleccionadas;
	}

	public void setNuevasEvaluacionesSeleccionadas(
			List<DTOCEvaluacion> nuevasEvaluacionesSeleccionadas) {
		this.nuevasEvaluacionesSeleccionadas = nuevasEvaluacionesSeleccionadas;
	}

	public List<DTOCEvaluacion> getNuevasEvaluaciones() {
		return nuevasEvaluaciones;
	}

	public void setNuevasEvaluaciones(List<DTOCEvaluacion> nuevasEvaluaciones) {
		this.nuevasEvaluaciones = nuevasEvaluaciones;
	}

	public List<DTOCEvaluacion> getEvaluacionesXDefault() {
		return evaluacionesXDefault;
	}

	public void setEvaluacionesXDefault(
			List<DTOCEvaluacion> evaluacionesXDefault) {
		this.evaluacionesXDefault = evaluacionesXDefault;
	}

	public DTOCEvaluacion getNuevaEvaluacion() {
		return nuevaEvaluacion;
	}

	public void setNuevaEvaluacion(DTOCEvaluacion nuevaEvaluacion) {
		this.nuevaEvaluacion = nuevaEvaluacion;
	}

	public String getDescripcionEvaluacion() {
		return descripcionEvaluacion;
	}

	public void setDescripcionEvaluacion(String descripcionEvaluacion) {
		this.descripcionEvaluacion = descripcionEvaluacion;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public List<DTOReglas> getReglasEvaluacion() {
		return reglasEvaluacion;
	}

	public void setReglasEvaluacion(List<DTOReglas> reglasEvaluacion) {
		this.reglasEvaluacion = reglasEvaluacion;
	}

	public List<DTOReglas> getReglasSeleccionadas() {
		return reglasSeleccionadas;
	}

	public void setReglasSeleccionadas(List<DTOReglas> reglasSeleccionadas) {
		this.reglasSeleccionadas = reglasSeleccionadas;
	}

	public DTOCEvaluacion getSelectEvaluacion() {
		return selectEvaluacion;
	}

	public void setSelectEvaluacion(DTOCEvaluacion selectEvaluacion) {
		this.selectEvaluacion = selectEvaluacion;
	}

	public List<DTOReglas> getReglasAsociadasEva() {
		return reglasAsociadasEva;
	}

	public void setReglasAsociadasEva(List<DTOReglas> reglasAsociadasEva) {
		this.reglasAsociadasEva = reglasAsociadasEva;
	}

	public List<DTOReglas> getNuevasReglasSeleccionadas() {
		return nuevasReglasSeleccionadas;
	}

	public void setNuevasReglasSeleccionadas(
			List<DTOReglas> nuevasReglasSeleccionadas) {
		this.nuevasReglasSeleccionadas = nuevasReglasSeleccionadas;
	}

	public boolean isEsCurso() {
		return esCurso;
	}

	public void setEsCurso(boolean esCurso) {
		this.esCurso = esCurso;
	}

	public Map<String, Integer> getOrigenesCurso() {
		return origenesCurso;
	}

	public void setOrigenesCurso(Map<String, Integer> origenesCurso) {
		this.origenesCurso = origenesCurso;
	}

	public Integer getOrigenCurso() {
		return origenCurso;
	}

	public void setOrigenCurso(Integer origenCurso) {
		this.origenCurso = origenCurso;
	}

	public DTOReglas getSelectRule() {
		return selectRule;
	}

	public void setSelectRule(DTOReglas selectRule) {
		this.selectRule = selectRule;
	}

}
