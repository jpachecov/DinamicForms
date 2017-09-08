package mx.ine.observadoresINE.bsd;

import java.util.List;

import mx.ine.observadoresINE.dto.DTODatosAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK;

/**
 * 
 * @author Jean Pierre Pacheco Avila
 *
 */
public interface BSDAgrupacionesInterface {

	/**
	 * Obtiene los datos de una agrupacion
	 * 
	 * @param pk
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTOAgrupaciones obtenAgrupacion(DTOAgrupacionesPK pk) throws Exception;

	/**
	 * 
	 * Guarda una nueva agrupacion dados los datos capturados en el formulario.
	 * 
	 * Actualiza una agrupacion con los datos capturados.
	 * 
	 * @param agrupacion
	 * @author Jean Pierre Pacheco Avila
	 */
	public void guardaActualiza(DTODatosAgrupaciones dto) throws Exception;

	/**
	 * Obtiene todas las agrupaciones existentes para este proceso y detalle
	 * 
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public List<DTOAgrupaciones> obtenAgrupaciones(Integer idProceso, Integer idDetalle) throws Exception;

	/**
	 * Actualiza los datos una agrupacion
	 * @param pk
	 * @param datos
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public void actualizaAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones datos) throws Exception;
	
	/**
	 * Mapea de un tipo a otro
	 * @param dto
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTODatosAgrupaciones mapeaDatos(DTOAgrupaciones dto);
	
	/**
	 * Método que verifica que el nombre del
	 * titular de la agrupacion sea válido.
	 * @param dto
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean esNombreTitularValido(DTODatosAgrupaciones dto) throws Exception;
	
	/**
	 * Método que verifica que los apellidos
	 * del titular de la agrupación sean válidos.
	 * @param dto
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean sonApellidosInvalidos(DTODatosAgrupaciones dto) throws Exception;
	
	/**
	 * Método que verifica si existe el nombre de la agrupación en la tabla de agrupaciones.
	 * Si la pk es null entonces se verifica en todos los registros
	 * Si pk no es null, se usa el pk.idAgrupacion para excluir este registro
	 * de la consulta.
	 * @param pk - La pk de la agrupacion a excluir del query.
	 * @param dto - Los datos del formulario que contiene el nombre
	 * de agrupacion a verificar.
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean existeNombreAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception;
	
	/**
	 * Método que verifica si existe la abreviatura de la agrupación en la tabla de agrupaciones.
	 * Si la pk es null entonces se verifica en todos los registros
	 * Si pk no es null, se usa el pk.idAgrupacion para excluir este registro
	 * de la consulta.
	 * @param pk - La pk de la agrupacion a excluir del query.
	 * @param dto - Los datos del formulario que contiene la abreviatura
	 * de la agrupacion a verificar.
	 * @return
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public boolean existeAbreviAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception;
	
	/**
	 *
	 * Método que elimina una agrupación.
	 *
	 * @param pk - La llave primaria del registro de la agrupacion a eliminar.
	 * @throws Exception
	 * @author Jean Pierre Pacheco Avila
	 */
	public void eliminaAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones datos) throws Exception;
	
}
