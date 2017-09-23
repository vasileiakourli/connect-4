package gr.aueb.cs.ai.connect4;

public interface Heuristic {

	int calculate(int[][] matrix, int rows, int columns, int minPlayer);
	
}
