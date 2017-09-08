/**
 * @(#)JaxbContextHelperFactory.java 08/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import mx.ine.common.util.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase que genera la instancia del objeto que obtiene el contexto de JAXB
 *
 * @author Jos&eacute; Carlos Ortega Romano
 * @copyright Direcci&oacute;n de sistemas - INE
 * @since 08/09/2016
 */
public class JaxbContextHelperFactory {
    /**
     * Constructor privado
     *
     * @author Jos&eacute; Carlos Ortega Romano
     * @since 08/09/2016
     */
    private JaxbContextHelperFactory() {
        // Constructor privado utilizado para evitar la creaci&oacute;n de instancias.
    }

    /**
     * Metodo que genera la instancia del objeto que obtiene el contexto de JAXB
     *
     * @param classesToBeBound Lista de clases a mapear.
     *
     * @return el objeto que obtiene el contexto de JAXB.
     *
     * @author Jos&eacute; Carlos Ortega Romano
     * @since 08/09/2016
     */
    public static JaxbUtil.JaxbContextHelper getJaxbContextHelper(Class<?>[] classesToBeBound) {
        return new SijeJaxbContextHelper(classesToBeBound);
    }

    /**
     * Clase privada que genera el contexto de JAXB con las clases propias utilizadas en el proyecto
     *
     * @author Jos&eacute; Carlos Ortega Romano
     * @since 08/09/2016
     */
    private static class SijeJaxbContextHelper implements JaxbUtil.JaxbContextHelper {

        private static final Log LOGGER = LogFactory.getLog(JaxbUtil.JaxbContextHelper.class);

        /**
         * Contexto de JAXB
         */
        private JAXBContext context;

        /**
         * Clases que seran mapeadas en formato XML
         */
        private final Class<?>[] classesToBeBound;

        /**
         * Constructor con par&aacute;metros
         *
         * @param classesToBeBound Lista de clases a mapear
         *
         * @author Jos&eacute; Carlos Ortega Romano
         * @since 19/10/2016
         */
        SijeJaxbContextHelper(Class<?>[] classesToBeBound) {
            this.classesToBeBound = classesToBeBound;
        }

        /**
         * Metodo que obtiene el contexto de JAXB
         *
         * @author Jos&eacute; Carlos Ortega Romano
         * @since 08/09/2016
         */
        @Override
        public JAXBContext getInstance() {
            if (context == null) {
                try {
                    context = JAXBContext.newInstance(classesToBeBound);
                } catch (JAXBException e) {
                    LOGGER.error("Error al generar el contexto de Jaxb", e);
                }
            }

            return context;
        }
    }
}
