package com.oieliseiev.gpstracking.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum Action {

    ENTER_DEVICE_IMEI("Enter GPS device imei:", 0);

    @Getter
    private String message;
    @Getter
    private int code;

    public static Action getAction(String value) {
        return Arrays.stream(Action.values()).filter(v -> value.startsWith(v.getMessage())).findAny().orElse(null);
    }
}
