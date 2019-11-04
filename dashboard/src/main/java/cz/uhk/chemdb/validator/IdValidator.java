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
public class IdValidator extends BaseValidator implements Validator {
    public static final String K_DATA_VALID_ID = "K\\d{1,4}";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String id = (String) value;

        if (StringUtils.isEmpty(id)) {
            throw new ValidatorException(new FacesMessage("Atribut Id je povinný"));
        }
        if (!id.matches(K_DATA_VALID_ID)) {
            throw new ValidatorException(new FacesMessage("Id musí mít formát K###"));
        }
    }

    @Override
    public boolean isValid(String id) {
        return id.matches("K\\d{1,4}");
    }


}
