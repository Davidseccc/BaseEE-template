package cz.uhk.chemdb.security;

import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.User;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

@ApplicationScoped
@ResearcherSecured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ResearcherAuthenticationFilter extends BaseAuthenticationFilter {

    @Inject
    private UserRepository userRepository;

    @Override
    protected Principal getUserPrincipal(String token) {
        User user = userRepository.findResearcherWithToken(token);

        if (user == null) {
            return null;
        }

        return user::getEmail;
    }
}