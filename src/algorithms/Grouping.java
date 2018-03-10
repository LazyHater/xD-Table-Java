package algorithms;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Grouping {
	private static boolean getState(int[] data, int x, int y) {
		if (x > 9 || x < 0)
			return false;
		if (y > 9 || y < 0)
			return false;

		if (data[y * 10 + x] != 0)
			return true;
		else
			return false;
	}

	private static ArrayList<Point> getNeightbours(int[] data, int x, int y) {
		ArrayList<Point> tmp = new ArrayList<Point>();
		if (getState(data, x - 1, y))
			tmp.add(new Point(x - 1, y)); // left
		if (getState(data, x - 1, y - 1))
			tmp.add(new Point(x - 1, y - 1)); // up - left
		if (getState(data, x, y - 1))
			tmp.add(new Point(x, y - 1)); // up
		if (getState(data, x + 1, y - 1))
			tmp.add(new Point(x + 1, y - 1)); // up - right
		if (getState(data, x + 1, y))
			tmp.add(new Point(x + 1, y)); // right
		if (getState(data, x + 1, y + 1))
			tmp.add(new Point(x + 1, y + 1)); // down - right
		if (getState(data, x, y + 1))
			tmp.add(new Point(x, y + 1)); // down
		if (getState(data, x - 1, y + 1))
			tmp.add(new Point(x - 1, y + 1)); // down - left
		return tmp;
	}
	
	public static ArrayList<Centroid> getCentroids(int[] data) {
		int[] data2 = data.clone();
		ForestOfDisjointSets fods = new ForestOfDisjointSets(data2.length);

		//create groups in fods
		for (int i = 0; i < data2.length; i++) {
			if (getState(data, i % 10, i / 10))
				for (Point p : getNeightbours(data2, i % 10, i / 10)) {
					fods.Union(i, p.y * 10 + p.x);
				}
		}

		ArrayList<Centroid> centroids = new ArrayList<Centroid>();
		for (ArrayList<Integer> list : fods.getGroups()) {
			ArrayList<Point> points = new ArrayList<Point>();
			for (int idx : list) {
				if (getState(data, idx % 10, idx / 10))
					points.add(new Point(idx % 10, idx / 10));
			}

			if (!points.isEmpty()) {
				Centroid c = new Centroid();
				c.setPoints(points);
				centroids.add(c);
			}
		}
		
		for(int i = 0; i < centroids.size(); i++){
			centroids.get(i).updatePosition();
			centroids.get(i).setColor(Color.getHSBColor(((float)i)*(1.0f / centroids.size()), 1,1));
		}
		
		return centroids;
	}

}
