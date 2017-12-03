package sentifi.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CsvFileWriterTest extends TestCase{
	private List<String> expectedHeaders = Arrays.asList("Ticker","Date","Open", "High",
														 "Low", "Close", "Volumn",
														 "TWAP-Open", "TWAP-High", "TWAP-Low",
														 "TWAP-Close", "SMA-50", "SMA-200",
														 "LWMA-15", "LWMA-50");
	private static final String[] TICKER = {"GE", "GOOG", "MSFT"}; 
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CsvFileWriterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CsvFileWriterTest.class );
    }
    
    

    /**
     * Rigourous Test :-)
     */
    public void testCorrectHeader()
    {
    	File file = new File(TICKER[0]+".csv");
    	CsvReader csvReader = new CsvReader();
    	CsvParser csvParser = null;
		try {
			csvParser = csvReader.parse(file, StandardCharsets.UTF_8);
			List<String> header = csvParser.nextRow().getFields();
			System.out.println(header);
			System.out.println(expectedHeaders);
			System.out.println(expectedHeaders.s(Arrays.asList(header)));
	    	assertTrue(Arrays.asList(header).containsAll(expectedHeaders));
		} catch (IOException e) {
			assertTrue(true);
		}
    }
}
