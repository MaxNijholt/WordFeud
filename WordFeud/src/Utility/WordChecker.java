package Utility;

import java.util.ArrayList;
import java.util.HashMap;

import WordFeud.GameStone;
import WordFeud.Tile;

public class WordChecker {
	HashMap<String, GameStone> field;
	HashMap<String, GameStone> play;
	private ArrayList<String> createdWords = new ArrayList<>();
	private boolean check = false;
	private ArrayList<String> coordinate = new ArrayList<>();
	private ArrayList<String> theWords = new ArrayList<>();	
	private ArrayList<String> checkwords = new ArrayList<>();

	/**
	 * constructor no functions
	 */
	public WordChecker() {

	}

	/**
	 * the method which gives you a list of word coordinates from the letters
	 * you played
	 */
	public void checkWords(HashMap<String, GameStone> playBoard,
			HashMap<String, GameStone> movePlayed)
	{
		createdWords.clear();
		coordinate.clear();
		theWords.clear();
		this.field = playBoard;
		this.play = movePlayed; // mergen van gespeelde letters met het bord
		for (String merge : play.keySet())
		{
			field.put(merge, play.get(merge));
		}

		//ERROR! NULL
		if (coordinateChecker(play) && connectionChecker(play, null)) // de x,y
																// coordinaat
																// controle
		{

			letterChecker(); // controleerd per letter gelegde woorden en
								// voegt
								// die toe aan een collectie
			wordChecker(createdWords);
			System.out.print("the created words are: ");
			for (String words : theWords)
			{
				System.out.print("<" + words + ">");
			}

		}

		else
		{
			System.out.println("De gelegde letters zijn niet geldig");
			for (String merge : play.keySet())
			{
				field.remove(merge);
			}
		}

	}

	// joost!
	/**
	 * creates words out of coordinates and checks them in through the database
	 */
	private void wordChecker(ArrayList<String> wordsForCheck)
	{
		checkwords.add("ra");
		checkwords.add("dasse");
		checkwords.add("dassen");
		checkwords.add("as");
		checkwords.add("re");
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
				this.theWords.add(word);
				// hier moet de controle met de database komen!(ook een stukje
				// lager)
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
				this.theWords.add(word);
				// hier moet de controle met de database komen!(ook een stukje
				// hoger)
				theWords.add(word);
				word = "";
			}

		}
		// hier moet de controle met de database komen
		// in test fase
		String wordsCheck = "";
		String wordsCheckCheck = "";
		for (String wordForCheck : theWords)
		{
			if (checkwords.contains(wordForCheck))
			{
				wordsCheck = wordsCheck + "1";
			}
			else
			{
				wordsCheck = wordsCheck + "0";
				deniedWords.add(wordForCheck);
			}

		}
		for (int i = 0; i < theWords.size(); i++)
		{
			wordsCheckCheck = wordsCheckCheck + 1;
		}

		// test loopt tot hier
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
<<<<<<< .merge_file_a06160
	 * checkes the letters and creates words of them, puts them in a list and
	 * removes duplicates and unnecessary entry's
	 */
	private void letterChecker()
	{
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

		while (i < coordinate.size())
		{
			String[] parts = coordinate.get(i).split(",");
			x = Integer.parseInt(parts[0]);
			y = Integer.parseInt(parts[1]); // spliten van coordinaten in x en y

			while (horCheckStart)// voor het checken van de horizontale start
									// coordinaten
			{
				y--;
				horCorStart = Integer.toString(x) + "," + Integer.toString(y);
				if (field.get(horCorStart) == null)
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
				if (field.get(horCorEnd) == null)
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
				if (field.get(verCorStart) == null)
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
				if (field.get(verCorEnd) == null)
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
					if (createdWords.get(t).equals(checker) && first == false)
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
			if (field.keySet().contains(x + "," + y)
					&& !playd.keySet().contains(x + "," + y))
			{
				one = true;
			}
			x--;
			x--;
			if (field.keySet().contains(x + "," + y)
					&& !playd.keySet().contains(x + "," + y))
			{
				two = true;
			}
			x++;
			y--;
			if (field.keySet().contains(x + "," + y)
					&& !playd.keySet().contains(x + "," + y))
			{
				three = true;
			}
			y++;
			y++;
			if (field.keySet().contains(x + "," + y)
					&& !playd.keySet().contains(x + "," + y))
			{
				four = true;
			}
			if (field.get(x + "," + y).getGameStone() == null
					&& !playd.keySet().contains(x + "," + y))
			{
				one = false;
			}
			x--;
			x--;
			if (field.get(x + "," + y).getGameStone() == null
					&& !playd.keySet().contains(x + "," + y))
			{
				two = false;
			}
			x++;
			y--;
			if (field.get(x + "," + y).getGameStone() == null
					&& !playd.keySet().contains(x + "," + y))
			{
				three = false;
			}
			y++;
			y++;
			if (field.get(x + "," + y).getGameStone() == null
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