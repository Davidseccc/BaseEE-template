package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.Attribute;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AttributeRepositiry extends EntityRepository<Attribute, Long> {

    @Override
    @Query("SELECT a from Attribute a where a.deleted = false")
    List<Attribute> findAll();

    Set<Attribute> findByDeletedAtIsNullOrderByIdAsc();

    @Query("SELECT a from Attribute a where a.compound.id = ?1 and a.deleted = false")
    Set<Attribute> findByCompound(Long CompoundId);

}
