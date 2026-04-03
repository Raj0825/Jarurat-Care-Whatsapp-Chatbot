package com.jarurat.chatbot;

import com.jarurat.chatbot.service.ChatbotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatbotServiceTest {

    @Autowired
    private ChatbotService chatbotService;

    @Test
    void testHiReply() {
        String reply = chatbotService.generateReply("+911234567890", "Hi");
        assertThat(reply).contains("Hello");
    }

    @Test
    void testHiLowercaseReply() {
        String reply = chatbotService.generateReply("+911234567890", "hi");
        assertThat(reply).contains("Hello");
    }

    @Test
    void testByeReply() {
        String reply = chatbotService.generateReply("+911234567890", "Bye");
        assertThat(reply).contains("Goodbye");
    }

    @Test
    void testByeLowercaseReply() {
        String reply = chatbotService.generateReply("+911234567890", "bye");
        assertThat(reply).contains("Goodbye");
    }

    @Test
    void testHelpReply() {
        String reply = chatbotService.generateReply("+911234567890", "help");
        assertThat(reply).contains("help");
    }

    @Test
    void testUnknownMessageReply() {
        String reply = chatbotService.generateReply("+911234567890", "random unknown message xyz");
        assertThat(reply).contains("didn't understand");
    }

    @Test
    void testMessageIsLogged() {
        long before = chatbotService.getTotalMessageCount();
        chatbotService.generateReply("+911234567890", "Hi");
        long after = chatbotService.getTotalMessageCount();
        assertThat(after).isEqualTo(before + 1);
    }

    @Test
    void testLogsAreRetrievable() {
        chatbotService.generateReply("+910000000000", "Hello");
        assertThat(chatbotService.getAllLogs()).isNotEmpty();
    }
}
