package com.jarurat.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class IncomingMessage {

    @NotBlank(message = "Sender number must not be blank")
    @JsonProperty("from")
    private String from;

    @NotBlank(message = "Message must not be blank")
    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    public IncomingMessage() {}

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
