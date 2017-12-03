package sentifi.data;

/**
 * A CLI to retrieve, process, transform and write various formats
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public class App {
	public static void main(String[] args) {
		TickerCLI cli = new TickerCLI();
		cli.proceed(args);
	}
}
