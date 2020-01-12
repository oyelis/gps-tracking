package com.oieliseiev.gpstracking.bot;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Command {

    START("Start", "/start"),
    CHECK_LAST_LOCATION("Get last location", "/lastlocation"),
    GET_CURRENT_LOCATION("Get current location", "/currentlocation"),
    ADD_NEW_DEVICE("Add new device", "/addnewdevice"),
    LIST_DEVICES("List my devices", "/listdevices"),
    SELECT_DEVICE("Activate device", "/selectdevice"),
    NONE("None", "/none");

    @Getter
    private String name;
    @Getter
    private String value;

    Command(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static List<Command> getCommands() {
        return Arrays.stream(Command.values()).filter(c -> c != START && c != NONE).collect(Collectors.toList());
    }

    public static Command getCommand(String value) {
        return Arrays.stream(Command.values()).filter(v -> value.startsWith(v.getValue())).findAny().orElse(NONE);
    }
}
