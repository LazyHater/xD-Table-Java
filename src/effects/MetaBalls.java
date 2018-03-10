package effects;
import java.awt.Color;
import java.awt.geom.Point2D;

import matrix.Display;
import matrix.TouchScreen;

public class MetaBalls extends Effect {

	class Ball {
		Point2D.Double pos;
		Point2D.Double vel;
		Color color;
		
		Ball(){
		
		}
		public void update(){
			pos.x += vel.x;
			pos.y += vel.y;
		}
	}
	
	Ball[] balls;
	private int strenght = 4; 
	
	public MetaBalls(Display display, int n) {
		super(display);
		
		balls = new Ball[n];
		for(int i = 0; i < n; i++)
			balls[i] = new Ball();
		
		for(Ball b : balls){
			b.pos = new Point2D.Double(Math.random() * display.getWidth(), Math.random() * display.getHeight());
			b.vel = new Point2D.Double(Math.random() - 0.5d, Math.random() - 0.5d);
			b.color = Color.getHSBColor((float) Math.random(), 1.0f, 1.0f);
		}
	}

	@Override
	public void nextFrame(TouchScreen touchScreen) {
		display.background(new Color(0,0,0));
		
		for(Ball b : balls){
			b.update();
			handleBorderCollisions(b);
			showBall(b);
		}
	}

	private void showBall(Ball b) {
		for(int y = 0; y < display.getHeight(); y++)	
			for(int x = 0; x < display.getWidth(); x++){
				Color base = display.getColor(x, y);
				double dist = b.pos.distanceSq(x, y) / 
							 (display.getHeight()*display.getHeight()+display.getWidth()*display.getWidth()); // normalized, now from 0 - 1
				
				dist = 1 - dist;
				for(int i = 0; i < strenght; i++)	
					dist *= dist;
				
				int newRed = (int) (base.getRed()+b.color.getRed()*dist);
				int newGreen = (int) (base.getGreen()+b.color.getGreen()*dist);
				int newBlue = (int) (base.getBlue()+b.color.getBlue()*dist);
				
				if(newRed > 255) newRed = 255;
				if(newGreen > 255) newGreen = 255;
				if(newBlue > 255) newBlue = 255;
				
				display.setColor(x, y, new Color(newRed, newGreen, newBlue));
			}
	}

	private void handleBorderCollisions(Ball b) {
		if( b.pos.x < 0 || b.pos.x > display.getWidth()){
			b.vel.x = -b.vel.x;
			b.update();
		}
		
		if( b.pos.y < 0 || b.pos.y > display.getHeight()){
			b.vel.y = -b.vel.y;
			b.update();
		}
	}

	@Override
	public String toString(){
		return "Meta Balls";
	}
}
