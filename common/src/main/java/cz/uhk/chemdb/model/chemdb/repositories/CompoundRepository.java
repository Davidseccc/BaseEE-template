package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Compound;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface CompoundRepository extends EntityRepository<Compound, Long> {
    @Query(value = "SELECT DISTINCT c.*" +
            "from public.compound as c " +
            "left outer join public.attribute as a on c.id = a.compound_id" +
            "where UPPER(c.smiles) like UPPER(?1)" +
            "or UPPER(c.ion) like UPPER(?1)" +
            "or UPPER(c.notes) like UPPER(?1)" +
            "or UPPER(a.key) like UPPER(?1)" +
            "or UPPER(a.value) like UPPER(?1)", isNative = true)
    List<Compound> fullTextSearch(String search);

    @Query("SELECT c from Compound c where c.k =?1")
    Compound findByK(int k);

    Compound findOptionalByK(int k);


    List<Compound> findByDeletedAtIsNullOrderByIdAsc();

}
