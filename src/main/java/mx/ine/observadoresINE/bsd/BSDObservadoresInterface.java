package mx.ine.observadoresINE.bsd;

import java.util.List;

import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridades;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacion;
import mx.ine.observadoresINE.dto.db.DTOCJustificaciones;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.db.DTOObservadoresPK;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

public interface BSDObservadoresInterface {
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
	public void guarda(DTOObservadores observador) throws Exception ;

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
	
	/**
	 * Obtiene los datos del catalogo.
	 * @param dto
	 */
	public List<DTOCEscolaridades> obtenEscolaridad(DTOUsuarioLogin user);

	public List<DTOEstado> obtenEdos(DTOUsuarioLogin user);

	public List<DTOMunicipio> obtenMunicipios(DTOObservadores observadores);

	public List<DTOAgrupaciones> obtenListaAgrupaciones(DTOUsuarioLogin user);

	public List<DTOCEvaluacion> obtenListaEvaluaciones(DTOUsuarioLogin user);

	public List<DTOCursos> obtenListaCursos(DTOUsuarioLogin user, DTOReglasEvalucaion evaluacion);

	public List<DTOCJustificaciones> obtenerJustificacion(DTOUsuarioLogin user);

	public List<DTOReglasEvalucaion> obtenReglasE(DTOObservadores obs);

	public Boolean habilitaRatifica(DTOUsuarioLogin user);

	public Short generaEdad(String fechaCadena);

	public List<DTOObservadores> obtenerObservadores(DTOUsuarioLogin user);

	public boolean claveElectorExiste(DTOUsuarioLogin user, String claveCompleta);

	public List<DTOCJustificaciones> obtenJustificacionNA(DTOUsuarioLogin usuario);

	public Integer obtenIdObservador();

	public String obtenRutaFotoDefault(DTOUsuarioLogin user);
	
}
