package gr.aueb.cs.ai.connect4;

import java.util.HashMap;
import java.util.Map;

public class SimpleBlockHeuristic implements Heuristic {

	static final int MAX_SCORE = Integer.MAX_VALUE / 2;
	
	@Override
	public int calculate(int[][] matrix, int rows, int columns, int minPlayer) {
		Map<Integer, Integer> scores = new HashMap<>();
		scores.put(Player.PLAYER_RED, 0);
		scores.put(Player.PLAYER_YELLOW, 0);
		
		if (this.calculateRows(matrix, rows, columns, scores)) {
			return this.calculateTotalScore(scores, minPlayer);
		}
		
		if (this.calculateColumns(matrix, rows, columns, scores)) {
			return this.calculateTotalScore(scores, minPlayer);
		}
		
		if (this.calculateDiagonal(matrix, rows, columns, scores)) {
			return this.calculateTotalScore(scores, minPlayer);
		}
		
		this.calculateBackDiagonal(matrix, rows, columns, scores);
		
		return this.calculateTotalScore(scores, minPlayer);
	}
	
	private int calculateTotalScore(Map<Integer, Integer> scores, int minPlayer) {
		return scores.get(Player.switchPlayer(minPlayer)) - scores.get(minPlayer);
	}
	
	private boolean calculateRows(int[][] matrix, int rows, int columns, Map<Integer, Integer> scores) {
		int blockSize, player;
	
		for(int row = 0; row < rows; row++) {
			boolean emptyRow = true;
			blockSize = 0;
			player = Player.EMPTY;
			
			for (int column = 0; column < columns; column++) {
				int cellPlayer = matrix[row][column];
				
				if (cellPlayer != Player.EMPTY) {
					emptyRow = false;
				}
				
				if (cellPlayer != player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player != Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					}
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0 : 1;
				}
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize >= 4) {
						scores.put(player, this.getBlockScore(blockSize));

						return true;
					}
				}
			}
			
			if (emptyRow) {
				break;
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
		}
		
		return false;
	}

	private boolean calculateColumns(int[][] matrix, int rows, int columns, Map<Integer, Integer> scores) {
		int blockSize, player;
		
		for (int column = 0; column < columns; column++) {
			blockSize = 0;
			player = Player.EMPTY;
			
			for (int row = 0; row < rows; row++) {
				int cellPlayer = matrix[row][column];
				
				if (cellPlayer == Player.EMPTY) {
					break;
				}
				
				if (cellPlayer!=player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player!=Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					} 
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0 : 1;
				}
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize == 4) {
						scores.put(player, this.getBlockScore(blockSize));
						
						return true;
					}
				}
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
		}
		
		return false;
	}

	private boolean calculateDiagonal(int[][] matrix, int rows, int columns, Map<Integer, Integer> scores) {
		int blockSize, player;
		
		for (int row =0; row < rows; row++) {
			int i = row, j = 0;
			blockSize = 0;
			player = Player.EMPTY;
			
			while (i < rows && j < columns) {
				int cellPlayer = matrix[i][j];
				
				if (cellPlayer != player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player != Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					} 
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0 : 1;
					
				}
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize == 4) {
						scores.put(player, this.getBlockScore(blockSize));
						
						return true;
					}
				}
				
				i++;
				j++;
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
			
		}
		
		for (int column = 1; column < columns; column++) {
			int i = 0, j = column;
			blockSize = 0;
			player = Player.EMPTY;
			
			while (i < rows && j < columns) {
				int cellPlayer = matrix[i][j];
				
				if (cellPlayer != player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player != Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					}
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0 : 1;
					
				} 
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize == 4) {
						scores.put(player, this.getBlockScore(blockSize));

						return true;
					}
				}
				
				i++;
				j++;
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
		}
		
		return false;
	}
	
	private boolean calculateBackDiagonal(int[][] matrix, int rows, int columns, Map<Integer, Integer> scores) {
		int blockSize, player;
		
		for (int row = 0; row < rows; row++) {
			int i =row, j = 6;
			blockSize = 0;
			player = Player.EMPTY;
			
			while (i < rows && j >= 0) {
				int cellPlayer = matrix[i][j];
				
				if (cellPlayer != player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player != Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					}
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0: 1;
				} 
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize ==4) {
						scores.put(player, this.getBlockScore(blockSize));
						
						return true;
					}
				}
				
				i++;
				j--;
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
			
		}
		
		
		for (int column = 0; column < columns - 1; column++) {
			int i = 5, j = column;
			blockSize = 0;
			player = Player.EMPTY;
			
			while (i >= 0 && j < columns - 1) {
				int cellPlayer = matrix[i][j];
				
				if (cellPlayer != player) {
					int blockScore = this.getBlockScore(blockSize);
					
					if (player != Player.EMPTY) {
						int playerTotalScore = scores.get(player) + blockScore;
						scores.put(player, playerTotalScore);
					}
					
					player = cellPlayer;
					blockSize = cellPlayer == Player.EMPTY ? 0: 1;
				} 
				else if (cellPlayer != Player.EMPTY) {
					blockSize++;
					
					if (blockSize ==4) {
						scores.put(player, this.getBlockScore(blockSize));

						return true;
					}
				}
				
				i--;
				j++;
			}
			
			if (player != Player.EMPTY) {
				int playerTotalScore = scores.get(player) + this.getBlockScore(blockSize);
				scores.put(player, playerTotalScore);
			}
		}
		
		return false;
	}
	
	private int getBlockScore(int blockSize) {
		if (blockSize >= 4) {
			return MAX_SCORE;
		}
		
		switch (blockSize) {
			case 2: 
				return 10;
			case 3: 
				return 19;
			default: 
				return 0;
		}
	}

}
