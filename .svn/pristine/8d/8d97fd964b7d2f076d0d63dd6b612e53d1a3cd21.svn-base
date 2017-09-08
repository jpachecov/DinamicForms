/**
 * @(#)DAOAdministradorSistemaInterface.java 03/05/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao;

import java.util.List;
import mx.ine.observadoresINE.dto.db.DTOAccesosSistema;

/**
 *
 * @author Pablo Zuñiga Mata
 * @since 03/05/2017
 * @copyright Direccion de sistemas - INE
 */
public interface DAOAdministradorSistemaInterface {

    /**
     * Método para obtener la lista de grupos por sistema
     *
     * @author Pablo Zuñiga Mata
     * @since 15/05/2017
     * @param idSistema
     * @return lista de grupos
     */
    public List<String> obtieneListaPermisosSistema(Integer idSistema);

    /**
     * Método encargado de guardar un registro en la tabla ACCESOS_SISTEMA cada
     * que un usario inicia sesión
     *
     * @param usuario: usuario que acceso con la fecha en la que acceso
     *
     * @author Emmanuel García Ysamit
     * @since 03/05/2017
     */
    public void guardarRegistroAcceso(DTOAccesosSistema usuario);
}
