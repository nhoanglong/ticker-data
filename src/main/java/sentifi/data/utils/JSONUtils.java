package sentifi.data.utils;

import java.io.FileWriter;

import org.json.JSONObject;

/**
 * JSON Utils class provides utility functions for exporting JSON file
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class JSONUtils {
	
	/**
	 * Write to JSON file as pretty-print format.
	 * Exported file: ticker-name.json.
	 * For example: GE.json
	 * @param ticker
	 * @param json
	 */
	public static void writeToJSONFile(String ticker, JSONObject json) {
		try (FileWriter fileWriter = new FileWriter(ticker + ".json")) {
			fileWriter.write(json.toString(4));
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create JSON Object of record attributes.
	 * @param ticker
	 * @param date
	 * @param open
	 * @param high
	 * @param low
	 * @param close
	 * @param volume
	 * @param twapOpen
	 * @param twapHigh
	 * @param twapLow
	 * @param twapClose
	 * @param sma50
	 * @param sma200
	 * @param lwma15
	 * @param lwma50
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject createJSONRecord(String ticker, String date, String open, String high, String low,
			String close, String volume, String twapOpen, String twapHigh, String twapLow, String twapClose,
			String sma50, String sma200, String lwma15, String lwma50) {
		JSONObject json = new JSONObject();
		json.put(Utils.TICKER_STR, ticker);
		json.put(Utils.DATE_STR, date);
		json.put(Utils.OPEN_STR, open);
		json.put(Utils.HIGH_STR, high);
		json.put(Utils.LOW_STR, low);
		json.put(Utils.CLOSE_STR, close);
		json.put(Utils.VOLUME_STR, volume);
		json.put(Utils.TWAP_OPEN_STR, twapOpen);
		json.put(Utils.TWAP_HIGH_STR, twapHigh);
		json.put(Utils.TWAP_LOW_STR, twapLow);
		json.put(Utils.TWAP_CLOSE_STR, twapClose);
		json.put(Utils.SMA_50_STR, sma50);
		json.put(Utils.SMA_200_STR, sma200);
		json.put(Utils.SMA_50_STR, lwma15);
		json.put(Utils.SMA_200_STR, lwma50);
		return json;
	}
}
