/**
 * @(#)DTOBitacoraReportesFiltros.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "BITACORA_REPORTES_FILTROS", schema="OBSERVADORESINE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DTOBitacoraReportesFiltros.findAll", query = "SELECT d FROM DTOBitacoraReportesFiltros d")})
public class DTOBitacoraReportesFiltros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOBitacoraReportesFiltrosPK dTOBitacoraReportesFiltrosPK;
    @Size(max = 500)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumns({
        @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID_SISTEMA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_FILTRO", referencedColumnName = "ID_FILTRO", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DTOCFiltros dTOCFiltros;
    @JoinColumn(name = "ID_BITACORA", referencedColumnName = "ID_BITACORA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DTOBitacoraReportes dTOBitacoraReportes;

    public DTOBitacoraReportesFiltros() {
    }

    public DTOBitacoraReportesFiltros(DTOBitacoraReportesFiltrosPK dTOBitacoraReportesFiltrosPK) {
        this.dTOBitacoraReportesFiltrosPK = dTOBitacoraReportesFiltrosPK;
    }

    public DTOBitacoraReportesFiltros(long idBitacora, short idSistema, short idModulo, short idFiltro, int idValor) {
        this.dTOBitacoraReportesFiltrosPK = new DTOBitacoraReportesFiltrosPK(idBitacora, idSistema, idModulo, idFiltro, idValor);
    }

    public DTOBitacoraReportesFiltrosPK getDTOBitacoraReportesFiltrosPK() {
        return dTOBitacoraReportesFiltrosPK;
    }

    public void setDTOBitacoraReportesFiltrosPK(DTOBitacoraReportesFiltrosPK dTOBitacoraReportesFiltrosPK) {
        this.dTOBitacoraReportesFiltrosPK = dTOBitacoraReportesFiltrosPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public DTOCFiltros getDTOCFiltros() {
        return dTOCFiltros;
    }

    public void setDTOCFiltros(DTOCFiltros dTOCFiltros) {
        this.dTOCFiltros = dTOCFiltros;
    }

    public DTOBitacoraReportes getDTOBitacoraReportes() {
        return dTOBitacoraReportes;
    }

    public void setDTOBitacoraReportes(DTOBitacoraReportes dTOBitacoraReportes) {
        this.dTOBitacoraReportes = dTOBitacoraReportes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOBitacoraReportesFiltrosPK != null ? dTOBitacoraReportesFiltrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOBitacoraReportesFiltros)) {
            return false;
        }
        DTOBitacoraReportesFiltros other = (DTOBitacoraReportesFiltros) object;
        if ((this.dTOBitacoraReportesFiltrosPK == null && other.dTOBitacoraReportesFiltrosPK != null) || (this.dTOBitacoraReportesFiltrosPK != null && !this.dTOBitacoraReportesFiltrosPK.equals(other.dTOBitacoraReportesFiltrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOBitacoraReportesFiltros[ dTOBitacoraReportesFiltrosPK=" + dTOBitacoraReportesFiltrosPK + " ]";
    }

}
