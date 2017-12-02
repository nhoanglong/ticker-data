package sentifi.data.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class QuadlDataRetrievor {
	private final static Logger logger = Logger.getLogger(QuadlDataRetrievor.class);

	public void getQuadlData(String tickerSymbol) throws Exception {
		logger.info("Retrieving data of "+tickerSymbol+" from Quandl...");
		int counter = 0;
		String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + tickerSymbol + ".csv?order=asc";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			counter++;
		}
		in.close();
		System.out.println(counter);
	}
}
