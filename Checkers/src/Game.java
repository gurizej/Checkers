import javax.swing.JPanel;

import behavior.ComputerWithAI;
import behavior.GuriAI;
import behavior.HumanWithConsole;
import behavior.MoveStrategy;
import gameLogic.GameState;
import gameLogic.GameStateManager;
import gui.GameGUI;


public class Game {
	public static final int RED = 0;
	public static final int BLACK = 1;
	JPanel gameBoard;
	boolean gameIsOver = false;
	GameGUI gui;
	GameStateManager gsm;
	MoveStrategy pBlack, pRed, currentPlayer;

	public Game(int moveFirstOrSecond) {
		gui = new GameGUI();
		gsm = new GameStateManager(new GameState());
		gsm.setNewGame();
//		gsm.getMovesTest();
//		gsm.getBoardValueTest();

		//we move first so we are black
		//and the AI is red
		if(moveFirstOrSecond==1){
			pBlack = new HumanWithConsole(); //make this us.
//			pBlack = new GuriAI();
			pBlack.setColor(BLACK);
//			pRed = new ComputerWithAI();
			pRed = new GuriAI();
			pRed.setColor(RED);
		}
		else{
			//we move second so we are red.
			//and the AI is black
			pRed = new HumanWithConsole(); //make this us.
//			pRed = new GuriAI ();
			pRed.setColor(RED);
//			pBlack = new ComputerWithAI();
			pBlack = new GuriAI();
			pBlack.setColor(BLACK);
		}
		currentPlayer = pBlack;
	}

	public void startGame() {
		while(!gameIsOver) {
			if (currentPlayer.getColor() == 1)
				System.out.println("Black's turn");
			else
				System.out.println("Red's turn");
			currentPlayer.makeMove(gsm);

			gameIsOver = gsm.checkWin();

			gui.render(gsm.getGameState());

			if (currentPlayer == pBlack) currentPlayer = pRed;
			else currentPlayer = pBlack;
		}
	}

}
