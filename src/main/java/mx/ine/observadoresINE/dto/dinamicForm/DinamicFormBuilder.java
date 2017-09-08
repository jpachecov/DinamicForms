package mx.ine.observadoresINE.dto.dinamicForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que sirve para construir un Formulario Dinámico Válido
 * @author jpachecov
 *
 */
public class DinamicFormBuilder implements Serializable{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5960774487262797499L;

	/**
	 * Lista interna con todos los filtros definidos y construidos.
	 */
	private List<DFilter> l_filtros;
	
	/**
	 * Lista de DFilters que aparecerán desde el inicio en la vista
	 */
	private List<DFilter> initialState;
	
	/**
	 * Hash con todos los filtros.
	 * La llave es el id del filtro
	 */
	private Map<String, List<DFilter>> filtros;
	
	/**
	 * Lista de errores generados al momento de 
	 * validar la definición de este formulario dinámico.
	 */
	private List<String> errores = new ArrayList<String>();
	
	/**
	 * 
	 * Si queremos usar este constructor estonces
	 * cada uno de los filtros dados debe tener definida su transicion Fi.
	 * @param todos
	 * @param transitions
	 */
	public DinamicFormBuilder(List<DFilter> todos, List<DFilter> initialState){
		this.l_filtros = todos;
		this.initialState = initialState;
		this.filtros = new LinkedHashMap<String, List<DFilter>>();
		for(DFilter f : todos){
			if(filtros.get(f.getId()) == null){
				filtros.put(f.getId(), new ArrayList<DFilter>());
			}
			filtros.get(f.getId()).add(f);
		}
	}
	
	/**
	 * Obtiene una máquina de estados finita bien definida para manejar
	 * la renderización y eventos en un formulario dinámico.
	 * 
	 * La máquina obtenida es válida sí y sólo sí validateDefiniciton es true
	 * 
	 * @return
	 */
	public RenderMachine getMagia(){
		RenderState initial = new RenderState(initialState);
		RenderMachine machine = new RenderMachine(initial);
		machine.setAllFilters(l_filtros);
		return machine;
	}
	
	/**
	 * Decide si la definicion del formulario dinámico es válida.
	 * @return
	 * @throws Exception
	 */
	public boolean validateDefinition() throws Exception{
		boolean valid = true;
		// Primero validamos nombres
		valid &= checkFilterNames();
		// Validamos estado inicial
		valid &= checkInitiaState();
		// Validamos transiciones
		valid &= checkTransitions();
		return valid;
	}
		
	/**
	 * Varifica que no haya ids repetidos en los filtros
	 * definidos.
	 * @return  true - Si todos los id son validos, false en otro caso
	 * 
	 */
	public boolean checkFilterNames(){
		boolean valid = true;
		for(Object id : filtros.keySet()){
			if(filtros.get((String)id).size() > 1){
				valid &= false;
				errores.add("El id: " + (String)id + " esta repetido.");
			}
		}
		return valid;
	}
	
	/**
	 * Verifica que el estado inicial sea valido
	 * @return
	 */
	public boolean checkInitiaState(){
		boolean valid = true;
		if(initialState == null || initialState.isEmpty()){
			errores.add("Error. El estado inicial es vacío.");
			return false;
		}
		for(DFilter d : initialState){
			valid &= existFilter(d.getId());
		}
		if(!valid){
			errores.add("Error en la definición del estado inicial");
		}
		return valid;
	}
	
	/**
	 * Verifica que esten bien definidas todas las transiciones.
	 * @return
	 */
	public boolean checkTransitions(){
		boolean cool = true;
		for(DFilter f : l_filtros){
			cool &= checkFi(f, f.getFiTransition());
		}
		if(!cool){
			errores.add("Existen errores en algunas transiciones definidas entre componentes.");
		}
		return cool;
	}
	
	/**
	 * Método para saber si una transición está bien definida
	 * @param fi
	 * @return
	 */
	public boolean checkFi(DFilter f, FiTransition fi){
		boolean valid = true;
		//Vamos por los casos definidos en fi
		LinkedHashMap map = (LinkedHashMap) fi.getMap();
		for(Object k : map.keySet()){
			List<String> img = fi.getImagen((String) k);
			for(String id: img){
				valid &= existFilter(id);
			}
		}
		// Vamos por las transiciones por default
		LinkedHashMap p_map = (LinkedHashMap) fi.getP_map();
		for(Object k : p_map.keySet()){
			List<String> img = fi.getImagen((String) k);
			for(String id: img){
				valid &= existFilter(id);
			}
		}		
		if(!valid){
			errores.add("El filtro con id: " + f.getId() + " no tiene una transición válida.");
		}
		return valid;
	}
	
	/**
	 * Método para saber si existe un filtro en la
	 * lista de todos los filtros.
	 * @param id
	 * @return
	 */
	public boolean existFilter(String id){
		boolean exist = false;
		for(DFilter d : l_filtros){
			exist |= d.getId().equals(id);
		}
		if(!exist){
			errores.add("El filtro con id: " + id + " no existe en la lista de todos los filtros.");
		}
		return exist;
	}
	
	/**
	 * Obtiene la lista de errores.
	 * @return
	 */
	public List<String> getErrores(){
		return this.errores;
	}
	
}
