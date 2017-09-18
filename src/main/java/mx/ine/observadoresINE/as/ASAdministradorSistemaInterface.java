/**
 * 
 */
package mx.ine.observadoresINE.as;

import java.util.List;

import mx.ine.common.enums.EnumEstatusModulo;
import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;
import mx.ine.common.ws.api.exception.ClienteWebServiceException;
import mx.ine.observadoresINE.dto.db.DTOAccesosSistema;
import mx.org.ine.servicios.exception.ApplicationException;

/**
 * @author INE
 *
 */
public interface ASAdministradorSistemaInterface {
	/**
     * Método encagardo de obtener la lista de detalles proceso electoral dependiendo de los parámetros
     *
     * @param idSistema  : Id del sistema
     * @param vigente    : S-vigentes N-no vigentes (Puede ser nulo)
     * @param anio       : Año (Puede ser nulo)
     * @param idEstado   : Id estado (Puede ser nulo)
     * @param idDistrito : Id distrito (Puede ser nulo)
     *
     * @return List<DTODetalleProcesoWS> : Lista de detalles proceso
     *
     * @throws ClienteWebServiceException En caso de ocurrir un error durante la conexi&oacute;n con el webservice.
     * @author José Antonio López Torres
     * @since 07/02/2017
     */
    List<DTODetalleProcesoWS> obtenerDetallesProceso(Integer idSistema, String vigente, Integer anio, Integer idEstado,
            Integer idDistrito) throws ClienteWebServiceException;

    /**
     * Método encargado de obtener la lista de estados involucrados en el proceso electoral
     *
     * @param idSistema : Id del sistema
     * @param idProceso : Id proceso electoral
     * @param idDetalle : Id detalle proceso electoral (Puede ser nulo)
     *
     * @return List<DTODetalleEstadoProcesoWS> : Lista de estados
     *
     * @throws ClienteWebServiceException : En caso de ocurrir un error durante la conexi&oacute;n con el webservice.
     * @author José Antonio López Torres
     * @since 07/02/2017
     */
    List<DTODetalleEstadoProcesoWS> obtenerEstadosDestalle(Integer idSistema, Integer idProceso, Integer idDetalle
    		, Integer idEstado, Integer idDistrito, Integer idMunicipio
            , String vigente, Integer anio)
            throws ClienteWebServiceException;

    /**
     * Método encargado de obtener la lista de distritos invlucrados en el estado y proceso electoral
     *
     * @param idSistema : Id del sistema
     * @param idProceso : Id proceso electoral
     * @param idDetalle : Id detalle proceso electoral (puede ser nulo)
     * @param idEstado  : Id estado
     *
     * @return List<DTODetalleDistritoProcesoWS> : Lista de distritos
     *
     * @throws ClienteWebServiceException : En caso de ocurrir un error durante la conexi&oacute;n con el webservice.
     * @author José Antonio López Torres
     * @since 07/02/2017
     */
    List<DTODetalleDistritoProcesoWS> obtenerDistritosDetalle(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, String ambitoUsuario
            , String vigente, Integer anio) throws ClienteWebServiceException;
    
    /**
     * Método encargado de obtener el estatus del modulo
     * 
     * @param idProceso : Id proceso electoral
     * @param idDetalle : Id detalle proceso
     * @param idSistema : Id sistema
     * @param idEstado : Id estado
     * @param idDistrito : Id distrito
     * @param grupo : Grupo
     * @param idModulo : Id modulo
     * 
     * @return EnumEstatusModulo : estatus
     * 
     * @throws ClienteWebServiceException : En caso de ocurrir un error durante la conexi&oacute;n con el webservice.
     *
     * @author Pablo Zuñiga Mata 
     * @since 16/03/2017
     */
    EnumEstatusModulo obtenEstatusModulo(Integer idProceso, Integer idDetalle, Integer idSistema, Integer idEstado, Integer idDistrito, String grupo, Integer idModulo) 
            throws ClienteWebServiceException;

    /**
     * Método encargado de obtener el estatus del modulo
     * consumiento EL NUEVO SERVICIO
     * 
     * @param idProceso : Id proceso electoral
     * @param idDetalle : Id detalle proceso
     * @param idSistema : Id sistema
     * @param idEstado : Id estado
     * @param idDistrito : Id distrito
     * @param grupo : Grupo
     * @param idModulo : Id modulo
     * 
     * @return EnumEstatusModulo : estatus
     * 
     * @throws ClienteWebServiceException : En caso de ocurrir un error durante la conexi&oacute;n con el webservice.
     *
     * @since 13/09/2017
     */
    EnumEstatusModulo obtenEstatusModuloINE(Integer idProceso, Integer idDetalle, Integer idSistema, Integer idEstado, Integer idDistrito, String grupo, Integer idModulo) 
            throws ClienteWebServiceException;
    
    /**
     * Método para obtener la lista de grupos por sistema
     *
     * @author Pablo Zuñiga Mata
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 15/05/2017
     * @param idSistema
     * @return lista de grupos
     */
    public List<String> obtieneListaPermisosSistema(Integer idSistema) throws ApplicationException;
    
    /**
     * Método encargado de guardar un registro en la tabla ACCESOS_SISTEMA cada que un usario inicia sesión
     * 
     * @param usuario: usuario que acceso con la fecha en la que acceso
     * 
     * @author Emmanuel García Ysamit
     * @since 03/05/2017
     */
    public void guardarRegistroAcceso(DTOAccesosSistema usuario);

}
