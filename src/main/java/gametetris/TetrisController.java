package main.java.gametetris;

import javafx.scene.shape.Rectangle;

public class TetrisController {

	// Getting the numbers and the MAP from Tetris
	public static final int FALL = Tetris.FALL;
	public static final int TILE = Tetris.TILE;
	public static int WIDTH = Tetris.WIDTH;
	public static int HEIGHT = Tetris.HEIGHT;
	public static int[][] MAP = Tetris.MAP;

	public static void fallRight(FormBlock form) {
		if (form.a.getX() + FALL <= WIDTH - TILE && form.b.getX() + FALL <= WIDTH - TILE
				&& form.c.getX() + FALL <= WIDTH - TILE && form.d.getX() + FALL <= WIDTH - TILE) {
			int falla = MAP[((int) form.a.getX() / TILE) + 1][((int) form.a.getY() / TILE)];
			int fallb = MAP[((int) form.b.getX() / TILE) + 1][((int) form.b.getY() / TILE)];
			int fallc = MAP[((int) form.c.getX() / TILE) + 1][((int) form.c.getY() / TILE)];
			int falld = MAP[((int) form.d.getX() / TILE) + 1][((int) form.d.getY() / TILE)];
			if (falla == 0 && falla == fallb && fallb == fallc && fallc == falld) {
				form.a.setX(form.a.getX() + FALL);
				form.b.setX(form.b.getX() + FALL);
				form.c.setX(form.c.getX() + FALL);
				form.d.setX(form.d.getX() + FALL);
			}
		}
	}

	public static void fallLeft(FormBlock form) {
		if (form.a.getX() - FALL >= 0 && form.b.getX() - FALL >= 0 && form.c.getX() - FALL >= 0
				&& form.d.getX() - FALL >= 0) {
			int falla = MAP[((int) form.a.getX() / TILE) - 1][((int) form.a.getY() / TILE)];
			int fallb = MAP[((int) form.b.getX() / TILE) - 1][((int) form.b.getY() / TILE)];
			int fallc = MAP[((int) form.c.getX() / TILE) - 1][((int) form.c.getY() / TILE)];
			int falld = MAP[((int) form.d.getX() / TILE) - 1][((int) form.d.getY() / TILE)];
			if (falla == 0 && falla == fallb && fallb == fallc && fallc == falld) {
				form.a.setX(form.a.getX() - FALL);
				form.b.setX(form.b.getX() - FALL);
				form.c.setX(form.c.getX() - FALL);
				form.d.setX(form.d.getX() - FALL);
			}
		}
	}

	public static FormBlock makeRect() {
		int block = (int) (Math.random() * 100);
		String name;
		Rectangle a = new Rectangle(TILE-1, TILE-1), b = new Rectangle(TILE-1, TILE-1), c = new Rectangle(TILE-1, TILE-1),
				d = new Rectangle(TILE-1, TILE-1);
		if (block < 15) { 
			a.setX(WIDTH / 2 - TILE);
			b.setX(WIDTH / 2 - TILE);
			b.setY(TILE);
			c.setX(WIDTH / 2);
			c.setY(TILE);
			d.setX(WIDTH / 2 + TILE);
			d.setY(TILE);
			name = "j";
		} else if (block < 30) { 
			a.setX(WIDTH / 2 + TILE);
			b.setX(WIDTH / 2 - TILE);
			b.setY(TILE);
			c.setX(WIDTH / 2);
			c.setY(TILE);
			d.setX(WIDTH / 2 + TILE);
			d.setY(TILE);
			name = "l";
		} else if (block < 45) { 
			a.setX(WIDTH / 2 - TILE);
			b.setX(WIDTH / 2);
			c.setX(WIDTH / 2 - TILE);
			c.setY(TILE);
			d.setX(WIDTH / 2);
			d.setY(TILE);
			name = "o";
		} else if (block < 60) { 
			a.setX(WIDTH / 2 + TILE);
			b.setX(WIDTH / 2);
			c.setX(WIDTH / 2);
			c.setY(TILE);
			d.setX(WIDTH / 2 - TILE);
			d.setY(TILE);
			name = "s";
		} else if (block < 75) { 
			a.setX(WIDTH / 2 - TILE);
			b.setX(WIDTH / 2);
			c.setX(WIDTH / 2);
			c.setY(TILE);
			d.setX(WIDTH / 2 + TILE);
			name = "t";
		} else if (block < 90) { 
			a.setX(WIDTH / 2 + TILE);
			b.setX(WIDTH / 2);
			c.setX(WIDTH / 2 + TILE);
			c.setY(TILE);
			d.setX(WIDTH / 2 + TILE + TILE);
			d.setY(TILE);
			name = "z";
		} else { 
			a.setX(WIDTH / 2 - TILE - TILE);
			b.setX(WIDTH / 2 - TILE);
			c.setX(WIDTH / 2);
			d.setX(WIDTH / 2 + TILE);
			name = "i";
		}
		return new FormBlock(a, b, c, d, name);
	}
}