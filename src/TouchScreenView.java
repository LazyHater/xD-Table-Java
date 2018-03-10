import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import matrix.TouchScreen;

public class TouchScreenView extends MatrixView implements MouseListener {
	private static final long serialVersionUID = 1L;
	private TouchScreen touchScreen;
	private boolean renderValues = true;

	TouchScreenView(TouchScreen touchScreen, int scale) {
		super(touchScreen, scale);
		this.touchScreen = touchScreen;
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / getScale();
		int y = e.getY() / getScale();

		touchScreen.setStateAt(x, y, touchScreen.getStateAt(x, y) ? false : true);
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!renderValues)
			return;
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setColor(new Color(255, 0, 0));
			for (int y = 0; y < touchScreen.getHeight(); y++)
				for (int x = 0; x < touchScreen.getWidth(); x++) {
					g2d.drawString(Integer.toString(touchScreen.getValueAt(x, y)), x * getScale() + getScale() / 2, y * getScale() + getScale() / 2);
				}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
