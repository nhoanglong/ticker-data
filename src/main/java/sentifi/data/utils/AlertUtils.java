package sentifi.data.utils;

/**
 * Alert Utils class provides utility functions for Alert
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class AlertUtils {
	
	/**
	 * Get string of alert when SMA-50 is lower than SMA-200
	 * @param ticker
	 * @param date
	 * @param open
	 * @param high
	 * @param low
	 * @param close
	 * @param volume
	 * @param SMA50Average
	 * @param SMA200Average
	 * @return string
	 */
	public static String getAlertSMA50LowerThanSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double SMA50Average, double SMA200Average) {
		return Utils.ALERT_BEARISH_STR + "," + ticker + "," + date + "," + open + "," + high + "," + low + "," + close + "," + volume
				+ "," + SMA50Average + "," + SMA200Average;
	}

	/**
	 * Get string of alert when SMA-50 is above SMA-200
	 * 		and current volume 10% above the avg of past 50 days 
	 * @param ticker
	 * @param date
	 * @param open
	 * @param high
	 * @param low
	 * @param close
	 * @param volume
	 * @param volAverage50
	 * @param SMA50Average
	 * @param SMA200Average
	 * @return
	 */
	public static String getAlertSMA50AbovesSMA200(String ticker, String date, double open, double high, double low,
			double close, double volume, double volAverage50, double SMA50Average, double SMA200Average) {
		return Utils.ALERT_BULLISH_STR + "," + ticker + "," + date + "," + open + "," + high + "," + low + "," + close + "," + volume
				+ "," + volAverage50 + "," + SMA50Average + "," + SMA200Average;
	}
}
