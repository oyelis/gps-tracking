package com.oieliseiev.gpstracking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserModel {
    private Integer userId;
    private Long chatId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastActiveDate;
}
