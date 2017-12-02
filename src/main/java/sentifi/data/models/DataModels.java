package sentifi.data.models;

public class DataModels {
	private TWAP twap = new TWAP();
	private SimpleMovingAverage sma50 = new SimpleMovingAverage(50, "SMA-50");
	private SimpleMovingAverage sma200 = new SimpleMovingAverage(200, "SMA-200");

	public void addNewRecord(double open, double close, double high, double low) {
		twap.addNewRecord(open, close, high, low);
		sma50.addNewValue(close);
		sma200.addNewValue(close);
	}

	public TWAP getTwap() {
		return twap;
	}

	public void setTwap(TWAP twap) {
		this.twap = twap;
	}

	public SimpleMovingAverage getSMA50() {
		return sma50;
	}

	public void setSMA50(SimpleMovingAverage sma50) {
		this.sma50 = sma50;
	}

	public SimpleMovingAverage getSMA200() {
		return sma200;
	}

	public void setSMA200(SimpleMovingAverage sma200) {
		this.sma200 = sma200;
	}

	@Override
	public String toString() {
		return this.twap.toString() + "\n" + this.sma50.toString() + "\n" + this.sma200.toString();
	}
}
