package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Compound;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface CompoundRepository extends EntityRepository<Compound, Long> {
    @Query("SELECT c from Compound c where UPPER(c.smiles) LIKE UPPER(?1) or UPPER(c.ion) like UPPER(?1) or UPPER(c.notes) like UPPER(?1)")
    List<Compound> fullTextSearch(String text);
}
