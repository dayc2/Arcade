package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private int money = 50;
	private List<Card> hand = new ArrayList<>();
	
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
}
