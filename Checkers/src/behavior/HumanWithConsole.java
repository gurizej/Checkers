package behavior;

import gameLogic.GameStateManager;
import gameLogic.Move;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanWithConsole extends MoveStrategy{

	public void makeMove(GameStateManager gsm) {
		boolean success = false;
		Move move = null;
		while (success == false){
			Scanner keyboard = new Scanner(System.in);
			//getMoves test
			ArrayList<ArrayList<Move>> possibleMoves = gsm.getMoves(color);
			if (possibleMoves.isEmpty() == true){
				System.out.println("There are no possible moves");
				return;
			}
			System.out.println("These are your moves (from, to)");
			for (ArrayList<Move> bMove: possibleMoves){
				for (Move sMove: bMove){
					System.out.print(gsm.getClassNotation(sMove.xFrom, sMove.yFrom) +", "+ gsm.getClassNotation(sMove.xTo, sMove.yTo)+" - ");
				}
				System.out.println("");
			}
			
			//get in the change they made
			System.out.println("Enter in the square number for the move you want to make (From then To)");
			
			int from = keyboard.nextInt();
			int to = keyboard.nextInt();
			
			//now get the actual notation
			
			int xFrom = gsm.getTheXPosition(from);
			int yFrom = gsm.getTheYPosition(from);
			int xTo = gsm.getTheXPosition(to);
			int yTo = gsm.getTheYPosition(to);
			//set move
			move = new Move(xFrom, yFrom, xTo, yTo);
			//check if move is legal
			for (ArrayList<Move> m : possibleMoves){
				//look at the first and last move of every possible move
				//if the first from and the last to match the players move
				if (m.get(0).xFrom == move.xFrom && m.get(0).yFrom == move.yFrom 
						&& m.get(m.size()-1).xTo == move.xTo && m.get(m.size()-1).yTo == move.yTo){
					//Execute the move and return;
					gsm.executeMove(m);
					success = true;
					return;
				}
			
			}
			//success = gsm.attemptMove(move, color);
			/*/if legal and not a jump execute move
			if (success == true){
				ArrayList<Move> moves = new ArrayList<Move>();
				moves.add(move);
				gsm.executeMove(moves);
			}*/
			
			//otherwise move is invalid
			//else
				System.out.println("Invalid Move");
		}
		
	}

	
	
}
