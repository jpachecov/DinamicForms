/**
 * @(#)DTOAccionesPromocion.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.org.ine.servicios.dto.DTOBase;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "ACCIONES_PROMOCION", schema = "OBSERVADORESINE")
public class DTOAccionesPromocion extends DTOBase implements Serializable {

	private static final long serialVersionUID = 280620170620L;
	@EmbeddedId
	private DTOAccionesPromocionPK dtoAccionesPromocionPK;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_ESTADO")
	private Integer idEstado;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_DISTRITO")
	private Integer idDistrito;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_ACCION")
	private Integer idAccion;
	
	/*@OneToOne(optional=false, fetch=FetchType.EAGER)	
	@JoinColumns({
		@JoinColumn(name = "ID_ACCION", insertable=false, updatable=false),
		@JoinColumn(name = "ID_DETALLE_PROCESO", insertable=false, updatable=false),
		@JoinColumn(name = "ID_PROCESO_ELECTORAL", insertable=false, updatable=false)
	})
	private DTOCAcciones dtoAccion;*/
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "FECHA_ACCION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAccion;
	@Basic(optional = false)
	@NotNull
	@NotBlank@NotEmpty
	@Column(name = "DESCRIPCION")
	private String descripcion;
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
	
	public DTOAccionesPromocion() {
	}

	public DTOAccionesPromocion(DTOAccionesPromocionPK dtoAccionesPromocionPK) {
		this.dtoAccionesPromocionPK = dtoAccionesPromocionPK;
	}

	public DTOAccionesPromocion(DTOAccionesPromocionPK dtoAccionesPromocionPK,
			Integer idEstado, Integer idDistrito, Integer idAccion,
			Date fechaAccion, String descripcion, String usuario, Date fechaHora) {
		this.dtoAccionesPromocionPK = dtoAccionesPromocionPK;
		this.idEstado = idEstado;
		this.idDistrito = idDistrito;
		this.idAccion = idAccion;
		this.fechaAccion = fechaAccion;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fechaHora = fechaHora;
	}

	public DTOAccionesPromocion(int idProcesoElectoral, int idDetalleProceso,
			int idAccionesPromocion) {
		this.dtoAccionesPromocionPK = new DTOAccionesPromocionPK(
				idProcesoElectoral, idDetalleProceso, idAccionesPromocion);
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
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

	public DTOAccionesPromocionPK getDtoAccionesPromocionPK() {
		return dtoAccionesPromocionPK;
	}

	public void setDtoAccionesPromocionPK(
			DTOAccionesPromocionPK dtoAccionesPromocionPK) {
		this.dtoAccionesPromocionPK = dtoAccionesPromocionPK;
	}

	/*
	 * public DTOCAcciones getDtoAccion() { return dtoAccion; }
	 */

}
