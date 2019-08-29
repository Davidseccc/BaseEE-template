package cz.uhk.chemdb.model.chemdb.repositories;

import cz.uhk.chemdb.model.chemdb.table.User;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends EntityRepository<User, String> {

    Optional<User> findOptionalByEmailAndPasswordAndDeletedAtIsNull(String email, String password);

    List<User> findByDeletedAtIsNull();

    List<User> findBySendNotification(boolean sendNotification);

    User findOptionalByEmail(String email);

    User findOptionalById(Long id);

    @Query(value = "select r from User r where r.deletedAt = null and r.token = ?1", max = 1, singleResult = SingleResultType.OPTIONAL)
    User findResearcherWithToken(String token);
}