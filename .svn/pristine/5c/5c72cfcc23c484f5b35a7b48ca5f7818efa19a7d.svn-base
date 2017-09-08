/**
 * @(#)QRYContainer.java 06/06/2016
 *
 * Copyright (C) 2014 Instituto Federal Electoral (IFE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.query;

import java.util.Properties;

import mx.org.ine.servicios.query.QRYContainerInterface;

import org.springframework.stereotype.Component;

/**
 * Esta clase define e implementa los m�todos b�sicos para administrar 
 * los queries almacenados en el archivo </strong>QuerySource.properties</strong>
 * 
 * NOTA IMPORTANTE: Esta clase es �nica y con �nica nos referimos a que
 *                  no deben crearse m�s de este tipo ya que solo esta podra
 *                  contener el properties de los querys que se utilizen, esto 
 *                  para tener una mejor adminitraci�n de los querys.
 * 
 * @author IFE - Roberto Shir�sago Dom�nguez
 * @since 21/02/2012
 */
@Component("qryContainer")
public class QRYContainer implements QRYContainerInterface {

	/* ------------------------------- ATRIBUTOS ------------------------------------- */

	/**
	 * Archivo properties que contiene todos los queries de la aplicaci�n.
	 */
	private Properties querySource;
	
	/* -------------------------------------------------------------------------------- */
	/* --------------------------------- METODOS -------------------------------------- */
	/* -------------------------------------------------------------------------------- */
	
	public QRYContainer() throws Exception {
		
		querySource = new Properties();
		querySource.load( QRYContainer.class.getResourceAsStream( "/QuerySource.properties" ) );
	}

	/*
	 * (non-Javadoc)
	 * @see mx.org.ine.bases.query.QRYContainerInterface#getQuery(java.lang.String)
	 */
	@Override
	public String getQuery(String nombreQuery) {
		return querySource.getProperty(nombreQuery);
	}

}
