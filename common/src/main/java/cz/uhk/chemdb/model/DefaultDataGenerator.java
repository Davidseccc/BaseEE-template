package cz.uhk.chemdb.model;

import cz.uhk.chemdb.model.chemdb.builder.UserBuilder;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.User;
import cz.uhk.chemdb.model.security.PasswordHash;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DefaultDataGenerator {
    @Inject
    UserRepository userRepository;
    @Inject
    PasswordHash passwordHash;


    public void generateDummyData() {
        System.out.println("GENERATING DATA ....");
        generateTestUsers();
    }

    @Transactional
    private void generateTestUsers() {
        User admin = UserBuilder.createUserBuilder("admin@email.cz", passwordHash.getPasswordHash(
                "admin", "admin")).setName("Administrator").setAdmin(true)
                .setSuperAdmin(true).setToken("demo").build();
        userRepository.saveAndFlush(admin);

    }


}
