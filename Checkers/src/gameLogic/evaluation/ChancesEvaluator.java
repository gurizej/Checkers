package gameLogic.evaluation;

import gameLogic.GameState;

public class ChancesEvaluator implements Evaluator {

	int myClr;
	
	public ChancesEvaluator(int myClr) {
		this.myClr = myClr;
	}

	public int evaluate(GameState gs){

		int myScore = 0;
		int theirScore=0;

		int opntClr = 5;
		if(myClr==1){
			opntClr=2;
		}else{
			opntClr=1;
		}
		//if im red
		if(myClr%2==0){
			myClr=2;
		}

		int myAdvancement = 0;
		int theirAdvancement = 0;
		
		//get the # of kings and pieces
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				int piece=gs.getStateOfSquare(i, j);
				//count pieces on the board
				if(piece==myClr){
					myScore += 10;
				}else if(piece == myClr+2){
					myScore += 30;
				}else if(piece == opntClr){
					theirScore += 10;
				}else if(piece == opntClr+2){
					theirScore += 30;
				}
				
				//count the y value on the board of each piece:
				//see who's more advanced to the other side
				if (piece == myClr) {
					if (piece == GameState.RED) {
						myAdvancement += i;
					} else if (piece == GameState.BLACK) {
						myAdvancement += 7 - i;
					}
				} else if (piece == opntClr){
					
					if (piece == GameState.RED) {
						theirAdvancement += i;
					} else {
						theirAdvancement += 7 - i;
					}
				}
				if (theirAdvancement > myAdvancement) {
					theirScore += 1;
				} else if (myAdvancement > theirAdvancement){
					myScore += 1;
				}
				
				
				//if they are in the middle, they are worth more
//				if((i>1 && i<6) && (j<5 && j>2) ){
//					if(piece==myClr){
//						myScore += .01;
//					}else if(piece==myClr+2){
//						myScore += .01;
//					}else if(piece == opntClr){
//						theirScore += .01;
//					}else if(piece==opntClr+2){
//						theirScore += .01;
//					}
//				}
			}
		}
		
		if (theirScore < 1) return Integer.MAX_VALUE;
		if (myScore < 1) return Integer.MIN_VALUE;
		
		return myScore - theirScore;
	}	
}
