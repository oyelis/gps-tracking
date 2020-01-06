package com.oieliseiev.gpstracking.service;

import com.oieliseiev.gpstracking.model.UserModel;
import com.oieliseiev.gpstracking.repository.impl.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JdbcUserRepository jdbcUserRepository;

    public boolean initializeUser(User telegramUser, Long chatId) {
        Optional<UserModel> initializedUser = jdbcUserRepository.findUserByChatIdAndUserId(telegramUser.getId(), chatId);
        if (!initializedUser.isPresent()) {
            jdbcUserRepository.saveUser(new UserModel(telegramUser.getId(), chatId, telegramUser.getFirstName(), telegramUser.getLastName(), LocalDateTime.now()));
            return false;
        } else {
            jdbcUserRepository.updateLastActiveDate(initializedUser.get().getUserId());
            return true;
        }
    }
}
