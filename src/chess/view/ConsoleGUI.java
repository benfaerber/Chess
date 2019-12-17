package chess.view;

import java.util.ArrayList;

import chess.model.*;

public class ConsoleGUI
{
	public String whiteSquare = "x";
	public String blackSquare = "o️";
	
	public boolean isSquareWhite(int x, int y)
	{
		boolean isWhite = x % 2 == 0;
		isWhite = y % 2 == 0 ? isWhite : !isWhite;
		return isWhite;
	}
	
	public void renderBoard(Game game)
	{
		String render = "\n  a b c d e f g h\n";
		for (int y = 0; y < game.board.length; y++)
		{
			render += Integer.toString(8-y);
			for (int x = 0; x < game.board[y].length; x++)
			{
				Piece piece = game.board[y][x];
				render += " ";
				if (piece == null)
					render += isSquareWhite(x, y) ? whiteSquare : blackSquare;
				else
					render += piece.symbol;
			}
			render = render.substring(0, render.length());
			render += deadPieces(game, y);
			render += "\n";
		}
		render += "\nWhite: " + game.scoreWhite + ", Black: " + game.scoreBlack;
		//render += "\nDebug Info: " + game.stringToPoint("c7").toString();
		System.out.println(render);
	}
	
	public String deadPieces(Game game, int rowNumber)
	{
		String outputString = "";
		boolean isWhite = rowNumber == 0 || rowNumber == 1;
		boolean isFirst = rowNumber == 0 || rowNumber == 7;
		boolean isSecond = rowNumber == 1 || rowNumber == 6;
		ArrayList<Piece> pieces = isWhite ? game.capturedWhites : game.capturedBlacks;
		
		if (pieces.size() > 0)
		{
			if (isFirst)
			{
				int deadFirstRow = pieces.size();
				if (deadFirstRow > 8)
					deadFirstRow = 8;
				
				for (int i = 0; i < deadFirstRow; i++)
					outputString += pieces.get(i).symbol;
			}
			else if (isSecond)
			{
				if (pieces.size() > 8)
				{
					for (int i = 8; i < pieces.size(); i++)
						outputString += pieces.get(i).symbol;
				}
			}
		}
		
		if (!outputString.equals(""))
			outputString = " " + outputString;
		
		return outputString;
	}
}
