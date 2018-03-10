package effects;
import java.awt.Color;

import matrix.Display;
import matrix.TouchScreen;

public class Rainbow extends Effect {
	private float offset = 0;

	public Rainbow(Display display) {
		super(display);
	}

	public void nextFrame(TouchScreen touchScreen) {
		for (int y = 0; y < display.getHeight(); y++)
			for (int x = 0; x < display.getWidth(); x++) {
				display.setColor(x, y, getHsvColorFor(x, y, touchScreen.getStateAt(x, y) ? true : false));
			}
		offset += 1;
	}

	private Color getHsvColorFor(int x, int y, boolean shifted) {
		float hue = (offset + x + y * display.getHeight()) / 360.0f;
		Color color = Color.getHSBColor(shifted ? hue : hue + 0.5f, 1.0f, 1.0f);
		return color;
	}

	@Override
	public String toString() {
		return "Rainbow";
	}

}
