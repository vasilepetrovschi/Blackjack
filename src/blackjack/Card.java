package blackjack;

public class Card {

	private String rank;
	private String suite;
	private int value;

	public Card(String rank, String suite, int value) {
		super();
		this.rank = rank;
		this.suite = suite;
		this.value = value;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return rank + " " + suite;
	}

}
