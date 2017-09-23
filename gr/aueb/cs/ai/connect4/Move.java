package gr.aueb.cs.ai.connect4;

public class Move
{
	private int col;
	private int value;
	
	public Move()
	{
		col = -1;
		value = 0;
	}
	
	public Move(int col, int value)
	{
		this.col = col;
		this.value = value;
	}
	
	public Move(Move other) {
		this(other.col, other.value);
	}
	
	public Move(int value)
	{
		this.col = -1;
		this.value = value;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getValue()
	{
		return value;
	}
	

	public void setCol(int col)
	{
		this.col = col;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
}
