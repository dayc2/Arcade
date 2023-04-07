package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private int money;
	private List<Card> hand = new ArrayList<>();
	
	public Player(int m, List<Card> h){
		money = m;
		hand = h;
	}
	
	public int getMoney () {
		return money;
	}
	
	public List<Card> getHand(){
		return hand;
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
}
