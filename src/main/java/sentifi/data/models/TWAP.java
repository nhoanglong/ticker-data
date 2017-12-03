package sentifi.data.models;

/**
 * Time Weighted Average Price model.
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class TWAP {
	private int counter;// actual number of record
	private double openSum;
	private double closeSum;
	private double highSum;
	private double lowSum;

	public TWAP() {
		super();
	}

	/**
	 * Add new record and sum open, close, high and low with new added values.
	 * @param open
	 * @param close
	 * @param high
	 * @param low
	 */
	public void addNewRecord(double open, double close, double high, double low) {
		openSum += open;
		closeSum += close;
		highSum += high;
		lowSum += low;
		counter++;
	}

	/**
	 * Get TWAP-Open value
	 * @return TWAP-Open
	 */
	public double getTWAPOpen() {
		return openSum / counter;
	}

	/**
	 * Get value of TWAP-Close
	 * @return TWAP-Close
	 */
	public double getTWAPClose() {
		return closeSum / counter;
	}

	/**
	 * Get value of TWAP-High
	 * @return TWAP-High
	 */
	public double getTWAPHigh() {
		return highSum / counter;
	}

	/**
	 * Get value of TWAP-Low
	 * @return TWAP-Low
	 */
	public double getTWAPLow() {
		return lowSum / counter;
	}

	public double getOpenSum() {
		return openSum;
	}

	public void setOpenSum(double openSum) {
		this.openSum = openSum;
	}

	public double getCloseSum() {
		return closeSum;
	}

	public void setCloseSum(double closeSum) {
		this.closeSum = closeSum;
	}

	public double getHighSum() {
		return highSum;
	}

	public void setHighSum(double highSum) {
		this.highSum = highSum;
	}

	public double getLowSum() {
		return lowSum;
	}

	public void setLowSum(double lowSum) {
		this.lowSum = lowSum;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "TWAP via " + counter + " records:\nTWAP-Open: " + getTWAPOpen() + " TWAP-High: "
				+ getTWAPHigh() + "\nTWAP-Low: " + getTWAPLow() + " TWAP-Close: " + getTWAPClose();
	}
}
