package com.jarurat.chatbot.controller;

import com.jarurat.chatbot.model.BotResponse;
import com.jarurat.chatbot.model.IncomingMessage;
import com.jarurat.chatbot.model.MessageLog;
import com.jarurat.chatbot.service.ChatbotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ChatbotService chatbotService;

    public WebhookController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<BotResponse> receiveMessage(@Valid @RequestBody IncomingMessage incomingMessage) {
        logger.info("Webhook triggered - From: {}, Message: \"{}\"",
                incomingMessage.getFrom(), incomingMessage.getMessage());

        String reply = chatbotService.generateReply(
                incomingMessage.getFrom(),
                incomingMessage.getMessage()
        );

        BotResponse response = BotResponse.success(incomingMessage.getFrom(), reply);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getLogs() {
        List<MessageLog> logs = chatbotService.getAllLogs();
        Map<String, Object> responseBody = Map.of(
                "total_messages", chatbotService.getTotalMessageCount(),
                "logs", logs
        );
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "WhatsApp Chatbot - Jarurat Care",
                "version", "1.0.0"
        ));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        logger.warn("Validation error: {}", errorMsg);
        return ResponseEntity.badRequest().body(Map.of("error", errorMsg));
    }
}