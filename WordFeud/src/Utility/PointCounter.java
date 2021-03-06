package Utility;

import java.util.ArrayList;
import java.util.HashMap;

import WordFeud.GameStone;
import WordFeud.Tile;

public class PointCounter {

	public PointCounter() {

	}

	/**
	 * takes the played letters and makes coordinates of created words
	 * 
	 * @param fieldTiles
	 * @param movePlayed
	 * @return
	 */
	public ArrayList<String> createWords(HashMap<String, Tile> fieldTiles,
			HashMap<String, GameStone> movePlayed)
	{
		HashMap<String, Tile> field = fieldTiles;
		HashMap<String, GameStone> play = movePlayed;
		ArrayList<String> coordinates = new ArrayList<>();

		// merging the fields
		for (String merge : play.keySet())
		{
			coordinates.add(merge);
		}
		ArrayList<String> createdWords = letterChecker(field, coordinates);

		// create word coordinates
		return createdWords;

	}

	/**
	 * takes the created words coordinates and checks the posible scores of the
	 * move
	 * 
	 * @param wordsForCount
	 * @param playBoard
	 * @return
	 */
	public int count(ArrayList<String> wordsForCount,
			HashMap<String, Tile> playBoard, int stonesPlayed)
	{

		HashMap<String, Tile> field = playBoard;
		int score = 0;
		int wordMultiplier = 1;
		int wordScore = 0;
		int x = 0;
		int y = 0;
		String[] cords = null;
		String[] cordsSplit = null;
		String[] cordsSplit2 = null;
		int t = 0;
		for (String wordcor : wordsForCount)
		{
			wordMultiplier = 1;
			wordScore = 0;
			cords = wordcor.split("_");
			cordsSplit = cords[0].split(",");
			cordsSplit2 = cords[1].split(",");
			x = Integer.parseInt(cordsSplit[0]);
			y = Integer.parseInt(cordsSplit[1]);
			if (cordsSplit[0].equals(cordsSplit2[0]))
			{
				while (t <= (Integer.parseInt(cordsSplit2[1]) - Integer
						.parseInt(cordsSplit[1])))
				{

					if (field.get(x + "," + y).getBonus().equals(""))
					{
						wordScore = wordScore
								+ field.get(x + "," + y).getGameStone()
										.getValue();
					}
					else
					{

						if (field.get(x + "," + y).getBonus().equals("*"))
						{
							wordScore = wordScore
									+ (field.get(x + "," + y).getGameStone()
											.getValue());
						}
						if (!field.get(x + "," + y).isBonusUsed())
						{

							if (field.get(x + "," + y).getBonus().equals("DL"))
							{
								wordScore = wordScore
										+ (field.get(x + "," + y)
												.getGameStone().getValue() * 2);
							}
							if (field.get(x + "," + y).getBonus().equals("TL"))
							{
								wordScore = wordScore
										+ (field.get(x + "," + y)
												.getGameStone().getValue() * 3);
							}
							if (field.get(x + "," + y).getBonus().equals("DW"))
							{
								wordScore = wordScore
										+ field.get(x + "," + y).getGameStone()
												.getValue();
								wordMultiplier = wordMultiplier * 2;
							}
							if (field.get(x + "," + y).getBonus().equals("TW"))
							{
								wordScore = wordScore
										+ field.get(x + "," + y).getGameStone()
												.getValue();
								wordMultiplier = wordMultiplier * 3;
							}
						}
						else
						{
							wordScore = wordScore
									+ field.get(x + "," + y).getGameStone()
											.getValue();
						}

					}
					y++;
					t++;

				}
				wordScore = wordScore * wordMultiplier;
				t = 0;

			}
			else
			{
				while (t <= (Integer.parseInt(cordsSplit2[0]) - Integer
						.parseInt(cordsSplit[0])))
				{

					if (field.get(x + "," + y).getBonus().equals(""))
					{
						wordScore = wordScore
								+ field.get(x + "," + y).getGameStone()
										.getValue();
					}
					else
					{


						if (!field.get(x + "," + y).isBonusUsed())
						{
							if (field.get(x + "," + y).getBonus().equals("*"))
							{
								wordScore = wordScore
										+ (field.get(x + "," + y).getGameStone()
												.getValue());
							}

							if (field.get(x + "," + y).getBonus().equals("DL"))
							{
								wordScore = wordScore
										+ (field.get(x + "," + y)
												.getGameStone().getValue() * 2);
							}
							if (field.get(x + "," + y).getBonus().equals("TL"))
							{
								wordScore = wordScore
										+ (field.get(x + "," + y)
												.getGameStone().getValue() * 3);
							}
							if (field.get(x + "," + y).getBonus().equals("DW"))
							{
								wordScore = wordScore
										+ field.get(x + "," + y).getGameStone()
												.getValue();
								wordMultiplier = wordMultiplier * 2;
							}
							if (field.get(x + "," + y).getBonus().equals("TW"))
							{
								wordScore = wordScore
										+ field.get(x + "," + y).getGameStone()
												.getValue();
								wordMultiplier = wordMultiplier * 3;
							}
						}
						else
						{
							wordScore = wordScore
									+ field.get(x + "," + y).getGameStone()
											.getValue();
						}

					}
					x++;
					t++;

				}
				wordScore = wordScore * wordMultiplier;
				t = 0;

			}

			score = score + wordScore;
		}
		if (stonesPlayed == 7)
		{
			score = score + 40;
		}
		return score;

	}

	private ArrayList<String> letterChecker(HashMap<String, Tile> playBoard,
			ArrayList<String> coordinatesForCheck)
	{
		HashMap<String, Tile> field = playBoard;
		ArrayList<String> coordinates = coordinatesForCheck;
		ArrayList<String> createdWords = new ArrayList<>();
		int x = 0;
		int y = 0;
		int i = 0;
		boolean horCheckStart = true;
		String horCorStart = null;
		boolean horCheckEnd = true;
		String horCorEnd = null;
		boolean verCheckStart = true;
		String verCorStart = null;
		boolean verCheckEnd = true;
		String verCorEnd = null;

		while (i < coordinates.size())
		{
			String[] parts = coordinates.get(i).split(",");
			x = Integer.parseInt(parts[0]);
			y = Integer.parseInt(parts[1]); // spliten van coordinaten in x en y

			while (horCheckStart)// voor het checken van de horizontale start
									// coordinaten
			{
				y--;
				horCorStart = Integer.toString(x) + "," + Integer.toString(y);
				if (field.get(horCorStart) == null
						|| field.get(horCorStart).getGameStone() == null)
				{
					y++;
					horCorStart = Integer.toString(x) + ","
							+ Integer.toString(y);
					horCheckStart = false;
				}

			}
			while (horCheckEnd)// voor het checken van de horizontale eind
								// coordinaten
			{
				y++;
				horCorEnd = Integer.toString(x) + "," + Integer.toString(y);
				if (field.get(horCorEnd) == null
						|| field.get(horCorEnd).getGameStone() == null)
				{
					y--;
					horCorEnd = Integer.toString(x) + "," + Integer.toString(y);
					horCheckEnd = false;
				}

			}
			createdWords.add(horCorStart + "_" + horCorEnd); // gemaakte woord
																// toevoegen aan
																// collectie
			x = Integer.parseInt(parts[0]);
			y = Integer.parseInt(parts[1]); // spliten van coordinaten in x
											// en y

			while (verCheckStart)// voor het checken van de verticale start
									// coordinaten
			{

				x--;
				verCorStart = Integer.toString(x) + "," + Integer.toString(y);

				if (field.get(verCorStart) == null
						|| field.get(verCorStart).getGameStone() == null)
				{
					x++;
					verCorStart = Integer.toString(x) + ","
							+ Integer.toString(y);
					verCheckStart = false;
				}

			}
			while (verCheckEnd)// voor het checken van de verticale eind
								// coordinaten
			{
				x++;
				verCorEnd = Integer.toString(x) + "," + Integer.toString(y);
				if (field.get(verCorEnd) == null
						|| field.get(verCorEnd).getGameStone() == null)
				{
					x--;
					verCorEnd = Integer.toString(x) + "," + Integer.toString(y);
					verCheckEnd = false;
				}

			}
			createdWords.add(verCorStart + "_" + verCorEnd);
			horCheckEnd = true;
			horCheckStart = true;
			verCheckEnd = true;
			verCheckStart = true;
			i++;
		}
		// het samen voegen en verwijderen van gedupliceerde woorden en enkele
		// letters
		int t = 0;
		int j = 0;
		boolean first = false;
		if (createdWords.size() != 0)
		{

			String checker = createdWords.get(j);

			// verwijderen duplicaten
			while (j < createdWords.size())
			{
				first = true;
				checker = createdWords.get(j);
				while (t < createdWords.size())
				{
					if (createdWords.get(t).equals(checker) && first == true)
					{
						first = false;
					}
					else
					{
						if (createdWords.get(t).equals(checker)
								&& first == false)
						{
							createdWords.remove(t);
						}
					}
					t++;
				}
				t = 0;
				j++;
			}
			// verwijderen enkele letters
			j = 0;
			String[] checker2;
			while (j < createdWords.size())
			{
				checker2 = createdWords.get(j).split("_");
				if (checker2[0].equals(checker2[1]))
				{
					createdWords.remove(j);
					j = 0;
				}
				else
				{
					j++;
				}
			}
		}
		return createdWords;

	}

	public void deactivateBonusses(ArrayList<String> wordsForCount,
			HashMap<String, Tile> playBoard, int stonesPlayed)
	{

		HashMap<String, Tile> field = playBoard;
		int x = 0;
		int y = 0;
		String[] cords = null;
		String[] cordsSplit = null;
		String[] cordsSplit2 = null;
		int t = 0;
		for (String wordcor : wordsForCount)
		{
			ArrayList<String> bonusToDeactivate = new ArrayList<>();
			cords = wordcor.split("_");
			cordsSplit = cords[0].split(",");
			cordsSplit2 = cords[1].split(",");
			x = Integer.parseInt(cordsSplit[0]);
			y = Integer.parseInt(cordsSplit[1]);
			if (cordsSplit[0].equals(cordsSplit2[0]))
			{
				while (t <= (Integer.parseInt(cordsSplit2[1]) - Integer
						.parseInt(cordsSplit[1])))
				{

					if (field.get(x + "," + y).getBonus() != null)
					{
						bonusToDeactivate.add(x + "," + y);
					}

					y++;
					t++;

				}
				t = 0;

			}
			else
			{
				while (t <= (Integer.parseInt(cordsSplit2[0]) - Integer
						.parseInt(cordsSplit[0])))
				{

					if (field.get(x + "," + y).getBonus() != null)
					{
						bonusToDeactivate.add(x + "," + y);
					}

					x++;
					t++;

				}
				t = 0;

			}

		}

		for (@SuppressWarnings("unused")
		String deleter : wordsForCount)
		{
			// deactivate bonusses
		}

	}

}
