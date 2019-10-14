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
public class PurityValidator extends BaseValidator implements Validator {
    public static final String PURITY_PATTERN = "(?>\\>|\\<)?\\d{1,3}(?>\\.|\\,)?\\d{1,2}";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String purity = (String) value;

        if (StringUtils.isEmpty(purity)) {
            return;
        }
        if (!purity.matches(PURITY_PATTERN)) {
            throw new ValidatorException(new FacesMessage("Purity neodpovídá zadanému formátu"));
        }
    }

    @Override
    public boolean isValid(String purity) {
        return purity.matches("(?>\\>|\\<)?\\d{1,4}(?>\\.|\\,)?\\d{1,2}");
    }
}
