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
public class OwnerValidator extends BaseValidator implements Validator {

    public static final String OWNER_PATTER = "UHK|FNHK|FVZ|NUDZ|FAF";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String tag = (String) value;

        if (StringUtils.isEmpty(tag)) {
            throw new ValidatorException(new FacesMessage("Pole je povinné"));
        }

        if (!tag.matches(OWNER_PATTER)) {
            throw new ValidatorException(new FacesMessage("Může obsahovat pouze hodnotu ze seznamu"));
        }
    }


}
