/**
 * @(#)MBHomeDirectorioDis.java 06/06/2016
 *
 * Copyright (C) 2014 Instituto Nacional Electoral (INE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.util.Utilidades;
import mx.org.ine.servicios.dto.OpcionMenuSistema;
import mx.org.ine.servicios.exception.ApplicationException;
import mx.org.ine.servicios.ui.BigMenu;
import mx.org.ine.servicios.ui.Etapa;
import mx.org.ine.servicios.ui.ModeloBigMenu;
import mx.org.ine.servicios.ui.Modulo;
import mx.org.ine.servicios.ui.Pestanha;

/**
 * Esta clase provee la funcionalidad para generar el men�, as� como para cargar todos los parametros, listas y constantes que se usaran de manera gen�rica en el sistema
 * 
 * @author  Israel V�zquez Jim�nez
 * @since   06/06/2016
 */
@Component("mbHomeIneBlank")
@Scope("session")
public class MBHomeIneBlank implements Serializable {

	/**
	 * Elemento necesario para la serializaci�n de los objetos generados de esta clase.
	*/
	private static final long serialVersionUID = 2442464004299422525L;
	
	/** 
	 * Objeto para el servicio de bit�cora de mensajes de la aplicaci�n. 
	 */
	public static final Logger logger = Logger.getLogger(MBHomeIneBlank.class);	
	
	
	/* ----------------------------------------------------------------------------------------- */
	/* ------------------------------------- ATRIBUTOS ----------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */		
	
	private ModeloBigMenu bigMenuModelo;
	
	private List<OpcionMenuSistema> menuPrincipal;
	
	private Boolean renderMenu;	
	
	/* ----------------------------------------------------------------------------------------- */
	/* ------------------------------------ CONSTRUCTOR  --------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */	
	
	/**
	 * Constructor de la clase
	 * 
	 * @param 
	 * return 
	 * 
	 * @author Israel V�zquez Jim�nez
	 * @since 06/06/2016
	 */	
	public MBHomeIneBlank() throws ApplicationException {
		
		try {
			logger.debug("Inicia:MBHomeDirectorioDis-MBHomeDirectorioDis =============================>>>>>>>");
			
			//Se inicializa la lista que contendr� el men�
			menuPrincipal = new ArrayList<OpcionMenuSistema>();
			
			//Se realiza la genaci�n del men�
			handleBuildMenu();
			
			logger.debug("Fin:MBHomeDirectorioDis-MBHomeDirectorioDis =============================>>>>>>>");			
		} 
		catch (Exception e) {
			logger.error("Error:MBHomeDirectorioDis-MBHomeDirectorioDis =============================>>>>>>>",e);
			logger.error(e.toString());
		}
	}


	/* ----------------------------------------------------------------------------------------- */
	/* --------------------------------------- METODOS  ---------------------------------------- */
	/* ----------------------------------------------------------------------------------------- */	
	
	
	/**
	 * M�todo que inicializa los campos/objetos que se utilizan en la pantalla del home as� como de manera gen�rica en el sistema
	 * 
	 * @param 
	 * return 
	 * 
	 * @author Israel V�zquez Jim�nez
	 * @since 06/06/2016
	 */	
	public void iniciaPantalla(){
		
		try {
			logger.debug("Inicia:MBHomeDirectorioDis-iniciaPantalla =============================>>>>>>>");	
			
			//Se define si el men� se muestra
			setRenderMenu(true);
			
			//Se realiza la genaci�n del men�
			handleBuildMenu();
			
			logger.debug("Fin:MBHomeDirectorioDis-iniciaPantalla =============================>>>>>>>");			
		} 
		catch (Exception e) {
			logger.error("Error:MBHomeDirectorioDis-iniciaPantalla  =============================>>>>>>>",e);
			logger.error(e.toString());
		}
	}


	/**
	 * M�todo que maneja la accion del cambio en los combos de estados y distritos, adicional de pintar el menu al seleccionar el estado  
	 * 
	 * @param 
	 * return 
	 * 
	 * @author Israel V�zquez Jim�nez
	 * @since 06/06/2016
	 */		
	public void handleBuildMenu() {
		
		try {
			logger.debug("Inicia:MBHomeDirectorioDis-handleBuildMenu =============================>>>>>>>");			
			
			//Se limpia el men�
			menuPrincipal.clear();
			
			//Se realiza la genaci�n del men�
			setBigMenuModelo(this.menuFijo());
			
			logger.debug("Fin:MBHomeDirectorioDis-handleBuildMenu =============================>>>>>>>");			
		} 
		catch (ApplicationException e) {
			logger.error("Error:MBHomeDirectorioDis-handleBuildMenu  =============================>>>>>>>",e);
			logger.error(e.toString());
		}
	}

	
	/**
	 * M�todo que gener� el men� de manera est�tica
	 * 
	 * @param 
	 * return ModeloBigMenu: men� de la aplicaci�n.
	 * 
	 * @author Israel V�zquez Jim�nez
	 * @since 06/06/2016
	 */		
	private ModeloBigMenu menuFijo()throws ApplicationException {
		
		/* Instancias */
		BigMenu bigMenuModeloTmp = new BigMenu();
		List<Pestanha> pestanhas = new ArrayList<Pestanha>();
		Pestanha pestanha = null;
		Etapa etapa = null;
		Modulo modulo = null;
		
		try {
			logger.debug("Inicia:MBHomeDirectorioDis-menuFijo =============================>>>>>>>");		

            //Se genera grupo
			pestanha = new Pestanha(Utilidades.mensajeProperties("etiqueta_menu_grupo_administracionProyectos"));
		    //Se genera subgrupo
			etapa = new Etapa(Utilidades.mensajeProperties("etiqueta_menu_subGrupo_administracionProyectos_modulo"));
			pestanha.getEtapas().add(etapa);
			//Se genera m�dulo
			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_registroProyectos"),"#"); 
			etapa.getModulos().add(modulo);
			//Se agrega grupo a lista del men�
			pestanhas.add(pestanha);
			
//            //Se genera grupo
//			pestanha = new Pestanha(Utilidades.mensajeProperties("etiqueta_menu_grupo_administracionEmpleados"));
//		    //Se genera subgrupo
//			etapa = new Etapa(Utilidades.mensajeProperties("etiqueta_menu_subGrupo_administracionEmpleados_modulo"));
//			pestanha.getEtapas().add(etapa);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_registroEmpleados"),"/app/registroEmpleados/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_asignacionEmpleaosProyecto"),"/app/asignacionEmpleadosProyecto/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_designacionEmpleaosProyecto"),"/app/designacionEmpleadosProyecto/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se agrega grupo a lista del men�
//			pestanhas.add(pestanha);
//
//            //Se genera grupo
//			pestanha = new Pestanha(Utilidades.mensajeProperties("etiqueta_menu_grupo_administracionUsuarios"));
//		    //Se genera subgrupo
//			etapa = new Etapa(Utilidades.mensajeProperties("etiqueta_menu_subGrupo_administracionUsuarios_modulo"));
//			pestanha.getEtapas().add(etapa);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_registroUsuarios"),"/app/registroUsuarios/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_asignacionUsuariosProyecto"),"/app/asignacionUsuariosProyecto/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se agrega grupo a lista del men�
//			pestanhas.add(pestanha);
//			
//            //Se genera grupo
//			pestanha = new Pestanha(Utilidades.mensajeProperties("etiqueta_menu_grupo_administracionCatalogos"));
//		    //Se genera subgrupo
//			etapa = new Etapa(Utilidades.mensajeProperties("etiqueta_menu_subGrupo_administracionCatalogos_modulo"));
//			pestanha.getEtapas().add(etapa);
//			//Se genera m�dulo
//			modulo = new Modulo(Utilidades.mensajeProperties("etiqueta_menu_modulos_registroOficinas"),"/app/registroOficinas/captura"); 
//			etapa.getModulos().add(modulo);
//			//Se agrega grupo a lista del men�
//			pestanhas.add(pestanha);			
			
			//Se agrega lista al objeto que pinta el men�
			bigMenuModeloTmp.setPestanhas(pestanhas);
			
			logger.debug("Fin:MBHomeDirectorioDis-menuFijo =============================>>>>>>>");				
			return bigMenuModeloTmp;
		} 
		catch (Exception e) {
			logger.error("Error:MBHomeDirectorioDis-menuFijo  =============================>>>>>>>",e);
			logger.error(e.toString());
			return null;
		}
	}

	
	
	/**
	 * M�todo que obtiene la guia general del sistema
	 * 
	 * @param 
	 * return StreamedContent: gu�a del sistema en frmato pdf.
	 * 
	 * @author Israel V�zquez Jim�nez
	 * @since 06/06/2016
	 */			
	public StreamedContent getdownloadMaterial(){
		
		/* Instancias */
		StreamedContent file = null;
		
		try {
			logger.debug("Inicia:MBHomeDirectorioDis-downloadMaterial =============================>>>>>>>");				
			
			//SAe obtiene el contexto de la aplicaci�n
			HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			//Se obtiene la ruta de la aplicaci�n 
			@SuppressWarnings("deprecation")
			String path = origRequest.getRealPath("");
			
			// Se  define la ruta y archivo a descargar
			String ruta = path+ File.separator +"resources"+ File.separator +"pdf" + File.separator +"Guia_General_Administracion_Directorio_Personal_Direccion_Sistemas_UNICOM.pdf";
			
			//Se obtiene el archivo
			File arc = new File(ruta);
			InputStream stream = new FileInputStream(arc);
			file = new DefaultStreamedContent(stream, "application/pdf", "Guia_General_Administracion_Directorio_Personal_Direccion_Sistemas_UNICOM.pdf");
			
			logger.debug("Fin:MBHomeDirectorioDis-downloadMaterial =============================>>>>>>>");			
		    return file;
			
		} catch (Exception e) {
			logger.error("Error:MBHomeDirectorioDis-downloadMaterial  =============================>>>>>>>",e);
			logger.error(e.toString());
			return null;
		}
	}
	
	
	/* ----------------------------------------------------------------------------------------- */
	/* ---------------------------- M�TODOS GETTERS & SETTERS ---------------------------------- */
	/* ----------------------------------------------------------------------------------------- */		


	public ModeloBigMenu getBigMenuModelo() {
		return bigMenuModelo;
	}


	public void setBigMenuModelo(ModeloBigMenu bigMenuModelo) {
		this.bigMenuModelo = bigMenuModelo;
	}


	public Boolean getRenderMenu() {
		return renderMenu;
	}


	public void setRenderMenu(Boolean renderMenu) {
		this.renderMenu = renderMenu;
	}

}
