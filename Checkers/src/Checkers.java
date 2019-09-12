import gameLogic.GameState;
import gameLogic.GameStateManager;

import java.util.Scanner;


public class Checkers {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Would you like to be Black(1) or Red?(0)");
		int moveFirstOrSecond = keyboard.nextInt();
		
		Game game = new Game(moveFirstOrSecond);
		game.startGame();
		
	}
}
