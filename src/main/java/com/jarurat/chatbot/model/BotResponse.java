package com.jarurat.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BotResponse {

    @JsonProperty("to")
    private String to;

    @JsonProperty("reply")
    private String reply;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timestamp")
    private String timestamp;

    public BotResponse() {}

    public BotResponse(String to, String reply, String status, String timestamp) {
        this.to = to;
        this.reply = reply;
        this.status = status;
        this.timestamp = timestamp;
    }

    public static BotResponse success(String to, String reply) {
        return new BotResponse(
                to, reply, "delivered",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
