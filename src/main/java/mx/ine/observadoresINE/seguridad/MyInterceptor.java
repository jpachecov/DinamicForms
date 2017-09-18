package mx.ine.observadoresINE.seguridad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(MyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getMethod().equals("POST")) {
			log.info("preHandle");
			log.info("request URI : " + request.getRequestURI());
			log.info("request URL : " + request.getRequestURL());
			log.info("context path: " + request.getContextPath());
//			if (request.getRequestURI().contains("Agrupaciones")) {
//				response.sendRedirect("https://localhost:8443/observadoresINE/app/error");
//				return false;
//			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (request.getMethod().equals("POST")) {
			log.info("postHandle");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
			ex.printStackTrace();
		}
		if (request.getMethod().equals("POST")) {
			log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
		}
	}
}
