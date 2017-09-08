package mx.ine.observadoresINE.dto.dinamicForm.filters;

import mx.ine.observadoresINE.dto.dinamicForm.DFilter;
import mx.ine.observadoresINE.dto.dinamicForm.LabelValueInterface;
import mx.ine.observadoresINE.dto.dinamicForm.TransitionFunction;

/**
 * Clase concreta que representa un SelectOneMenu en vista.
 * @author jpachecov
 *
 * @param <S> - Tipo del valor que tendrá el filtro
 * @param <T> - Tipo del valor que el filtro necesita para funcionar.
 * 
 * En este caso T es el tipo de la lista de items del menu.
 * 
 * Por ejemplo: List<String> o List<DTOEstado> Si vamos a mostrar listas
 * de Strings o de estados respectivamente.
 */
public class SelectOneMenuFilter<S,T> extends DFilter<S,T> implements LabelValueInterface<T, String>{
	
	public SelectOneMenuFilter(String id, String name) {
		super(id, name, "SELECT_ONE_MENU");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5843849093252844112L;
	
	private S value;
	private T data;
	private S itemV;
	private String itemL;

	private TransitionFunction<T, String> labelFunction;
	
	private TransitionFunction<T, String> valueFunction;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		setData((T)getInitF().apply(getValue()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init(S value) {
		setData((T)getInitF().apply(value));
	}
	public S getValue() {
		return value;
	}
	public void setValue(S value) {
		this.value = value;
	}
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	public S getItemV() {
		return itemV;
	}
	public void setItemV(S itemV) {
		this.itemV = itemV;
	}
	public String getItemL() {
		return itemL;
	}
	public void setItemL(String itemL) {
		this.itemL = itemL;
	}
	
	public TransitionFunction<T, String> getLabelFunction() {
		return labelFunction;
	}

	public void setLabelFunction(TransitionFunction<T, String> labelFunction) {
		this.labelFunction = labelFunction;
	}

	public TransitionFunction<T, String> getValueFunction() {
		return valueFunction;
	}

	public void setValueFunction(TransitionFunction<T, String> valueFunction) {
		this.valueFunction = valueFunction;
	}

	@Override
	public String getLabelOf(T e) {
		return labelFunction.apply(e);
	}

	@Override
	public String getValueOf(T e) {
		return valueFunction.apply(e);
	}
}