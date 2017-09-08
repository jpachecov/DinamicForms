 /**
 * @(#)BOCursos.java 06/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bo.impl;

import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOCursosPK;
import mx.ine.observadoresINE.dto.form.FormCursos;
import mx.ine.observadoresINE.enums.EnumImparticionCursos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

 /**
 * 
 * @author Emmanuel García Ysamit
 * @since 06/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Component("boCursos")
@Scope("prototype")
public class BOCursos {
	
	private static final Log logger = LogFactory.getLog(BOAgrupaciones.class);
	
	public boolean esNombreValido(DTOCursos dto) throws Exception {
		return dto.getNombre() != null && !dto.getNombre().equals("");
	}

	public boolean sonApellidosInvalidos(DTOCursos dto) throws Exception {
		return (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().equals(""))
				&& (dto.getApellidoMaterno() == null || dto.getApellidoMaterno().equals(""));
	}
	
}
