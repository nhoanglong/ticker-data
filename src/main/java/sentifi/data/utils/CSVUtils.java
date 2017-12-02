package sentifi.data.utils;

import sentifi.data.models.DataModels;

public class CSVUtils {
	public static String getHeaders(DataModels dm) {
		return Utils.TICKER_STR + "," + Utils.DATE_STR + "," + Utils.OPEN_STR + "," + Utils.HIGH_STR + ","
				+ Utils.LOW_STR + "," + Utils.CLOSE_STR + "," + Utils.VOLUME_STR + "," + Utils.TWAP_OPEN_STR + ","
				+ Utils.TWAP_HIGH_STR + "," + Utils.TWAP_LOW_STR + "," + Utils.TWAP_CLOSE_STR + ","
				+ dm.getSMA50().getName() + "," + dm.getSMA200().getName() + "," + dm.getLWMA15().getName() + ","
				+ dm.getLWMA50().getName();
	}

	public static String getValuesAsString(String ticker, String date, double open, double high, double low,
			double close, double volume, double twapOpen, double twapHigh, double twapLow, double twapClose,
			double sma50, double sma200, double lwma15, double lwma50) {
		return ticker + "," + date + "," + open + "," + high + "," + low + "," + close + "," + close + "," + twapOpen
				+ "," + twapHigh + "," + twapLow + "," + twapClose + "," + sma50 + "," + sma200 + "," + lwma15 + ","
				+ lwma50;
	}
}
