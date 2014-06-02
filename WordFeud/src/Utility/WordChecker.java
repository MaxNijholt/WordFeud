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
			HashMap<String, Tile> playBoard, boolean firstTurn)
	{
		ArrayList<String> deniedWords = new ArrayList<String>();
		if (coordinateChecker(playedMove, firstTurn))
		{

			if (connectionChecker(playedMove, playBoard, firstTurn))
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

		for (String wordForCheck : theWords)
		{
			if (DBCommunicator
					.requestData("SELECT woord FROM woordenboek where woord='"
							+ wordForCheck
							+ "' and status = 'Accepted' AND letterset_code = 'EN'") == null)
			{

				deniedWords.add(wordForCheck);
			}

		}

		return deniedWords;

	}

	/**
	 * the check if there are no random stones played on the board
	 */
	private boolean coordinateChecker(HashMap<String, GameStone> playd,
			boolean firstTurn)
	{
		ArrayList<String> coordinate = new ArrayList<>();

		boolean firstTurnCheck = firstTurn;
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
				xx = xx + parts[0] + " ";
				yy = yy + parts[1] + " ";
			}

			int t = 0;
			while (t < playd.size())
			{
				String[] parts = xx.split(" ");
				int xxNumber = Integer.parseInt(parts[0]);
				xCheck = xCheck + Integer.toString(xxNumber) + " ";
				t++;
			}
			t = 0;
			while (t < playd.size())
			{
				String[] parts = yy.split(" ");
				int yyNumber = Integer.parseInt(parts[0]);
				yCheck = yCheck + Integer.toString(yyNumber) + " ";
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
		if (firstTurnCheck)
		{
			if (!coordinate.contains("8,8"))
			{
				check = false;
			}
		}

		return check;
	}

	/**
	 * Checks if the letters do have a connections with the play board and if
	 * they are in line with each other
	 */
	private boolean connectionChecker(HashMap<String, GameStone> playd,
			HashMap<String, Tile> playBoard, boolean firstTurn)
	{
		HashMap<String, Tile> field = playBoard;
		boolean connections = false;
		boolean looseStone = false;
		int connectionsWithBoard = 0;
		int connectionsWithSelf = 0;
		String xx = "";
		String yy = "";
		ArrayList<String> coordinate = new ArrayList<>();
		Boolean top = false;
		Boolean down = false;
		Boolean left = false;
		Boolean right = false;
		Boolean topSelf = false;
		Boolean downSelf = false;
		Boolean leftSelf = false;
		Boolean rightSelf = false;

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
			if (firstTurn)
			{
				if (!coordinate.contains("8,8"))
				{
					connections = false;
				}
				if (yCheckerNumbers[0] == yCheckerNumbers[1])
				{
					boolean firstTurnChecker = true;
					for (int myValue = 0; myValue < xCheckerNumbers.length; myValue++)
					{
						if (myValue + 1 < xCheckerNumbers.length)
						{
							if (xCheckerNumbers[myValue + 1] != xCheckerNumbers[myValue] + 1)
							{
								firstTurnChecker = false;
							}
						}
					}
					if (firstTurnChecker)
					{
						connections = true;
					}
				}
				else
				{
					boolean firstTurnChecker = true;
					for (int myValue = 0; myValue < yCheckerNumbers.length; myValue++)
					{
						if (myValue + 1 < yCheckerNumbers.length)
						{
							if (xCheckerNumbers[myValue + 1] != xCheckerNumbers[myValue] + 1)
							{
								firstTurnChecker = false;
							}
						}
					}
					if (firstTurnChecker)
					{
						connections = true;
					}
				}
			}
			else
			{

				if (yCheckerNumbers[0] == yCheckerNumbers[1])
				{

					String yCor = Integer.toString(yCheckerNumbers[0]);
					String xCor = "";
					for (int myValue = 0; myValue < xCheckerNumbers.length; myValue++)
					{
						xCor = Integer.toString((xCheckerNumbers[myValue]));

						if ((myValue + 1) <= xCheckerNumbers.length)
						{
							top = false;
							down = false;
							left = false;
							right = false;
							topSelf = false;
							downSelf = false;
							leftSelf = false;
							rightSelf = false;
							int x = xCheckerNumbers[myValue];
							int y = yCheckerNumbers[0];
							y--;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								top = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								topSelf = true;
							}

							y++;
							y++;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								down = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								downSelf = true;
							}

							y--;
							x--;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								left = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								leftSelf = true;
							}

							x++;
							x++;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								right = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								rightSelf = true;
							}

							if (top || down || left || right)
							{
								connectionsWithBoard++;
							}
							if (topSelf || downSelf || leftSelf || rightSelf)
							{
								connectionsWithSelf++;
							}
							if (!(top || down || left || right)
									&& !(topSelf || downSelf || leftSelf || rightSelf))
							{
								looseStone = true;
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

								top = false;
								down = false;
								left = false;
								right = false;
								topSelf = false;
								downSelf = false;
								leftSelf = false;
								rightSelf = false;
								int x = xCheckerNumbers[0];
								int y = yCheckerNumbers[myValue];
								y--;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									top = true;
								}
								if (playd.keySet().contains(x + "," + y))
								{
									topSelf = true;
								}

								y++;
								y++;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									down = true;
								}
								if (playd.keySet().contains(x + "," + y))
								{
									downSelf = true;
								}

								y--;
								x--;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									left = true;
								}
								if (playd.keySet().contains(x + "," + y))
								{
									leftSelf = true;
								}

								x++;
								x++;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									right = true;
								}
								if (playd.keySet().contains(x + "," + y))
								{
									rightSelf = true;
								}

								if (top || down || left || right)
								{
									connectionsWithBoard++;
								}
								if (topSelf || downSelf || leftSelf
										|| rightSelf)
								{
									connectionsWithSelf++;
								}
								if (!(top || down || left || right)
										&& !(topSelf || downSelf || leftSelf || rightSelf))
								{
									looseStone = true;
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

						if ((myValue + 1) <= yCheckerNumbers.length)
						{

							top = false;
							down = false;
							left = false;
							right = false;
							topSelf = false;
							downSelf = false;
							leftSelf = false;
							rightSelf = false;
							int x = xCheckerNumbers[0];
							int y = yCheckerNumbers[myValue];
							y--;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								top = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								topSelf = true;
							}

							y++;
							y++;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								down = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								downSelf = true;
							}

							y--;
							x--;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								left = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								leftSelf = true;
							}

							x++;
							x++;
							if ((field.get(x + "," + y) != null && field.get(
									x + "," + y).getGameStone() != null)
									&& !playd.keySet().contains(x + "," + y))
							{
								right = true;
							}
							if (playd.keySet().contains(x + "," + y))
							{
								rightSelf = true;
							}

							if (top || down || left || right)
							{
								connectionsWithBoard++;
							}
							if (topSelf || downSelf || leftSelf || rightSelf)
							{
								connectionsWithSelf++;
							}
							if (!(top || down || left || right)
									&& !(topSelf || downSelf || leftSelf || rightSelf))
							{
								looseStone = true;
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
								top = false;
								down = false;
								left = false;
								right = false;
								int x = xCheckerNumbers[0];
								int y = yCheckerNumbers[myValue];
								y--;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									top = true;
								}

								y++;
								y++;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									down = true;
								}
								x--;

								y--;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									left = true;
								}

								x++;
								x++;
								if ((field.get(x + "," + y) != null && field
										.get(x + "," + y).getGameStone() != null)
										&& !playd.keySet()
												.contains(x + "," + y))
								{
									right = true;
								}

								if (top || down || left || right)
								{
									connectionsWithBoard++;
								}
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
			int x = xCheckerNumbers[0];
			int y = yCheckerNumbers[0];
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
			connectionsWithSelf++;

		}

		if (connectionsWithBoard > 0 && connectionsWithSelf > 0)
		{
			connections = true;
		}
		if (connectionsWithBoard == 0 && !firstTurn)
		{
			connections = false;
		}
		if (looseStone)
		{
			connections = false;
		}

		return connections;
	}
}