/**
 * @(#)DTOCAcciones.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "C_ACCIONES", schema="OBSERVADORESINE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DTOCAcciones.findAll", query = "SELECT d FROM DTOCAcciones d")})
public class DTOCAcciones extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private DTOCAccionesPK dTOCAccionesPK;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
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

    public DTOCAcciones() {
    }

    public DTOCAcciones(DTOCAccionesPK dTOCAccionesPK) {
        this.dTOCAccionesPK = dTOCAccionesPK;
    }

    public DTOCAcciones(DTOCAccionesPK dTOCAccionesPK, String usuario, Date fechaHora, String nombre) {
        this.dTOCAccionesPK = dTOCAccionesPK;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.nombre = nombre;
    }

    public DTOCAcciones(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idAccion) {
        this.dTOCAccionesPK = new DTOCAccionesPK(idProcesoElectoral, idDetalleProceso, idAccion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public DTOCAccionesPK getdTOCAccionesPK() {
		return dTOCAccionesPK;
	}

	public void setdTOCAccionesPK(DTOCAccionesPK dTOCAccionesPK) {
		this.dTOCAccionesPK = dTOCAccionesPK;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOCAccionesPK != null ? dTOCAccionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCAcciones)) {
            return false;
        }
        DTOCAcciones other = (DTOCAcciones) object;
        if ((this.dTOCAccionesPK == null && other.dTOCAccionesPK != null) || (this.dTOCAccionesPK != null && !this.dTOCAccionesPK.equals(other.dTOCAccionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ dTOCAccionesPK=" + dTOCAccionesPK + " ]";
    }

}
