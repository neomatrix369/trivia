package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;

    boolean isInPenaltyBox;
    private int roll;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isInThePenaltyBox() {
        return this.isInPenaltyBox;
    }

    public void addRoll(int roll) {
        this.roll = roll;
    }
}
