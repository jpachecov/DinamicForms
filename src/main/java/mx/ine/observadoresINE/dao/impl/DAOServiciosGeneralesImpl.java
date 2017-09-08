package mx.ine.observadoresINE.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.dao.DAOServiciosGeneralesInterface;
import mx.org.ine.servicios.dto.db.DTODistrito;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOLocalidad;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

@Repository("daoServiciosGenerales")
@Scope("prototype")
public class DAOServiciosGeneralesImpl extends DAOGeneric<Serializable, Serializable>
		implements DAOServiciosGeneralesInterface {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DTOEstado> obtenEstados() throws Exception {
		//TODO agregar ID_CORTE
		List<DTOEstado> estados = new ArrayList<DTOEstado>();

		String q = "SELECT ID_ESTADO idEstado, NOMBRE_ESTADO nombreEstado ";
		q += " FROM GEOGRAFICOINE.ESTADOS ";
		q += " WHERE ID_ESTADO > 0  order by 1";

		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idEstado", IntegerType.INSTANCE);
		query.addScalar("nombreEstado", StringType.INSTANCE);
		List<Object[]> lista = query.list();

		DTOEstado edo;
		for (int i = 0; i < lista.size(); i++) {
			Object[] est = lista.get(i);
			edo = new DTOEstado();
			edo.setIdEstado((Integer)est[0]);
			edo.setNombreEstado((String) est[1]);

			estados.add(edo);
		}

		return estados;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DTODistrito> obtenDistritos(Integer idEstado, EnumAmbitoSistema ambito) throws Exception {
		//TODO agregar ID_CORTE
		List<DTODistrito> distritos = new ArrayList<DTODistrito>();
		
		String q = "SELECT ID_DISTRITO_<AMBITO> idDistrito, CABECERA_DISTRITAL_<AMBITO> nombreDto FROM ";
		if(ambito == EnumAmbitoSistema.F){
			q = q.replace("<AMBITO>", "FEDERAL");
			q += " GEOGRAFICOINE.DISTRITOS_FEDERALES";
		} else {
			q = q.replace("<AMBITO>", "LOCAL");
			q += " GEOGRAFICOINE.DISTRITOS_LOCALES";			
		}
		q += " WHERE ID_ESTADO = " + idEstado;
		
		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idDistrito", IntegerType.INSTANCE);
		query.addScalar("nombreDto", StringType.INSTANCE);
		List<Object[]> lista = query.list();
		
		DTODistrito dtto;
		for(int i = 0; i < lista.size(); i++){
			Object[] dt = lista.get(i);
			dtto = new DTODistrito();
			dtto.setIdEstado(idEstado);
			dtto.setIdDistrito((Integer)dt[0]);
			dtto.setCabeceraDistrital((String)dt[1]);
			
			distritos.add(dtto);
		}
		
		return distritos;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DTOMunicipio> obtenMunicipios(Integer idEstado, EnumAmbitoSistema ambito) throws Exception {
		//TODO agregar ID_CORTE
		List<DTOMunicipio> municipios = new ArrayList<DTOMunicipio>();

		String q = "SELECT ";

		if (ambito == EnumAmbitoSistema.F) {
			q += " ID_MUNICIPIO idMunicipio, NOMBRE_MUNICIPIO nombreMunicipio ";
			q += " FROM GEOGRAFICOINE.MUNICIPIOS ";

		} else {
			throw new Exception("Sólo están definidos los MUNICIPIOS en ÁMBITO FEDERAL");
		}

		q += " WHERE ID_ESTADO = " + idEstado;
		q += " ORDER BY idMunicipio";

		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idMunicipio", IntegerType.INSTANCE);
		query.addScalar("nombreMunicipio", StringType.INSTANCE);

		List<Object[]> lista = query.list();
		DTOMunicipio mun;
		for (Object[] m : lista) {
			mun = new DTOMunicipio();
			mun.setIdMunicipio((Integer) m[0]);
			mun.setNombreMunicipio((String) m[1]);
			municipios.add(mun);
		}

		return municipios;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DTOLocalidad> obtenLocalidades(Integer idEstado, Integer idMunicipio) throws Exception {
		//TODO agregar ID_CORTE
		List<DTOLocalidad> localidades = new ArrayList<DTOLocalidad>();

		String q = "SELECT ID_LOCALIDAD idLocalidad, NOMBRE_LOCALIDAD nombreLocalidad ";
		q += " FROM GEOGRAFICOINE.LOCALIDADES ";
		q += " WHERE ID_ESTADO = " + idEstado;
		q += " AND ID_MUNICIPIO = " + idMunicipio;

		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idLocalidad", IntegerType.INSTANCE);
		query.addScalar("nombreLocalidad", StringType.INSTANCE);

		List<Object[]> lista = query.list();
		DTOLocalidad loc;

		for (Object[] l : lista) {
			loc = new DTOLocalidad();
			loc.setIdLocalidad((Integer) l[0]);
			loc.setNombreLocalidad((String) l[1]);
			localidades.add(loc);
		}
		return localidades;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public DTOMunicipio obtenMunicipio(Integer idEstado, Integer idMunicipio, EnumAmbitoSistema ambito)
			throws Exception {
		//TODO agregar ID_CORTE
		DTOMunicipio mun = new DTOMunicipio();
		String q = "SELECT ";

		if (ambito == EnumAmbitoSistema.F) {
			q += " ID_MUNICIPIO idMunicipio, NOMBRE_MUNICIPIO nombreMunicipio ";
			q += " FROM GEOGRAFICOINE.MUNICIPIOS ";

		} else {
			throw new Exception("Sólo están definidos los MUNICIPIOS en ÁMBITO FEDERAL");
		}

		q += " WHERE ID_ESTADO = " + idEstado;
		q += " AND ID_MUNICIPIO = " + idMunicipio;

		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idMunicipio", IntegerType.INSTANCE);
		query.addScalar("nombreMunicipio", StringType.INSTANCE);

		Object[] m = (Object[]) query.uniqueResult();
		
		if(m == null || m.length == 0){
			throw new Exception("No se encontró municipio con los parámetros dados.");
		}
		
		mun.setIdMunicipio((Integer) m[0]);
		mun.setNombreMunicipio((String) m[1]);
		
		return mun;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public DTOLocalidad obtenLocalidad(Integer idEstado, Integer idMunicipio, Integer idLocalidad) throws Exception {
		//TODO agregar ID_CORTE
		DTOLocalidad loc = new DTOLocalidad();
		String q = "SELECT ID_LOCALIDAD idLocalidad, NOMBRE_LOCALIDAD nombreLocalidad ";
		q += " FROM GEOGRAFICOINE.LOCALIDADES ";
		q += " WHERE ID_ESTADO = " + idEstado;
		q += " AND ID_MUNICIPIO = " + idMunicipio;
		q += " AND ID_LOCALIDAD = " + idLocalidad;

		SQLQuery query = getSession().createSQLQuery(q);
		query.addScalar("idLocalidad", IntegerType.INSTANCE);
		query.addScalar("nombreLocalidad", StringType.INSTANCE);

		Object[] l = (Object[]) query.uniqueResult();
		
		if(l == null || l.length == 0){
			throw new Exception("No se encontró localidad con los parámetros dados.");
		}
		
		loc.setIdLocalidad((Integer) l[0]);
		loc.setNombreLocalidad((String) l[1]);

		return loc;
	}
}
