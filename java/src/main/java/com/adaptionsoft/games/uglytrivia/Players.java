package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private List<Player> list = new ArrayList<>();

    public void add(Player playerName) {
        list.add(playerName);
    }

    public int count() {
        return list.size();
    }

    public Player get(int index) {
        return list.get(index);
    }
}
