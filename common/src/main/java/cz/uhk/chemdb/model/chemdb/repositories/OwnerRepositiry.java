package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Owner;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;


@Repository
public interface OwnerRepositiry extends EntityRepository<Owner, Long> {

    @Query("SELECT o from Owner o where o.name = ?1")
    Owner findByName(String name);
}
