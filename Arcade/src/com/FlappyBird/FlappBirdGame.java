package com.FlappyBird;

// import com.FlappyBird;
import com.Game;

public class FlappBirdGame extends Game{

    @Override
    public String getDescription() {
        return "Flappy Bird";
    }

    @Override
    public String getName() {
        return "Flappy Bird";
    }

    @Override
    public void run(int players) {
        new FlappyBird();
    }

    @Override
    public void resume() {
        new FlappBirdGame();
    }

    @Override
    public int maxPlayers() {
        return 1;
    }

    @Override
    public boolean nextUnlocked() {
        return false;
    }
    
}
