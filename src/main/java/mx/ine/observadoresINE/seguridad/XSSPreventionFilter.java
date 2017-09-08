package mx.ine.observadoresINE.seguridad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
 



import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

 
/**
 * Filters Http requests and removes malicious characters/strings
 * (i.e. XSS) from the Query String
 */
public class XSSPreventionFilter implements Filter {
	
	Log LOGGER = LogFactory.getLog(XSSPreventionFilter.class);
	
	class XSSRequestWrapper extends HttpServletRequestWrapper {

	    
	    
		private Map<String, String[]> sanitizedQueryString;
		
		public XSSRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		

		
		@Override
	    public String getParameter(String parameter) {
	        String value = super.getParameter(parameter);
	        return stripXSS(value);
	    }
		
		
		public String[] getParameterValues(String parameter) {
			LOGGER.debug("entra parameterValues");
		    String[] values = super.getParameterValues(parameter);
		    
		    if (values == null) {
		        return null;
		    }
		    int count = values.length;
		    String[] encodedValues = new String[count];
		    for (int i = 0; i < count; i++) {
		        encodedValues[i] = stripXSS(values[i]);
		    }
		    return encodedValues;
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(getParameterMap().keySet());
		}
		
		@Override
		public Map<String,String[]> getParameterMap() {
			if(sanitizedQueryString == null) {
				Map<String, String[]> res = new HashMap<String, String[]>();
				Map<String, String[]> originalQueryString = super.getParameterMap();
				if(originalQueryString!=null) {
					for (String key : (Set<String>) originalQueryString.keySet()) {
						String[] rawVals = originalQueryString.get(key);
						String[] snzVals = new String[rawVals.length];
						for (int i=0; i < rawVals.length; i++) {
							snzVals[i] = stripXSS(rawVals[i]);
							LOGGER.debug("Sanitized: " + rawVals[i] + " to " + snzVals[i]+" key: "+key);
						}
						res.put(stripXSS(key), snzVals);
					}
				}
				sanitizedQueryString = res;
			}
			return sanitizedQueryString;
		}
		 
	    @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return stripXSS(value);
	    }
		    
		/**
		 * Removes all the potentially malicious characters from a string
		 * @param value the raw string
		 * @return the sanitized string
		 */
		private String stripXSS(String value) {
			String cleanValue = null;
			if (value != null) {
//				cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
				cleanValue = value;
 
				// Avoid null characters
				cleanValue = cleanValue.replaceAll("\0", "");
				
				// Avoid anything between script tags
				Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid anything in a src='...' type of expression
				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Remove any lonesome </script> tag
				scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				// Remove any lonesome <script ...> tag
				scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				// Avoid eval(...) expressions
				scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid expression(...) expressions
				scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid javascript:... expressions
				scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid vbscript:... expressions
				scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid onload= expressions
				scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid scriptalert expressions
				scriptPattern = Pattern.compile("<scriptalert(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				//Avoid script expressions
				scriptPattern = Pattern.compile("script", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				//Avoid script expressions
				scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				scriptPattern = Pattern.compile("[.*]/", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				scriptPattern = Pattern.compile(";", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");					
				
			}
			return cleanValue;
		}
	}
 
	public void destroy() {
		LOGGER.debug("XSSPreventionFilter: destroy()");
	}
 

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
		XSSRequestWrapper wrapper = new XSSRequestWrapper((HttpServletRequest)request);	
        String uriAux = wrapper.getRequestURI();
        String uri = wrapper.stripXSS(uriAux);
        if(uriAux != null && uri != null){
	        if(!uriAux.equalsIgnoreCase(uri)){
	    		try {
	    			if(!res.isCommitted()){
	    				res.resetBuffer();
	        			res.setStatus(400);
	            		PrintWriter out = res.getWriter();
	        		    res.setContentType("application/json");
	        		    out.println("{\"Error\": \"Ocurri\u00F3 un error inesperado\",");
	        		    out.println("\"Mensaje\": \"Se encontraron simbolos no permitidos.\"}");
	        		    out.flush();
	        		    out.close();
	        		    res.setContentType(out.toString());  
	    			}
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
        }
		chain.doFilter(wrapper, response);
	}
 

	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("XSSPreventionFilter: init()");
	}
}