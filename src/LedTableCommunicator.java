import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import matrix.Display;

public class LedTableCommunicator implements Runnable {

	private DatagramSocket dsocket;
	private String host;
	private int port;
	ArrayList<FeedbackListener> feedbackListeners = new ArrayList<FeedbackListener>();

	public class PACKET_TYPE {
		final static byte PING = 0;
		final static byte PONG = 1;
		final static byte SET_MATRIX = 2;
		final static byte GET_TOUCHSCREEN = 3;
		final static byte GET_TOUCHSCREEN_RAW = 4;
		final static byte TOUCHSCREEN = 5;
		final static byte TOUCHSCREEN_RAW = 6;
		final static byte CALIBRATE = 7;
		final static byte RESET = 8;
	}

	// PACKET_TYPE PACKET_TYPE = new PACKET_TYPE();

	public void addFeedbackListeners(FeedbackListener fbl) {
		feedbackListeners.add(fbl);
	}

	public void removeFeedbackListeners(FeedbackListener fbl) {
		feedbackListeners.remove(fbl);
	}

	public LedTableCommunicator(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			dsocket = new DatagramSocket();
			dsocket.setSoTimeout(0);
		} catch (SocketException e) {
			System.err.println(e);
		}

		new Thread(this).start();
	}

	public void sendFrame(Display d) {
		byte[] data = d.getRaw();
		byte[] frame = new byte[data.length + 2];

		frame[0] = PACKET_TYPE.SET_MATRIX; // packet type
		frame[1] = (byte) (data.length / 3); // number of leds

		for (int i = 0; i < data.length; i++)
			frame[i + 2] = data[i];

		sendData(frame);
	}

	public void getTouchScreenData() {
		byte[] requestData = new byte[1];
		requestData[0] = PACKET_TYPE.GET_TOUCHSCREEN;
		sendData(requestData);
	}

	public void getTouchScreenRawData() {
		byte[] requestData = new byte[1];
		requestData[0] = PACKET_TYPE.GET_TOUCHSCREEN_RAW;
		sendData(requestData);
	}

	public String autoDetectHost() {
		try {
			System.out.println("Sending PING");
			sendPing("192.168.0.255", port);

			return host;

		} catch (Exception e) {
			System.err.println(e.toString());
			return null;
		}
	}

	private void sendPing(String host, int port) {
		byte[] pingData = new byte[1];
		pingData[0] = PACKET_TYPE.PING;
		try {
			InetAddress address = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(pingData, pingData.length, address, port);
			dsocket.send(packet);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void sendCalibrate(int howManyAvg) {
		byte[] calibrateData = new byte[2];
		calibrateData[0] = PACKET_TYPE.CALIBRATE;
		calibrateData[1] = (byte) howManyAvg;
		sendData(calibrateData);
	}

	public void poke() {
		try {
			System.out.println("Sending PING.");
			sendPing(host, port);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void sendData(byte[] data) {
		try {
			InetAddress address = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			dsocket.send(packet);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void run() {
		try {
			dsocket.setSoTimeout(0);

			System.out.println("listening on port: " + dsocket.getLocalPort());

			byte[] data = new byte[200];

			while (true) {
				DatagramPacket responsePacket = new DatagramPacket(data, data.length);

				dsocket.receive(responsePacket);

				byte packetType = responsePacket.getData()[0];

				switch (packetType) {
				case PACKET_TYPE.PONG: {
					host = responsePacket.getAddress().toString();

					if (host.charAt(0) == '/')
						host = host.substring(1, host.length());
					System.out.println("Led Table at host: " + host);

					for (FeedbackListener fbl : feedbackListeners)
						fbl.hostUpdate(host);

				}
					break;
				case PACKET_TYPE.TOUCHSCREEN:
				case PACKET_TYPE.TOUCHSCREEN_RAW: {
					int[] touchScreenData = new int[100];
					for (int i = 0; i < touchScreenData.length; i++)
						touchScreenData[i] = (int) 0xff & data[i + 1];

					for (FeedbackListener fbl : feedbackListeners)
						fbl.touchScreenDataUpdate(touchScreenData);

				}
					break;
				default:
					System.out.println("Got packet of length: " + responsePacket.getLength() + " sentence: "
							+ new String(responsePacket.getData()));
				}

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
