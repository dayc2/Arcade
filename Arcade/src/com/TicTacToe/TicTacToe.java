package com.TicTacToe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Game;

public class TicTacToe extends Game{

    private char[] board;
    private boolean turn;
    private int squareSize = 150;
    @Override
    public String getDescription() {
        return "Description";
    }

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public void run(int players) {
        this.players = players;
        board = new char[9];
        resume();

    }

    @Override
    public void resume() {
        frame = new JFrame();
        addWindowListener();

        frame.add(new GamePanel());

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public int maxPlayers() {
        return 2;
    }
    
    private class GamePanel extends JPanel{
        
        private SquarePanel[] panels;
        GamePanel(){
            setPreferredSize(new Dimension(squareSize * 3, squareSize * 3));
            panels = new SquarePanel[9];
            setLayout(new GridLayout(3, 3));
            for (int i = 0; i < board.length; i++) {
                panels[i] = new SquarePanel(i);
                panels[i].updatePanel(board[i]);
                this.add(panels[i]);
            }
        }

        private class SquarePanel extends JPanel{

            final int id;
            SquarePanel(int id){
                this.id = id;
                setPreferredSize(new Dimension(squareSize, squareSize));
                addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if(board[id] != 0) return;
                        if(!turn){
                            board[id] = 'x';
                            updatePanel('x');
                        }else{
                            board[id] = 'o';
                            updatePanel('o');
                        }
                        if(checkWinner(board)){
                            System.out.println("human winner");
                            exit();
                            return;
                        }else if(board[0] != 0 && board[1] != 0 && board[2] != 0 && board[3] != 0 && board[4] != 0 && board[5] != 0 && board[6] != 0 && board[7] != 0 && board[8] != 0){
                            System.out.println("TIE");
                            exit();
                        }
                        System.out.println(Arrays.toString(board));
                        turn = !turn;
                        if(players == 1 && turn){
                            int move = computerMove(board, 10);
                            if(move == -1){
                                System.out.println(move);
                                exit();
                                return;
                            }
                            board[move] = !turn? 'x' : 'o';
                            panels[move].updatePanel(board[move]);
                            turn = !turn;
                            if(checkWinner(board)){
                                System.out.println("comp winner");
                                exit();
                            }
                            System.out.println(Arrays.toString(board));
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                    
                });
            }
            public void updatePanel(char c) {
                if(c == 'x'){
                    setBackground(Color.RED);
                }else if(c == 'o'){
                    setBackground(Color.BLUE);
                }else{
                    setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    boolean checkWinner(char[] board){
        if(board[0] != 0){
            if(board[0] == board[1] && board[1] == board[2]) return true;
            if(board[0] == board[3] && board[3] == board[6]) return true;
            if(board[0] == board[4] && board[4] == board[8]) return true;
        }
        if(board[6] != 0){
            if(board[6] == board[4] && board[4] == board[2]) return true;
            if(board[6] == board[7] && board[7] == board[8]) return true;
        }
        if(board[4] != 0){
            if(board[4] == board[3] && board[3] == board[5]) return true;
            if(board[4] == board[1] && board[1] == board[7]) return true;
        }if(board[2] != 0){
            if(board[2] == board[5] && board[5] == board[8]) return true;
        }
        return false;
    }

    // int computerMove(){
    //     if(move == 0) return 0;
    //     if(move == 1){
    //         if(board[0] != 0 || board[2] != 0 || board[6] != 0 || board[8] != 0) return 4;
    //         if(board[1] != 0 || board[3] != 0 || board[5] != 0 || board[7] != 0) return 4;
    //     }
    //     return 0;
    // }

    // int DFS(char[] arr, boolean turn, int depth){
    //     ArrayList<Integer> moves = new ArrayList<>();
    //     for (int i = 0; i < 9; i++) {
    //         if(arr[i] == 0) moves.add(i);
    //     }
    //     // int best = turn? Integer.MAX_VALUE: Integer.MIN_VALUE;
    //     int bestMove = -1;
    //     for (Integer move : moves) {
    //         char[] newArr = new char[9];
    //         System.arraycopy(arr, 0, newArr, 0, 9);
    //         if(turn){
    //             newArr[move] = 'x';
    //         }else{
    //             newArr[move] = 'o';
    //         }
    //         boolean winner = checkWinner(newArr);
    //         if(winner && turn == this.turn) return move;
    //     }
        
    //     return -1;
    // }
    
    private ArrayList<Integer> list = new ArrayList<>();

    int computerMove(char[] b, int depth){
        list.clear();
        for (int i = 0; i < b.length; i++) {
            if(b[i] == 0) list.add(i);
        }
        return max(b, this.turn, -1, depth).first;
    }

    Pair<Integer, Integer> max(char[] b, boolean c, Integer lastMove, int depth){
        // Base Case
        if(checkWinner(b)){
            if(this.turn == !c) return new Pair<Integer,Integer>(lastMove, depth+1);
            if(this.turn == c) return new Pair<Integer,Integer>(lastMove, -depth-1);
            return new Pair<Integer, Integer>(lastMove, 0);
        }
        if(depth <= 0){
            return new Pair<Integer,Integer>(lastMove, 0);
        }

        int max = -1000;
        int best = 7;
        ArrayList<Integer> local = new ArrayList<>(list);
        Collections.shuffle(local);
        for (int i : local) {
            char[] next = new char[9];
            System.arraycopy(b, 0, next, 0, 9);
            if(next[i] == 0){
                if(!c){
                    next[i] = 'x';
                }else{
                    next[i] = 'o';
                }
                Pair<Integer, Integer> p = min(next, !c, i, depth-1);
                max = Math.max(max, p.second);
                if(max == p.second){
                    best = i;
                }
            }
        }

        return new Pair<Integer, Integer>(best, max);
    }

    Pair<Integer, Integer> min(char[] b, boolean c, int lastMove, int depth){
        // Base Case
        if(checkWinner(b)){
            if(this.turn == !c) return new Pair<Integer,Integer>(lastMove, depth+1);
            if(this.turn == c) return new Pair<Integer,Integer>(lastMove, -depth-1);
            return new Pair<Integer, Integer>(lastMove, 0);
        }
        if(depth <= 0){
            return new Pair<Integer,Integer>(lastMove, 0);
        }

        int max = 1000;
        int best = 7;
        ArrayList<Integer> local = new ArrayList<>(list);
        Collections.shuffle(local);
        for (int i : local) {
            char[] next = new char[9];
            System.arraycopy(b, 0, next, 0, 9);
            if(next[i] == 0){
                if(!c){
                    next[i] = 'x';
                }else{
                    next[i] = 'o';
                }
                Pair<Integer, Integer> p = max(next, !c, i, depth-1);
                max = Math.min(max, p.second);
                if(max == p.second){
                    best = i;
                }
            }
        }

        return new Pair<Integer, Integer>(best, max);
    }

    private static class Pair<T, K>{
        T first;
        K second;
        Pair(T first, K second){
            this.first = first;
            this.second = second;
        }
    }

    @Override
    public boolean nextUnlocked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextUnlocked'");
    }
}