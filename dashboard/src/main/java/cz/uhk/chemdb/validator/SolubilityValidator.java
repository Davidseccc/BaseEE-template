package cz.uhk.chemdb.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@ApplicationScoped
public class SolubilityValidator extends BaseValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }

    @Override
    public boolean isValid(String string) {
        return super.isValid(string);
    }
}
