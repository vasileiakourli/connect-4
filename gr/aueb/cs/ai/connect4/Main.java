package gr.aueb.cs.ai.connect4;

import java.util.Scanner;

public class Main {
		
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		try {
			boolean wins = false;
			int column;
			int currentPlayer = Player.PLAYER_RED;
			GamePlayer YPlayer = new GamePlayer(Player.PLAYER_YELLOW, 5, new SimpleBlockHeuristic());
			State state = new State(currentPlayer);
			System.out.println(state);
			//PC(YELLOW)->MIN PLAYER
			//user(RED)->MAX PLAYER
			
			while(!wins && !state.isFull()){
				
				switch (currentPlayer) {
					case Player.PLAYER_YELLOW:
						System.out.println(Player.playerString(currentPlayer) + " moves");
						Move yMove = YPlayer.MiniMax(state);
						state.makeMove(yMove.getCol());
						System.out.println(state);
						wins = state.isTerminal(currentPlayer);
						if(!wins) {
							currentPlayer=state.getNextPlayer();
						}
						break;
					case Player.PLAYER_RED:
						String playerName = Player.playerString(currentPlayer);
						System.out.println("Player " + playerName + ", choose column [1-7]");
						column = in.nextInt() - 1;
						
						while(!state.isValidMove(column)) {
							System.out.println("Wrong position! "+"\n"+ playerName+ " column[1-7]");
							column = in.nextInt() - 1;
						}
						state.makeMove(column);
						System.out.println(state);
						wins = state.isTerminal(currentPlayer);
						if(!wins) {
							currentPlayer=state.getNextPlayer();
						}
						break;
					default:
						break;
				}
				
			}
			
			if(wins) {
				System.out.println("Player " + Player.playerString(currentPlayer) + " won!");
			} else {
				System.out.println("Draw!");
			}
		}
		finally {
			in.close();
		}
	}//main

}//class
