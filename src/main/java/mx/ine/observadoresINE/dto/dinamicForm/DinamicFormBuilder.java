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
	* Constructor
	*/
	public DinamicFormBuilder(){
		this.l_filtros = new ArrayList<DFilter>();
		this.filtros = new LinkedHashMap<String, List<DFilter>>();
		this.initialState = new ArrayList<DFilter>();
	}

	/**
	 * Constructor
	 * Construye la lista de todos los filtros
	 * y  la lista de filtros que aparecerán inicialmente
	 * en la vista.
	 * @param todos
	 * @param transitions
	 */
	public DinamicFormBuilder(List<DFilter> todos, List<DFilter> initialState){
		this.l_filtros = todos;
		this.initialState = initialState;
		this.filtros = new LinkedHashMap<String, List<DFilter>>();
		for(DFilter f : todos){
			if(f.getFiTransition() == null){
				f.setFiTransition(new FiTransition());
			}
			if(filtros.get(f.getId()) == null){
				filtros.put(f.getId(), new ArrayList<DFilter>());
			}
			filtros.get(f.getId()).add(f);
		}
	}

	/**
	* Este metodo se llama cuando ya existe la lista de todos los filtros.
	*
	*
	*
	*/
	public void setInitialFilters(String filtros) throws Exception{
		boolean hay = false;
		List<String> flts = mapString(filtros){
			for(String id : flts){
				if(!existFilter(id)){
					hay &= true;
					errores.add("El id: " + id + " no se encuentra asociado a ningun filtro.");
				} 
			}
		}
		if(!hay){
			agregaIniciales(flts);
		}
		throw Exception("No se pudieron definir los filtros iniciales del formulario.");

	}

	/**
	* Agrega los filtros iniciales a partir de una lista valida de ids de filtros.
	* Es decir, estos si existen.
	*
	*/
	public void agregaIniciales(List<String> ids){
		for(String id : ids){
			initialState.add(filtros.get(id));
		}

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



	/**
	*
	* Agrega un nuevo filtro a la lista de todos los filtros y
	* al Hash.
	*/
	public void addFilter(DFilter filter) throws Exception{
		if(existFilter(filter)){
			throw new Exception("El id: " + filter.getId() + " ya existe.");
		}
		l_filtros.add(filter);
		filtros.put(filter.getId(), filter);
	}	

	/**
	* Obtiene el objecto FiTransition del filtro
    * Este método debe ser llamado después de construir el objeto
    * DinamicFormFilter con la lista de todos los filtros,
    * ya que se hace validacion de existencia de filtro para el 
    * id dado.
	*
	*/
	public FiTransition setFor(String idF) throws Exception {
		if(existFilter(idF)){
			return filtros.get(idF).getFiTransition();
		}
		throw new Exception("El filtro con id: " + idF + " no existe.");
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
	public boolean isValidDefinition() throws Exception{
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
