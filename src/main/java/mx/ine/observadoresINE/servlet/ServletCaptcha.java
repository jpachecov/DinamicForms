/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.ine.observadoresINE.servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

/**
 *
 * @author IFE
 */
public class ServletCaptcha extends HttpServlet {
        
    private static final long serialVersionUID = -4606332809029385682L;

    private static final String PARAM_HEIGHT = "height";
    
    private static final String PARAM_WIDTH  = "width";
    
    private static final String PARAM_LENGTH = "length";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer width;
        Integer height;
        Integer length;

        if (getInitParameter(PARAM_WIDTH) != null) {
            try {
                width = Integer.valueOf(getInitParameter(PARAM_WIDTH));
            } catch (NumberFormatException e) {
                width = 200;
            }
        } else {
            width = 200;
        }

        if (getInitParameter(PARAM_HEIGHT) != null) {
            try {
                height = Integer.valueOf(getInitParameter(PARAM_HEIGHT));
            } catch (NumberFormatException e) {
                height = 50;
            }
        } else {
            height = 50;
        }

        if (getInitParameter(PARAM_LENGTH) != null) {
            try {
                length = Integer.valueOf(getInitParameter(PARAM_LENGTH));
            } catch (NumberFormatException e) {
                length = 6;
            }
        } else {
            length = 6;
        }
        
    	Captcha captcha;
    	Captcha.Builder captchaBuilder = new Captcha.Builder(width, height);
    	
    	//Letras y números permitidas
    	char[] letrasCaptcha = new char[] { 'a', 'b', 'c', 'd','e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y',
    			'2', '3', '4', '5', '6', '7', '8', '9'};
    
    	//azul, negro, cafe, rojo, verde 
    	List<java.awt.Color> textColors = Arrays.asList(new Color(0,0,64), Color.black, new Color(66,36,0),
    			new Color(64,0,0), new Color(0,51,26));
    	
    	//Tipos de letra
    	List<java.awt.Font> textFonts = Arrays.asList(new Font("Arial", Font.BOLD, 38), new Font("Arial", Font.ITALIC, 38),
    			new Font("Arial", Font.ITALIC, 40), new Font("Arial", Font.PLAIN, 38), new Font("Arial", Font.PLAIN, 40));
    	
    	//Asignación de longitud de letras y colores asignados al captcha
    	captchaBuilder.addText( new DefaultTextProducer(length,letrasCaptcha), new DefaultWordRenderer(textColors, textFonts) );
    	captchaBuilder.addBorder();
    	captchaBuilder.addBackground( new GradiatedBackgroundProducer() );
    	captchaBuilder.addNoise( new CurvedLineNoiseProducer(Color.BLACK,1) );
    
    	captcha = captchaBuilder.build();
    
    	//Asignación de captcha a sesión para poder replicar en cluster
         req.getSession().setAttribute(Captcha.NAME, captcha);
         CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }
    
}
