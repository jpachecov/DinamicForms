package mx.ine.observadoresINE.bsd;

import java.util.List;

import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;

public interface BSDAccionesPromocionInterface {

	/**
	 * Guarda o modifica en OBSERVADORESINE.ACCIONES_PROMOCION una acción de
	 * promoción específica
	 * 
	 * @param accionDePromocion
	 * @param usuarioLogin
	 * @throws Exception
	 */
	void guardar(DTOAccionesPromocion accionDePromocion, DTOUsuarioLogin usuario)
			throws Exception;

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
			DTOAccionesPromocion filtros, DTOUsuarioLogin usuario) throws Exception;

	/**
	 * Elimina en OBSERVADORESINE.ACCIONES_PROMOCION una acción de promoción
	 * específica
	 * 
	 * @param accionDePromocion
	 * @throws Exception
	 */
	void eliminar(DTOAccionesPromocion accionDePromocion) throws Exception;

	/**
	 * Modifica en OBSERVADORESINE.ACCIONES_PROMOCION una acción de promoción
	 * específica
	 * 
	 * @param accionDePromocion
	 * @throws Exception
	 */
	void modificar(DTOAccionesPromocion accionDePromocion) throws Exception;

}
