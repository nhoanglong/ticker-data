package sentifi.data.models;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleMovingAverage implements MovingAverageModel{
	private String name;
	private int length;
	private double average;
	private boolean lowerThanLengthFlag = true;
	
	protected double basicSum;
	protected Queue<Double> values = new LinkedList<Double>();
	
	public SimpleMovingAverage() {
		super();
	}

	public SimpleMovingAverage(int length, String name) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be greater than zero!");
		}
		this.length = length;
		this.name = name;
	}

	/**
	 * Add new value into list.
	 * Calculate the new simple moving average value.
	 * @param closeValue
	 */
	@Override
	public void addNewValue(double closeValue) {
		if (values.size() == this.getLength() && this.getLength() > 0) {
			this.setLowerThanLengthFlag(false);
			basicSum -= ((Double) values.element()).doubleValue();
			values.remove();
		}
		/*if(value == -1) {//mean missing data of the day
			values.addLast(new Double(value)); // only add last
			// no calculate new sum
			// no calculate new average
		} else {*/
			basicSum += closeValue;
			values.add(new Double(closeValue));
			this.setAverage(basicSum / values.size());//calculate new average
		//}		
	}
	
	public Queue<Double> getValues() {
		return values;
	}

	public void setValues(Queue<Double> values) {
		this.values = values;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getBasicSum() {
		return basicSum;
	}

	public void setBasicSum(double basicSum) {
		this.basicSum = basicSum;
	}

	public boolean isLowerThanLengthFlag() {
		return lowerThanLengthFlag;
	}

	public void setLowerThanLengthFlag(boolean lowerThanLengthFlag) {
		this.lowerThanLengthFlag = lowerThanLengthFlag;
	}

	@Override
	public double getAverage() {
		if(this.isLowerThanLengthFlag()) {
			return 0;
		}
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.getAverage();
	}
}
