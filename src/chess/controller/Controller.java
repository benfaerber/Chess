package chess.controller;

import java.util.Scanner;

import chess.model.*;
import chess.view.*;

public class Controller
{
	private Scanner scanner;
	private Game game;
	private ConsoleGUI gui;
	private boolean hasGameover = false;
	private boolean whiteWon = false;
	
	public void start()
	{
		scanner = new Scanner(System.in);
		game = new Game();
		gui = new ConsoleGUI();
		gui.renderBoard(game);
		
		while (!hasGameover)
		{
			update();
		}
		gameover();
	}
	
	public void update()
	{
		String nextMove = "";
		Movement move = null;
		String moveError = "first";
				
		System.out.print(game.isWhiteTurn ? "White" : "Black");
		System.out.print("'s Turn: ");
		nextMove = scanner.nextLine();
		
		if (nextMove.equals("score"))
		{
			gui.showScore(game);
		}
		else if (nextMove.equals("debug"))
		{
			//game.board.generate();
			//game.board.setPiece(new Point("b6"), new Piece("pawn", true));
		}
		else
		{
			move = new Movement(nextMove);
			if (move.isValid())
			{
				moveError = game.isValidMove(move);
				
				if (game.checkProperTeam(move))
				{
					if (moveError.equals(""))
					{
						if (!game.makeMove(move))
						{
							System.out.println("\nThis would put you in check!");
						}
					}
					else
					{
						System.out.println("\n" + moveError);	
					}
				}
				else
				{
					System.out.println("\nThis is not a valid move");	
				}
			}
			else
			{
				System.out.println("\nYou typed your move incorrectly!");
			}
		}
		
		if (game.isCheck(true))
			System.out.println("White is in check!");
		
		if (game.isCheck(false))
			System.out.println("Black is in check!");
		
		gui.renderBoard(game);
		
		if (nextMove.equals("checkmate"))
		{
			if (game.isCheck(true))
			{
				hasGameover = true;
				whiteWon = false;
			}
			else if (game.isCheck(false))
			{
				hasGameover = true;
				whiteWon = true;
			}
		}
	}
	
	public void gameover()
	{
		String winner = whiteWon ? "White" : "Black";
		System.out.println("Checkmate! " + winner + " won!");
		System.out.print("The score was: ");
		gui.showScore(game);
	}
}
