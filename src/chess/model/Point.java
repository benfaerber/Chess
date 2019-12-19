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
		boolean hasError = false;
		// Validator
		if (input.length() != 2)
			hasError = true;
		String acceptableLetters = "abcdefgh";
		String acceptableNumbers = "12345678";
		String letter = input.substring(0,1);
		String number = input.substring(1,2);
		if (!(acceptableLetters.contains(letter) && acceptableNumbers.contains(number)))
			hasError = true;
					
		input = input.toLowerCase();
		String letters = "abcdefgh";
		this.x = letters.indexOf(input.substring(0, 1));
		this.y = 8 - Integer.parseInt(input.substring(1, 2));
		
		if (hasError)
		{
			this.x = -1;
			this.y = -1;
		}
	}
	
	public boolean isValid()
	{
		return x >= 0 && x <= 7 && y >= 0 && y <= 7;
	}
	
	public boolean equals(Point other)
	{
		return this.x == other.x && this.y == other.y;
	}
	
	public String pointToGraph()
	{
		if (!isValid())
			return "";
		
		String letters = "abcdefgh";
		String numbers = "87654321";
		return letters.substring(x, x+1) + numbers.substring(y, y+1);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ") " + pointToGraph();
	}
}
