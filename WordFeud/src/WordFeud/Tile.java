package WordFeud;

public class Tile {

	private String bonus;
	private boolean bonusUsed;
	private int xPos;
	private int yPos;
	private GameStone gameStone;
	
	public Tile(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public String getBonus(){
		return bonus;
	}
	
	public GameStone getGameStone(){
		return gameStone;
	}
	
	public boolean getBonusUsed(){
		return bonusUsed;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public void setBonusUsed(boolean used){
		this.bonusUsed = used;
	}
	
	public void setGameStone(GameStone gamestone){
		this.gameStone = gamestone;
	}
	
	public void setCoordinates(int posX, int posY){
		this.xPos = posX;
		this.yPos = posY;
	}
	
	public void setBonus(String bonus){
		this.bonus = bonus;
	}
}
