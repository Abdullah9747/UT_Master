package edu.berkeley.cs.jqf.examples;

public class GetTournamentGameValue {
public static final int getTournamentGameValue(boolean declarer,
			int gameValue, int numberOfPlayers) {

		int result = 0;

		if (declarer) {
			// calculation for declarer of the game
			if (gameValue > 0) {

				result = gameValue + 50;
			} else {

				result = gameValue - 50;
			}
		} else {
			// calculation for opponents
			if (gameValue < 0) {

				if (numberOfPlayers == 3) {

					result = 40;
				} else if (numberOfPlayers == 4) {

					result = 30;
				}
			}
		}

		return result;
	}
public static void main(String[] args) {


getTournamentGameValue(true, 73, 65);
}
}