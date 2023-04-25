package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


import com.Game;

public class Blackjack extends Game {
	
	private JFrame frame;

	public static List<Card> deck = new ArrayList<Card>();
	
	public void run(int players) {
		
		
		JFrame frame = new JFrame();
		JButton button = new JButton();
		button .setBounds(700, 200, 100, 100);
		button.addActionListener(e -> System.out.println("Working"));
		button.setText("HIT");

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Blackjack");
		
		frame.add(button);
		
		frame.setVisible(true);
		
		
		List<Player> playerList = new ArrayList<>(); 
		
		int cycle = 0;
		boolean game = true;
		boolean round = true;
		
		while(cycle <= players) {
			
			playerList.add(new Player());
			if (cycle == 1) {
				playerList.get(cycle).makeSpace(frame, 0, 0, "PLAYER ONE");
			} else if (cycle == 2) {
				playerList.get(cycle).makeSpace(frame, 320, 0, "PLAYER TWO");
			} else if (cycle == 3) {
				playerList.get(cycle).makeSpace(frame, 0, 360, "PLAYER THREE");
			} else if (cycle == 4) {
				playerList.get(cycle).makeSpace(frame, 320, 360, "PLAYER FOUR");
			} else {
				playerList.get(cycle).makeSpace(frame, 946, 0, "DEALER");
			}
			cycle++;
		}
		
		deck = createDeck();
		
		cycle = 0;

		while(game) {
			
			while(cycle < playerList.size()) {
				hit(playerList.get(cycle), frame);
				hit(playerList.get(cycle), frame);
				cycle++;
			}
			while(round) {
				int cycle1 = 0;
				int completed = 0;
				while (cycle1 < playerList.size()) {
					if (playerList.get(cycle1).getValue() <= 21) {
						hit(playerList.get(cycle1), frame);
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

	public static void hit(Player currentPlayer, JFrame frame){
		
		Card result;
		int choice;
		choice = (int)(Math.random() * (deck.size() - 1));
		result = deck.get(choice);
		deck.remove(choice);
		currentPlayer.addCard(result);
		currentPlayer.updateSpace(frame);
	}
	
	public static void printHand(List<Card> hand) {
		int cycle = 0;
		while(cycle < hand.size()) {
			System.out.println(hand.get(cycle).getType() + " of " + hand.get(cycle).getSuit());
			cycle++;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getName'");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'resume'");
	}

	@Override
	public int maxPlayers() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'maxPlayers'");
	}
	
	
	
	
	
}










