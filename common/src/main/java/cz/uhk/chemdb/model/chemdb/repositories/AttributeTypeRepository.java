package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.AttributeType;

import javax.ejb.Singleton;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Named
public class AttributeTypeRepository implements Serializable {

    public List<AttributeType> findAllAttributeTypes() {
        return new ArrayList<>(Arrays.asList(AttributeType.values()));
    }

    public AttributeType findById(int id) {
        for (AttributeType attributeType : AttributeType.values()) {
            if (attributeType.getId() == id)
                return attributeType;
        }
        return null;
    }

    public AttributeType findByName(String value) {
        for (AttributeType attributeType : AttributeType.values()) {
            if (attributeType.getName().equalsIgnoreCase(value))
                return attributeType;
        }
        return null;
    }

}
