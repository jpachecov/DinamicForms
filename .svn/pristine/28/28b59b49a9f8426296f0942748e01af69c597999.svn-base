package mx.ine.observadoresINE.dto.form;

import java.io.Serializable;
import java.util.List;

import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.helper.HLPAccionModulo;

/**
 * Clase Form que mapea los elementos requeridos en la pantalla del menú de módulos, 
 * menú de acciones, combos de ubicación Geográfica. 
 *  
 * @author Mayra Victoria
 * @updatedBy Jose Antonio López Torres
 * @version 1.0
 * @since 09/09/2016
 * @copyright INE
 *
 */
public class FormAdministrador implements Serializable {
    /**
     * Elemento necesario para la serialización de los objetos generados de esta
     * clase.
     */
    private static final long serialVersionUID = -1877569776608011582L;
    /**
     * Atributo que indica el año en que está operando el sistema, empleado principalmente en procesos electorales
     */
    private Integer anio;
    /**
     * Atributo que permite identificar el tipo de sistema que se tiene.
     */
    private String tipoSistema;
    
    /**
     * Atributo que indica que acción está ejecutandose en la vista
     */
    private Integer idAccion;
        
    /**Variable que indica el tipo de proceso que se consultará en el servicio web
     * 1.-Procesos electorales
     * 2.-Detalle de procesos electorales
     * **/
    private Integer tipoProceso;
    /**
     * Atributo para las migas modulo actual al que pertenece.
    
    private String modulo; */
    
    private String tituloModulo;
    
    /**Atributo empleado en la vista para indicar que el módulo seleccionado es donde se encuentra el usuario
     * (Usada para los estilos).
     * **/
    private Integer idModulo;
    /**
     * Cadena utilizada para dibujar la lista de módulos de acuerdo a lo seleccionado en la pantalla.
     */
    private String jsonMenuLateral;
    /**
     * Cadena utilizada para dibujar la lista de acciones de acuerdo al módulo seleccionado.
     */
    private String jsonMenuAcciones;
    /**
     * Lista de acciones
     */
    private List<HLPAccionModulo> listaAcciones;
    /**
     * Usuario logueado
     */
    private DTOUsuarioLogin usuario;
    /**
     * Permite saber si deshabilitados en combo de detalle proceso
     * en la vista (menu.xhtml)
     */
    private boolean disableDetalle;
    /**
     * Permite saber si deshabilitados en combo de estado
     * en la vista (menu.xhtml)
     */
    private boolean disableEstado;
    /**
     * Permite saber si deshabilitados en combo de distrito
     * en la vista (menu.xhtml)
     */
    private boolean disableDistrito;

    /**
     * Proceso seleccionado
     */
    private DTODetalleProcesoWS proceso;
    /**
     * id proceso
     */
    private Integer idProceso;
    /**
     * id proceso
     */
    private Integer idDetalleProceso;
    /**
     * vigente
     */
    private String vigente;
    
    
    /**
     * Constructor
     */
    public FormAdministrador() {
    	super();
    }

    /**
     * Método que obtiene el valor de el atributo anio
     *
     * @return Integer : valor que tiene el atributo anio
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * Método que ingresa el valor de el atributo anio
     *
     * @param anio : valor que ingresa a el atributo anio
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * Método que obtiene el valor de el atributo tipoSistema
     *
     * @return String : valor que tiene el atributo tipoSistema
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getTipoSistema() {
        return tipoSistema;
    }

    /**
     * Método que ingresa el valor de el atributo tipoSistema
     *
     * @param tipoSistema : valor que ingresa a el atributo tipoSistema
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setTipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
    }

    /**
     * Método que obtiene el valor de el atributo tipoProceso
     *
     * @return Integer : valor que tiene el atributo tipoProceso
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getTipoProceso() {
        return tipoProceso;
    }

    /**
     * Método que ingresa el valor de el atributo tipoProceso
     *
     * @param tipoProceso : valor que ingresa a el atributo tipoProceso
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setTipoProceso(Integer tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * Método que obtiene el valor de el atributo usuario
     *
     * @return DTOUsuarioLogin : valor que tiene el atributo usuario
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public DTOUsuarioLogin getUsuario() {
        return usuario;
    }

    /**
     * Método que ingresa el valor de el atributo usuario
     *
     * @param usuario : valor que ingresa a el atributo usuario
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setUsuario(DTOUsuarioLogin usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que obtiene el valor de el atributo idAccion
     *
     * @return Integer : valor que tiene el atributo idAccion
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getIdAccion() {
        return idAccion;
    }

    /**
     * Método que ingresa el valor de el atributo idAccion
     *
     * @param idAccion : valor que ingresa a el atributo idAccion
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    /**
     * Método que obtiene el valor de el atributo jsonMenuLateral
     *
     * @return String : valor que tiene el atributo jsonMenuLateral
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getJsonMenuLateral() {
        return jsonMenuLateral;
    }

    /**
     * Método que ingresa el valor de el atributo jsonMenuLateral
     *
     * @param jsonMenuLateral : valor que ingresa a el atributo jsonMenuLateral
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setJsonMenuLateral(String jsonMenuLateral) {
        this.jsonMenuLateral = jsonMenuLateral;
    }

    /**
     * Método que obtiene el valor de el atributo jsonMenuAcciones
     *
     * @return String : valor que tiene el atributo jsonMenuAcciones
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getJsonMenuAcciones() {
        return jsonMenuAcciones;
    }

    /**
     * Método que ingresa el valor de el atributo jsonMenuAcciones
     *
     * @param jsonMenuAcciones : valor que ingresa a el atributo jsonMenuAcciones
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setJsonMenuAcciones(String jsonMenuAcciones) {
        this.jsonMenuAcciones = jsonMenuAcciones;
    }

    /**
     * Método que obtiene el valor de el atributo modulo
     *
     * @return String : valor que tiene el atributo modulo
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     *
    /**public String getModulo() {
        return modulo;
    }

    /**
     * Método que ingresa el valor de el atributo modulo
     *
     * @param modulo : valor que ingresa a el atributo modulo
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     *
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }*/
    public String getTituloModulo() {
        return tituloModulo;
    }

    public void setTituloModulo(String tituloModulo) {
        this.tituloModulo = tituloModulo;
    }
    /**
     * Método que obtiene el valor de el atributo idModulo
     *
     * @return Integer : valor que tiene el atributo idModulo
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getIdModulo() {
        return idModulo;
    }

    /**
     * Método que ingresa el valor de el atributo idModulo
     *
     * @param idModulo : valor que ingresa a el atributo idModulo
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    /**
     * Método que obtiene el valor de el atributo listaAcciones
     *
     * @return List<HLPAccionModulo> : valor que tiene el atributo listaAcciones
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public List<HLPAccionModulo> getListaAcciones() {
        return listaAcciones;
    }

    /**
     * Método que ingresa el valor de el atributo listaAcciones
     *
     * @param listaAcciones : valor que ingresa a el atributo listaAcciones
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setListaAcciones(List<HLPAccionModulo> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }
    

    /**
	 * Método que obtiene el valor de el atributo disableDetalle
	 *
	 * @return Date : valor que tiene el atributo disableDetalle
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 03/03/2017
	 */
	public boolean isDisableDetalle() {
		return disableDetalle;
	}

	/**
	 * Método que ingresa el valor de el atributo disableDetalle
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo disableDetalle
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 03/03/2017
	 */
	public void setDisableDetalle(boolean disableDetalle) {
		this.disableDetalle = disableDetalle;
	}

	/**
     * Método que obtiene el valor del atributo disableEstado
     * 
     * @return disableEstado: valor del atributo disableEstado
     * @author Pablo Zuñiga Mata.
     * @since  20/02/2017
     */
    public boolean isDisableEstado() {
        return disableEstado;
    }

    /**
     * Método que ingresa el valor del atributo disableEstado
     * 
     * @param disableEstado: valor del atributo disableEstado
     * @author Pablo Zuñiga Mata.
     * @since  20/02/2017
     */
    public void setDisableEstado(boolean disableEstado) {
        this.disableEstado = disableEstado;
    }

    /**
     * Método que obtiene el valor del atributo disableDistrito
     * 
     * @return disableDistrito: valor del atributo disableDistrito
     * @author Pablo Zuñiga Mata.
     * @since  20/02/2017
     */
    public boolean isDisableDistrito() {
        return disableDistrito;
    }

    /**
     * Método que ingresa el valor del atributo disableDistrito
     * 
     * @param disableDistrito: valor del atributo disableDistrito
     * @author Pablo Zuñiga Mata.
     * @since  20/02/2017
     */
    public void setDisableDistrito(boolean disableDistrito) {
        this.disableDistrito = disableDistrito;
    }

    /**
     * Método que obtiene el valor del atributo proceso
     * 
     * @return proceso: valor del atributo proceso
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public DTODetalleProcesoWS getProceso() {
        return proceso;
    }

    /**
     * Método que ingresa el valor del atributo proceso
     * 
     * @param proceso: valor del atributo proceso
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public void setProceso(DTODetalleProcesoWS proceso) {
        this.proceso = proceso;
    }

    /**
     * Método que obtiene el valor del atributo idProceso
     * 
     * @return idProceso: valor del atributo idProceso
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public Integer getIdProceso() {
        return idProceso;
    }

    /**
     * Método que ingresa el valor del atributo idProceso
     * 
     * @param idProceso: valor del atributo idProceso
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    /**
     * Método que obtiene el valor del atributo vigente
     * 
     * @return vigente: valor del atributo vigente
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public String getVigente() {
        return vigente;
    }

    /**
     * Método que ingresa el valor del atributo vigente
     * 
     * @param vigente: valor del atributo vigente
     * @author Pablo Zuñiga Mata.
     * @since  21/02/2017
     */
    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

	/**
	 * Método que obtiene el valor de el atributo idDetalleProceso
	 *
	 * @return Date : valor que tiene el atributo idDetalleProceso
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 03/03/2017
	 */
	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	/**
	 * Método que ingresa el valor de el atributo idDetalleProceso
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo idDetalleProceso
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 03/03/2017
	 */
	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}
    
}
