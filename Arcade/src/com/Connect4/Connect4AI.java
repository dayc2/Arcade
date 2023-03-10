package com.Connect4;

import java.util.ArrayList;
import java.util.Collections;

public class Connect4AI {

    private static ArrayList<Integer> list = new ArrayList<>();
    
    static int computerMove(Board b){
        return computerMove(b, 4);
    }

    static int computerMove(Board b, int depth){
        list.clear();
        for (int i = 0; i < b.width; i++) {
            list.add(i);
        }
        return max(b, b.turn, -1, depth).first;
    }

    static Pair<Integer, Integer> max(Board b, Color c, Integer lastMove, int depth){
        // Base Case
        if(b.winner){
            if(b.turn == c) return new Pair<Integer,Integer>(lastMove, depth+1);
            if(b.turn == c.next()) return new Pair<Integer,Integer>(lastMove, -depth-1);
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
            Board next = new Board(b);
            if(next.drop(i)){
                Pair<Integer, Integer> p = min(next, c, i, depth-1);
                max = Math.max(max, p.second);
                if(max == p.second){
                    best = i;
                }
            }
        }

        return new Pair<Integer, Integer>(best, max);
    }

    static Pair<Integer, Integer> min(Board b, Color c, int lastMove, int depth){
        // Base Case
        if(b.winner){
            if(b.turn == c) return new Pair<Integer,Integer>(lastMove, depth+1);
            if(b.turn == c.next()) return new Pair<Integer,Integer>(lastMove, -depth-1);
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
            Board next = new Board(b);
            if(next.drop(i)){
                Pair<Integer, Integer> p = max(next, c, i, depth-1);
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
}


