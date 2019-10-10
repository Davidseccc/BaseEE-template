package cz.uhk.chemdb.converter;

import cz.uhk.chemdb.model.chemdb.repositories.AttributeTypeRepository;
import cz.uhk.chemdb.model.chemdb.table.AttributeType;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AttributeTypeConverter implements Converter {

    @Inject
    AttributeTypeRepository attributeTypeRepository;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return attributeTypeRepository.findByName(value);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid AttributeType."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        System.out.println("Convert" + object.toString());
        if (object instanceof AttributeType) {
            return object.toString();
        } else {
            return null;
        }
    }
}