package ex;


import java.util.ArrayList;

public class Interval {

	private double min;
	private double max;
	
	private double M;
	private double s;
	
	public Interval() {}
	
	public void calculateMean (int numberAccesses, int numberDaysAccesses, int numberHours) {
		M = (double)numberAccesses/((numberDaysAccesses+1)*numberHours);
	}
	
	public void calculateS (ArrayList<Integer> list, int numberHours ) {
		Double sum = 0.0;
		for (Integer item: list) {
			double accessesPerHour = (double)item/numberHours;
			sum+=Math.pow((double)accessesPerHour - M, 2);	
		}
		s = Math.sqrt(sum/((list.size())-1));
	}
	
	public void calculateIntervals (int coefficient) {
		min = (double)M-coefficient*s;
		max = (double)M+coefficient*s;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getM() {
		return M;
	}

	public double getS() {
		return s;
	}
}
