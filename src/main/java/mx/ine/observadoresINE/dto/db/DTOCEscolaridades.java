/**
 * @(#)DTOCEscolaridades.java 28/06/2017
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
import javax.xml.bind.annotation.XmlRootElement;

import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "C_ESCOLARIDADES", schema="OBSERVADORESINE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DTOCEscolaridades.findAll", query = "SELECT d FROM DTOCEscolaridades d")})
public class DTOCEscolaridades extends DTOBase implements Serializable {
 
     
	private static final long serialVersionUID = -3618303564402710587L;
	@EmbeddedId
    private DTOCEscolaridadesPK dTOCEscolaridadesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOCEscolaridades", fetch = FetchType.LAZY)
    private List<DTOObservadores> dTOObservadoresList;

    public DTOCEscolaridades() {
    }

    public DTOCEscolaridades(DTOCEscolaridadesPK dTOCEscolaridadesPK) {
        this.dTOCEscolaridadesPK = dTOCEscolaridadesPK;
    }

    public DTOCEscolaridades(DTOCEscolaridadesPK dTOCEscolaridadesPK, String descripcion, String usuario, Date fechaHora) {
        this.dTOCEscolaridadesPK = dTOCEscolaridadesPK;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }
    
    
    public DTOCEscolaridades(DTOCEscolaridadesPK dTOCEscolaridadesPK, String descripcion ) {
        this.dTOCEscolaridadesPK = dTOCEscolaridadesPK;
        this.descripcion = descripcion; 
    }
    

    public DTOCEscolaridades(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idEscolaridad) {
        this.dTOCEscolaridadesPK = new DTOCEscolaridadesPK(idProcesoElectoral, idDetalleProceso, idEscolaridad);
    }

    public DTOCEscolaridadesPK getDTOCEscolaridadesPK() {
        return dTOCEscolaridadesPK;
    }

    public void setDTOCEscolaridadesPK(DTOCEscolaridadesPK dTOCEscolaridadesPK) {
        this.dTOCEscolaridadesPK = dTOCEscolaridadesPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (dTOCEscolaridadesPK != null ? dTOCEscolaridadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCEscolaridades)) {
            return false;
        }
        DTOCEscolaridades other = (DTOCEscolaridades) object;
        if ((this.dTOCEscolaridadesPK == null && other.dTOCEscolaridadesPK != null) || (this.dTOCEscolaridadesPK != null && !this.dTOCEscolaridadesPK.equals(other.dTOCEscolaridadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCEscolaridades[ dTOCEscolaridadesPK=" + dTOCEscolaridadesPK + " ]";
    }

}
