package sentifi.data.models;

/**
 * Data model object which contain multiple data transformation models.
 * Include TWAP, SMA-50, SMA-200, LWMA-15, LWMA-50 and VA-50
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class DataModels {
	private TWAP twap = new TWAP();
	private MovingAverageModel sma50 = new SimpleMovingAverage(50, "SMA-50");
	private MovingAverageModel sma200 = new SimpleMovingAverage(200, "SMA-200");
	private MovingAverageModel lwma15 = new LinearWeightedMovingAverage(15, "LWMA-15");
	private MovingAverageModel lwma50 = new LinearWeightedMovingAverage(50, "LWMA-50");
	private MovingAverageModel va50 = new VolumeAverage(5, "VolumeAverage-50");
	
	/**
	 * Add new record into data model.
	 * @param open
	 * @param close
	 * @param high
	 * @param low
	 * @param volume
	 */
	public void addNewRecord(double open, double close, double high, double low, double volume) {
		twap.addNewRecord(open, close, high, low);
		sma50.addNewValue(close);
		sma200.addNewValue(close);
		lwma15.addNewValue(close);
		lwma50.addNewValue(close);
		va50.addNewValue(volume);
	}

	public TWAP getTwap() {
		return twap;
	}

	public void setTwap(TWAP twap) {
		this.twap = twap;
	}

	public MovingAverageModel getSMA50() {
		return sma50;
	}

	public void setSMA50(MovingAverageModel sma50) {
		this.sma50 = sma50;
	}

	public MovingAverageModel getSMA200() {
		return sma200;
	}

	public void setSMA200(MovingAverageModel sma200) {
		this.sma200 = sma200;
	}

	public MovingAverageModel getLWMA15() {
		return lwma15;
	}

	public void setLWMA15(MovingAverageModel lwma15) {
		this.lwma15 = lwma15;
	}

	public MovingAverageModel getLWMA50() {
		return lwma50;
	}

	public void setLWMA50(MovingAverageModel lwma50) {
		this.lwma50 = lwma50;
	}
	
	public MovingAverageModel getVA50() {
		return va50;
	}

	public void setVA50(MovingAverageModel va50) {
		this.va50 = va50;
	}

	@Override
	public String toString() {
		return this.getTwap().toString() + "\n" + this.getSMA50().toString() + "\n" + this.getSMA200().toString() + "\n"
				+ this.getLWMA15().toString() + "\n" + this.getLWMA50().toString() + "\n" + this.getVA50().toString();
	}
}
