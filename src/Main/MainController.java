package Main;

import javax.swing.JFrame;

public class MainController {

	public static boolean loadGame = false;

	public static void main(String[] args) {
		JFrame window = new JFrame("Valorant Mob Shooter");
		window.setContentPane(new GameBoard());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}

}
