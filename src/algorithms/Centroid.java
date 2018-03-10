package algorithms;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Centroid {
		private Point2D.Double pos;
		private Color color;
		private ArrayList<Point> points = new ArrayList<Point>();

		public Centroid(int x, int y, Color c) {			
			setPos(new Point2D.Double(x, y));
			setColor(c);
		}
		
		public Centroid(int x, int y) {			
			setPos(new Point2D.Double(x, y));
			setColor(Color.getHSBColor((float)Math.random(), 1, 1));
		}
		
		public Centroid() {			
			setPos(new Point2D.Double(0, 0));
			setColor(Color.getHSBColor((float)Math.random(), 1, 1));
		}


		public void updatePosition() {
			if (getPoints().isEmpty())
				return;

			int sumX = 0;
			int sumY = 0;
			for (Point p : getPoints()) {
				sumX += p.x;
				sumY += p.y;
			}
			sumX /= getPoints().size();
			sumY /= getPoints().size();
			getPos().setLocation(sumX, sumY);
		}
		
		public Color getColor(){
			return color;
		}

		public String toString() {
			return getPos().toString();
		}

		public ArrayList<Point> getPoints() {
			return points;
		}

		public void setPoints(ArrayList<Point> points) {
			this.points = points;
		}

		public Point2D.Double getPos() {
			return pos;
		}

		public void setPos(Point2D.Double pos) {
			this.pos = pos;
		}

		public void setColor(Color color) {
			this.color = color;
		}
	}