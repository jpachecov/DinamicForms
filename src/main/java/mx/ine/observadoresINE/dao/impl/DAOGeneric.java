/**
 * @(#)DAOGeneric.java 06/06/2016
 *
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import mx.ine.observadoresINE.dao.DAOGenericInterface;
import mx.org.ine.servicios.dto.DTOBase;
import mx.org.ine.servicios.query.QRYContainerInterface;

/**
 * Esta clase define la implementación de los métodos básicos de un DAO (insertar, actualizar,
 * consultar o borrar informacion).
 * 
 * @author Israel Vázquez Jiménez
 * @since  06/06/2016
 */
public class DAOGeneric <T extends Serializable, ID extends Serializable> implements DAOGenericInterface<T, ID>{
	
	
	/* -------------------------------------------------------------------------------- */
	/* -------------------------------- ATRIBUTOS ------------------------------------- */
	/* -------------------------------------------------------------------------------- */	
	
	/**
	 * Creador de sesiones de base de datos.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Atributo de persistencia
	 */
	private Class<T> persistentClass;
	
	/**
	 * Contenedor de queries.
	 */
	@Autowired
	@Qualifier("qryContainer")
	@SessionScoped
	private QRYContainerInterface qryContainer;
	


	/* -------------------------------------------------------------------------------- */
	/* ------------------------------ CONSTRUCTOR ------------------------------------- */
	/* -------------------------------------------------------------------------------- */
	
	
	/**
	 * Constructor que recibe la clase de persistencia, se debe utilizar siempre este constructor para 
	 * inicializar los DAOs de la aplicación.
	 * 
	 * @param persistentClass La clase de un objeto que representa una entidad relacional de base de datos.
	 * @return 
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DAOGeneric()
	{
		this.persistentClass = ((Class)
		((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	
	/* -------------------------------------------------------------------------------- */
	/* --------------------------------- METODOS -------------------------------------- */
	/* -------------------------------------------------------------------------------- */	
	
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#getContainer()
	 */
	@Override
	public QRYContainerInterface getContainer() {
		return qryContainer;
	}

	/*
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#guardar(mx.org.ine.servicios.dto.DTOBase)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ID guardar(DTOBase object) {
		
		object.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		object.setFechaHora(new Date());		
		return (ID) getSession().save(object);
	}

	/*
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#modificar(mx.org.ine.servicios.dto.DTOBase)
	 */
	@Override
	public void modificar(DTOBase object) {
		
		object.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		object.setFechaHora(new Date());		
		getSession().update(object);
	}

	/*
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#eliminar(mx.org.ine.servicios.dto.DTOBase)
	 */
	@Override
	public void eliminar(DTOBase object) {
		
		modificar(object);
		getSession().delete(object);
	}
	
	/*
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#consultarTodosOrdenadosAscDescPor(java.lang.String, java.lang.Boolean)
	 */
	public List<?> consultarTodosOrdenadosAscDescPor(String propiedadOrdenamiento, Boolean orden) {
		Criteria criteria = getSession().createCriteria(this.getPersistentClass());
		
		if(orden != null && orden){
			criteria.addOrder(Order.asc(propiedadOrdenamiento));	
		}
		
		if(orden != null && orden){
			criteria.addOrder(Order.desc(propiedadOrdenamiento));	
		}
		
		return criteria.list();
	}
	
	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#getPersistentClass()
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#getSession()
	 */
	@Override
	public Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#getSessionFactory()
	 */
	@Override
	public SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#openSession()
	 */
	@Override
	public Session openSession() {
		
		return sessionFactory.openSession();
	}
	
	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#buscarPorId(ID)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T buscarPorId(ID id) {
		
		return (T) getSession().get(getPersistentClass(), id);
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#buscarTodos()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarTodos(){
		
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		
		return (List<T>) criteria.list();
    }

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#guardarOactualizar(mx.org.ine.servicios.dto.DTOBase)
	 */
	@Override
	public void guardarOactualizar(DTOBase object) {
		
		object.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		object.setFechaHora(new Date());
		getSession().saveOrUpdate(object);
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#modificarYeliminar(mx.org.ine.servicios.dto.DTOBase)
	 */
	@Override
	public void modificarYeliminar(DTOBase object) {
		
		object.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		object.setFechaHora(new Date());
		
		getSession().update(object);
		getSession().delete(object);
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#activaMatchModeWords()
	 */
	@Override
	public void activaMatchModeWords() {
		
		getSession().createSQLQuery("ALTER SESSION SET NLS_SORT=BINARY_AI").executeUpdate();
		getSession().createSQLQuery("ALTER SESSION SET NLS_COMP=LINGUISTIC").executeUpdate();
		getSession().createSQLQuery("COMMIT").executeUpdate();
	}

	/* 
	 * (non-Javadoc)
	 * (El comentario se encuentra en la interfase donde esta declarado el método)
	 * @see mx.ine.pautas.dao.DAOGenericPautasInterface#flush()
	 */
	@Override
	public void flush() {
		
		getSession().flush();
	}
	
}
