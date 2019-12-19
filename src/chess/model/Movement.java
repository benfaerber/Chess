package chess.model;

public class Movement
{
	public Point from;
	public Point to;
	
	public Movement(Point from, Point to)
	{
		this.from = from;
		this.to = to;
	}
	
	public Movement(String input)
	{	
		input = input.toLowerCase();
		input = input.replace(" ", "");
		// This error movement is a move that is impossible to make, it will throw an error in the move validator
		Boolean hasError = false;
		if (input.length() != 4)
			hasError = true;
		
		// Check the string to ensure it contains only allowed characters
		String allowedCharacters = "abcdefgh12345678 ";
		for (int i = 0; i < input.length(); i++)
		{
			String currentCharacter = input.substring(i, i+1);
			if (!allowedCharacters.contains(currentCharacter))
				hasError = true;
		}
		
		if (!hasError)
		{
			from = new Point(input.substring(0, 2));
			to = new Point(input.substring(2, 4));
		}
		else
		{
			from = new Point(-1, -1);
			to = new Point(-1, -1);
		}
	}
	
	public boolean isValid()
	{
		return !from.equals(to);
	}
	
	public String toString()
	{
		return from.pointToGraph() + "->" + to.pointToGraph();
	}
}
