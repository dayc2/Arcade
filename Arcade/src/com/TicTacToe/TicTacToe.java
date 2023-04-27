import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
	
    private JButton[][] buttons = new JButton[3][3];
    
    private boolean playerOneTurn = true;
    
    private int turnsTaken = 0;
    
    private boolean playAgainstBot;

    public TicTacToe() {
        super("Tic Tac Toe");
        setSize(1000, 1000);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        initializeButtons();

        
        int result = JOptionPane.showConfirmDialog(this, "Do you want to play against a bot?", "Choose Opponent", JOptionPane.YES_NO_OPTION);
        playAgainstBot = result == JOptionPane.YES_OPTION;

        setVisible(true);
    }

    private void initializeButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton)e.getSource();
        if (buttonClicked.getText().equals("")) {
            if (playerOneTurn) {
                buttonClicked.setText("X");
            } else {
                buttonClicked.setText("O");
            }
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
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, getResultMessage());
        resetGame();
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
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText("O");
        turnsTaken++;
        if (checkForWin() || checkForTie()) {
            endGame();
        } else {
            playerOneTurn = !playerOneTurn;
        }
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
    }
}
