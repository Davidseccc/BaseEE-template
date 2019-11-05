package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.parser.InvitroExcelParser;
import cz.uhk.chemdb.model.chemdb.parser.KDataExcelParser;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.table.FileUploadType;
import cz.uhk.chemdb.model.chemdb.table.Owner;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.LogUtils;
import cz.uhk.chemdb.utils.ObprpService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ImportDataDialogView implements Serializable {

    @Inject
    OwnerRepositiry ownerRepositiry;
    @Inject
    LogUtils logUtils;
    @Inject
    UserManager userManager;

    @Inject
    ObprpService obprpService;

    @Inject
    CompoundRepository compoundRepository;

    List<KDataExcelParser.KDatabaseDTO> kDatabaseDTOS;
    InvitroExcelParser.Invitro invitro;

    List<String> owners;

    @PostConstruct
    public void init() {
        kDatabaseDTOS = new ArrayList<>();
        owners = new ArrayList<>();
        for (Owner owner : ownerRepositiry.findAll()) {
            owners.add(owner.getName());
        }
    }

    public void openDialog(String fileHash, FileUploadType uploadType) {
        String dialogName;

        if (uploadType == FileUploadType.K_DATA) {
            dialogName = "dialog/importKDataDialog";
        } else if (uploadType == FileUploadType.INVITRO_DATA) {
            dialogName = "dialog/importInviotroDialog";
        } else {
            dialogName = "dialog/error";
        }
        DialogUtils.openPageAsDialog(dialogName, "fileHash", fileHash);
    }
}
