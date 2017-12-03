package sentifi.data.models;

public class VolumeAverage extends SimpleMovingAverage {

	public VolumeAverage(int length, String name) {
		super(length, name);
	}

	/**
	 * Get the Average Moving value.
	 * @return average
	 */
	@Override
	public double getAverage() {
		return average;
	}
}
