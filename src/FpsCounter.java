
public class FpsCounter {
	int[] data;
	int next = 0;

	FpsCounter(int size) {
		data = new int[size];

		for (int i = 0; i < data.length; i++)
			data[i] = 0;
	}

	void add(int fps) {
		data[next] = fps;
		next++;
		if (next >= data.length)
			next = 0;
	}

	int getAvg() {
		int sum = 0;
		for (int i = 0; i < data.length; i++)
			sum += data[i];
		sum /= data.length;

		return sum;
	}
}
