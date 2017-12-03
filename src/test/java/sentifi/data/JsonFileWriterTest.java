package sentifi.data;

import java.io.IOException;

import org.json.JSONObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sentifi.data.utils.JSONUtils;

public class JsonFileWriterTest extends TestCase {
	private static final String[] TICKER = { "GE", "GOOG", "MSFT" };

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public JsonFileWriterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JsonFileWriterTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public void testCorrectJsonString() {
		String jsonData;
		try {
			jsonData = JSONUtils.readFile(TICKER[0] + ".json");
			JSONObject jobj = new JSONObject(jsonData);
			JSONObject a = new JSONObject(jobj.getJSONArray("Prices").get(0).toString());
			Exception ex = null;
			try {
				a.get("Ticker");
				a.get("Date");
				a.get("Open");
				a.get("High");
				a.get("Low");
				a.get("Close");
				a.get("Volume");
				a.get("TWAP-Open");
				a.get("TWAP-High");
				a.get("TWAP-Low");
				a.get("TWAP-Close");
			} catch (Exception e) {
				ex = e;
			}
			assertTrue(ex == null);
		} catch (IOException e1) {
			assertTrue(false);
		}
	}

	public void testIncorrectJsonString() {
		try {
			String jsonData = JSONUtils.readFile(TICKER[0] + ".json");
			JSONObject jobj = new JSONObject(jsonData);
			JSONObject a = new JSONObject(jobj.getJSONArray("Prices").get(0).toString());
			Exception ex = null;
			try {
				a.get("Ticker1");
				a.get("Date");
				a.get("Open");
				a.get("High");
				a.get("Low");
				a.get("Close");
				a.get("Volume");
				a.get("TWAP-Open");
				a.get("TWAP-High");
				a.get("TWAP-Low");
				a.get("TWAP-Close");
			} catch (Exception e) {
				ex = e;
			}
			assertFalse(ex == null);
		} catch (IOException e1) {
			assertFalse(true);
		}
		
	}

	public void testGOOGFileDoesNotExist() {
		Exception ex = null;
		try {
			JSONUtils.readFile(TICKER[1] + ".json");
		} catch (Exception e) {
			ex = e;
		}
		assertFalse(ex == null);
	}

	public void testMSFTFileDoesNotExist() {
		Exception ex = null;
		try {
			JSONUtils.readFile(TICKER[2] + ".json");
		} catch (Exception e) {
			ex = e;
		}
		assertFalse(ex == null);

	}
}
