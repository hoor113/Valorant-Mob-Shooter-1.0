package Levels;

import Tiles.TileAccessories;
import Tiles.Background;
import Zombies.Bird;
import Zombies.Wingman;
import Zombies.Dog;
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

public class Level3Class extends GameController {

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

	public Level3Class(GameControlManager gsm) {
		this.gsm = gsm;
		init();

	}

	public void init() {

		levelStart = true;
		tileMap = new TileAccessories(30);
		tileMap.loadTiles("/Tilesets/breeze.png");
		tileMap.loadMap("/Maps/level3-1.map");
		tileMap.setPosition(0, 0);

		bg = new Background("/Backgrounds/breeze_img.jpg", 0);

		player = new Player(tileMap);
		player.setPosition(10, 80);
		player.setLevel(3);

		try {
			populateEnemies();
		} catch (IOException ex) {
			Logger.getLogger(Level3Class.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Level3Class.class.getName()).log(Level.SEVERE, null, ex);
		}

		explosions = new ArrayList<Explosion>();
		objects = new ArrayList<Object>();
		texts = new ArrayList<WritingClass>();

		hud = new ScoreBoard(player);

		bgMusic = new AudioPlayer("/Music/breeze.mp3");
		item = new AudioPlayer("/SFX/pick_item.mp3");
		if (!player.getMute()) {
			bgMusic.loop();
		}

	}

	private void populateEnemies() throws IOException, FileNotFoundException, ClassNotFoundException {

		enemies = new ArrayList<Enemy>();

		Dog s;
		Point[] points = new Point[] {
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				// new Point(rand.nextInt(200)+1500, 180),
				// new Point(rand.nextInt(200)+1500, 180),
				// new Point(rand.nextInt(200)+1500, 180),
				// new Point(rand.nextInt(200)+1500, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				// new Point(rand.nextInt(150)+1760, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(200) + 1500, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),
				new Point(rand.nextInt(150) + 1760, 180),

		};
		for (int i = 0; i < points.length; i++) {
			s = new Dog(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Bird spider;
		Point[] spiderpoints = new Point[] {
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(1350) + 160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(1350)+160, rand.nextInt(75)),

				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75)),
				new Point(rand.nextInt(2500) + 160, rand.nextInt(75))

				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
				// new Point(rand.nextInt(2500)+160, rand.nextInt(75))

		};
		for (int i = 0; i < spiderpoints.length; i++) {
			spider = new Bird(tileMap, spiderpoints[i].x, 00);
			spider.setPosition(spiderpoints[i].x, spiderpoints[i].y);
			enemies.add(spider);
		}
		Wingman boss;
		Point[] bosspoints = new Point[] {
				new Point(rand.nextInt(150) + 2150, 120),
				new Point(rand.nextInt(150) + 2150, 120),
				new Point(rand.nextInt(150) + 2150, 120)
		};
		for (int i = 0; i < bosspoints.length; i++) {
			boss = new Wingman(tileMap);
			boss.setPosition(bosspoints[i].x, bosspoints[i].y);
			enemies.add(boss);
		}
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
		if (player.getx() <= 0)

		{
			player.setPosition(0, 80);
		}

		if (player.getx() > tileMap.getWidth() - player.getWidth()) {
			gsm.setState(GameControlManager.LEVEL4STATE);
			bgMusic.stop();
		}

		if (player.gety() >= tileMap.getHeight() - player.getHeight()) {
			player.kill();
		}

		if (player.gety() < 0) {
			player.setPosition(player.getx(), 0);
		}
		if (player.isDead()) {
			player.setPosition(0, 80);
			deathScreen = true;
			deathScreenTimer = System.nanoTime();
			player.loseLife();
			player.setHealth(5);
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
				explosions.add(
						new Explosion(e.getx(), e.gety()));
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
		String s = "Level 3: Breeze";
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
