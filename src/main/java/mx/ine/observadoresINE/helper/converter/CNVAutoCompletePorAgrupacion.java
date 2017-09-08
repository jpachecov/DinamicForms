/**
 * @(#)CNVAutoCompletePorAgrupacion.java 30/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.helper.converter;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;

/**
 * <code>CNVAutoCompletePorAgrupacion.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 30/09/2016
 */
 @FacesConverter(value="CNVAutoCompletePorAgrupacion", forClass = HLPAcreditacionesGafeteAutocomplete.class)
public class CNVAutoCompletePorAgrupacion implements Converter, Serializable{

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = 2264239135034063406L;

	/**
	 * Lista de las agrupaciones encontradas, utilizada por el autocomplete
	 */
	private List<HLPAcreditacionesGafeteAutocomplete> listAgrupacionesAutoComplete;

	/**
	 * Sobrecarga del constructor para guardar la lista de las agrupaciones
	 * encontradas por el autocomplete
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 13/09/2016
	 * @param listAgrupacionesAutoComplete : lista de agrupaciones encontradas por el
	 * autocomplete
	 */
	public CNVAutoCompletePorAgrupacion( List<HLPAcreditacionesGafeteAutocomplete> listAgrupacionesAutoComplete ){
		this.listAgrupacionesAutoComplete = listAgrupacionesAutoComplete;
	}
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext contex, UIComponent component, String completeAgrupacion) {
		if (completeAgrupacion.trim().equals("")) { 

            return null;  
        } 
		
		for (HLPAcreditacionesGafeteAutocomplete agrupacion : listAgrupacionesAutoComplete) {
			
			if (agrupacion.getNombreAgrupacion().equals(completeAgrupacion)) {
				
				return agrupacion;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext contex, UIComponent component, Object valorDto) {
		if (valorDto == null) {

			return "";
		}else if (valorDto instanceof HLPAcreditacionesGafeteAutocomplete) {

			return ((HLPAcreditacionesGafeteAutocomplete) valorDto).getNombreAgrupacion();
		}

		return null;
	}
	/**
	 * Método que obtiene el valor del atributo listAgrupacionesAutoComplete
	 * 
	 * @return listAgrupacionesAutoComplete: valor del atributo listAgrupacionesAutoComplete
	 * @author Pablo Zuñiga Mata.
	 * @since  14/09/2016
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getListAgrupacionesAutoComplete() {
		return listAgrupacionesAutoComplete;
	}
	/**
	 * Método que ingresa el valor del atributo listAgrupacionesAutoComplete
	 * 
	 * @param listAgrupacionesAutoComplete: valor del atributo listAgrupacionesAutoComplete
	 * @author Pablo Zuñiga Mata.
	 * @since  14/09/2016
	 */
	public void setListAgrupacionesAutoComplete(
			List<HLPAcreditacionesGafeteAutocomplete> listAgrupacionesAutoComplete) {
		this.listAgrupacionesAutoComplete = listAgrupacionesAutoComplete;
	}

}
