package cz.uhk.chemdb.validator;

import cz.uhk.chemdb.utils.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MeltingPointValidator extends BaseValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String meltingPoint = (String) value;
        if (StringUtils.isEmpty(meltingPoint)) {
            throw new ValidatorException(new FacesMessage("Melting point je povinný"));
        }

        if (meltingPoint.equalsIgnoreCase("oil")) {
            return;

        } else if (meltingPoint.matches("\\d{1,4}(?>\\.|\\,)?\\d?(?>-{1}\\d{1,4}(?>\\.|\\,)?\\d?)?")) {
            return;
        } else if (meltingPoint.matches("(?>\\>|\\<)?\\d{1,4}")) {
            return;
        } else {
            throw new ValidatorException(new FacesMessage("Melting point neodpovídá zadanému formátu"));
        }
    }

    @Override
    public boolean isValid(String meltingPoint) {
        if (meltingPoint.equalsIgnoreCase("oil")) {
            return true;
        } else if (meltingPoint.matches("\\d{1,4}(?>\\.|\\,)?\\d?(?>-{1}\\d{1,4}(?>\\.|\\,)?\\d?)?")) {
            return true;
        } else return meltingPoint.matches("(?>\\>|\\<)?\\d{1,4}");
    }
}
