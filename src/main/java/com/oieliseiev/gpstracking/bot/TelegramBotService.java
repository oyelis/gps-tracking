package com.oieliseiev.gpstracking.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
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
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        Set<String> names = callbackButtons.keySet();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            String name1 = iterator.next();
            addButton(name1, callbackButtons.get(name1), buttons);
            if (iterator.hasNext()) {
                String name2 = iterator.next();
                addButton(name2, callbackButtons.get(name2), buttons);
            }
            keyboardButtons.add(buttons);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    private void addButton(String name, String callback, List<InlineKeyboardButton> buttons) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(name);
        inlineKeyboardButton.setCallbackData(callback);
        buttons.add(inlineKeyboardButton);
    }

    public SendChatAction getChatAction(Message message, ActionType actionType) {
        Long chatId = message.getChatId();
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setAction(actionType);
        sendChatAction.setChatId(chatId);
        return sendChatAction;
    }
}
