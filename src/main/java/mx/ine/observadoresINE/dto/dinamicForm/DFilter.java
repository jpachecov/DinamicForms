package mx.ine.observadoresINE.dto.dinamicForm;

import java.io.Serializable;

/**
 * 
 * Clase que representa un componente o filtro dentro de un formulario.
 * 
 * @author jpachecov
 *
 *	T es el tipo del valor que guarda el filtro
 *	S es el tipo del data que necesita el filtro para funcionar
 */
public abstract class DFilter<T, S> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5017720670204359597L;

	/**
	 * Identificador del filtro
	 * 
	 * Debe coincidir con el id del componente.
	 */
	protected String id;

	/**
	 * Nombre para este filtro
	 */
	protected String name;

	/**
	 * Variable para ligar el valor del componente desde la vista.
	 */
	protected T value;
	
	/**
	 * Variable para saber el tipo del filtro
	 * Por ejemplo: SELECT_ONE_MENU
	 * ,INPUT_TEXT, etc.
	 */
	private String type;
	
	/**
	 * Variable para saber si el filtro debe necesariamente tener
	 * un valor.
	 */
	private boolean required;
	
	/**
	 * Variable para saber si el filtro es visible en la vista.
	 */
	private boolean visible;
	
	/**
	 * 
	 * Función F de transición de este componente.
	 * 
	 * Una definición es la siguiente:
	 * 
	 * F : Object -> [DFilter]
	 *  
	 * F(x1) = {X11, X12, ... , X1n}
	 * . 
	 * . 
	 * . 
	 * F(xn) = {Xn1,Xn2, ... , Xnn}
	 * 
	 * Es decir:
	 * 
	 * Si el filtro tiene un valor x1 entonces deben renderizarce los filtros
	 * {X11, X12, ... , X1n}.
	 * 
	 * Si el filtro tiene un valor x2 entonces deben renderizarce los filtros
	 * {X21, X22, ... , X2n}.
	 * 
	 * Si el filtro muestra un mismo conjunto de filtros para cualquier valor
	 * que tenga, entonces F se define como:
	 * 
	 * F(x) = {X1, X2, ... , Xn} para cualquier valor x.
	 * 
	 * 
	 */
	private FiTransition<T> fiTransition;
	
	/**
	 * Función para inicilializar el filtro
	 * 
	 * Es tipo T : Object -> Object porque no sabemos que tipo de
	 * valor se necesita para inicilizarlo
	 */
	private TransitionFunction<Object, Object> initF;
	
	/**
	 * Constructor con un id.
	 * 
	 * @param id
	 */
	public DFilter(String id) {
		this.id = id;
	}

	public DFilter (String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public DFilter(String id, String name, String type){
		this.id = id;
		this.name = name;
		this.type = type;
	}

	/**
	 * Método de inicialización del componente cuando no depende de nadie
	 * Hace una aplicación de la función lambda initF.
	 */
	protected abstract void init();
	
	/**
	 * Método de inicialización del componente cuando depende de un valor externo
	 * Hace una aplicación de la función lambda initF.
	 */
	protected abstract void init(T value);
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public TransitionFunction<Object, Object> getInitF() {
		return initF;
	}

	public void setInitF(TransitionFunction<Object, Object> initF) {
		this.initF = initF;
	}

	public FiTransition<T> getFiTransition() {
		return fiTransition;
	}

	public void setFiTransition(FiTransition<T> fiTransition) {
		this.fiTransition = fiTransition;
	}
}