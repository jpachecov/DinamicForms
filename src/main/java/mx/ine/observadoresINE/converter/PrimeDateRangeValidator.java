/**
 * @(#)PrimeDateRangeValidator.java 11/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.converter;

/**
 * Clase que permite hacer la comparación de dos fechas
 * @author Helaine Flores Cervantes
 * @since 11/08/2017
 * @copyright Direccion de sistemas - INE
 */
import java.util.Date;
 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
 

@FacesValidator("primeDateRangeValidator")
public class PrimeDateRangeValidator implements Validator {
     
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
         
        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
        if (startDateValue==null) {
            return;
        }
         
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)value;
        if (endDate.before(startDate)) {
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Start date may not be after end date.", null));
        }
    }
}