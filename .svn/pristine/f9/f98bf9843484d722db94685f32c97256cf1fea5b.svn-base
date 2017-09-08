/**
 * @(#)DAOAdministradorSistemaImpl.java 03/05/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.ine.observadoresINE.dao.DAOAdministradorSistemaInterface;
import mx.ine.observadoresINE.dto.db.DTOAccesosSistema;

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pablo Zuñiga Mata
 * @since 03/05/2017
 * @copyright Direccion de sistemas - INE
 */
@Repository("daoAdministradorSistema")
@Scope("prototype")
public class DAOAdministradorSistemaImpl extends DAOGeneric<DTOAccesosSistema, Integer> implements DAOAdministradorSistemaInterface {


    /* (non-Javadoc)
	 * @see mx.ine.sije.dao.DAOAdministradorSistemaInterface#obtieneListaPermisosSistema(java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> obtieneListaPermisosSistema(Integer idSistema) {
        List<Object[]> tmp = new ArrayList<Object[]>();
        List<String> listaGrupos = new ArrayList<String>();
        String sql = getContainer().getQuery("query_admin_obtenGruposSistema");
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("SISTEMA", idSistema);
        tmp = (List<Object[]>) query.list();
		if(tmp != null){
			for(Object[] obj:tmp){
                listaGrupos.add(obj[1].toString());
            }

        }
        return listaGrupos;
    }

    @Override
    public void guardarRegistroAcceso(DTOAccesosSistema usuario) {
        guardar(usuario);
    }

}
