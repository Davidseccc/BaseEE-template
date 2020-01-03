package cz.uhk.chemdb.model.chemdb.rest;

import com.google.gson.annotations.SerializedName;

public class Work {
    String publisher;//	String	Yes	Name of work's publisher
    String[] title;//Array of String	Yes	Work titles, including translated titles
    @SerializedName("original-title")
    String[] originalTitle;//	Array of String	No	Work titles in the work's original publication language
    @SerializedName("short-title")
    String[] shortTitle;//Array of String	No	Short or abbreviated work titles
    @SerializedName("abstract")
    String artAbstract;//	XML String	No	Abstract as a JSON string or a JATS XML snippet encoded into a JSON string
    @SerializedName("reference-count")
    int referenceCount;//	Number	Yes	Deprecated Same as references-count
    @SerializedName("references-count")
    int referencesCount;//	Number	Yes	Count of outbound references deposited with Crossref
    @SerializedName("is-referenced-by-count")
    int isReferencedByCount;    //Number	Yes	Count of inbound references deposited with Crossref
    String source;//	String	Yes	Currently always Crossref
    String prefix; //	String	Yes	DOI prefix identifier of the form http://id.crossref.org/prefix/DOI_PREFIX
    String DOI;    //String	Yes	DOI of the work
    String URL;//	URL	Yes	URL form of the work's DOI
    String member;//	String	Yes	Member identifier of the form http://id.crossref.org/member/MEMBER_ID
    String type;    //String	Yes	Enumeration, one of the type ids from https://api.crossref.org/v1/types
    Date created;//	Date	Yes	Date on which the DOI was first registered
    Date deposited;//	Date	Yes	Date on which the work metadata was most recently updated
    Date indexed;//	Date	Yes	Date on which the work metadata was most recently indexed. Re-indexing does not imply a metadata change, see deposited for the most recent metadata change date
    PartialDate issued;//	Partial Date	Yes

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String getTitleString() {
        if (title.length > 0) {
            return title[0];
        }
        return null;
    }

    public String[] getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String[] originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String[] getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String[] shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getArtAbstract() {
        return artAbstract;
    }

    public void setArtAbstract(String artAbstract) {
        this.artAbstract = artAbstract;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public int getReferencesCount() {
        return referencesCount;
    }

    public void setReferencesCount(int referencesCount) {
        this.referencesCount = referencesCount;
    }

    public int getIsReferencedByCount() {
        return isReferencedByCount;
    }

    public void setIsReferencedByCount(int isReferencedByCount) {
        this.isReferencedByCount = isReferencedByCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDOI() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeposited() {
        return deposited;
    }

    public void setDeposited(Date deposited) {
        this.deposited = deposited;
    }

    public Date getIndexed() {
        return indexed;
    }

    public void setIndexed(Date indexed) {
        this.indexed = indexed;
    }

    public PartialDate getIssued() {
        return issued;
    }

    public void setIssued(PartialDate issued) {
        this.issued = issued;
    }
}
