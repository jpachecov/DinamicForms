/**
 * @(#)DTOReglasEvalucaion.java 11/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import mx.org.ine.servicios.dto.DTOBase;


/**
 *
 * @author Helaine Flores Cervantes
 * @since 11/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "REGLAS_EVALUACION")
public class DTOReglasEvalucaion extends DTOBase implements Serializable {
  
	private static final long serialVersionUID = 2574012011734736861L;
	@EmbeddedId
    private DTOReglasEvalucaionPK dTOReglasEvalucaionPK;
    @Size(max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column (name = "ORIGEN_CURSO")
    private Integer origenCurso;
    @Transient
    private Integer idAgrupacionCurso;
    
 

	public Integer getIdAgrupacionCurso() {
		return idAgrupacionCurso;
	}

	public void setIdAgrupacionCurso(Integer idAgrupacionCurso) {
		this.idAgrupacionCurso = idAgrupacionCurso;
	}
	@JoinColumns({
        @JoinColumn(name = "ID_PROCESO_ELECTORAL", referencedColumnName = "ID_PROCESO_ELECTORAL", insertable = false, updatable = false),
        @JoinColumn(name = "ID_DETALLE_PROCESO", referencedColumnName = "ID_DETALLE_PROCESO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_EVALUACION", referencedColumnName = "ID_EVALUACION", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DTOCEvaluacion dTOCEvaluacion;

    public DTOReglasEvalucaion() {
    }

    public DTOReglasEvalucaion(DTOReglasEvalucaionPK dTOReglasEvalucaionPK) {
        this.dTOReglasEvalucaionPK = dTOReglasEvalucaionPK;
    }

    public DTOReglasEvalucaion(int idProcesoElectoral, int idDetalleProceso, int idEvaluacion, int idRegla) {
        this.dTOReglasEvalucaionPK = new DTOReglasEvalucaionPK(idProcesoElectoral, idDetalleProceso, idEvaluacion, idRegla);
    }

    public DTOReglasEvalucaionPK getDTOReglasEvalucaionPK() {
        return dTOReglasEvalucaionPK;
    }

    public void setDTOReglasEvalucaionPK(DTOReglasEvalucaionPK dTOReglasEvalucaionPK) {
        this.dTOReglasEvalucaionPK = dTOReglasEvalucaionPK;
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

    public DTOCEvaluacion getDTOCEvaluacion() {
        return dTOCEvaluacion;
    }

    public void setDTOCEvaluacion(DTOCEvaluacion dTOCEvaluacion) {
        this.dTOCEvaluacion = dTOCEvaluacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOReglasEvalucaionPK != null ? dTOReglasEvalucaionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOReglasEvalucaion)) {
            return false;
        }
        DTOReglasEvalucaion other = (DTOReglasEvalucaion) object;
        if ((this.dTOReglasEvalucaionPK == null && other.dTOReglasEvalucaionPK != null) || (this.dTOReglasEvalucaionPK != null && !this.dTOReglasEvalucaionPK.equals(other.dTOReglasEvalucaionPK))) {
            return false;
        }
        return true;
    }
    public Integer getOrigenCurso() {
 		return origenCurso;
 	}

 	public void setOrigenCurso(Integer origenCurso) {
 		this.origenCurso = origenCurso;
 	}
    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion[ dTOReglasEvalucaionPK=" + dTOReglasEvalucaionPK + " ]";
    }

}
