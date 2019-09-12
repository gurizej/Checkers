package gameLogic.search;

import gameLogic.GameStateManager;
import gameLogic.Move;
import gameLogic.Node;
import gameLogic.evaluation.Evaluator;

import java.util.ArrayList;

public class MinMaxSearcher {
	
	Evaluator evaluator;
	Boolean isMyTurn;
	
	public MinMaxSearcher(Evaluator evaluator) {
		this.evaluator = evaluator;
		isMyTurn = true;
	}
	
	public Node minMax(int level, GameStateManager gsm, int player){
		Node best = max(level, gsm, player);		
		return best;
	}
	
	public Node min(int level, GameStateManager gsm, int player) {
		ArrayList<ArrayList<Move>> possibleMoves = new ArrayList<ArrayList<Move>>();
		possibleMoves=gsm.getMoves(player);
		int oppositePlayer = ((player == 1) ? 0 : 1);

		//if we are at the max depth
		if (level <= 0 || possibleMoves.size()==0){
			Node node = new Node();
			node.setValue(evaluator.evaluate(gsm.getGameState()));
			isMyTurn = !isMyTurn;
			return node;
		}		
		
		Node best = new Node();
		best.setValue(10000);
		
		for (ArrayList<Move> move : possibleMoves){
			GameStateManager newgsm = gsm.getCopy();
			newgsm.executeMove(move);
			Node current = new Node();
			current = max(level -1, newgsm, oppositePlayer);
			current.setMove(move);
			if (current.getValue() < best.getValue()){
				best.setMove(current.getMove());
				best.setValue(current.getValue());
			}
		}
		
		return best;
	}
	
	public Node max(int level, GameStateManager gsm, int player) {
		ArrayList<ArrayList<Move>> possibleMoves = new ArrayList<ArrayList<Move>>();
		possibleMoves=gsm.getMoves(player);
		int oppositePlayer = ((player == 1) ? 0 : 1);

		//if we are at the max depth
		if (level <= 0 || possibleMoves.size()==0){
			Node node = new Node();
			node.setValue(evaluator.evaluate(gsm.getGameState()));
			isMyTurn = !isMyTurn;
			return node;
		}		
		
		Node best = new Node();
		best.setValue(10000);
		
		for (ArrayList<Move> move : possibleMoves){
			GameStateManager newgsm = gsm.getCopy();
			newgsm.executeMove(move);
			Node current = new Node();
			current = min(level -1, newgsm, oppositePlayer);
			current.setMove(move);
			if (current.getValue() < best.getValue()){
				best.setMove(current.getMove());
				best.setValue(current.getValue());
			}
		}
		
		return best;
	}

}



















