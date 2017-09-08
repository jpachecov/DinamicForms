/**
 * @(#)MBGeneric.java 8/11/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.mb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.ine.observadoresINE.bsd.BSDGenericInterface;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase encargada de iniciar variables genéricas del sistema
 * 
 * @author José Antonio López Torres
 * @since 8/11/2016
 * @copyright Direccion de sistemas - INE
 */
public class MBGeneric implements Serializable{
    
    private static final Log LOGGER= LogFactory.getLog(MBGeneric.class);
    /**
     * Elemento para la serialización de los objetos generados por esta clase
     */
    private static final long serialVersionUID = -6053272616956359988L;
    
    /**
     * Referencia al bean con la ruta del Gluster
     * */
    @Autowired
    @Qualifier("rutaGluster")
    protected String rutaGluster;
    
    /**
     * Referencia al bean con los metodos para obtener textos y rutas de las imagenes
     */
    @Autowired
    @Qualifier("bsdGeneric")
    protected transient BSDGenericInterface bsdGeneric; 
    
    /**
     * Ruta de las fotos
     */
    protected String rutaFoto;
    
    /**
     * Ruta de la foto en caso de que no exista la foto del observador
     */
    protected String rutaFotoSuplente;

    /**
     * Para el manejo de los mensajes que se le envian al usuario 
     */
    protected enum TipoMensaje {
        ERROR_MENSAJE("mensajesError", 1), 
        INFO_MENSAJE("mensajesInfo", 2),
        ADVERTENCIA_MENSAJE("mensajesAdvertencia", 3);
        
        private TipoMensaje(String nombreMensaje, int tipo) {
            this.nombreMensaje = nombreMensaje;
            this.tipo = tipo;
        }
        
        private String nombreMensaje;
        private final int tipo;

        public String getNombreMensaje() {
            return nombreMensaje;
        }

        public int getTipo() {
            return tipo;
        }
    }
    
    /**
     * Método encargado de obtener al usuario logueado
     * 
     * @author José Antonio López Torres
     * @return DTOUsuarioLogin : usuario
     * @since 8/11/2016
     */
    public DTOUsuarioLogin obtenUsuario(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return (DTOUsuarioLogin) auth.getPrincipal();
    }
    
    /**
     * Metodo auxiliar para mostrar un mensaje de error o de informacion
     */
    protected void agregaMensaje(TipoMensaje tipoMensaje, String mensaje) {
        agregaMensaje(tipoMensaje.getTipo(), tipoMensaje.getNombreMensaje(), mensaje);
    }
    
    /**
     * Metodo auxiliar para mostrar un mensaje de error o de informacion
     */
    protected void agregaMensaje(int tipoMensaje, String nombreMensaje, String mensaje) {
        FacesMessage message = new FacesMessage();
        if(TipoMensaje.ERROR_MENSAJE.getTipo() == tipoMensaje) {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if(TipoMensaje.INFO_MENSAJE.getTipo() == tipoMensaje) {
                message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        message.setSummary(mensaje);
        FacesContext.getCurrentInstance().addMessage(nombreMensaje, message);
    }

    /**
     * Funci&oacute;n que agrega mensaje de notificaci&oacute;n
     *
     * @param mensaje  texto del mensaje de notificaci&oacute;n
     * @param severity tipo de mensaje de notificaci&oacute;n
     *
     * @author Jos&eacute; Carlos Ortega Romano
     * @since 08/09/2016
     */
    protected void agregaMensajeNotificacion(String mensaje, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage(severity, mensaje, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    /**
     * Método para obtener las rutas de las fotos
     * 
     * @author Pablo Zuñiga Mata
     * @param IdProceso
     * @param idDetalle
     * @since 07/08/2017
     */
    protected void obtenRutasFoto(Integer idProceso, Integer idDetalle){
    	List<DTOCImagenes> rutas = new ArrayList<DTOCImagenes>();
    	List<Integer> listaId = new ArrayList<Integer>();
    	listaId.add(1); // Es la ruta de la foto suplente para Hombre
    	listaId.add(2); // Es la ruta donde se encuentran las fotos
    	try{
    		rutas = bsdGeneric.obtenRutasIMG(idProceso, idDetalle, listaId);
    		if(rutas != null && rutas.size() == 0 && idDetalle != 0){
    			rutas = bsdGeneric.obtenRutasIMG(idProceso, 0, listaId);
    		}
    		for(DTOCImagenes ruta: rutas){
    			if(ruta.getDTOCImagenesPK().getIdImagen() == 1){
    				this.setRutaFotoSuplente(ruta.getRuta());
    			}else if(ruta.getDTOCImagenesPK().getIdImagen() == 2){
    				this.setRutaFoto(ruta.getRuta());
    			}
    		}
    	}catch(Exception e){
    		LOGGER.error("Error: MBGeneric.obtenRutasFoto",e);
    	}
    	
    }
    
    /**
     * Método encargado de validar la existencia de la foto, si no existe, deacuerdo al sexo 
     * regresamos la ruta de las imagenes predeterminadas
     * 
     * @author Pablo Zuñiga Mata
     * @param nombreFoto
     * @param sexo
     * @return
     * @since 08/08/2017
     */
    protected String validaRutaFoto(String nombreFoto,String usuario){
		String ruta =rutaGluster+File.separator+rutaFoto+File.separator+nombreFoto;
		File foto = new File(ruta);
		if (foto.exists()) {
			ruta = "/image//"+rutaFoto+File.separator+nombreFoto;
		}else {
			ruta = "/image//"+rutaFotoSuplente;			
		}
		return ruta;
    }

	/**
	 * Método que obtiene el valor del atributo rutaFoto
	 * 
	 * @return rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public String getRutaFoto() {
		return rutaFoto;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFoto
	 * 
	 * @param rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	/**
	 * Método que obtiene el valor del atributo rutaFotoSuplente
	 * 
	 * @return rutaFotoSuplente: valor del atributo rutaFotoSuplente
	 * @author Pablo Zuñiga Mata.
	 * @since  11/08/2017
	 */
	public String getRutaFotoSuplente() {
		return rutaFotoSuplente;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFotoSuplente
	 * 
	 * @param rutaFotoSuplente: valor del atributo rutaFotoSuplente
	 * @author Pablo Zuñiga Mata.
	 * @since  11/08/2017
	 */
	public void setRutaFotoSuplente(String rutaFotoSuplente) {
		this.rutaFotoSuplente = rutaFotoSuplente;
	}
    
    
       
}