package sentifi.data.utils;

public class AlertUtils {
	public static String getAlertSMA50LowerThanSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double SMA50Average, double SMA200Average) {
		return String.format("bearish,%s,%s,%f,%f,%f,%f,%f,%f,%f", ticker, date, open, high, low, close, volume,
				SMA50Average, SMA200Average);
	}

	public static String getAlertSMA50AbovesSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double volAverage50, double SMA50Average, double SMA200Average) {
		return String.format("bullish,%s,%s,%f,%f,%f,%f,%f,%f,%f,%f", ticker, date, open, high, low, close, volume,
				volAverage50, SMA50Average, SMA200Average);
	}
}
