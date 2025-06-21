package org.MT;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.concurrent.*;

public class PasswordBot extends TelegramLongPollingBot {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public String getBotUsername() {
        return "PasswordGeneratorBot";
    }

    @Override
    public String getBotToken() {
        return "8177691684:AAGZ66Jz3pyySrvKbrB-vCleoy28LxU8OhA"; // Получите у @BotFather
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (messageText) {
            case "/start":
                sendMessage(chatId, "🔐 Привет! Я бот для работы с паролями.\n" +
                        "• /generate – Сгенерировать надежный пароль\n" +
                        "• /check – Проверить пароль на надежность");
                break;

            case "/generate":
                // Генерация в отдельном потоке, чтобы бот не завис
                executor.execute(() -> {
                    String password = PasswordGenerator.generatePassword(12, true);
                    sendMessage(chatId, "🔑 Ваш пароль: " + password + "\n" +
                            "Надежность: " + (PasswordValidator.isPasswordStrong(password, true) ? "Отличный" : "Слабый"));
                });
                break;

            case "/check":
                sendMessage(chatId, "Отправьте пароль для проверки:");
                break;

            default:
                // Проверка пароля (если пользователь отправил текст)
                if (messageText.length() > 0) {
                    executor.execute(() -> {
                        boolean isStrong = PasswordValidator.isPasswordStrong(messageText, true);
                        sendMessage(chatId, "Пароль: " + messageText + "\n" +
                                "Надежность: " + (isStrong ? "Отличный" : "Слабый"));
                    });
                }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
