/**
 * @(#)DTOList.java 10/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dto;

import java.io.Serializable;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 10/08/2017
 * @copyright Direccion de sistemas - INE
 */
public class DTOList implements Serializable {

    private static final long serialVersionUID = -7420944086239268106L;

    /**
     * Identificador del objeto
     */
    private Integer key;

    /**
     * Etiqueta que representa al objeto, asociada al identificador
     */
    private String value;
    
    
    private Boolean disabled;

    public DTOList(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
   
    
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    
}
