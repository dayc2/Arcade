package com.Connect4;
public class Piece {
    Color color;

    public Piece(Color color){
        this.color = color;
    }
    public String toString(){return color.toString();}
}
