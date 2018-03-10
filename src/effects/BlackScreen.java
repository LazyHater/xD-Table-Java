package effects;
import java.awt.Color;

import matrix.Display;
import matrix.TouchScreen;

public class BlackScreen extends Effect {	
	public BlackScreen(Display display) {
		super(display);
	}
	@Override
	public void nextFrame(TouchScreen touchScreen) {
		display.background(new Color(0,0,0));
	}
	
	@Override
	public String toString(){
		return "Black Screen";
	}
}
