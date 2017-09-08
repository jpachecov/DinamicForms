/**
 * @(#)DTOBitacoraReportesFiltrosPK.java 28/06/2017
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
public class DTOBitacoraReportesFiltrosPK implements Serializable {

    private static final long serialVersionUID = 6583564132284950236L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BITACORA")
    private long idBitacora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SISTEMA")
    private short idSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MODULO")
    private short idModulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FILTRO")
    private short idFiltro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VALOR")
    private int idValor;

    public DTOBitacoraReportesFiltrosPK() {
    }

    public DTOBitacoraReportesFiltrosPK(long idBitacora, short idSistema, short idModulo, short idFiltro, int idValor) {
        this.idBitacora = idBitacora;
        this.idSistema = idSistema;
        this.idModulo = idModulo;
        this.idFiltro = idFiltro;
        this.idValor = idValor;
    }

    public long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public short getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(short idSistema) {
        this.idSistema = idSistema;
    }

    public short getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(short idModulo) {
        this.idModulo = idModulo;
    }

    public short getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(short idFiltro) {
        this.idFiltro = idFiltro;
    }

    public int getIdValor() {
        return idValor;
    }

    public void setIdValor(int idValor) {
        this.idValor = idValor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idBitacora;
        hash += (int) idSistema;
        hash += (int) idModulo;
        hash += (int) idFiltro;
        hash += (int) idValor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOBitacoraReportesFiltrosPK)) {
            return false;
        }
        DTOBitacoraReportesFiltrosPK other = (DTOBitacoraReportesFiltrosPK) object;
        if (this.idBitacora != other.idBitacora) {
            return false;
        }
        if (this.idSistema != other.idSistema) {
            return false;
        }
        if (this.idModulo != other.idModulo) {
            return false;
        }
        if (this.idFiltro != other.idFiltro) {
            return false;
        }
        if (this.idValor != other.idValor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOBitacoraReportesFiltrosPK[ idBitacora=" + idBitacora + ", idSistema=" + idSistema + ", idModulo=" + idModulo + ", idFiltro=" + idFiltro + ", idValor=" + idValor + " ]";
    }

}
