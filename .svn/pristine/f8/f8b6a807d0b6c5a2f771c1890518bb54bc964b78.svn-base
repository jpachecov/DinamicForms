/**
 * @(#)DAOCImagenesImpl.java 18/07/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.observadoresINE.dao.DAOCImagenesInterface;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCImagenesPK;

/**
 * <code>DAOCImagenesImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 18/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Repository("daoCImagenes")
@Scope("prototype")
public class DAOCImagenesImpl extends DAOGeneric<DTOCImagenes, DTOCImagenesPK> implements DAOCImagenesInterface{

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOCImagenesInterface#obtenRutas(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCImagenes> obtenRutas(Integer idProceso, Integer idDetalle,
			List<Integer> ListIdImagen) throws Exception {
		Criteria criteria;
		criteria = getSession().createCriteria(DTOCImagenes.class);
		criteria.add(Restrictions.eq("dTOCImagenesPK.idProcesoElectoral", idProceso));
		criteria.add(Restrictions.eq("dTOCImagenesPK.idDetalleProceso", idDetalle));
		if(ListIdImagen != null){
			criteria.add(Restrictions.in("dTOCImagenesPK.idImagen", ListIdImagen));
		}
		criteria.addOrder(Order.asc("dTOCImagenesPK.idImagen"));

		return (List<DTOCImagenes>) criteria.list();
	}


}
