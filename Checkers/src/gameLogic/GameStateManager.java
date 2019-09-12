package gameLogic;

import java.util.ArrayList;
import java.util.Collection;

public class GameStateManager {

    private GameState gameState;
    public boolean  noMoves;
    
    public GameStateManager(GameState gameState) {
        this.gameState = gameState;
        this.noMoves = false;
        //setJumpTest();
        //setNewGame();
    }
    
    public GameStateManager getCopy() {
		GameState newState = new GameState();

		for(int k=0; k<8; k++){
			for(int j=0; j<8; j++){
				int oldStateOFSquare = gameState.getStateOfSquare(j, k);
				newState.setStateOfSquare(oldStateOFSquare, j, k);
			}
		}
		GameStateManager newGsm = new GameStateManager(newState);
    	
    	return newGsm;
    }
    
    public GameState getGameState() {
        return gameState;
    }
    //Get Moves
    //takes in a game state and a color
    //returns an array list of ArrayList<Move>
    public ArrayList<ArrayList<Move>> getMoves(int color){//TODO
    	ArrayList<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();
    	boolean jumpsExist = false;
    	//look for jumps
    	//search the board for pieces of your color
        for (int i=0; i<8; i++){
        	for(int j=0; j<8; j++){
        		//if the piece is the right color
        		if ((gameState.getStateOfSquare(i, j) != 0)&&(gameState.getStateOfSquare(i, j)%2 == color)){
//        			System.out.println("found a piece you own");
        			//find all jumps of that piece
        			//get jumps
        			ArrayList<ArrayList<Move>> jumps = getJumps(color, i, j);
        			//if there are jumps return only the jumps
        			if (jumps.isEmpty() == false){
        				moves.addAll(jumps);
        				jumpsExist = true;
        			}
        		}
        	}
        }
        if (jumpsExist == false){
	        //look for diagonals
	    	//search the board for pieces of your color
	        for (int i=0; i<8; i++){
	        	for(int j=0; j<8; j++){
	        		//if the piece is the right color
	        		if ((gameState.getStateOfSquare(i, j) != 0)&&(gameState.getStateOfSquare(i, j)%2 == color)){
//	        			System.out.println("found a piece you own");
	        			//get diagonals
	        			//try up right
	        			Move tryMove = new Move(i,j,i+1,j+1);
	        			if (attemptMove(tryMove, color)){// if this move is valid add it to moves
	        				addMove(tryMove, moves);
	        			}
	        			//try up left
	        			tryMove = new Move(i,j,i-1,j+1);
	        			if (attemptMove(tryMove, color)){// if this move is valid add it to moves
	        				addMove(tryMove, moves);
	        			}
	        			//try down right
	        			tryMove = new Move(i,j,i+1,j-1);
	        			if (attemptMove(tryMove, color)){// if this move is valid add it to moves
	        				addMove(tryMove, moves);
	        			}
	        			//try down left
	        			tryMove = new Move(i,j,i-1,j-1);
	        			if (attemptMove(tryMove, color)){// if this move is valid add it to moves
	        				addMove(tryMove, moves);
	        			}
	        			}
	        		}
	        }
	    }
        if (moves.isEmpty() == true){
        	noMoves = true;
        }
        			//
    	return moves;
    }
    
    //add specified move array to the array of moves
    public void addMove(Move tryMove, ArrayList<ArrayList<Move>> moves){
//			System.out.println("Added: " + getClassNotation(tryMove.xFrom, tryMove.yFrom) +"" + " "+ getClassNotation(tryMove.xTo, tryMove.yTo));
			ArrayList<Move> move = new ArrayList<Move>();
			move.add(tryMove);
			moves.add(move);
    }
    
    //returns true if valid move (doesn't check right now)
    public boolean attemptMove(Move move, int color) {
        //ArrayList<Move> jumps = canJump(color);
        //make sure the to and from values don't go out of bounds
        if (move.xFrom > 7 || move.yFrom > 7 || move.xTo > 7 || move.yTo > 7 ||
        	move.xFrom < 0 || move.yFrom < 0 || move.xTo < 0 || move.yTo < 0 ){
//            System.out.println("out of bounds");
            return false;
        }
        int stateOfFrom = gameState.getStateOfSquare(move.xFrom, move.yFrom);
        int stateOfTo = gameState.getStateOfSquare(move.xTo, move.yTo);
        

        //if there in no piece in the "from" location return false
        if (stateOfFrom == 0){
//            System.out.println("no piece at 'from'");
            return false;
        }
        
        //if there is a piece in the "to" location return false
        if (!(stateOfTo == 0)){
//            System.out.println("'to' is not empty");
            return false;
        }
        
        //if the "from" piece is not the correct color return false
        if (!(gameState.getStateOfSquare(move.xFrom, move.yFrom)%2 == color))
        {
//            System.out.println("that is not your piece");
            return false;
        }
        
        //check if the "from" piece is moving in the right direction
        
        /*if (jumps.isEmpty() == false) //if there are jumps.
        {
            System.out.println("there are jumps");
            //for every possible jump
            for (int i=0; i<jumps.size(); i++){
            	//if this move matches a possible jump then it is valid
            	System.out.println("is this jump "+ i + "?");
            	if ((move.xFrom == jumps.get(i).xFrom)&&
            			(move.yFrom == jumps.get(i).yFrom)&&
            			(move.xTo == jumps.get(i).xTo)&&
            			(move.yTo == jumps.get(i).yTo)){
            		System.out.println("yes");
            		return true;
            	}
            	else{
            		System.out.println("there are possible jumps");
            	}
            		
            	
            }
            //return false;
            
            //handle jumps
        }
        */
        //moving diagonally
        else{
            if (move.xTo == move.xFrom + 1 || move.xTo == move.xFrom - 1){
                //if (stateOfFrom >= 3) 
                //if piece is king it can move both forward and back
                if (stateOfFrom >= 3 && (move.yTo == move.yFrom + 1 || move.yTo == move.yFrom - 1)){
                	return true;
                }
                //red can only move up
                else if(color == 0 && (move.yTo == move.yFrom + 1)){
                	return true;
                }
                //black can only move down
                else if(color == 1 && (move.yTo == move.yFrom - 1)){
                	return true;
                }
                else{
//                    System.out.println("wrong way");
                    return false;
                }
            }
            else{
//                System.out.println("too far away");
                return false;
            }
        }
        //return true;
        
        
    }
    
    public ArrayList<Move> canJump(int color) {
        ArrayList<Move> jumps = new ArrayList<Move>();
        
        //search the board for jumps of your color
//        System.out.println("looking for jumps");
        for (int i=0; i<8; i++){
        	for(int j=0; j<8; j++){
        		//if the piece is the right color
        		if ((gameState.getStateOfSquare(i, j) != 0)&&(gameState.getStateOfSquare(i, j)%2 == color)){
//        			System.out.println("found a piece you own");
        			int x = i;
        			int y = j;
        			jumps.addAll(possibleJumps(color, x, y, i, j));
        		}
        	}
        }
        return jumps;
    }
    
    public ArrayList<Move> possibleJumps(int color, int x, int y, int i, int j) {
    	
        ArrayList<Move> jumps = new ArrayList<Move>();
		
			//check if diagonal is out of bounds then
        
			//if any diagonal pieces are not empty and not your color
			//up left
        if(color == 0 || gameState.getStateOfSquare(i,j) >= 3){ // if player is red or a king
			if (i-1 > 0 && j+1 < 7){
    			if (gameState.getStateOfSquare(i-1, j+1) != 0 && gameState.getStateOfSquare(i-1, j+1)%2 != color){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i-2, j+2) == 0){
//    					System.out.println("added jump");
    					//attempt to find more jumps
    					ArrayList<Move> multiJumps = possibleJumps(color, x, y, i-2, j+2);
    					if (multiJumps.isEmpty() == false)
    						jumps.addAll(multiJumps);
    					else
    						jumps.add(new Move(x, y, i-2, j+2));
    				}
    			}
			}
			
			// up right
			if (i+1 < 7 && j+1 < 7){
    			if (gameState.getStateOfSquare(i+1, j+1) != 0 && gameState.getStateOfSquare(i+1, j+1)%2 != color){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i+2, j+2) == 0){
//    					System.out.println("added jump");
    					//attempt to find more jumps
    					ArrayList<Move> multiJumps = possibleJumps(color, i, j, i+2, j+2);
    					if (multiJumps.isEmpty() == false)
    						jumps.addAll(multiJumps);
    					else
    						jumps.add(new Move(x, y, i+2, j+2));
    				}
    			}
			}
        }
        if(color == 1 || gameState.getStateOfSquare(i,j) >= 3){ // if player is black or a king
			// down left
			if (i-1 > 0 && j-1 > 0){
    			if (gameState.getStateOfSquare(i-1, j-1) != 0 && gameState.getStateOfSquare(i-1, j-1)%2 != color){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i-2, j-2) == 0){
//    					System.out.println("added jump");
    					//attempt to find more jumps
    					ArrayList<Move> multiJumps = possibleJumps(color, x, y, i-2, j-2);
    					if (multiJumps.isEmpty() == false)
    						jumps.addAll(multiJumps);
    					else
    						jumps.add(new Move(x, y, i-2, j-2));
    				}
    			}
			}
			
			//down left
			if (i+1 < 7 && j-1 > 0){
    			if (gameState.getStateOfSquare(i+1, j-1) != 0 && gameState.getStateOfSquare(i+1, j-1)%2 != color){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i+2, j-2) == 0){
//    					System.out.println("added jump");
    					//attempt to find more jumps
    					ArrayList<Move> multiJumps = possibleJumps(color, x, y, i+2, j-2);
    					if (multiJumps.isEmpty() == false)
    						jumps.addAll(multiJumps);
    					else
//    						System.out.println("jump ");
    						jumps.add(new Move(x, y, i+2, j-2));
    				}
    			}
			}
        }
			
		else;
		return jumps;
    }
    
public ArrayList<ArrayList<Move>> getJumps(int color, int i, int j) {//TODO
    	
	ArrayList<ArrayList<Move>> Alljumps = new ArrayList<ArrayList<Move>>();
		
			//check if diagonal is out of bounds then
	//if (!(i < 0 || i > 7 || j < 0 || j > 7)){
			//if any diagonal pieces are not empty and not your color
			//up left
	 	if(gameState.getStateOfSquare(i,j) == 2 || gameState.getStateOfSquare(i,j) >= 3){ // if player is red or a king
			if (i-1 > 0 && j+1 < 7){
    			if (gameState.getStateOfSquare(i-1, j+1) != 0 
    					&& gameState.getStateOfSquare(i-1, j+1)%2 != color 
    					&& gameState.getStateOfSquare(i-1, j+1) < 6){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i-2, j+2) == 0){
//    					System.out.println("found jump");
    					//create new jump move
    					ArrayList<Move> jump = new ArrayList<Move>();
    					//add this jump as a part of the new jump move
    					jump.add( new Move(i, j, i-2, j+2));
    					//set the end location of the jump to the current piece
    					//then set the current location to empty
    					//to prepare to check for multijumps
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i,j), i-2, j+2);
    					gameState.setStateOfSquare(GameState.EMPTY , i, j);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i-1,j+1) + 6, i-1,j+1);
    					if (getJumps(color, i-2, j+2).isEmpty() == false){//check if more jumps could be made
    						//if they exist get all of the multijumps
    						ArrayList<ArrayList<Move>> multiJumps = (getJumps(color, i-2, j+2));
    						//then combine each of the multijumps with the current jump to get the full jump path
    						//for each jump returned 
    						for (int m = 0; m < multiJumps.size(); m++){
    							//append each jumps move to the current jump
    							ArrayList<Move> tempJump = new ArrayList<Move>();
    							tempJump.addAll(jump);
    							tempJump.addAll(multiJumps.get(m)); 
    							//then add the move of full parts for this jump to the list of all jumps from this piece
    							Alljumps.add(tempJump);
    						}
    						
    					}
    					else	//otherwise just create a normal jump
    						Alljumps.add(jump);
    					
    					//reset the checked squares
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i-2, j+2), i, j);
    					gameState.setStateOfSquare(GameState.EMPTY, i-2, j+2);
    					gameState.setStateOfSquare((gameState.getStateOfSquare(i-1,j+1)) -6, i-1,j+1);

    				}
    			}
			}
			
			// up right
			if (i+1 < 7 && j+1 < 7){
    			if (gameState.getStateOfSquare(i+1, j+1) != 0 
    					&& gameState.getStateOfSquare(i+1, j+1)%2 != color
    					&& gameState.getStateOfSquare(i+1, j+1) < 6){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i+2, j+2) == 0){
//    					System.out.println("added jump");
    					//create new jump move
    					ArrayList<Move> jump = new ArrayList<Move>();
    					//add this jump as a part of the new jump move
    					jump.add( new Move(i, j, i+2, j+2));
    					//set the end location of the jump to the current piece
    					//then set the current location to empty
    					//to prepare to check for multijumps
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i,j), i+2, j+2);
    					gameState.setStateOfSquare(GameState.EMPTY , i, j);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+1,j+1) + 6, i+1,j+1);
    					if (getJumps(color, i+2, j+2).isEmpty() == false){//check if more jumps could be made
    						//if they exist get all of the multijumps
    						ArrayList<ArrayList<Move>> multiJumps = (getJumps(color, i+2, j+2));
    						//then combine each of the multijumps with the current jump to get the full jump path
    						//for each jump returned 
    						for (int m = 0; m < multiJumps.size(); m++){
    							//append each jumps move to the current jump
    							ArrayList<Move> tempJump = new ArrayList<Move>();
    							tempJump.addAll(jump);
    							tempJump.addAll(multiJumps.get(m)); 
    							//then add the move of full parts for this jump to the list of all jumps from this piece
    							Alljumps.add(tempJump);
    						}
    						
    					}
    					else	//otherwise just create a normal jump
    						Alljumps.add(jump);
    					
    					//reset the checked squares
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+2, j+2), i, j);
    					gameState.setStateOfSquare(GameState.EMPTY, i+2, j+2);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+1,j+1)-6, i+1,j+1);
    				}
    			}
			}
        }
        
        if(gameState.getStateOfSquare(i,j) == 1 || gameState.getStateOfSquare(i,j) >= 3){ // if player is black or a king
			// down left
        	//check if the piece next to it is within the bounds
			if (i-1 > 0 && j-1 > 0){
				//check if the piece next to it has an enemy piece
    			if (gameState.getStateOfSquare(i-1, j-1) != 0 
    					&& gameState.getStateOfSquare(i-1, j-1)%2 != color
    					&& gameState.getStateOfSquare(i-1, j-1) < 6){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i-2, j-2) == 0){
//    					System.out.println("added jump");
    					//create new jump move
    					ArrayList<Move> jump = new ArrayList<Move>();
    					//add this jump as a part of the new jump move
    					jump.add( new Move(i, j, i-2, j-2));
    					//set the end location of the jump to the current piece
    					//then set the current location to empty
    					//to prepare to check for multijumps
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i,j), i-2, j-2);
    					gameState.setStateOfSquare(GameState.EMPTY , i, j);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i-1,j-1) + 6, i-1,j-1);
    					if (getJumps(color, i-2, j-2).isEmpty() == false){//check if more jumps could be made
    						//if they exist get all of the multijumps
    						ArrayList<ArrayList<Move>> multiJumps = (getJumps(color, i-2, j-2));
    						//then combine each of the multijumps with the current jump to get the full jump path
    						//for each jump returned 
    						for (int m = 0; m < multiJumps.size(); m++){
    							//append each jumps move to the current jump
    							ArrayList<Move> tempJump = new ArrayList<Move>();
    							tempJump.addAll(jump);
    							tempJump.addAll(multiJumps.get(m)); 
    							//then add the move of full parts for this jump to the list of all jumps from this piece
    							Alljumps.add(tempJump);
    						}
    						
    					}
    					else	//otherwise just create a normal jump
    						Alljumps.add(jump);
    					
    					//reset the checked squares
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i-2, j-2), i, j);
    					gameState.setStateOfSquare(GameState.EMPTY, i-2, j-2);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i-1,j-1) - 6, i-1,j-1);
    				}
    			}
			}
			
			//down right
			if (i+1 < 7 && j-1 > 0){
    			if (gameState.getStateOfSquare(i+1, j-1) != 0 
    					&& gameState.getStateOfSquare(i+1, j-1)%2 != color
    					&& gameState.getStateOfSquare(i+1, j-1) < 6){
    				//if the space beyond is empty add the move to the list
    				if (gameState.getStateOfSquare(i+2, j-2) == 0){
//    					System.out.println("added jump");
    					//create new jump move
    					ArrayList<Move> jump = new ArrayList<Move>();
    					//add this jump as a part of the new jump move
    					jump.add( new Move(i, j, i+2, j-2));
    					//set the end location of the jump to the current piece
    					//then set the current location to empty
    					//to prepare to check for multijumps
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i,j), i+2, j-2);
    					gameState.setStateOfSquare(GameState.EMPTY , i, j);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+1,j-1) + 6, i+1,j-1);
    					if (getJumps(color, i+2, j-2).isEmpty() == false){//check if more jumps could be made
    						//if they exist get all of the multijumps
    						ArrayList<ArrayList<Move>> multiJumps = (getJumps(color, i+2, j-2));
    						//then combine each of the multijumps with the current jump to get the full jump path
    						//for each jump returned 
    						for (int m = 0; m < multiJumps.size(); m++){
    							//append each jumps move to the current jump
    							ArrayList<Move> tempJump = new ArrayList<Move>();
    							tempJump.addAll(jump);
    							tempJump.addAll(multiJumps.get(m)); 
    							//then add the move of full parts for this jump to the list of all jumps from this piece
    							Alljumps.add(tempJump);
    						}
    						
    					}
    					else	//otherwise just create a normal jump
    						Alljumps.add(jump);
    					
    					//reset the checked squares
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+2, j-2), i, j);
    					gameState.setStateOfSquare(GameState.EMPTY, i+2, j-2);
    					gameState.setStateOfSquare(gameState.getStateOfSquare(i+1,j-1) - 6, i+1,j-1);
    				}
    			}
			}
        //}
	}
		return Alljumps;
    }

    //private ArrayList<ArrayList<Move>> getMultiJumps(int color, int i, int j) {
	// TODO Auto-generated method stub
	//return null;
//}

	//public boolean canJumpFrom(int color, int i, int j) {
	// TODO Auto-generated method stub
	//return false;
//}
    
    

	//executes a given move
    public void executeMove(ArrayList<Move> moves){
        //System.out.println(gameState.getStateOfSquare(move.xFrom, move.yFrom));
        //System.out.println(gameState.getStateOfSquare(move.xTo, move.yTo));
    	for (int i=0; i<moves.size(); i++){
	        int squareState = gameState.getStateOfSquare(moves.get(i).xFrom, moves.get(i).yFrom);
	        gameState.setStateOfSquare(GameState.EMPTY, moves.get(i).xFrom, moves.get(i).yFrom);
	        gameState.setStateOfSquare(squareState, moves.get(i).xTo, moves.get(i).yTo);
	        //if red and moved to the top row and not a king, put red king in 'to'
	        if (squareState == 2 && moves.get(i).yTo == 7){
	            gameState.setStateOfSquare(4, moves.get(i).xTo, moves.get(i).yTo);
	        }
	        //if black and moved to bottom row and not a king, put black king in 'to'
	        if (squareState == 1 && moves.get(i).yTo == 0){
	            gameState.setStateOfSquare(3, moves.get(i).xTo, moves.get(i).yTo);
	        }
	        //if you move more than one space (jump)
	        if (Math.abs(moves.get(i).yFrom - moves.get(i).yTo) > 1 || Math.abs(moves.get(i).xFrom - moves.get(i).xTo) > 1 ){
//	        	System.out.println("there was a jump made attempting to delete peice");
	        	
	        	int xRemove = 0;
	        	int yRemove = 0;
	        	
	        	if(moves.get(i).yFrom - moves.get(i).yTo > 1){
	        		yRemove = moves.get(i).yFrom - 1;
	        	}
	        	else if(moves.get(i).yFrom - moves.get(i).yTo < 1){
	        		yRemove = moves.get(i).yFrom + 1;
	        	}
	        	
	        	if(moves.get(i).xFrom - moves.get(i).xTo > 1){
	        		xRemove = moves.get(i).xFrom - 1;
	        	}
	        	else if(moves.get(i).xFrom - moves.get(i).xTo < 1){
	        		xRemove = moves.get(i).xFrom + 1;
	        	}
	        	
	        	gameState.setStateOfSquare(0, xRemove, yRemove);
	        }
    	}
    }
    
    public boolean checkWin() {
        int blacks=0;
        int reds=0;
        
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                //any black pieces
                if(gameState.getStateOfSquare(j, i)==1 || gameState.getStateOfSquare(j, i)==3){
                    blacks += gameState.getStateOfSquare(j, i);
                }
                //any red pieces
                if (gameState.getStateOfSquare(j, i)==2 || gameState.getStateOfSquare(j, i)==4){
                    reds += gameState.getStateOfSquare(j, i);
                }
            }
        }
        
        //win condition here
    	//if a player has no moves
    	if(noMoves){
        	System.out.println("No Moves Left");
            return true;
        }
        //if red wins
        if(blacks==0 && reds>0){
            System.out.println("Red Wins");
            return true;
        }
        //if black wins
        else if(blacks>0 && reds==0){
            System.out.println("Black Wins");
            return true;
        }
        //if none
        else{
            return false;
        }
    }
    
    public void setNewGame() {
        gameState.setStateOfSquare(GameState.RED, 0, 0);
        gameState.setStateOfSquare(GameState.RED, 2, 0);
        gameState.setStateOfSquare(GameState.RED, 4, 0);
        gameState.setStateOfSquare(GameState.RED, 6, 0);
        gameState.setStateOfSquare(GameState.RED, 1, 1);
        gameState.setStateOfSquare(GameState.RED, 3, 1);
        gameState.setStateOfSquare(GameState.RED, 5, 1);
        gameState.setStateOfSquare(GameState.RED, 7, 1);
        gameState.setStateOfSquare(GameState.RED, 0, 2);
        gameState.setStateOfSquare(GameState.RED, 2, 2);
        gameState.setStateOfSquare(GameState.RED, 4, 2);
        gameState.setStateOfSquare(GameState.RED, 6, 2);
        
        gameState.setStateOfSquare(GameState.BLACK, 1, 7);
        gameState.setStateOfSquare(GameState.BLACK, 3, 7);
        gameState.setStateOfSquare(GameState.BLACK, 5, 7);
        gameState.setStateOfSquare(GameState.BLACK, 7, 7);
        gameState.setStateOfSquare(GameState.BLACK, 0, 6);
        gameState.setStateOfSquare(GameState.BLACK, 2, 6);
        gameState.setStateOfSquare(GameState.BLACK, 4, 6);
        gameState.setStateOfSquare(GameState.BLACK, 6, 6);
        gameState.setStateOfSquare(GameState.BLACK, 1, 5);
        gameState.setStateOfSquare(GameState.BLACK, 3, 5);
        gameState.setStateOfSquare(GameState.BLACK, 5, 5);
        gameState.setStateOfSquare(GameState.BLACK, 7, 5);    
    }
    
    public void setJumpTest() {

        gameState.setStateOfSquare(GameState.RED_KING, 6, 0);
        gameState.setStateOfSquare(GameState.BLACK, 5, 1);
        gameState.setStateOfSquare(GameState.BLACK, 3, 1);
        gameState.setStateOfSquare(GameState.BLACK, 3, 3);
        
        gameState.setStateOfSquare(GameState.BLACK, 1, 1);
        
        //gameState.setStateOfSquare(GameState.RED, 2, 0);
        gameState.setStateOfSquare(GameState.RED, 3, 7);
        gameState.setStateOfSquare(GameState.RED, 5, 7);
        
    }
    
    public void getMovesTest() {

        gameState.setStateOfSquare(GameState.RED, 6, 6);
        gameState.setStateOfSquare(GameState.RED, 4, 4);
        gameState.setStateOfSquare(GameState.RED, 2, 2);
        gameState.setStateOfSquare(GameState.RED, 2, 4);
        gameState.setStateOfSquare(GameState.RED, 4, 2);
        gameState.setStateOfSquare(GameState.RED, 4, 6);
        gameState.setStateOfSquare(GameState.RED, 6, 4);
        gameState.setStateOfSquare(GameState.BLACK_KING, 3, 5);

        
    }
	
	//from notation to our actual grid notation for y
	public int getTheYPosition(int y){
		if(y>=1 && y<=4){
			return 7;
		}
		else if(y>=5 && y<=8){
			return 6;
		}
		else if(y>=9 && y<=12){
			return 5;
		}
		else if(y>=13 && y<=16){
			return 4;
		}
		else if(y>=17 && y<=20){
			return 3;
		}
		else if(y>=21 && y<=24){
			return 2;
		}
		else if(y>=25 && y<=28){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	//from notation to our actual grid notation for x now
	public int getTheXPosition(int x){
			if(x==5 || x==13 || x==21 || x==29){
				return 0;
			}
			else if(x==1 || x==9 || x==17 || x==25){
				return 1;
			}
			else if(x==6 || x==14 || x==22 || x==30){
				return 2;
			}
			else if(x==2 || x==10 || x==18 || x==26){
				return 3;
			}
			else if(x==7 || x==15 || x==23 || x==31){
				return 4;
			}
			else if(x==3 || x==11 || x==19 || x==27){
				return 5;
			}
			else if(x==8 || x==16 || x==24 || x==32){
				return 6;
			}
			else{
				return 7;
			}		
	}
	//now going from  grid notation to actual notation
		public int getClassNotation(int x, int y){
			if(x==0){
				//first col
				if(y==0) return 29;
				else if(y==2) return 21;
				else if(y==4) return 13;
				else return 5;
			}else if(x==1){
				//2nd col
				if(y==1) return 25;
				else if(y==3) return 17;
				else if(y==5) return 9;
				else return 1;
			}else if(x==2){
				//3rd col
				if(y==0) return 30;
				else if(y==2) return 22;
				else if(y==4) return 14;
				else return 6;
			}else if(x==3){
				//4th col
				if(y==1) return 26;
				else if(y==3) return 18;
				else if(y==5) return 10;
				else return 2;
			}else if(x==4){
				//5th col
				if(y==0) return 31;
				else if(y==2) return 23;
				else if(y==4) return 15;
				else return 7;
			}else if(x==5){
				//6th col
				if(y==1) return 27;
				else if(y==3) return 19;
				else if(y==5) return 11;
				else return 3;
			}else if(x==6){
				//7th col
				if(y==0) return 32;
				else if(y==2) return 24;
				else if(y==4) return 16;
				else return 8;
			}else{
				//8th col
				if(y==1) return 28;
				else if(y==3) return 20;
				else if(y==5) return 12;
				else return 4;
			}
		}

}
