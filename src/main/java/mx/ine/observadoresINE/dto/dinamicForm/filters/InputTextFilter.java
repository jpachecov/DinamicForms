package mx.ine.observadoresINE.dto.dinamicForm.filters;

import mx.ine.observadoresINE.dto.dinamicForm.DFilter;

/**
 * Clase concreta que representa un InputText
 * @author jpachecov
 *
 * @param <S>
 */
public class InputTextFilter<S> extends DFilter<S, Object> {

	public InputTextFilter(String id, String name) {
		super(id, name, "INPUT_TEXT");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8006581854288363679L;

	@Override
	protected void init() {
		
	}

	@Override
	protected void init(S value) {
		// TODO Auto-generated method stub
		
	}
}