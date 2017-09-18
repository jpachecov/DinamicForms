/**
 *
 */
package mx.ine.observadoresINE.as.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.common.enums.EnumEstatusModulo;
import mx.ine.common.helper.HLPAdministracionInterface;
import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleProcesoWS;
import mx.ine.common.ws.api.exception.ClienteWebServiceException;
import mx.ine.observadoresINE.as.ASAdministradorSistemaInterface;
import mx.ine.observadoresINE.dao.DAOAdministradorSistemaInterface;
import mx.ine.observadoresINE.dto.db.DTOAccesosSistema;
import mx.org.ine.servicios.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author INE
 *
 */
@Service("asAdmin")
@Scope("prototype")
public class ASAdministradorSistemaImpl implements ASAdministradorSistemaInterface {

    @Autowired
    @Qualifier("hlpAdministracion")
    private HLPAdministracionInterface hlpAdmin;

    @Autowired
    @Qualifier("daoAdministradorSistema")
    private DAOAdministradorSistemaInterface daoAdmin;
    
    private static final Log LOGGER = LogFactory.getLog(ASAdministradorSistemaImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTODetalleProcesoWS> obtenerDetallesProceso(Integer idSistema,
            String vigente, Integer anio, Integer idEstado, Integer idDistrito)
            throws ClienteWebServiceException {
        return hlpAdmin.obtenerDetallesProceso(idSistema, vigente, anio, idEstado, idDistrito);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTODetalleEstadoProcesoWS> obtenerEstadosDestalle(
            Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, Integer idMunicipio, String vigente, Integer anio)
            throws ClienteWebServiceException {
        return hlpAdmin.obtenerEstadosDestalleINE(idSistema, idProceso, idDetalle, idEstado, idDistrito, idMunicipio, vigente, anio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTODetalleDistritoProcesoWS> obtenerDistritosDetalle(
            Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, String ambitoUsuario, String vigente, Integer anio) throws ClienteWebServiceException {
        return hlpAdmin.obtenerDistritosDetalleINE(idSistema, idProceso, idDetalle, idEstado, idDistrito, ambitoUsuario, vigente, anio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumEstatusModulo obtenEstatusModulo(Integer idProceso,
            Integer idDetalle, Integer idSistema, Integer idEstado,
            Integer idDistrito, String grupo, Integer idModulo)
            throws ClienteWebServiceException {
        return hlpAdmin.obtenEstatusModulo(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo, idModulo);
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public EnumEstatusModulo obtenEstatusModuloINE(Integer idProceso, Integer idDetalle, Integer idSistema,
			Integer idEstado, Integer idDistrito, String grupo, Integer idModulo) throws ClienteWebServiceException {
		return hlpAdmin.obtenEstatusModuloINE(idProceso, idDetalle, idSistema, idEstado, idDistrito, grupo, idModulo);
	}
	
    /* (non-Javadoc)
		 * @see mx.ine.sije.as.ASAdministradorSistemaInterface#obtieneListaPermisosSistema(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true, rollbackFor = {Exception.class})
    public List<String> obtieneListaPermisosSistema(Integer idSistema) throws ApplicationException {
        return daoAdmin.obtieneListaPermisosSistema(idSistema);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void guardarRegistroAcceso(DTOAccesosSistema usuario) {
        try {
            daoAdmin.guardarRegistroAcceso(usuario);
        } catch (Exception e) {
            LOGGER.error("[Error] ASAdministradorSistema.guardarRegistroAcceso(): ", e);
        }
    }


}
