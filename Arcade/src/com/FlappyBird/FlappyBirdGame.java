package com.FlappyBird;

import javax.swing.JFrame;

import com.Game;

public class FlappyBirdGame extends Game{

    static FlappyBirdGame g;
    @Override
    public String getDescription() {
        return "This is a single player game where the player will try to fly as far as possible without hitting a pipe.";
    }

    @Override
    public String getName() {
        return "Flappy Bird";
    }

    @Override
    public void run(int players) {
        Object stat = getStat("Games Played");
        if(stat == null)
            updateStat("Games Played", 0);
        stat = getStat("High Score");
        if(stat == null)
            updateStat("High Score", 0);
        g = this;
        resume();
    }

    @Override
    public void resume() {
        frame = new JFrame();
        addWindowListener();
        FlappyBird g = new FlappyBird(frame);
        frame.setLocationRelativeTo(null);
        // exit();
    }

    @Override
    public int maxPlayers() {
        return 1;
    }

    @Override
    public boolean nextUnlocked() {
        return false;
    }

    static void endGame(int score){
        g.updateStats(score);
        g.exit();
    }

    void updateStats(int score){
        Object games = getStat("Games Played");
        updateStat("Games Played", Integer.parseInt(games==null?"0":games.toString())+1);
        games = getStat("High Score");
        if(score > Integer.parseInt(games==null?"0":games.toString()))
            updateStat("High Score", score);
    }

    public String getImage(){
        return "FlappyBird.png";
    }
    
}
