package mx.ine.observadoresINE.dto.dinamicForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Clase que representa un estado del para la maquina de Render.
 * 
 * Para el caso del problema de Renderización de Filtros dinámicos
 * Este objeto representa una lista de objectos DFilter
 * 
 * @author jpachecov
 *
 */
public class RenderState implements MachineState<List<DFilter>>, Serializable{


	/**
	 * Serialización
	 */
	private static final long serialVersionUID = -8496080301809270538L;
	
	/**
	 * Lista interna para representar el estado
	 * Un objeto RenderState en realidad es una lista de 
	 * objetos tipo DFilter.
	 */
	private List<DFilter> filtros;
	
	/**
	 * Constructor
	 */
	public RenderState(){
		this.filtros = new ArrayList<DFilter>();
	}
	
	/**
	 * Constructor con filtros inicializados.
	 * @param filters
	 */
	public RenderState(List<DFilter> filters){
		for(DFilter f: filters){
			f.setVisible(true);
		}
		this.filtros = filters;
	}
	
	/**
	 * Método para comparar dos Estados
	 */
	@Override
	public int compareTo(List<DFilter> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<DFilter> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<DFilter> filtros) {
		this.filtros = filtros;
	}

}
