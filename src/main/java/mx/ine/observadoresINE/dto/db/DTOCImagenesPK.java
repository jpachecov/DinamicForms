/**
 * @(#)DTOCImagenesPK.java 10/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 10/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOCImagenesPK implements Serializable {

    private static final long serialVersionUID = -9070369884566833416L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCESO_ELECTORAL")
    private int idProcesoElectoral;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETALLE_PROCESO")
    private int idDetalleProceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_IMAGEN")
    private int idImagen;

    public DTOCImagenesPK() {
    }

    public DTOCImagenesPK(int idProcesoElectoral, int idDetalleProceso, int idImagen) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idImagen = idImagen;
    }

    public int getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    public void setIdProcesoElectoral(int idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

    public int getIdDetalleProceso() {
        return idDetalleProceso;
    }

    public void setIdDetalleProceso(int idDetalleProceso) {
        this.idDetalleProceso = idDetalleProceso;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProcesoElectoral;
        hash += (int) idDetalleProceso;
        hash += (int) idImagen;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCImagenesPK)) {
            return false;
        }
        DTOCImagenesPK other = (DTOCImagenesPK) object;
        if (this.idProcesoElectoral != other.idProcesoElectoral) {
            return false;
        }
        if (this.idDetalleProceso != other.idDetalleProceso) {
            return false;
        }
        if (this.idImagen != other.idImagen) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCImagenesPK[ idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idImagen=" + idImagen + " ]";
    }

}
