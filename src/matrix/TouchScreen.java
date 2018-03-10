package matrix;
import java.awt.Color;
import java.util.ArrayList;

import algorithms.Centroid;
import algorithms.Grouping;
import algorithms.KMeanAlghoritm;

public class TouchScreen implements IShowable {
	private int[] pixels;

	private int width;
	private int height;

	public TouchScreen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public int getValueAt(int x, int y) {
		return pixels[x + height * y];
	}

	public void setValueAt(int x, int y, int val) {
		pixels[x + height * y] = val;
	}

	public boolean getStateAt(int x, int y) {
		return pixels[x + height * y] > 0 ? true : false;
	}

	public void setStateAt(int x, int y, boolean state) {
		pixels[x + height * y] = state ? 255 : 0;
	}

	public void update(int[] data) {
		if (data == null) {
			System.err.println("Data is null in TouchScreen update.");
			return;
		}
		if (data.length != pixels.length) {
			System.err.println("Data lenght not equal to pixels lenght in TouchScreen.");
			return;
		}

		pixels = data.clone();
	}

	@Override
	public Color getColor(int x, int y) {
		int brightness = pixels[x + height * y];
		return new Color(brightness, brightness, brightness);
	}

	public Centroid[] getKMeanCentroids() {
		return new KMeanAlghoritm(this, 4, 20).getBest().getCentroids();
	}

	public ArrayList<Centroid> getCentroids() {
		return Grouping.getCentroids(pixels);
	}
}
