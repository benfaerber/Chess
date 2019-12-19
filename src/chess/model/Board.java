package chess.model;

public class Board
{
	private Piece[][] board;
	private int scoreWhite = 0;
	private int scoreBlack = 0;
	
	public Piece getPiece(Point at)
	{
		return board[at.y][at.x];
	}
	
	public Piece getPiece(int x, int y)
	{
		return board[y][x];
	}
	
	public void setPiece(Point at, Piece piece)
	{
		board[at.y][at.x] = piece;
	}
	
	public Piece[][] getPieceArray()
	{
		return board;
	}
	
	public int getSize()
	{
		return board.length;
	}
	
	public int getScore(boolean isWhite)
	{
		calculateScore();
		return isWhite ? scoreWhite : scoreBlack;
	}
	
	public String lookupPiece(String input)
	{
		if (input.equals("n"))
			return "knight";
		String[] pieces = new String[] {"rook", "bishop", "king", "queen", "pawn"};
		for (int i = 0; i < pieces.length; i++)
		{
			if (pieces[i].substring(0, 1).equals(input))
				return pieces[i];
		}
		return "";
	}
	
	public Board()
	{
		generate();
	}
	
	public Board(int gameMode)
	{
		switch (gameMode)
		{
		case 0:
			// Default board
			generate();
			break;
		case 1:
			// Chariott chess
			generate("nnnknnnn", "nnnnnnnn");
		case 2:
			// Debug board
			generate("rnbkqbnp", "        ");
		}
	}
	
	public Board(String backrow, String frontrow)
	{
		generate(backrow, frontrow);
	}
	
	public void generate()
	{
		generate("rnbkqbnr", "pppppppp");
	}
	
	public void generate(String backrow, String frontrow)
	{
		board = new Piece[8][];
		Piece[] whiteRow1 = new Piece[8];
		Piece[] whiteRow2 = new Piece[8];
		Piece[] blackRow1 = new Piece[8];
		Piece[] blackRow2 = new Piece[8];
		
		for (int i = 0; i < whiteRow1.length; i++)
		{
			String ba = lookupPiece(backrow.substring(i, i+1));
			String fr = lookupPiece(frontrow.substring(i, i+1));
			if (ba != "")
			{
				whiteRow1[i] = new Piece(ba, true, new Point(i, 7));
				blackRow1[i] = new Piece(ba, false, new Point(i, 0));	
			}
			
			if (fr != "")
			{
				whiteRow2[i] = new Piece(fr, true, new Point(i, 6));
				blackRow2[i] = new Piece(fr, false, new Point(i, 1));
			}

		}
		
		board[0] = blackRow1;
		board[1] = blackRow2;
		board[2] = new Piece[8];
		board[3] = new Piece[8];
		board[4] = new Piece[8];
		board[5] = new Piece[8];
		board[6] = whiteRow2;
		board[7] = whiteRow1;
	}
	
	public void calculateScore()
	{
		scoreWhite = 0;
		scoreBlack = 0;
		for (int y = 0; y < board.length; y++)
		{
			for (int x = 0; x < board[y].length; x++)
			{
				Piece currentPiece = board[y][x];
				if (currentPiece != null)
				{
					if (currentPiece.isWhite)
						scoreWhite += currentPiece.points;
					else
						scoreBlack += currentPiece.points;
				}
			}
		}
	}
}
