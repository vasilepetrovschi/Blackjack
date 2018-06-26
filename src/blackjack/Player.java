package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player {

	private String[] name;
	private ArrayList<String> players;
	

	public Player(String... name) {
		super();
		this.name = name;
		addPlayer();
	}

	private void addPlayer() {
		players = new ArrayList<>();
		Collections.addAll(players, name);
	}

	

	public ArrayList<String> getPlayers() {
		return this.players;
	}


}
