package gr.aueb.cs.ai.connect4;

import java.util.ArrayList;
import java.util.Random;

public class GamePlayer {

	private final int minPlayer;
	private final int maxPlayer;
	private final int maxDepth;
	private final Heuristic heuristic;

	public GamePlayer(int player, int maxDepth, Heuristic heuristic) {
		this.minPlayer = player;
		this.maxPlayer = Player.switchPlayer(player);
		this.maxDepth = maxDepth;
		this.heuristic = heuristic;
	}

	public Move MiniMax(State state) {
		if (state.isFull()) {
			String msg = "Cannot run min-max algorithm when no moves can be played on the board.";
			throw new IllegalStateException(msg);
		}

		return min(new State(state), 0);
	}

	private Move max(State state, int depth) {
		Random r = new Random();

		if (this.isTerminal(state, this.minPlayer) || depth == maxDepth) {
			Move lastMove = new Move(state.getLastMove().getCol(), state.execute(this.heuristic, this.minPlayer));
			return lastMove;
		}
		
		ArrayList<State> children = new ArrayList<State>(state.getChildren());
		Move maxMove = new Move(Integer.MIN_VALUE);
		
		for (State child : children) {
			Move move = min(child, depth + 1);
			if (move.getValue() >= maxMove.getValue()) {
				if ((move.getValue() == maxMove.getValue())) {
					if (r.nextInt(2) == 0) {
						maxMove.setCol(child.getLastMove().getCol());
						maxMove.setValue(move.getValue());
					}
				} else {
					maxMove.setCol(child.getLastMove().getCol());
					maxMove.setValue(move.getValue());
				}
			}
		}
		
		return maxMove;
	}

	private Move min(State state, int depth) {
		Random r = new Random();

		if (this.isTerminal(state, this.maxPlayer) || depth == maxDepth) {
			Move lastMove = new Move(state.getLastMove().getCol(), state.execute(this.heuristic, this.minPlayer));
			return lastMove;
		}
		
		ArrayList<State> children = new ArrayList<State>(state.getChildren());
		Move minMove = new Move(Integer.MAX_VALUE);
		
		for (State child : children) {
			Move move = max(child, depth + 1);
			
			if (move.getValue() <= minMove.getValue()) {
				if ((move.getValue() == minMove.getValue())) {
					if (r.nextInt(2) == 0) {
						minMove.setCol(child.getLastMove().getCol());
						minMove.setValue(move.getValue());
					}
				} else {
					minMove.setCol(child.getLastMove().getCol());
					minMove.setValue(move.getValue());
				}
			}
		}
		
		return minMove;
	}
	
	private boolean isTerminal(State state, int previousPlayer) {
		return state.isFull() || state.isTerminal(previousPlayer);
	}

}
