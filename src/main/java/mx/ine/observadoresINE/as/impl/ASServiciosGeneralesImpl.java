package mx.ine.observadoresINE.as.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.as.ASServiciosGeneralesInterface;
import mx.ine.observadoresINE.dao.DAOServiciosGeneralesInterface;
import mx.org.ine.servicios.dto.db.DTODistrito;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOLocalidad;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

/**
 * 
 * @author jpachecov
 *
 */
@Service("asServiciosGenerales")
@Scope("prototype")
public class ASServiciosGeneralesImpl implements ASServiciosGeneralesInterface {

	@Autowired
	@Qualifier("daoServiciosGenerales")
	private transient DAOServiciosGeneralesInterface daoServicios;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public List<DTOEstado> obtenEstados() throws Exception{
		return daoServicios.obtenEstados();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public List<DTOMunicipio> obtenMunicipios(Integer idEstado, EnumAmbitoSistema ambito) throws Exception{
		return daoServicios.obtenMunicipios(idEstado, ambito);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public List<DTOLocalidad> obtenLocalidades(Integer idEstado, Integer idMunicipio) throws Exception {
		return daoServicios.obtenLocalidades(idEstado, idMunicipio);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public DTOMunicipio obtenMunicipio(Integer idEstado, Integer idMunicipio, EnumAmbitoSistema ambito)
			throws Exception {
		return daoServicios.obtenMunicipio(idEstado, idMunicipio, ambito);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public DTOLocalidad obtenLocalidad(Integer idEstado, Integer idMunicipio, Integer idLocalidad) throws Exception {
		return daoServicios.obtenLocalidad(idEstado, idMunicipio, idLocalidad);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor={Exception.class})
	public List<DTODistrito> obtenDistritos(Integer idEstado, EnumAmbitoSistema ambito) throws Exception {
		return daoServicios.obtenDistritos(idEstado, ambito);
	}

}
