package chess.model;

public class Point
{
	public int x;
	public int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(String input)
	{
		input = input.toLowerCase();
		String letters = "abcdefgh";
		x = letters.indexOf(input.substring(0, 1));
		y = 8 - Integer.parseInt(input.substring(1, 2));
	}
	
	public boolean isValid()
	{
		return x >= 0 && x <= 7 && y >= 0 && y <= 7;
	}
	
	public boolean equals(Point other)
	{
		return this.x == other.x && this.y == other.y;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}
