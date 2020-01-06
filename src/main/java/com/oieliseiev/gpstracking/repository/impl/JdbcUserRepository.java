package com.oieliseiev.gpstracking.repository.impl;

import com.oieliseiev.gpstracking.model.UserModel;
import com.oieliseiev.gpstracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String SELECT_BY_CHAT_ID = "SELECT * FROM tbl_user WHERE chatId = :chatId";
    private static final String UPDATE_LAST_ACTIVE = "UPDATE tbl_user SET lastActiveDate = current_timestamp() where userId = :userId";
    private static final String INSERT_USER = "INSERT INTO tbl_user (userId, chatId, firstName, lastName, lastActiveDate) values (:userId, :chatId, :firstName, :lastName, :lastActiveDate)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<UserModel> findUserByChatIdAndUserId(Integer userId, Long chatId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        mapSqlParameterSource.addValue("chatId", chatId);
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_CHAT_ID, mapSqlParameterSource, (rs, i) ->
                    Optional.of(new UserModel(rs.getInt("userId"),
                            rs.getLong("chatId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getTimestamp("lastActiveDate").toLocalDateTime())));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean saveUser(UserModel userModel) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userModel.getUserId());
        mapSqlParameterSource.addValue("chatId", userModel.getChatId());
        mapSqlParameterSource.addValue("firstName", userModel.getFirstName());
        mapSqlParameterSource.addValue("lastName", userModel.getLastName());
        mapSqlParameterSource.addValue("lastActiveDate", Timestamp.valueOf(LocalDateTime.now()));
        return namedParameterJdbcTemplate.update(INSERT_USER, mapSqlParameterSource) > 0;
    }

    @Override
    public void updateLastActiveDate(Integer userId) {
        namedParameterJdbcTemplate.update(UPDATE_LAST_ACTIVE, new MapSqlParameterSource("userId", userId));
    }

}
