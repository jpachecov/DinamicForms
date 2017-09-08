/**
 * @(#)Validaciones.java 06/06/2016
 *
 * Copyright (C) 2014 Instituto Nacional Electoral (INE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Clase que contiene m�todos para realizar validaciones genericas para todo el sistema
 * 
 * @author  Israel V�zquez Jim�nez
 * @since   06/04/2016
 */
@Component("validaciones")
@Scope("prototype")
public class Validaciones implements Serializable {

	/**
	 * Elemento necesario para la serializaci�n de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 
	 * Objeto para el servicio de bit�cora de mensajes de la aplicaci�n. 
	 */
	private static final Logger logger = Logger.getLogger(Validaciones.class);
	
	/* ----------------------------------------------------------------------------------------- */
	/* ------------------------------------- METODOS ------------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */		

	
	/**
	 * M�todo que valida los caracteres permitidos para el nombre del proyecto 
	 * @param  String: Cadena a evaluar
	 * @return Boolean: true-Cadena valida  False-Cadena invalida
	 */
	public Boolean validaCaracteresNombreProyecto(String cadena){
		
	try{	
		
		
		
		 //Se comprueba la cadena	
	     Pattern pat = Pattern.compile(Utilidades.mensajeProperties("validacion_expresion_generales_caracteresValidos_nombre_proyecto"));
	     Matcher mat = pat.matcher(cadena);
	
	     return mat.matches();	
	}
     catch (Exception e) {
			logger.error("Error:Validaciones-validaCaracteresNombreProyecto =============================>>>>>>>>>>>>>>>>>", e);
			logger.error(e.toString());
			 return false;
		}     
	}		
	
	
	/**
	 * M�todo que valida los caracteres permitidos para una descripcion 
	 * @param  String: Cadena a evaluar
	 * @return Boolean: true-Cadena valida  False-Cadena invalida
	 */
	public Boolean validaCaracteresDescripcion(String cadena){
		
	try{	
		 //Se comprueba la cadena		
	     Pattern pat = Pattern.compile(Utilidades.mensajeProperties("validacion_expresion_generales_caracteresValidos_descripcion"));
	     Matcher mat = pat.matcher(cadena);
	
	     return mat.matches();	
	}
     catch (Exception e) {
			logger.error("Error:Validaciones-validaCaracteresDescripcion =============================>>>>>>>>>>>>>>>>>", e);
			logger.error(e.toString());
			 return false;
		}        
	}	
	
	/**
	 * M�todo que valida los caracteres permitidos para el d�a 
	 * @param  String: Cadena a evaluar
	 * @return Boolean: true-Cadena valida  False-Cadena invalida
	 */
	public Boolean validaCaracteresDia(String cadena){
		
	try{	
		 //Se comprueba la cadena			
	     Pattern pat = Pattern.compile(Utilidades.mensajeProperties("validacion_expresion_generales_caracteresValidos_dia"));
	     Matcher mat = pat.matcher(cadena);
	
	     return mat.matches();	
	}
     catch (Exception e) {
			logger.error("Error:Validaciones-validaCaracteresDia =============================>>>>>>>>>>>>>>>>>", e);
			logger.error(e.toString());
			 return false;
	  }       
	}	
	
	
	/**
	 * M�todo que valida el Mime-Type de un archivo  
	 * @param  InputStream:  Archivo
	 * @param  String: Nombre del archivo
	 * @param  String: tipo archivo a validar
	 * @return Boolean: true-Cadena valida  False-Cadena invalida
	 */
	public Boolean validaMimeType(InputStream archivo, String nombreArchivo, String tipoArchivo){
		
		/* Instancias */
		String patron = "";
		
		try {
		
			//Imagen
			if(tipoArchivo.equalsIgnoreCase("I")){
				patron = Utilidades.mensajeProperties("validacion_expresion_generales_tipoArchivo_imagen");
			}
			//Texto
			if(tipoArchivo.equalsIgnoreCase("T")){
				
			}
			
			//Se obtiene el tipo de mime del archivo
			 TikaConfig config = TikaConfig.getDefaultConfig();
			 Detector detector = config.getDetector();
	
			 TikaInputStream stream = TikaInputStream.get(archivo);
	
			 Metadata metadata = new Metadata();
			 metadata.add(Metadata.RESOURCE_NAME_KEY, nombreArchivo);
			 MediaType mediaType;
			
			mediaType = detector.detect(stream, metadata);
			
			 //Se comprueba el archivo
		     Pattern pat = Pattern.compile(patron);
		     Matcher mat = pat.matcher( mediaType.getSubtype());
	
	     return mat.matches();	
	  }
     catch (Exception e) {
			logger.error("Error:Validaciones-validaMimeType =============================>>>>>>>>>>>>>>>>>", e);
			logger.error(e.toString());
			 return false;
	  }  
	}	
	
}
