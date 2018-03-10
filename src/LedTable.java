import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import effects.Effect;
import effects.EffectManager;
import matrix.Display;
import matrix.TouchScreen;

public class LedTable extends JFrame implements ActionListener, DocumentListener, FeedbackListener {
	private static final long serialVersionUID = 1L;

	JPanel mainPanel = new JPanel();

	JLabel hostLabel = new JLabel("ip:");
	JLabel portLabel = new JLabel("UDP port:");
	JLabel pingLabel = new JLabel("ping:0ms");
	JLabel fpsLabel = new JLabel("FPS:0");

	JTextField hostTextField = new JTextField("192.168.43.174");
	JTextField portTextField = new JTextField("6660");

	MatrixView displayView;
	TouchScreenView touchScreenView;

	JButton autoDetectHostButton = new JButton("Auto Detect Host");
	JButton sendButton = new JButton("Send packet");
	JToggleButton connectionToggleButton = new JToggleButton("Start connection");
	JButton testButton = new JButton("Test Button");
	JComboBox<Effect> effectComboBox = new JComboBox<Effect>();
	JSlider brightnessSlider = new JSlider(0, 255, 255);

	Display display;
	TouchScreen touchScreen;
	LedTableCommunicator ledTableCommunicator;
	Timer communicationTimer;
	Timer pokeTimer;
	Timer effectUpdateTimer;

	EffectManager effectManager;

	FpsCounter fpsCounter = new FpsCounter(10);

	public static void main(String[] args) {
		new LedTable();
	}

	LedTable() {
		super("LedTable Soft");
		this.setPreferredSize(new Dimension(1183, 495));
		communicationTimer = new Timer(30, this);
		pokeTimer = new Timer(3000, this);
		effectUpdateTimer = new Timer(20, this);

		portTextField.getDocument().addDocumentListener(this);
		hostTextField.getDocument().addDocumentListener(this);

		ledTableCommunicator = new LedTableCommunicator(hostTextField.getText(),

				Integer.parseInt(portTextField.getText()));
		ledTableCommunicator.addFeedbackListeners(this);
		display = new Display(10, 10);
		touchScreen = new TouchScreen(10, 10);
		displayView = new MatrixView(display, 40);
		touchScreenView = new TouchScreenView(touchScreen, 40);

		effectManager = new EffectManager(display);
		effectManager.registerComboBox(effectComboBox);

		brightnessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				display.setBrightness((double) brightnessSlider.getValue() / brightnessSlider.getMaximum());
			}
		});

		mainPanel.add(hostLabel);
		mainPanel.add(hostTextField);
		mainPanel.add(portLabel);
		mainPanel.add(portTextField);
		mainPanel.add(autoDetectHostButton);
		mainPanel.add(sendButton);
		mainPanel.add(testButton);
		mainPanel.add(pingLabel);
		mainPanel.add(fpsLabel);
		mainPanel.add(connectionToggleButton);
		mainPanel.add(effectComboBox);
		mainPanel.add(brightnessSlider);
		mainPanel.add(displayView);
		mainPanel.add(touchScreenView);

		effectComboBox.addActionListener(this);
		autoDetectHostButton.addActionListener(this);
		sendButton.addActionListener(this);
		connectionToggleButton.addActionListener(this);
		testButton.addActionListener(this);

		this.getContentPane().add(mainPanel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// autoDetectHostButton.doClick();
		 pokeTimer.start();
		effectUpdateTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == sendButton) {
			ledTableCommunicator.sendCalibrate(5);
		}

		if (source == autoDetectHostButton) {
			hostTextField.setText(ledTableCommunicator.autoDetectHost());
		}

		if (source == testButton) {
			/*
			 * KMeanAlghoritm algo = new KMeanAlghoritm(touchScreen, 4, 10);
			 * display.background(new Color(0, 0, 0)); KMean bestKMean =
			 * algo.getBest(); if (bestKMean == null) return; for (Centroid cen
			 * : bestKMean.getCentroids()) { for (Point p : cen.points) {
			 * display.setColor((int) p.x, (int) p.y,
			 * cen.getColor().darker().darker()); } display.setColor((int)
			 * cen.pos.x, (int) cen.pos.y, cen.getColor()); }
			 * displayView.repaint();
			 */
			ledTableCommunicator.sendCalibrate(3);

		}

		if (source == connectionToggleButton) {
			if (!connectionToggleButton.isSelected()) {
				connectionToggleButton.setText("Start connection");
				communicationTimer.stop();
			} else {
				connectionToggleButton.setText("Stop connection");
				communicationTimer.start();
			}

		}
		if (source == effectUpdateTimer) {
			effectManager.nextFrame(touchScreen);
			displayView.repaint();
		}

		if (source == communicationTimer) {
			ledTableCommunicator.sendFrame(display);
			ledTableCommunicator.getTouchScreenData();
			touchScreenView.repaint();
		}

		if (source == pokeTimer) {
			/*long startTime = System.currentTimeMillis();
			ledTableCommunicator.poke();
			startTime = System.currentTimeMillis() - startTime;
			pingLabel.setText("Ping:" + startTime + "ms");
			*/
			Robot r;
			try {
				r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_W);
				// later...
				r.keyRelease(KeyEvent.VK_W);
				r.keyRelease(KeyEvent.VK_CONTROL);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	public void updateCommunicator() {
		ledTableCommunicator.setHost(hostTextField.getText());
		ledTableCommunicator.setPort(Integer.parseInt(portTextField.getText()));
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		updateCommunicator();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		updateCommunicator();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		updateCommunicator();
	}

	@Override
	public void hostUpdate(String host) {
		hostTextField.setText(host);
	}

	@Override
	public void touchScreenDataUpdate(int[] data) {
		// TODO Auto-generated method stub
		System.out.println("usu " + System.currentTimeMillis());

		touchScreen.update(data);
	}
}
