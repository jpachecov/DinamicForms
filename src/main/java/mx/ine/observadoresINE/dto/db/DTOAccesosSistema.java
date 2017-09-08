/**
 * @(#)DTOAccesosSistema.java 17/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.org.ine.servicios.dto.DTOBase;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 17/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "ACCESOS_SISTEMA", schema = "OBSERVADORESINE")
@SuppressWarnings("PersistenceUnitPresent")
public class DTOAccesosSistema extends DTOBase implements Serializable {

    private static final long serialVersionUID = 4058109324612548265L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESO_SISTEMA")
    private int idAccesoSistema;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GRUPO")
    private String grupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaHoraEntrada;
    @Transient
    private Date fechaHora;

    public DTOAccesosSistema() {
    }

    public DTOAccesosSistema(int idAccesoSistema, String grupo, String usuario, Calendar fechaHoraEntrada) {
        this.idAccesoSistema = idAccesoSistema;
        this.grupo = grupo;
        this.usuario = usuario;
        this.fechaHoraEntrada = fechaHoraEntrada;
    }
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String getUsuario() {
        return usuario;
    }

    @Override
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Calendar getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Calendar fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }


    @Override
    public Date getFechaHora() {
        return fechaHora;
    }

    @Override
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getIdAccesoSistema() {
        return idAccesoSistema;
    }

    public void setIdAccesoSistema(int idAccesoSistema) {
        this.idAccesoSistema = idAccesoSistema;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idAccesoSistema;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DTOAccesosSistema other = (DTOAccesosSistema) obj;
        if (this.idAccesoSistema != other.idAccesoSistema) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTOAccesosSistema{" + "idAccesoSistema=" + idAccesoSistema + ", grupo=" + grupo + ", usuario=" + usuario + ", fechaHoraEntrada=" + fechaHoraEntrada + '}';
    }
    
    
}
