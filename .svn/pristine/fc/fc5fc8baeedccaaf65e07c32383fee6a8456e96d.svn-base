/**
 * @(#)CNVAutoCompletePorObservadores.java 30/09/2016
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
 * <code>CNVAutoCompletePorObservadores.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 30/09/2016
 */
@FacesConverter(value="CNVAutoCompletePorObservadores", forClass = HLPAcreditacionesGafeteAutocomplete.class)
public class CNVAutoCompletePorObservadores implements Converter, Serializable {

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = 3099761087205616146L;

	/**
	 * Lista de los observadores encontrados utilizada por el autocomplete
	 */
	private List<HLPAcreditacionesGafeteAutocomplete> listObservadoresAutoComplete;

	/**
	 * Sobrecarga del constructor para guardar la lista de los observadores
	 * encontrados por el autocomplete
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 13/09/2016
	 * @param listObservadoresAutoComplete : lista de observadores encontrados por el
	 * autocomplete
	 */
	public CNVAutoCompletePorObservadores(List<HLPAcreditacionesGafeteAutocomplete> listObservadoresAutoComplete){
		this.listObservadoresAutoComplete = listObservadoresAutoComplete;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String completeObservador) {
		if (completeObservador.trim().equals("")) { 

            return null;  
        } 
		
		for (HLPAcreditacionesGafeteAutocomplete observador : listObservadoresAutoComplete) {
			
			if (observador.getNombreObservador().equals(completeObservador)) {
				
				return observador;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object valorDto) {
		if (valorDto == null) {

			return "";
		}else if (valorDto instanceof HLPAcreditacionesGafeteAutocomplete) {

			return ((HLPAcreditacionesGafeteAutocomplete) valorDto).getNombreObservador();
		}

		return null;
	}
	/**
	 * Método que obtiene el valor del atributo listObservadoresAutoComplete
	 * 
	 * @return listObservadoresAutoComplete: valor del atributo listObservadoresAutoComplete
	 * @author Pablo Zuñiga Mata.
	 * @since  30/09/2016
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getListObservadoresAutoComplete() {
		return listObservadoresAutoComplete;
	}
	/**
	 * Método que ingresa el valor del atributo listObservadoresAutoComplete
	 * 
	 * @param listObservadoresAutoComplete: valor del atributo listObservadoresAutoComplete
	 * @author Pablo Zuñiga Mata.
	 * @since  30/09/2016
	 */
	public void setListObservadoresAutoComplete(
			List<HLPAcreditacionesGafeteAutocomplete> listObservadoresAutoComplete) {
		this.listObservadoresAutoComplete = listObservadoresAutoComplete;
	}

}
