package gameLogic;

import java.util.ArrayList;

public class Node {
	private double value;
	private ArrayList<Move> move;
	
	//defautl constr
	public Node (){
		this.value = 0;
		this.move = null;
	}
	
	//parametrized constr
	public Node (int value, ArrayList<Move> move){
		this.value = value;
		this.move = move;
	}
	
	//setter methods
	public void setMove (ArrayList<Move> move){
		this.move = move;
	}
	
	public void setValue(double value){
		this.value=value;
	}
	
	//getter methods
	public double getValue(){
		return value;
	}
	
	public ArrayList<Move> getMove(){
		return move;
	}
}
