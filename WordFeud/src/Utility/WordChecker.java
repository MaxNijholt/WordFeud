package Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import Utility.DBCommunicator;
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
		if (coordinateChecker(playedMove))
		{

			if (connectionChecker(playedMove, playBoard))
			{
				deniedWords = wordChecker(wordsForCheck, playBoard);

			}
			else
			{
				deniedWords
						.add("played stones have no connection with the field stones");
			}

		}
		else
		{
			deniedWords.add("incorrect played stones");
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
			if (x || y)
			{
				check = true;
			}
			checker = false;
		}

		return check;
	}

	/**
	 * Checks if the letters do have a connections with the play board and if
	 * they are in line with each other
	 */
	private boolean connectionChecker(HashMap<String, GameStone> playd,
			HashMap<String, Tile> playBoard)
	{
		HashMap<String, Tile> field = playBoard;
		boolean connections = true;
		int connectionsWithBoard = 0;
		String xx = "";
		String yy = "";
		ArrayList<String> coordinate = new ArrayList<>();
		Boolean top = false;
		Boolean down = false;
		Boolean left = false;
		Boolean right = false;

		for (String myValue : playd.keySet())
		{

			String[] parts = myValue.split(",");
			coordinate.add(myValue);
			xx = xx + parts[0] + " ";
			yy = yy + parts[1] + " ";

		}

		String[] xChecker = xx.split(" ");
		int[] xCheckerNumbers = new int[xChecker.length];
		int a = 0;
		for (String myValue : xChecker)
		{
			int merger = Integer.parseInt(myValue);

			xCheckerNumbers[a] = merger;
			a++;
		}

		String[] yChecker = yy.split(" ");
		int[] yCheckerNumbers = new int[yChecker.length];
		a = 0;
		for (String myValue : yChecker)
		{
			int merger = Integer.parseInt(myValue);

			yCheckerNumbers[a] = merger;
			a++;
		}
		Arrays.sort(xCheckerNumbers);
		Arrays.sort(yCheckerNumbers);
		if (yCheckerNumbers.length > 1)
		{

			if (yCheckerNumbers[0] == yCheckerNumbers[1])
			{

				String yCor = Integer.toString(yCheckerNumbers[0]);
				String xCor = "";
				for (int myValue = 0; myValue < xCheckerNumbers.length; myValue++)
				{
					xCor = Integer.toString((xCheckerNumbers[myValue]));

					if ((myValue + 1) == xCheckerNumbers.length)
					{
						top = false;
						down = false;
						left = false;
						right = false;
						int x = Integer.parseInt(xx.substring(0, 1));
						int y = Integer.parseInt(yy.substring(
								(yy.length() - 2), (yy.length() - 1)));
						y--;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							top = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						y++;
						y++;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							down = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						y--;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							left = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						x++;
						x++;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							right = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						if (top || down || left || right)
						{
							connectionsWithBoard++;
						}
					}
					else
					{
						if ((xCheckerNumbers[myValue + 1] - 1) != xCheckerNumbers[myValue])
						{

							if (field.get(xCor + "," + yCor).getGameStone() == null)
							{
								connections = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							top = true;
							down = true;
							left = true;
							right = true;
							int x = Integer.parseInt(xx.substring(0, 1));
							int y = Integer.parseInt(yy.substring(
									(yy.length() - 2), (yy.length() - 1)));
							y--;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								top = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							y++;
							y++;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								down = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							y--;
							x--;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								left = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							x++;
							x++;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								right = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							if (!top && !down && !left && !right)
							{
								connections = false;
							}
						}
					}
				}
			}
			else
			{

				String yCor = "";
				String xCor = Integer.toString(xCheckerNumbers[0]);
				for (int myValue = 0; myValue < yCheckerNumbers.length; myValue++)
				{
					yCor = Integer.toString((yCheckerNumbers[myValue]));

					if ((myValue + 1) == yCheckerNumbers.length)
					{
						top = false;
						down = false;
						left = false;
						right = false;
						int x = Integer.parseInt(xx.substring(0, 1));
						int y = Integer.parseInt(yy.substring(
								(yy.length() - 2), (yy.length() - 1)));
						y--;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							top = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						y++;
						y++;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							down = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						y--;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							left = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						x++;
						x++;
						if ((field.get(x + "," + y) != null || field.get(
								x + "," + y).getGameStone() != null)
								&& playd.keySet().contains(x + "," + y))
						{
							right = true;
						}
						else
						{
							connectionsWithBoard++;
						}
						if (top || down || left || right)
						{
							connectionsWithBoard++;
						}
					}
					else
					{
						if ((yCheckerNumbers[myValue + 1] - 1) != yCheckerNumbers[myValue])
						{

							if (field.get(xCor + "," + yCor).getGameStone() == null)
							{
								connections = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							top = true;
							down = true;
							left = true;
							right = true;
							int x = Integer.parseInt(xx.substring(0, 1));
							int y = Integer.parseInt(yy.substring(
									(yy.length() - 2), (yy.length() - 1)));
							y--;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								top = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							y++;
							y++;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								down = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							y--;
							x--;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								left = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							x++;
							x++;
							if ((field.get(x + "," + y) == null || field.get(
									x + "," + y).getGameStone() == null)
									|| playd.keySet().contains(x + "," + y))
							{
								right = false;
							}
							else
							{
								connectionsWithBoard++;
							}
							if (!top && !down && !left && !right)
							{
								connections = false;
							}
						}
					}
				}
			}
		}
		else
		{
			top = true;
			down = true;
			left = true;
			right = true;
			int x = Integer.parseInt(xx.substring(0, 1));
			int y = Integer.parseInt(yy.substring(0, 1));
			y--;
			if (field.get(x + "," + y) == null
					|| field.get(x + "," + y).getGameStone() == null)
			{
				top = false;
			}
			else
			{
				connectionsWithBoard++;
			}
			y++;
			y++;
			if (field.get(x + "," + y) == null
					|| field.get(x + "," + y).getGameStone() == null)
			{
				down = false;
			}
			else
			{
				connectionsWithBoard++;
			}
			y--;
			x--;
			if (field.get(x + "," + y) == null
					|| field.get(x + "," + y).getGameStone() == null)
			{
				left = false;
			}
			else
			{
				connectionsWithBoard++;
			}
			x++;
			x++;
			if (field.get(x + "," + y) == null
					|| field.get(x + "," + y).getGameStone() == null)
			{
				right = false;
			}
			else
			{
				connectionsWithBoard++;
			}
			if (!top && !down && !left && !right)
			{
				connections = false;
			}

		}
		if (connectionsWithBoard == 0)
		{
			connections = false;
		}

		return connections;
	}
}