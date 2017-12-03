package sentifi.data.models;

/**
 * Volume Average model.
 * Extends and re-uses from Simple Moving Average model due to many common attributes.
 * 
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
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
