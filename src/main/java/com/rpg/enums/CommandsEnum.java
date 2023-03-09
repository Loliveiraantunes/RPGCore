package com.rpg.enums;

public enum CommandsEnum {
    RPG ("rpg"),
    START("start"),
    CLASS("class"),
    STATUS("status"),
    LEVELTOP("leveltop");

    private final String name;

    private CommandsEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
