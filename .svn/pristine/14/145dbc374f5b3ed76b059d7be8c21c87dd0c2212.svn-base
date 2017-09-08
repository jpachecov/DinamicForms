/**
 * 2017 (C) Instituto Nacional Electoral (INE).
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Esta clase implementa la interfaz <code>ApplicationContextUtils</code> que establece 
 * la funcionalidad para aceder al contexto de Spring. 
 * 
 * @author José Guadalupe Burgos Colomoxcatl
 * @since 1.0
 * @version 1.0 - 24/02/2017
 * @see ApplicationContextAware
 * 
 * @copyright INE
 */
@Component("springApplicationContextUtils")
@Scope("singleton")
public class ApplicationContextUtils implements ApplicationContextAware {

	/**
	 * Objeto que contiene la referencia al Applicaction Contex de Spring Framework.
	 */
	private static ApplicationContext applicationContext;	
	
	/**
	 * Regresa el objeto que contiene la referencia al Applicaction Contex de 
	 * Spring Framework.
	 * 
	 * @return El valor del atributo <code>applicationContext</code>.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * Establece el objeto que contiene la referencia al Applicaction Contex de 
	 * Spring Framework.
	 * 
	 * @param applicationContext Objeto que contiene la referencia al Applicaction Contex de 
	 * Spring Framework.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 ApplicationContextUtils.applicationContext = applicationContext;
	}	 

}