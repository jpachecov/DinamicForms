/**
 * @(#)DTOReportesBitacoraPK.java 28/06/2017
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
import javax.validation.constraints.Size;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOReportesBitacoraPK implements Serializable {

    private static final long serialVersionUID = 1440278142857132121L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    private short idEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DISTRITO")
    private short idDistrito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REPORTE")
    private short idReporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BITACORA")
    private int idBitacora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO")
    private String usuario;

    public DTOReportesBitacoraPK() {
    }

    public DTOReportesBitacoraPK(short idEstado, short idDistrito, short idReporte, int idBitacora, String usuario) {
        this.idEstado = idEstado;
        this.idDistrito = idDistrito;
        this.idReporte = idReporte;
        this.idBitacora = idBitacora;
        this.usuario = usuario;
    }

    public short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(short idEstado) {
        this.idEstado = idEstado;
    }

    public short getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(short idDistrito) {
        this.idDistrito = idDistrito;
    }

    public short getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(short idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEstado;
        hash += (int) idDistrito;
        hash += (int) idReporte;
        hash += (int) idBitacora;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOReportesBitacoraPK)) {
            return false;
        }
        DTOReportesBitacoraPK other = (DTOReportesBitacoraPK) object;
        if (this.idEstado != other.idEstado) {
            return false;
        }
        if (this.idDistrito != other.idDistrito) {
            return false;
        }
        if (this.idReporte != other.idReporte) {
            return false;
        }
        if (this.idBitacora != other.idBitacora) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOReportesBitacoraPK[ idEstado=" + idEstado + ", idDistrito=" + idDistrito + ", idReporte=" + idReporte + ", idBitacora=" + idBitacora + ", usuario=" + usuario + " ]";
    }

}
