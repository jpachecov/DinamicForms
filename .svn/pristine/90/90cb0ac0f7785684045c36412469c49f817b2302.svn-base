/**
 * @(#)DTOBase.java 21/02/2014
 *
 * Copyright (C) 2014 INE.
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import mx.ine.common.dto.DTOUsuario;
import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;


/**
 * Clase que contiene los datos del usuario que se autentica.
 * 
 * @author Martha Castañón
 * @since 20 de marzo de 2014
 */
public class DTOUsuarioLogin extends DTOUsuario implements Serializable {

    /**
     * Elemento necesario para la serialización de los objetos generados de esta
     * clase.
     */
    private static final long serialVersionUID = 1146712022864463386L;

    /**
     * Username del usuario que se autentica.
     */
    private String usuario;

    /**
     * Nombre del usuario que se autentica.
     */
    private String nombreUsuario;

    /**
     * Identificador de la Entidad dónde se ubica la Junta a la que pertenece el
     * usuario
     */
    private Integer idEstado;

    /**
     * Atributo utilizado para mostra la entidad a la que pertenece el usuario
     */
    private String nombreEstado;

    /**
     * Identificador del Distito dónde se ubica la Junta a la que pertenece el
     * usuario
     */
    private Integer idDistrito;

    /**
     * Atributo nombre del distrito
     */
    private String nombreDistrito;

    /**
     * Atributo que contiene la ubicación o área a la que pertenece el usuario
     * ou en LDAP
     */
    private String ubicacion;

    /**
     * Rol que el usuario tiene dentro del sistema.
     */
    private String rolUsuario;

    /**
     * Atributo del identificador del sistema utilizado para el single sing on
     */
    private Integer idSistema;

    /**
     * Version del usuario: OC, JL o JD
     */
    private String version;

    /**
     * Atributo que contiene la lista de roles no correspondientes al sistema
     * con los que cuenta el usuario para el inicio único.
     */
    private List<String> rolesLdap;
    /**
     * Lista de estados del proceso seleccionado
     */
    private List<DTODetalleEstadoProcesoWS> listaEstados;
    /**
     * Estado seleccionado
     */
    private DTODetalleEstadoProcesoWS estadoSeleccionado;
    /**
     * Id Estado seleccionado del menu lateral
     */ 
    private Integer idEstadoSeleccionado;
    /**
     * Lista de distritos del estado seleccionado
     */
    private List<DTODetalleDistritoProcesoWS> listaDistritos;
    /**
     * Distrito seleccionado
     */
    private DTODetalleDistritoProcesoWS distritoSeleccionado;
    /**
     * Id Distrito seleciconado del menu lateral
     */
    private Integer idDistritoSeleccionado;
    /**
     * id proceso
     */
    private Integer idProcesoElectoral;
    
    /**
     * Lista de detalles de proceso electoral
     */
    private List<DTODetalleProcesoWS> listaDetalles;
    /**
     * Detalle seleccionado
     */
    private DTODetalleProcesoWS detalleSeleccionado;
    /**
     * id detalle
     */
    private Integer idDetalleProceso;

    /**
     * Constructor
     * 
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public DTOUsuarioLogin(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

   

	/**
     * Método que obtiene el valor de el atributo usuario
     *
     * @return String : valor que tiene el atributo usuario
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getUsuario() {
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
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que obtiene el valor de el atributo nombreUsuario
     *
     * @return String : valor que tiene el atributo nombreUsuario
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Método que ingresa el valor de el atributo nombreUsuario
     *
     * @param nombreUsuario : valor que ingresa a el atributo nombreUsuario
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Método que obtiene el valor de el atributo idEstado
     *
     * @return Integer : valor que tiene el atributo idEstado
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getIdEstado() {
        return idEstado;
    }

    /**
     * Método que ingresa el valor de el atributo idEstado
     *
     * @param idEstado : valor que ingresa a el atributo idEstado
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Método que obtiene el valor de el atributo nombreEstado
     *
     * @return String : valor que tiene el atributo nombreEstado
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getNombreEstado() {
        return nombreEstado;
    }

    /**
     * Método que ingresa el valor de el atributo nombreEstado
     *
     * @param nombreEstado : valor que ingresa a el atributo nombreEstado
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    /**
     * Método que obtiene el valor de el atributo idDistrito
     *
     * @return Integer : valor que tiene el atributo idDistrito
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getIdDistrito() {
        return idDistrito;
    }

    /**
     * Método que ingresa el valor de el atributo idDistrito
     *
     * @param idDistrito : valor que ingresa a el atributo idDistrito
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    /**
     * Método que obtiene el valor de el atributo nombreDistrito
     *
     * @return String : valor que tiene el atributo nombreDistrito
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getNombreDistrito() {
        return nombreDistrito;
    }

    /**
     * Método que ingresa el valor de el atributo nombreDistrito
     *
     * @param nombreDistrito : valor que ingresa a el atributo nombreDistrito
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    /**
     * Método que obtiene el valor de el atributo ubicacion
     *
     * @return String : valor que tiene el atributo ubicacion
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Método que ingresa el valor de el atributo ubicacion
     *
     * @param ubicacion : valor que ingresa a el atributo ubicacion
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Método que obtiene el valor de el atributo rolUsuario
     *
     * @return String : valor que tiene el atributo rolUsuario
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getRolUsuario() {
        return rolUsuario;
    }

    /**
     * Método que ingresa el valor de el atributo rolUsuario
     *
     * @param rolUsuario : valor que ingresa a el atributo rolUsuario
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * Método que obtiene el valor de el atributo idSistema
     *
     * @return Integer : valor que tiene el atributo idSistema
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public Integer getIdSistema() {
        return idSistema;
    }

    /**
     * Método que ingresa el valor de el atributo idSistema
     *
     * @param idSistema : valor que ingresa a el atributo idSistema
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    /**
     * Método que obtiene el valor de el atributo rolesLdap
     *
     * @return List<String> : valor que tiene el atributo rolesLdap
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public List<String> getRolesLdap() {
        return rolesLdap;
    }

    /**
     * Método que ingresa el valor de el atributo rolesLdap
     *
     * @param rolesLdap : valor que ingresa a el atributo rolesLdap
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setRolesLdap(List<String> rolesLdap) {
        this.rolesLdap = rolesLdap;
    }

    /**
     * Método que obtiene el valor de el atributo version
     *
     * @return String : valor que tiene el atributo version
     * 
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public String getVersion() {
        return version;
    }

    /**
     * Método que ingresa el valor de el atributo version
     *
     * @param version : valor que ingresa a el atributo version
     *
     * @author José Antonio López Torres
     * @since 8/11/2016
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the idProcesoElectoral
     */
    public Integer getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    /**
     * @param idProcesoElectoral the idProcesoElectoral to set
     */
    public void setIdProcesoElectoral(Integer idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

	/**
	 * Método que obtiene el valor de el atributo procesoSeleccionado
	 *
	 * @return Date : valor que tiene el atributo procesoSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
//	public DTOProcesoElectoralVigenteWS getProcesoSeleccionado() {
//		return procesoSeleccionado;
//	}

	/**
	 * Método que ingresa el valor de el atributo procesoSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo procesoSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
//	public void setProcesoSeleccionado(
//			DTOProcesoElectoralVigenteWS procesoSeleccionado) {
//		this.procesoSeleccionado = procesoSeleccionado;
//	}

	/**
	 * Método que obtiene el valor de el atributo listaProcesos
	 *
	 * @return Date : valor que tiene el atributo listaProcesos
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
//	public List<DTOProcesoElectoralVigenteWS> getListaProcesos() {
//		return listaProcesos;
//	}

	/**
	 * Método que ingresa el valor de el atributo listaProcesos
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo listaProcesos
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
//	public void setListaProcesos(List<DTOProcesoElectoralVigenteWS> listaProcesos) {
//		this.listaProcesos = listaProcesos;
//	}

    /**
	 * Método que obtiene el valor de el atributo listaEstados
	 *
	 * @return Date : valor que tiene el atributo listaEstados
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public List<DTODetalleEstadoProcesoWS> getListaEstados() {
		return listaEstados;
	}

	/**
	 * Método que ingresa el valor de el atributo listaEstados
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo listaEstados
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setListaEstados(List<DTODetalleEstadoProcesoWS> listaEstados) {
		this.listaEstados = listaEstados;
	}

	/**
	 * Método que obtiene el valor de el atributo estadoSeleccionado
	 *
	 * @return Date : valor que tiene el atributo estadoSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public DTODetalleEstadoProcesoWS getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	/**
	 * Método que ingresa el valor de el atributo estadoSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo estadoSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setEstadoSeleccionado(DTODetalleEstadoProcesoWS estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	/**
	 * Método que obtiene el valor de el atributo idEstadoSeleccionado
	 *
	 * @return Date : valor que tiene el atributo idEstadoSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public Integer getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	/**
	 * Método que ingresa el valor de el atributo idEstadoSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo idEstadoSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setIdEstadoSeleccionado(Integer idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	/**
	 * Método que obtiene el valor de el atributo listaDistritos
	 *
	 * @return Date : valor que tiene el atributo listaDistritos
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public List<DTODetalleDistritoProcesoWS> getListaDistritos() {
		return listaDistritos;
	}

	/**
	 * Método que ingresa el valor de el atributo listaDistritos
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo listaDistritos
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setListaDistritos(List<DTODetalleDistritoProcesoWS> listaDistritos) {
		this.listaDistritos = listaDistritos;
	}

	/**
	 * Método que obtiene el valor de el atributo distritoSeleccionado
	 *
	 * @return Date : valor que tiene el atributo distritoSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public DTODetalleDistritoProcesoWS getDistritoSeleccionado() {
		return distritoSeleccionado;
	}

	/**
	 * Método que ingresa el valor de el atributo distritoSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo distritoSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setDistritoSeleccionado(
			DTODetalleDistritoProcesoWS distritoSeleccionado) {
		this.distritoSeleccionado = distritoSeleccionado;
	}

	/**
	 * Método que obtiene el valor de el atributo idDistritoSeleccionado
	 *
	 * @return Date : valor que tiene el atributo idDistritoSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public Integer getIdDistritoSeleccionado() {
		return idDistritoSeleccionado;
	}

	/**
	 * Método que ingresa el valor de el atributo idDistritoSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo idDistritoSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 22/02/2017
	 */
	public void setIdDistritoSeleccionado(Integer idDistritoSeleccionado) {
		this.idDistritoSeleccionado = idDistritoSeleccionado;
	}
	
	

	/**
	 * Método que obtiene el valor de el atributo listaDetalles
	 *
	 * @return Date : valor que tiene el atributo listaDetalles
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 02/03/2017
	 */
	public List<DTODetalleProcesoWS> getListaDetalles() {
		return listaDetalles;
	}

	/**
	 * Método que ingresa el valor de el atributo listaDetalles
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo listaDetalles
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 02/03/2017
	 */
	public void setListaDetalles(List<DTODetalleProcesoWS> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	/**
	 * Método que obtiene el valor de el atributo detalleSeleccionado
	 *
	 * @return Date : valor que tiene el atributo detalleSeleccionado
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 02/03/2017
	 */
	public DTODetalleProcesoWS getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	/**
	 * Método que ingresa el valor de el atributo detalleSeleccionado
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo detalleSeleccionado
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 02/03/2017
	 */
	public void setDetalleSeleccionado(DTODetalleProcesoWS detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DTOUsuarioLogin [usuario=" + usuario + ", nombreUsuario="
				+ nombreUsuario + ", idEstado=" + idEstado + ", nombreEstado="
				+ nombreEstado + ", idDistrito=" + idDistrito
				+ ", nombreDistrito=" + nombreDistrito + ", ubicacion="
				+ ubicacion + ", rolUsuario=" + rolUsuario + ", idSistema="
				+ idSistema + ", version=" + version + ", rolesLdap="
				+ rolesLdap + ", listaEstados=" + listaEstados
				+ ", estadoSeleccionado=" + estadoSeleccionado
				+ ", idEstadoSeleccionado=" + idEstadoSeleccionado
				+ ", listaDistritos=" + listaDistritos
				+ ", distritoSeleccionado=" + distritoSeleccionado
				+ ", idDistritoSeleccionado=" + idDistritoSeleccionado
				+ ", idProcesoElectoral=" + idProcesoElectoral
				+ ", listaDetalles=" + listaDetalles + ", detalleSeleccionado="
				+ detalleSeleccionado + ", idDetalleProceso="
				+ idDetalleProceso + "]";
	}
	
	
}
