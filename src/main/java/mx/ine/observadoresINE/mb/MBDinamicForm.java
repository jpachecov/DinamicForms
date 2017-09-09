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
		fiA.any("B");
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
		
		
		SelectOneRadio<String, String> C = new SelectOneRadio<>("C", "Sexo");
		FiTransition<String> fiC = new FiTransition<>();
		C.setFiTransition(fiC);
		C.setInitF(v -> {
			List<String> l = new ArrayList<String>();
			l.add("H");
			l.add("M");
			return l;
		});
		C.setLabelFunction(s -> {
			if(s.equals("H")){
				return "Hombre";
			}
			return "Mujer";
		});
		
		
		CalendarFilter<Date> fecha = new CalendarFilter<>("D", "Fecha de nacimiento");
		FiTransition<Date> fiFecha = new FiTransition<>();
		fecha.setFiTransition(fiFecha);
		
		
		InputTextFilter<String> nombre = new InputTextFilter<>("F", "Nombre completo");
		FiTransition<String> fiNombre = new FiTransition<>();
		nombre.setFiTransition(fiNombre);
		
		
		List<DFilter> allFilters = new ArrayList<DFilter>();
		allFilters.add(C);
		allFilters.add(fecha);
		allFilters.add(A);
		allFilters.add(B);
		allFilters.add(nombre);

		
		List<DFilter> initialFilters = new ArrayList<DFilter>();
		initialFilters.add(nombre);
		initialFilters.add(C);
		initialFilters.add(fecha);
		initialFilters.add(A);
		
		
		for(DFilter s : allFilters){
			s.setRequired(true);
		}
		
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
	
	public void doSomething() {
		log.info("DoSomething");
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