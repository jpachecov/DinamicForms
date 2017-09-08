/**
 * @(#)DTOCJustificaciones.java 28/06/2017
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "C_JUSTIFICACIONES", schema="OBSERVADORESINE")
public class DTOCJustificaciones extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOCJustificacionesPK dTOCJustificacionesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "DESCRIPCION_CORTA")
    private String descripcionCorta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESULTADO")
    private Character resultado;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOCJustificaciones", fetch = FetchType.LAZY)
    private List<DTOObservadores> dTOObservadoresList;

    public DTOCJustificaciones() {
    }

    public DTOCJustificaciones(DTOCJustificacionesPK dTOCJustificacionesPK) {
        this.dTOCJustificacionesPK = dTOCJustificacionesPK;
    }

    public DTOCJustificaciones(DTOCJustificacionesPK dTOCJustificacionesPK, String descripcion, String descripcionCorta, Character resultado, String usuario, Date fechaHora) {
        this.dTOCJustificacionesPK = dTOCJustificacionesPK;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
        this.resultado = resultado;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }
    
    
    public DTOCJustificaciones(DTOCJustificacionesPK dTOCJustificacionesPK, String descripcion ) {
        this.dTOCJustificacionesPK = dTOCJustificacionesPK;
        this.descripcion = descripcion;
        
    }

    public DTOCJustificaciones(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idJustificacion) {
        this.dTOCJustificacionesPK = new DTOCJustificacionesPK(idProcesoElectoral, idDetalleProceso, idJustificacion);
    }

    public DTOCJustificacionesPK getDTOCJustificacionesPK() {
        return dTOCJustificacionesPK;
    }

    public void setDTOCJustificacionesPK(DTOCJustificacionesPK dTOCJustificacionesPK) {
        this.dTOCJustificacionesPK = dTOCJustificacionesPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public Character getResultado() {
        return resultado;
    }

    public void setResultado(Character resultado) {
        this.resultado = resultado;
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


    public List<DTOObservadores> getDTOObservadoresList() {
        return dTOObservadoresList;
    }

    public void setDTOObservadoresList(List<DTOObservadores> dTOObservadoresList) {
        this.dTOObservadoresList = dTOObservadoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOCJustificacionesPK != null ? dTOCJustificacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCJustificaciones)) {
            return false;
        }
        DTOCJustificaciones other = (DTOCJustificaciones) object;
        if ((this.dTOCJustificacionesPK == null && other.dTOCJustificacionesPK != null) || (this.dTOCJustificacionesPK != null && !this.dTOCJustificacionesPK.equals(other.dTOCJustificacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCJustificaciones[ dTOCJustificacionesPK=" + dTOCJustificacionesPK + " ]";
    }

}
