package Levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Tiles.Background;

public class InstructionClass extends GameController {

	private Background bg;
	private String options = "Press B to Back";
	private Color titleColor;
	private Font titleFont;
	private Font font, font1;

	public InstructionClass(GameControlManager gsm) {

		this.gsm = gsm;

		try {

			bg = new Background("/Backgrounds/background introduct.png", 1);
			bg.setVector(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font(
					"VALORANT",
					Font.BOLD,
					15);

			font = new Font("VALORANT", Font.PLAIN, 10);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {
	}

	public void update() {
		bg.update();
	}

	public void draw(Graphics2D g) {

		bg.draw(g);

		g.setColor(Color.RED);
		g.setFont(titleFont);

		g.drawString("How To Play", 80, 20);
		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString("=> Use Arrow Key To Move or Jump", 10, 35);
		g.drawString("=> Hold Up to Float Around", 10, 45);
		g.drawString("=> Or Use 'D' To Move Forward", 10, 55);
		g.drawString("=> Or Use 'A' To Move Backward", 10, 65);

		g.drawString("=> Or Use 'W' To Jump ", 10, 75);
		g.drawString("=> Use 'Space' To use Knives", 10, 85);
		g.drawString("=> Use 'M' To Mute Sound", 10, 95);

		font1 = new Font("Arial", Font.BOLD, 12);
		g.setColor(Color.RED);
		g.drawString(options, 10, 117);
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_B) {
			gsm.setState(GameControlManager.MENUSTATE);
		}

	}

	public void keyReleased(int k) {
	}

}
