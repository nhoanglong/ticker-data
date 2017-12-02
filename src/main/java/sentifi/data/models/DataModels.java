package sentifi.data.models;

public class DataModels {
	private TWAP twap = new TWAP();
	private SimpleMovingAverage sma50 = new SimpleMovingAverage(50);
	private SimpleMovingAverage sma200 = new SimpleMovingAverage(200);

	public void addNewRecord(String record) {
		// 1st column is Date
		String[] attributes = record.split(",");
		twap.addNewRecord(Double.parseDouble(attributes[1]), Double.parseDouble(attributes[4]),
				Double.parseDouble(attributes[2]), Double.parseDouble(attributes[3]));
	}

	public TWAP getTwap() {
		return twap;
	}

	public void setTwap(TWAP twap) {
		this.twap = twap;
	}

	@Override
	public String toString() {
		return this.twap.toString();
	}
}
