package gameLogic.evaluation;

import gameLogic.GameState;
import gameLogic.GameStateManager;

public class GuriEvaluator implements Evaluator{
int myClr;
	
	public GuriEvaluator(int myClr) {
		this.myClr = myClr;
	}

	public int evaluate(GameState gs){

		int myScore = 0;
		int theirScore=0;
		int myTotalPieces=0;
		int theirTotalPieces=0;

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
					myTotalPieces++;
				}else if(piece == myClr+2){
					myScore += 50;
					myTotalPieces++;
				}else if(piece == opntClr){
					theirScore += 10;
					theirTotalPieces++;
				}else if(piece == opntClr+2){
					theirScore += 50;
					theirTotalPieces++;
				}
				
				//count the y value on the board of each piece:
				//see who's more advanced to the other side
				//only if it is a non king
				if(piece==1 || piece==2){
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
						theirScore += 5;
					} else if (myAdvancement > theirAdvancement){
						myScore += 5;
					}
				}
				
				//if they are in the middle, they are worth more
				if((i>1 && i<6) && (j<5 && j>2) ){
					if(piece==myClr){
						myScore += 3;
					}else if(piece==myClr+2){
						myScore += 3;
					}else if(piece == opntClr){
						theirScore += 3;
					}else if(piece==opntClr+2){
						theirScore += 3;
					}
				}
				
				//try not to move the home row
				if(myClr==2){
					if(j==0 && piece==myClr){
						myScore +=10;
					}
				}else if(myClr==1){
					if(j==7 && piece==myClr){
						myScore +=10;
					}
				}
				
				//try to move in groups, so the opponent cant jump you
				if(piece == 1 || piece == 2){
					try{
						if(myClr==1){ //if im black
							if(piece==myClr){ //if its my piece, see if it has back up
								int backUp = gs.getStateOfSquare(i+1, j+1); //up and to the right
								if(backUp==myClr){
									myScore = myScore + 3;
								}
								backUp = gs.getStateOfSquare(i+1, j-1); //up and to the left
								if(backUp==myClr){
									myScore = myScore + 3;
								}
							}
						}
						if(myClr==2){ //if im red
							if(piece==myClr ){ //if its my piece, see if it has back up
								int backUp = gs.getStateOfSquare(i-1, j+1); //up and to the right
								if(backUp==myClr){
									myScore = myScore + 3;
								}
								backUp = gs.getStateOfSquare(i-1, j-1); //up and to the left
								if(backUp==myClr){
									myScore = myScore + 3;
								}
							}
						}
					}catch (IndexOutOfBoundsException e){
						//meaning we are at the edge
					}
				}
				
				//the one move corners
				if(myClr==2 && i==0 && j==0){
					if(piece==myClr){
						myScore -=10;
					}
				}
				if(myClr==1 && i==7 && j==0){
					if(piece==myClr){
						myScore -=10;
					}
				}
			}
		}
		//if i have no pieces thats bad
		if(myTotalPieces==0){
			return Integer.MIN_VALUE;
		}
		//if they have no pieces thats good!
		if(theirTotalPieces==0){
			return Integer.MAX_VALUE;
		}
		
		if (theirScore < 1) return Integer.MAX_VALUE;
		if (myScore < 1) return Integer.MIN_VALUE;
		
		return myScore - theirScore;
	}	
}
