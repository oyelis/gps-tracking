package com.oieliseiev.gpstracking.bot;

import com.oieliseiev.gpstracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TelegramBotService {

    private static final Logger LOGGER = Logger.getLogger(TelegramBotService.class.getName());

    public SendLocation getCurrentLocation(Message message) {
        Long chatId = message.getChatId();
        //todo
        SendLocation sendLocation = new SendLocation(49.812345f, 23.973682f);
        sendLocation.setChatId(chatId);
        return sendLocation;
    }

    public SendLocation getLastLocation(Message message) {
        Long chatId = message.getChatId();
        //todo
        SendLocation sendLocation = new SendLocation(49.812345f, 23.973682f);
        sendLocation.setChatId(chatId);
        return sendLocation;
    }

    public SendMessage getKeyBoardMarkup(Message message, String markupMsg, Map<String, String> callbackButtons) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(markupMsg);
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardButtons = Collections.singletonList(buttons);
        callbackButtons.forEach((b, c) -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(b).setCallbackData(c);
            buttons.add(inlineKeyboardButton);
        });
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendChatAction getChatAction(Message message, ActionType actionType) {
        Long chatId = message.getChatId();
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setAction(actionType);
        sendChatAction.setChatId(chatId);
        return sendChatAction;
    }
}
