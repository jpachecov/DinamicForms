/**
 * @(#)MBReporteControlObs.java 8/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.mb;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.faces.validator.ValidatorException;
import mx.ine.observadoresINE.bsd.BSDReporteControlObsInterface;
import mx.ine.observadoresINE.dto.DTOList;
import mx.ine.observadoresINE.dto.form.FormRepControlObs;
import mx.ine.observadoresINE.helper.HLPReporteControlObs;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;
import mx.org.ine.servicios.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Clase que responde a las peticiones relacionadas con la generación del módulo
 * "Control de Observadores"
 *
 * @author Helaine Flores Cervantes
 * @since 8/08/2017
 * @copyright Direccion de sistemas - INE
 */
public class MBReporteControlObs extends MBReportesMenu implements Serializable {

    /**
     * Elemento para la serialización de los objetos generados por esta clase
     */
    private static final long serialVersionUID = 3814202258121167928L;
    /**
     * Elemento para generar log
     */
    private static final Log LOGGER = LogFactory.getLog(MBReporteControlObs.class);

    @Autowired
    @Qualifier("bsdReporteControlObs")
    private transient BSDReporteControlObsInterface bsdReporte;
    private Boolean muestraTabla;
    FormRepControlObs formFiltros;
    StringBuffer tituloReporte;
    int incioColumnaAsumar;
    Boolean reporteConTotales;
    private HLPReporteControlObs hlpControlObs;
    int opcionConsultarReporte;
    private Integer[] valoresSegundoNivel;
    private Boolean requeridoSegundoNivel;
    private Boolean mostarTotalPDF;
    StringBuffer tituloFiltros;

    @Override
    public void init() {
        super.init();
        formFiltros = new FormRepControlObs();
        formFiltros.setOpcionDeFechas(true);
        formFiltros.setUsuario(obtenUsuario());
        formFiltros.setNivelOficinas(nivelOficinas);
        muestraTabla = false;
        this.mbAdmin.getDto().setTituloModulo(Utilidades.mensajeProperties("etiqueta_reportes_controlDeObservadores"));
        obtenerPrimerNivelFiltros();
    }

    /**
     * Método que obtiene el primer nivel de filtros para ser mostrados en vista
     */
    public void obtenerPrimerNivelFiltros() {
        formFiltros.setPrimerNivelFiltros(new ArrayList<DTOList>());
        formFiltros.getPrimerNivelFiltros().add(new DTOList(1, Utilidades.mensajeProperties("etiqueta_reportes_solicitudesDeAcreditacion")));
        formFiltros.getPrimerNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_acreditacionesAprobadas")));
        formFiltros.getPrimerNivelFiltros().add(new DTOList(3, Utilidades.mensajeProperties("etiqueta_reportes_acreditacionesNoAprobadas")));
    }

    /**
     * Método que obtiene el segundo nivel de filtros, el cuál depende de lo
     * seleccionado en el primer filtro.
     */
    public void obtenerSegundoNivelFiltros() {
        requeridoSegundoNivel = true;
        formFiltros.setSegundoNivelFiltros(null);
        formFiltros.setTercerNivelFiltros(null);
        formFiltros.setValorSegundoFiltroCheck(null);
        formFiltros.setValorSegundoFiltroRadio(null);
        formFiltros.setOpcionDeFechas(false);
        formFiltros.setSegundoNivelFiltros(new ArrayList<DTOList>());
        formFiltros.setMostrarFiltroFechas(false);
        muestraTabla = false;
        switch (formFiltros.getValorPrimerFiltro()) {
            case 1:// Solicitudes de acreditación
                switch (formFiltros.getNivelOficinas()) {
                    case 1: // Opciones para Oficinas Centrales
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(0, Utilidades.mensajeProperties("etiqueta_reportes_filtro_nacional")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(1, Utilidades.mensajeProperties("etiqueta_reportes_filtro_entidad")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_filtro_agrupacion")));
                        break;
                    case 2: // Opciones para Junta Local
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(1, Utilidades.mensajeProperties("etiqueta_reportes_filtro_entidad")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(3, Utilidades.mensajeProperties("etiqueta_reportes_filtro_distrito")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_filtro_agrupacion")));
                        break;
                    case 3: // Opciones para Junta Distrital
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(1, Utilidades.mensajeProperties("etiqueta_reportes_filtro_distrito")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_filtro_agrupacion")));
                        break;
                    default:
                        break;
                }
                break;
            case 2:// Acreditaciones Aprobadas
                switch (formFiltros.getNivelOficinas()) {
                    case 2: // Opciones para Junta Local
                    case 3: // Opciones para Junta Distrital
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(4, Utilidades.mensajeProperties("etiqueta_reportes_filtro_concentrado")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(5, Utilidades.mensajeProperties("etiqueta_reportes_filtro_listadoObservadores")));
                        break;
                    default:
                        break;
                }
                break;
            case 3:// Acreditaciones No Aprobdas
                switch (formFiltros.getNivelOficinas()) {
                    case 1: // Opciones para Oficinas Centrales
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(4, Utilidades.mensajeProperties("etiqueta_reportes_filtro_concentrado")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_filtro_agrupacion")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(1, Utilidades.mensajeProperties("etiqueta_reportes_filtro_entidad")));
                        break;
                    case 2: // Opciones para Junta Local
                    case 3: // Opciones para Junta Distrital
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(4, Utilidades.mensajeProperties("etiqueta_reportes_filtro_concentrado")));
                        formFiltros.getSegundoNivelFiltros().add(new DTOList(5, Utilidades.mensajeProperties("etiqueta_reportes_filtro_listadoObservadores")));
                        break;
                    default:
                        break;
                }
                break;
            default:
                formFiltros.setSegundoNivelFiltros(null);
                break;
        }
    }

    /**
     * Metodo que obtiene los valores del segundo filtro... Aqui esta la magia
     */
    public void llenaDatosSegundoNivel() {
        this.valoresSegundoNivel = formFiltros.getValorSegundoFiltroCheck();
    }

    /**
     * Método que valida en vista las opciones seleccionadas del segundo filtro
     * para OC y JL
     *
     * @throws ValidatorException
     */
    public void validarSegundoNivelOCyJL() throws ValidatorException {
        Integer[] valores;
        valores = (Integer[]) this.valoresSegundoNivel;
        if (valores.length == 0) { // No selecciono nada
            requeridoSegundoNivel = true;
            // Se habilitan todas las opciones
            formFiltros.getSegundoNivelFiltros().get(0).setDisabled(false);
            formFiltros.getSegundoNivelFiltros().get(1).setDisabled(false);
            formFiltros.getSegundoNivelFiltros().get(2).setDisabled(false);
        } else {// Selecciono alguna opción
            requeridoSegundoNivel = false;
            List<Integer> seleccionado;
            seleccionado = Arrays.asList(valores);
            if (formFiltros.getNivelOficinas() == 1) { // Oficinas Centrales
                if (seleccionado.contains(0)) { // Selecciono Nacional
                    formFiltros.getSegundoNivelFiltros().get(1).setDisabled(true); // Bloquear Entidad
                    formFiltros.getSegundoNivelFiltros().get(2).setDisabled(true); // Bloquear Argupación
                } else if (seleccionado.contains(1) || seleccionado.contains(2)) { // Seleccionó Entidad o Agrupación
                    formFiltros.getSegundoNivelFiltros().get(0).setDisabled(true); // Bloquear Nacional
                }
            } else // Junta Local
             if (seleccionado.contains(1))// Selecciono Entidad
                {
                    formFiltros.getSegundoNivelFiltros().get(2).setDisabled(true); // Bloquear
                    // Agrupación
                    if (seleccionado.contains(3)) {
                        formFiltros.getSegundoNivelFiltros().get(2).setDisabled(false); // Desbloquear Agrupación
                    }
                }
        }
        this.muestraTabla = false;
    }

    /**
     * Método que obtiene el Tercer nivel de filtros que serán presentados en la
     * vista, dependiendo de lo seleccionado en el primer filtro
     */
    public void obtenerTercerNivelFiltros() {
        formFiltros.setTercerNivelFiltros(null);
        formFiltros.setValorTercerFiltro(null);
        formFiltros.setOpcionDeFechas(false);
        formFiltros.setMostrarFiltroFechas(false);
        formFiltros.setOpcionDeFecha(null);
        formFiltros.setFechaFin(null);
        formFiltros.setFechaIncio(null);
        this.muestraTabla = false;
        switch (formFiltros.getValorPrimerFiltro()) {
            case 2:// Acreditaciones Aprobadas
                List<DTOList> tercerNivel;
                try {// Las opciones son tomadas del cátalogo de C_JUSTIFICACIONES(son dinámicas)
                    tercerNivel = bsdReporte.obtenerJustificacionesAprobadas(formFiltros);
                    formFiltros.setTercerNivelFiltros(tercerNivel);
                } catch (ApplicationException e) {
                    enviaMensajeException(e);
                }
                formFiltros.setOpcionDeFechas(true);
                break;
            case 3:// Acreditaciones No Aprobadas
                formFiltros.setTercerNivelFiltros(new ArrayList<DTOList>());
                formFiltros.getTercerNivelFiltros().add(new DTOList(2, Utilidades.mensajeProperties("etiqueta_reportes_filtro_denegadas")));
                formFiltros.getTercerNivelFiltros().add(new DTOList(3, Utilidades.mensajeProperties("etiqueta_reportes_filtro_candeladas")));
                formFiltros.getTercerNivelFiltros().add(new DTOList(5, Utilidades.mensajeProperties("etiqueta_reportes_filtro_declinadas")));
                // Si seleccionó el listado de observadores del segundo filtro
                if (formFiltros.getValorSegundoFiltroRadio() == 5) {
                    formFiltros.setOpcionDeFechas(true); // Se debe mostrar el filtro de fechas
                }
                break;
            default:
                formFiltros.setTercerNivelFiltros(null);
                break;
        }
    }

    /**
     * Método que muestra en vista las opciones de Fecha de incio y Fecha Fin en
     * vista
     */
    public void mostrarYlimpiarCamposFecha() {
        if (formFiltros.getOpcionDeFecha().length == 1) {
            // Si selecciono la opción de filtrado por fecha
            formFiltros.setMostrarFiltroFechas(true); // Se muestran las opciones de Fecha inicio y Fecha Fin
        } else {
            formFiltros.setMostrarFiltroFechas(false);// Se ocultan las opciones de FEcha inicio y Fecha Fin
        }
        this.muestraTabla = false; // Se oculta la tabla del reporte
        formFiltros.setFechaFin(null);// Se limpia la Fecha fin
        formFiltros.setFechaIncio(null);// Se limpia la Fecha inicio

    }

    /**
     * Se hace la consulta de Reporte
     *
     */
    public void consultaReporte() {
        List<Object[]> listaDatos = null;

        // Se inicializa el encabezado
        super.inicializaParametrosEncabezado();
        // Se obtiene el reporte seleccionado
        obtenerSubReporte();
        // Se crean los encabezados
        try {
            switch (opcionConsultarReporte) {
                case 1:
                    listaDatos = bsdReporte.obtenerDatosReporte(formFiltros);
                    break;
                case 2:
                    listaDatos = bsdReporte.consultarReporteControlObsDinamico(formFiltros);
                    break;
                case 3:
                    listaDatos = bsdReporte.RepAcreditacionesNoAprobadasPorEntidad(formFiltros);
                    break;
                default:
                    break;
            }
            if (reporteConTotales) {// Si el reporte lleva totales
                obtenerTotalesReportes(listaDatos);// Se calculan los totales a partir de los datos obtenido
            }
        } catch (ApplicationException e) {
            enviaMensajeException(e); // Se enviá el mensaje de error, en caso
            // de algún problema
            muestraTabla = false;
        }
        hlpControlObs = new HLPReporteControlObs(formFiltros);
        dtoParametros.setColumnas(hlpControlObs.getColumnas()); // Número de columnas
        dtoParametros.setEncabezado(hlpControlObs.getListaEncabezados());// Encabezado
        dtoParametros.setListaDatos(listaDatos);// Datos
        dtoParametros.setTituloReporte(tituloReporte.toString());// título del reporte
        // nombreReporte = "ReporteControlObs"; // Nombre del archivo para la exportación
        obtenerMedidasEncabezado(hlpControlObs.getColumnas());
        muestraTabla = true;
    }

    /**
     * Método que obtiene las medidas para los encabezados de Entidad
     * Federativa, Distrito y FechaHora
     *
     * @param columnas
     */
    public void obtenerMedidasEncabezado(Integer columnas) {
        Integer valor;
        if (formFiltros.getNivelOficinas() == 1
                || formFiltros.getNivelOficinas() == 2) {
            valor = columnas / 2;
            dtoParametros.setAnchoEntidad(valor);
            dtoParametros.setAnchoFechaHora(columnas - valor);
        } else {
            valor = columnas / 3;
            dtoParametros.setAnchoEntidad(valor);
            dtoParametros.setAnchoDistrito(valor);
            dtoParametros.setAnchoFechaHora(columnas - (valor * 2));
        }
    }

    /**
     * Método que suma las columnas de los datos del reporte y obtiene los
     * totales
     *
     * @param datosReporte
     */
    public void obtenerTotalesReportes(List<Object[]> datosReporte) {
        List<Integer> listaTotales = null;

        if (!datosReporte.isEmpty()) {
            int numeroRegistro = 0;
            listaTotales = new ArrayList<>();
            Integer valor;
            for (Object[] registro : datosReporte) {
                // Se relizan las sumas de la columna
                for (int columna = incioColumnaAsumar; columna < registro.length; columna++) {
                    // Si se trata del primer registro, se crea el elemnto
                    if (numeroRegistro == 0) {
                        listaTotales.add(0);
                    }
                    valor = Integer.parseInt(registro[columna] != null ? registro[columna]
                            .toString().replace(",", "").trim()
                            : "0");
                    listaTotales.set(columna - incioColumnaAsumar,
                            listaTotales.get(columna - incioColumnaAsumar)
                            + valor);
                }
                numeroRegistro++;
            }
        }
        formFiltros.setTotales(listaTotales);
    }

    /**
     * Método que obtiene las abreviatura de los estados para ser mostrados en
     * el encabezado de algunos reportes
     *
     * @return
     */
    public List<DTOList> consultarAbreviaturaEstados() {
        List<DTOList> estados = null;
        try {
            estados = bsdReporte.obtenerAbreviaturaEstados();
        } catch (ApplicationException e) {
            enviaMensajeException(e);
        }
        return estados;
    }

    /**
     * Método que obtiene el encabezado dinámico del reporte Acreditaciones no
     * aprobadas, por Enditad
     *
     */
    public void consultarEncabezadoDecDenDecl() {
        // Se obtiene la lista de idJustificacion y Resultado
        // (2.Denegadas,3.Canceladas,5.Declinadas)
        List<Object[]> encabezado = null;
        try {
            encabezado = bsdReporte.consultarEncabezadoCanDenDecl(formFiltros);
        } catch (ApplicationException e) {
            enviaMensajeException(e);
        }
        // Se cuentan cuántos idJustificaciones existen por resultado, para
        // crear el encabezado
        if (encabezado != null) {
            int resultadoAnterior;
            int resuldoActual;
            List<int[]> resultados = new ArrayList();
            List<Integer> justificaciones = new ArrayList();
            List<String> descripcionJust = new ArrayList();//Pie de página
            int posicion = 0;
            int[] res = new int[2];
            resultadoAnterior = Integer.parseInt(encabezado.get(0)[1]
                    .toString()); // Se obtiene el primer resultado
            res[0] = resultadoAnterior;
            res[1] = 0;
            resultados.add(res); // Se agrega a la lista el primer resultado
            for (Object[] e : encabezado) {
                justificaciones.add(Integer.parseInt(e[0].toString()));
                descripcionJust.add(e[2] != null ? e[2].toString() : " ");
                resuldoActual = Integer.parseInt(e[1].toString());
                if (resultadoAnterior == resuldoActual) { // Se compara el resultado
                    resultados.get(posicion)[1] = (resultados.get(posicion)[1] + 1); // Si se trate del mismo resultado, entonce se suma el contador
                } else {// Es un nuevo resultado
                    posicion++;
                    res = new int[2];
                    res[0] = resuldoActual;
                    res[1] = 1;
                    resultados.add(res); // Se agrega a la lista
                    resultadoAnterior = resuldoActual;
                }
            }
            formFiltros.setEncabezadoCanDenDecl(resultados);
            formFiltros.setJustificaciones(justificaciones);
            formFiltros.setDescripcionJust(descripcionJust);
        }

    }

    /**
     * Método que obtiene los filtros seleccionados
     */
    public void obtenerFiltroReporte() {
        if (formFiltros.getValorTercerFiltro() != null) {
            String campo;
            formFiltros.setFiltroReporte(new StringBuffer());
            tituloFiltros = new StringBuffer();
            campo = formFiltros.getValorPrimerFiltro() == 2 ? "ID_JUSTIFICACION"
                    : "RESULTADO";
            formFiltros.getFiltroReporte().append("AND (");
            int indice = 0;
            for (Integer filtro : formFiltros.getValorTercerFiltro()) {
                if (indice == 0) {
                    formFiltros.getFiltroReporte().append(campo).append(" = ").append(filtro);
                } else {
                    formFiltros.getFiltroReporte().append(" OR ").append(campo).append(" = ").append(filtro);
                }
                //Se agregan los nombres de los filtros en el título del reporte para las Acreditaciones no Aprobadas
                if (formFiltros.getValorPrimerFiltro() == 3) {
                    switch (filtro) {
                        case 2:
                            tituloFiltros.append(", Denegadas");
                            break;
                        case 3:
                            tituloFiltros.append(", Canceladas");
                            break;
                        case 5:
                            tituloFiltros.append(", Declinadas");
                            break;
                        default:
                            break;
                    }
                } else if (formFiltros.getValorPrimerFiltro() == 2) {
                    //Se agregan los nombres de los filtros en el título del reporte para las Acreditaciones Aprobadas
                    for (DTOList idFiltro : formFiltros.getTercerNivelFiltros()) {
                        if (Objects.equals(filtro, idFiltro.getKey())) {
                            tituloFiltros.append(", ").append(idFiltro.getValue());
                            break;
                        }
                    }
                }

                indice++;
            }
            // Obtener filtros por fecha
            formFiltros.getFiltroReporte().append(")");
            if (formFiltros.getFechaIncio() != null
                    && formFiltros.getFechaFin() != null) {
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                formFiltros.getFiltroReporte()
                        .append(" AND FECHA_SESION BETWEEN TO_DATE('")
                        .append(dt.format(formFiltros.getFechaIncio()))
                        .append("')  AND  TO_DATE('")
                        .append(dt.format(formFiltros.getFechaFin()))
                        .append("')");
                tituloFiltros.append(", por fecha de sesión del ")
                        .append(dt.format(formFiltros.getFechaIncio()))
                        .append(" al ")
                        .append(dt.format(formFiltros.getFechaFin()));

            }

        }
    }
    /**
     * Método que agrega al título del reporte las opciones de filtro seleccionadas por el usuario.
     * Se agrega una "y" al último filtro seleccionado
     */
    public void agregarFiltrosTitulo() {
        int ultimaComa;
       if(tituloFiltros != null && tituloFiltros.length()>0 ){
           tituloReporte.append(tituloFiltros.toString().substring(1));//Se elimina la primera coma
           ultimaComa = tituloReporte.toString().lastIndexOf(","); //Se busca la última coma
           if(ultimaComa>0){ // si tiene comas
               //Se reemplaza por una "y"
           tituloReporte = tituloReporte.replace(ultimaComa, ultimaComa+2, " y ");
           }
       }
    }

    /**
     * Método encargado de obtener los datos del reporte a partir de los filtros
     * seleccionados por el usuario
     */
    public void obtenerSubReporte() {
        incioColumnaAsumar = 1;
        reporteConTotales = true;
        tituloReporte = new StringBuffer();
        opcionConsultarReporte = 1;
        mostarTotalPDF = true;
        switch (formFiltros.getValorPrimerFiltro()) {
            case 1:// Solicitudes de Acreditación
                tituloReporte.append("Solicitudes de acreditación");
                if (formFiltros.getNivelOficinas() == 1 || formFiltros.getNivelOficinas() == 2) {
                    // OC y JL se obtiene el valor del CheckBox
                    switch (formFiltros.getValorSegundoFiltroCheck().length) {
                        case 1:// Seleccionó sólo una opción
                            switch (formFiltros.getValorSegundoFiltroCheck()[0]) {
                                case 0: // Nacional
                                    LOGGER.info("Reporte: Solicitudes de acreditación Nacional");
                                    tituloReporte.append(" Nacional");
                                    formFiltros.setFormatoDeTabla(1);
                                    formFiltros.setPrimerColumna(" ");
                                    formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesNacional_OC");
                                    nombreReporte = "RepSolAcreditacionNal";
                                    mostarTotalPDF = false; //Mostrar Total en PDF
                                    break;
                                case 1:// Entidad
                                    LOGGER.info("Reporte: Solicitudes de acreditación Entidad");
                                    tituloReporte.append(" Por Entidad");
                                    formFiltros.setFormatoDeTabla(formFiltros.getNivelOficinas() == 1 ? 1 : 4);
                                    formFiltros.setPrimerColumna(" ");
                                    formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 1 ? "query_reporte_controlObs_SolicitudesEntidad_OC"
                                            : "query_reporte_controlObs_SolicitudesEntidad_JL");
                                    nombreReporte = "RepSolAcreEntidad";
                                    mostarTotalPDF = formFiltros.getNivelOficinas() == 1; //Mostrar Total en PDF para  JL
                                    break;
                                case 2:// Agrupación
                                    LOGGER.info("Reporte: Solicitudes de acreditación Agrupación");
                                    tituloReporte.append(" Por Agrupación");
                                    formFiltros.setFormatoDeTabla(formFiltros.getNivelOficinas() == 1 ? 2 : 6);
                                    formFiltros.setPrimerColumna("Agrupaciones");
                                    formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 1 ? "query_reporte_controlObs_SolicitudesAgrupacion_OC"
                                            : "query_reporte_controlObs_SolicitudesAgrupacion_JL");
                                    nombreReporte = "RepSolAcreAgrupacion";
                                    break;
                                case 3:// Distrito
                                    LOGGER.info("Reporte: Solicitudes de acreditación Distrito JL");
                                    tituloReporte.append(" Por Distrito");
                                    this.incioColumnaAsumar = 2;
                                    formFiltros.setFormatoDeTabla(5);
                                    formFiltros.setPrimerColumna("Distrito");
                                    formFiltros.setSegundaColumna("Rubro");
                                    formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesDistrito_JL");
                                    nombreReporte = "RepSolAcreDistrito";
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2:// Seleccionó dos opciones
                            // Ofinicas Centrales. Para OC sólo puede existe la
                            // combinación Entidad y Agrupación
                            if (formFiltros.getNivelOficinas() == 1) {
                                LOGGER.info("Reporte: Solicitudes de acreditación Por Entidad y Por Agrupación");
                                List<DTOList> abreviaturaEstados;
                                abreviaturaEstados = consultarAbreviaturaEstados();
                                formFiltros.setAbreviaturaEstados(abreviaturaEstados);
                                opcionConsultarReporte = 2; // Reporte dinámico
                                formFiltros.setFormatoDeTabla(7);
                                tituloReporte.append(" Por Entidad y Por Agrupación");
                                formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesEntAgru_OC");
                                nombreReporte = "RepSolAcreEntAgru";
                            } else {// Junta Local. Para JL pueden existir las combinaciones (Entidad, Distrito) y (Distrito,Agrupacion)
                                List<Integer> opcionesSeleccionadas;
                                opcionesSeleccionadas = Arrays.asList(formFiltros
                                        .getValorSegundoFiltroCheck());
                                if (opcionesSeleccionadas.contains(1)) { // Selecciono Entidad y Distrito
                                    LOGGER.info("Reporte: Solicitudes de acreditación Por Entidad y Por Distrito");
                                    tituloReporte.append(" Por Entidad y Por Distrito");
                                    formFiltros.setFormatoDeTabla(1);
                                    formFiltros.setPrimerColumna(" ");
                                    formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesEntDist_JL");
                                    nombreReporte = "RepSolAcreEntDist";
                                    mostarTotalPDF = false; //Mostrar Total en PDF
                                } else {// Selecciono Distrito y Agrupación
                                    LOGGER.info("Reporte: Solicitudes de acreditación Por Distrito y Por Agrupación");
                                    tituloReporte.append(" Por Agrupación");
                                    formFiltros.setFormatoDeTabla(6);
                                    formFiltros.setPrimerColumna("Agrupaciones");
                                    formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesDistAgru_JL");
                                    nombreReporte = "RepSolAcreDistAgru";
                                }
                            }
                            break;
                        case 3:// Selecciono tres opciones (Junta Distrital). Sólo para
                            // JL puede existrir la combinación de tres
                            LOGGER.info("Reporte: Solicitudes de acreditación Por Entidad y Por Distrito y Por Agrupación");
                            tituloReporte.append(" Por Agrupación");
                            formFiltros.setFormatoDeTabla(2);
                            formFiltros.setPrimerColumna("Agrupaciones");
                            formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesEntDistAgru_JL");
                            nombreReporte = "RepSolAcreEntDistAgru";
                            break;
                    }
                } else// Solicitudes de Acrecitación JD. Se obtiene el valor del Radio
                {
                    switch (formFiltros.getValorSegundoFiltroRadio()) {
                        case 1: // Por Distrito
                            LOGGER.info("Reporte: Solicitudes de acreditación Por Distrito JD");
                            tituloReporte.append(" Por Distrito");
                            formFiltros.setFormatoDeTabla(4);
                            formFiltros.setPrimerColumna(" ");
                            formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesDistrito_JD");
                            nombreReporte = "RepSolAcreDist";
                            mostarTotalPDF = false;//Mostrar Total en PDF
                            break;
                        case 2:// Por Agrupación
                            LOGGER.info("Reporte: Solicitudes de acreditación Por Agrupación JD");
                            tituloReporte.append(" Por Agrupación");
                            formFiltros.setFormatoDeTabla(6);
                            formFiltros.setPrimerColumna(" ");
                            formFiltros.setNombreQuery("query_reporte_controlObs_SolicitudesAgrupacion_JD");
                            nombreReporte = "RepSolAcreAgru";
                            mostarTotalPDF = false;//Mostrar Total en PDF
                            break;
                    }
                }
                break;
            case 2:// Acreditaciones Aprobadas
                if (formFiltros.getNivelOficinas() == 1) {
                    LOGGER.info("Reporte: Acreditaciones Aprobadas OC");
                    opcionConsultarReporte = 2;
                    List<DTOList> abreviaturaEstados;
                    abreviaturaEstados = consultarAbreviaturaEstados();// Se obtienen las abreviaturas de los estados
                    formFiltros.setAbreviaturaEstados(abreviaturaEstados);
                    formFiltros.setFormatoDeTabla(7);
                    tituloReporte.append("Acreditaciones Aprobadas");
                    formFiltros.setNombreQuery("query_reporte_controlObs_AcreditacionesAp_OC");
                    nombreReporte = "RepAcreAprobadas";
                } else {
                    switch (formFiltros.getValorSegundoFiltroRadio()) {
                        case 4:
                            LOGGER.info("Reporte: Concentrado de Acreditaciones Aprobadas");
                            formFiltros.setFormatoDeTabla(formFiltros.getNivelOficinas() == 2 ? 8 : 9);
                            formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 2
                                    ? "query_reporte_controlObs_AcreditacionesApConcentrado_JL"
                                    : "query_reporte_controlObs_AcreditacionesApConcentrado_JD");
                            obtenerFiltroReporte();
                            tituloReporte.append("Concentrado de Acreditaciones Aprobadas ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepConAcreAprobadas";
                            break;
                        case 5:
                            LOGGER.info("Reporte: Listado de Acreditaciones Aprobadas");
                            reporteConTotales = false; // Los listados no llevan totales
                            formFiltros.setFormatoDeTabla(formFiltros.getNivelOficinas() == 2 ? 10 : 11);
                            formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 2
                                    ? "query_reporte_controlObs_AcreditacionesApListado_JL"
                                    : "query_reporte_controlObs_AcreditacionesApListado_JD");
                            obtenerFiltroReporte();
                            tituloReporte.append("Concentrado de Acreditaciones Aprobadas ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepLisAcreAprobadas";
                            break;
                    }
                }
                break;
            case 3:// Acreditaciones No Aprobadas
                reporteConTotales = false;
                if (formFiltros.getNivelOficinas() == 1) {// Oficinas Centrales
                    switch (formFiltros.getValorSegundoFiltroRadio()) {
                        case 4: // Concentrado
                            LOGGER.info("Reporte: Acreditaciones No Aprobadas OC");
                            formFiltros.setFormatoDeTabla(12);
                            formFiltros.setNombreQuery("query_reporte_controlObs_AcreditacionesNoApConcentrado_OC");
                            this.obtenerFiltroReporte();
                            tituloReporte.append("Concentrado de Acreditaciones no aprobadas ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepConAcreNoAprobadas";
                            mostarTotalPDF = false;
                            break;
                        case 2:// Por Agrupación
                            LOGGER.info("Reporte: Acreditaciones No Aprobadas Por Agrupación OC");
                            opcionConsultarReporte = 2;
                            List<DTOList> abreviaturaEstados;
                            abreviaturaEstados = consultarAbreviaturaEstados(); // Se obtienen las abreviaturas
                            formFiltros.setAbreviaturaEstados(abreviaturaEstados);
                            formFiltros.setFormatoDeTabla(7);
                            formFiltros.setNombreQuery("query_reporte_controlObs_AcreditacionesNoApAgrupacion_OC");
                            reporteConTotales = true;
                            this.obtenerFiltroReporte();
                            tituloReporte.append("Acreditaciones No Aprobadas por Agrupación  ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepAcreNoAprobAgru";
                            break;
                        case 1:// Por Entidad
                            LOGGER.info("Reporte: Acreditaciones No Aprobadas Por Entidad OC");
                            opcionConsultarReporte = 3; // Reporte dinámico por Endidad
                            this.obtenerFiltroReporte();
                            consultarEncabezadoDecDenDecl();
                            formFiltros.setFormatoDeTabla(15);
                            tituloReporte.append("Acreditaciones No Aprobadas por Entidad ");
                            agregarFiltrosTitulo();
                            formFiltros.setNombreQuery("query_reporte_controlObs_AcreditacionesNoApEntidad_OC");
                            reporteConTotales = true;
                            nombreReporte = "RepAcreNoAprobEnt";
                            break;
                        default:
                            break;
                    }
                } else {// JL y JD
                    switch (formFiltros.getValorSegundoFiltroRadio()) {
                        case 4: // Concentrado
                            LOGGER.info("Reporte: Acreditaciones No Aprobadas JD");
                            formFiltros.setFormatoDeTabla(12);
                            formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 2 ? "query_reporte_controlObs_AcreditacionesNoApConcentrado_JL"
                                    : "query_reporte_controlObs_AcreditacionesNoApConcentrado_JD");
                            this.obtenerFiltroReporte();
                            tituloReporte.append("Concentrado de Acreditaciones No Aprobadas ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepConAcreNoAprob";
                            mostarTotalPDF = false;
                            break;
                        case 5: // Listado
                            LOGGER.info("Reporte: Listado Acreditaciones No Aprobadas");
                            formFiltros.setFormatoDeTabla(formFiltros.getNivelOficinas() == 2 ? 13 : 14);
                            formFiltros.setNombreQuery(formFiltros.getNivelOficinas() == 2 ? "query_reporte_controlObs_AcreditacionesNoApListado_JL"
                                    : "query_reporte_controlObs_AcreditacionesNoApListado_JD");
                            this.obtenerFiltroReporte();
                            tituloReporte.append("Listado de Acreditaciones No Aprobadas ");
                            agregarFiltrosTitulo();
                            nombreReporte = "RepLisAcreNoAprob";
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }

    // Método que exporta a formato Excel
    @Override
    public void postProcessXLS(Object document) {
        super.postProcessXLS(document);
    }

    // Método que exporta a formato PDF
    @Override
    public void exportPDF() throws IOException {
        obtenerEncabezadoPDF();
        super.exportPDF();
    }

    public void obtenerEncabezadoPDF() {
        Map<String, Serializable> parametrosPDF;
        parametrosPDF = new LinkedHashMap<>();
        parametrosPDF.put(Constantes.PARAMETRO_INTEGER_COLUMNAS, hlpControlObs.getColumnas());
        parametrosPDF.put(Constantes.PARAMETRO_STRING_TITULO, tituloReporte);
        parametrosPDF.put(Constantes.PARAMETRO_STRING_FILENAME, nombreReporte);
        if (mostarTotalPDF) {//Mostrar total global en encabezado PDF
            parametrosPDF.put(Constantes.PARAMETRO_STRING_TOTALES, dtoParametros.getListaDatos().size());
        }
        if (formFiltros.getNivelOficinas() == 1 || formFiltros.getNivelOficinas() == 2) {
            parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, getUsuario().getEstadoSeleccionado());
        } else {
            parametrosPDF.put(Constantes.PARAMETRO_OBJECT_ESTADO, getUsuario().getEstadoSeleccionado());
            parametrosPDF.put(Constantes.PARAMETRO_OBJECT_DISTRITO, getUsuario().getDistritoSeleccionado());
        }
        setParametros(parametrosPDF);
    }

    /**
     * Método encargado de enviar mensaje a la vista dependiendo el codigo de
     * exception
     *
     * @param e
     *
     * @author José Antonio López Torres
     * @since 29/11/2016
     */
    private void enviaMensajeException(ApplicationException e) {
        // Error
        if (e.getCodigoError() == Constantes.CODIGO_EXEPTION) {
            agregaMensaje(TipoMensaje.ERROR_MENSAJE, e.getMessage());
            // Advertencia
        } else {
            agregaMensaje(TipoMensaje.ADVERTENCIA_MENSAJE, e.getMessage());
        }
    }

    // ***************************** GETTERS AND SETTERS *******************/
    public void obtenerCuartoNivelFiltros() {
    }

    public Boolean getMuestraTabla() {
        return muestraTabla;
    }

    public void setMuestraTabla(Boolean muestraTabla) {
        this.muestraTabla = muestraTabla;
    }

    public FormRepControlObs getFormFiltros() {
        return formFiltros;
    }

    public void setFormFiltros(FormRepControlObs formFiltros) {
        this.formFiltros = formFiltros;
    }

    public Integer[] getValoresSegundoNivel() {
        return valoresSegundoNivel;
    }

    public void setValoresSegundoNivel(Integer[] valoresSegundoNivel) {
        this.valoresSegundoNivel = valoresSegundoNivel;
    }

    public Boolean getRequeridoSegundoNivel() {
        return requeridoSegundoNivel;
    }

    public void setRequeridoSegundoNivel(Boolean requeridoSegundoNivel) {
        this.requeridoSegundoNivel = requeridoSegundoNivel;
    }

}
