package com.Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class word {
	public ArrayList<String> display = new ArrayList<String>(HangmanAI.answer);
	
	public static void display() {
		// System.out.println(HangmanAI.alphabet.toString());
	}

	public static void correctDisplay(ArrayList<String> correct, int index, String letter) {
		correct.add(index, letter + " ");
		correct.remove(index + 1);
		// System.out.println(correct.toString());
		// if(!(correct.contains("_ ")))
		// 	System.out.println("You Win!");
	}
	
	public static void correctDisplay(ArrayList<String> correct) {
		System.out.println(correct.toString());
	}

}



public class HangmanAI {
	public static int strikes = 0;
	public static ArrayList<String> answer = new ArrayList<String>();
	public static ArrayList<String> alphabet = new ArrayList<String>();
	public static ArrayList<String> correctGuess = new ArrayList<String>();
	public static ArrayList<String> workingAnswer = new ArrayList<String>();

	public static ArrayList<String> picker() throws FileNotFoundException {
		String temp = "";
		strikes = 0;
		try (Scanner sc = new Scanner(new File("Arcade/src/com/Hangman/wordReserve.txt"))) {
			Scanner sc2 = new Scanner(new File("Arcade/src/com/Hangman/wordReserve.txt"));
			int num = 0;
			while(sc2.hasNext()) {
				num++;
				sc2.next();
			}
			int randomNum = (int) Math.round(Math.random() * (num - 1) + 1);
			for(int i = 0; i < randomNum; i++) {
				temp = sc.next();
			}
			sc.close();
			temp = temp.strip();
			for(int i = 0; i < temp.length(); i++) {
				String let = "";
				let += temp.toLowerCase().charAt(i);
				answer.add(let);
			}
			
			
			workingAnswer = new ArrayList<String>();
			correctGuess = new ArrayList<String>();
			for(int i = 0; i < answer.size(); i++) {
				workingAnswer.add(answer.get(i));
				correctGuess.add("");
			}
			
			return answer;
		}
	}
	
	public static void guess(String s) {
		int count = 0;
		if(!workingAnswer.contains(s)) {
			strikes++;
		} else {
			do {
				if(answer.get(count).compareTo(s) == 0) {
					Hangman.displayLetter(s, count);
					workingAnswer.remove(s);
					correctGuess.add(count, s);
				}
				count++;
			} while(count < answer.size());
		}
	}

	public static void restart() {
		strikes = 0;
		alphabet.removeAll(alphabet);
		answer.removeAll(answer);
		workingAnswer.removeAll(workingAnswer);
		String letters = "qwertyuiopasdfghjklzxcvbnm";
		int i = 0;
		while(i < 26) {
			String letter = "";
			letter += letters.charAt(i);
			alphabet.add(letter);
			i++;
		}
		try {
			picker();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static int findIndex(ArrayList<String> answer, String guess) {
		for(int i = 0; i < answer.size(); i++) {
			if(guess.compareTo(answer.get(i)) == 0)
				return i;
		}
		
		return 0;
	}

	public static boolean checkWin() {
		if(workingAnswer.isEmpty())
			return true;
		return false;
	}

	public static boolean checkLose() {
		if(strikes == 6)
			return true;
		return false;
	}
	
	
	
}