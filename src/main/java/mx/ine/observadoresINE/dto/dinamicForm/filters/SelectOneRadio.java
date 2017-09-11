package mx.ine.observadoresINE.dto.dinamicForm.filters;

import mx.ine.observadoresINE.dto.dinamicForm.DFilter;
import mx.ine.observadoresINE.dto.dinamicForm.LabelValueInterface;
import mx.ine.observadoresINE.dto.dinamicForm.TransitionFunction;

/**
 * Clase concreta que represena un componente SelectOneRadio
 * @author jpachecov
 *
 * @param <S>
 * @param <T>
 */
public class SelectOneRadio<S, T> extends DFilter<S, T> implements LabelValueInterface<T,String>{

	public SelectOneRadio(String id, String name) {
		super(id, name , "SELECT_ONE_RADIO");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9145084702861942667L;

	private T data;

	private TransitionFunction<T, String> labelFunction;
	
	private TransitionFunction<T, String> valueFunction;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init() {
		setData((T)getInitF().apply(getValue()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init(S value) {
		setData((T)getInitF().apply(getValue()));
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String getLabelOf(T e) {
		return this.labelFunction.apply(e);
	}

	@Override
	public String getValueOf(T e) {
		return this.valueFunction.apply(e);
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

}