package chess.model;

import java.util.ArrayList;

public class Game
{
	public Board board;
	public boolean isWhiteTurn = false;
	public ArrayList<Piece> capturedWhites;
	public ArrayList<Piece> capturedBlacks;
	public int gameMode = 0;
	
	public Game()
	{
		capturedWhites = new ArrayList<Piece>();
		capturedBlacks = new ArrayList<Piece>();
		switch (gameMode)
		{
		case 0:
			board.generate("rnbkqbnr", "pppppppp");
			break;
		case 1:
			board.generate("nnnknnnn", "nnnnnnnn");
			break;
		}
	}
	
	// I am going to rework this parser so that it will never fail
	public Movement parseMove(String input)
	{
		/*
		input = input.toLowerCase();
		String[] moves = input.split(" ");
		Point from = stringToPoint(moves[0]);
		Point to = stringToPoint(moves[1]);
		return new Movement(from, to);
		*/
		
		input = input.toLowerCase();
		// This error movement is a move that is impossible to make, it will throw an error in the move validator
		Movement error = new Movement(new Point(0, 0), new Point(0, 0));
		if (input.length() != 5)
			return error;
		
		// Check the string to ensure it contains only allowed characters
		String allowedCharacters = "abcdefgh12345678 ";
		for (int i = 0; i < input.length(); i++)
		{
			String currentCharacter = input.substring(i, i+1);
			if (!allowedCharacters.contains(currentCharacter))
				return error;
		}
		
		if (!input.substring(2, 3).equals(" "))
			return error;
		String[] moves = input.split(" ");
		
		if (!isValidPoint(moves[0]) || !isValidPoint(moves[1]))
			return error;
		
		Point from = stringToPoint(moves[0]);
		Point to = stringToPoint(moves[1]);
		return new Movement(from, to);
	}
	
	// Added this checker method
	public boolean isValidPoint(String input)
	{
		if (input.length() != 2)
			return false;
		String acceptableLetters = "abcdefgh";
		String acceptableNumbers = "12345678";
		String letter = input.substring(0,1);
		String number = input.substring(1,2);
		return acceptableLetters.contains(letter) && acceptableNumbers.contains(number);
		
	}
	
	public Point stringToPoint(String input)
	{
		input = input.toLowerCase();
		String letters = "abcdefgh";
		int x = letters.indexOf(input.substring(0, 1));
		int y = 8 - Integer.parseInt(input.substring(1, 2));
		
		return new Point(x, y);
	}
	
	public void makeMove(Movement move)
	{
		Piece piece = board.getPiece(move.from);		
		Piece pieceAtDestination = board.getPiece(move.to);
		board.setPiece(move.to, piece);
		board.setPiece(move.from, null);
		
		if (pieceAtDestination != null)
		{
			if (pieceAtDestination.isWhite)
				capturedWhites.add(pieceAtDestination);
			else
				capturedBlacks.add(pieceAtDestination);
			board.calculateScore();
		}
		
		piece.hasMoved = true;
		isWhiteTurn = !isWhiteTurn;
	}
	
	public String isValidMove(Movement move)
	{
		Piece movingPiece = board.getPiece(move.from);
		Piece pieceAtDestination = board.getPiece(move.to);
		
		if (move.to.equals(move.from))
			return "This piece is already here";
		
		if (movingPiece == null)
			return "There is no piece here";
		
		if (movingPiece.isWhite != isWhiteTurn)
			return "This is not your piece";

		if (pieceAtDestination != null)
		{
			if (pieceAtDestination.isWhite == isWhiteTurn)
				return "You can't capture your own piece";
		}
		
		boolean moveFound = false;
		for (int i = 0; i < movingPiece.moves.length; i++)
		{
			Move chessMove = movingPiece.moves[i];
			if ((chessMove.onlyOnFirstMove && !movingPiece.hasMoved) || !chessMove.onlyOnFirstMove)
			{
				// Flips the movement if its on the white team
				if (chessMove.mirrorWithTeam && movingPiece.isWhite)
					chessMove.downward *= -1;
				
				if (chessMove.limitDistance)
				{
					Point destination = new Point(move.from.x, move.from.y);
					destination.y += chessMove.downward;
					destination.x += chessMove.rightward;
					if (destination.isValid())
					{
						// Make sure there is a piece at the target
						if (chessMove.moveType == 1)
						{
							Piece pieceAtTarget = board.getPiece(destination);
							if (pieceAtTarget != null)
							{
								if (pieceAtTarget.isWhite != movingPiece.isWhite && destination.equals(move.to))
									return "";
							}
						}
						else if (chessMove.moveType == 0)
						{
							if (destination.equals(move.to))
								return "";
						}
					}
				}
				else
				{
					Point checkpoint = new Point(move.from.x, move.from.y);
					for (int j = 0; j < 8; j++)
					{
						checkpoint.x += negative(chessMove.rightward);
						checkpoint.y += negative(chessMove.downward);
						
						if (checkpoint.equals(move.to))
							return "";
					}
				}
			}
			
		}

		return "This is not a valid move";
	}
	
	public boolean isMoveBlocked(Move move, Movement movement)
	{
		Point checkpoint = new Point(movement.from.x, movement.from.y);
		for (int i = 0; i < 8; i++)
		{
			checkpoint.x += negative(move.rightward);
			checkpoint.y += negative(move.downward);
			
			if (checkpoint.equals(movement.to))
				return false;
			
			if (checkpoint.isValid())
			{
				if (board.getPiece(checkpoint) != null)
					return true;
			}
		}
		return true;
	}
	
	public int negative(int input)
	{
		return input < 0 ? -1 : 1;
	}
}
