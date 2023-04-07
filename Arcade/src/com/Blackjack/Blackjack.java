package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


import com.Game;

public class Blackjack extends Game{
	
	private JFrame frame;

	public static List<Card> deck = new ArrayList<Card>();
	
	public static void main(String[] args) {
		
		List<List<Card>> hands = new ArrayList<>(); 
		int players = 5;
		int cycle = 0;
		boolean game = true;
		boolean round = true;
		
		deck = createDeck();
		
		while(cycle < players) {
			List<Card> hand = new ArrayList<Card>();
			hands.add(hand);
			cycle++;
		}
		cycle = 0;

		while(game) {
			while(round) {
				while(cycle < players) {
					hands.get(cycle).add(hit());
					hands.get(cycle).add(hit());
					cycle++;
				}
				
				round = false;
			}
			game = false;
		}
		cycle = 0;
		while(cycle  < hands.size()) {
			System.out.println("Hand " + (cycle + 1));
			printHand(hands.get(cycle));
			System.out.println("");
			cycle++;
		}
		
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
}

