package Levels;

import Tiles.TileAccessories;
import Tiles.Background;
import Zombies.Bird;
import Zombies.Wingman;
import Zombies.Dog;
import Zombies.Thrash;
import Accessories.Player;
import Accessories.Enemy;
import Accessories.ScoreBoard;
import Accessories.WritingClass;
import Accessories.Explosion;
import Main.GameBoard;
import Accessories.Object;
import Bonuces.ExtraHealth;
import Music.AudioPlayer;
import Bonuces.ExtraKnives;
import static Levels.GameControlManager.score;
import static Levels.GameControlManager.ACTIVE;
import static Levels.GameControlManager.PAUSED;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Level4Class extends GameController {

	Graphics2D g;
	private AudioPlayer bgMusic;
	private AudioPlayer item;
	private TileAccessories tileMap;
	private Background bg;
	Random rand = new Random();

	private static Player player;
	private boolean deathScreen, gameOver, levelStart, ammoOrheart;
	private long deathScreenTimer, levelStartTimerDiff, levelStartTimer = 0;
	private long deathScreenDelay = 2000;
	private long jumpingTime = 0;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Object> objects;
	private ArrayList<WritingClass> texts;

	private ScoreBoard hud;

	public Level4Class(GameControlManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		levelStart = true;
		tileMap = new TileAccessories(30);
		tileMap.loadTiles("/Tilesets/sunsets.png");
		tileMap.loadMap("/Maps/level4-1.map");
		tileMap.setPosition(0, 0);

		bg = new Background("/Backgrounds/sunset_img.jpg", 0);

		player = new Player(tileMap);
		// player.setPosition(2000, 30);
		player.setPosition(10, 80);
		player.setLevel(4);
		try {
			populateEnemies();
		} catch (IOException ex) {
			Logger.getLogger(Level4Class.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Level4Class.class.getName()).log(Level.SEVERE, null, ex);
		}

		explosions = new ArrayList<Explosion>();
		objects = new ArrayList<Object>();
		texts = new ArrayList<WritingClass>();

		hud = new ScoreBoard(player);

		bgMusic = new AudioPlayer("/Music/sunset.mp3");
		item = new AudioPlayer("/SFX/pick_item.mp3");
		if (!player.getMute()) {
			bgMusic.loop();
		}

	}

	private void populateEnemies() throws IOException, FileNotFoundException, ClassNotFoundException {

		enemies = new ArrayList<Enemy>();
		Dog s;
		Point[] points = new Point[] {
				new Point(890, rand.nextInt(50)),
				new Point(920, rand.nextInt(50)),
				new Point(950, rand.nextInt(50)),
				new Point(1320 / 2, 120),
				new Point((1320 + rand.nextInt(100) / 2), 120),
				new Point((1320 + rand.nextInt(10)) / 2, 10),
		};
		for (int i = 0; i < points.length; i++) {
			s = new Dog(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Bird bird;
		Point[] birdpoints = new Point[] {
				new Point(690, rand.nextInt(50)),
				new Point(720, rand.nextInt(50)),
				new Point(750, rand.nextInt(50)),
				new Point(1690, rand.nextInt(50)),
				new Point(1720, rand.nextInt(50)),
				new Point(1750, rand.nextInt(50)),
				new Point(1790, 20),
				new Point(rand.nextInt(30) + 1790, 20),
				new Point(rand.nextInt(30) + 1790, 20),
		};
		for (int i = 0; i < birdpoints.length; i++) {
			bird = new Bird(tileMap, birdpoints[i].x, 00);
			bird.setPosition(birdpoints[i].x, birdpoints[i].y);
			enemies.add(bird);
		}
		Wingman wingman;
		Point[] wingxpoints = new Point[] {
				new Point((int) ((1560 + rand.nextInt(100)) / 1.5), 120),
				new Point((int) ((1560 + rand.nextInt(100)) / 1.5), 120),
				new Point((int) ((1560 + rand.nextInt(100)) / 1.5), 120),

		};
		for (int i = 0; i < wingxpoints.length; i++) {
			wingman = new Wingman(tileMap);
			wingman.setPosition(wingxpoints[i].x, wingxpoints[i].y);
			enemies.add(wingman);
		}
		Point[] bossPoint = new Point[] {
				new Point(3360, 80),
		};
		Thrash thrash = new Thrash(tileMap);
		thrash.setPosition(bossPoint[0].x, bossPoint[0].y);
		enemies.add(thrash);

	}

	public void setDeathScreen(boolean b) {
		deathScreen = b;
	}

	public void update() {
		if (levelStartTimer == 0 && levelStart) {
			levelStartTimer = System.nanoTime();
		} else {
			levelStartTimerDiff = (System.nanoTime() - levelStartTimer) / 1000000;
			if (levelStartTimerDiff > 5000) {
				levelStartTimerDiff = 0;
				levelStart = false;
			}
		}
		if (player.getx() <= 0) {
			player.setPosition(0, 80);
		}

		if (player.getx() > tileMap.getWidth() - player.getWidth()) {
			gsm.setState(GameControlManager.WINSTATE);
			bgMusic.stop();
		}

		if (player.gety() >= tileMap.getHeight() - player.getHeight()) {
			player.kill();
		}

		if (player.gety() < 0) {
			player.setPosition(player.getx(), 0);
		}
		if (player.isDead()) {
			player.setPosition(10, 80);
			// player.setPosition(3400, 30);
			deathScreen = true;
			deathScreenTimer = System.nanoTime();
			player.loseLife();
			player.setHealth(5);
			score -= 350;
		}
		if (player.getLives() == 0) {
			gameOver = true;
		}

		if (gsm.motion == ACTIVE) {
			player.update();
		}
		tileMap.setPosition(GameBoard.WIDTH / 2 - player.getx(),
				GameBoard.HEIGHT / 2 - player.gety());

		bg.setPosition(tileMap.getx(), tileMap.gety());

		player.checkAttack(enemies);
		player.checkObjects(objects);

		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (gsm.motion == ACTIVE) {
				e.update();
			}
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				if (e.getEnemyType() == 1)
					score += 20;
				else if (e.getEnemyType() == 2)
					score += 50;
				else if (e.getEnemyType() == 3)
					score += 100;
				else if (e.getEnemyType() == 4) {
					score += 1000;
					tileMap.changeMapIngame(0, 7, 121);
				}
				explosions.add(new Explosion(e.getx(), e.gety()));
				int r = rand.nextInt(30);
				if (r < 10) {
					createExtraAmmo(e.getx(), e.gety());
					ammoOrheart = false;
				} else if (r > 10 && r < 21) {
					createExtraHeart(e.getx(), e.gety());
					ammoOrheart = true;
				}
			}
		}

		for (int i = 0; i < objects.size(); i++) {
			Object o = objects.get(i);
			if (gsm.motion == ACTIVE) {
				o.update();
			}
			if (o.isDead()) {
				objects.remove(i);
				i--;
				if (ammoOrheart) {
					if (player.getHealth() < player.getMaxHealth()) {
						player.increaseHealth(1);
						texts.add(new WritingClass(GameBoard.WIDTH / 2, player.gety(), 3000, "+20% Health!"));
						item.play();
					} else {
						texts.add(new WritingClass(GameBoard.WIDTH / 2, player.gety(), 3000, "Already Max Health!"));
					}
				} else if (!ammoOrheart) {
					if (player.getKnives() < player.getMaxKnives()) {
						player.increaseAmmo(5);
						texts.add(new WritingClass(GameBoard.WIDTH / 2, player.gety(), 3000, "+5 Knives!"));
						item.play();
					} else {
						texts.add(new WritingClass(GameBoard.WIDTH / 2, player.gety(), 3000, "Already Max Knives!"));
					}
				}
			}
		}

		for (int i = 0; i < texts.size(); i++) {
			if (texts.get(i).update()) {
				texts.remove(i);
				i--;
			}
		}

		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

	}

	public static Player getPlayer() {
		return player;
	}

	private void createExtraHeart(int x, int y) {

		ExtraHealth e = new ExtraHealth(tileMap);
		e.setPosition(x, y);
		objects.add(e);

	}

	private void createExtraAmmo(int x, int y) {

		ExtraKnives ea = new ExtraKnives(tileMap);
		ea.setPosition(x, y);
		objects.add(ea);

	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).draw(g);
		}

		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
					(int) tileMap.getx(), (int) tileMap.gety());
			explosions.get(i).draw(g);
		}

		hud.draw(g);

		g.setFont((new Font("VALORANT", Font.PLAIN, 18)));
		String s = "Level 4: Sunset";
		int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int alpha = (int) (255 * Math.sin(3.14 * levelStartTimerDiff / 5000));
		if (alpha > 255)
			alpha = 255;
		g.setColor(new Color(255, 255, 255, alpha));
		int ypos = (int) levelStartTimerDiff / 5;
		if (ypos <= GameBoard.HEIGHT / 2)
			g.drawString(s, GameBoard.WIDTH / 2 - length / 2, ypos);
		else {
			g.drawString(s, GameBoard.WIDTH / 2 - length / 2, GameBoard.HEIGHT / 2);
		}

		for (int i = 0; i < texts.size(); i++) {
			texts.get(i).draw(g);
		}

		if (deathScreen == true) {
			long currentTime = System.nanoTime();
			long elapsed = (currentTime - deathScreenTimer) / 1000000;
			if (elapsed >= deathScreenDelay) {
				levelStart = true;
				levelStartTimer = 0;
			}
			if (elapsed < deathScreenDelay) {
				// g.setColor(Color.BLACK);
				// g.fillRect(0,0,GameBoard.WIDTH,GameBoard.HEIGHT);
				// Font font = new Font("VALORANT", Font.PLAIN, 14);
				// g.setFont(font);
				// g.setColor(Color.RED);
				if (!gameOver) {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
					Font font = new Font("VALORANT", Font.PLAIN, 14);
					g.setFont(font);
					g.setColor(Color.RED);
					g.drawString("You Died!", GameBoard.WIDTH / 2 - 30, GameBoard.HEIGHT / 2);
					player.setHealth(player.getMaxHealth());
					player.setKnives(30);

				} else if (gameOver) {
					Background b = new Background("/Backgrounds/defeat.jpg", 0);
					b.draw(g);
					// g.drawString("GAME OVER", GameBoard.WIDTH/2 - 40, GameBoard.HEIGHT/2);
					bgMusic.stop();
				}

			}

			else {
				deathScreen = false;
				if (gameOver) {
					gsm.setState(GameControlManager.MENUSTATE);
					gameOver = false;
				}
			}
		}
		if (gsm.motion == PAUSED) {
			Font font = new Font("VALORANT", Font.CENTER_BASELINE, 28);
			g.setFont(font);
			g.setColor(new Color(0, 0, 0));
			String text = "PAUSED";
			int l = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
			int x = GameBoard.WIDTH / 2 - l / 2;
			int y = GameBoard.HEIGHT / 2;
			g.drawString(text, x, y);
		}

	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_A)
			player.setLeft(true);
		if (k == KeyEvent.VK_D)
			player.setRight(true);
		if (k == KeyEvent.VK_UP) {
			player.setJumping(true);
			if (jumpingTime == 0) {
				jumpingTime = System.currentTimeMillis();
			}
			long floatingTime = System.currentTimeMillis();
			if (floatingTime - jumpingTime > 500) {
				player.setGliding(true);
			}
		}
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_W)
			player.setJumping(true);
		if (k == KeyEvent.VK_R)
			player.setScratching();
		if (k == KeyEvent.VK_SPACE)
			player.setFiring();
		if (k == KeyEvent.VK_M && !player.getMute()) {
			player.setMute(true);
			bgMusic.stop();
		} else if (k == KeyEvent.VK_M && player.getMute()) {
			player.setMute(false);
			bgMusic.loop();
		}
		if (k == KeyEvent.VK_ESCAPE) {
			if (gsm.motion == ACTIVE) {
				gsm.setMotion(PAUSED);
			} else if (gsm.motion == PAUSED) {
				gsm.setMotion(ACTIVE);
			}
		}
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_UP) {
			player.setGliding(false);
			player.setJumping(false);
		}
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_W)
			player.setJumping(false);
		if (k == KeyEvent.VK_A)
			player.setLeft(false);
		if (k == KeyEvent.VK_D)
			player.setRight(false);
	}

}
