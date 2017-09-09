package mx.ine.observadoresINE.dto.dinamicForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * Clase que representa una relacion o funcion
 * entre valores y listas de String.
 * 
 * En el mejor de los casos esta relación es una función.
 * 
 * 
 * Sea Fi una transición
 * 
 * Fi : T -> List<String>
 * 
 * Donde Fi puede ser:
 * 
 * Constante.
 * Para todo x en T Fi(x) = {a,b,c,d,e,...,n} con a,b,c...,n Strings
 * Y se define usando la funcion ANY.
 * 
 * POr casos.
 * Sean x1, ... , xn en T entonces
 * Fi(x1) = L1
 * Fi(x2) = L2
 * ...
 * Fi(xn) = Ln
 * 
 * Se define usando WHEN
 * 
 * 
 * @author jpachecov
 *
 * @param <T>
 */
public class FiTransition<T> implements Serializable{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -8213012618658664938L;
	
	/**
	 * Log
	 */
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(FiTransition.class);
	
	/**
	 * Hash interno para representar una relacion
	 */
	private Map<T, String> map;
	
	/**
	 * Hash interno para representar las transiciones por default.
	 */
	private Map<String, String> p_map;
	
	/**
	 * Constructor
	 */
	public FiTransition(){
		this.map = new LinkedHashMap<T, String>();
		this.p_map = new LinkedHashMap<String, String>();
	}
	
	/**
	 * Permite definir transiciones por casos
	 * @param in
	 * @param out
	 */
	public void when(T in, String out){
		map.put(in, out);
	}
	
	/**
	 * Define una transicion para cualquier valor
	 * @param ids
	 */
	public void any(String ids){
		p_map.put("any", ids);
	}
	
	/**
	 * Define una transición por default
	 * @param ids
	 */
	public void defaultt(String ids){
		p_map.put("default", ids);
	}
	
	/**
	 * Obtiene una lista con los ids.
	 * Es la imagen de la 'funcion' bajo el valor v
	 * @param v - El valor
	 * @return Lista que representa la imagen
	 */
	public List<String> getImagen(T v){
		List<String> ids = new ArrayList<String>();
		if(map.keySet().contains(v)){
			ids = mapString(map.get(v));
		}
		else if(p_map.keySet().contains("any")){
			ids = mapString(p_map.get("any"));
		}
		else if(p_map.keySet().contains("default")){
			ids = mapString(p_map.get("default"));
		}
		return ids;
	}
	
	/**
	 *
	 * TODO Este codigo lo tengo en varios lados, hay que factorizar
	 *
	 * Mapea una String a una lista de Strings
	 * @param st - Es una cadena con la siguiente forma
	 * s1,s2,s3,s4,s5 ie separada por comas.
	 * @return
	 */
	public List<String> mapString(String st){
		List<String> ids = new ArrayList<String>();
			Arrays.stream(st.split(",")).map(s -> {
				return s.trim();
			}).forEach(s -> {
				ids.add(s);
			});
		return ids;
	}
	
	public Map<T, String> getMap() {
		return map;
	}

	public void setMap(Map<T, String> map) {
		this.map = map;
	}
	
	public Map<String, String> getP_map() {
		return p_map;
	}

	public void setP_map(Map<String, String> p_map) {
		this.p_map = p_map;
	}
}