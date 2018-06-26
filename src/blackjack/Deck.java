package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {

	private ArrayList<Card> deck;
	private int i=0;
	
	public Deck() {
		initialize();
	}

	private void initialize() {

		String[] ranks = { "A", "A", "A", "A", "A", "A", "8", "A", "10", "A", "A", "K", "A" };
		String[] suites = { "Club", "Diamond", "Heart", "Spade" };
		

		deck = new ArrayList<>();
		Card card;

		for (; i < ranks.length; i++) {
			for (int j = 0; j < suites.length; j++) {
				
				card = new Card(ranks[i], suites[j], cardValue(ranks));

				deck.add(card);
			}
		}
		shuffle();
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public int cardValue(String[] string) {
		int value = 0;
		
		try {
			value = Integer.parseInt(string[i]);
		} catch (NumberFormatException e) {
			if (string[i].equals("A")) {
				value = 11;
			} else {
				value = 10;
			}

		}
		return value;
	}

	
	public ArrayList<Card> getDeck() {
		return this.deck;
	}

}
