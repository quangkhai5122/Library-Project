package gametetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FormBlock {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	Color color;
	private String name;
	public int form = 1;

	public FormBlock(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public FormBlock(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.name = name;

		switch (name) {
		case "j":
			color = Color.HOTPINK;
			break;
		case "l":
			color = Color.CADETBLUE;
			break;
		case "o":
			color = Color.FORESTGREEN;
			break;
		case "s":
			color = Color.INDIANRED;
			break;
		case "t":
			color = Color.DARKGOLDENROD;
			break;
		case "z":
			color = Color.SLATEGRAY;
			break;
		case "i":
			color = Color.SANDYBROWN;
			break;

		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}


	public String getName() {
		return name;
	}


	public void changeForm() {
		if (form != 4) {
			form++;
		} else {
			form = 1;
		}
	}
}