package sentifi.data.models;

import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SimpleMovingAverageTest extends TestCase {
	private MovingAverageModel sma5 = new SimpleMovingAverage(5, "SMA-5");
	
	// 4 days with open, close, high, low respectively
	private static double[][] testData1 = { { 1.0, 2.0, 3.0, 4.0 }, 		  // day 1
											{ 50.1, 20.2, 31.3, 23.7 },       // day 2
											{ 25.1, 36.2, 24.3, 13.7 },       // day 3
											{ 15.1, 12.2, 13.3, 12.7 },		  // day 4
											{ 10.1, 10.2, 11.3, 13.7 },		  // day 5
											{ 14.1, 15.2, 11.3, 23.7 },		  // day 6
											{ 16.1, 12.2, 13.3, 12.8 },       // day 7
											{ 150.1, 120.2, 131.3, 123.7 },   // day 8
											{ 150.1, 120.2, 131.3, 123.7 },   // day 9
											{ 150.1, 120.2, 131.3, 123.7 },   // day 10
											{ 150.1, 120.2, 131.3, 123.7 }};  // day 11
	private static final double[] correctTestData1 = { 0.0,0.0,0.0,0.0,0.0, 
														18.800000000000004, 17.200000000000003, 
														34.0, 55.6, 77.6, 98.6 };
	
	private static final double[] incorrectTestData1 = { 0.0,0.0,0.0,0.0,0.0, 
														10, 20, 34.0, 55.6, 77.6, 98.6 };
	// 1 day
	private static double[][] testData2 = { { 50.1, 20.2, 31.3, 23.7 } };
	private static final double[] correctTestData2 = {0.0};
	private static final double[] incorrectTestData2 = {1.0};
	
	// 3 days with open, close, high, low respectively
	private static double[][] testData3 = { { 100.1, 68.5, 98.0, 78.0 },  // day 1
											{ 50.1, 20.2, 31.3, 23.7 },   // day 2
											{ 25.1, 36.2, 24.3, 13.7 } }; // day 3
	
	private static final double[] correctTestData3 = {0.0, 0.0, 0.0};
	private static final double[] incorrectTestData3 = {0.0, 0.0, 1.0};


	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleMovingAverageTest ( String testName )
    {
    	super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimpleMovingAverageTest.class );
    }
    
    private double[] prepareRecords(double[][] testData) {
    	double[] sma5Values = new double[testData.length];
    	
    	for (int i = 0; i < testData.length; i++) {
    		sma5.addNewValue(testData[i][1]);
    		sma5Values[i] = sma5.getAverage();
		}
    	return sma5Values;
    }
    
    public void testCorrectSM5Data1() {
    	assertTrue(Arrays.equals(prepareRecords(testData1), correctTestData1));
    }
    
    public void testIncorrectSM5Data1() {
    	assertFalse(Arrays.equals(prepareRecords(testData1), incorrectTestData1));
    }
    
    public void testCorrectSM5Data2() {
    	assertTrue(Arrays.equals(prepareRecords(testData2), correctTestData2));
    }
    
    public void testIncorrectSM5Data2() {
    	assertFalse(Arrays.equals(prepareRecords(testData2), incorrectTestData2));
    }
    
    public void testCorrectSM5Data3() {
    	assertTrue(Arrays.equals(prepareRecords(testData3), correctTestData3));
    }
    
    public void testIncorrectSM5Data3() {
    	assertFalse(Arrays.equals(prepareRecords(testData3), incorrectTestData3));
    }
   

}
