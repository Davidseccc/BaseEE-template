package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.*;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class HomeView {
    @Inject
    UserRepository userRepository;
    @Inject
    InvitroRepository invitroRepository;
    @Inject
    CompoundRepository compoundRepository;
    @Inject
    SynonymumRepository synonymumRepository;
    @Inject
    TargetRepository targetRepository;
    @Inject
    QuantityRepository quantityRepository;
    @Inject
    DescriptorRepository descriptorRepository;

    long userRepositorySize;
    long invitroRepositorySize;
    long compoundRepositorySize;
    long synonymumRepositorySize;
    long targetRepositorySize;
    long quantityRepositorySize;
    long descriptorRepositorySize;
    List<Compound> compounds;


    @PostConstruct
    public void init() {
        userRepositorySize = userRepository.count();
        invitroRepositorySize = invitroRepository.count();
        compoundRepositorySize = compoundRepository.count();
        synonymumRepositorySize = synonymumRepository.count();
        targetRepositorySize = targetRepository.count();
        quantityRepositorySize = quantityRepository.count();
        descriptorRepositorySize = descriptorRepository.count();
        compounds = compoundRepository.findAll();
    }

    public long getUserRepositorySize() {
        return userRepositorySize;
    }

    public void setUserRepositorySize(long userRepositorySize) {
        this.userRepositorySize = userRepositorySize;
    }

    public long getInvitroRepositorySize() {
        return invitroRepositorySize;
    }

    public void setInvitroRepositorySize(long invitroRepositorySize) {
        this.invitroRepositorySize = invitroRepositorySize;
    }

    public long getCompoundRepositorySize() {
        return compoundRepositorySize;
    }

    public void setCompoundRepositorySize(long compoundRepositorySize) {
        this.compoundRepositorySize = compoundRepositorySize;
    }

    public long getSynonymumRepositorySize() {
        return synonymumRepositorySize;
    }

    public void setSynonymumRepositorySize(long synonymumRepositorySize) {
        this.synonymumRepositorySize = synonymumRepositorySize;
    }

    public long getTargetRepositorySize() {
        return targetRepositorySize;
    }

    public void setTargetRepositorySize(long targetRepositorySize) {
        this.targetRepositorySize = targetRepositorySize;
    }

    public long getQuantityRepositorySize() {
        return quantityRepositorySize;
    }

    public void setQuantityRepositorySize(long quantityRepositorySize) {
        this.quantityRepositorySize = quantityRepositorySize;
    }

    public long getDescriptorRepositorySize() {
        return descriptorRepositorySize;
    }

    public void setDescriptorRepositorySize(long descriptorRepositorySize) {
        this.descriptorRepositorySize = descriptorRepositorySize;
    }

    public List<Compound> getCompounds() {
        return compounds;
    }

    public void setCompounds(List<Compound> compounds) {
        this.compounds = compounds;
    }
}
