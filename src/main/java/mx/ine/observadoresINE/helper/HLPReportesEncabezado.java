/**
 * @(#)HLPReportesEncabezado.java 12/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>HLPReportesEncabezado.java</code>
 *
 * @author Pablo Zuñiga Mata
 * @since 07/02/2017
 * @copyright Direccion de sistemas - INE
 */
public class HLPReportesEncabezado implements Serializable {

    private static final long serialVersionUID = 3639903430972079733L;

    private List<Integer> index;
    private List<Integer> ancho;
    private List<Integer> alto;
    private List<String> descEncabezado;
    private List<Integer> tipoFila;

    public HLPReportesEncabezado() {
        this.ancho = new ArrayList<>();
        this.alto = new ArrayList<>();
        this.descEncabezado = new ArrayList<>();
        this.index = new ArrayList<>();
        this.tipoFila = new ArrayList<>();
    }

    /**
     * @param index
     * @param ancho
     * @param alto
     * @param descripcion
     * @param tipoFila
     */
    public void ingresarEncabezado(Integer index, Integer ancho, Integer alto, String descripcion, Integer tipoFila) {
        this.index.add(index);
        this.ancho.add(ancho);
        this.alto.add(alto);
        this.descEncabezado.add(descripcion);
        this.tipoFila.add(tipoFila);
    }

    /**
     * Método que obtiene el valor del atributo ancho
     *
     * @return ancho: valor del atributo ancho
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public List<Integer> getAncho() {
        return ancho;
    }

    /**
     * Método que ingresa el valor del atributo ancho
     *
     * @param ancho: valor del atributo ancho
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public void setAncho(List<Integer> ancho) {
        this.ancho = ancho;
    }

    /**
     * Método que obtiene el valor del atributo alto
     *
     * @return alto: valor del atributo alto
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public List<Integer> getAlto() {
        return alto;
    }

    /**
     * Método que ingresa el valor del atributo alto
     *
     * @param alto: valor del atributo alto
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public void setAlto(List<Integer> alto) {
        this.alto = alto;
    }

    /**
     * Método que obtiene el valor del atributo descEncabezado
     *
     * @return descEncabezado: valor del atributo descEncabezado
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public List<String> getDescEncabezado() {
        return descEncabezado;
    }

    /**
     * Método que ingresa el valor del atributo descEncabezado
     *
     * @param descEncabezado: valor del atributo descEncabezado
     * @author Pablo Zuñiga Mata.
     * @since 07/02/2017
     */
    public void setDescEncabezado(List<String> descEncabezado) {
        this.descEncabezado = descEncabezado;
    }

    /**
     * Método que obtiene el valor del atributo index
     *
     * @return index: valor del atributo index
     * @author Pablo Zuñiga Mata.
     * @since 08/02/2017
     */
    public List<Integer> getIndex() {
        return index;
    }

    /**
     * Método que ingresa el valor del atributo index
     *
     * @param index: valor del atributo index
     * @author Pablo Zuñiga Mata.
     * @since 08/02/2017
     */
    public void setIndex(List<Integer> index) {
        this.index = index;
    }

    /**
     * Método que obtiene el valor de el atributo tipoFila
     *
     * @return Date : valor que tiene el atributo tipoFila
     *
     * @author Pablo Zuñiga Mata
     * @since 23/03/2017
     */
    public List<Integer> getTipoFila() {
        return tipoFila;
    }

    /**
     * Método que ingresa el valor de el atributo tipoFila
     *
     * @author Pablo Zuñiga Mata
     * @param tipoFila
     * @since 23/03/2017
     */
    public void setTipoFila(List<Integer> tipoFila) {
        this.tipoFila = tipoFila;
    }

}
