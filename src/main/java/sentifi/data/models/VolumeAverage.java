package sentifi.data.models;

public class VolumeAverage extends SimpleMovingAverage {

	public VolumeAverage(int length, String name) {
		super(length, name);
	}

	@Override
	public double getAverage() {
		return average;
	}
}
