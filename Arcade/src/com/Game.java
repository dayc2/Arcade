package com;
/*
 * This is an abstract game class that will be implemented by each game
 */

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.GameSelectionScreen.GameSelectionScreen;

public abstract class Game {

    public int players = 1;
    protected JFrame frame;
    private boolean close = true;
    public boolean paused = false; 
    public boolean unlocked = false;

    static String statsFile = System.getProperty("user.dir") + "/stats.json";

    public boolean running = false;

    /**
     * @return the description of the game to be displayed with game delection
     */
    public abstract String getDescription();

    /**
     * @return the name of the game
     */
    public abstract String getName();

    /**
     * Use this to start running the game.
     * 
     * Make sure any stats are added using updateStat so they are saved and shown on stats screen
     * 
     * @param players the number of players for the game
     */
    public abstract void run(int players);

    /**
     * Use to resume a ongoing game. This method is called when the game is resumed.
     * All game properties are saved and can be used except for the frame, which will need to be re-initialized.
     */
    public abstract void resume();

    /**
     * @return the maximum number of players that the game supports
     */
    public abstract int maxPlayers();

    /**
     * Adds a {@code Window Listener} to the frame that the game is displayed on. 
     * Used to provide exit confirmation and to reopen the selection screen
     * 
     * @throws NullPointerException if the frame was not initialized
     */
    protected void addWindowListener(){
        if(frame==null){
            throw new NullPointerException("Frame was not initialized");
        }
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                if(close){
                    if(JOptionPane.showOptionDialog(frame, "Are you sure you want to close? The current game will be saved", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"No", "Yes"}, null) == 1){
                        frame.dispose();
                        paused = true;
                        System.out.println("paused " + paused);
                        new GameSelectionScreen();
                    }
                }
            }
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    /**
     * Use this to exit the game
     */
    public void exit(){
        int option = -1;
        while(option == -1){
            option = JOptionPane.showOptionDialog(frame, "Play again?", "Play again?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, new String[]{"No", "Yes", "Change player amount"}, null);
        }
        if(option == 1){
            close = false;
            if(frame!=null) frame.dispose();
            run(players);
            close = true;
        }else if(option == 0){
            close = true;
            if(frame!=null)frame.dispose();
            new GameSelectionScreen();
        }else{
            close = false;
            Object players[] = new Object[maxPlayers() + 1];
            players[0] = "Back";
            for (int i = 1; i < players.length; i++) {
                players[i] = players.length-i;
            }
            option = JOptionPane.showOptionDialog(frame, "How many players?", "How Many Players?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, players, 0);
            if(option > 0 && option < players.length){
                close = false;
                if(frame != null) frame.dispose();
                run(players.length-option);
                close = true;
            }else{
                exit();
            }
        }
    }

    /**
     * @return Returns a string that will be displayed with the stats page
     */
    @SuppressWarnings("unchecked")
    public String getStats(){
        StringBuilder s = new StringBuilder();
        JSONParser j = new JSONParser();
        File f = new File(statsFile);
        try {
            if(f.createNewFile()){
                FileWriter fw = new FileWriter(f);
                fw.write("{}");
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileReader reader = new FileReader(f)){
            JSONObject obj = (JSONObject) j.parse(reader);
            Object game = ((JSONObject) obj).get(getName());
            if(game == null){
                obj.putIfAbsent(getName(), new JSONObject());
                reader.close();
                return "No stats yet, play one game";
            }
            JSONObject gameObj = (JSONObject) game;
            for (Object stat : gameObj.keySet().stream().sorted().toArray()) {
                s.append(stat.toString() + ": " + gameObj.get(stat) + "\n");
            }
            reader.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        return s.toString();
    }

    /**
     * Use this to update / add any stat
     * 
     * @param statName the name of the stat to update / add
     * @param value the value taht they stat will have
     */
    @SuppressWarnings("unchecked")
    protected void updateStat(String statName, Object value){
        JSONParser j = new JSONParser();
        File f = new File(statsFile);
        try {
            if(f.createNewFile()){
                FileWriter fw = new FileWriter(f);
                fw.write("{}");
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileReader reader = new FileReader(f)){
            JSONObject obj = (JSONObject) j.parse(reader);
            obj.putIfAbsent(getName(), new JSONObject());
            JSONObject gameObj = (JSONObject) (obj.get(getName()));
            gameObj.put(statName, value);
            FileWriter writer = new FileWriter(f);
            writer.write(obj.toJSONString());
            writer.flush();
            writer.close();
            reader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns an {@code Object} of the current value of {@code statName}
     * 
     * @param statName name of the stat
     * @return the current stat. Returns null if the stat doesn't exist, is null, or is 0
     */
    @SuppressWarnings("unchecked")
    public Object getStat(String statName){
        JSONParser j = new JSONParser();
        File f = new File(statsFile);
        try {
            if(f.createNewFile()){
                FileWriter fw = new FileWriter(f);
                fw.write("{}");
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileReader reader = new FileReader(f)){
            JSONObject obj = (JSONObject) j.parse(reader);
            obj.putIfAbsent(getName(), new JSONObject());
            JSONObject gameObj = (JSONObject) (obj.get(getName()));
            gameObj.putIfAbsent(statName, 0);
            reader.close();
            return gameObj.get(statName);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @return String the name of the image file being displayed on the selection screen
     * 
     */
    public String getImage(){
        return "DefaultImage.jpg";
    }

    /**
     * @return a boolean value based on a condition you decide from the previous game
     * once that condition is met in the stats your condition should be false
     */
    public boolean locked() {
        return false;
    }
}
