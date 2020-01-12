package com.oieliseiev.gpstracking.bot;

import com.oieliseiev.gpstracking.model.GpsDevice;
import com.oieliseiev.gpstracking.service.DeviceService;
import com.oieliseiev.gpstracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TelegramBot.class.getName());

    @Value("${bot.username}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private TelegramBotService telegramBotService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        } else {
            LOGGER.warning("Message handler is skipped." + update);
        }
    }


    private void handleMessage(Message message) {
        if (message != null && message.hasText()) {
            userService.initializeUser(message.getFrom(), message.getChatId());
            String msg = message.getText();
            LOGGER.info("Message: " + msg);
            if (message.isReply()) {
                handleReplyMessage(message);
            } else {
                handleCommand(message, Command.getCommand(msg));
            }
        }
    }

    private void handleReplyMessage(Message message) {
        String resultMessage = "No action";
        try {
            if (Action.getAction(message.getReplyToMessage().getText()) == Action.ENTER_DEVICE_IMEI) {
                resultMessage = deviceService.saveDevice(message.getText(), message.getFrom().getId()) ? "GPS device successfully added." : "GPS device already added.";
            }
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText(resultMessage);
        sendBotApiMessage(response);
    }

    private void handleCommand(Message message, Command command) {
        switch (command) {
            case LIST_DEVICES:
                handleListDevices(message);
                break;
            case ADD_NEW_DEVICE:
                handleAddNewDevice(message);
                break;
            case NONE:
            default:
                Map<String, String> buttons = new LinkedHashMap<>();
                Command.getMainMenuCommands().forEach(v -> buttons.put(v.getName(), v.getValue()));
                sendBotApiMessage(telegramBotService.getKeyBoardMarkup(message, "Please select option: ", buttons));
        }
    }

    private void handleListDevices(Message message) {
        List<GpsDevice> devices = deviceService.getUserDevices(message.getFrom().getId(), message.getChatId());
        if (devices.isEmpty()) {
            Map<String, String> buttons = new HashMap<>();
            buttons.put("Add new GPS device", Command.ADD_NEW_DEVICE.getValue());
            sendBotApiMessage(telegramBotService.getKeyBoardMarkup(message, "You have no GPS devices. You can add new device: ", buttons));
        } else {
            Map<String, String> buttons = new HashMap<>();
            devices.forEach(d -> buttons.put(d.getImei(), Command.SELECT_DEVICE.getValue() + ":" + d.getId()));
            sendBotApiMessage(telegramBotService.getKeyBoardMarkup(message, "You can activate single device: ", buttons));
        }
    }

    private void handleAddNewDevice(Message message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId());
        sendMessageRequest.setReplyToMessageId(message.getMessageId());
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        forceReplyKeyboard.setSelective(true);
        sendMessageRequest.setReplyMarkup(forceReplyKeyboard);
        sendMessageRequest.setText(Action.ENTER_DEVICE_IMEI.getMessage());
        try {
            executeAsync(sendMessageRequest, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> method, Message sentMessage) {
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, TelegramApiRequestException e) {
                    LOGGER.severe(e.getMessage());
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                    LOGGER.severe(e.getMessage());
                }
            });
        } catch (TelegramApiException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        if (message != null && message.hasText()) {
            userService.initializeUser(message.getFrom(), message.getChatId());
            String data = callbackQuery.getData();
            LOGGER.info("CallbackQuery: " + data);
            Command command = Command.getCommand(data);
            handleCommand(message, command);
        }
    }

    private void sendBotApiMessage(BotApiMethod<? extends Serializable> botApiMethod) {
        try {
            execute(botApiMethod);
        } catch (TelegramApiException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
