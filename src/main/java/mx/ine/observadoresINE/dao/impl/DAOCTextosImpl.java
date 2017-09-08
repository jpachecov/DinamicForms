/**
 * @(#)DAOCTextosImpl.java 18/07/2017
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

import mx.ine.observadoresINE.dao.DAOCTextosInterface;
import mx.ine.observadoresINE.dto.db.DTOCTextos;
import mx.ine.observadoresINE.dto.db.DTOCTextosPK;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;

/**
 * <code>DAOCTextosImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 18/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Repository("daoCTextos")
@Scope("prototype")
public class DAOCTextosImpl extends DAOGeneric<DTOCTextos, DTOCTextosPK> implements DAOCTextosInterface{

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOCTextosInterface#obtenTextos(mx.ine.observadoresINE.dto.form.FormAcreditacionGafete)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCTextos> obtenTextos(Integer idProceso, Integer idDetalle, Integer idParrafo, 
			Integer idTexto) throws Exception {
		Criteria criteria;
		criteria = getSession().createCriteria(DTOCTextos.class);
		criteria.add(Restrictions.eq("dTOCTextosPK.idProcesoElectoral", idProceso));
		criteria.add(Restrictions.eq("dTOCTextosPK.idDetalleProceso", idDetalle));
		if(idParrafo != null){
			criteria.add(Restrictions.eq("dTOCTextosPK.idParrafo", idParrafo));
		}
		if(idTexto != null){
			criteria.add(Restrictions.eq("dTOCTextosPK.idTexto", idTexto));
		}
		criteria.addOrder(Order.asc("dTOCTextosPK.idParrafo"));
		criteria.addOrder(Order.asc("dTOCTextosPK.idTexto"));
		return (List<DTOCTextos>) criteria.list();
	}

}
