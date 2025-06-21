package org.MT;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new PasswordBot());
            System.out.println("Бот успешно запущен!");
        } catch (TelegramApiException e) {
            // Игнорируем ошибку удаления вебхука, если бот не использует вебхуки
            if (!e.getMessage().contains("Error removing old webhook")) {
                e.printStackTrace();
            }
            System.out.println("Бот запущен в polling режиме");
        }
    }
}