package mx.ine.observadoresINE.as.impl;

import mx.ine.common.helper.HLPAdministracionInterface;
import mx.ine.common.ws.api.exception.ClienteWebServiceException;
import mx.ine.observadoresINE.as.ASMenuServiceInterface;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * Clase que provee la inplementación de la interfaz 
 * que establece los métodos para el manejo de los menú
 * 
 * @author José Antonio López Torres
 * @since 24/11/2016
 * @copyright Direccion de sistemas - INE
 */
@Scope("prototype")
@Component("asMenu")
public class ASMenuService implements ASMenuServiceInterface{

    public static final Logger LOGGER = Logger.getLogger(ASMenuService.class);
    
    @Autowired
    @Qualifier("jsonParser")
    private transient Gson jsonParser;
    
    @Autowired
    @Qualifier("hlpAdministracion")
    private HLPAdministracionInterface hlpAdmin;

    /**
    * {@inheritDoc}
    */
    @Override
    public String generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) 
            throws ClienteWebServiceException{
//        return hlpAdmin.generaMenuLateral(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
        return hlpAdmin.generaMenuLateralConsejo(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String generaMenuAcciones(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) 
            throws ClienteWebServiceException{
//        return hlpAdmin.generaMenuAcciones(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
    	return hlpAdmin.generaMenuAccionesConsejo(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
    }
    
    
}
