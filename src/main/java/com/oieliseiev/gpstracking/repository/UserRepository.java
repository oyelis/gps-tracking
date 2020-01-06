package com.oieliseiev.gpstracking.repository;

import com.oieliseiev.gpstracking.model.UserModel;

import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findUserByChatIdAndUserId(Integer userId, Long chatId);

    boolean saveUser(UserModel userModel);

    void updateLastActiveDate(Integer userId);
}
