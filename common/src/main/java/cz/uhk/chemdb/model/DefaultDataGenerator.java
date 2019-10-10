package cz.uhk.chemdb.model;

import cz.uhk.chemdb.model.chemdb.builder.UserBuilder;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.Owner;
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
    OwnerRepositiry ownerRepositiry;
    @Inject
    PasswordHash passwordHash;


    public void generateDummyData() {
        System.out.println("GENERATING DATA ....");
        generateTestUsers();
        generateOwners();
    }

    @Transactional
    private void generateTestUsers() {
        User admin = UserBuilder.createUserBuilder("admin", passwordHash.getPasswordHash(
                "admin", "admin")).setName("Administrator").setAdmin(true)
                .setSuperAdmin(true).setToken("demo").build();
        userRepository.saveAndFlush(admin);
    }

    private void generateOwners() {
        Owner owner = new Owner();
        owner.setName("UHK");
        ownerRepositiry.save(owner);
    }


}
