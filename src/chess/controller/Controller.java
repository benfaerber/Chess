package chess.controller;

import java.util.Scanner;

import chess.model.*;
import chess.view.*;

public class Controller
{
	private Scanner scanner;
	private Game game;
	private ConsoleGUI gui;
	
	public void start()
	{
		scanner = new Scanner(System.in);
		game = new Game();
		gui = new ConsoleGUI();
		gui.renderBoard(game);
		
		while (true)
		{
			update();
		}
	}
	
	public void update()
	{
		
		String nextMove = "";
		Movement move = null;
		String moveError = "first";
				
		System.out.print(game.isWhiteTurn ? "White" : "Black");
		System.out.print("'s Turn: ");
		nextMove = scanner.nextLine();
		move = game.parseMove(nextMove);
		moveError = game.isValidMove(move);
		
		if (moveError.equals(""))
			game.makeMove(move);
		else
			System.out.println(moveError);
		
		gui.renderBoard(game);
	}
}
