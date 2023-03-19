package com;

public class testGame extends Game{

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getName() {
        return "TestGame";
    }
    @Override
    public void run(int players) {
        Object games = getStat("Games Played");
        updateStat("Games Played", Integer.parseInt(games.toString())+1);
        exit();
    }

    @Override
    public int maxPlayers() {
        return 5;
    }
    
}
