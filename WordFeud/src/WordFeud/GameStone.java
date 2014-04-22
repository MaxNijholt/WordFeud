package WordFeud;

public class GameStone {

	private int value;
	private char letter;
	
	public GameStone(int value, char letter){
		this.value = value;
		this.letter = letter;
	}
	
	public int getValue(){
		return value;
	}
	
	public char getLetter(){
		return letter;
	}
}
