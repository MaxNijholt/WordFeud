package Core;

import java.util.ArrayList;

import AccountType.Account;
import GUI.CompetitionPanel;
import GUI.CompetitionPlayersPanel;
import GUI.GUI;
import GUI.GamePanel;
import GUI.LoginPanel;
import GUI.PlayerPanel;
import GUI.SpectatorGamePanel;
import GUI.SpectatorPanel;
import Utility.DBCommunicator;
import Utility.Loader;
import Utility.MasterThread;
import WordFeud.Competition;
import WordFeud.Game;
import WordFeud.GameStone;

import java.util.Calendar;

import javax.swing.JOptionPane;


public class Application {
	
	private Game selectedGame;
	private Competition selectedCompetition;	
	private Account currentAccount;
	private GUI myGui;
	private Loader loader;
//	private MasterThread mt;


	/**
	 * start up the application
	 * create the Gui
	 */
	public Application(){
		DBCommunicator.getConnection();
		loader = new Loader();
		loader.loadAllImages();
		myGui = new GUI(this);
//		mt = mt.getInstance();
//		mt.startThread();
	}
	
	
	/**create a new competition*/
	public void addCompetition(String endDate, String description, int mini, int maxi){
		selectedCompetition = new Competition(endDate, description, mini, maxi, currentAccount.getUsername());
		myGui.switchPanel(new CompetitionPanel(myGui));
	}	
	
	/**
	 * let Game create a new game
	 * call the playgame method
	 */
	public void newGame(String player2, boolean visibility){
		String visible;
		if(visibility){
			visible = "openbaar";
		}
		else{
			visible = "prive";
		}
			
		DBCommunicator.writeData("INSERT INTO spel (id, competitie_id, toestand_type, account_naam_uitdager, account_naam_tegenstander, moment_uitdaging, reaktie_type, zichtbaarheid_type, bord_naam, letterset_naam)"
								+ " VALUES(" + (DBCommunicator.requestInt("SELECT id FROM spel ORDER BY id DESC") + 1) + ", " + selectedCompetition.getID() + ", 'Request', '" + currentAccount.getUsername() + "', '" + player2 + "', CURRENT_TIMESTAMP(), 'Unknown', '" + visible + "' , 'Standard', 'EN');");
	}
	
	
	
	/**
	 * create the new competition
	 * switch to the competitionpanel
	 */
	public void selectCompetition(int compID){
		selectedCompetition = new Competition(compID);
		myGui.switchPanel(new CompetitionPanel(myGui));
	}

	/**
	 * create the new account
	 */
	public void login(Account username){
		currentAccount = username;
	}
	
	public void selectGame(int gameID) {
		if(doInitializeGame(gameID)){
			Game newGame = new Game(gameID, this);
			selectedGame = newGame;
			myGui.switchPanel(new GamePanel(myGui));
		}
	}
	
	public void spectateCompetition(int compID) {
		myGui.switchPanel(new SpectatorPanel(myGui, compID));
	}
	
	public void spectateGame(int gameID) {
		myGui.switchPanel(new SpectatorGamePanel(myGui, gameID));
	}
	
	/**
	 * tell game to lay a gamestone
	 * return the int from game
	 */
	public int layGameStone(GameStone gamestone, String location){
		int retrievedPoints = selectedGame.layGameStone(gamestone, location);
		return retrievedPoints;
	}
	
	public int removeGameStone(String location){
		int points = selectedGame.removeGameStone(location);
		return points;
	}
	
	public void resign(){
		System.out.println("resign");
		selectedGame.resign();
	}

	/**
	 * accept or deny a game in the db
	 * @param gameID
	 */
	public void acceptGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Accepted', moment_reaktie = CURRENT_TIMESTAMP(), toestand_type = 'Playing' WHERE id = " + gameID);
		String opponent = this.getOpponentName(gameID);
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type)"
				+ "VALUES (1, " + gameID + ", '" + opponent + "', 0, 'Begin'), (2, " + gameID + ", '"+ currentAccount.getUsername() +"', 0, 'Begin')");
		myGui.switchPanel(new PlayerPanel(myGui));
	}
	
	public void acceptGame(int gameID, int compID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Accepted', moment_reaktie = CURRENT_TIMESTAMP(), toestand_type = 'Playing' WHERE id = " + gameID);
		String opponent = this.getOpponentName(gameID);
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type)"
				+ "VALUES (1, " + gameID + ", '" + opponent + "', 0, 'Begin'), (2, " + gameID + ", '"+ currentAccount.getUsername() +"', 0, 'Begin')");
		myGui.switchPanel(new CompetitionPlayersPanel(myGui, compID));
	}
	
	public boolean doInitializeGame(int gameID){
		int letter = DBCommunicator.requestInt("SELECT id FROM letter WHERE spel_id = " + gameID);
		if(letter == 0){
			if(getMyTurn(gameID)){
				createGameLetters(gameID);
				createGameHands(gameID);
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "Opponent has not initialzed this game yet!", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else{
			return true;
		}		
	}
	
	/**
	 * add all the letters for the beginning of a game to the DB
	 * @param gameID
	 */
	private void createGameLetters(int gameID){
		int letterID = 1;
		for(char letter = 'A'; letter <= 'Z'; letter++){
			int amount = DBCommunicator.requestInt("SELECT aantal FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + letter + "'");
			
			for(int i = 0; i < amount; i++){
				DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '" + letter + "')" );
				letterID++;
			}
		}

		DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '?')");
		letterID++;
		DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '?')");
	}
	
	/**
	 * add 7 random letters to the hands of each player
	 * @param gameID
	 */
	private void createGameHands(int gameID){
		for(int beurt = 1; beurt <= 2; beurt++){
			int e = 0;
			while(e < 7){
				int letterID = (int) (Math.random() * 105);
				String character = DBCommunicator.requestData("SELECT karakter FROM pot WHERE spel_id = " + gameID + " AND letter_id = " + letterID);
				if(character != null){
					DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES(" + gameID + ", " + letterID + ", " + beurt + ")");
					e++;
				}
			}
		}
	}
	
	public void denyGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Rejected', moment_reaktie = CURRENT_TIMESTAMP() WHERE id = " + gameID);
		myGui.switchPanel(new PlayerPanel(myGui));
	}
	
	public void denyGame(int gameID, int compID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Rejected', moment_reaktie = CURRENT_TIMESTAMP() WHERE id = " + gameID);
		myGui.switchPanel(new CompetitionPlayersPanel(myGui, compID));
	}
	
	/**
	 * get all the games that have finished (finished or resigned) and return their integers
	 * @param activeType
	 */
	public ArrayList<Integer> getFinishedGames(boolean resigned) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String resign = "Finished";
		if(resigned){
			resign = "Resigned";
		}
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = '" + resign + "'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	/**
	 * get all the games that have finished (finished or resigned) and return their integers
	 * @param activeType
	 */
	public ArrayList<Integer> getFinishedGames(boolean resigned, int compID) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String resign = "Finished";
		if(resigned){
			resign = "Resigned";
		}
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND competitie_id = " + compID +" AND toestand_type = '" + resign + "'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}


	/**
	 * get all games that are still playing (my turn or opponents turn) and return their integers
	 * @param activeType
	 * @param myTurn
	 * @return
	 */
	public ArrayList<Integer> getPlayingGames(Boolean myTurn) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		ArrayList<Integer> turnInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = 'Playing'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);

			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		
		for(int e : gameInts){
			String name = DBCommunicator.requestData("SELECT account_naam FROM beurt WHERE spel_id = " + e + " ORDER BY id DESC");
			if((name.equals(currentAccount.getUsername()) && (!myTurn))){
				turnInts.add(e);
			}
			else if((!name.equals(currentAccount.getUsername()) && (myTurn))){
				turnInts.add(e);
			}
		}
		
		return turnInts;
	}

	public ArrayList<Integer> getPlayingGames(Boolean myTurn, int compID) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		ArrayList<Integer> turnInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = 'Playing' AND competitie_id = '" + compID +"'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);

			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		
		for(int e : gameInts){
			String name = DBCommunicator.requestData("SELECT account_naam FROM beurt WHERE spel_id = " + e + " ORDER BY id DESC");
			if((name.equals(currentAccount.getUsername()) && (!myTurn))){
				turnInts.add(e);
			}
			else if((!name.equals(currentAccount.getUsername()) && (myTurn))){
				turnInts.add(e);
			}
		}
		
		return turnInts;
	}

	
	/**
	 * get all requested games (currentAccount or opponents request) (denied or unknown) and return their integers
	 * @param myRequest
	 * @param denied
	 * @return
	 */
	public ArrayList<Integer> getRequestedGames(boolean myRequest, boolean denied) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "";
		if(denied){
			query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = 'Request' AND reaktie_type = 'Rejected'";
		}
		else{
			if(myRequest){
				query = "SELECT id FROM spel WHERE account_naam_uitdager = '"+ player + "' AND toestand_type = 'Request' AND reaktie_type = 'Unknown'";
			}
			else{
				query = "SELECT id FROM spel WHERE account_naam_tegenstander = '"+ player + "' AND toestand_type = 'Request' AND reaktie_type = 'unknown'";
			}
		}
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	/**
	 * get all requested games (currentAccount or opponents request) (denied or unknown) and return their integers
	 * @param myRequest
	 * @param denied
	 * @return
	 */
	public ArrayList<Integer> getRequestedGames(boolean myRequest, boolean denied, int compID) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "";
		if(denied){
			query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND competitie_id = " + compID +" AND toestand_type = 'Request' AND reaktie_type = 'Rejected'";
		}
		else{
			if(myRequest){
				query = "SELECT id FROM spel WHERE account_naam_uitdager = '"+ player + "' AND competitie_id = " + compID +" AND toestand_type = 'Request' AND reaktie_type = 'Unknown'";
			}
			else{
				query = "SELECT id FROM spel WHERE account_naam_tegenstander = '"+ player + "' AND competitie_id = " + compID +" AND toestand_type = 'Request' AND reaktie_type = 'unknown'";
			}
		}
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	public ArrayList<Integer> getSpectatableGames(int compID) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		
		String query = "SELECT id FROM spel WHERE competitie_id = " + compID + " AND zichtbaarheid_type = 'openbaar'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);

			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	public ArrayList<Integer> getPlayingCompetitions(){
		ArrayList<Integer> compInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();

		Calendar rightNow = Calendar.getInstance();
		String now = rightNow.get(1) + "-" + (rightNow.get(2) + 1) + "-" + rightNow.get(5);
		String query = "SELECT competitie_id FROM deelnemer LEFT JOIN competitie ON deelnemer.competitie_id = competitie.id WHERE account_naam = '" + player + "'" + " AND einde > '" + now + "%'";
		boolean searching = true;
		
		while(searching){
			int compID = DBCommunicator.requestInt(query);
			if(compID == 0){
				
				
				searching = false;
			}
				else{
						query += " AND competitie_id <> " + compID;
						compInts.add(compID);
					

			}
		}
		
		
		return compInts;
	}
	
	public ArrayList<Integer> getFinishedCompetitions(){
		ArrayList<Integer> compInts = new ArrayList<Integer>();

		Calendar rightNow = Calendar.getInstance();
		String now = rightNow.get(1) + "-" + (rightNow.get(2) + 1) + "-" + rightNow.get(5);
		String query = "SELECT competitie_id FROM deelnemer LEFT JOIN competitie ON deelnemer.competitie_id = competitie.id WHERE einde <= '" + now + "%'";
		boolean searching = true;
		
		while(searching){
			int compID = DBCommunicator.requestInt(query);
			if(compID == 0){
				
				
				searching = false;
			}
				else{
						query += " AND competitie_id <> " + compID;
						compInts.add(compID);
					

			}
		}
		
		
		return compInts;
	}

	
	
	
	public ArrayList<Integer> getAllCompetitions(){
		ArrayList<Integer> compInts = new ArrayList<Integer>();

		Calendar rightNow = Calendar.getInstance();
		String now = rightNow.get(1) + "-" + (rightNow.get(2) + 1) + "-" + rightNow.get(5);
		String query = "SELECT DISTINCT id FROM competitie WHERE einde > '"+ now + "'";
		boolean searching = true;
		
		while(searching){
			int compID = DBCommunicator.requestInt(query);
			if(compID == 0){
				
				
				searching = false;
			}
				else{
					query += " AND id <> " + compID;
					if(getJoinable(compID)){
						compInts.add(compID);		
					}
				}
			}
	
		
		return compInts;
	}
	
	public boolean getHaveGameWith(String opponent, int compID){
		int gameID = DBCommunicator.requestInt("SELECT id FROM spel WHERE competitie_id = " + compID + " AND account_naam_uitdager = '" + currentAccount.getUsername() + "' AND account_naam_tegenstander = '" + opponent + "' AND reaktie_type = 'Accepted' AND toestand_type = 'Playing'");
		if(gameID == 0){
			gameID = DBCommunicator.requestInt("SELECT id FROM spel WHERE competitie_id = " + compID + " AND account_naam_uitdager = '" + opponent + "' AND account_naam_tegenstander = '" + currentAccount.getUsername() + "' AND reaktie_type = 'Accepted' AND toestand_type = 'Playing'");
			if(gameID == 0){
				return false;
			}
		}
		return true;
	}
	
	public int getAmountGames(String player, int compID){
		int amount = DBCommunicator.requestInt("SELECT COUNT(id) FROM spel WHERE competitie_id = " + compID + " AND (account_naam_uitdager = '" + player + "' OR account_naam_tegenstander = '" + player + "') AND reaktie_type = 'Accepted'");
		return amount;
	}
	
	public ArrayList<Integer> getAllGames(String player, int compID, boolean finished){
		ArrayList<Integer> allGames = new ArrayList<Integer>();
		boolean done = false;
		String restQuery = "";
		while(!done){
			int gameID = 0;
			if(!finished){
				gameID = DBCommunicator.requestInt("SELECT id FROM spel WHERE (account_naam_uitdager = '" + player + "' OR account_naam_tegenstander = '" + player + "') " + restQuery);
			}
			else{
				gameID = DBCommunicator.requestInt("SELECT id FROM spel WHERE (account_naam_uitdager = '" + player + "' OR account_naam_tegenstander = '" + player + "') AND (toestand_type = 'Finished' OR toestand_type = 'Resigned') " + restQuery);
			}
			if(gameID != 0){
				allGames.add(gameID);
				restQuery = restQuery + " AND id <> " + gameID;
			}
			else{
				done = true;
			}
		}
		return allGames;
	}
	
	public int getTotalPoints(String player, int compID){
		int amount = DBCommunicator.requestInt("SELECT SUM(score) FROM beurt INNER JOIN spel ON beurt.spel_id = spel.id WHERE competitie_id = " + compID + " AND beurt.account_naam = '" + player + "'");
		return amount;
	}
	
	public String getAveragePoints(String player, int compID){
		int pointsTotal = getTotalPoints(player, compID);
		int gamesTotal = getAmountGames(player, compID);
		int average = 0;
		
		if(pointsTotal != 0  && gamesTotal != 0){
			average = pointsTotal/gamesTotal;
		}
		else if(gamesTotal == 0){
			average = pointsTotal;
		}
		
		String ratioS = average + "";
		try{
			ratioS = ratioS.substring(0, 4);
		}
		catch(StringIndexOutOfBoundsException e){
			ratioS = "0,0";
		}
		
		return ratioS;
	}
	
	public int getGamesWon(String player, int compID){
		ArrayList<Integer> allGames = getAllGames(player, compID, true);
		int numberWon = 0;
		for(int id: allGames){
			String winner = DBCommunicator.requestData("SELECT account_naam FROM score WHERE spel_id = " + id + " ORDER BY totaalscore DESC");
			
			if(winner.equals(player)){
				numberWon++;
			}
		}
		return numberWon;
	}
	
	public int getGamesLost(String player, int compID){
		ArrayList<Integer> allGames = getAllGames(player, compID, true);
		int numberWon = 0;
		for(int id: allGames){
			String winner = DBCommunicator.requestData("SELECT account_naam FROM score WHERE spel_id = " + id + " ORDER BY totaalscore ASC");
			if(winner.equals(player)){
				numberWon++;
			}
		}
		return numberWon;
	}
	
	public String getWinLose(String player, int compID){
		double won = getGamesWon(player,compID);
		double lost = getGamesLost(player,compID);
		double ratio = 0.00000;
		if(won != 0 && lost != 0){
			ratio = won/lost;
		}
		else if(lost == 0){
			ratio = won;
		}
		
		String ratioS = ratio + "";
		try{
			ratioS = ratioS.substring(0, 4);
		}
		catch(StringIndexOutOfBoundsException e){
			ratioS = "0,0";
		}
		return ratioS;
	}	
	
	public ArrayList<Integer> getSpectatableCompetitions() {
		ArrayList<Integer> compInts = new ArrayList<Integer>();
		
		String endQuery = "";
		String query = "SELECT id FROM competitie WHERE id <> 0 " + endQuery;
		Boolean searching = true;
		
		while(searching){
			int compID = DBCommunicator.requestInt(query);

			if(compID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + compID;
				compInts.add(compID);
			}
		}
		return compInts;
	}
	
	/**
	 * get a the opponents name from the db
	 */
	public String getOpponentName(int gameID){
		
		String name = DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID);
		if(name.equals(currentAccount.getUsername())){
			name = DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID);
		}
		
		return name;
	}
	
	public String getCompetitionOwner(int compID){
		String ownerName = DBCommunicator.requestData("SELECT account_naam_eigenaar FROM competitie WHERE id = " + compID);
		return ownerName;
	}
	
	public String getCompetitionDescription(int compID){
		String description = DBCommunicator.requestData("SELECT omschrijving FROM competitie WHERE id = " + compID);
		return description;
	}
	
	public ArrayList<String> getGamePlayers(int gameID){
		ArrayList<String> players = new ArrayList<String>();
		players.add(DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID));
		players.add(DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID));
		return players;
	}
	
	public ArrayList<String> getCompetitionPlayers(int compID){
		ArrayList<String> players = new ArrayList<String>();
		String rankingQuery = "SELECT account_naam FROM ranking WHERE competitie_id = " + compID;
		String restQuery = "SELECT account_naam FROM deelnemer WHERE competitie_id = " + compID;
		boolean done = false;
		
		while(!done){
			String name = DBCommunicator.requestData(rankingQuery);
			if(name == null){
				done = true;
			}
			else{
				players.add(name);
				rankingQuery += " AND account_naam <> '" + name + "'";
				restQuery += " AND account_naam <> '" + name + "'";
			}
		}
		
		done = false;
		while(!done){
			String name = DBCommunicator.requestData(restQuery);
			if(name == null){
				done = true;
			}
			else{
				players.add(name);
				restQuery += " AND account_naam <> '" + name + "'";
			}
		}
		
		return players;
	}
	
	public String getPlayerRanking(int compID, String player){
		String ranking = DBCommunicator.requestData("SELECT bayesian_rating FROM ranking WHERE competitie_id = " + compID + " AND account_naam = '" + player + "'");
		return ranking;
	}
	
	/**
	 * get the last turns information
	 */
	public String getLastTurnType(int gameID){
		String turnType = DBCommunicator.requestData("SELECT aktie_type FROM beurt WHERE spel_id = " + gameID + " ORDER BY id desc");
		return turnType;
	}
	
	public int getLastTurnScore(int gameID){
		int turnScore = DBCommunicator.requestInt("SELECT score FROM beurt WHERE spel_id = " + gameID + " ORDER BY id desc");
		return turnScore;
	}
	
	/**
	 * return a boolean if a competition has ended
	 * @return
	 */
	public boolean getTimeEnd(int compID){
		int comp = DBCommunicator.requestInt("SELECT id FROM competitie WHERE einde <= CURRENT_TIMESTAMP() AND id = " + compID);
		if(comp == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public String getEndDate(int compID){
		String date = DBCommunicator.requestData("SELECT einde FROM competitie WHERE id = " + compID);
		return date;
	}
	
	public void addPlayer(String player, int compID){
		DBCommunicator.writeData("INSERT INTO deelnemer SET account_naam='" + player + "', competitie_id='"+ compID + "'");
	}
	
	public boolean getMyTurn(int gameID){
		String name = DBCommunicator.requestData("SELECT account_naam FROM beurt WHERE spel_id = " + gameID + " ORDER BY id DESC");
		if(name.equals(currentAccount.getUsername())){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean getJoinable(int compID){
		int current = DBCommunicator.requestInt("SELECT COUNT(account_naam) FROM deelnemer WHERE competitie_id = " + compID);
		int max = DBCommunicator.requestInt("SELECT maximum_aantal_deelnemers FROM competitie WHERE id = " + compID);
		
		if(current < max){
			if(getTimeEnd(compID)){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}
	
	public String getWinner(int gameID){
		
		return "";
	}
	
	/**
	 * call the game to play a word
	 */
	public ArrayList<String> playWord(){
		ArrayList<String> words = selectedGame.playWord();
		return words;
	}
	
	/**
	 * tell game to pass a turn
	 */
	public void pass(){
		System.out.println("pass");
		selectedGame.pass();
	}
	
	/**
	 *tell game to swapGameStones
	 */
	public void swapGameStones(ArrayList<Integer> stoneIDs){
		selectedGame.swapGameStones(stoneIDs);
	}
	
	/**
	 * tell the game to shuffle
	 */
	public void shuffle(){
		selectedGame.shuffle();
	}
	
	
	/**
	 * getters and setters
	 * @return
	 */
	public Game getSelectedGame() {
		return selectedGame;
	}


	public void setSelectedGame(Game selectedGame) {
		this.selectedGame = selectedGame;
	}


	public Competition getSelectedCompetition() {
		return selectedCompetition;
	}


	public void setSelectedCompetition(Competition selectedCompetition) {
		this.selectedCompetition = selectedCompetition;
	}


	public Account getCurrentAccount() {
		return currentAccount;
	}


	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}


	public void logout() {
		this.setCurrentAccount(null);
		myGui.switchPanel(new LoginPanel(myGui));
	}
	
	public void seeComps(int compID, String panel){
		SpectatorPanel sp = new SpectatorPanel(myGui, compID);
		sp.setMenuPanel(panel);
		myGui.switchPanel(sp);
	}
}
