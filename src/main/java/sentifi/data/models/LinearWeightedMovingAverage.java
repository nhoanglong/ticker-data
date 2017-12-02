package sentifi.data.models;

import java.util.ArrayList;
import java.util.List;

public class LinearWeightedMovingAverage extends SimpleMovingAverage {
	private double linearSum; // sum of (value * its multiplier)
	private double multiplierSum; // sum of multiplier only
	private List<Integer> multipliers = new ArrayList<Integer>();

	public LinearWeightedMovingAverage(int length, String name) {
		super(length, name);
		for (int i = length; i > 0; i--) {
			multipliers.add(i);
		}
	}

	/**
	 * Add new value into list. Calculate the new simple moving average value.
	 * 
	 * @param closeValue
	 */
	@Override
	public void addNewValue(double closeValue) {
		// new sum = current sum - the oldest value - sum of (N - 1) latest
		// values + new value x biggest multiplier
		// However, the oldest value - sum of (N - 1) latest values = basic sum
		// For example, we have a queue as: tail [2, 4, 7, 4, 5] head, and
		// multiplier is from 1-5.
		// Means 2*5 + 4*4 + 7*3 + 4*2 + 5*1 = 60 -> valuesSum
		// 1 + 2 + 3 + 4 + 5 = 15 -> multiplierSum
		// 2 + 4 + 7 + 4 + 5 = 22 -> basicSum
		// Add new value as 6 into the queue, we have new queue as tail [6, 2,
		// 4, 7, 4] head
		// 6*5 + 2*(5-1) + 4*(4-1) + 7*(3-1) + 4*(2-1) = 6*5 + 2*5 + 4*4 + 7*3 +
		// 4*2 + 5*1 - (2 + 4 + 7 + 4 + 5)
		// = 6*5 + 60 - 22
		
		// subtract the basic sum
		// and add with new value x biggest multiplier
		linearSum = linearSum - basicSum + closeValue * multipliers.get(0);		

		if (values.size() == this.getLength() && this.getLength() > 0) { // if the value list is full
			this.setLowerThanLengthFlag(false);
			basicSum = basicSum + closeValue - values.element(); // update basic sum
			values.remove(); // remove the oldest element from queue
		} else {// in case of value list is not full yet
			basicSum = basicSum + closeValue; // basic sum add with new value
			multiplierSum = multiplierSum + (this.getLength() - values.size());
		}

		values.add(new Double(closeValue));// add new element
		this.setAverage(linearSum / multiplierSum * 1.0);// calculate new average
	}

	public List<Integer> getMultipliers() {
		return multipliers;
	}

	public void setMultipliers(List<Integer> multipliers) {
		this.multipliers = multipliers;
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
}
