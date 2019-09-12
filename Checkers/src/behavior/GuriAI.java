package behavior;

import gameLogic.GameStateManager;
import gameLogic.Move;
import gameLogic.Node;
import gameLogic.evaluation.ChancesEvaluator;
import gameLogic.evaluation.Evaluator;
import gameLogic.evaluation.GuriEvaluator;
import gameLogic.search.DifferentMinMaxSearcher;

public class GuriAI extends MoveStrategy{

	public void makeMove(GameStateManager gsm){
		int myClr = this.getColor();
		Evaluator evaluator = new GuriEvaluator(myClr);		
		DifferentMinMaxSearcher searcher = new DifferentMinMaxSearcher(evaluator, myClr);
		
		Node moveToMake = searcher.minMax(gsm);
		
		//print out the move we are making
		try{
			for(int k=0; k<moveToMake.getMove().size(); k++){
				System.out.println(gsm.getClassNotation(((Move) moveToMake.getMove().get(k)).getxFrom(), ((Move) moveToMake.getMove().get(k)).getyFrom())+"," +
						gsm.getClassNotation(((Move) moveToMake.getMove().get(k)).getxTo(), ((Move) moveToMake.getMove().get(k)).getyTo()));
			}

			//execute the best move
//			System.out.println("score of best move: " + moveToMake.getValue());
			gsm.executeMove(moveToMake.getMove());
		}catch(NullPointerException e){
			System.out.println("No possibe moves");
//			gsm.noMoves=true;
		}
		
	}
}
