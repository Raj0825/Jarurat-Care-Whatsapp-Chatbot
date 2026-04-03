package com.jarurat.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarurat.chatbot.model.IncomingMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testWebhookWithHi() throws Exception {
        IncomingMessage msg = new IncomingMessage("+911234567890", "Hi", "2024-01-15T10:30:00");

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value(org.hamcrest.Matchers.containsString("Hello")))
                .andExpect(jsonPath("$.status").value("delivered"))
                .andExpect(jsonPath("$.to").value("+911234567890"));
    }

    @Test
    void testWebhookWithBye() throws Exception {
        IncomingMessage msg = new IncomingMessage("+911234567890", "Bye", null);

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value(org.hamcrest.Matchers.containsString("Goodbye")));
    }

    @Test
    void testWebhookWithMissingFrom() throws Exception {
        String badJson = "{\"message\": \"Hi\"}";

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testWebhookWithMissingMessage() throws Exception {
        String badJson = "{\"from\": \"+911234567890\"}";

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogsEndpoint() throws Exception {
        // Send a message first
        IncomingMessage msg = new IncomingMessage("+911234567890", "Hi", null);
        mockMvc.perform(post("/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(msg)));

        // Then check logs
        mockMvc.perform(get("/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logs").isArray())
                .andExpect(jsonPath("$.total_messages").isNumber());
    }

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void testWelcomeEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.webhook").value("POST /webhook"));
    }
}
