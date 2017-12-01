package sentifi.data.models;

import java.util.LinkedList;

public class SimpleMovingAverage {
	private LinkedList<Double> values = new LinkedList<Double>();
	private int length;
	private double sum;
	private double average;

	public SimpleMovingAverage(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be greater than zero!");
		}
		this.length = length;
	}

	/**
	 * Add new value into list.
	 * Calculate the new simple moving average value.
	 * @param value
	 */
	public void addNewValue(double value) {
		if (values.size() == length && length > 0) {
			sum -= ((Double) values.getFirst()).doubleValue();
			values.removeFirst();
		}
		/*if(value == -1) {//mean missing data of the day
			values.addLast(new Double(value)); // only add last
			// no calculate new sum
			// no calculate new average
		} else {*/
			sum += value;
			values.addLast(new Double(value));
			average = sum / values.size();//calculate new average
		//}		
	}

	public LinkedList<Double> getValues() {
		return values;
	}

	public void setValues(LinkedList<Double> values) {
		this.values = values;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
}
