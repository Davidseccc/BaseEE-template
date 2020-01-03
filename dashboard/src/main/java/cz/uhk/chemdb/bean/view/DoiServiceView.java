package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.rest.CrossRefRestClient;
import cz.uhk.chemdb.model.chemdb.rest.CrossRefWorkMessage;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ApplicationScoped
public class DoiServiceView implements Serializable {

    @Inject
    CrossRefRestClient crossRefRestClient;
    private String doi;
    private CrossRefWorkMessage crossRefWorkMessage;

    public void openDialog(String doi) {
        DialogUtils.openPageAsDialog("dialog/DOIDialog");
        if (StringUtils.isNotEmpty(doi)) {
            this.doi = doi;
            load(doi);
        }
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public void load(String doi) {
        if (StringUtils.isNotEmpty(doi)) {
            Call<CrossRefWorkMessage> call = crossRefRestClient.getService().getWork(doi);
            try {
                Response<CrossRefWorkMessage> response = call.execute();
                if (response.code() == 200) {
                    crossRefWorkMessage = response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public CrossRefWorkMessage getCrossRefWorkMessage() {
        return crossRefWorkMessage;
    }

    public void setCrossRefWorkMessage(CrossRefWorkMessage crossRefWorkMessage) {
        this.crossRefWorkMessage = crossRefWorkMessage;
    }
}
