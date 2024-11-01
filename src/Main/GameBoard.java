package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.swing.JPanel;

import Levels.GameControlManager;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 320, HEIGHT = 240, SCALE = 3;

	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	private BufferedImage image;
	private Graphics2D g;

	private GameControlManager gsm;

	public GameBoard() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();

	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameControlManager();
	}

	public void run() {

		init();

		long start;
		long elapsed;
		long wait;

		while (running) {
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0) {

				wait = 5;
			}
			try {
				thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void update() {
		gsm.update();
	}

	private void draw() {
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

	public void keyTyped(KeyEvent key) {

	}
}
