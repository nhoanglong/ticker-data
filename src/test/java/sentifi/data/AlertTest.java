package sentifi.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sentifi.data.utils.Utils;

public class AlertTest extends TestCase {

	//"Date","Open","High","Low","Close","Volume", "VolumeAverage-50", "SMA-50", "SMA-200"
	private static String[][] testData = {
			{ "GE", "1962-10-04", "64.5", "66.0", "63.5", "66.0", "16500.0", "13120.0", "66.40", "0.0" },
			{ "GE", "1975-10-16", "67.25", "67.75", "67.25", "67.25", "12700.0", "13120", "66.64", "69.319" },
			{ "GE", "1999-10-05", "6.5", "6.0", "6.5", "6.0", "16500.0", "13120.0", "66.4", "66.4" } };

	//"bearish"/"bullish","Date","Open","High","Low","Close","Volume", (VolumeAverage-50",)* "SMA-50", "SMA-200"
	private static String[] expectedData = { "bullish,GE,1962-10-04,64.5,66.0,63.5,66.0,16500.0,13120.0,66.4,0.0",
			"bearish,GE,1975-10-16,67.25,67.75,67.25,67.25,12700.0,66.64,69.319" };

	/**
	 * Create the test case
	 * @param testName
	 */
	public AlertTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AlertTest.class);
	}

	/**
	 * Test of exported alert file name is alerts.dat
	 */
	public void testAlertFileName() {
		File alert = new File(Utils.ALERT_FILE_NAME);
		if (alert.exists()) {
			assertTrue(true);
		}
		assertFalse(false);
	}

	/**
	 * Test Alert data with conditions
	 */
	public void testAlertData() {
		// delete existed file first
		File alert = new File(Utils.ALERT_FILE_NAME);
		if (alert.exists()) {
			alert.delete();
		}

		TickerCLI cli = new TickerCLI();
		try (FileWriter fw = new FileWriter(Utils.ALERT_FILE_NAME, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);) {
			for (int i = 0; i < testData.length; i++) {
				cli.writeAlertIfSatisfiesConditions(out, testData[i][0], testData[i][1], Double.valueOf(testData[i][2]),
						Double.valueOf(testData[i][3]), Double.valueOf(testData[i][4]), Double.valueOf(testData[i][5]),
						Double.valueOf(testData[i][6]), Double.valueOf(testData[i][7]), Double.valueOf(testData[i][8]),
						Double.valueOf(testData[i][9]));
			}
		} catch (IOException e) {
			assertFalse(false);
		}

		try (FileReader fr = new FileReader(Utils.ALERT_FILE_NAME); BufferedReader br = new BufferedReader(fr);) {
			String record;
			int counter = 0;
			while ((record = br.readLine()) != null) {
				if(!record.equals(expectedData[counter])) {
					assertFalse(false);
				}
			}

		} catch (IOException e) {
			assertFalse(false);
		}

		assertTrue(true);
	}
}
