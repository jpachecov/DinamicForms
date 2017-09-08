package mx.ine.observadoresINE.as;

import java.util.List;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.org.ine.servicios.dto.db.DTODistrito;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOLocalidad;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

/**
 * 
 * @author jpachecov
 *
 */
public interface ASServiciosGeneralesInterface {

	/**
	 * Método que obtiene la lista de entidades federativas.
	 * @return
	 * @throws Exception
	 */
	public List<DTOEstado> obtenEstados() throws Exception;
	
	/**
	 * Método que obtiene la lista de distritos correspondientes al id del
	 * estado dado.
	 * 
	 * @param idEstado
	 * @return
	 * @throws Exception
	 */
	public List<DTODistrito> obtenDistritos(Integer idEstado, EnumAmbitoSistema ambito) throws Exception;

	/**
	 * Método que obtiene la lista de municipios para un estado y el ámbito.
	 * @param idEstado - EL id del estado del que se requieren sus municipios.
	 * @param ambito - Ambito Local o Federal. En el NUEVO GEOGRAFICO sólo Federal.
	 * @return
	 * @throws Exception
	 */
	public List<DTOMunicipio> obtenMunicipios(Integer idEstado, EnumAmbitoSistema ambito) throws Exception;
	
	/**
	 * Método que obtiene las localides por Estado - Municipio
	 * @param idEstado - El id del estado.
	 * @param idMunicipio - El id del municipio.
	 * @return
	 * @throws Exception
	 */
	public List<DTOLocalidad> obtenLocalidades(Integer idEstado, Integer idMunicipio) throws Exception;
	
	/**
	 * Obtiene un municipio especifico.
	 * @param idEstado
	 * @param idMunicipio
	 * @param ambito
	 * @return
	 * @throws Exception
	 */
	public DTOMunicipio obtenMunicipio(Integer idEstado, Integer idMunicipio, EnumAmbitoSistema ambito) throws Exception;
	
	/**
	 * Método que obtiene una localidad especifica.
	 * @param idEstado
	 * @param idMunicipio
	 * @param idLocalidad
	 * @return
	 * @throws Exception
	 */
	public DTOLocalidad obtenLocalidad(Integer idEstado, Integer idMunicipio, Integer idLocalidad) throws Exception;
}
