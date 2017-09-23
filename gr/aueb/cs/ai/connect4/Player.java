package gr.aueb.cs.ai.connect4;

public class Player {

	public static final int PLAYER_RED = 1;
	public static final int PLAYER_YELLOW = -1;
	public static final int EMPTY = 0;
	
	public static String playerString(int currentPlayer){
		switch(currentPlayer)
		{
			case PLAYER_RED: return "X";
			case PLAYER_YELLOW: return "O";
		}
		return " ";
	}
	
	public static int switchPlayer(int currentPlayer){
		int nextPlayer;
		
		if(currentPlayer == PLAYER_RED){
			nextPlayer = PLAYER_YELLOW;
		} else{
			nextPlayer = PLAYER_RED;
		}
		
		return nextPlayer;
	}
	
}
