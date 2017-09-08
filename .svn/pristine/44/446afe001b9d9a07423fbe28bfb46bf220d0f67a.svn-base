package mx.ine.observadoresINE.as.impl;

import java.util.List;

import mx.ine.observadoresINE.as.ASAccionesPromocionInterface;
import mx.ine.observadoresINE.dao.DAOAccionesPromocionInterface;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Scope("prototype")
@Service("asAccionesPromocion")
public class ASAccionesPromocionImpl implements ASAccionesPromocionInterface {

	@Autowired
	@Qualifier("daoAccionesPromocion")
	private transient DAOAccionesPromocionInterface daoAccionesPromocion;

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void guardar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		daoAccionesPromocion.guardar(accionDePromocion);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public List<DTOAccionesPromocion> consultaAccionesPromocion(
			DTOAccionesPromocion filtros, Integer idProceso, Integer idDetalle,
			Integer idEstado, Integer idDistrito) throws Exception {
		return daoAccionesPromocion.consultaAccionesPromocion(filtros,
				idProceso, idDetalle, idEstado, idDistrito);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void eliminar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		daoAccionesPromocion.eliminar(accionDePromocion);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void modificar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		daoAccionesPromocion.modificar(accionDePromocion);

	}

}
