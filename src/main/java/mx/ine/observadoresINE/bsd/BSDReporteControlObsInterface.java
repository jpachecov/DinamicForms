/**
 * @(#)BSDReporteControlObsInterface.java 16/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd;

import java.util.List;
import mx.ine.observadoresINE.dto.DTOList;
import mx.ine.observadoresINE.dto.form.FormRepControlObs;
import mx.org.ine.servicios.exception.ApplicationException;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 16/08/2017
 * @copyright Direccion de sistemas - INE
 */
public interface BSDReporteControlObsInterface {

    /**
     * Método que obtiene los datos del reporte
     *
     * @param form
     * @return
     * @throws ApplicationException
     */
    List<Object[]> obtenerDatosReporte(FormRepControlObs form) throws ApplicationException;
    /**
     * Método que obtiene los identificadores y abreviatura de los estados
     *
     * @return
     * @throws ApplicationException
     */
    List<DTOList> obtenerAbreviaturaEstados() throws ApplicationException;
    /**
     * Método que obtiene el reporte control de Observadores con encabezado
     * dinámico
     *
     * @param form
     * @return
     * @throws mx.org.ine.servicios.exception.ApplicationException
     */
    List<Object[]> consultarReporteControlObsDinamico(FormRepControlObs form) throws ApplicationException;
   /**
     * Método que onsulta el catálogo C_Justificaciones y obtiene los aprobados
     * 
     * @author Helaine Flores C.
     * @param form
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 24/08/2017
     * @return 
     */
    List<DTOList> obtenerJustificacionesAprobadas(FormRepControlObs form) throws ApplicationException;

    /**
     * Método que consulta el encabezado dinámico para el reporte Control de
     * Observadores. Acreditaciones No aprobadas. Por Entidad OC
     *
     * @param form
     * @return
     * @throws mx.org.ine.servicios.exception.ApplicationException
     */
    List<Object[]> consultarEncabezadoCanDenDecl(FormRepControlObs form) throws ApplicationException;
    /**
     * Método que consulta el reporte "Acreditaciones No aprobadas por Entidad"
     * @param form
     * @return
     * @throws ApplicationException 
     */
    List<Object[]> RepAcreditacionesNoAprobadasPorEntidad(FormRepControlObs form) throws ApplicationException;

}
