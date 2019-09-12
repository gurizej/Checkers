package gameLogic.search;

import java.util.ArrayList;

import gameLogic.GameStateManager;
import gameLogic.Move;
import gameLogic.Node;
import gameLogic.evaluation.Evaluator;

public class DifferentMinMaxSearcher {

	Evaluator evaluator;
	int me, opponent;

	public DifferentMinMaxSearcher(Evaluator evaluator, int player) {
		this.evaluator = evaluator;
		me = player;
		opponent = ((player == 1) ? 0 : 1);
	}

	public Node minMax(GameStateManager gsm){

		int depth = 8;
		Node bestMove = max(depth, gsm);
		return bestMove;
	}

	private Node max(int depth, GameStateManager gsm) {		
		if (depth == 0) {
			Node n = new Node();
			n.setValue(evaluator.evaluate(gsm.getGameState()));
			return n;
		} else {
			ArrayList<ArrayList<Move>> possibleMovesForMe = gsm.getMoves(me);
			Node bestMoveForMe = new Node();
			bestMoveForMe.setValue(Integer.MIN_VALUE);
			
			for (ArrayList<Move> move : possibleMovesForMe) {
				GameStateManager newgsm = gsm.getCopy();
				newgsm.executeMove(move);
				Node current = min(depth - 1, newgsm);
				
				if (current.getValue() > bestMoveForMe.getValue()) {
					bestMoveForMe.setMove(move);
					bestMoveForMe.setValue(current.getValue());
				}
			}
			return bestMoveForMe;
		}
	}

	private Node min(int depth, GameStateManager gsm) {
		if (depth == 0) {
			Node n = new Node();
			n.setValue(evaluator.evaluate(gsm.getGameState()));
			return n;
		} else {
			ArrayList<ArrayList<Move>> possibleMovesForThem = gsm.getMoves(opponent);
			Node bestMoveForThem = new Node();
			bestMoveForThem.setValue(Integer.MAX_VALUE);
			
			for (ArrayList<Move> move : possibleMovesForThem) {
				GameStateManager newgsm = gsm.getCopy();
				newgsm.executeMove(move);
				Node current = max(depth - 1, newgsm);
				
				if (current.getValue() < bestMoveForThem.getValue()) {
					bestMoveForThem.setMove(move);
					bestMoveForThem.setValue(current.getValue());
				}
			}
			return bestMoveForThem;
		}
	}
}







