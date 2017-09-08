/**
 * @(#)DTOCFiltrosPK.java 28/06/2017
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
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOCFiltrosPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2423110750005489219L;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "ID_SISTEMA")
    private Integer idSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MODULO")
    private Integer idModulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FILTRO")
    private Integer idFiltro;

    public DTOCFiltrosPK() {
    }

    public DTOCFiltrosPK(Integer idSistema, Integer idModulo, Integer idFiltro) {
        this.idSistema = idSistema;
        this.idModulo = idModulo;
        this.idFiltro = idFiltro;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSistema;
        hash += (int) idModulo;
        hash += (int) idFiltro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCFiltrosPK)) {
            return false;
        }
        DTOCFiltrosPK other = (DTOCFiltrosPK) object;
        if (this.idSistema != other.idSistema) {
            return false;
        }
        if (this.idModulo != other.idModulo) {
            return false;
        }
        if (this.idFiltro != other.idFiltro) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCFiltrosPK[ idSistema=" + idSistema + ", idModulo=" + idModulo + ", idFiltro=" + idFiltro + " ]";
    }

}
