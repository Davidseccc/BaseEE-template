package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.PasswordHasher;
import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.User;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.utils.StringUtils;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

@Named
@ViewScoped
public class EditUserDialog implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private UserRepository researcherRepository;

    @Inject
    private PasswordHasher passwordHasher;

    private User researcher;
    private boolean newResearcher;

    private String oldEmail;

    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;

    public static void openDialog(User researcher) {
        DialogUtils.openPageAsDialog("dialog/editUser", researcher.getEmail());
    }

    public static void openDialog() {
        DialogUtils.openPageAsDialog("dialog/editUser");
    }

    @PostConstruct
    public void init() {
        String email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (email != null) {
            researcher = researcherRepository.findOptionalByEmail(email);
            oldEmail = researcher.getEmail();
        } else {
            researcher = new User();
            newResearcher = true;
        }
    }

    public boolean isNewResearcher() {
        return newResearcher;
    }

    public boolean isCurrentResearcher() {
        return !newResearcher && oldEmail.equals(userManager.getUserEmail());
    }

    @Transactional
    public void generateTokenAndShow() {
        if (!newResearcher) {
            User unchangedResearcher = researcherRepository.findOptionalByEmail(oldEmail);
            researcherRepository.save(researcher);
        }
    }

    @Transactional
    public void saveResearcherAndClose() {
        if (!newResearcher && !passwordHasher.hashPassword(oldPassword, oldEmail).equals(researcher.getPassword())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Špatné heslo", "Bylo zadáno špatné současné heslo.");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }

        if (!StringUtils.isEmpty(newPassword)) {
            if (newPassword.length() < 6) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Příliš krátké heslo", "Nové heslo je příliš krátké. Heslo musí obsahovat minimálně 6 znaků.");
                PrimeFaces.current().dialog().showMessageDynamic(message);
                return;
            }

            if (newPasswordAgain == null || !newPasswordAgain.equals(newPassword)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Hesla se neshodují", "Zadaná nová hesla se neshodují.");
                PrimeFaces.current().dialog().showMessageDynamic(message);
                return;
            }

            researcher.setPassword(passwordHasher.hashPassword(newPassword, researcher.getEmail()));
        } else if (!oldEmail.equals(researcher.getEmail())) {
            researcher.setPassword(passwordHasher.hashPassword(oldPassword, researcher.getEmail()));
            userManager.setUserEmail(researcher.getEmail());
        }

        researcherRepository.save(researcher);
        close();
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public User getResearcher() {
        return researcher;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }
}