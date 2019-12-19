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
		board = new Board(gameMode);
	}

	public boolean makeMove(Movement move)
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
		}

		piece.hasMoved = true;
		piece.position = move.to;
						
		//If this causes a piece to be in check, undo and disallow
		if (isCheck(isWhiteTurn))
		{
			board.setPiece(move.from, piece);
			board.setPiece(move.to, pieceAtDestination);
			piece.position = move.from;
			return false;
		}
		
		isWhiteTurn = !isWhiteTurn;
		return true;
	}

	// Returns true if the move is valid for the current team
	public boolean checkProperTeam(Movement move)
	{
		Piece movingPiece = board.getPiece(move.from);
		Piece pieceAtDestination = board.getPiece(move.to);
		
		if (movingPiece.isWhite != isWhiteTurn)
			return false;

		if (pieceAtDestination != null)
		{
			if (pieceAtDestination.isWhite == isWhiteTurn)
				return false;
			
			if (pieceAtDestination.name.equals("king"))
				return false;
		}
		
		return true;
	}
	
	public String isValidMove(Movement move)
	{
		if (!move.isValid())
			return "You typed your move incorrectly!";

		Piece movingPiece = board.getPiece(move.from);

		if (move.to.equals(move.from))
			return "This piece is already here";

		if (movingPiece == null)
			return "There is no piece here";

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
						} else if (chessMove.moveType == 0)
						{
							if (destination.equals(move.to))
								return "";
						}
					}
				} else
				{
					if (!isMoveBlocked(chessMove, move))
					{
						Point checkpoint = new Point(move.from.x, move.from.y);
						for (int j = 0; j < 8; j++)
						{
							checkpoint.x += sign(chessMove.rightward);
							checkpoint.y += sign(chessMove.downward);

							if (checkpoint.equals(move.to))
								return "";
						}
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
			checkpoint.x += sign(move.rightward);
			checkpoint.y += sign(move.downward);

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

	// 0 - not check
	// 1 - check
	// 2 - checkmate
	public boolean isCheck(boolean isWhite)
	{		
		ArrayList<Piece> possibleAttackers = new ArrayList<Piece>();
		Piece king = null;
		// Loop through all pieces
		for (int y = 0; y < board.getSize(); y++)
		{
			for (int x = 0; x < board.getSize(); x++)
			{
				Piece current = board.getPiece(x, y);
				if (current != null)
				{
					if (current.isWhite != isWhite)
						possibleAttackers.add(current);
					else if (current.name.equals("king"))
						king = current;
				}
			}
		}

		// Scan attackers to find check
		for (Piece current : possibleAttackers)
		{
			Movement killKing = new Movement(current.position, king.position);	
			String errorCode = isValidMove(killKing);
			
			//Check or checkmate
			if (errorCode.equals(""))
			{
				return true;
			}
		}

		return false;
	}

	public int sign(int input)
	{
		if (input == 0)
			return 0;
		return input < 0 ? -1 : 1;
	}
}
