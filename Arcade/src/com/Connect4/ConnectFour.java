package com.Connect4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectFour extends JFrame {
    
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int CELL_SIZE = 100;
    private static final int BOARD_WIDTH = COLS * CELL_SIZE;
    private static final int BOARD_HEIGHT = ROWS * CELL_SIZE;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final Color PLAYER_1_COLOR = Color.RED;
    private static final Color PLAYER_2_COLOR = Color.YELLOW;
    
    private int[][] board = new int[ROWS][COLS];
    private int currentPlayer = 1;
    private boolean gameWon = false;
    
    private JPanel boardPanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x = col * CELL_SIZE;
                    int y = (ROWS - 1 - row) * CELL_SIZE;
                    if (board[row][col] == 1) {
                        g.setColor(PLAYER_1_COLOR);
                        g.fillOval(x, y, CELL_SIZE, CELL_SIZE);
                    } else if (board[row][col] == 2) {
                        g.setColor(PLAYER_2_COLOR);
                        g.fillOval(x, y, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    };
    
    public ConnectFour() {
        setTitle("Connect Four");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameWon) {
                    int col = e.getX() / CELL_SIZE;
                    if (isValidMove(col)) {
                        int row = getNextOpenRow(col);
                        board[row][col] = currentPlayer;
                        boardPanel.repaint();
                        if (isWinningMove(row, col)) {
                            gameWon = true;
                            String winner = "Player " + currentPlayer + " wins!";
                            setTitle(winner);
                        } else if (isBoardFull()) {
                            setTitle("Game over - tie!");
                        } else {
                            currentPlayer = (currentPlayer == 1) ? 2 : 1;
                            setTitle("Player " + currentPlayer + "'s turn");
                        }
                    }
                }
            }
        });
        
        boardPanel.setBackground(Color.BLUE);
        boardPanel.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        add(boardPanel);
        setVisible(true);
    }
    
    private boolean isValidMove(int col) {
        if (col < 0 || col >= COLS) {
            return false;
        }
        return board[ROWS-1][col] == 0;
    }
    
    private int getNextOpenRow(int col) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }

    private boolean isWinningMove(int row, int col) {
        int color = board[row][col];
        // Check horizontal
        int count = 0;
        for (int c = 0; c < COLS; c++) {
            if (board[row][c] == color) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        // Check vertical
        count = 0;
        for (int r = 0; r < ROWS; r++) {
            if (board[r][col] == color) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        // Check diagonal (top-left to bottom-right)
        count = 0;
        int r = row, c = col;
        while (r > 0 && c > 0) {
            r--;
            c--;
        }
        while (r < ROWS && c < COLS) {
            if (board[r][c] == color) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            r++;
            c++;
        }
        // Check diagonal (top-right to bottom-left)
        count = 0;
        r = row;
        c = col;
        while (r > 0 && c < COLS - 1) {
            r--;
            c++;
        }
        while (r < ROWS && c >= 0) {
            if (board[r][c] == color) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            r++;
            c--;
        }
        return false;
    }
    
    private boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[ROWS-1][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        new ConnectFour();
    }
}    