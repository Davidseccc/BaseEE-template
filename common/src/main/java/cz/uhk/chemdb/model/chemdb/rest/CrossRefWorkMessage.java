package cz.uhk.chemdb.model.chemdb.rest;

import com.google.gson.annotations.SerializedName;

/**
 * "status": "ok",
 * "message-type": "work",
 * "message-version": "1.0.0",
 * "message": {}
 */
public class CrossRefWorkMessage {
    String status;
    @SerializedName("message-type")
    MessageType messageType;
    @SerializedName("message-version")
    String messageVersion;
    Work message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public Work getMessage() {
        return message;
    }

    public void setMessage(Work message) {
        this.message = message;
    }

    public enum MessageType {
        WORK(1, "work");

        int id;
        String name;

        MessageType(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
