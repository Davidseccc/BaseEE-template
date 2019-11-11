package cz.uhk.chemdb.bean.view.dialog;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.bean.view.FileUploadView;
import cz.uhk.chemdb.model.chemdb.repositories.AttributeRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.AttributeTypeRepository;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.*;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.FileUploadUtils;
import cz.uhk.chemdb.util.LogUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Named
@ApplicationScoped
public class AddCompoundAttributeDialog implements Serializable {

    @Inject
    CompoundRepository compoundRepository;
    @Inject
    AttributeTypeRepository attributeTypeRepository;
    @Inject
    AttributeRepositiry attributeRepositiry;
    @Inject
    LogUtils logUtils;
    @Inject
    UserManager userManager;
    @Inject
    FileUploadUtils fileUploadUtils;
    @Inject
    FileUploadView fileUploadView;

    List<AttributeType> attributeTypeList;

    List<Attribute> attributesToDelete;
    int maxIndex = 0;
    private UploadedFile file;

    private Compound compound;
    private List<Attribute> attributes;
    private Attribute attribute;
    private String attributeName;
    private String attributeValue;
    private AttributeType selectedAttributeType;

    @PostConstruct
    public void init() {
        attributeTypeList = attributeTypeRepository.findAllAttributeTypes();
        attributes = new LinkedList<>();
        attributesToDelete = new ArrayList<>();
    }

    public void openDialog(long compoundId) {
        compound = compoundRepository.findBy(compoundId);
        if (compound != null) {
            attributes = new ArrayList<>(compound.getAttributes());
            maxIndex = getMaxIndex(attributes);
            attribute = new Attribute();
        }
        DialogUtils.openPageAsDialog("dialog/addCompoundAttribute");
    }

    @Transactional
    public void saveAndClose() {
        updateAndSaveAttributes();
        close();
    }

    @Transactional
    private void updateAndSaveAttributes() {
        for (Attribute attribute : attributes) {
            if (compound.getAttributes().contains(attribute)) {
                upadteAttribute(attribute);
            } else {
                createNewAttribute(attribute);
            }
        }
        compoundRepository.save(compound);
        logUtils.createAndSaveLog(EventType.EDIT, userManager.getCurrentUser(), LogSection.ATTRIBUTE, Arrays.toString(attributes.toArray()));
    }

    @Transactional
    private void upadteAttribute(Attribute attribute) {
        attributeRepositiry.save(attribute);
    }

    @Transactional
    private void createNewAttribute(Attribute attribute) {
        attribute.setCompound(compound);
        compound.getAttributes().add(attribute);
        attributeRepositiry.save(attribute);
    }

    public void onRowReorder(ReorderEvent event) {
        switchIndexes(event.getFromIndex(), event.getToIndex());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null && event.getFile() != null) {
            file = event.getFile();
            String fileHash = fileUploadUtils.saveUploadedFile(file);
            attributeName = (attributeName == null || attributeName.isEmpty()) ? file.getFileName() : attributeName;
            attribute = new Attribute(AttributeType.ATTACHEMENT, attributeName, fileHash, ++maxIndex);
            attributes.add(attribute);
            attribute = new Attribute();
            logUtils.createAndSaveLog(EventType.UPLOAD_DOCUMENT, userManager.getCurrentUser(), LogSection.ATTRIBUTE, fileHash);

        }
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void addToAtributes() {
        attribute = new Attribute(selectedAttributeType, attributeName, attributeValue, ++maxIndex);
        attributes.add(attribute);
        attribute = new Attribute();
    }

    private int getMaxIndex(List<Attribute> attributes) {
        int max = 0;
        for (Attribute attribute : attributes) {
            max = (max < attribute.getOrd()) ? attribute.getOrd() : max;
        }
        return max;
    }

    @Transactional
    private void switchIndexes(int fromIndex, int toIndex) {
        Attribute from = attributes.get(fromIndex);
        Attribute to = attributes.get(toIndex);
        int pom = to.getOrd();
        to.setOrd(from.getOrd());
        from.setOrd(pom);
        attributeRepositiry.save(from);
        attributeRepositiry.save(to);
    }

    public void removeAttribute(Attribute attribute) {
        attribute.setDeletedAt(LocalDateTime.now());
        attributes.remove(attribute);
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public List<AttributeType> getAttributeTypeList() {
        return attributeTypeList;
    }

    public void setAttributeTypeList(List<AttributeType> attributeTypeList) {
        this.attributeTypeList = attributeTypeList;
    }

    public AttributeType getSelectedAttributeType() {
        return selectedAttributeType;
    }

    public void setSelectedAttributeType(AttributeType selectedAttributeType) {
        this.selectedAttributeType = selectedAttributeType;
    }
}
