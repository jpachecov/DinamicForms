package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.as.ASServiciosGeneralesInterface;
import mx.ine.observadoresINE.bsd.BSDServiciosGeneralesInterface;
import mx.org.ine.servicios.dto.db.DTODistrito;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOLocalidad;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

@Component("bsdServiciosGenerales")
@Scope("prototype")
public class BSDServiciosGeneralesImpl implements BSDServiciosGeneralesInterface{

	@Autowired
	@Qualifier("asServiciosGenerales")
	private ASServiciosGeneralesInterface asServicios;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DTOEstado> obtenEstados() throws Exception{
		return asServicios.obtenEstados();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DTOMunicipio> obtenMunicipios(Integer idEstado, EnumAmbitoSistema ambito) throws Exception{
		return asServicios.obtenMunicipios(idEstado, ambito);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DTOLocalidad> obtenLocalidades(Integer idEstado, Integer idMunicipio) throws Exception {
		return asServicios.obtenLocalidades(idEstado, idMunicipio);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DTOMunicipio obtenMunicipio(Integer idEstado, Integer idMunicipio, EnumAmbitoSistema ambito)
			throws Exception {
		return asServicios.obtenMunicipio(idEstado, idMunicipio, ambito);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DTOLocalidad obtenLocalidad(Integer idEstado, Integer idMunicipio, Integer idLocalidad) throws Exception {
		return asServicios.obtenLocalidad(idEstado, idMunicipio, idLocalidad);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DTODistrito> obtenDistritos(Integer idEstado, EnumAmbitoSistema ambito) throws Exception {
		return asServicios.obtenDistritos(idEstado, ambito);
	}

}
