package sentifi.data.models;

public interface MovingAverageModel {
	public void addNewValue(double closeValue);
	public double getAverage();
}
