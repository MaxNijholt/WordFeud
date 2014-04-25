package Utility;

import java.util.ArrayList;
import java.util.HashMap;

import WordFeud.GameStone;

public class WordChecker {
	private HashMap<String, GameStone> field;
	private HashMap<String, GameStone> play;
	private ArrayList<String> createdWords = new ArrayList<>();
	private boolean check = false;
	private String xx = "";
	private String yy = "";
	private ArrayList<String> coordinate = new ArrayList<>();
	private ArrayList<String> theWords = new ArrayList<>();

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
		this.field = playBoard;
		this.play = movePlayed; // mergen van gespeelde letters met het bord
		for (String merge : play.keySet())
		{
			field.put(merge, play.get(merge));
		}

		if (coordinateChecker(play)) // de x,y coordinaat controle
		{
			letterChecker(); // controleerd per letter gelegde woorden en voegt
								// die toe aan een collectie
			wordChecker(createdWords);
		}

		else
		{
			System.out.println(" de gelegde letters zijn niet geldig");
			for (String merge : play.keySet())
			{
				field.remove(merge);
			}
		}
		System.out.print("the created words are: ");
		for (String words : theWords)
		{
			System.out.print("<" + words + ">");
		}
	}

	/**
	 * creates words out of coordinates and checks them in through the database
	 */
	private void wordChecker(ArrayList<String> wordsForCheck)
	{
		String word = "";
		int x = 0;
		int y = 0;
		String[] words = null;
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
				word = word + field.get(x + "," + y).getLetter();
				while (t < (Integer.parseInt(cordsSplit2[1]) - Integer
						.parseInt(cordsSplit[1])))
				{
					y++;
					word = word + field.get(x + "," + y).getLetter();

					t++;

				}
				t = 0;
				System.out.print("vertical word: ");
				System.out.println(word);
				this.theWords.add(word);
				// hier moet de controle met de database komen!(ook een stukje
				// lager)
				word = "";
			}
			else
			{
				x = Integer.parseInt(cordsSplit[0]);
				y = Integer.parseInt(cordsSplit[1]);
				word = word + field.get(x + "," + y).getLetter();
				while (t < (Integer.parseInt(cordsSplit2[0]) - Integer
						.parseInt(cordsSplit[0])))
				{
					x++;
					word = word + field.get(x + "," + y).getLetter();

					t++;

				}
				t = 0;
				System.out.print("horizontal word: ");
				System.out.println(word);
				this.theWords.add(word);
				// hier moet de controle met de database komen!(ook een stukje
				// hoger)
				word = "";
			}

		}

	}

	/**
	 * the check if there are no random stones played on the board
	 */
	private boolean coordinateChecker(HashMap<String, GameStone> playd)
	{

		boolean checker = true;
		boolean x = false;
		boolean y = false;

		String xCheck = "";
		String yCheck = "";
		while (checker)
		{

			for (String myValue : playd.keySet())
			{
				String[] parts = myValue.split(",");
				coordinate.add(myValue);
				xx = xx + parts[0];
				xCheck = parts[0];
				yy = yy + parts[1];
				yCheck = parts[1];
			}
			xCheck = xCheck + xCheck + xCheck + xCheck + xCheck;
			yCheck = yCheck + yCheck + yCheck + yCheck + yCheck;
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
	 * checkes the letters and creates words of them, puts them in a list and
	 * removes duplicates and unnecessary entry's
	 */
	private void letterChecker()// controleerd per letter gemaakte woorden en
								// voegt die toe aan een collectie
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

}