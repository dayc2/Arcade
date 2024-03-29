package com.Hangman;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.Game;
import com.JPanelButton;


public class Hangman extends Game{

    
    private JPanel imgPanel;
    private JLabel noose;
    private JLabel lines;
    private static JPanel displayLetters;
    int width;
    int height;
    
    public Hangman(){
        this(7, 6);
    }

    public Hangman(int width, int height){
        this.width = width;
        this.height = height;
    }
    public String getImage(){
        return "hangman.png";
    }


    @Override
    public String getDescription() {
        return "A single player game where the player will guess one letter at a time." +
        " If you guess wrong you will be given a body part. Six parts and you lose.";
    }

    @Override
    public String getName() {
        return "Hangman";
    }

    @Override
    public void run(int players) {
        HangmanAI.restart();
        resume();

        Object games = getStat("Games Played");
        updateStat("Games Played", Integer.parseInt(games==null?"0":games.toString())+1);

        Object stat = getStat("Games Won");
        if(stat == null)
            updateStat("Games Won", 0);
    }


    @Override
    public void resume() {
        try{
            frame = new JFrame();
        }catch(Exception e){
            System.out.println("Cannot open JFrame");
            return;
        }
        imgPanel = new JPanel();
        noose = new JLabel();
        lines = new JLabel();
        displayLetters = new JPanel();
        frame.setLayout(new GridLayout(2,1));
        frame.setSize(width*100,height*100);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        getImg(imgPanel, HangmanAI.strikes);
        
        JPanel lowerHalf = new JPanel();
        lowerHalf.setLayout(new GridLayout(3,1));
        
        JPanel display = getWord();
        JPanel buttons = buttons();

        frame.add(imgPanel);
        lowerHalf.add(displayLetters);
        lowerHalf.add(display);
        lowerHalf.add(buttons);

        frame.add(lowerHalf);
        addWindowListener();

        frame.setFocusable(true);
        frame.setAutoRequestFocus(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                
                
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                
                
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if(!HangmanAI.alphabet.contains(e.getKeyChar()+"")) return;
                HangmanAI.guess(e.getKeyChar()+"");
                    getImg(imgPanel, HangmanAI.strikes);
                    if(HangmanAI.checkLose()) {
                        exit();
                        playagain();
                    }
                    if(HangmanAI.checkWin()) {
                        Object games = getStat("Games Won");
                        updateStat("Games Won", Integer.parseInt(games.toString())+1);
                        exit();
                    }
                
            }
        });
        

    }

    public boolean nextUnlocked() {
        if(getStats().compareTo("No stats yet, play one game") == 0)
            return false;
        Integer goal = 1;
        Object stat = getStat("Games Won");
        if(Integer.parseInt(stat.toString()) >= goal)
            return true;
        return false;
    }

    @Override
    public int maxPlayers() {
        return 1;
    }

    public void getImg(JPanel j, int i) {
        BufferedImage img;
        
        try {
            URL url = Hangman.class.getResource(getStage(i));
            img = ImageIO.read(url);
            Image image = img.getScaledInstance((int) (img.getWidth()), (int) (img.getHeight()), Image.SCALE_SMOOTH);
            ImageIcon temp = new ImageIcon(image);
            
            noose.setIcon(temp);
            j.add(noose);
    
        } catch (IOException e) {
            System.out.println("IMAGE NOT FOUND");
            e.printStackTrace();            
            return;
        }
    }


    public JLabel getLines() {
        BufferedImage img;
        lines = new JLabel();
        try {
            URL url = Hangman.class.getResource(getStage(7));
            img = ImageIO.read(url);
            Image image = img.getScaledInstance((int) (img.getWidth()), (int) (img.getHeight()), Image.SCALE_SMOOTH);
            ImageIcon temp = new ImageIcon(image);
            
            lines.setIcon(temp);
            return lines;
    
        } catch (IOException e) {
            System.out.println("IMAGE NOT FOUND");
            e.printStackTrace();            
            return lines;
        }
    }
    

    public JPanel getWord() {
        ArrayList<String> word = HangmanAI.answer;
        JPanel j = new JPanel();
        j.setLayout(new GridLayout(1,word.size(),10,0));  
        displayLetters.setLayout(new GridLayout(1, word.size(),10,0));     
        for(Integer i = 0; i < word.size(); i++) {
                j.add(getLines());
                JLabel temp = new JLabel();
                temp.setName(i.toString());
                displayLetters.add(temp);
        }
        return j;
    }
   
    private String getStage(int i) {
        if(i == 0)
            return "/com/Images/stages/noose.png";
        if(i == 1) 
            return "/com/Images/stages/stage1.png";
        if(i == 2) 
            return "/com/Images/stages/stage2.png";
        if(i == 3) 
            return "/com/Images/stages/stage3.png";
        if(i == 4) 
            return "/com/Images/stages/stage4.png";
        if(i == 5) 
            return "/com/Images/stages/stage5.png";
        if(i == 6)
            return "/com/Images/stages/stage6.png";
        if(i== 7)
            return "/com/Images/stages/Line.png";
        return null;
    }

    public JPanel buttons() {
        JPanel buttons = new JPanel();
        ArrayList<JButton> letters = new ArrayList<JButton>();
        buttons.setLayout(new GridLayout(3,1,1,1));
        int count = 0;
        JPanel p1 = new JPanel(new GridLayout(1,10,1,1));
        JPanel p2 = new JPanel(new GridLayout(1,9,1,1));
        JPanel p3 = new JPanel(new GridLayout(1,7,1,2));
        buttons.add(p1);
        buttons.add(p2);
        buttons.add(p3);
        p2.add(new JPanel());
        p3.add(new JPanel());
        for(int i = 0; i < HangmanAI.alphabet.size(); i++) {
            String letter = HangmanAI.alphabet.get(i);
            JPanelButton temp = new JPanelButton(letter){
                public void onClick(){
                    HangmanAI.guess(letter);
                    getImg(imgPanel, HangmanAI.strikes);
                    if(HangmanAI.checkLose()) {
                        exit();
                        playagain();
                    }
                    if(HangmanAI.checkWin()) {
                        Object games = getStat("Games Won");
                        updateStat("Games Won", Integer.parseInt(games.toString())+1);
                        exit();
                    }
                }
            };
            temp.setSize(10, 10);
            if(i < 10)
                p1.add(temp);
            else if(i < 19)
                p2.add(temp);
            else
                p3.add(temp);
            // buttons.add(temp);
            // letters.add(new JButton());
            // letters.get(count).setText(letter);
            // letters.get(count).addActionListener(new ActionListener() {

            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         HangmanAI.guess(letter);
            //         getImg(imgPanel, HangmanAI.strikes);
            //         if(HangmanAI.checkLose()) {
            //             exit();
            //             playagain();
            //         }
            //         if(HangmanAI.checkWin()) {
            //             Object games = getStat("Games Won");
            //             updateStat("Games Won", Integer.parseInt(games.toString())+1);
            //             exit();
            //         }
            //     }
                
            // });
            // buttons.add(letters.get(count));
            count++;
        }
        p2.add(new JPanel());
        p3.add(new JPanel());
        return buttons;
    }

    public static void displayLetter(String s, Integer index) {
        Font f = new Font("serif",1,40);
        for(Component c : displayLetters.getComponents()) {
            if(c.getName().compareTo(index.toString()) == 0) {
                c.setFont(f);
                ((JLabel) c).setText("  "+ s);
            }
        }
        displayLetters.updateUI();
    }

    public void playagain() {
        HangmanAI.restart();
    }


}
