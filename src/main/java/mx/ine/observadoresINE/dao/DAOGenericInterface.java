/**
 * @(#)DAOGenericInterface.java 06/06/2016
 *
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import mx.org.ine.servicios.dto.DTOBase;
import mx.org.ine.servicios.query.QRYContainerInterface;

/**
 * Esta interfaz define los métodos básicos de un DAO (insertar, actualizar, consultar o borrar informacion).
 * 
 * @author Israel Vázquez Jiménez
 * @since  06/06/2016
 */
public interface DAOGenericInterface<T extends Serializable, ID extends Serializable>{
	
	
	/* ----------------------------------------------------------------------------------------- */
	/* --------------------------------------- METODOS  ---------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */		
	

	/**
	 * Obtiene el contenedor de querys
	 * 
	 * @param 
	 * @return QRYContainerInterface contenedor con las querys.
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public QRYContainerInterface getContainer();

	/**
	 * Persiste un objeto a la base de datos .
	 * 
	 * @param  DTOBase: Objeto que se guardará en la base de datos
	 * @return DTOBase ID: Objeto que representa el identificador del objeto guardado en la base de datos
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 * 
	 */
	public ID guardar(DTOBase object);

	/**
	 * Modifica o actualiza un bojeto a la base de datos 
	 * 
	 * @param  DTOBase: Objeto que se modificará en la base de datos
	 * @return 
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void modificar(DTOBase object);

	/**
	 * Elimina un objeto de la base de datos
	 * 
	 * @param  DTOBase: Objeto que se eliminará en la base de datos
	 * @return 
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void eliminar(DTOBase object);
	
	/**
	 * Método genérico para obtener una la lista de objetos ordenados por un atributo ordenado ascendentemente
	 * 
	 * @param  String: Atributo por el cúal se ordenara el resultado
	 * @param  Boolean: Orden ascendente (true) o descendente (false)
	 * @return List<?>: Lista de objetos ordenados por el atributo definido
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public List<?> consultarTodosOrdenadosAscDescPor(String propiedadOrdenamiento, Boolean orden);
	
	/**
	 * Obtiene la sesión actual de base de datos.
	 * 
	 * @param
	 * @return Session : sesión actual de bd.
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public Session getSession();
	
	/**
	 * Obtiene la instancia del objeto que 
	 * se encarga de crear las sesiones de base de datos.
	 * 
	 * @param
	 * @return SessionFactory : el valor del atributo <code>sessionFactory</code>
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * Abre una sesión a base de datos.
	 * 
	 * @param
	 * @return Session : Sesión de bd.
	 *
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public Session openSession();
	
	/**
	 * Método que busca un objeto por su llave primaria
	 * 
	 * @param id : llave primaria del objeto que se busca
	 * @return T : Objeto encontrado
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public T buscarPorId(ID id);

	/**
	 * Método que busca todos los registros en una tabla
	 * 
	 * @param
	 * @return List<T> : lista con todos los objetos
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public List<T> buscarTodos();
	
	/**
	 *  Método para guardar (en caso de que sea un registro nuevo) o
	 *  actualizar (en caso un registro existente) un registro en la BD.
	 *   
	 * @param DTOBase: Objeto que se guardará o modificará en la base de datos
	 * @return
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void guardarOactualizar(DTOBase object);

	/**
	 * Método para eliminar un objeto pero con la regla donde 
	 * primero se modifica el registro y despues se elimina en la BD.
	 * 
	 * @param DTOBase: Objeto que se modificará y eliminará en la base de datos
	 * @return
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void modificarYeliminar(DTOBase object);
	
	/**
	 * Método que ejecuta una serie de codigos los cuales
	 * hacen que durante la consulta en las comparaciones no se haga 
	 * distinción entre Mayusculas, Minusculas y Acentos, por ejemplo
	 * si se hace un like sobre un campo digamos "Nombre" y se esta 
	 * pasando el valor "maria" y existen valores como María, Maria, mAria,
	 * este los traera como resultado.
	 * 
	 * @param 
	 * @return
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void activaMatchModeWords();

	/**
	 * Método para sincronizar los datos en sesión con los que se encuentran en la base de datos
	 * 
	 * @param 
	 * @return
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public void flush();
	
	/**
	 * Obtiene la clase de persistencia de este dao
	 * 
	 * @param 
	 * @return Class<T>: Clase de persistencia del DAO
	 * 
	 * @author Israel Vázquez Jiménez
	 * @since  06/06/2016
	 */
	public Class<T> getPersistentClass();

}
