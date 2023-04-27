package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class Player {

	private int money = 50;
	private List<Card> hand = new ArrayList<>();
	JPanel playerPanel = new JPanel();
	JLabel nameLabel = new JLabel();
	JLabel totalLabel = new JLabel();
	
	public Player(){
		//does nothing
	}
	
	public int getMoney () {
		return money;
	}
	
	public List<Card> getHand(){
		return hand;
	}
	
	public void setHand(List<Card> newHand) {
		hand = newHand;
	}
	
	public int getHandSize() {
		return (hand.size());
	}
	
	public void addCard(Card newCard) {
		hand.add(newCard);
	}
	
	public void setMoney(int gain) {
		money = (money + gain);
	}
	
	public int getValue() {
		int result = 0;
		int cycle = 0;
		int aceLocation = -1;
		
		while(cycle < hand.size()) {
			result = (result + hand.get(cycle).getValue());
			if(11 == hand.get(cycle).getValue()) {
				aceLocation = cycle;
			}
			if((result > 21) && (aceLocation != -1)) {
				hand.get(aceLocation).lowerAce();
				result = getValue();
			}
			cycle++;
		}
		
		
		return result;
	}
	
	public void makeSpace(JFrame frame, int x, int y, String p) {
		Border border = BorderFactory.createLineBorder(Color.black);
		nameLabel.setBounds(10, 269, 160, 81);
		totalLabel.setBounds(160, 269, 80, 81);
		playerPanel.setBounds(x, y, 320, 360);
		playerPanel.setLayout(null);
		playerPanel.setBorder(border);
		nameLabel.setText(p);
		totalLabel.setText("Total: " + getValue());
		playerPanel.add(nameLabel);
		playerPanel.add(totalLabel);
		frame.add(playerPanel);
		playerPanel.setBackground(Color.green);
	}
	
	public void updateSpace(JFrame frame, String specialType) {
		System.out.println("called");
		JLabel label = new JLabel();
		if (hand.size() <= 5) {
			label.setBounds(((hand.size() - 1) * 64), 0, 64, 93);
		}else if (hand.size() <= 10) {
			label.setBounds(((hand.size() - 6) * 64), 93, 64, 93);
		}else {
			label.setBounds(((hand.size() - 11) * 64), 186, 64, 93);
		}
		BufferedImage img = null;
		System.out.println(("Arcade/src/com/Images/BlackjackCards/" + hand.get(hand.size() - 1).getURL()));
		try {
			URL url;
			if(specialType == "special1"){
				url = Blackjack.class.getResource("/com/images/BlackjackCards/CardBack.png");
			} else{
				url = Blackjack.class.getResource("/com/Images/BlackjackCards/" + hand.get(hand.size() - 1).getURL());
			}
		    // img = ImageIO.read(new File(hand.get(hand.size() - 1).getURL()));
			img = ImageIO.read(url);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(dimg);
		

		if(specialType == "none"){
			totalLabel.setText("Total: " + getValue());
		}else{
			totalLabel.setText("Total: ? + " + (getValue() - hand.get(0).getValue()));
		}
		
		label.setIcon(image);
		label.setBackground(Color.blue);
		playerPanel.add(label);
		frame.revalidate();
		frame.repaint();
		
	}
	
}
