package com.jarurat.chatbot.service;

import com.jarurat.chatbot.model.MessageLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    private final List<MessageLog> messageLogs = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    private static final Map<String, String> REPLY_MAP = new HashMap<>();

    static {
        REPLY_MAP.put("hi",       "Hello! Welcome to Jarurat Care. How can I assist you today?");
        REPLY_MAP.put("hello",    "Hello! Welcome to Jarurat Care. How can I assist you today?");
        REPLY_MAP.put("hey",      "Hello! Welcome to Jarurat Care. How can I assist you today?");
        REPLY_MAP.put("bye",      "Goodbye! Thank you for contacting Jarurat Care. Take care!");
        REPLY_MAP.put("goodbye",  "Goodbye! Thank you for contacting Jarurat Care. Take care!");
        REPLY_MAP.put("help",     "I can help you with:\n1. Book a nurse\n2. Home care services\n3. Emergency support\nReply with the number of your choice.");
        REPLY_MAP.put("1",        "You chose: Book a Nurse. Please share your location and preferred time.");
        REPLY_MAP.put("2",        "You chose: Home Care Services. Our team will contact you shortly.");
        REPLY_MAP.put("3",        "You chose: Emergency Support. Calling our emergency line now!");
        REPLY_MAP.put("thanks",   "You're welcome! Is there anything else I can help you with?");
        REPLY_MAP.put("thank you","You're welcome! Is there anything else I can help you with?");
    }

    public String generateReply(String from, String message) {
        String trimmed = message.trim().toLowerCase();
        String reply = REPLY_MAP.getOrDefault(trimmed,
                "Sorry, I didn't understand that. Type 'help' to see available options.");

        logger.info("Message from [{}]: \"{}\" -> Reply: \"{}\"", from, message, reply);
        logMessage(from, message, reply);
        return reply;
    }

    private void logMessage(String from, String receivedMessage, String botReply) {
        MessageLog log = new MessageLog(
                idCounter.getAndIncrement(), from, receivedMessage, botReply,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        messageLogs.add(log);
    }

    public List<MessageLog> getAllLogs() {
        return new ArrayList<>(messageLogs);
    }

    public long getTotalMessageCount() {
        return idCounter.get() - 1;
    }
}
