package sentifi.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import sentifi.data.models.DataModels;
import sentifi.data.utils.AlertUtils;
import sentifi.data.utils.JSONUtils;
import sentifi.data.utils.Utils;

/**
 * A CLI to retrieve, process, transform and write various formats.
 * 
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class TickerCLI {
	private final static Logger logger = Logger.getLogger(App.class);

	/**
	 * Proceed CLI with input parameters
	 * 
	 * @param args
	 */
	public void proceed(String[] args) {
		Options options = new Options();
		String ticker = null;
		options.addOption("h", "help", false, "Show help.");
		options.addOption("t", "ticker", true, "Ticker symbol which generates TWAP, SMA, LWMA.");
		options.addOption("m", "models", true,
				"Expected models that will generated, separated by colon. \nFor example: TWAP,SMA");

		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {// help
				this.help(options);
			}

			if (cmd.hasOption("t")) {// ticker
				System.out.println(cmd.getOptionValue("t"));
				ticker = cmd.getOptionValue("t");
				this.proceedQuandlData(ticker);
			} else {
				this.help(options);
			}
		} catch (ParseException e) {
			this.help(options);
		} catch (Exception e) {
			logger.error("Error while getting data from Quandl: " + e.getMessage());
		}
	}

	/**
	 * Provide Help information of CLI
	 * 
	 * @param options
	 */
	private void help(Options options) {
		// Print Help and Usage
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}

	/**
	 * Retrieve Quandle data, then transform and export to JSON, CSV and make
	 * alerts
	 * 
	 * @param tickerSymbol
	 * @throws Exception
	 */
	private void proceedQuandlData(String tickerSymbol) throws Exception {
		logger.info("Retrieving data of " + tickerSymbol + " from Quandl...");
		int counter = 0;
		String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + tickerSymbol + ".csv?order=asc";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// default is GET
		int responseCode = con.getResponseCode();
		logger.debug("Sending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String record;

		File file = new File(tickerSymbol + ".csv");
		CsvWriter csvWriter = new CsvWriter();
		DataModels dm = new DataModels();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		try (FileWriter fw = new FileWriter("alerts.dat", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {

			// Append CSV header
			writeCSVLine(csvAppender, Utils.TICKER_STR, Utils.DATE_STR, Utils.OPEN_STR, Utils.HIGH_STR, Utils.LOW_STR,
					Utils.CLOSE_STR, Utils.VOLUME_STR, Utils.TWAP_OPEN_STR, Utils.TWAP_HIGH_STR, Utils.TWAP_LOW_STR,
					Utils.TWAP_CLOSE_STR, dm.getSMA50().getName(), dm.getSMA200().getName(), dm.getLWMA15().getName(),
					dm.getLWMA50().getName());

			logger.info("Processing data of " + tickerSymbol + " from Quandl...");
			while ((record = in.readLine()) != null) {
				if (counter == 0) {
					// this is header
				} else {
					String[] attributes = record.split(",");
					String date = attributes[0]; // 1st column is Date
					double openValue = Double.parseDouble(attributes[1]);
					double highValue = Double.parseDouble(attributes[2]);
					double lowValue = Double.parseDouble(attributes[3]);
					double closeValue = Double.parseDouble(attributes[4]);
					double volume = Double.parseDouble(attributes[5]);

					dm.addNewRecord(openValue, closeValue, highValue, lowValue, volume);

					// convert to String and ready for export CSV and JSON
					String openValueStr = Double.toString(openValue);
					String highValueStr = Double.toString(highValue);
					String lowValueStr = Double.toString(lowValue);
					String closeValueStr = Double.toString(closeValue);
					String volumeStr = Double.toString(volume);
					String twapOpenStr = Double.toString(dm.getTwap().getTWAPOpen());
					String twapHighStr = Double.toString(dm.getTwap().getTWAPHigh());
					String twapLowStr = Double.toString(dm.getTwap().getTWAPLow());
					String twapCloseStr = Double.toString(dm.getTwap().getTWAPClose());
					String sma50Str = Double.toString(dm.getSMA50().getAverage());
					String sma200Str = Double.toString(dm.getSMA200().getAverage());
					String lwma15Str = Double.toString(dm.getLWMA15().getAverage());
					String lwma50Str = Double.toString(dm.getLWMA50().getAverage());

					// Append functional REQ #1, #2 and #3 in ticker.csv
					writeCSVLine(csvAppender, tickerSymbol, date, openValueStr, highValueStr, lowValueStr,
							closeValueStr, volumeStr, twapOpenStr, twapHighStr, twapLowStr, twapCloseStr, sma50Str,
							sma200Str, lwma15Str, lwma50Str);

					// Append functional REQ #1, #2 and #3 in JSONArray and
					// ready for export ticker.json
					JSONObject tmp = JSONUtils.createJSONRecord(tickerSymbol, date, openValueStr, highValueStr,
							lowValueStr, closeValueStr, volumeStr, twapOpenStr, twapHighStr, twapLowStr, twapCloseStr,
							sma50Str, sma200Str, lwma15Str, lwma50Str);
					;

					jsonArray.put(tmp);

					if (counter < 5) {
						System.out.println(tmp);
						System.out.println(jsonArray);
					}

					// Append functional REQ #4 in alerts.dat
					double sma50Avg = dm.getSMA50().getAverage();
					double sma200Avg = dm.getSMA200().getAverage();
					double va50 = dm.getVA50().getAverage();
					if (sma50Avg < sma200Avg) {
						out.println(AlertUtils.getAlertSMA50LowerThanSMA200(tickerSymbol, date, openValue, highValue,
								lowValue, closeValue, volume, sma50Avg, sma200Avg));
					} else if (sma50Avg > sma200Avg && volume > va50 * 1.1) {
						out.println(AlertUtils.getAlertSMA50AbovesSMA200(tickerSymbol, date, openValue, highValue,
								lowValue, closeValue, volume, va50, sma50Avg, sma200Avg));
					}
				}
				counter++;
			}
			in.close();

			json.put(Utils.PRICES_STR, jsonArray);
			JSONUtils.writeToJSONFile(tickerSymbol, json);
			logger.info("Completed writing data to JSON.");
		} catch (IOException e) {
			// exception handling
			logger.error("Error while handling file: " + e.getMessage());
		} catch (Exception e) {
			// exception handling
			logger.error("Error while handling data: " + e.getMessage());
		}
		System.out.println(counter + " lines, 1st is headers");
		System.out.println(dm);
	}

	public void writeCSVLine(CsvAppender csvAppender, String tickerSymbol, String date, String openValueStr,
			String highValueStr, String lowValueStr, String closeValueStr, String volumeStr, String twapOpenStr,
			String twapHighStr, String twapLowStr, String twapCloseStr, String sma50Str, String sma200Str,
			String lwma15Str, String lwma50Str) throws IOException {
		csvAppender.appendLine(tickerSymbol, date, openValueStr, highValueStr, lowValueStr, closeValueStr, volumeStr,
				twapOpenStr, twapHighStr, twapLowStr, twapCloseStr, sma50Str, sma200Str, lwma15Str, lwma50Str);
	}
}
