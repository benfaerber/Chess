package chess.model;

import java.util.ArrayList;

public class Game
{
	public Board board;
	public boolean isWhiteTurn = false;
	public ArrayList<Piece> capturedWhites;
	public ArrayList<Piece> capturedBlacks;
	public int gameMode = 2;

	public Game()
	{
		capturedWhites = new ArrayList<Piece>();
		capturedBlacks = new ArrayList<Piece>();
		board = new Board(gameMode);
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
		}

		piece.hasMoved = true;
		piece.position = move.to;
		
		System.out.println("Check: White " + detectCheck(true) + " Black " + detectCheck(false));
		
		isWhiteTurn = !isWhiteTurn;
	}

	public String isValidMove(Movement move)
	{
		if (!move.isValid())
			return "You typed your move incorrectly!";

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
			
			if (pieceAtDestination.name.equals("king"))
				return "You can't capture the king!";
		}

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
					else
					{
						return "This move is blocked";
					}
				}
			}

		}

		return "This is not a valid move";
	}

	public boolean isMoveBlocked(Move move, Movement movement)
	{
		Point checkpoint = new Point(movement.from.x, movement.from.y);
		System.out.println(move.toString());
		for (int i = 0; i < 8; i++)
		{
			checkpoint.x += sign(move.rightward);
			checkpoint.y += sign(move.downward);

			System.out.println(checkpoint.toString());
			if (checkpoint.equals(movement.to))
				return false;

			if (checkpoint.isValid())
			{
				System.out.println(board.getPiece(checkpoint).toString());
				if (board.getPiece(checkpoint) != null)
					return true;
			}
		}
		
		return true;
	}

	public boolean isCheck(boolean isWhite)
	{
		return detectCheck(isWhite) == 1;
	}

	public boolean isCheckmate(boolean isWhite)
	{
		return detectCheck(isWhite) == 2;
	}

	// 0 - not check
	// 1 - check
	// 2 - checkmate
	public int detectCheck(boolean isWhite)
	{
		int checkState = 0;
		
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
			if (!errorCode.equals("This is not a valid move") && !errorCode.equals("This move is blocked"))
				return 1;
		}

		return 0;
	}

	public int sign(int input)
	{
		if (input == 0)
			return 0;
		return input < 0 ? -1 : 1;
	}
}
