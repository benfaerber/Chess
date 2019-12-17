package chess.model;

public class Piece
{
	public String name;
	public String symbol;
	public int points;
	public Move[] moves;
	public boolean hasMoved;
	public boolean isWhite;
	
	public Piece(String name, boolean isWhite)
	{
		this.name = name;
		this.hasMoved = false;
		this.isWhite = isWhite;
		
		// Move(int up, int down, int left, int right, boolean limitDistance, int moveType, boolean mirrorWithTeam, boolean onlyOnFirstMove)
		switch(name)
		{
		case "pawn":
			this.symbol = isWhite ? "♟": "♙";
			this.points = 1;
			this.moves = new Move[] {
					new Move(0, 1, 0, 0, true, 0, true, false), // Regular 1 move
					new Move(0, 2, 0, 0, true, 0, true, true), // First 2 move
					new Move(0, 1, 1, 0, true, 1, true, false), // Left capture
					new Move(0, 1, 0, 1, true, 1, true, false) // Right capture
			};
			break;
		case "knight":
			this.symbol = isWhite ? "♞": "♘";
			this.points = 3;
			this.moves = new Move[] {
					new Move(1, 0, 2, 0, true),
					new Move(2, 0, 1, 0, true),
					
					new Move(1, 0, 0, 2, true),
					new Move(2, 0, 0, 1, true),
					
					new Move(0, 1, 0, 2, true),
					new Move(0, 2, 0, 1, true),
					
					new Move(0, 1, 2, 0, true),
					new Move(0, 2, 1, 0, true)
			};
			break;
		case "bishop":
			this.symbol = isWhite ? "♝": "♗";
			this.points = 3;
			this.moves = new Move[] {
					new Move(1, 0, 1, 0, false),
					new Move(0, 1, 1, 0, false),
					new Move(1, 0, 0, 1, false),
					new Move(0, 1, 0, 1, false)
			};
			break;
		case "rook":
			this.symbol = isWhite ? "♜": "♖";
			this.points = 5;
			this.moves = new Move[] {
					new Move(1, 0, 0, 0, false),
					new Move(0, 1, 0, 0, false),
					new Move(0, 0, 1, 0, false),
					new Move(0, 0, 0, 1, false)
			};
			break;
		case "queen":
			this.symbol = isWhite ? "♛": "♕";
			this.points = 9;
			this.moves = new Move[] {
					new Move(1, 0, 0, 0, false),
					new Move(0, 1, 0, 0, false),
					new Move(0, 0, 1, 0, false),
					new Move(0, 0, 0, 1, false),
					
					new Move(1, 0, 1, 0, false),
					new Move(0, 1, 1, 0, false),
					new Move(1, 0, 0, 1, false),
					new Move(0, 1, 0, 1, false)
			};
			break;
		case "king":
			this.symbol = isWhite ? "♚": "♔";
			this.points = 0;
			this.moves = new Move[] {
					new Move(1, 0, 0, 0, true),
					new Move(0, 1, 0, 0, true),
					new Move(0, 0, 1, 0, true),
					new Move(0, 0, 0, 1, true),
					
					new Move(1, 0, 1, 0, true),
					new Move(0, 1, 1, 0, true),
					new Move(1, 0, 0, 1, true),
					new Move(0, 1, 0, 1, true)
			};
			break;
		}
	}

	// In case you want to create a custom piece
	public Piece(String name, String symbol, int points, Move[] moves, boolean isWhite)
	{
		this.name = name;
		this.symbol = symbol;
		this.points = points;
		this.moves = moves;
		this.isWhite = isWhite;
	}
	
	public String toString()
	{
		String n = isWhite ? "White" : "Black";
		n += " " + name;
		return n;
	}
}
