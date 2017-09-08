/**
 * @(#)DTOCCargoResponsable.java 28/06/2017
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
import javax.persistence.Embeddable;
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
@Table(name = "C_CARGO_RESPONSABLE", schema="OBSERVADORESINE")
public class DTOCCargoResponsable extends DTOBase implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6478814296341978784L;
	
	@EmbeddedId
    private DTOCCargoResponsablePK id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "INICIALES")
    private String iniciales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORIGEN")
    private Integer origen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CARGO_ALTERNO")
    private Integer idCargoAlterno;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargoResponsable", fetch = FetchType.LAZY)
    private List<DTOCursos> dTOCursosList;

    public DTOCCargoResponsable() {
    }

    public DTOCCargoResponsable(DTOCCargoResponsablePK dTOCCargoResponsablePK) {
        this.id = dTOCCargoResponsablePK;
    }

    public DTOCCargoResponsable(DTOCCargoResponsablePK dTOCCargoResponsablePK, String descripcion, String iniciales, Integer origen, Integer idCargoAlterno, String usuario, Date fechaHora) {
        this.id = dTOCCargoResponsablePK;
        this.descripcion = descripcion;
        this.iniciales = iniciales;
        this.origen = origen;
        this.idCargoAlterno = idCargoAlterno;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }

    public DTOCCargoResponsable(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idCargo) {
        this.id = new DTOCCargoResponsablePK(idProcesoElectoral, idDetalleProceso, idCargo);
    }

    public DTOCCargoResponsablePK getId() {
        return id;
    }

    public void setId(DTOCCargoResponsablePK dTOCCargoResponsablePK) {
        this.id = dTOCCargoResponsablePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public Integer getIdCargoAlterno() {
        return idCargoAlterno;
    }

    public void setIdCargoAlterno(Integer idCargoAlterno) {
        this.idCargoAlterno = idCargoAlterno;
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


    public List<DTOCursos> getDTOCursosList() {
        return dTOCursosList;
    }

    public void setDTOCursosList(List<DTOCursos> dTOCursosList) {
        this.dTOCursosList = dTOCursosList;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCCargoResponsable)) {
            return false;
        }
        DTOCCargoResponsable other = (DTOCCargoResponsable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCCargoResponsable[ dTOCCargoResponsablePK=" + id + " ]";
    }

}
