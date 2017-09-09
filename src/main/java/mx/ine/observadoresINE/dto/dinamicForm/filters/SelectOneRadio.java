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

	private S value;
	private T data;
	private S itemV;
	private String itemL;

	private TransitionFunction<T, String> labelFunction;
	
	private TransitionFunction<T, String> valueFunction;
	
	@Override
	protected void init() {
		setData((T)getInitF().apply(getValue()));
	}
	
	@Override
	protected void init(S value) {
		setData((T)getInitF().apply(getValue()));
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