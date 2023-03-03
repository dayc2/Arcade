package com.Connect4;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


import com.Game;


public class Connect4 extends Game{

    private JFrame frame;
    private BoardPanel boardPanel;

    private Board b;
    private int width;
    private int height;

    private int players = 1;

    private java.awt.Color selecedColor = new java.awt.Color(150, 150, 150);
    private java.awt.Color unselecedColor = new java.awt.Color(0, 0, 0);
    private java.awt.Color backgroundColor = new java.awt.Color(20, 20, 200);

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

    
    public void run(int players){
        b = new Board(width, height);

        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width*100, height*100));
        frame.pack();
        boardPanel = new BoardPanel(height, width);
        frame.add(boardPanel);
        frame.setVisible(true);

        this.players=players%2;


        running = true;

    }

    public void exit(){
        System.out.println(b.turn.name() + " wins!");
        System.out.println(b);
        // frame.dispose();
    }

    public String getDescription(){
        return "This is basic Connect4";
    }

    public String getHighScore(){
        return "CHANGE THIS";
    }

    public int computerMove(){
        // return ThreadLocalRandom.current().nextInt(0, 7);
        return Connec4AI.computerMove(b);
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
                    };

                    if(players==1 && !b.winner){
                        while(!b.drop(computerMove()));
                        boardPanel.updateColor(b.lastDrop);
                    }

                    if(b.winner){
                        running = false;
                        exit();
                    }
                    mouseEntered(e);
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
