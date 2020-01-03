package cz.uhk.chemdb.model.chemdb.rest;

import com.google.gson.annotations.SerializedName;

public class PartialDate {
    @SerializedName("date-parts")
    int[][] dateParts;

    public int[][] getDateParts() {
        return dateParts;
    }

    public void setDateParts(int[][] dateParts) {
        this.dateParts = dateParts;
    }
}
