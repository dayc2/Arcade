package com.Blackjack;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import com.Game;

public class Blackjack extends Game {
	public static List<Card> deck = new ArrayList<Card>();
	public List<Player> playerList;
	int currentPlayer = 1;
	JFrame frame;
	JLabel turnLabel;
	JLabel winnerLabel;
	
	JButton hitButton;
	JButton passButton;
	JButton doubleButton;
	JButton quitButton;
	
	
	public void run(int players) {
		
		this.players = players;

		playerList = new ArrayList<>(); 
		

		gameSetup();

		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Blackjack");
		
		frame.setVisible(true);
		
		
        Object games = getStat("Games Played");
        updateStat("Games Played", Integer.parseInt(games==null?"0":games.toString())+1);
		
		
	}
	
	public String getDescription() {
		return "This is a standard game of Blackjack with up to 4 players. The goal is to get as close to 21 as possible without going over.";
	}

	public String getImage(){
        return "blackjack.png";
    }
	/* 
	public void exit() {
		System.out.println("not done yet");
	}
	
	public String getStats() {
		//return ("Games played: " + gamesPlayed);
		return "CHANGE";
	}
	*/
		
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
		//System.out.println(currentPlayer);
		Card result;
		int choice;
		int checks = 0;
		choice = (int)(Math.random() * (deck.size() - 1));
		result = deck.get(choice);
		deck.remove(choice);
		givenPlayer.addCard(result);
		givenPlayer.updateSpace(frame, specialType);

		if(specialType == "none"){
			while(checks < 7){
				if(currentPlayer == players){
					currentPlayer = 1;
				}else{
					currentPlayer++;
				}
				if(!(playerList.get(currentPlayer).done())){
					break;
				}
				checks++;
			}
			if(checks == 7){
				while(playerList.get(0).getValue() < 17){
					hit(playerList.get(0), frame, "special2");
				}
				playerList.get(0).updateSpace(frame, "special3");
				winnerLabel.setText(findWinner());
				frame.add(winnerLabel);
				hitButton.setEnabled(false);
				passButton.setEnabled(false);
		
				frame.revalidate();
				frame.repaint();

				//exitGame();
			}
			turnLabel.setText("It is Player " + currentPlayer + "'s turn");
			frame.revalidate();
			frame.repaint();
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
		this.players = players;

		playerList = new ArrayList<>(); 
		frame = new JFrame();

		gameSetup();

		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

		currentPlayer = 1;
		frame = new JFrame();

		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();

		turnLabel = new JLabel();
		hitButton = new JButton();
		passButton = new JButton();
		doubleButton = new JButton();
		quitButton = new JButton();
		winnerLabel = new JLabel();

		winnerLabel.setBounds(960, 540, 320, 180);

		turnLabel.setBounds(960, 360, 320, 180);

		hitButton.setBounds(700, 100, 200, 100);
		hitButton.addActionListener(e -> hit(playerList.get(currentPlayer), frame, "none"));
		hitButton.setText("HIT");

		passButton .setBounds(700, 250, 200, 100);
		passButton.addActionListener(e -> pass());
		passButton.setText("PASS");

		/* 
		doubleButton .setBounds(700, 400, 200, 100);
		doubleButton.addActionListener(e -> System.out.println("Working"));
		doubleButton.setText("DOUBLE DOWN");
		*/

		quitButton .setBounds(700, 550, 200, 100);
		quitButton.addActionListener(e -> exitGame());
		quitButton.setText("QUIT");

		frame.add(hitButton);
		frame.add(passButton);
		frame.add(doubleButton);
		frame.add(quitButton);

		frame.add(turnLabel);

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

		}

	public void exitGame(){
		frame.dispose();
		exit();
	}

	public void pass(){
		int checks = 0;
		playerList.get(currentPlayer).passing();

		while(checks < 7){
			if(currentPlayer == players){
				currentPlayer = 1;
			}else{
				currentPlayer++;
			}
			if(!(playerList.get(currentPlayer).done())){
				break;
			}
			checks++;
		}
		if(checks == 7){
			while(playerList.get(0).getValue() < 17){
				hit(playerList.get(0), frame, "special2");
			}
			playerList.get(0).updateSpace(frame, "special3");
			winnerLabel.setText(findWinner());
			frame.add(winnerLabel);
			frame.revalidate();
			frame.repaint();

			hitButton.setEnabled(false);
			passButton.setEnabled(false);
		}
		turnLabel.setText("It is Player " + currentPlayer + "'s turn");
		frame.revalidate();
		frame.repaint();
	}

	public String findWinner(){
		int cycle = 0;
		int highest = 0;
		String result = "";
		while(cycle < playerList.size()){
			if((playerList.get(cycle).getValue() > highest) && (playerList.get(cycle).getValue() < 22) && (cycle == 0)){
				highest = playerList.get(cycle).getValue();
				result = ("The Winner is the dealer");
			}else if((playerList.get(cycle).getValue() > highest) && (playerList.get(cycle).getValue() < 22)){
				highest = playerList.get(cycle).getValue();
				result = ("The Winner is Player " + cycle);
			} else if(playerList.get(cycle).getValue() == highest){
				result = ("It is a tie between " + result.substring(14) + " and player " + cycle);
			} 
			cycle++;
		}

		hitButton.setEnabled(false);
		return result;
	}
	
}










