package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Blackjack {

	static Card dealerHiddenCard;
	static Deck deck = new Deck();
	static HashMap<String, Integer> sumPlayer;
	static HashMap<String, Integer> sumPlayerSplit = new HashMap<>();
	static HashMap<String, Integer> playerNumberAceCards = new HashMap<>();
	static HashMap<String, Integer> playerNumberAceCardsSplit = new HashMap<>();
	static HashMap<String, Integer> playersOption = new HashMap<>();
	static HashMap<String, Boolean> handLost = new HashMap<>();
	static Player players = new Player("Dealer", "Vasi", "Andrei");
	static ArrayList<Boolean> equalCards = new ArrayList<>();
	static List<Card> dealerCards = new ArrayList<>();
	static int index = 0;
	static int cardIndex = 0;
	// static boolean splitOption = false;
	static int aceCount = 0;
	static boolean secondHand = false;

	public static void main(String[] args) {

		System.out.println(deck.getDeck());

		boolean nobodyPlaying = false;

		handOne(players.getPlayers(), deck.getDeck(), equalCards);

		for (String player : players.getPlayers()) {

			if (player.equalsIgnoreCase("dealer")) {
				checkSumCardsDealer(player);
			} else {
				checkSumCardsHandOne(player);
			}

		}

		while (!nobodyPlaying) {
			index = 0;
			for (String player : players.getPlayers()) {
				// int index = 0;
				try {
					if (playersOption.get(player) == 3) {

						int splitCardValue = sumPlayer.get(player) / 2;
						System.out.println("First Hand:");
						int cardValueFirstHand = handTwo(players.getPlayers(), deck.getDeck(), player);
						addNewCardValueToSumAfterSplit(cardValueFirstHand, splitCardValue, player, sumPlayer);

						System.out.println("Second Hand:");
						secondHand = true;
						int cardValueSecondHand = handTwo(players.getPlayers(), deck.getDeck(), player);
						addNewCardValueToSumAfterSplit(cardValueSecondHand, splitCardValue, player, sumPlayerSplit);
						secondHand = false;
						checkSumCardsTheOtherHands(player);
						System.out.println();

					} else if (playersOption.get(player) == 1) {

						if (player.equalsIgnoreCase("dealer")) {
							int cardValue = handTwo(players.getPlayers(), deck.getDeck(), player);
							addNewCardValueToSum(cardValue, player, sumPlayer);
							checkSumCardsDealer(player);

						} else if (handLost.get(player) == true) {
							secondHand = true;
							int cardValue = handTwo(players.getPlayers(), deck.getDeck(), player);
							addNewCardValueToSum(cardValue, player, sumPlayerSplit);
							checkSumCardsTheOtherHands(player);
							System.out.println();
						} else {
							int cardValue = handTwo(players.getPlayers(), deck.getDeck(), player);
							addNewCardValueToSum(cardValue, player, sumPlayer);
							checkSumCardsTheOtherHands(player);
							System.out.println();
						}

					}
				} catch (NullPointerException e) {
					if (playersOption.isEmpty()) {
						nobodyPlaying = true;
						// index++;
						break;
					}
				}
			}

		}

		whichPlayerWon(players.getPlayers());

	}

	static void handOne(ArrayList<String> playersName, ArrayList<Card> card, ArrayList<Boolean> equalCards) {

		int cardOneValue;
		int cardTwoValue;

		sumPlayer = new HashMap<>();

		for (String player : playersName) {

			Card cardOne = card.get(cardIndex);
			aceCard(player, cardOne);
			cardOneValue = cardOne.getValue();
			cardIndex = cardIndex + playersName.size();

			Card cardTwo = card.get(cardIndex);
			aceCard(player, cardTwo);
			cardTwoValue = cardTwo.getValue();
			cardIndex = cardIndex + 1 - playersName.size();

			sumPlayer.put(player, sumOfValueCards(cardOneValue, cardTwoValue));
			sumPlayerSplit.put(player, 0);
			cardsAreEqual(player, cardOne, cardTwo, equalCards);
			handLost.put(player, false);

			if (player.equalsIgnoreCase("dealer")) {
				System.out.println(player + "   " + cardOne + " |  ?");
				dealerCards.add(cardOne);
				dealerCards.add(cardTwo);
			} else {
				System.out.println(player + "	 " + cardOne + " | " + cardTwo);
			}

		}

	}

	public static int handTwo(ArrayList<String> playersName, ArrayList<Card> card, String player) {

		int anotherCardValue;
		cardIndex = cardIndex + playersName.size();
		Card anotherCard = card.get(cardIndex);
		aceCard(player, anotherCard);
		anotherCardValue = anotherCard.getValue();
		if (player.equalsIgnoreCase("dealer")) {
			dealerCards.add(anotherCard);
		} else {
			System.out.println(player + " 	 " + anotherCard);
		}
		cardIndex = cardIndex + 1 - playersName.size();

		return anotherCardValue;
	}

	public static int sumOfValueCards(int a, int b) {
		int sum = a + b;
		return sum;
	}

	public static void cardsAreEqual(String playerName, Card cardOne, Card cardTwo, ArrayList<Boolean> equalCards) {

		if (cardOne.toString().startsWith(cardTwo.toString().substring(0, 1))) {
			equalCards.add(true);
		}

		else
			equalCards.add(false);
	}

	public static void aceCard(String playerName, Card card) {

		try {

			if (card.toString().startsWith("A") && secondHand) {
				aceCount = playerNumberAceCardsSplit.get(playerName) + 1;

				playerNumberAceCardsSplit.put(playerName, aceCount);

			} else if (card.toString().startsWith("A")) {
				aceCount = playerNumberAceCards.get(playerName);
				aceCount++;
				playerNumberAceCards.put(playerName, aceCount);
			}

			else {
				aceCount = playerNumberAceCards.get(playerName) + 0;
				playerNumberAceCards.put(playerName, aceCount);
			}
		} catch (NullPointerException e) {
			aceCount = 0;
			playerNumberAceCards.put(playerName, aceCount);
			playerNumberAceCardsSplit.put(playerName, aceCount);
			if (card.toString().startsWith("A")) {
				aceCount++;
				playerNumberAceCards.put(playerName, aceCount);
			}
		}

	}

	public static int options(ArrayList<Boolean> equalCards, int index) {

		int option;
		if (equalCards.get(index).equals(true)) {
			option = optionA();
		} else {
			option = optionB();
		}
		return option;

	}

	public static int optionA() {

		int optionA = 0;
		System.out.println("Choose: \n1.Hit \n2.Stand \n3.Split");
		optionA = optionInput(3);

		return optionA;
	}

	public static int optionB() {

		int optionB = 0;
		System.out.println("Choose: \n1. Hit \n2. Stand");
		optionB = optionInput(2);

		return optionB;
	}

	public static int optionInput(int optionsNumber) {
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		int option = 0;
		while (true) {

			try {
				option = Integer.parseInt(line);
				if (option < 1 || option > optionsNumber) {
					System.out.println("Your choose is not corect.");
				} else
					break;

			} catch (NumberFormatException e) {
				System.out.println("Error!(" + line + ") Please enter a number ");
			}
			line = scan.nextLine();

		}

		return option;
	}

	public static void optionChose(String playerName, int a) {

		playersOption.put(playerName, a);

		if (a == 2) {
			playersOption.remove(playerName);

		}

	}

	public static void checkSumCardsDealer(String playerName) {
		int numberOfAce = 0;

		if (sumPlayer.get(playerName) < 17) {
			playersOption.put(playerName, 1);
			index++;

		} else if (sumPlayer.get(playerName) > 21 && playerNumberAceCards.get(playerName) > 0) {

			numberOfAce = playerNumberAceCards.get(playerName) - 1;
			playerNumberAceCards.put(playerName, numberOfAce);
			int sumPlayerAfterReduceValueOfAce = sumPlayer.get(playerName) - 10;
			sumPlayer.put(playerName, sumPlayerAfterReduceValueOfAce);

			if (sumPlayer.get(playerName) < 17) {
				playersOption.put(playerName, 1);
				index++;

			} else if (sumPlayer.get(playerName) >= 17 && sumPlayer.get(playerName) < 22) {
				playersOption.remove(playerName);
				index++;
			}

		} else {
			playersOption.remove(playerName);
			index++;
		}

	}

	public static void checkSumCardsHandOne(String playerName) {
		int numberOfAce = 0;
		if (sumPlayer.get(playerName) == 21) {
			System.out.println(playerName + " sum is " + sumPlayer.get(playerName));
			System.out.println("BLACKJACK!!!");
			playersOption.remove(playerName);
			index++;
		} else if (sumPlayer.get(playerName) > 21) {

			if (playerNumberAceCards.get(playerName) > 0) {

				numberOfAce = playerNumberAceCards.get(playerName) - 1;
				playerNumberAceCards.put(playerName, numberOfAce);
				int sumPlayerAfterReduceValueOfAce = sumPlayer.get(playerName) - 10;
				sumPlayer.put(playerName, sumPlayerAfterReduceValueOfAce);
				System.out.println(playerName + " sum is " + sumPlayer.get(playerName));

				optionChose(playerName, options(equalCards, index));
				index++;
				if (playersOption.get(playerName) == 3) {

					int sumPlayerAfterSplit = sumPlayer.get(playerName) + 10;
					sumPlayer.put(playerName, sumPlayerAfterSplit);
					playerNumberAceCardsSplit.put(playerName, numberOfAce);

				}
			}

		} else if (sumPlayer.get(playerName) < 21) {
			System.out.println(playerName + " sum is " + sumPlayer.get(playerName));

			optionChose(playerName, options(equalCards, index));
			index++;
			if (playersOption.get(playerName) == 3) {

				int sumPlayerAfterSplit = sumPlayer.get(playerName) + 10;
				sumPlayer.put(playerName, sumPlayerAfterSplit);
				playerNumberAceCardsSplit.put(playerName, numberOfAce);

			}
		}

	}

	public static void checkSumCardsTheOtherHands(String playerName) {

		if (sumPlayer.get(playerName) == 21) {
			System.out.println(playerName + " sum is " + sumPlayer.get(playerName));
			System.out.println("BLACKJACK!!!");
			playersOption.remove(playerName);
			index++;
		} else if (sumPlayer.get(playerName) < 21) {
			System.out.println(playerName + " sum is " + sumPlayer.get(playerName));

			optionChose(playerName, optionB());
			index++;
		} else if (sumPlayer.get(playerName) > 21 && handLost.get(playerName) == false) {

			if (playerNumberAceCards.get(playerName) > 0) {
				int numberOfAce;
				numberOfAce = playerNumberAceCards.get(playerName) - 1;
				playerNumberAceCards.put(playerName, numberOfAce);
				int sumPlayerAfterReduceValueOfAce = sumPlayer.get(playerName) - 10;
				sumPlayer.put(playerName, sumPlayerAfterReduceValueOfAce);
				if (sumPlayer.get(playerName) == 21) {
					System.out.println(playerName + " sum is " + sumPlayer.get(playerName));
					System.out.println("BLACKJACK!!!");
					playersOption.remove(playerName);
					index++;
				} else {
					System.out.println(playerName + " sum is " + sumPlayer.get(playerName));
					optionChose(playerName, optionB());
					index++;
				}

			} else if (sumPlayerSplit.get(playerName) > 0) {
				System.out.println(playerName + " sum is " + sumPlayer.get(playerName)
						+ "!\nYou lost with the first hand. \nYou have another chance with the second hand.");
				handLost.put(playerName, true);
				if (sumPlayerSplit.get(playerName) > 21) {

					if (playerNumberAceCardsSplit.get(playerName) > 0) {
						int numberOfAce;
						numberOfAce = playerNumberAceCardsSplit.get(playerName) - 1;
						playerNumberAceCardsSplit.put(playerName, numberOfAce);
						int sumPlayerAfterReduceValueOfAce = sumPlayerSplit.get(playerName) - 10;
						sumPlayerSplit.put(playerName, sumPlayerAfterReduceValueOfAce);

						System.out.println(playerName + " the second hand sum is " + sumPlayerSplit.get(playerName));
						optionChose(playerName, optionB());
						index++;
					}
				} else if (sumPlayerSplit.get(playerName) == 21) {

					System.out.println(playerName + " the second hand sum is " + sumPlayerSplit.get(playerName));
					System.out.println("BLACKJACK!!!");
					playersOption.remove(playerName);
					index++;
				} else if (sumPlayerSplit.get(playerName) < 21) {
					System.out.println(playerName + " the second hand sum is " + sumPlayerSplit.get(playerName));

					optionChose(playerName, optionB());
					index++;
				}

			} else {
				System.out.println(playerName + " sum is " + sumPlayer.get(playerName));
				System.out.println("YOU LOST");
				playersOption.remove(playerName);
				index++;

			}

		} else if (sumPlayerSplit.get(playerName) == 21) {
			System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));
			System.out.println("BLACKJACK!!!");
			playersOption.remove(playerName);
			index++;

		} else if (sumPlayerSplit.get(playerName) < 21) {
			System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));

			optionChose(playerName, optionB());
			index++;
		} else if (sumPlayerSplit.get(playerName) > 21) {

			if (playerNumberAceCardsSplit.get(playerName) > 0) {
				int numberOfAce;
				numberOfAce = playerNumberAceCardsSplit.get(playerName) - 1;
				playerNumberAceCardsSplit.put(playerName, numberOfAce);
				int sumPlayerAfterReduceValueOfAce = sumPlayerSplit.get(playerName) - 10;
				sumPlayerSplit.put(playerName, sumPlayerAfterReduceValueOfAce);
				if (sumPlayerSplit.get(playerName) == 21) {
					System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));
					System.out.println("BLACKJACK!!!");
					playersOption.remove(playerName);
					index++;
				} else {
					System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));
					optionChose(playerName, optionB());
					index++;
				}

			} else {
				System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));
				System.out.println("YOU LOST");
				playersOption.remove(playerName);
				index++;
			}

		} else {
			System.out.println(playerName + " sum is " + sumPlayerSplit.get(playerName));
			System.out.println("YOU LOST");
			playersOption.remove(playerName);
			index++;

		}
	}

	public static void addNewCardValueToSumAfterSplit(int cardValue, int splitCardValue, String playerName,
			HashMap<String, Integer> sumPlayer) {
		int newSumCardsValue = cardValue + splitCardValue;
		sumPlayer.put(playerName, newSumCardsValue);

	}

	public static void addNewCardValueToSum(int cardValue, String playerName, HashMap<String, Integer> sumPlayer) {
		int newSumCardsValue = cardValue + sumPlayer.get(playerName);
		sumPlayer.put(playerName, newSumCardsValue);

	}

	private static void whichPlayerWon(ArrayList<String> playersName) {

		int sum = 0;
		ArrayList<String> playerWin = new ArrayList<>();

		for (String player : playersName) {
			int playerSplitSum = sumPlayerSplit.get(player);
			int playerSum = sumPlayer.get(player);

			if (player.equalsIgnoreCase("dealer")) {
				System.out.println("Dealer cards " + dealerCards + "\nDealer sum " + sumPlayer.get(player) + "\n");
			}

			if ((handLost.get(player) == true) && (playerSplitSum < 22)) {
				if (sum == playerSplitSum) {
					playerWin.add(player);
				} else if (sum < playerSplitSum) {
					playerWin.clear();
					playerWin.add(player);
					sum = playerSplitSum;

				}
			} else if (playerSum < 22) {
				if (sum == playerSum) {
					playerWin.add(player);
				} else if (sum < playerSum) {
					playerWin.clear();
					playerWin.add(player);
					sum = playerSum;

				}
			}
		}

		if (sum == 0) {
			System.out.println("Nobody won!!!");
		}else {
			for (String string : playerWin) {
				System.out.println("A castigat " + string + "\nSuma cartilor este " + sum);
			}
		}
		

	}
}
