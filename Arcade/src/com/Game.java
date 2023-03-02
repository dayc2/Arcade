package com;
/*
 * This is an abstract game class that will be implemented by each game
 */

public abstract class Game {

    public boolean running = false;

    /*
     * @return description Returns the description of the game to be displayed with game delection
     */
    public abstract String getDescription();

    /*
     * Use this to start running the game
     */
    public abstract void run(int players);

    /*
     * Use this to exit the game
     */
    public abstract void exit();

    /*
     * @return score Returns the high score text that will be displayed with game selection
     */
    public abstract String getHighScore();
}
