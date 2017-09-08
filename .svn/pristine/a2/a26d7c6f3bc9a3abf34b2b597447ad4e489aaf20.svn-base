/**
 * @(#)BSDAdministradorSistemaImpl.java 07/02/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import mx.ine.common.enums.EnumEstatusModulo;
import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;
import mx.ine.common.ws.api.exception.ClienteWebServiceException;
import mx.ine.observadoresINE.as.ASAdministradorSistemaInterface;
import mx.ine.observadoresINE.as.ASMenuServiceInterface;
import mx.ine.observadoresINE.bsd.BSDAdministradorSistemaInterface;
import mx.ine.observadoresINE.dto.db.DTOAccesosSistema;
import mx.org.ine.servicios.exception.ApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa los métodos para obtener datos de servicios de
 * administración
 *
 * @author Mayra Victoria
 * @since 09/09/16
 */
@Component("bsdAdmin")
@Scope("prototype")
public class BSDAdministradorSistemaImpl implements BSDAdministradorSistemaInterface {
    
    @Autowired
    @Qualifier("asAdmin")
    private ASAdministradorSistemaInterface asAdmin;

    @Autowired
    @Qualifier("asMenu")
    private transient ASMenuServiceInterface asMenu;
    
    /**
     * {@inheritDoc}
     */
     @Override
     public List<DTODetalleProcesoWS> obtenerDetallesProceso(Integer idSistema,
             String vigente, Integer anio, Integer idEstado, Integer idDistrito)
             throws ClienteWebServiceException {
         return asAdmin.obtenerDetallesProceso(idSistema, vigente, anio, idEstado, idDistrito);
     }

     /**
     * {@inheritDoc}
     */
     @Override
     public List<DTODetalleEstadoProcesoWS> obtenerEstadosDetalle(
             Integer idSistema, Integer idProceso, Integer idDetalle
             , Integer idEstado, Integer idDistrito, Integer idMunicipio
             , String vigente, Integer anio)
             throws ClienteWebServiceException {
         return asAdmin.obtenerEstadosDestalle(idSistema, idProceso, idDetalle
        		 , idEstado, idDistrito, idMunicipio, vigente, anio);
     }

     /**
     * {@inheritDoc}
     */
     @Override
     public List<DTODetalleDistritoProcesoWS> obtenerDistritosDetalle(
             Integer idSistema, Integer idProceso, Integer idDetalle,
             Integer idEstado, Integer idDistrito, String ambitoUsuario
	            , String vigente, Integer anio) throws ClienteWebServiceException {
         return asAdmin.obtenerDistritosDetalle(idSistema, idProceso, idDetalle, idEstado
        		 , idDistrito, ambitoUsuario, vigente, anio);
     }

     /**
     * {@inheritDoc}
     */
    @Override
    public String generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) throws ClienteWebServiceException {
        return asMenu.generaMenuLateral(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generaMenuAcciones(Integer idProceso, Integer idDetalle, Integer idSistema,
            Integer idEstado, Integer idDistrito, String grupo) throws ClienteWebServiceException {
            return asMenu.generaMenuAcciones(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo);
    }
    
    /**
     * {@inheritDoc}
     */
     @Override
     public EnumEstatusModulo obtenEstatusModulo(Integer idProceso,
             Integer idDetalle, Integer idSistema, Integer idEstado,
             Integer idDistrito, String grupo, Integer idModulo)
             throws ClienteWebServiceException {
         return asAdmin.obtenEstatusModulo(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo, idModulo);
     }

    /* (non-Javadoc)
     * @see mx.ine.sije.bsd.BSDAdministradorSistemaInterface#obtieneListaPermisosSistema(java.lang.Integer)
     */
    @Override
    public List<String> obtieneListaPermisosSistema(Integer idSistema) throws ApplicationException {
        return asAdmin.obtieneListaPermisosSistema(idSistema);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void guardarRegistroAcceso(DTOAccesosSistema usuario){
        asAdmin.guardarRegistroAcceso(usuario);
        
    }
}
