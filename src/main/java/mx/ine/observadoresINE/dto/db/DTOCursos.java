/**
 * @(#)DTOCursos.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import mx.ine.observadoresINE.enums.EnumMismoDomicilio;
import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "CURSOS", schema="OBSERVADORESINE")
public class DTOCursos extends DTOBase implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -173599354489356919L;
	
	@EmbeddedId
    protected DTOCursosPK pk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DISTRITO")
    private Integer idDistrito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORIGEN_CURSO")
    private Integer origenCurso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 30)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 30)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    /**
     * Fecha del curso
     */
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    /**
     * Hora de inicio del curso
     */
    private Date horaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    /**
     * Hora final del curso
     */
    private Date horaFin;
    @Size(max = 100)
    @Column(name = "CALLE")
    private String calle;
    @Size(max = 15)
    @Column(name = "NUMERO_EXTERIOR")
    private String numeroExterior;
    @Size(max = 15)
    @Column(name = "NUMERO_INTERIOR")
    private String numeroInterior;
    @Size(max = 100)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 5)
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    @Column(name = "ID_MUNICIPIO_DOMICILIO")
    private Integer idMunicipioDomicilio;
    @Column(name = "ID_ESTADO_DOMICILIO")
    private Integer idEstadoDomicilio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MISMO_DOMICILIO")
    @Enumerated(EnumType.STRING)
    private EnumMismoDomicilio mismoDomicilio;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    
    /**
     * Fecha y hora en la que fue realizada la transacción
     */
    private Date fechaHora;
    @Basic(optional=false)
    @NotNull
    @Column(name = "ID_CARGO")
    private Integer idCargo;
    @Basic(optional=false)
    @Column(name = "ID_AGRUPACION")
    private Integer idAgrupacion;
    
    @Transient
    private String etiqueta;
    
  

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOCursos", fetch = FetchType.LAZY)
    private List<DTOObservadores> dTOObservadoresList;
    
    @JoinColumns({
        @JoinColumn(name = "ID_PROCESO_ELECTORAL", referencedColumnName = "ID_PROCESO_ELECTORAL", insertable = false, updatable = false),
        @JoinColumn(name = "ID_DETALLE_PROCESO", referencedColumnName = "ID_DETALLE_PROCESO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DTOCCargoResponsable cargoResponsable;
    
    @JoinColumns({
        @JoinColumn(name = "ID_PROCESO_ELECTORAL", referencedColumnName = "ID_PROCESO_ELECTORAL", insertable = false, updatable = false),
        @JoinColumn(name = "ID_DETALLE_PROCESO", referencedColumnName = "ID_DETALLE_PROCESO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_AGRUPACION", referencedColumnName = "ID_AGRUPACION", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DTOAgrupaciones agrupaciones;

    public DTOCursos() {
    }

    public DTOCursos(DTOCursosPK pk) {
        this.pk = pk;
    }

    public DTOCursos(DTOCursosPK pk, Integer origenCurso,  Date fecha, 
    		Date horaInicio, Date horaFin, String observaciones , String etiqueta) {
        this.pk = pk;
        this.origenCurso = origenCurso;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.observaciones = observaciones;
        this.etiqueta = etiqueta;
    }
    
    
    
    public DTOCursos(DTOCursosPK pk,  String nombre, Date fecha, Date horaInicio, 
    		Date horaFin, EnumMismoDomicilio mismoDomicilio, String usuario, Date fechaHora) {
        this.pk = pk;
        this.idEstado = idEstado;
        this.idDistrito = idDistrito;
        this.origenCurso = origenCurso;
        this.nombre = nombre;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.mismoDomicilio = mismoDomicilio;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }

    public DTOCursos(int idProcesoElectoral, int idDetalleProceso, int idCurso) {
        this.pk = new DTOCursosPK(idProcesoElectoral, idDetalleProceso, idCurso);
    }

    public DTOCursos(DTOCursosPK tmpPk, String etiqueta) {
    	this.pk =  tmpPk;
    	this.etiqueta = etiqueta;
	}

	public DTOCursosPK getPk() {
        return pk;
    }

    public void setPk(DTOCursosPK pk) {
        this.pk = pk;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    public Integer getOrigenCurso() {
        return origenCurso;
    }

    public void setOrigenCurso(Integer origenCurso) {
        this.origenCurso = origenCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Integer getIdMunicipioDomicilio() {
        return idMunicipioDomicilio;
    }

    public void setIdMunicipioDomicilio(Integer idMunicipio) {
        this.idMunicipioDomicilio = idMunicipio;
    }

    public Integer getIdEstadoDomicilio() {
        return idEstadoDomicilio;
    }

    public void setIdEstadoDomicilio(Integer idEstadoDomicilio) {
        this.idEstadoDomicilio = idEstadoDomicilio;
    }

    public EnumMismoDomicilio getMismoDomicilio() {
        return mismoDomicilio;
    }

    public void setMismoDomicilio(EnumMismoDomicilio mismoDomicilio) {
        this.mismoDomicilio = mismoDomicilio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
	 * Método que obtiene el valor de el atributo idCargo
	 *
	 * @return Integer : valor que tiene el atributo idCargo
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 14/07/2017
	 */
	public Integer getIdCargo() {
		return idCargo;
	}

	/**
	 * Método que ingresa el valor de el atributo idCargo
	 *
	 * @param idCargo : valor que ingresa a el atributo idCargo
	 *
	 * @author Emmanuel García Ysamit
	 * @since 14/07/2017
	 */
	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	/**
	 * Método que obtiene el valor de el atributo idAgrupacion
	 *
	 * @return Integer : valor que tiene el atributo idAgrupacion
	 * 
	 * @author Emmanuel García Ysamit
	 * @since 14/07/2017
	 */
	public Integer getIdAgrupacion() {
		return idAgrupacion;
	}

	/**
	 * Método que ingresa el valor de el atributo idAgrupacion
	 *
	 * @param idAgrupacion : valor que ingresa a el atributo idAgrupacion
	 *
	 * @author Emmanuel García Ysamit
	 * @since 14/07/2017
	 */
	public void setIdAgrupacion(Integer idAgrupacion) {
		this.idAgrupacion = idAgrupacion;
	}

	public List<DTOObservadores> getDTOObservadoresList() {
        return dTOObservadoresList;
    }

    public void setDTOObservadoresList(List<DTOObservadores> dTOObservadoresList) {
        this.dTOObservadoresList = dTOObservadoresList;
    }

    public DTOCCargoResponsable getCargoResponsable() {
        return cargoResponsable;
    }

    public void setCargoResponsable(DTOCCargoResponsable dTOCCargoResponsable) {
        this.cargoResponsable = dTOCCargoResponsable;
    }

    public DTOAgrupaciones getAgrupaciones() {
        return agrupaciones;
    }

    public void setAgrupaciones(DTOAgrupaciones agrupaciones) {
        this.agrupaciones = agrupaciones;
    }
    
    public String getEtiqueta() {
  		return etiqueta;
  	}

  	public void setEtiqueta(String etiqueta) {
  		this.etiqueta = etiqueta;
  	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCursos)) {
            return false;
        }
        DTOCursos other = (DTOCursos) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

	/**
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "DTOCursos [pk=" + pk + ", idEstado=" + idEstado
				+ ", idDistrito=" + idDistrito + ", origenCurso=" + origenCurso
				+ ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", fecha=" + fecha
				+ ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
				+ ", calle=" + calle + ", numeroExterior=" + numeroExterior
				+ ", numeroInterior=" + numeroInterior + ", colonia=" + colonia
				+ ", codigoPostal=" + codigoPostal + ", idMunicipio="
				+ idMunicipioDomicilio + ", idEstadoDomicilio=" + idEstadoDomicilio
				+ ", mismoDomicilio=" + mismoDomicilio + ", observaciones="
				+ observaciones + ", usuario=" + usuario + ", fechaHora="
				+ fechaHora + ", idCargo=" + idCargo + ", idAgrupacion="
				+ idAgrupacion + ", dTOObservadoresList=" + dTOObservadoresList
				+ ", cargoResponsable=" + cargoResponsable + ", agrupaciones="
				+ agrupaciones + "]";
	}

    

    
    
}
