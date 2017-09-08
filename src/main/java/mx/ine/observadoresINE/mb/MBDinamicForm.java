package mx.ine.observadoresINE.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.common.enums.EnumAmbitoSistema;
import mx.ine.observadoresINE.bsd.BSDServiciosGeneralesInterface;
import mx.ine.observadoresINE.dto.dinamicForm.DFilter;
import mx.ine.observadoresINE.dto.dinamicForm.DinamicFormBuilder;
import mx.ine.observadoresINE.dto.dinamicForm.FiTransition;
import mx.ine.observadoresINE.dto.dinamicForm.RenderMachine;
import mx.ine.observadoresINE.dto.dinamicForm.RenderState;
import mx.ine.observadoresINE.dto.dinamicForm.filters.CalendarFilter;
import mx.ine.observadoresINE.dto.dinamicForm.filters.InputTextFilter;
import mx.ine.observadoresINE.dto.dinamicForm.filters.SelectOneMenuFilter;
import mx.ine.observadoresINE.dto.dinamicForm.filters.SelectOneRadio;
import mx.org.ine.servicios.dto.db.DTODistrito;
import mx.org.ine.servicios.dto.db.DTOEstado;
import mx.org.ine.servicios.dto.db.DTOMunicipio;

public class MBDinamicForm extends MBGeneric implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1370419532799184543L;

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(MBDinamicForm.class);

	/**
	 * Objeto bsd para los servicios generales usados en el módulo
	 */
	@Autowired
	@Qualifier("bsdServiciosGenerales")
	private transient BSDServiciosGeneralesInterface bsdServicios;
	
	private RenderMachine machine;
	
	private String prueba;
	

	
	@SuppressWarnings("rawtypes")
	public void init(){
		
		log.info("MBDinamicForm");
//		
//		// Filtros
//		InputTextFilter<String> A = new InputTextFilter<>("A", "Filtro A");
//		CalendarFilter<Date> B = new CalendarFilter<>("B", "Filtro B");
//		InputTextFilter<String> C = new InputTextFilter<>("C", "Filtro C");
//		CalendarFilter<Date> D = new CalendarFilter<>("D", "Filtro D");
//		SelectOneMenuFilter<String, List<String>> E = new SelectOneMenuFilter<>("E", "Filtro E"); 
//		SelectOneRadio<String, String> F = new SelectOneRadio<>("F", "Filtro F");
//		
//		
//		
//		// Transiciones del componente A
//		FiTransition<String> fiA = new FiTransition<>();
//		fiA.when("1", "B,C");
//		fiA.any("D");
//		A.setFiTransition(fiA);
//		
//		FiTransition<Date> fiB = new FiTransition<>();
//		B.setFiTransition(fiB);
//		
//		
//		FiTransition<String> fiC = new FiTransition<>();
//		fiC.when("1", "D");
//		C.setFiTransition(fiC);
//		
//		FiTransition<Date> fiD = new FiTransition<>();
//		fiD.any("E");
//		D.setFiTransition(fiD);
//		D.setInitF(v -> {
//			D.setValue(null);
//			return new Date();
//		});
//
//		FiTransition<String> fiE = new FiTransition<>();
//		E.setFiTransition(fiE);
//		E.setInitF(v -> {
//			List<String> l = new ArrayList<String>();
//			l.add("1");
//			l.add("2");
//			return l;
//		});
//		
//		
//		FiTransition<String> fiF = new FiTransition<>();
//		F.setFiTransition(fiF);
//		F.setInitF(v -> {
//			List<String> l = new ArrayList<String>();
//			l.add("Sí");
//			l.add("No");
//			return l;
//		});
//		
//		
//		List<DFilter> allFilters = new ArrayList<DFilter>();
//		allFilters.add(A);
//		allFilters.add(B);
//		allFilters.add(C);
//		allFilters.add(D);
//		allFilters.add(E);
//		allFilters.add(F);
//		
//		List<DFilter> initialState = new ArrayList<DFilter>();
//
//		initialState.add(A);
//		initialState.add(F);
//		RenderState initial = new RenderState(initialState);
//		machine = new RenderMachine(initial);
//		machine.setAllFilters(allFilters);
		
		test();
		
	}
	
	public void test(){
		SelectOneMenuFilter<String, DTOEstado> A = new SelectOneMenuFilter<String, DTOEstado>("A", "Entidades Federativas");
		A.setInitF(v -> {
			List<DTOEstado> edos = new ArrayList<DTOEstado>();
			try {
				edos = bsdServicios.obtenEstados();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return edos;
		});
		A.setLabelFunction(edo -> {
			return edo.getIdEstado() + ".-" + edo.getNombreEstado();
		});
		A.setValueFunction(edo -> {
			return edo.getIdEstado() + "";
		});
		FiTransition<String> fiA = new FiTransition<>();
//		fiA.when("1", "C");
		fiA.any("B, C");
		A.setFiTransition(fiA);
		
		
		
		SelectOneMenuFilter<String, DTOMunicipio> B = new SelectOneMenuFilter<>("B", "Municipios");
		
		B.setInitF(v -> {
			B.setValue(null);
			List<DTOMunicipio> dttos = new ArrayList<DTOMunicipio>();
			try {
				dttos = bsdServicios.obtenMunicipios(Integer.valueOf((String) v), EnumAmbitoSistema.F);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dttos;
		});
		B.setLabelFunction(dtto -> {
			return dtto.getIdMunicipio() + ".-" + dtto.getNombreMunicipio();
		});
		B.setValueFunction(dtto -> {
			return dtto.getIdMunicipio() + "";
		});
		
		FiTransition<String> fiB = new FiTransition<>();
		B.setFiTransition(fiB);
		
		
		SelectOneRadio<String, String> C = new SelectOneRadio<>("C", "Filtro F");
		FiTransition<String> fiC = new FiTransition<>();
		C.setFiTransition(fiC);
		C.setInitF(v -> {
			List<String> l = new ArrayList<String>();
			l.add("Sí");
			l.add("No");
			return l;
		});
		
		
		List<DFilter> allFilters = new ArrayList<DFilter>();
		allFilters.add(A);
		allFilters.add(B);
		allFilters.add(C);
		
		List<DFilter> initialFilters = new ArrayList<DFilter>();
		initialFilters.add(A);
		
		DinamicFormBuilder builder = new DinamicFormBuilder(allFilters, initialFilters);
		try {
			if(builder.validateDefinition()){
				log.info("Bien definido!");
				machine = builder.getMagia();
			} else {
				log.error("Error de definición");
				log.error(builder.getErrores());
			}
		} catch (Exception e){
			log.error(e);
			log.error("Ocurrió un error inesperado al verificar definición de formulario.");
			log.error(builder.getErrores());
		}
		
		
	}
	
	public RenderMachine getMachine() {
		return machine;
	}

	public void setMachine(RenderMachine machine) {
		this.machine = machine;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
}