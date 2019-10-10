package cz.uhk.chemdb.model.chemdb.table;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum EventType implements Serializable {
    VIEW,
    CREATE,
    EDIT,
    DELETE,
    DOWNLOAD_ATTACHMENT,
    UPLOAD_DOCUMENT

}
