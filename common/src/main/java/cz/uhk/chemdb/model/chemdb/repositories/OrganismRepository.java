package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Organism;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface OrganismRepository extends EntityRepository<Organism, Long> {

    Organism findOptionalByName(String name);
}
