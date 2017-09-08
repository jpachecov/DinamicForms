package mx.ine.observadoresINE.dao.impl;

import java.util.List;

import mx.ine.observadoresINE.dao.DAOAccionesPromocionInterface;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocionPK;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("daoAccionesPromocion")
@Scope("prototype")
public class DAOAccionesPromocionImpl extends
		DAOGeneric<DTOAccionesPromocion, DTOAccionesPromocionPK> implements
		DAOAccionesPromocionInterface {

	private static final Log logger = LogFactory
			.getLog(DAOAccionesPromocionImpl.class);

	@Override
	public void guardar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		logger.info("DAOAccionesPromocionImpl.guardar");
		super.guardarOactualizar(accionDePromocion);
		getSession().flush();
		getSession().clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOAccionesPromocion> consultaAccionesPromocion(
			DTOAccionesPromocion filtros, Integer idProceso, Integer idDetalle,
			Integer idEstado, Integer idDistrito) throws Exception {

		List<DTOAccionesPromocion> result = null;
		logger.info("DAOAccionesPromocionImpl.consultaAccionesPromocion");
		Session session = getSession();
		Criteria cr = session.createCriteria(DTOAccionesPromocion.class);
		cr.add(Restrictions.eq("dtoAccionesPromocionPK.idProcesoElectoral",
				idProceso));
		cr.add(Restrictions.eq("dtoAccionesPromocionPK.idDetalleProceso",
				idDetalle));
		cr.add(Restrictions.eq("idEstado", idEstado));
		cr.add(Restrictions.eq("idDistrito", idDistrito));
		cr.add(Restrictions.eq("idAccion", filtros.getIdAccion()));
		cr.add(Restrictions.eq("fechaAccion", filtros.getFechaAccion()));
		result = cr.list();
		return result;
	}

	@Override
	public void eliminar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		super.eliminar(accionDePromocion);
		getSession().flush();
		getSession().clear();
	}
	
	@Override
	public void modificar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		super.modificar(accionDePromocion);
		getSession().flush();
		getSession().clear();
	}

}
