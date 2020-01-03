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
public class AliasValidator extends BaseValidator implements Validator {
    public static final String VALID_REGEX = "\\w{1,},?";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String aliasList = (String) value;
        if (StringUtils.isNotEmpty(aliasList) && !aliasList.trim().matches(VALID_REGEX)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alias musí být složen ze znaků abecedy a platných číslic", "Jednotlivé názvy oddělujte čárkou"));
        }
    }
}