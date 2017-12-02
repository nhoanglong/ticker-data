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

import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import sentifi.data.models.DataModels;
import sentifi.data.utils.AlertUtils;

public class TickerCLI {
	private final static Logger logger = Logger.getLogger(App.class);

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
			if (cmd.hasOption("h")) {
				this.help(options);
			}

			if (cmd.hasOption("t")) {
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

	private void help(Options options) {
		// Print Help and Usage
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}

	private void proceedQuandlData(String tickerSymbol) throws Exception {
		logger.info("Retrieving data of " + tickerSymbol + " from Quandl...");
		int counter = 0;
		String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + tickerSymbol + ".csv?order=asc";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// default is GET
		int responseCode = con.getResponseCode();
		logger.debug("\nSending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String record;

		File file = new File("foo.csv");
		CsvWriter csvWriter = new CsvWriter();
		DataModels dm = new DataModels();
		//
		
		try (FileWriter fw = new FileWriter("alerts.dat", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {
			
			//CSV header
		    csvAppender.appendLine("header1", "header2");
			while ((record = in.readLine()) != null) {
				if (counter == 0) {
					// this is header
				} else {
					// 1st column is Date
					String[] attributes = record.split(",");
					String date = attributes[0];
					double openValue = Double.parseDouble(attributes[1]);
					double highValue = Double.parseDouble(attributes[2]);
					double lowValue = Double.parseDouble(attributes[3]);
					double closeValue = Double.parseDouble(attributes[4]);
					double volume = Double.parseDouble(attributes[5]);
					dm.addNewRecord(openValue, closeValue, highValue, lowValue, volume);
					// if(counter < 10) {
					System.out.println(record);
					System.out.println(dm.getSMA50().toString());
					System.out.println(dm.getSMA200().toString());
					System.out.println(dm.getLWMA15().toString());
					System.out.println(dm.getLWMA50().toString());
					System.out.println(dm.getVA50().toString());
					
					//Functional REQ #1, #2 and #3 in CSV

					//Functional REQ #4
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
					// }
				}
				counter++;
			}
			in.close();
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
		System.out.println(counter + " lines, 1st is headers");
		System.out.println(dm);
	}
}
