package com.jarurat.chatbot.model;

public class MessageLog {

    private long id;
    private String from;
    private String receivedMessage;
    private String botReply;
    private String receivedAt;

    public MessageLog() {}

    public MessageLog(long id, String from, String receivedMessage, String botReply, String receivedAt) {
        this.id = id;
        this.from = from;
        this.receivedMessage = receivedMessage;
        this.botReply = botReply;
        this.receivedAt = receivedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getReceivedMessage() { return receivedMessage; }
    public void setReceivedMessage(String receivedMessage) { this.receivedMessage = receivedMessage; }

    public String getBotReply() { return botReply; }
    public void setBotReply(String botReply) { this.botReply = botReply; }

    public String getReceivedAt() { return receivedAt; }
    public void setReceivedAt(String receivedAt) { this.receivedAt = receivedAt; }
}
