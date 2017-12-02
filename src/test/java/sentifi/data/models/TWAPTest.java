package sentifi.data.models;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TWAPTest extends TestCase {
	private TWAP twap = new TWAP();
	//4 days with open, close, high, low respectively
	private static Double[][] testData1 = { {1.0, 2.0, 3.0, 4.0}, 		   // day 1
											{50.1, 20.2, 31.3, 23.7},	   // day 2
											{25.1, 36.2, 24.3, 13.7},      // day 3
											{150.1, 120.2, 131.3, 123.7}}; // day 4
	//1 day
	private static Double[][] testData2 = {{50.1, 20.2, 31.3, 23.7}};
	//3 days with open, close, high, low respectively
	private static Double[][] testData3 = { {100.1, 68.5, 98.0, 78.0}, // day 1
											{50.1, 20.2, 31.3, 23.7},  // day 2
											{25.1, 36.2, 24.3, 13.7}}; // day 3

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TWAPTest( String testName )
    {
    	super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TWAPTest.class );
    }
    
    private Double[] prepareRecords(Double[][] testData) {
    	Double[] expectedSum = {0.0, 0.0,0.0,0.0};
    	
    	for (int i = 0; i < testData.length; i++) {
    		twap.addNewRecord(testData[i][0],testData[i][1],testData[i][2], testData[i][3]);
    		for (int j = 0; j < 4; j++) {
    			expectedSum[j] = expectedSum[j] + testData[i][j];
			}
		}
    	return expectedSum;
    }

    /**
     * Rigourous Test :-)
     */
    public void testData1() 
    {
    	int numberOfRecords = testData1.length;
    	Double[] expectedSum = prepareRecords(testData1);
    	assertEquals(twap.getOpenSum(), expectedSum[0]);
    	assertEquals(twap.getCloseSum(), expectedSum[1]);
    	assertEquals(twap.getHighSum(), expectedSum[2]);
    	assertEquals(twap.getLowSum(), expectedSum[3]);
    	
    	assertEquals(twap.getTWAPOpen(), expectedSum[0]/numberOfRecords);
    	assertEquals(twap.getTWAPClose(), expectedSum[1]/numberOfRecords);
    	assertEquals(twap.getTWAPHigh(), expectedSum[2]/numberOfRecords);
    	assertEquals(twap.getTWAPLow(), expectedSum[3]/numberOfRecords);
    }
    
    public void testData2() 
    {
    	int numberOfRecords = testData2.length;
    	Double[] expectedSum = prepareRecords(testData2);
    	assertEquals(twap.getOpenSum(), expectedSum[0]);
    	assertEquals(twap.getCloseSum(), expectedSum[1]);
    	assertEquals(twap.getHighSum(), expectedSum[2]);
    	assertEquals(twap.getLowSum(), expectedSum[3]);
    	
    	assertEquals(twap.getTWAPOpen(), expectedSum[0]/numberOfRecords);
    	assertEquals(twap.getTWAPClose(), expectedSum[1]/numberOfRecords);
    	assertEquals(twap.getTWAPHigh(), expectedSum[2]/numberOfRecords);
    	assertEquals(twap.getTWAPLow(), expectedSum[3]/numberOfRecords);
    }
    
    public void testData3() 
    {
    	int numberOfRecords = testData3.length;
    	Double[] expectedSum = prepareRecords(testData3);
    	assertEquals(twap.getOpenSum(), expectedSum[0]);
    	assertEquals(twap.getCloseSum(), expectedSum[1]);
    	assertEquals(twap.getHighSum(), expectedSum[2]);
    	assertEquals(twap.getLowSum(), expectedSum[3]);
    	
    	assertEquals(twap.getTWAPOpen(), expectedSum[0]/numberOfRecords);
    	assertEquals(twap.getTWAPClose(), expectedSum[1]/numberOfRecords);
    	assertEquals(twap.getTWAPHigh(), expectedSum[2]/numberOfRecords);
    	assertEquals(twap.getTWAPLow(), expectedSum[3]/numberOfRecords);
    }
}
