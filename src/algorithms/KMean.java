package algorithms;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class KMean {
	Centroid[] centroids;

	private ArrayList<Point> points;
	int k;

	KMean(ArrayList<Point> points, int k, int numbOfIterations) {
		this.points = points;
		this.k = k;

		if (k <= 0)
			System.err.println("k <= 0!!!");

		centroids = new Centroid[k];

		/*
		 * for (int i = 1; i < k + 1; i++) { int n = (100 * (i)) / (k + 1); //
		 * rethink for something smarter centroids[i - 1] = new Centroid(n % 10,
		 * n / 10, Color.getHSBColor((float)i/k, 1.0f, 1.0f)); }
		 */
		switch (k) {
		case 1:
			centroids[0] = new Centroid(2, 2, Color.RED);
			break;
		case 2:
			centroids[0] = new Centroid(2, 2, Color.RED);
			centroids[1] = new Centroid(7, 2, Color.BLUE);
			break;

		case 3:
			centroids[0] = new Centroid(2, 2, Color.RED);
			centroids[1] = new Centroid(7, 2, Color.BLUE);
			centroids[2] = new Centroid(7, 7, Color.GREEN);
			break;

		case 4:
			centroids[0] = new Centroid(2, 2, Color.RED);
			centroids[1] = new Centroid(7, 2, Color.BLUE);
			centroids[2] = new Centroid(7, 7, Color.GREEN);
			centroids[3] = new Centroid(2, 7, Color.WHITE);
			break;
}

		for (int i = 0; i < numbOfIterations; i++)
			iterate();

	}

	private void iterate() {
		for (Centroid cen : centroids) {
			cen.getPoints().clear();
		}

		for (Point p : points) {
			Centroid bestCen = null;
			double bestDist = Double.MAX_VALUE;
			for (Centroid cen : centroids) {
				double dist = cen.getPos().distanceSq(p);
				if (dist < bestDist) {
					bestCen = cen;
					bestDist = dist;
				}
			}
			bestCen.getPoints().add(p);
		}

		for (Centroid cen : centroids) {
			cen.updatePosition();
		}
	}

	public double getScore() {
		double score = 0;
		for (Centroid cen : centroids) {
			if (cen.getPoints().isEmpty())
				score += 10000;
			for (Point p : cen.getPoints()) {
				score += cen.getPos().distanceSq(p);
			}
			for (Centroid cen2 : centroids) {
				if(cen == cen2) continue;
				if((((int)cen.getPos().x)==((int)cen2.getPos().x))&&(((int)cen.getPos().y)==((int)cen2.getPos().y)))
					score += 100000;
			}
			
		}
		return score;
	}

	public int getK() {
		return k;
	}

	public Centroid[] getCentroids() {
		return centroids;
	}
}
