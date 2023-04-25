package com.Connect4;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.SwingUtilities;


import com.Game;


public class Connect4 extends Game{

    private BoardPanel boardPanel;

    private Board b;
    private int width;
    private int height;

    private java.awt.Color selecedColor = new java.awt.Color(150, 150, 150);
    private java.awt.Color unselecedColor = new java.awt.Color(220, 220, 220);
    private java.awt.Color backgroundColor = new java.awt.Color(20, 20, 200);

    static int gamesPlayed;
    static int gamesWon;

    public Connect4(){
        this(7, 6);
    }
    
    public Connect4(int width, int height){
        this.width = width;
        this.height = height;
    }
    public String toString(){
        return b.toString();
    }

    public String getImage(){
        return "connect4.jpeg";
    }

    public String getName(){
        return "Connect Four";
    }

    public int maxPlayers(){
        return 2;
    }
    public void run(int players){
        b = new Board(width, height);
        try{
            frame = new JFrame();
        }catch(Exception e){
            System.out.println("Cannot open JFrame");
            return;
        }
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width*100, height*100));
        boardPanel = new BoardPanel(height, width);
        frame.add(boardPanel);
        frame.pack();
        frame.setVisible(true);

        this.players=players%2;

        if(players == 1){
            Object games = getStat("Single Player Games Played");
            updateStat("Single Player Games Played", Integer.parseInt(games==null?"0":games.toString())+1);
        }else{
            Object games = getStat("Two Player Games Played");
            updateStat("Two Player Games Played", Integer.parseInt(games==null?"0":games.toString())+1);
        }

        Object stat = getStat("Single Player Games Won");
        if(stat == null)
            updateStat("Single Player Games Won", 0);
        stat = getStat("Two Player Games Won as Red");
        if(stat == null)
            updateStat("Two Player Games Won as Red", 0);
        stat = getStat("Two Player Games Won as Yellow");
        if(stat == null)
            updateStat("Two Player Games Won as Yellow", 0);


        addWindowListener();
        frame.setLocationRelativeTo(null);
    }

    public void resume(){
        try{
            frame = new JFrame();
        }catch(Exception e){
            System.out.println("Cannot open JFrame");
            return;
        }
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width*100, height*100));
        boardPanel = new BoardPanel(height, width);
        frame.add(boardPanel);
        frame.pack();
        frame.setVisible(true);
        addWindowListener();
        frame.setLocationRelativeTo(null);

        for(PiecePanel p : boardPanel.piecePanels){
            boardPanel.updateColor(p.id);
        }
    }

    public String getDescription(){
        return "Players take turn dropping pieces into a vertial grid. " + 
        "The pieces fall straight down, occupying the lowest available" + 
        "space within the column. The objective of the game is to be the " +
        "first to form a horizontal, vertical, or diagonal line of four of one's own pieces";
    }

    // public String getStats(){
    //     return "Games Played: " + gamesPlayed +
    //         "\nGames Won: " + gamesWon;
    // }

    public boolean nextUnlocked() {
        Integer goal = 1;
        Object stat = getStat("Single Player Games Won");
        if(Integer.parseInt(stat.toString()) >= goal)
            return true;
        return false;
    }

    public int computerMove(){
        // return ThreadLocalRandom.current().nextInt(0, 7);
        return Connect4AI.computerMove(b);
    }
    private class BoardPanel extends JPanel{

        PiecePanel[] piecePanels;

        public BoardPanel(int height, int width){
            setLayout(new GridLayout(height, width));
            setSize(new Dimension(width*100, height*100));
            setBackground(new java.awt.Color(32, 32, 32));
            setDoubleBuffered(true);
            piecePanels = new PiecePanel[height*width];
            for (int i = 0; i < piecePanels.length; i++) {
                piecePanels[i] = new PiecePanel(i);
                add(piecePanels[i]);
            }
            setPreferredSize(new Dimension(100*width, 100*height));
            validate();
        }

        public void updateColor(int id){
            piecePanels[id].color = b.board[id].color.getColor();
            piecePanels[id].repaint();
        }
    }

    private class PiecePanel extends JPanel{

        private int id;

        private java.awt.Color color = unselecedColor;

        public PiecePanel(int id){
            super(new GridBagLayout());
            this.id = id;
            setBackground(backgroundColor);
            // setPreferredSize(new Dimension(10, 10));
            addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
                    

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(!b.winner && b.drop(getId())){
                        boardPanel.updateColor(b.lastDrop);
                    

                        if(players==1 && !b.winner){
                            while(!b.drop(computerMove()));
                            boardPanel.updateColor(b.lastDrop);
                        }

                        if(b.winner){
                            if(b.turn == Color.RED){
                                gamesWon++;
                            }
                            if(players == 1){
                                if(b.turn == Color.RED){
                                    Object games = getStat("Single Player Games Won");
                                    updateStat("Single Player Games Won", Integer.parseInt(games.toString())+1);
                                }
                            }else if(b.turn == Color.RED){
                                Object games = getStat("Two Player Games Won as Red");
                                updateStat("Two Player Games Won as Red", Integer.parseInt(games.toString())+1);
                            }else{
                                Object games = getStat("Two Player Games Won as Yellow");
                                updateStat("Two Player Games Won as Yellow", Integer.parseInt(games.toString())+1);

                            }
                            exit();
                            return;
                        }
                        mouseEntered(e);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(b.board[(id%width)].color == Color.NONE){
                        boardPanel.piecePanels[id%width].color = (b.turn.getColor());
                    }
                    for (int i = 1; i < height; i++) {
                        if(b.board[(id%width)+(i*width)].color == Color.NONE){
                            boardPanel.piecePanels[(id%width)+(i*width)].color = selecedColor;
                        }
                    }

                    SwingUtilities.invokeLater(() -> {
                        for (PiecePanel p : boardPanel.piecePanels) {
                            p.repaint();
                        }
                        boardPanel.repaint();
                    });
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    for (int i = 0; i < height; i++) {
                        if(b.board[(id%width)+(i*width)].color == Color.NONE) boardPanel.piecePanels[(id%width)+(i*width)].color = unselecedColor;
                    }
                    SwingUtilities.invokeLater(() -> {
                        for (PiecePanel p : boardPanel.piecePanels) {
                            p.repaint();
                        }
                        boardPanel.repaint();
                    });
                }
            });
        }

        public int getId(){return id;}

        public void paintComponent(Graphics g){
            g = (Graphics2D) g;
            super.paintComponent(g);
            g.setColor(color);
            g.fillOval(0, 0, this.getWidth()-1, this.getWidth()-1);
        }
    }
}
