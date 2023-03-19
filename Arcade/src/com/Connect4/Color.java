package com.Connect4;
public enum Color {


    RED{
        public java.awt.Color getColor(){ return new java.awt.Color(255, 0, 0);}
        public String toString(){return "R";}
    }, YELLOW{
        public java.awt.Color getColor(){ return new java.awt.Color(255, 255, 60);}
        public String toString(){return "Y";}
    }, NONE{
        public java.awt.Color getColor(){ return new java.awt.Color(220, 220, 220);}
        public String toString(){return " ";}
    };

    public abstract java.awt.Color getColor();

    public Color next(){
        return (this == RED ? YELLOW : RED);
    }
}
