package effects;
import matrix.Display;
import matrix.TouchScreen;

public abstract class Effect {
	Display display;

	public abstract void nextFrame(TouchScreen touchScreen);
	
	public Effect(Display display) {
		this.display = display;
	}

}
