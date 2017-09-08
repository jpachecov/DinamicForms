package mx.ine.observadoresINE.as;

import mx.ine.common.ws.api.exception.ClienteWebServiceException;

/**
 * Interfaz que establece los métodos para el manejo de los menú
 * 
 * @author José Antonio López Torres
 * @since 24/11/2016
 * @copyright Direccion de sistemas - INE
 */
public interface ASMenuServiceInterface {
    /**
     * Método utilizado para obtener la información de los módulos a mostrar 
     * en el menú según los datos geográficos seleccionados o cargados por el usuario
     * 
     * @param parametros
     * @return String : respuesta json
     */
    public String generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) throws ClienteWebServiceException;
    /**
     * Método utilizado para obtener las acciones de un módulo seleccionado
     * 
     * @param parametros
     * @return String : respuesta json
     */
    public String generaMenuAcciones(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) throws ClienteWebServiceException;
}
