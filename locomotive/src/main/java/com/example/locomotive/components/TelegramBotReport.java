package com.example.locomotive.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.example.locomotive.dto.response.LocomotiveSummaryResponse;
import com.example.locomotive.services.TelegramService;

@Component
@DependsOn("mongoService")
public class TelegramBotReport extends TelegramLongPollingBot {
    private TelegramService telegramService;
    private static final String BOT_TOKEN = "7209761146:AAHB_ipN11oXunNd8vNgoCmtqgtePjmV8bs";
    private static final String CHAT_ID = "6432640256";

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotReport.class);

    public TelegramBotReport(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendLatestSummary() {
        LocomotiveSummaryResponse latestSummary = telegramService.getLatestSummary();

        sendTelegramMessage(latestSummary);
    }

    private void sendTelegramMessage(LocomotiveSummaryResponse message) {
        String messageText = String.format("Summary Date: %s\nTotal Locomotives: %s\nActive: %s\nNot Active: %s\nMaintenance: %s", message.getUpdatedAt(), message.getTotalLocomotive(), message.getActive(), message.getNonActive(), message.getMaintenance());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(messageText);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        
    }

    @Override
    public String getBotUsername() {
        return "darylclaudio_locomotive_bot";
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}