package com.TicTacToe;

import javax.swing.*;

import com.Game;
import com.JPanelButton;
import com.GameSelectionScreen.GameSelectionScreen;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe extends Game{
	
    private JPanelButton[][] buttons;
    char[][] board;
    
    private boolean playerOneTurn = true;
    
    private int turnsTaken = 0;
    
    private boolean playAgainstBot;

    public TicTacToe() {
        buttons = new JPanelButton[3][3];
    }

    private void initializeButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JPanelButton(){
                    public void onClick(){
                        JPanelButton buttonClicked = this;
                        int row = 0, col = 0;
                        for (int i = 0; i < board.length; i++) {
                            for (int j = 0; j < board.length; j++) {
                                if(buttonClicked == buttons[i][j]) {
                                    row = i;
                                    col = j;
                                    i = 3;
                                    break;
                                }
                            }
                        }
                        if (board[row][col] == 0) {
                            if (playerOneTurn) {
                                buttonClicked.setText("X");
                                board[row][col] = 'X';
                            } else {
                                buttonClicked.setText("O");
                                board[row][col] = 'O';
                            }
                            buttons[row][col].setTextFont(new Font("Arial", Font.PLAIN, frame.getWidth()/5));
                            turnsTaken++;
                            if (checkForWin() || checkForTie()) {
                                endGame();
                            } else {
                                playerOneTurn = !playerOneTurn;
                                if (playAgainstBot && !playerOneTurn) {
                                    makeBotMove();
                                }
                            }
                        }
                    }
                };

                if(board[row][col] != 0){
                    buttons[row][col].setText(Character.toString(board[row][col]));
                }
                // buttons[row][col].addActionListener(new ActionListener() {

                //     @Override
                //     public void actionPerformed(ActionEvent e) {
                //         JButton buttonClicked = (JButton)e.getSource();
                //         int row = 0, col = 0;
                //         for (int i = 0; i < board.length; i++) {
                //             for (int j = 0; j < board.length; j++) {
                //                 if(buttonClicked == buttons[i][j]) {
                //                     row = i;
                //                     col = j;
                //                     i = 3;
                //                     break;
                //                 }
                //             }
                //         }
                //         if (board[row][col] == 0) {
                //             if (playerOneTurn) {
                //                 buttonClicked.setText("X");
                //                 board[row][col] = 'X';
                //             } else {
                //                 buttonClicked.setText("O");
                //                 board[row][col] = 'O';
                //             }
                //             buttons[row][col].setFont(new Font("Arial", Font.PLAIN, frame.getWidth()/5));
                //             turnsTaken++;
                //             if (checkForWin() || checkForTie()) {
                //                 endGame();
                //             } else {
                //                 playerOneTurn = !playerOneTurn;
                //                 if (playAgainstBot && !playerOneTurn) {
                //                     makeBotMove();
                //                 }
                //             }
                //         }
                //     }
                    
                // });
                buttons[row][col].setTextFont(new Font("Arial", Font.PLAIN, frame.getWidth()/5));
                buttons[row][col].setFocusable(false);
                frame.add(buttons[row][col]);
            }
        }
    }

    // public void actionPerformed(ActionEvent e) {
        
    // }

    private boolean checkForWin() {
        // Check for horizontal wins
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                buttons[row][1].getText().equals(buttons[row][2].getText()) &&
                !buttons[row][0].getText().equals("")) {
                return true;
            }
        }
        
        // Check for vertical wins
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                buttons[1][col].getText().equals(buttons[2][col].getText()) &&
                !buttons[0][col].getText().equals("")) {
                return true;
            }
        }
        
        // Check for diagonal wins
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            return true;
        }
        
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText()) &&
            !buttons[0][2].getText().equals("")) {
            return true;
        }
        return false;
    }

    private boolean checkForTie() {
        return turnsTaken == 9;
    }

    private void endGame() {
        // for (int row = 0; row < 3; row++) {
        //     for (int col = 0; col < 3; col++) {
        //         buttons[row][col].setEnabled(false);
        //     }
        // }
        // JOptionPane.showMessageDialog(this, getResultMessage());
        // resetGame();
        if(!checkForTie()){
            Object stat = getStat("Single Player Games Won");
            if(players == 1){
                Object games = getStat("Single Player Games Won");
                updateStat("Single Player Games Won", Integer.parseInt(games==null?"0":games.toString())+1);
            }
            else {
                Object games = getStat("Two Player Games Played");
                updateStat("Two Player Games Played", Integer.parseInt(games==null?"0":games.toString())+1);
            }
        }
        exit();
    }

    private String getResultMessage() {
    	if (checkForWin()) {
    		if (playerOneTurn) {
				return "Player 2 wins!";
    		} else {
    			return "Player 1 wins!";
    		}
    	} else {
    		return "It's a tie!";
    	}
	}
    
    
    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("");
            }
        }
        playerOneTurn = true;
        turnsTaken = 0;
    }

    private void makeBotMove() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != 0);

        buttons[row][col].setText("O");
        board[row][col] = 'O';
        turnsTaken++;
        if (checkForWin() || checkForTie()) {
            endGame();
        } else {
            playerOneTurn = !playerOneTurn;
        }
    }

    @Override
    public String getDescription() {
        return "Tic Tac Toe";
    }

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public void run(int players) {
        Object stat = getStat("Single Player Games Won");
        if(stat == null)
            updateStat("Single Player Games Won", 0);
        stat = getStat("Two Player Games Won");
            if(stat == null)
                updateStat("Two Player Games Won", 0);
        turnsTaken = 0;
        playAgainstBot = players == 1;
        this.players = players;
        board = new char[3][3];
        resume();
    }

    @Override
    public void resume() {
        frame = new JFrame("Tic Tac Toe");
        // super("Tic Tac Toe");
        frame.setSize(800, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));
        initializeButtons();
        addWindowListener();

        // int result = JOptionPane.showConfirmDialog(this, "Do you want to play against a bot?", "Choose Opponent", JOptionPane.YES_NO_OPTION);
        // playAgainstBot = result == JOptionPane.YES_OPTION;

        frame.setVisible(true);
    }

    @Override
    public int maxPlayers() {
        return 2;
    }

    @Override
    public boolean nextUnlocked() {
        return true;
    }

    public String getImage()
    {
        return "TicTacToe.png";
    }
}
