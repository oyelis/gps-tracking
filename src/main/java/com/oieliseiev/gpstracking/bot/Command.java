package com.oieliseiev.gpstracking.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Command {

    START("Start", "/start", false, 0),
    CHECK_LAST_LOCATION("Get last location", "/lastlocation", true, 1),
    GET_CURRENT_LOCATION("Get current location", "/currentlocation", true, 2),
    ADD_NEW_DEVICE("Add new device", "/addnewdevice", true, 3),
    LIST_DEVICES("List my devices", "/listdevices", true, 4),
    SELECT_DEVICE("Activate device", "/selectdevice", true, 5),
    NONE("None", "/none", false, 6);

    @Getter
    private String name;
    @Getter
    private String value;
    @Getter
    private boolean menuEnabled;
    @Getter
    private int order;

    public static List<Command> getMainMenuCommands() {
        return Arrays.stream(Command.values()).filter(Command::isMenuEnabled).sorted(Comparator.comparing(Command::getOrder)).collect(Collectors.toList());
    }

    public static Command getCommand(String value) {
        return Arrays.stream(Command.values()).filter(v -> value.startsWith(v.getValue())).findAny().orElse(NONE);
    }
}
