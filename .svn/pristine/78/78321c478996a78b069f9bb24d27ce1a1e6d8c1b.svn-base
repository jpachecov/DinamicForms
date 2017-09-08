package mx.ine.observadoresINE.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.observadoresINE.dao.DAOAgrupacionesInterface;
import mx.ine.observadoresINE.dto.DTODatosAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK;

/**
 * 
 * Clase DAO para el módulo de agrupaciones.
 * @author Jean Pierre Pacheco Avila
 *
 */
@Repository("daoAgrupaciones")
@Scope("prototype")
public class DAOAgrupacionesImpl extends DAOGeneric<DTOAgrupaciones, DTOAgrupacionesPK>
		implements DAOAgrupacionesInterface {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DTOAgrupaciones obtenAgrupacion(DTOAgrupacionesPK pk) throws Exception {
		Criteria criteria;
		criteria = getSession().createCriteria(DTOAgrupaciones.class);
		criteria.add(Restrictions.eq("pk.idProcesoElectoral", pk.getIdProcesoElectoral()));
		criteria.add(Restrictions.eq("pk.idDetalleProceso", pk.getIdDetalleProceso()));
		criteria.add(Restrictions.eq("pk.idAgrupacion", pk.getIdAgrupacion()));
		return (DTOAgrupaciones) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void guardaActualiza(DTOAgrupaciones dto) throws Exception {
		guardarOactualizar(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DTOAgrupaciones> obtenAgrupaciones(Integer idProceso, Integer idDetalle) throws Exception {
		Criteria criteria;
		criteria = getSession().createCriteria(DTOAgrupaciones.class);
		criteria.add(Restrictions.eq("pk.idProcesoElectoral", idProceso));
		criteria.add(Restrictions.eq("pk.idDetalleProceso", idDetalle));

		return (List<DTOAgrupaciones>) criteria.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaAgrupaciones(DTOAgrupaciones dto) throws Exception {
		modificar(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeIdAgrupacion(Integer id) throws Exception {
		String q = "SELECT * FROM AGRUPACIONES WHERE ID_AGRUPACION = " + id;
		SQLQuery query = getSession().createSQLQuery(q);
		return query.list().size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeNombreAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception {
		String q = "SELECT * FROM AGRUPACIONES WHERE NOMBRE_AGRUPACION = '" + dto.getNombreAgrupacion() + "' ";
		if(pk != null){
			q += " AND ID_AGRUPACION <> " + pk.getIdAgrupacion();
		}
		SQLQuery query = getSession().createSQLQuery(q);
		return query.list().size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeAbreviAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception {
		String q = "SELECT * FROM AGRUPACIONES WHERE ABREVIATURA = '" + dto.getAbreviAgrupacion() + "'";
		if(pk != null){
			q += " AND ID_AGRUPACION <> " + pk.getIdAgrupacion();
		}
		SQLQuery query = getSession().createSQLQuery(q);
		return query.list().size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaAgrupacion(DTOAgrupaciones dto) throws Exception {
		eliminar(dto);
	}

}
