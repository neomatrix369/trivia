package com.adaptionsoft.games.uglytrivia;

public class Purses {
    private int[] list = new int[6];

    public void setPlayersPlaceToZero(int index) {
        list[index] = 0;
    }

    public void increment(int playerIndex) {
        list[playerIndex]++;
    }

    public int get(int playerIndex) {
        return list[playerIndex];
    }
}
