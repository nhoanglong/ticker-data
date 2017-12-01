package sentifi.data.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LinearWeightedMovingAverage {
	private Queue<Double> values = new LinkedList<Double>();
	private List<Integer> multipliers =  new ArrayList<Integer>();
	private int length; 
	private double linearSum; 		// sum of (value * its multiplier)
	private double multiplierSum; 	// sum of multiplier only
	private double basicSum; 		// basic sum of existed values
	private double average;

	public LinearWeightedMovingAverage(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be greater than zero!");
		}
		this.length = length;
		for (int i = length; i > 0; i--) {
			multipliers.add(i);
		}
	}

	/**
	 * Add new value into list.
	 * Calculate the new simple moving average value.
	 * @param value
	 */
	public void addNewValue(double value) {
		// new sum = current sum - the oldest value - sum of (N - 1) latest values + new value x biggest multiplier
		// However, the oldest value - sum of (N - 1) latest values = basic sum
		// For example, we have a queue as: tail [2, 4, 7, 4, 5] head, and multiplier is from 1-5.
		// Means 2*5 + 4*4 + 7*3 + 4*2 + 5*1 = 60 -> valuesSum
		// 1 + 2 + 3 + 4 + 5 = 15 -> multiplierSum
		// 2 + 4 + 7 + 4 + 5 = 22 -> basicSum
		// Add new value as 6 into the queue, we have new queue as tail [6, 2, 4, 7, 4] head
		// 6*5 + 2*(5-1) + 4*(4-1) + 7*(3-1) + 4*(2-1) = 6*5 + 2*5 + 4*4 + 7*3 + 4*2 + 5*1 - (2 + 4 + 7 + 4 + 5)
		//											   = 6*5 + 60 - 22
		linearSum = linearSum - basicSum + value*multipliers.get(0); //subtract the basic sum 
																	 //and add with new value x biggest multiplier
		
		if (values.size() == length && length > 0) { // in case of the value list is full
			basicSum = basicSum + value - values.element(); // update basic sum
			values.remove(); // remove the oldest element from queue			
		} else {// in case of value list is not full yet
			basicSum = basicSum + value; // basic sum  add with new value
		}
		
		values.add(new Double(value));// add new element
		average = linearSum / multiplierSum * 1.0;// calculate new average
	}

	public Queue<Double> getValues() {
		return values;
	}

	public void setValues(Queue<Double> values) {
		this.values = values;
	}

	public List<Integer> getMultipliers() {
		return multipliers;
	}

	public void setMultipliers(List<Integer> multipliers) {
		this.multipliers = multipliers;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getLinearSum() {
		return linearSum;
	}

	public void setLinearSum(double linearSum) {
		this.linearSum = linearSum;
	}

	public double getMultiplierSum() {
		return multiplierSum;
	}

	public void setMultiplierSum(double multiplierSum) {
		this.multiplierSum = multiplierSum;
	}

	public double getBasicSum() {
		return basicSum;
	}

	public void setBasicSum(double basicSum) {
		this.basicSum = basicSum;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
}
