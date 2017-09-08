/**
 * @(#)BSDExportacionTablasImp.java 16/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;
import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDExportacionTablasInterface;
import mx.org.ine.servicios.exception.ApplicationException;
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
@Component("bsdExportacionTablas")
@Scope("prototype")
public class BSDExportacionTablasImp implements BSDExportacionTablasInterface {

    @Autowired
    @Qualifier("asReportes")
    private ASReportesInterface asReportes;

    @Override
    public List<String> obtenerTabla(int numeroTabla) throws ApplicationException {
        return asReportes.obtenerTabla(numeroTabla);
    }
}
