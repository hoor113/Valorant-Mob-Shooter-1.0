package Zombies;

import Accessories.Enemy;
import Accessories.Animation;
import Tiles.TileAccessories;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Bird extends Enemy {

	private BufferedImage[] sprites;
	private int webx, weby;

	public Bird(TileAccessories tm, int webx, int weby) {

		super(tm);

		this.webx = webx;
		this.weby = weby;
		moveSpeed = 1;
		maxSpeed = 2;
		enemyType = 2;
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;

		health = maxHealth = 2;
		damage = 1;

		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/skye_bats.png"));

			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(50);

		down = true;
	}

	private void getNextPosition() {
		if (up) {
			dy -= moveSpeed;
			if (dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		} else if (down) {
			dy += moveSpeed;
			if (dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
	}

	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		if (up && dy == 0 || y <= weby) {
			up = false;
			down = true;
		}
		// else if(down && (dy == 0 || y >= tileMap.getHeight() - height)) {
		else if (down && (dy == 0 || y >= tileMap.getHeight() - height)) {
			down = false;
			up = true;
			if (y >= tileMap.getHeight() - height) {
				dy = -dy;
			}
		}
		animation.update();
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		super.draw(g);
		g.setColor(Color.BLACK);
		g.setColor(Color.BLACK);
	}

}
