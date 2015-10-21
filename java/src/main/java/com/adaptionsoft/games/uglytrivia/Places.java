package com.adaptionsoft.games.uglytrivia;

public class Places {
    int[] list = new int[6];

    public void setPlayersPlaceToZero(int index) {
        list[index] = 0;
    }

    public void add(int index, int value) {
        list[index] += value;
    }

    public void subtract(int index, int value) {
        list[index] -= value;
    }

    public int get(int index) {
        return list[index];
    }
}
