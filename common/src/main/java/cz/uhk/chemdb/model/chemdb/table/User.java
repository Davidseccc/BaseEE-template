package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "chemuser")
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    private String name;

    @NotNull
    private String password;

    /**
     * Token for User API access
     */
    private String token;

    private LocalDateTime deletedAt;

    private boolean admin;

    private boolean superAdmin;

    private boolean contributor;

    public User() {
    }

    public User(String email, @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isAdmin() {
        return admin || superAdmin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    @Override
    public String toString() {
        return email;
    }

}