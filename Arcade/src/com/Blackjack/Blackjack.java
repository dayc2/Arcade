package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


import com.Game;

public class Blackjack extends Game{
	
	private JFrame frame;

	public static List<Card> deck = new ArrayList<Card>();
	
	public void run(int players) {
		
		java.awt.Color backColor = new java.awt.Color(0, 150, 0);
		frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(700, 600));
        frame.pack();
        frame.setVisible(true);
        frame.setBackground(backColor);
        JPanel newPanel = new JPanel();
        frame.add(newPanel);
        newPanel.setBackground(backColor);
        frame.setResizable(false);
        
		
		
		List<Player> playerList = new ArrayList<>(); 
		
		int cycle = 0;
		boolean game = true;
		boolean round = true;
		
		while(cycle <= players) {
			
			playerList.add(new Player());
			cycle++;
		}
		
		deck = createDeck();
		
		cycle = 0;

		while(game) {
			while(cycle < playerList.size()) {
				hit(playerList.get(cycle));
				hit(playerList.get(cycle));
				cycle++;
			}
			while(round) {
				int cycle1 = 0;
				int completed = 0;
				while (cycle1 < playerList.size()) {
					if (playerList.get(cycle1).getValue() <= 21) {
						hit(playerList.get(cycle1));
						System.out.println("Player " + cycle1 + ": " + playerList.get(cycle1).getValue());
						System.out.println(cycle1);
					} else {
						completed++;
					}
					cycle1++;
				}
				if (cycle1 - completed == 0) {
					round = false;
				}
			}
			game = false;
		}
		cycle = 0;
		while(cycle  < playerList.size()) {
			System.out.println("Hand " + (cycle + 1));
			printHand(playerList.get(cycle).getHand());
			System.out.println(playerList.get(cycle).getValue());
			System.out.println("");
			cycle++;
		}
		
	}
	
	public String getDescription() {
		return "This is a standard game of Blackjack";
	}
	
	public void exit() {
		System.out.println("not done yet");
	}
	
	public String getHighScore() {
		return "CHANGE THIS";
	}
		
	public static List<Card> createDeck() {
		
		List<Card> result = new ArrayList<Card>();
		int cycle = 0;
		
		while (cycle < 312) {
			result.add(new Card(cycle));
			
			cycle++;
		}
		return result;
	}

	public static void hit(Player currentPlayer){
		
		Card result;
		int choice;
		choice = (int)(Math.random() * (deck.size() - 1));
		result = deck.get(choice);
		deck.remove(choice);
		currentPlayer.addCard(result);
	}
	
	public static void printHand(List<Card> hand) {
		int cycle = 0;
		while(cycle < hand.size()) {
			System.out.println(hand.get(cycle).getType() + " of " + hand.get(cycle).getSuit());
			cycle++;
		}
	}
	
	class CardPanel extends JPanel{
		
		CardPanel(){
			
		}
		
	}
	
	
	
	
}


