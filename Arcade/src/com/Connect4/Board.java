package com.Connect4;

public class Board {

    int height = 6;
    int width = 7;

    Color turn;

    public Piece[] board;

    public boolean winner = false;

    public int lastDrop;

    Board(){
        this(7, 6);
    }

    Board(int width, int height){
        this.height = height;
        this.width = width;
        turn = Color.RED;
        board = new Piece[height*width];
        for (int i = 0; i < board.length; i++) {
            board[i] = new Piece(Color.NONE);
        }
    }

    Board(Board b){
        this(b.width, b.height);
        this.turn = b.turn;
        // System.arraycopy(b.board, 0, this.board, 0, b.board.length);
        for (int i = 0; i < b.board.length; i++) {
            board[i].color = b.board[i].color;
        }
        this.lastDrop = b.lastDrop;
        this.winner = b.winner;
    }

    public void reset(){
        turn = Color.RED;
        for (int i = 0; i < board.length; i++) {
            board[i] = new Piece(Color.NONE);
        }
        winner = false;
    }

    private boolean hasMoves(){
        for (int i = 0; i <= width; i++) {
            if(board[i].color == Color.NONE)
                return true;
        }
        return false;
    }

    public boolean drop(int column){
        if(winner) return false;
        column = column % width;
        if(column > width  || column < 0|| board[column].color != Color.NONE)
            return false;

        for(int i = board.length-width; i >= 0; i-=width){
            if(board[column + i].color == Color.NONE){
                board[column + i].color = turn;
                lastDrop = column + i;
                if(checkWinner(column + i)){
                    winner = true;
                    return true;
                }
                turn = turn.next();
                if(!hasMoves()){
                    winner = true;
                    turn = Color.NONE;
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkWinner(int pos) {
        for (int i = 0; i < 7; i++) {
            if(checkVertical(pos) || checkHorizontal(pos) || checkDiagonalLeft(pos) || checkDiagonalRight(pos))
                return true;
        }
        // if(checkVertical(pos)){System.out.println("vertically"); return true;}   For testing
        // if(checkHorizontal(pos)){System.out.println("Horizontally"); return true;}
        // if(checkDiagonalLeft(pos)){System.out.println("diagonal left"); return true;}
        // if(checkDiagonalRight(pos)){System.out.println("diagonal right"); return true;}
        return false;
    }

    private boolean checkDiagonalRight(int pos) {
        for (int i = 0; i < 5; i++) {
            if((pos-(width+1)*(3-i))%width < width-3 && (pos-(width+1)*(3-i)) >= 0 && (pos+(width+1)*(i)) < board.length){
                for (int j = pos-(width+1)*(3-i); j <= pos+(width+1)*(i); j+=(width+1)) {
                    if(board[j].color != board[pos].color){
                        break;
                    }
                    if(j==pos+(width+1)*(i)) return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalLeft(int pos) { 
        for (int i = 0; i < 5; i++) {
            if((pos+(width-1)*(3-i))%width < width-3 && (pos+(width-1)*(3-i)) < board.length && (pos-(width-1)*(i)) >= 0){
                for (int j = pos+(width-1)*(3-i); j >= pos-(width-1)*(i); j-=(width-1)) {
                    if(board[j].color != board[pos].color){
                        break;
                    }
                    if(j==pos-(width-1)*(i)) return true;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(int pos) {
        for (int i = 0; i < 5; i++) {
            if((pos + i - 3)% width < width-3 && pos + i < board.length && pos+i-3 >= 0)
                for (int j = pos+i-3; j < pos+i+1; j++) {
                    if(board[j].color != board[pos].color){
                        break;
                    }
                    if(j==pos+i) return true;
                }
        }
        return false;
    }

    private boolean checkVertical(int pos){
        for (int i = 0; i < 4; i++) {
            if(pos + 7 * i >= board.length) return false;
            if(!(board[pos + i * 7].color == board[pos].color)){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < board.length; i++){
            s.append(' ').append(board[i].color.toString()).append(' ');
            if(((i+1) % width) == 0)
                s.append("\n");
        }
        return s.toString();
        
    }
}
