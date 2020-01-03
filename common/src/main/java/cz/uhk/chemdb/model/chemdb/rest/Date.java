package cz.uhk.chemdb.model.chemdb.rest;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

/**
 * "date-parts": [
 * [
 * 2004,
 * 1,
 * 21
 * ]
 * ],
 * "date-time": "2004-01-21T14:31:19Z",
 * "timestamp": 1074695479000
 */
public class Date {
    @SerializedName("date-parts")
    int[][] dateParts;
    @SerializedName("date-time")
    LocalDateTime datetime;
    long timestamp;

    public int[][] getDateParts() {
        return dateParts;
    }

    public void setDateParts(int[][] dateParts) {
        this.dateParts = dateParts;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
