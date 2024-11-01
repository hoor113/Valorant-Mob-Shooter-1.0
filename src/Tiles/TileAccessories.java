package Tiles;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import Main.GameBoard;

public class TileAccessories {

	private static double x;
	private static double y;

	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	private BufferedImage tileset;
	private int numTilesAcross;
	private TileController[][] tiles;

	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileAccessories(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GameBoard.HEIGHT / tileSize + 2;
		numColsToDraw = GameBoard.WIDTH / tileSize + 2;
	}

	public void loadTiles(String s) {

		try {

			tileset = ImageIO.read(
					getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new TileController[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
						col * tileSize,
						0,
						tileSize,
						tileSize);
				tiles[0][col] = new TileController(subimage, TileController.NORMAL);
				subimage = tileset.getSubimage(
						col * tileSize,
						tileSize,
						tileSize,
						tileSize);
				tiles[1][col] = new TileController(subimage, TileController.BLOCKED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String s) {
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			xmin = GameBoard.WIDTH - width;
			xmax = 0;
			ymin = GameBoard.HEIGHT - height;
			ymax = 0;

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public int getCue(int row, int col) {
		int rc = map[row][col];
		return rc;
	}

	public void setPosition(double x, double y) {

		this.x += (x - this.x);
		this.y += (y - this.y);

		fixBounds();

		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;

	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}

	public void draw(Graphics2D g) {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows)
				break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols)
					break;

				if (map[row][col] == 0)
					continue;

				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				g.drawImage(
						tiles[r][c].getImage(),
						(int) x + col * tileSize,
						(int) y + row * tileSize,
						null);
			}
		}
	}

	public void changeMapIngame(int changeTo, int row, int column) {
		map[row][column] = changeTo;
	}
}