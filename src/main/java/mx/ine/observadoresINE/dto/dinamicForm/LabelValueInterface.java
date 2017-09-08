package mx.ine.observadoresINE.dto.dinamicForm;

/**
 * 
 * Interface que deben implementar aquellas subclases de DFilter que
 * necesiten un Label y un Value en la vista.
 * 
 * Parametriza.
 * S - Tipo del elemento - Label
 * T - El tipo del valor del elemento en vista - Value
 * @author jpachecov
 *
 * @param <S>
 * @param <T>
 */
public interface LabelValueInterface<S, T> {

	String getLabelOf(S e);
	T getValueOf(S e);
}
