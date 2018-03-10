package algorithms;
import java.awt.Point;
import java.util.ArrayList;

import matrix.TouchScreen;

public class KMeanAlghoritm {
	private KMean bestKMean = null;
	
	private KMean[] kMeans;
	public KMeanAlghoritm(TouchScreen touchScreen, int maxK, int numbOfIterations) {	
		
		ArrayList<Point> points = new ArrayList<Point>();

		for (int y = 0; y < 10; y++)
			for (int x = 0; x < 10; x++) {
				if (touchScreen.getStateAt(x, y))
					points.add(new Point(x, y));
			}
		
		kMeans = new KMean[maxK];
		for(int i = 0; i < maxK; i++) {
			kMeans[i] = new KMean(points, i + 1, numbOfIterations);
		}
		
		double bestScore = Double.MAX_VALUE;
		for (KMean kMean : kMeans) {
			double score = kMean.getScore();
			System.out.println("KMean "+kMean.getK()+", Score: "+score);
			if(score < bestScore){
				bestScore = score;
				bestKMean = kMean;
			}
		}
	}
	
	public KMean getBest(){
		return bestKMean;
	}

}
