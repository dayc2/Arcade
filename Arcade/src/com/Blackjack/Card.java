package com.Blackjack;


public class Card {

	private int value;
	private String type;
	private String suit;
	private int number;
	private int originalNumber;
	String url = "Arcade/src/com/Images/BlackjackCards/";
	
	public Card(int input) {
		originalNumber = input;
		number = (originalNumber % 52);
		
		
		
		if((number % 13) == 0) {
			type = "Ace";
			value = 11;
			url += "ace_of_";
		}else if((number % 13) == 1) {
			type = "Two";
			value = 2;
			url += "2_of_";
		}else if((number % 13) == 2) {
			type = "Three";
			value = 3;
			url += "3_of_";
		}else if((number % 13) == 3) {
			type = "Four";
			value = 4;
			url += "4_of_";
		}else if((number % 13) == 4) {
			type = "Five";
			value = 5;
			url += "5_of_";
		}else if((number % 13) == 5) {
			type = "Six";
			value = 6;
			url += "6_of_";
		}else if((number % 13) == 6) {
			type = "Seven";
			value = 7;
			url += "7_of_";
		}else if((number % 13) == 7) {
			type = "Eight";
			value = 8;
			url += "8_of_";
		}else if((number % 13) == 8) {
			type = "Nine";
			value = 9;
			url += "9_of_";
		}else if((number % 13) == 9) {
			type = "Ten";
			value = 10;
			url += "10_of_";
		}else if((number % 13) == 10) {
			type = "Jack";
			value = 10;
			url += "jack_of_";
		}else if((number % 13) == 11) {
			type = "Queen";
			value = 10;
			url += "queen_of_";
		}else if((number % 13) == 12) {
			type = "King";
			value = 10;
			url += "king_of_";
		}
		if(number < 13) {
			suit = "Hearts";
			url += "hearts";
		}else if(number < 26) {
			suit = "Clubs";
			url += "clubs";
		}else if(number < 39) {
			suit = "Diamonds";
			url += "diamonds";
		}else {
			suit = "Spades";
			url += "spades";
		}
		url += ".png";
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
	
	public String getURL() {
		return url;
	}
	
}
