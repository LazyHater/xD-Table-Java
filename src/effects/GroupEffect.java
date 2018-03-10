package effects;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import algorithms.Centroid;
import matrix.Display;
import matrix.TouchScreen;

public class GroupEffect extends Effect {

	public GroupEffect(Display display) {
		super(display);
	}
	
	@Override
	public void nextFrame(TouchScreen touchScreen) {
		ArrayList<Centroid> centroids = touchScreen.getCentroids();
		display.background(Color.BLACK);
		
		for(Centroid cen : centroids){
			for(Point p : cen.getPoints()){
				display.setColor(p.x, p.y, cen.getColor().darker().darker());
			}
			display.setColor((int)cen.getPos().x, (int)cen.getPos().y, cen.getColor());
		}
	}
	
	@Override
	public String toString(){
		return "Group Effect";
	}
}
