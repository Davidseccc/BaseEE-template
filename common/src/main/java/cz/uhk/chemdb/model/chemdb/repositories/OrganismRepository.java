package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Organism;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface OrganismRepository extends EntityRepository<Organism, Long> {

    Organism findOptionalByName(String name);

    List<Organism> findByDeletedAtIsNullOrderByIdAsc();



}
