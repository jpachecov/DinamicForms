/**
 * @(#)DTOCFiltros.java 28/06/2017
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
@Table(name = "C_FILTROS", schema="OBSERVADORESINE")
public class DTOCFiltros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOCFiltrosPK dTOCFiltrosPK;
    @Size(max = 500)
    @Column(name = "NOMBRE_FILTRO")
    private String nombreFiltro;
    @Size(max = 250)
    @Column(name = "TABLA_FILTRO")
    private String tablaFiltro;
    @Size(max = 100)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dTOCFiltros", fetch = FetchType.LAZY)
    private List<DTOBitacoraReportesFiltros> dTOBitacoraReportesFiltrosList;

    public DTOCFiltros() {
    }

    public DTOCFiltros(DTOCFiltrosPK dTOCFiltrosPK) {
        this.dTOCFiltrosPK = dTOCFiltrosPK;
    }

    public DTOCFiltros(DTOCFiltrosPK dTOCFiltrosPK, String usuario, Date fechaHora) {
        this.dTOCFiltrosPK = dTOCFiltrosPK;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }

    public DTOCFiltros(Integer idSistema, Integer idModulo, Integer idFiltro) {
        this.dTOCFiltrosPK = new DTOCFiltrosPK(idSistema, idModulo, idFiltro);
    }

    public DTOCFiltrosPK getDTOCFiltrosPK() {
        return dTOCFiltrosPK;
    }

    public void setDTOCFiltrosPK(DTOCFiltrosPK dTOCFiltrosPK) {
        this.dTOCFiltrosPK = dTOCFiltrosPK;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public String getTablaFiltro() {
        return tablaFiltro;
    }

    public void setTablaFiltro(String tablaFiltro) {
        this.tablaFiltro = tablaFiltro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        hash += (dTOCFiltrosPK != null ? dTOCFiltrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCFiltros)) {
            return false;
        }
        DTOCFiltros other = (DTOCFiltros) object;
        if ((this.dTOCFiltrosPK == null && other.dTOCFiltrosPK != null) || (this.dTOCFiltrosPK != null && !this.dTOCFiltrosPK.equals(other.dTOCFiltrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCFiltros[ dTOCFiltrosPK=" + dTOCFiltrosPK + " ]";
    }

}
