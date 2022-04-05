package se.iths;

import java.time.LocalTime;

public class HttpError {
    private LocalTime timestamp;
    private int status;
    private String message;

    public HttpError() {
    }

    public HttpError(int status, String message) {
        this.timestamp = LocalTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\n" +
                "timestamp:" + timestamp +
                ",\n status:" + status +
                ",\n message:'" + message +
                "\n'}'";
    }
}