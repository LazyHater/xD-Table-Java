import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import matrix.IShowable;

public class MatrixView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private IShowable display;
	private int scale;
	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	private int width;
	private int height;
	private boolean gridVisable = true;
	
	MatrixView(IShowable display, int scale){
		this.scale = scale;
		this.display = display;
		width = display.getWidth() * scale;
		height = display.getHeight() * scale;
		this.setPreferredSize(new Dimension(width + 1, height + 1)); // +1 for fixing ugly border/grid painting
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(new Color(255,0,0));
		g.drawRect(0, 0, width - 1, height - 1);

		for(int y = 0; y < display.getHeight(); y++)
			for(int x = 0; x < display.getWidth(); x++){
				g.setColor(display.getColor(x, y));
				g.fillRect(x * scale, y * scale, scale, scale);
				
				if(gridVisable){
					g.setColor(new Color(0,0,0));  
					g.drawRect(x * scale, y * scale, scale, scale);
				}
			}
	}

	public boolean isGridVisable() {
		return gridVisable;
	}

	public void setGridVisable(boolean gridVisable) {
		this.gridVisable = gridVisable;
	}

}
