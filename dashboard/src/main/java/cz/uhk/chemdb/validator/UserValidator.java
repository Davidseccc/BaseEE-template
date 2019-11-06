package cz.uhk.chemdb.validator;

import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.utils.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UserValidator implements Validator {

    @Inject
    UserRepository userRepository;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;

        if (!StringUtils.validateEmail(email)) {
            throw new ValidatorException(new FacesMessage("Zadejte validní email"));
        }
        String oldEmail = (String) component.getAttributes().get("oldEmail");

        if ((oldEmail == null || !oldEmail.equals(email)) && userRepository.findOptionalByEmail(email) != null) {
            throw new ValidatorException(new FacesMessage("Výzkumník se zvoleným emailem již existuje"));
        }
    }
}
