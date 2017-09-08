/**
 * @(#)BSDExportacionTablas.java 16/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.bsd;

import java.util.List;
import mx.org.ine.servicios.exception.ApplicationException;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 16/08/2017
 * @copyright Direccion de sistemas - INE
 */
public interface BSDExportacionTablasInterface {
    /**
     * Método encargado de obtener los valores de una tabla de la BD
     *
     * @param numeroTabla
     * @return registros de la tabla
     *
     * @author Helaine Flores Cervantes
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 05/07/2017
     */
    List<String> obtenerTabla(int numeroTabla) throws ApplicationException;
;

}
