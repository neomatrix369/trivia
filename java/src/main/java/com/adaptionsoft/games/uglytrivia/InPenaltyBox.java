package com.adaptionsoft.games.uglytrivia;

public class InPenaltyBox {
    private boolean[] list = new boolean[6];

    public boolean get(int playerIndex) {
        return list[playerIndex];
    }

    public void setPlayersPlaceTo(int playerIndex, boolean value) {
        list[playerIndex] = value;
    }
}
