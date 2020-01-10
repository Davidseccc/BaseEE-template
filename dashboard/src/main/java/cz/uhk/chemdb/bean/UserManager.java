package cz.uhk.chemdb.bean;

import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.User;
import cz.uhk.chemdb.utils.PermissionAttribute;
import cz.uhk.chemdb.utils.PermissionRole;
import cz.uhk.chemdb.utils.UserPermission;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Named
@SessionScoped
public class UserManager implements Serializable {

    private static final String HOME_PAGE_REDIRECT =
            "/secured/home.xhtml?faces-redirect=true";

    private static final String LOGIN_PAGE_REDIRECT =
            "/login.xhtml?faces-redirect=true";

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordHasher passwordHasher;

    private String userEmail;
    private String userPassword;
    private User currentUser;
    private Set<PermissionAttribute> userPermissions;

    public String login() {
        // lookup the user based on user name and user password
        Optional<User> user = findUser(userEmail, userPassword);

        if (user.isPresent()) {
            currentUser = user.get();
            userPermissions = UserPermission.setPermissions(currentUser);
            return HOME_PAGE_REDIRECT;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Nesprávné přihlašovací údaje",
                            null));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return LOGIN_PAGE_REDIRECT;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    public String isNotSuperAdminForwardHome() {
        if (currentUser == null || !currentUser.isSuperAdmin()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    public boolean hasPermission(PermissionAttribute attribute) {
        return attribute != null && userPermissions.contains(attribute);
    }

    public PermissionRole getUserRole() {
        if (currentUser.isSuperAdmin()) {
            return PermissionRole.SUPER_ADMIN;
        } else if (currentUser.isAdmin()) {
            return PermissionRole.ADMIN;
        } else if (currentUser.isContributor()) {
            return PermissionRole.CONTRIBUTOR;
        } else {
            return PermissionRole.USER;
        }
    }

    private Optional<User> findUser(String userEmail, String password) {
        String hashedPassword = passwordHasher.hashPassword(password, userEmail);
        return userRepository.findOptionalByEmailAndPasswordAndDeletedAtIsNull(userEmail, hashedPassword);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
