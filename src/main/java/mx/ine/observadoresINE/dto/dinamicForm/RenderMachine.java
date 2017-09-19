package mx.ine.observadoresINE.dto.dinamicForm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.detect.NNExampleModelDetector;


/**
 * 
 * Clase que representa una máquina de estados finitos para controlar la
 * renderización de filtros dinámicos.
 * 
 * Un estado del autómata es una Lista de DFilter.
 * 
 * La vista renderiza sólo los filtros que pertenezcan
 * al estado actual de la máquina.
 * 
 * 
 * @author jpachecov
 *
 */
public class RenderMachine implements Serializable{

	
	/**
	 * Serialización
	 */
	private static final long serialVersionUID = -3698262090232183062L;
	
	private static final Log log = LogFactory.getLog(RenderMachine.class);
	
	/**
	 * Lista con todos los filtros
	 */
	@SuppressWarnings("rawtypes")	
	private Map<String, DFilter> filtersMap;
	
	
	/**
	 * Estado actual de la máquina
	 */
	private RenderState current;
	
	/**
	 * 
	 * Construcor.
	 * Recibe el estado inicial del autómata.
	 * 
	 * @param initial
	 */
	@SuppressWarnings("rawtypes")
	public RenderMachine(RenderState initial){
		this.current = initial;
		for(DFilter ft : current.getFiltros()){
			if(ft.getInitF() != null){
				ft.init();
			}
		}
	}
	
	/**
	 * Función Delta de transiciones para esta
	 * máquuina.
	 * @param crt - EL estado actual del autómata
	 * @param id - El id del filtro que sufrió un cambio
	 * en la vista.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private RenderState delta(RenderState crt, String id) throws Exception{
		DFilter f = F(id);
		RenderState newState = obtenNuevoEstado(crt, f);
		return newState;
	}
	
	/**
	 * Método llamado desde la vista para dar aviso de un
	 * filtro ha sido modificado. Por lo que se hace una transición
	 * a un nuevo estado.
	 * 
	 * Normalmente esta función será invocada via ajax.
	 * @param id - El id del filtro que fue modificado.
	 */
	public void actionOn(String id){
		try {
			current = delta(current, id);
		} catch(Exception e){
			log.error("Error en RenderMachine.actionOn");
			log.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Dado el id de un filtro, busca en los filtros
	 * del estado actual de la máquina.
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DFilter F(String id) throws Exception{
		return filtersMap.get(id);
	}
	
	/**
	 * Obtiene el nuevo estado de la máquina. ie, 
	 * Obtiene los filtros que activa f y los agrega al 
	 * estado actual.
	 * @param crt : Estado actual de esta máquina
	 * @param f : Filtro donde hubo cambio.
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RenderState obtenNuevoEstado(RenderState crt, DFilter f) throws Exception {
		List<String> target = f.getFiTransition().getImagen(f.getValue());
		RenderState nState = new RenderState();
		for(DFilter df : crt.getFiltros()){
			log.info("agregando: " + df.getId());
			nState.addFilter(df);

		}
		
		//if(df.getId().equals(f.getId())){
			for(String n : target){
				DFilter ft = F(n);
				if(n.equals(ft.getId())){
					if(ft.getInitF() != null){
						ft.init(f.getValue());
					}
					if(!ft.isVisible()){
						ft.setVisible(true);
						//crt.getFiltros().add(ft);
						log.info("agregando: " + ft.getId());
						nState.addFilter(ft);
					}
				}
			}
		//}
		
		return nState;
	}
	
	public RenderState getCurrent() {
		return current;
	}

	public void setCurrent(RenderState current) {
		this.current = current;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, DFilter> getFiltersMap() {
		return filtersMap;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltersMap(Map<String, DFilter> filtersMap) {
		this.filtersMap = filtersMap;
	}
}