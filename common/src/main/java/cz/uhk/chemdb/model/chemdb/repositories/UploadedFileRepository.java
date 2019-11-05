package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.UploadedFile;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface UploadedFileRepository extends EntityRepository<UploadedFile, Long> {

    UploadedFile findOptionalByUuid(String uuid);


}
