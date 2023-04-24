package com.GameSelectionScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import com.Game;
import com.testGame;
import com.Connect4.Connect4;
import com.Hangman.Hangman;
// import com.TicTacToe.TicTacToe;

public class GameSelectionScreen{

    private JFrame frame;

    private JPanel panel;

    private SelectionPanel selectionPanel;

    private GamePanel gamePanel;

    // Add any new games to this list
    static Game[] GameList = new Game[]{new Connect4(), new Hangman(), new testGame()};

    static Color MAIN_BACKGROUND = new Color(1, 43, 110);
    static Color SELECTED = new Color(255, 255, 255);
    static Color DESELECTED = new Color(200, 200, 200);
    static Color HOVER = new Color(220, 220, 220);
    static Color DESCRIPTION = new Color(240, 240, 240);
    static Color PLAY_BUTTON = new Color(0, 255, 0);
    static Color STATS_BUTTON = new Color(255, 0, 255);
    static Color STATS = new Color(240, 240, 240);
    static Color GAME = new Color(1, 43, 110);
    static Color BORDER = new Color(32, 35, 54);

    public GameSelectionScreen(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(){
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground().brighter().brighter(), 0, getHeight(),
                        getBackground().darker().darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight()); 
    
            }
    
        };
        frame.add(panel);
        selectionPanel = new SelectionPanel();
        panel.add(selectionPanel);

        gamePanel = new GamePanel();
        panel.add(gamePanel);

        int inset = (int) (gamePanel.getWidth()+selectionPanel.getWidth()*.1);
        panel.setBorder(new EmptyBorder(inset, inset, inset, inset));
        
        frame.pack();
        panel.setBackground(MAIN_BACKGROUND);
        frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setVisible(true);

    }

    private class SelectionPanel extends JPanel{

        private gameSelectPanel selected;

        SelectionPanel(){
            setBackground(BORDER);
            JPanel p = new JPanel();

            p.setPreferredSize(new Dimension(200, Math.max(GameSelectionScreen.GameList.length*50, 400)));
            p.setBackground(DESELECTED);
            p.setLayout(new GridLayout(Math.max(GameSelectionScreen.GameList.length, 8), 1));


            for(Game game : GameSelectionScreen.GameList){
                if(!game.locked())
                    p.add(new gameSelectPanel(game));
                
            }
            JScrollPane scrollBar = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollBar.setPreferredSize(new Dimension(200, 400+20));
            scrollBar.setBackground(DESELECTED);
            add(scrollBar);

            revalidate();
        }

        private class gameSelectPanel extends JPanel{
            private Game game;
            JLabel label;

            gameSelectPanel(Game game){
                // super(game.getClass().getName());
                this.game = game;
                label = new JLabel(game.getName(), SwingConstants.LEFT);
                setLayout(new BorderLayout());
                setBorder(BorderFactory.createLineBorder(DESELECTED, 5));
                label.setBorder(new EmptyBorder(5, 5, 5, 5));
                add(label);
                
                setBackground(DESELECTED);

                addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(selected!=null &&  selected != getPanel()){
                            selected.setBackground(DESELECTED);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // display game info
                        if(selected != getPanel()){
                            selected = getPanel();
                            setBackground(SELECTED);
                            gamePanel.updateImage(selected.game.getImage());
                            gamePanel.updateDescription(selected.game.getDescription());
                            gamePanel.statsPanel.setVisible(false);
                            gamePanel.descriptionPanel.setVisible(true);
                            gamePanel.imagePanel.setVisible(true);
                            gamePanel.playButton.setVisible(true);
                            gamePanel.statsButton.setText("Stats");
                        }
                        
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(selected != getPanel()){
                            setBackground(HOVER);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(selected != getPanel()){
                            setBackground(DESELECTED);
                        }
                    }
                    
                });
            }

            gameSelectPanel getPanel(){return this;}
        }

    }

    private class GamePanel extends JPanel{

        private JPanel descriptionPanel;
        private JPanel imagePanel;
        private JButton statsButton;
        private JButton playButton;

        private StatsPanel statsPanel;
        
        GamePanel(){
            setPreferredSize(new Dimension(400, 400));
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            setBackground(GAME);

            c.fill = GridBagConstraints.HORIZONTAL;
            // c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 2;
            imagePanel = new JPanel();
            imagePanel.setSize(200, 200);
            imagePanel.setVisible(false);
            imagePanel.setOpaque(false);
            add(imagePanel, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(10, 10, 10, 10);
            c.ipady = 40;
            c.weighty = 1;
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            c.gridheight = 1;
            descriptionPanel = new JPanel();
            descriptionPanel.setLayout(new BorderLayout());
            descriptionPanel.setVisible(true);

            add(descriptionPanel, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            playButton = new JButton("Play");
            playButton.setVisible(false);
            playButton.setBackground(PLAY_BUTTON);
            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = .5;
            add(playButton, c);
            playButton.setFocusable(false);
            playButton.setVisible(false);
            playButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(selectionPanel.selected.game.paused){
                        int option = JOptionPane.showOptionDialog(frame, "Resume game?", "Resume Game?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Cancel", "No", "Yes"}, null);
                        if(option == 2){
                            selectionPanel.selected.game.resume();
                            return;
                        }
                    }
                    Object players[] = new Object[selectionPanel.selected.game.maxPlayers()+1];
                    players[0] = "Cancel";
                    for (int i = 1; i < players.length; i++) {
                        players[i] = players.length-i;
                    }
                    int option = JOptionPane.showOptionDialog(frame, "How many players?", "How Many Players?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, players, 0);
                    if(option > 0 && option < players.length){
                        selectionPanel.selected.game.run(players.length-option);
                        frame.dispose();
                    }
                }
                
            });

            c.fill = GridBagConstraints.HORIZONTAL;
            statsButton = new JButton("Stats");
            statsButton.setVisible(false);
            statsButton.setBackground(STATS_BUTTON);
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = .5;
            add(statsButton, c);
            statsButton.setFocusable(false);
            statsButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!statsPanel.isVisible()){
                        playButton.setVisible(false);
                        imagePanel.setVisible(false);
                        descriptionPanel.setVisible(false);
                        statsPanel.label.setText(selectionPanel.selected.game.getStats());
                        statsPanel.setVisible(true);
                        statsButton.setText("Back");
                    }else {
                        playButton.setVisible(true);
                        imagePanel.setVisible(true);
                        descriptionPanel.setVisible(true);
                        statsPanel.setVisible(false);
                        statsButton.setText("Stats");

                    }
                }
            });

            statsPanel = new StatsPanel();
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10, 10, 0, 10);
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 2;
            c.weightx = 1;
            c.gridheight = 2;
            add(statsPanel, c);


            validate();
            setVisible(true);
        }

        protected void paintComponent(Graphics grphcs) {
            super.paintComponent(grphcs);
            Graphics2D g2d = (Graphics2D) grphcs;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0,
                    getBackground().brighter().brighter(), 0, getHeight(),
                    getBackground().darker().darker());
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight()); 

        }

        
        void updateImage(String imageName){
            imagePanel.removeAll();
            imagePanel.setSize(400, 200);
            BufferedImage img;
            try {
                URL url = GameSelectionScreen.class.getResource("/com/Images/" + imageName);
                if(url == null) url = GameSelectionScreen.class.getResource("/com/Images/DefaultImage.jpg");
                img = ImageIO.read(url);
                double scale = Math.max((double)img.getWidth()/imagePanel.getWidth(), (double)img.getHeight()/ imagePanel.getHeight());
                Image image = img.getScaledInstance((int) (img.getWidth()/scale), (int) (img.getHeight()/scale), Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                imageLabel.setBorder(new LineBorder(BORDER, 5));
                imagePanel.add(imageLabel);
            } catch (IOException e) {
                System.out.println("IMAGE NOT FOUND");
                return;
            }
            imagePanel.setPreferredSize(gamePanel.getSize());
            revalidate();
        }

        void updateDescription(String descriptionText){
            descriptionPanel.removeAll();
            descriptionPanel.setBackground(DESCRIPTION);
            JTextArea description = new JTextArea(descriptionText, 3, 40);
            description.setLineWrap(true);
            description.setWrapStyleWord(true);
            description.setEditable(false);
            description.setOpaque(false);
            description.setBorder(new EmptyBorder(5, 5, 5, 5));
            descriptionPanel.add(description, BorderLayout.CENTER);
            descriptionPanel.setBorder(new LineBorder(BORDER, 5));
            playButton.setVisible(true);
            statsButton.setVisible(true);

        }
        
        private class StatsPanel extends JPanel{
            private JPanel p;
            JScrollPane scrollPane;
            private JTextArea label;

            StatsPanel(){
                p = new JPanel();
                // setLayout(new BorderLayout());
                setLayout(new GridLayout(1,1));
                label = new JTextArea();
                label.setEditable(false);
                label.setLineWrap(false);
                label.setWrapStyleWord(true);
                label.setOpaque(false);
                setBorder(new LineBorder(BORDER, 5));
                // label.setPreferredSize(new Dimension(380, 300));

                p.setBackground(STATS);
                setVisible(false);
                p.add(label);

                ((javax.swing.text.DefaultCaret) label.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

                scrollPane = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                // scrollPane.setPreferredSize(new Dimension(380, 300));
                scrollPane.setBackground(STATS);
                // p.setPreferredSize(new Dimension(380, 300));
                // panel.add(scrollPane);
                label.setCaretPosition(0);
                add(scrollPane);
                validate();
            }
        }
    }

}
