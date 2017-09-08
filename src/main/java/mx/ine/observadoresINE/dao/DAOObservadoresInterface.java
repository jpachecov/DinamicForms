package mx.ine.observadoresINE.dao;

import java.util.List;

import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridades;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacion;
import mx.ine.observadoresINE.dto.db.DTOCJustificaciones;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.db.DTOObservadoresPK;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion;

public interface DAOObservadoresInterface {
	
	/**
	 * Obtiene los datos de un/a observador/a.
	 * @param pk
	 * @return
	 */
	public DTOObservadores consultaObservador(DTOObservadoresPK identificador) throws Exception ;
	
	/**
	 * Agrega los datos de un/a observador/a.
	 * @param dto
	 * @throws Exception
	 */
	public void guarda(DTOObservadores observador) throws Exception;

	/**
	 * Modifica los datos de un/a observador/a.
	 * @param dto
	 * @throws Exception
	 */
	public void modifica(DTOObservadores observador) throws Exception ;
	/**
	 * Elimina los datos de un/a observador/a.
	 * @param dto
	 * @throws Exception
	 */
	public void elimina(DTOObservadores observador) throws Exception;

	public List<DTOCEscolaridades> obtenEscolaridad(DTOUsuarioLogin user);

	public List<DTOCEvaluacion> obtenListaEvaluaciones(DTOUsuarioLogin user);

	public List<DTOCJustificaciones> obtenerJustificacion(DTOUsuarioLogin user);

	public List<DTOReglasEvalucaion> obtenReglasE(DTOObservadores obs);

	public Boolean habilitaRatifica(DTOUsuarioLogin user);

	public Short generaEdad(String fechaCadena);

	public List<DTOObservadores> obtenerObservadores(DTOUsuarioLogin user);

	public List<DTOCEscolaridades> obtenEscolaridad(short idProcesoElectoral, short idDetalleProceso);

	public List<DTOCEvaluacion> obtenListaEvaluaciones(short idProcesoElectoral, short idDetalleProceso);

	public List<DTOCJustificaciones> obtenerJustificacion(short idProcesoElectoral, short idDetalleProceso);

	public List<DTOCursos> obtenCursos(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idOrigenSolicitud, Integer edo, Integer dtt);

	public boolean claveElectorExiste(DTOUsuarioLogin user, String claveCompleta);

	public List<DTOCJustificaciones> obtenJustificacionNA(DTOUsuarioLogin usuario);

	public Integer obtenIdObservador();

	public String obtenRutaFotoDefault(DTOUsuarioLogin user);

	public DTOCJustificaciones obtenerJustificacionNA(short idProcesoElectoral, short idDetalleProceso);

	 
}
