package sentifi.data.models;

/**
 * Moving average interface.
 * @author nhoanglong
 * @since Dec 03 2017
 * @version 1.0
 */
public interface MovingAverageModel {
	/**
	 * Add new value into moving average model.
	 * @param closeValue
	 */
	public void addNewValue(double closeValue);
	
	/**
	 * Get current moving average model.
	 * @return currentAvg
	 */
	public double getAverage();
	
	/**
	 * Get name of model
	 * @return name
	 */
	public String getName();
}
