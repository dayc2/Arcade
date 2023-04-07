package com.Blackjack;


public class Card {

	private int value;
	private String type;
	private String suit;
	private int number;
	private int originalNumber;
	
	public Card(int input) {
		originalNumber = input;
		number = (originalNumber % 52);
		
		if(number < 13) {
			suit = "Hearts";
		}else if(number < 26) {
			suit = "Clubs";
		}else if(number < 39) {
			suit = "Diamonds";
		}else {
			suit = "Spades";
		}
		if((number % 13) == 0) {
			type = "Ace";
			value = 11;
		}else if((number % 13) == 1) {
			type = "Two";
			value = 2;
		}else if((number % 13) == 2) {
			type = "Three";
			value = 2;
		}else if((number % 13) == 3) {
			type = "Four";
			value = 2;
		}else if((number % 13) == 4) {
			type = "Five";
			value = 2;
		}else if((number % 13) == 5) {
			type = "Six";
			value = 2;
		}else if((number % 13) == 6) {
			type = "Seven";
			value = 2;
		}else if((number % 13) == 7) {
			type = "Eight";
			value = 2;
		}else if((number % 13) == 8) {
			type = "Nine";
			value = 2;
		}else if((number % 13) == 9) {
			type = "Ten";
			value = 2;
		}else if((number % 13) == 10) {
			type = "Jack";
			value = 2;
		}else if((number % 13) == 11) {
			type = "Queen";
			value = 2;
		}else if((number % 13) == 12) {
			type = "King";
			value = 2;
		}
	}
	
	public void lowerAce() {
		value = 1;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getOriginalNumber() {
		return originalNumber;
	}
	
	public int getNumber() {
		return number;
	}
	
}
