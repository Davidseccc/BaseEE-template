package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Log;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;


@Repository
public interface LogRepository extends EntityRepository<Log, Long> {
}
