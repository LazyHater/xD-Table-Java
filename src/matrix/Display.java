package matrix;

import java.awt.Color;

public class Display implements IShowable {
	private Color[] pixels;
	private int width;
	private int height;
	private double brightness = 1.0d;

	public Display(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new Color[width * height];
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = new Color(0, 0, 0);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public Color getColor(int x, int y){
		Color c = pixels[x + height * y];
		return new Color((int) (c.getRed()*brightness),
						 (int) (c.getGreen()*brightness),
						 (int) (c.getBlue()*brightness));
	}
	
	public void setColor(int x, int y, Color c){
		pixels[x + height * y] = c;
	}
	
	public byte[] getRaw(){
		byte[] data = new byte[width * height * 3];
		
		for(int i = 0; i < width * height; i++){
			data[i * 3 + 0] = (byte) (pixels[i].getRed() * brightness);
			data[i * 3 + 1] = (byte) (pixels[i].getGreen() * brightness);
			data[i * 3 + 2] = (byte) (pixels[i].getBlue() * brightness);
		}
		
		return data;
	}

	public void background(Color color) {
		for(int i = 0; i < width*height; i++){
			pixels[i] = color;
		}
	}

	public double getBrightness() {
		return brightness;
	}

	public void setBrightness(double brightness) {
		this.brightness = brightness;
	}
}
