package mx.ine.observadoresINE.dto.dinamicForm;
import java.io.Serializable;
import java.util.function.Function;
/**
 * Interface TransitionFuncion.
 * Define el comportamiento general de una función de transición.
 * Hace que las funciones lambda sean serializables.
 * @author jpachecov
 *
 * @param <S> - Tipo genérico que representa el dominio de una función.
 * @param <T> - Tipo genérico que representa la imágen de una función.
 */
public interface TransitionFunction<S,T> extends Function<S,T>, Serializable{}