/**
 * @(#)DTOBitacoraReportes.java 28/06/2017
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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "BITACORA_REPORTES",schema="OBSERVADORESINE")
public class DTOBitacoraReportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BITACORA")
    private Long idBitacora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SISTEMA")
    private short idSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MODULO")
    private short idModulo;
    @Size(max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOBitacoraReportes", fetch = FetchType.LAZY)
    private List<DTOBitacoraReportesFiltros> dTOBitacoraReportesFiltrosList;

    public DTOBitacoraReportes() {
    }

    public DTOBitacoraReportes(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public DTOBitacoraReportes(Long idBitacora, short idSistema, short idModulo) {
        this.idBitacora = idBitacora;
        this.idSistema = idSistema;
        this.idModulo = idModulo;
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
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

    public List<DTOBitacoraReportesFiltros> getDTOBitacoraReportesFiltrosList() {
        return dTOBitacoraReportesFiltrosList;
    }

    public void setDTOBitacoraReportesFiltrosList(List<DTOBitacoraReportesFiltros> dTOBitacoraReportesFiltrosList) {
        this.dTOBitacoraReportesFiltrosList = dTOBitacoraReportesFiltrosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOBitacoraReportes)) {
            return false;
        }
        DTOBitacoraReportes other = (DTOBitacoraReportes) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOBitacoraReportes[ idBitacora=" + idBitacora + " ]";
    }

}
