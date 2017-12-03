package sentifi.data.models;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple Moving Average model.
 * Implements the Moving Average interface.
 * 
 * For example: the last five closing prices for MSFT are:
 * 		28.93+28.48+28.44+28.91+28.48 = 143.24
 * Simple Moving Average in the past 5 days is:
 * 		5-day SMA = 143.24/5 = 28.65
 * For the first N days in the series, set value to 0 when writing to files.
 * 
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class SimpleMovingAverage implements MovingAverageModel{
	private String name;
	private int length;
	private boolean lowerThanLengthFlag = true;
	
	protected double average;
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
		
		basicSum += closeValue;
		values.add(new Double(closeValue));
		this.setAverage(basicSum / values.size());//calculate new average	
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

	
	/**
	 * Get the Average Moving value.
	 * For the first N days, return to 0.
	 * @return average
	 */
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

	/**
	 * Get name of model
	 * @return name
	 */
	@Override
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
