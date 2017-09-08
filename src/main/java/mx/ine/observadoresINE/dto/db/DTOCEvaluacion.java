/**
 * @(#)DTOCEvaluacion.java 10/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 10/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "C_EVALUACION", schema="OBSERVADORESINE")
public class DTOCEvaluacion extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOCEvaluacionPK dTOCEvaluacionPK;
    @Size(max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    
    @Column(name = "TIPO")
    private Character tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOCEvaluacion", fetch = FetchType.LAZY)
    private List<DTOReglasEvalucaion> dTOReglasEvalucaionList;

    public DTOCEvaluacion() {
    }

    public DTOCEvaluacion(DTOCEvaluacionPK dTOCEvaluacionPK) {
        this.dTOCEvaluacionPK = dTOCEvaluacionPK;
    }

    public DTOCEvaluacion(int idProcesoElectoral, int idDetalleProceso, int idEvaluacion) {
        this.dTOCEvaluacionPK = new DTOCEvaluacionPK(idProcesoElectoral, idDetalleProceso, idEvaluacion);
    }
    
    
    public DTOCEvaluacion(DTOCEvaluacionPK dTOCEvaluacionPK , String descripcion) {
        this.dTOCEvaluacionPK = dTOCEvaluacionPK;
        this.descripcion = descripcion;
    }

    public DTOCEvaluacionPK getDTOCEvaluacionPK() {
        return dTOCEvaluacionPK;
    }

    public void setDTOCEvaluacionPK(DTOCEvaluacionPK dTOCEvaluacionPK) {
        this.dTOCEvaluacionPK = dTOCEvaluacionPK;
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
    
    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }
    
    public List<DTOReglasEvalucaion> getDTOReglasEvalucaionList() {
        return dTOReglasEvalucaionList;
    }

    public void setDTOReglasEvalucaionList(List<DTOReglasEvalucaion> dTOReglasEvalucaionList) {
        this.dTOReglasEvalucaionList = dTOReglasEvalucaionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOCEvaluacionPK != null ? dTOCEvaluacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCEvaluacion)) {
            return false;
        }
        DTOCEvaluacion other = (DTOCEvaluacion) object;
        if ((this.dTOCEvaluacionPK == null && other.dTOCEvaluacionPK != null) || (this.dTOCEvaluacionPK != null && !this.dTOCEvaluacionPK.equals(other.dTOCEvaluacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCEvaluacion[ dTOCEvaluacionPK=" + dTOCEvaluacionPK + " ]";
    }

}
