/**
 * @(#)DTOAgrupaciones.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "AGRUPACIONES", schema="OBSERVADORESINE")
public class DTOAgrupaciones  extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOAgrupacionesPK pk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "NOMBRE_AGRUPACION")
    private String nombreAgrupacion;
    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @Size(max = 35)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 30)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 30)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Size(max = 50)
    @Column(name = "CALLE")
    private String calle;
    @Size(max = 8)
    @Column(name = "NUMERO_EXTERIOR")
    private String numeroExterior;
    @Size(max = 8)
    @Column(name = "NUMERO_INTERIOR")
    private String numeroInterior;
    @Column(name = "SIN_NUMERO")
    private String sinNumero;
    
    @Size(max = 60)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 5)
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    @Column(name = "ID_MUNICIPIO")
    private Integer idMunicipio;

	@Column(name = "LADA_TELEFONO_01")
    private String lada1;
    
    @Column(name = "LADA_TELEFONO_02")
    private String lada2;
    
    @Size(max = 25)
    @Column(name = "TELEFONO_01")
    private String telefono01;
    @Size(max = 25)
    @Column(name = "TELEFONO_02")
    private String telefono02;

	@Column(name ="EXTENSION_01")
    private String extension1;
    
    @Column(name ="EXTENSION_02")
    private String extension2;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOAgrupaciones", fetch = FetchType.LAZY)
    private List<DTOObservadores> dTOObservadoresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agrupaciones", fetch = FetchType.LAZY)
    private List<DTOCursos> dTOCursosList;

    public DTOAgrupaciones() {
    }

    public DTOAgrupaciones(DTOAgrupacionesPK dTOAgrupacionesPK) {
        this.pk = dTOAgrupacionesPK;
    }

    public DTOAgrupaciones(DTOAgrupacionesPK dTOAgrupacionesPK, String nombreAgrupacion, String abreviatura, String usuario, Date fechaHora) {
        this.pk = dTOAgrupacionesPK;
        this.nombreAgrupacion = nombreAgrupacion;
        this.abreviatura = abreviatura;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }

    public DTOAgrupaciones(int idProcesoElectoral, int idDetalleProceso, int idAgrupacion) {
        this.pk = new DTOAgrupacionesPK(idProcesoElectoral, idDetalleProceso, idAgrupacion);
    }

    public DTOAgrupacionesPK getPk() {
        return pk;
    }

    public void setPk(DTOAgrupacionesPK dTOAgrupacionesPK) {
        this.pk = dTOAgrupacionesPK;
    }

    public String getNombreAgrupacion() {
        return nombreAgrupacion;
    }

    public void setNombreAgrupacion(String nombreAgrupacion) {
        this.nombreAgrupacion = nombreAgrupacion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    
    public String getLada1() {
		return lada1;
	}

	public void setLada1(String lada1) {
		this.lada1 = lada1;
	}

	public String getLada2() {
		return lada2;
	}

	public void setLada2(String lada2) {
		this.lada2 = lada2;
	}
	
    public String getTelefono01() {
        return telefono01;
    }

    public void setTelefono01(String telefono01) {
        this.telefono01 = telefono01;
    }

    public String getTelefono02() {
        return telefono02;
    }

    public void setTelefono02(String telefono02) {
        this.telefono02 = telefono02;
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

    
    public String getSinNumero() {
		return sinNumero;
	}

	public void setSinNumero(String sinNumero) {
		this.sinNumero = sinNumero;
	}

	public String getExtension1() {
		return extension1;
	}

	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}

	public String getExtension2() {
		return extension2;
	}

	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}

    public List<DTOObservadores> getDTOObservadoresList() {
        return dTOObservadoresList;
    }

    public void setDTOObservadoresList(List<DTOObservadores> dTOObservadoresList) {
        this.dTOObservadoresList = dTOObservadoresList;
    }


    public List<DTOCursos> getDTOCursosList() {
        return dTOCursosList;
    }

    public void setDTOCursosList(List<DTOCursos> dTOCursosList) {
        this.dTOCursosList = dTOCursosList;
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
        if (!(object instanceof DTOAgrupaciones)) {
            return false;
        }
        DTOAgrupaciones other = (DTOAgrupaciones) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOAgrupaciones[ dTOAgrupacionesPK=" + pk + " ]";
    }

}
