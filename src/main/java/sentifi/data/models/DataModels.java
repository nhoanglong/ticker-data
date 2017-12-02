package sentifi.data.models;

public class DataModels {
	private TWAP twap = new TWAP();
	private MovingAverageModel sma50 = new SimpleMovingAverage(5, "SMA-50");
	private MovingAverageModel sma200 = new SimpleMovingAverage(7, "SMA-200");
	private MovingAverageModel lwma15 = new LinearWeightedMovingAverage(5, "LWMA-15");
	private MovingAverageModel lwma50 = new LinearWeightedMovingAverage(7, "LWMA-50");

	public void addNewRecord(double open, double close, double high, double low) {
		twap.addNewRecord(open, close, high, low);
		sma50.addNewValue(close);
		sma200.addNewValue(close);
		lwma15.addNewValue(close);
		lwma50.addNewValue(close);
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

	@Override
	public String toString() {
		return this.getTwap().toString() + "\n" + this.getSMA50().toString() + "\n" + this.getSMA200().toString() + "\n"
				+ this.getLWMA15().toString() + "\n" + this.getLWMA50().toString();
	}
}
