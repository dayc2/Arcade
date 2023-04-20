package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


import com.Game;

public class Blackjack extends Game{
	
	private JFrame frame;

	public static List<Card> deck = new ArrayList<Card>();
	
	public void run(int players) {
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1000, 900);
		frame.setResizable(false);
		frame.setTitle("Blackjack");
		frame.setVisible(true);
		//frame.getContentPane().setBackground(new Color(0x0a7a0e));
		
		JLabel label = new JLabel();
		label.setText("This is a test label");
		
		BufferedImage img;
		try {
			URL url = Blackjack.class.getResource("/com/Images/Blackjack/10_of_clubs.png");
			img = ImageIO.read(url);
			Image image = img.getScaledInstance((int) (img.getWidth()), (int) (img.getHeight()), Image.SCALE_SMOOTH);
			ImageIcon brandNewImage = new ImageIcon(image);
		
			label.setIcon(brandNewImage);
			frame.add(label);
		}catch(IOException e){
			System.out.println("IMAGE NOT FOUND");
            e.printStackTrace();            
            return;
		}
		
		
		
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
		
		
		
		void updatePanel(){
			
		}
		
	}
	
	
	
	
}










