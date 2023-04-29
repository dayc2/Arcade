package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
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
	public static List<Card> deck = new ArrayList<Card>();
	public List<Player> playerList;
	int currentPlayer = 1;
	JFrame frame;
	/* 
	JButton hitButton;
	JButton passButton;
	JButton doubleButton;
	JButton quitButton;
	*/
	
	public void run(int players) {
		
		this.players = players;

		playerList = new ArrayList<>(); 
		frame = new JFrame();

		gameSetup();

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Blackjack");
		
		frame.setVisible(true);
		
		
		
		int cycle = 0;
		
		
		
		deck = createDeck();
		cycle = 0;

		while(cycle < playerList.size()) {
			if(cycle == 0){
				hit(playerList.get(cycle), frame, "special1");
				hit(playerList.get(cycle), frame, "special2");
			} else{
				hit(playerList.get(cycle), frame, "none");
				hit(playerList.get(cycle), frame, "none");
			}
			cycle++;
		}

		/* 
		while(game) {
			
			while(cycle < playerList.size()) {
				if(cycle == 0){
					hit(playerList.get(cycle), frame, "special1");
					hit(playerList.get(cycle), frame, "special2");
				} else{
					hit(playerList.get(cycle), frame, "none");
					hit(playerList.get(cycle), frame, "none");
				}
				cycle++;
			}

			while(round) {
				int cycle1 = 0;
				int completed = 0;
				while (cycle1 < playerList.size()) {
					if (playerList.get(cycle1).getValue() <= 21) {
						if(cycle1 == 0){
							hit(playerList.get(cycle), frame, "special2");
						} else{
							hit(playerList.get(cycle), frame, "none");
						}
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
		*/
		
		
		
	}
	
	public String getDescription() {
		return "This is a standard game of Blackjack";
	}
	/* 
	public void exit() {
		System.out.println("not done yet");
	}
	*/
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

	public void hit(Player givenPlayer, JFrame frame, String specialType){
		System.out.println(currentPlayer);
		Card result;
		int choice;
		choice = (int)(Math.random() * (deck.size() - 1));
		result = deck.get(choice);
		deck.remove(choice);
		givenPlayer.addCard(result);
		givenPlayer.updateSpace(frame, specialType);
		if(specialType == "none"){
			if(currentPlayer == players){
				currentPlayer = 1;
			}else{
				currentPlayer++;
			}
		}
	}
	
	public static void printHand(List<Card> hand) {
		int cycle = 0;
		while(cycle < hand.size()) {
			//System.out.println(hand.get(cycle).getType() + " of " + hand.get(cycle).getSuit());
			cycle++;
		}
	}

	@Override
	public String getName() {
		return "Blackjack";
	}

	@Override
	public void resume() {
		frame = new JFrame();
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
		addWindowListener();
		frame.setVisible(true);

		playerList = new ArrayList<>(); 

		int cycle = 0;
		boolean game = true;
		boolean round = true;
		int players = this.players;
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
				
		cycle = 0;

		while(game) {
			
			while(cycle < playerList.size()) {
				hit(playerList.get(cycle), frame, "none");
				hit(playerList.get(cycle), frame, "none");
				cycle++;
			}
			while(round) {
				int cycle1 = 0;
				int completed = 0;
				while (cycle1 < playerList.size()) {
					if (playerList.get(cycle1).getValue() <= 21) {
						hit(playerList.get(cycle), frame, "none");
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

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public boolean nextUnlocked() {
		if(getStats().compareTo("No stats yet, play one game") == 0)
			return false;
		/**
		Integer goal = 1;
        Object stat = getStat("Ex: Games Won");
        if(Integer.parseInt(stat.toString()) >= goal)
            return true;
		 */
		return false;
	}

	public void gameSetup(){


		System.out.println("Called");
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();

		JButton hitButton = new JButton();
		JButton passButton = new JButton();
		JButton doubleButton = new JButton();
		JButton quitButton = new JButton();

		hitButton .setBounds(700, 100, 200, 100);
		hitButton.addActionListener(e -> hit(playerList.get(currentPlayer), frame, "none"));
		hitButton.setText("HIT");

		passButton .setBounds(700, 250, 200, 100);
		passButton.addActionListener(e -> System.out.println("Working"));
		passButton.setText("PASS");

		doubleButton .setBounds(700, 400, 200, 100);
		doubleButton.addActionListener(e -> System.out.println("Working"));
		doubleButton.setText("DOUBLE DOWN");

		quitButton .setBounds(700, 550, 200, 100);
		quitButton.addActionListener(e -> exitGame());
		quitButton.setText("QUIT");

		frame.add(hitButton);
		frame.add(passButton);
		frame.add(doubleButton);
		frame.add(quitButton);

		frame.revalidate();
		frame.repaint();

		int cycle = 0;
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
	}

	public void exitGame(){
		exit();
		frame.dispose();
	}
}










