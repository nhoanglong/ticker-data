package sentifi.data.utils;

public class AlertUtils {
	public static String getAlertSMA50LowerThanSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double SMA50Average, double SMA200Average) {
		return Utils.ALERT_BEARISH_STR + "," + ticker + "," + date + "," + open + "," + high + "," + low + "," + close + "," + volume
				+ "," + SMA50Average + "," + SMA200Average;
	}

	public static String getAlertSMA50AbovesSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double volAverage50, double SMA50Average, double SMA200Average) {
		return Utils.ALERT_BULLISH_STR + "," + ticker + "," + date + "," + open + "," + high + "," + low + "," + close + "," + volume
				+ "," + volAverage50 + "," + SMA50Average + "," + SMA200Average;
	}
}
