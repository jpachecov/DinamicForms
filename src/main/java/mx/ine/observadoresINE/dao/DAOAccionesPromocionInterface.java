package mx.ine.observadoresINE.dao;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;

public interface DAOAccionesPromocionInterface {

	/**
	 * Guarda en OBSERVADORESINE.ACCIONES_PROMOCION una acción de promoción
	 * específica
	 * 
	 * @param accionDePromocion
	 * @throws Exception
	 */
	void guardar(DTOAccionesPromocion accionDePromocion) throws Exception;

	/**
	 * Obtiene la lista de acciones de promoción de la tabla
	 * OBSERVADORESINE.ACCIONES_PROMOCION
	 * 
	 * @param filtros
	 * @param idProceso
	 * @param idDetalle
	 * @param idEstado 
	 * @param idDistrito 
	 * @return
	 * @throws Exception
	 */
	List<DTOAccionesPromocion> consultaAccionesPromocion(
			DTOAccionesPromocion filtros, Integer idProceso, Integer idDetalle,
			Integer idEstado, Integer idDistrito)
			throws Exception;
	
	/**
	 * Elimina en OBSERVADORESINE.ACCIONES_PROMOCION una acción de promoción
	 * específica
	 * 
	 * @param accionDePromocion
	 * @throws Exception
	 */
	 void eliminar(DTOAccionesPromocion accionDePromocion) throws Exception;
	 
	 /**
	  * Modifica el idAccion, fechaAccion, descripcion, usuario y fechaHora para 
	  * el registro seleccionado
	  * @param accionDePromocion
	  * @throws Exception
	  */
	 void modificar(DTOAccionesPromocion accionDePromocion) throws Exception;

}
