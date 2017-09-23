package gr.aueb.cs.ai.connect4;
import java.util.ArrayList;

public class State {

	private final static int ROWS = 6;
	private final static int COLUMNS = 7;

	private int[][] matrix;
	private int[] columnSizes;
	private int nextPlayer;
	private Move lastMove;
	
	public State(int startingPlayer) {
		matrix = new int[ROWS][COLUMNS];
		columnSizes = new int[COLUMNS];
		
		this.nextPlayer = startingPlayer;
		lastMove = new Move();
	}

	public State(State state) {
		matrix = new int[ROWS][COLUMNS];
		columnSizes = new int[COLUMNS];
		
		for(int j = 0; j < COLUMNS; j++){
			columnSizes[j] = state.columnSizes[j];
		}
		
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLUMNS; j++) {
				matrix[i][j] = state.matrix[i][j];
			}
		}
		
		this.nextPlayer = state.nextPlayer;
		lastMove = new Move(state.lastMove);
	}
	
	public Move getLastMove()
	{
		return new Move(lastMove);
	}
	
	public int getNextPlayer() {
		return nextPlayer;
	}
	
	public boolean isFull() {
		for (int columnSize : this.columnSizes) {
			if (columnSize < ROWS) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isValidMove(int column) {
		
		if (this.isOutOfBounds(column) ) {
			return false;
		}
		
		return this.isAvailableColumn(column);
	}

	public void makeMove(int column) {
		int row = this.columnSizes[column];
		this.matrix[row][column] = this.nextPlayer;
		this.columnSizes[column]++;
		this.lastMove = new Move(column, this.nextPlayer);
		this.nextPlayer = Player.switchPlayer(this.nextPlayer);
	}

	public ArrayList<State> getChildren()
	{
		ArrayList<State> children = new ArrayList<State>();
		for(int col=0; col<COLUMNS; col++) {
			if(isValidMove(col))
			{
				State child = new State(this);
				child.makeMove(col);
				children.add(child);
			}
		}
		
		return children;
	}
	
	public boolean isTerminal(int player) {
		return isHorizontal(player) || isVertical(player) 
				|| isDiagonalSlash(player) || isDiagonalBackslash(player);
	}
	
	public int execute(Heuristic heuristic, int minPlayer) {
		return heuristic.calculate(matrix, ROWS, COLUMNS, minPlayer);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = ROWS - 1; i >= 0; i--) {
			for(int j = 0; j < COLUMNS; j++) {
				
				String move = Player.playerString(matrix[i][j]);
				sb.append("    ");
				sb.append(move);
				sb.append("    ");
				
				if (j < COLUMNS - 1) {
					sb.append("|");
				} else {
					sb.append("\n");
				}
				
			}
			
			if (i < ROWS){
				sb.append("---------------------------------------------------------------------\n");
			}
			
		}
		
		return sb.toString();
	}
	
	private boolean isOutOfBounds(int column) {
		return column < 0 || column >= COLUMNS;
	}
	
	private boolean isAvailableColumn(int column) {
		return this.columnSizes[column] < ROWS;
	}

	
	private boolean isHorizontal(int player) {
		int sum = 0;
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (matrix[i][j] == player) {
					sum++;
					if (sum == 4) {
						return true;
					}
				} else {
					sum = 0;
				}

			}

			sum = 0;
		}
		return false;

	}

	private boolean isVertical(int player) {
		int sum = 0;
		for (int j = 0; j < COLUMNS; j++) {
			for (int i = 0; i < ROWS; i++) {
				if (this.matrix[i][j] == player) {
					sum++;
					if (sum == 4) {
						return true;
					}
				} else {
					sum = 0;
				}

			}

			sum = 0;
		}
		return false;

	}

	private boolean isDiagonalSlash(int player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if ((this.matrix[i][j] == player)
						&& (this.matrix[i + 1][j + 1] == player)
						&& (this.matrix[i + 2][j + 2] == player)
						&& (this.matrix[i + 3][j + 3] == player)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isDiagonalBackslash(int player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 6; j > 2; j--) {
				if ((this.matrix[i][j] == player)
						&& (this.matrix[i + 1][j - 1] == player)
						&& (this.matrix[i + 2][j - 2] == player)
						&& (this.matrix[i + 3][j - 3] == player)) {
					return true;
				}
			}
		}

		return false;
	}
	
}
