package Utility;

import java.util.ArrayList;
import java.util.HashMap;

import WordFeud.GameStone;
import WordFeud.Tile;

public class WordChecker {

	/**
	 * constructor no functions
	 */
	public WordChecker() {

	}

	/**
	 * check with 3 checkers if a turn is valid(stone locations, connection,
	 * created words)
	 */
	public ArrayList<String> checkWords(ArrayList<String> wordsForCheck,
			HashMap<String, GameStone> playedMove,
			HashMap<String, Tile> playBoard)
	{
		ArrayList<String> deniedWords = new ArrayList<String>();
		if (coordinateChecker(playedMove)
				&& connectionChecker(playedMove, playBoard))
		{
			if (wordChecker(wordsForCheck, playBoard).size() != 0)
			{
				deniedWords = wordChecker(wordsForCheck, playBoard);
			}

		}

		return deniedWords;
	}

	/**
	 * creates words out of coordinates and checks them in through the database
	 */
	private ArrayList<String> wordChecker(ArrayList<String> wordsTooCheck,
			HashMap<String, Tile> playBoard)
	{
		ArrayList<String> deniedWords = new ArrayList<>();
		ArrayList<String> theWords = new ArrayList<>();
		HashMap<String, Tile> field = playBoard;
		ArrayList<String> wordsForCheck = wordsTooCheck;
		String word = "";
		int x = 0;
		int y = 0;
		String[] cords = null;
		String[] cordsSplit = null;
		String[] cordsSplit2 = null;
		int t = 0;
		for (String wordcor : wordsForCheck)
		{
			cords = wordcor.split("_");
			cordsSplit = cords[0].split(",");
			cordsSplit2 = cords[1].split(",");
			if (cordsSplit[0].equals(cordsSplit2[0]))
			{
				x = Integer.parseInt(cordsSplit[0]);
				y = Integer.parseInt(cordsSplit[1]);
				word = word + field.get(x + "," + y).getGameStone().getLetter();
				while (t < (Integer.parseInt(cordsSplit2[1]) - Integer
						.parseInt(cordsSplit[1])))
				{
					y++;
					word = word
							+ field.get(x + "," + y).getGameStone().getLetter();

					t++;

				}
				t = 0;
				theWords.add(word);

				word = "";
			}
			else
			{
				x = Integer.parseInt(cordsSplit[0]);
				y = Integer.parseInt(cordsSplit[1]);
				word = word + field.get(x + "," + y).getGameStone().getLetter();
				while (t < (Integer.parseInt(cordsSplit2[0]) - Integer
						.parseInt(cordsSplit[0])))
				{
					x++;
					word = word
							+ field.get(x + "," + y).getGameStone().getLetter();

					t++;

				}
				t = 0;
				theWords.add(word);
				word = "";
			}

		}

		String wordsCheck = "";
		String wordsCheckCheck = "";
		for (String wordForCheck : theWords)
		{
			if (DBCommunicator
					.requestData("SELECT woord FROM woordenboek where woord='"
							+ wordForCheck + "'") == null)
			{
				wordsCheck = wordsCheck + "0";
			}
			else
			{
				wordsCheck = wordsCheck + "1";
				deniedWords.add(wordForCheck);
			}

		}
		for (int i = 0; i < theWords.size(); i++)
		{
			wordsCheckCheck = wordsCheckCheck + 1;
		}

		return deniedWords;

	}

	/**
	 * the check if there are no random stones played on the board
	 */
	private boolean coordinateChecker(HashMap<String, GameStone> playd)
	{
		ArrayList<String> coordinate = new ArrayList<>();
		boolean check = false;
		boolean checker = true;
		boolean x = false;
		boolean y = false;
		String xx = "";
		String yy = "";

		String xCheck = "";
		String yCheck = "";
		while (checker)
		{

			for (String myValue : playd.keySet())
			{
				String[] parts = myValue.split(",");
				coordinate.add(myValue);
				xx = xx + parts[0];
				yy = yy + parts[1];
			}
			int t = 0;
			while (t < playd.size())
			{
				xCheck = xCheck + xx.substring(0, 1);
				t++;
			}
			t = 0;
			while (t < playd.size())
			{
				yCheck = yCheck + yy.substring(0, 1);
				t++;
			}

			if (xx.equals(xCheck))
			{
				x = true;
			}
			if (yy.equals(yCheck))
			{
				y = true;
			}
			if (x == false && y == false)
			{
				check = false;
			}
			else
			{
				check = true;
			}
			checker = false;
		}

		return check;
	}

	/**
	 * Checks if the letters do have a connections at all
	 */
	private boolean connectionChecker(HashMap<String, GameStone> playd,
			HashMap<String, Tile> playBoard)
	{
		HashMap<String, Tile> field = playBoard;
		boolean connections = false;
		int x;
		int y;
		for (String myValue : playd.keySet())
		{
			boolean one = true;
			boolean two = true;
			boolean three = true;
			boolean four = true;
			String[] parts = myValue.split(",");
			x = Integer.parseInt(parts[0]);
			y = Integer.parseInt(parts[1]);
			x++;
			if ((field.get(x + "," + y) == null || field.get(x + "," + y)
					.getGameStone() == null)
					&& !playd.keySet().contains(x + "," + y))
			{
				one = false;
			}
			x--;
			x--;
			if ((field.get(x + "," + y) == null || field.get(x + "," + y)
					.getGameStone() == null)
					&& !playd.keySet().contains(x + "," + y))
			{
				two = false;
			}
			x++;
			y--;
			if ((field.get(x + "," + y) == null || field.get(x + "," + y)
					.getGameStone() == null)
					&& !playd.keySet().contains(x + "," + y))
			{
				three = false;
			}
			y++;
			y++;
			if ((field.get(x + "," + y) == null || field.get(x + "," + y)
					.getGameStone() == null)
					&& !playd.keySet().contains(x + "," + y))
			{
				four = false;
			}
			if (!one || !two || !three || !four)
			{
				connections = true;
			}

		}

		return connections;
	}

}