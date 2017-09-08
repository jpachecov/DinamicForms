/**
 * @(#)BSDReporteControlObsImp.java 16/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;
import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDReporteControlObsInterface;
import mx.ine.observadoresINE.dto.DTOList;
import mx.ine.observadoresINE.dto.form.FormRepControlObs;
import mx.org.ine.servicios.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 16/08/2017
 * @copyright Direccion de sistemas - INE
 */
@Component("bsdReporteControlObs")
@Scope("prototype")
public class BSDReporteControlObsImp implements BSDReporteControlObsInterface {

    @Autowired
    @Qualifier("asReportes")
    private ASReportesInterface asReportes;
    private static final Log LOGGER = LogFactory.getLog(BSDReporteControlObsImp.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object[]> obtenerDatosReporte(FormRepControlObs form) throws ApplicationException {
        return asReportes.consultarReporteControlObs(form);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTOList> obtenerAbreviaturaEstados() throws ApplicationException {
        return asReportes.obtenerAbreviaturaEstados();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object[]> consultarReporteControlObsDinamico(FormRepControlObs form) throws ApplicationException {
        return asReportes.consultarReporteControlObsDinamico(form);   
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTOList> obtenerJustificacionesAprobadas(FormRepControlObs form) throws ApplicationException {
       return asReportes.obtenerJustificacionesAprobadas(form);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object[]> consultarEncabezadoCanDenDecl(FormRepControlObs form) throws ApplicationException {
        return asReportes.consultarEncabezadoCanDenDecl(form);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object[]> RepAcreditacionesNoAprobadasPorEntidad(FormRepControlObs form) throws ApplicationException {
        return asReportes.consultarRepAcreditacionesNoAprobadasPorEntidad(form);
    }

}
