/**
 * @(#)MBExportacionTablas.java 14/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.mb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import mx.ine.observadoresINE.bsd.BSDExportacionTablasInterface;
import mx.org.ine.servicios.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 14/08/2017
 * @copyright Direccion de sistemas - INE
 */
public class MBExportacionTablas extends MBReportesMenu implements Serializable {

    private static final Log LOGGER = LogFactory.getLog(MBExportacionTablas.class);
    private static final long serialVersionUID = -5705320407309582823L;

    @Autowired
    @Qualifier("bsdExportacionTablas")
    protected transient BSDExportacionTablasInterface bsdExportacionTablas;
    /**
     * Método que permite descargar las tablas a un archivo plano
     *
     * @param numeroTabla
     * @return
     */
    public DefaultStreamedContent obtenerTabla(int numeroTabla) {
        LOGGER.info("Número de Tabla " + numeroTabla);
        DefaultStreamedContent archivo = null;
        List<String> registros = null;
        StringBuilder encabezado;
        String nombreArchivo = null;

        encabezado = new StringBuilder();
        switch (numeroTabla) {

            case 1: //Tabla Agrupaciones
                encabezado.append("NOMBRE_AGRUPACION|ABREVIATURA|NOMBRE_PRESIDENTE|DOMICILIO|TELEFONO_01|TELEFONO_02\r\n");
                nombreArchivo = "Agrupaciones.txt";
                break;
            case 2: //Tabla Accion de Promoción
                encabezado.append("NOMBRE_ESTADO|CABECERA_DISTRITAL|NOMBRE|FECHA_ACCION|ACCION\r\n");
                nombreArchivo = "AccionesPromoción.txt";
                break;
            case 3: //Cursos de capacitación
                encabezado.append("NOMBRE_ESTADO|CABECERA_DISTRITAL|ORIGEN_CURSO|NOMBRE_RESPONSABLE|")
                        .append("CARGO|FECHA|HORA_INICIO|HORA_FIN|DOMICILIO|NOMBRE_AGRUPACION|MISMO_DOMICILIO|OBSERVACIONES\r\n");
                nombreArchivo = "CursosCapacitación.txt";
                break;
            case 4: //Observadoras y Observadores
                encabezado.append("NOMBRE_ESTADO|CABECERA_DISTRITAL|NOMBRE|FECHA_ACCION|ACCION\r\n")
                        .append("TELEFONO|TELEFONO_CELULAR|CORREO_ELECTRONICO|FECHA_NACIMIENTO|EDAD|GENERO|")
                        .append("FECHA_SESION|ACREDITACION_CURSO|JUSTIFICACION|SECCION|FOLIO|DOMICILIO|ESCOLARIDAD|")
                        .append("CURSO|FECHA_ENTREGA_GAFETE\r\n");
                nombreArchivo = "Observadores.txt";
                break;
            default:
                break;
        }
        //Se obtienen los registros de la tabla 
        try {
            registros = bsdExportacionTablas.obtenerTabla(numeroTabla);
        } catch (ApplicationException e) {
            LOGGER.error("[Error] MBReportes.obtenerTabla()", e);
        }

        try {
            archivo = crearArchivoTXT(encabezado.toString(), registros, nombreArchivo);
        } catch (IOException ex) {
            LOGGER.error("[Error] MBReportes.obtenerTabla()", ex);
        }

        return archivo;
    }

    /**
     * Método que crea un archivo .txt a partir de los parámetros dados
     *
     * @param encabezado. Encabezado del archivo
     * @param registros
     * @param nombreArchivo. Nombre del archivo
     * @return
     * @throws IOException
     */
    public DefaultStreamedContent crearArchivoTXT(String encabezado, List<String> registros, String nombreArchivo) throws IOException {

        StringBuilder contenidoArchivo = new StringBuilder();
        File archivoTmp;
        FileWriter archivo;
        BufferedWriter buffer;
        //Encabezado del archivo
        contenidoArchivo.append(encabezado);
        //Renglones del archivo
        for (String renglon : registros) {
            contenidoArchivo.append(renglon).append("\r\n");
        }

        archivoTmp = new File(".txt");
        archivo = new FileWriter(archivoTmp);
        buffer = new BufferedWriter(archivo);
        buffer.write(contenidoArchivo.toString());
        buffer.close();

        return new DefaultStreamedContent(new FileInputStream(archivoTmp), "Content-Type: text/plain", nombreArchivo);

    }

    @Override
    public boolean esNivelOC(){
        return super.esNivelOC();
    }

    //***************************** GETTERS AND SETTERS *******************/

    
}
