package Zombies;

import Accessories.Enemy;
import Accessories.Animation;
import Tiles.TileAccessories;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Thrash extends Enemy {
	private BufferedImage[] sprites;
	private double jumpFrequency;

	public Thrash(TileAccessories tm) {
		super(tm);
		rank = 3;
		enemyType = 4;
		moveSpeed = 2;
		maxSpeed = 2;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;

		health = maxHealth = 70;
		// health = maxHealth = 2;
		damage = 2;
		bounce = true;
		jumping = false;

		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/thrash.png"));

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
		animation.setDelay(100);

		right = true;
		facingRight = false;
	}

	private void getNextPosition() {
		if (health >= 1) {
			maxSpeed = (maxHealth / health) * 1.4;
			jumpFrequency = maxSpeed;
		}
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		if (falling) {
			dy += fallSpeed;
		}
	}

	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if (cue == 5) {
			// System.out.println("CUED");
			Random rand = new Random();
			int jumpQuery = rand.nextInt(100);
			if (jumpQuery < jumpFrequency) {
				jumping = true;
			} else
				jumping = false;
		}
		// if (jumpCue == 5) jumping = true;
		else
			jumping = false;
		if (jumping == true) {
			dy = jumpStart;
			// System.out.println("JUMP");
		}

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = true;
			dx = -dx;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = false;
			dx = -dx;
		}

		// if(!bottomLeft) {
		// left = false;
		// right = true;
		// facingRight = false;
		// }
		// if(!bottomRight) {
		// left = true;
		// right = false;
		// facingRight = true;
		// }

		animation.update();
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
}