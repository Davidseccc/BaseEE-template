package cz.uhk.chemdb.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NmrValidator extends BaseValidator {

    @Override
    public boolean isValid(String string) {
        return super.isValid(string);
    }
}
