package mx.ine.observadoresINE.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.dto.DTODatosAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK;
import mx.ine.observadoresINE.mb.MBAgrupaciones;

/**
 * 
 * Clase para hacer validacioes en el módulo de Agrupaciones
 * 
 * @author jpachecov
 *
 */
@Component("boAgrupaciones")
@Scope("prototype")
public class BOAgrupaciones {

	/*
	 * LOGGER
	 */
	private static final Log log = LogFactory.getLog(BOAgrupaciones.class);

	/**
	 * Nos dice si los datos provenientes del formulario de captura son válidos
	 * o no.
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean esCapturaValida(DTODatosAgrupaciones dto) throws Exception {
		return true;
	}

	/**
	 * Devuelve un objeto de tipo DTOAgrupaciones con los datos provenientes del
	 * formulario de captura.
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DTOAgrupaciones generaDTO(DTODatosAgrupaciones dto) throws Exception {

		log.info("Agrupacion");
		log.info("Nombre: " + dto.getNombreAgrupacion());
		log.info("Abreviatura: " + dto.getAbreviAgrupacion());
		log.info("APaterno: " + dto.getaPaternoTitular());
		log.info("AMaterno: " + dto.getaMaternoTitular());
		log.info("NombreT : " + dto.getNombreTitular());
		log.info("Estado: " + dto.getEstado());
		log.info("Municipio: " + dto.getMunicipio());
		log.info("Colonia: " + dto.getColonia());
		log.info("CP: " + dto.getCodigoPostal());
		log.info("Calle: " + dto.getCalle());
		log.info("Num Ext: " + dto.getNumExterior());
		log.info("Num Int: " + dto.getNumInterior());
		log.info("Sin Num: " + dto.isSinNumero());
		log.info("Tel 1: " + dto.getTelefono1());
		log.info("Ext 1: " + dto.getExtension1());
		log.info("Tel 2: " + dto.getTelefono2());
		log.info("Ext 2: " + dto.getExtension2());
		log.info("lada1: " + dto.getLada1());
		log.info("lada2: " + dto.getLada2());
		log.info("Correo: " + dto.getCorreo());

		DTOAgrupacionesPK pk = new DTOAgrupacionesPK();
		pk.setIdProcesoElectoral(dto.getDtoUsuarioLogin().getIdProcesoElectoral());
		pk.setIdDetalleProceso(dto.getDtoUsuarioLogin().getIdDetalleProceso());
		// pk.setIdAgrupacion(getGeneratedId(dto));
		pk.setIdAgrupacion(0);
		generatedId(dto);

		DTOAgrupaciones agrupacion = new DTOAgrupaciones();
		agrupacion.setPk(pk);
		agrupacion.setNombreAgrupacion(dto.getNombreAgrupacion());

		if (dto.getAbreviAgrupacion() != null) {
			if (dto.getAbreviAgrupacion().trim().equals("")) {
				agrupacion.setAbreviatura(null);

			} else {
				agrupacion.setAbreviatura(dto.getAbreviAgrupacion());
			}
		} else {
			agrupacion.setAbreviatura(null);
		}

		agrupacion.setNombre(dto.getNombreTitular());
		agrupacion.setApellidoPaterno(dto.getaPaternoTitular());
		agrupacion.setApellidoMaterno(dto.getaMaternoTitular());
		agrupacion.setIdEstado(dto.getEstado());
		agrupacion.setIdMunicipio(dto.getMunicipio());

		agrupacion.setColonia(dto.getColonia());
		agrupacion.setCodigoPostal(dto.getCodigoPostal());
		agrupacion.setCalle(dto.getCalle());

		if (!dto.isSinNumero()) {
			agrupacion.setNumeroExterior(dto.getNumExterior());
			agrupacion.setNumeroInterior(dto.getNumInterior());
			agrupacion.setSinNumero("0");
		} else {
			agrupacion.setSinNumero("1");
		}

		// Datos de contacto

		agrupacion.setLada1(dto.getLada1());
		agrupacion.setLada2(dto.getLada2());
		agrupacion.setTelefono01(dto.getTelefono1());
		agrupacion.setExtension1(dto.getExtension1());
		agrupacion.setTelefono02(dto.getTelefono2());
		agrupacion.setExtension2(dto.getExtension2());

		return agrupacion;
	}

	/**
	 * Obtiene un DTOAgrupaciones con los datos y la pk dados.
	 * 
	 * @param pk
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DTOAgrupaciones generaDTO(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception {
		DTOAgrupaciones agrupacion = new DTOAgrupaciones();
		agrupacion.setPk(pk);
		agrupacion.setNombreAgrupacion(dto.getNombreAgrupacion());

		if (dto.getAbreviAgrupacion() != null) {
			if (dto.getAbreviAgrupacion().trim().equals("")) {
				agrupacion.setAbreviatura(null);

			} else {
				agrupacion.setAbreviatura(dto.getAbreviAgrupacion());
			}
		} else {
			agrupacion.setAbreviatura(null);
		}
		agrupacion.setNombre(dto.getNombreTitular());
		agrupacion.setApellidoPaterno(dto.getaPaternoTitular());
		agrupacion.setApellidoMaterno(dto.getaMaternoTitular());
		agrupacion.setIdEstado(dto.getEstado());
		agrupacion.setIdMunicipio(dto.getMunicipio());
		agrupacion.setColonia(dto.getColonia());
		agrupacion.setCodigoPostal(dto.getCodigoPostal());
		agrupacion.setCalle(dto.getCalle());

		if (!dto.isSinNumero()) {
			agrupacion.setNumeroExterior(dto.getNumExterior());
			agrupacion.setNumeroInterior(dto.getNumInterior());
			agrupacion.setSinNumero("0");
		} else {
			agrupacion.setSinNumero("1");
		}

		// Datos de contacto
		agrupacion.setLada1(dto.getLada1());
		agrupacion.setLada2(dto.getLada2());
		agrupacion.setTelefono01(dto.getTelefono1());
		agrupacion.setExtension1(dto.getExtension1());
		agrupacion.setTelefono02(dto.getTelefono2());
		agrupacion.setExtension2(dto.getExtension2());

		return agrupacion;
	}

	/**
	 * Mapea un objecto de tipo DTOAgrupaciones a uno de tipo
	 * DTODatosAgrupaciones para usarlo en la vista.
	 * 
	 * @param dto
	 * @return
	 */
	public DTODatosAgrupaciones obtenDatos(DTOAgrupaciones dto) {
		DTODatosAgrupaciones datos = new DTODatosAgrupaciones();
		datos.setNombreAgrupacion(dto.getNombreAgrupacion());
		datos.setAbreviAgrupacion(dto.getAbreviatura());
		datos.setNombreTitular(dto.getNombre());
		datos.setaPaternoTitular(dto.getApellidoPaterno());
		datos.setaMaternoTitular(dto.getApellidoMaterno());
		datos.setCodigoPostal(dto.getCodigoPostal());
		datos.setCalle(dto.getCalle());
		datos.setNumExterior(dto.getNumeroExterior());
		datos.setNumInterior(dto.getNumeroInterior());
		datos.setEstado(dto.getIdEstado());
		datos.setMunicipio(dto.getIdMunicipio());
		datos.setColonia(dto.getColonia());
		datos.setSinNumero((dto.getSinNumero().equals("0")) ? false : true);
		datos.setTelefono1(dto.getTelefono01());
		datos.setTelefono2(dto.getTelefono02());
		datos.setLada1(dto.getLada1());
		datos.setLada2(dto.getLada2());
		datos.setExtension1(dto.getExtension1());
		datos.setExtension2(dto.getExtension2());

		return datos;

	}

	public Integer getGeneratedId(DTODatosAgrupaciones dto) {

		String nom1 = dto.getNombreAgrupacion();
		String nom2 = dto.getNombreTitular();
		String nom3 = dto.getCalle();
		String c = dto.getAbreviAgrupacion() + dto.getaMaternoTitular() + dto.getaPaternoTitular();

		String n = nom1 + nom2 + nom3 + c;
		int r1 = (new Double(Math.floor((Math.random() * 2048)))).intValue();
		int r2 = (new Double(Math.floor((Math.random() * 2048)))).intValue();
		Double m = (n.hashCode() ^ (2 + r1)) / ((13 ^ r2) * Math.sqrt(5f));

		Integer k = Math.abs(m.intValue());
		k = k % 99999;

		log.info("Genera id");
		log.info("id: " + k);

		return k;

	}

	public Integer generatedId(DTODatosAgrupaciones dto) {
		String nom1 = dto.getNombreAgrupacion();
		String nom2 = dto.getNombreTitular();
		String nom3 = dto.getCalle();

		String n = nom1 + nom2 + nom3;
		char[] k = n.toCharArray();
		byte ti;
		Integer h = 0;
		int highorder;

		for (char ki : k) {
			ti = (byte) ki;
			highorder = h & 0xf8000000;
			h = h << 5;
			h = h ^ (highorder >> 27);
			h = h ^ ti;
		}

		log.info("CRC variant");
		log.info("Hash = " + h);

		return h;
	}

	public boolean esNombreTitularValido(DTODatosAgrupaciones dto) throws Exception {
		return dto.getNombreTitular() != null && !dto.getNombreTitular().equals("");
	}

	public boolean sonApellidosInvalidos(DTODatosAgrupaciones dto) throws Exception {
		return (dto.getaPaternoTitular() == null || dto.getaPaternoTitular().equals(""))
				&& (dto.getaMaternoTitular() == null || dto.getaMaternoTitular().equals(""));
	}

}
