package mx.ine.observadoresINE.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.ine.observadoresINE.dto.helper.HLPAccionModulo;
import mx.ine.observadoresINE.mb.MBAdministradorSistema;

import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.logging.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 * Clase helper para realizar transformaciones de datos del Menú
 * 
 * @author Mayra Victoria
 * @version 1.0
 * @since 04/102016
 */
public class HLPTransformadorMenu implements Serializable {

    /**
     * Elemento para serialización de objetos 
     */
    private static final long serialVersionUID = -1402766265258219855L;
    /**
     * Objeto para el servicio de bitácora de mensajes de la aplicación.
     */
    private static final Logger logger = Logger.getLogger(MBAdministradorSistema.class);

    public List<HLPAccionModulo> construyeMenuAcciones(String jsonMenuAcciones, Integer idModuloUser ) {
		
        List<HLPAccionModulo> listaAcciones = new ArrayList<>();
        
        try{
            JSONObject menuJson = new JSONObject(jsonMenuAcciones);
            ///Obtenemos la lista principal
            JSONArray arr =null;
            Object listAccionesObject = menuJson.get("listAccionesMenu");
            if (listAccionesObject instanceof JSONArray) {
                // Es un arreglo
            	 arr =(JSONArray) listAccionesObject;
            }
            if (arr != null) { 
            	for (int i=0;i<arr.length();i++){
            		JSONObject menu=new JSONObject(arr.get(i).toString());
            		JSONArray modulosList= menu.getJSONArray("moduloslist");
            		if(modulosList!=null){
            			for (int j=0;j<modulosList.length();j++){
            				JSONObject modulo=new JSONObject(modulosList.get(j).toString());
            				String idModulo = modulo.getInt("idModulo")+"";
            				if(NumberUtils.isNumber(idModulo) && (Integer.parseInt(idModulo)==idModuloUser)){
            					//Es el mismo módulo, obtenemos las acciones en una lista de combos
            					listaAcciones = new ArrayList<>();
            					JSONArray accionesJson= modulo.getJSONArray("listUrlModulos");
            					if(accionesJson!=null){
            						for (int k=0;k<accionesJson.length();k++){
            							JSONObject accion=new JSONObject(accionesJson.get(k).toString());
            							HLPAccionModulo acciones= new HLPAccionModulo();
            							acciones.setIdAccion(accion.getInt("idAccion"));
            							acciones.setUrlModulo(accion.getString("urlModulo"));
            							String action = accion.getString("accionDescrip");
            							acciones.setAccionDescrip(action);
            							if(action!=null && action.length()>0)			
            								acciones.setAction(action.toLowerCase().substring(0, action.length()-1));
            							listaAcciones.add(acciones);
            						}
            					}
            
            				}
            			}
            
            		}
            
            	} 
            }
        }catch(Exception e){
        	logger.error("Error parseando el menú de acciones ",e);
        
        }
        return listaAcciones;		
    }
}
