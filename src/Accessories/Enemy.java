package Accessories;

import Tiles.TileAccessories;

public class Enemy extends MapObject {
	protected int health, maxHealth, damage, rank, enemyType;
	protected boolean dead, bounce, dropsBounce;

	protected boolean flinching;
	protected long flinchTimer;

	public Enemy(TileAccessories tm) {
		super(tm);

	}

	public boolean isDead() {
		return dead;
	}

	public void kill() {
		dead = true;
	}

	public int getDamage() {
		return damage;
	}

	public boolean getBounce() {
		return bounce;
	}

	public int getRank() {
		return rank;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public void hit(int damage) {
		if (dead || flinching)
			return;
		health -= damage;
		if (health <= 0) {
			dead = true;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void update() {
	}

}
