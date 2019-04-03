package ex;

import java.util.ArrayList;

public class WebPage {
	private String name;

	//the number of hourly webpage accesses for each time of day/type of day combinations
	private int numberMorningAccessesWeekdays;
	private int numberDayAccessesWeekdays;
	private int numberEveningAccessesWeekdays;
	private int numberNightAccessesWeekdays;
	
	private int numberMorningAccessesWeekends;
	private int numberDayAccessesWeekends;
	private int numberEveningAccessesWeekends;
	private int numberNightAccessesWeekends;
	
	//the number of accesses' days for each time of day/type of day combinations
	private int numberMorningsWeekdays;
	private int numberDaysWeekdays;
	private int numberEveningsWeekdays;
	private int numberNightsWeekdays;
	
	private int numberMorningsWeekends;
	private int numberDaysWeekends;
	private int numberEveningsWeekends;
	private int numberNightsWeekends;
	
	//the number of accesses' days for each time of day/type of day combinations
	public ArrayList<Integer> numberAccessesPerEachDayMoWeekdays; 
	public ArrayList<Integer> numberAccessesPerEachDayDayWeekdays; 
	public ArrayList<Integer> numberAccessesPerEachDayNightWeekdays; 
	public ArrayList<Integer> numberAccessesPerEachDayEveWeekdays; 
	
	public ArrayList<Integer> numberAccessesPerEachDayMoWeekends; 
	public ArrayList<Integer> numberAccessesPerEachDayDayWeekends; 
	public ArrayList<Integer> numberAccessesPerEachDayNightWeekends; 
	public ArrayList<Integer> numberAccessesPerEachDayEveWeekends; 

	//the number of accesses' days for each time of day/type of day combinations
	private Interval morningIntervalWeekdays;
	private Interval dayIntervalWeekdays;
	private Interval eveningIntervalWeekdays;
	private Interval nightIntervalWeekdays;
	
	private Interval morningIntervalWeekends;
	private Interval dayIntervalWeekends;
	private Interval eveningIntervalWeekends;
	private Interval nightIntervalWeekends;
	
	public WebPage(String namePage){
		name=namePage;
		
		numberMorningAccessesWeekdays = 0;
		numberDayAccessesWeekdays = 0;
		numberEveningAccessesWeekdays = 0;
		numberNightAccessesWeekdays = 0;
		
		numberMorningAccessesWeekends = 0;
		numberDayAccessesWeekends = 0;
		numberEveningAccessesWeekends = 0;
		numberNightAccessesWeekends = 0;	
		
		numberMorningsWeekdays = 0;
		numberDaysWeekdays = 0;
		numberEveningsWeekdays = 0;
		numberNightsWeekdays = 0;
		
		numberMorningsWeekends = 0;
		numberDaysWeekends = 0;
		numberEveningsWeekends = 0;
		numberNightsWeekends = 0;
		
		numberAccessesPerEachDayMoWeekdays = new ArrayList<Integer>();
		numberAccessesPerEachDayMoWeekdays.add(0);
		numberAccessesPerEachDayDayWeekdays = new ArrayList<Integer>();
		numberAccessesPerEachDayDayWeekdays.add(0);
		numberAccessesPerEachDayEveWeekdays = new ArrayList<Integer>();
		numberAccessesPerEachDayEveWeekdays.add(0);
		numberAccessesPerEachDayNightWeekdays = new ArrayList<Integer>();
		numberAccessesPerEachDayNightWeekdays.add(0);
		
		numberAccessesPerEachDayMoWeekends = new ArrayList<Integer>();
		numberAccessesPerEachDayMoWeekends.add(0);
		numberAccessesPerEachDayDayWeekends = new ArrayList<Integer>();
		numberAccessesPerEachDayDayWeekends.add(0);
		numberAccessesPerEachDayEveWeekends = new ArrayList<Integer>();
		numberAccessesPerEachDayEveWeekends.add(0);
		numberAccessesPerEachDayNightWeekends = new ArrayList<Integer>();
		numberAccessesPerEachDayNightWeekends.add(0);

		morningIntervalWeekdays = new Interval();
		dayIntervalWeekdays = new Interval();
		eveningIntervalWeekdays = new Interval();
		nightIntervalWeekdays = new Interval();
		
		morningIntervalWeekends = new Interval();
		dayIntervalWeekends = new Interval();
		eveningIntervalWeekends = new Interval();
		nightIntervalWeekends = new Interval();
	}
	
	
	public void calculateMeans() {
		morningIntervalWeekdays.calculateMean(numberMorningAccessesWeekdays, numberMorningsWeekdays, 2);
		dayIntervalWeekdays.calculateMean(numberDayAccessesWeekdays, numberDaysWeekdays, 10);
		eveningIntervalWeekdays.calculateMean(numberEveningAccessesWeekdays, numberEveningsWeekdays, 4);
		nightIntervalWeekdays.calculateMean(numberNightAccessesWeekdays, numberNightsWeekdays, 8);
		
		morningIntervalWeekends.calculateMean(numberMorningAccessesWeekends, numberMorningsWeekends, 2);
		dayIntervalWeekends.calculateMean(numberDayAccessesWeekends, numberDaysWeekends, 10);
		eveningIntervalWeekends.calculateMean(numberEveningAccessesWeekends, numberEveningsWeekends, 4);
		nightIntervalWeekends.calculateMean(numberNightAccessesWeekends, numberNightsWeekends, 8);
	}
	
	public void calculateS() {
		morningIntervalWeekdays.calculateS(numberAccessesPerEachDayMoWeekdays,2);
		dayIntervalWeekdays.calculateS(numberAccessesPerEachDayDayWeekdays,10);
		eveningIntervalWeekdays.calculateS(numberAccessesPerEachDayEveWeekdays,4);
		nightIntervalWeekdays.calculateS(numberAccessesPerEachDayNightWeekdays,8);
		
		morningIntervalWeekends.calculateS(numberAccessesPerEachDayMoWeekends,2);
		dayIntervalWeekends.calculateS(numberAccessesPerEachDayDayWeekends,10);
		eveningIntervalWeekends.calculateS(numberAccessesPerEachDayEveWeekends,4);
		nightIntervalWeekends.calculateS(numberAccessesPerEachDayNightWeekends,8);
	}
	
	public void calculateIntervals(int coefficient) {
		morningIntervalWeekdays.calculateIntervals(coefficient);
		dayIntervalWeekdays.calculateIntervals(coefficient);
		eveningIntervalWeekdays.calculateIntervals(coefficient);
		nightIntervalWeekdays.calculateIntervals(coefficient);
		
		morningIntervalWeekends.calculateIntervals(coefficient);
		dayIntervalWeekends.calculateIntervals(coefficient);
		eveningIntervalWeekends.calculateIntervals(coefficient);
		nightIntervalWeekends.calculateIntervals(coefficient);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void increaseNumberMorningsWeekdays() {
		numberMorningsWeekdays++;
	}
	
	public void increaseNumberDaysWeekdays() {
		numberDaysWeekdays++;
	}
	public void increaseNumberEveningsWeekdays() {
		numberEveningsWeekdays++;
	}
	
	public void increaseNumberNightsWeekdays() {
		numberNightsWeekdays++;
	}
	public void increaseNumberMorningsWeekends() {
		numberMorningsWeekends++;
	}
	
	public void increaseNumberDaysWeekends() {
		numberDaysWeekends++;
	}
	public void increaseNumberEveningsWeekends() {
		numberEveningsWeekends++;
	}
	
	public void increaseNumberNightsWeekends() {
		numberNightsWeekends++;
	}
	
	public void increaseNumberMorningAccessesWeekdays() {
		numberMorningAccessesWeekdays++;
	}
	
	public void increaseNumberDayAccessesWeekdays() {
		numberDayAccessesWeekdays++;
	}
	public void increaseNumberEveningAccessesWeekdays() {
		numberEveningAccessesWeekdays++;
	}
	
	public void increaseNumberNightAccessesWeekdays() {
		numberNightAccessesWeekdays++;
	}
	public void increaseNumberMorningAccessesWeekends() {
		numberMorningAccessesWeekends++;
	}
	
	public void increaseNumberDayAccessesWeekends() {
		numberDayAccessesWeekends++;
	}
	public void increaseNumberEveningAccessesWeekends() {
		numberEveningAccessesWeekends++;
	}
	
	public void increaseNumberNightAccessesWeekends() {
		numberNightAccessesWeekends++;
	}
	
	public void addDateNumberAccessesPerEachDayMoWeekdays() {
		numberAccessesPerEachDayMoWeekdays.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayMoWeekdays() {
		int numberOfAccesses=numberAccessesPerEachDayMoWeekdays.get(numberAccessesPerEachDayMoWeekdays.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayMoWeekdays.set(numberAccessesPerEachDayMoWeekdays.size()-1, numberOfAccesses);
	}
	
	public void addDateNumberAccessesPerEachDayMoWeekends() {
		numberAccessesPerEachDayMoWeekends.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayMoWeekends() {
		int numberOfAccesses=numberAccessesPerEachDayMoWeekends.get(numberAccessesPerEachDayMoWeekends.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayMoWeekends.set(numberAccessesPerEachDayMoWeekends.size()-1, numberOfAccesses);
	}
	public void addDateNumberAccessesPerEachDayDayWeekdays() {
		numberAccessesPerEachDayDayWeekdays.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayDayWeekdays() {
		int numberOfAccesses=numberAccessesPerEachDayDayWeekdays.get(numberAccessesPerEachDayDayWeekdays.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayDayWeekdays.set(numberAccessesPerEachDayDayWeekdays.size()-1, numberOfAccesses);
	}	
	public void addDateNumberAccessesPerEachDayDayWeekends() {
		numberAccessesPerEachDayDayWeekends.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayDayWeekends() {
		int numberOfAccesses=numberAccessesPerEachDayDayWeekends.get(numberAccessesPerEachDayDayWeekends.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayDayWeekends.set(numberAccessesPerEachDayDayWeekends.size()-1, numberOfAccesses);
	}
	public void addDateNumberAccessesPerEachDayEveWeekdays() {
		numberAccessesPerEachDayEveWeekdays.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayEveWeekdays() {
		int numberOfAccesses=numberAccessesPerEachDayEveWeekdays.get(numberAccessesPerEachDayEveWeekdays.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayEveWeekdays.set(numberAccessesPerEachDayEveWeekdays.size()-1, numberOfAccesses);
	}	
	public void addDateNumberAccessesPerEachDayEveWeekends() {
		numberAccessesPerEachDayEveWeekends.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayEveWeekends() {
		int numberOfAccesses=numberAccessesPerEachDayEveWeekends.get(numberAccessesPerEachDayEveWeekends.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayEveWeekends.set(numberAccessesPerEachDayEveWeekends.size()-1, numberOfAccesses);
	}	
	public void addDateNumberAccessesPerEachDayNightWeekdays() {
		numberAccessesPerEachDayNightWeekdays.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayNightWeekdays() {
		int numberOfAccesses=numberAccessesPerEachDayNightWeekdays.get(numberAccessesPerEachDayNightWeekdays.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayNightWeekdays.set(numberAccessesPerEachDayNightWeekdays.size()-1, numberOfAccesses);
	}
	public void addDateNumberAccessesPerEachDayNightWeekends() {
		numberAccessesPerEachDayNightWeekends.add(0);
	}
	
	public void increaseNumberAccessesPerEachDayNightWeekends() {
		int numberOfAccesses=numberAccessesPerEachDayNightWeekends.get(numberAccessesPerEachDayNightWeekends.size()-1);
		numberOfAccesses++;
		numberAccessesPerEachDayNightWeekends.set(numberAccessesPerEachDayNightWeekends.size()-1, numberOfAccesses);
	}
	public int getNumberEveningAccessesWeekdays() {
		return numberEveningAccessesWeekdays;
	}
	
	public int getNumberMorningAccessesWeekdays() {
		return numberMorningAccessesWeekdays;
	}

	public int getNumberDayAccessesWeekdays() {
		return numberDayAccessesWeekdays;
	}

	public int getNumberNightAccessesWeekdays() {
		return numberNightAccessesWeekdays;
	}

	public int getNumberMorningAccessesWeekends() {
		return numberMorningAccessesWeekends;
	}

	public int getNumberDayAccessesWeekends() {
		return numberDayAccessesWeekends;
	}

	public int getNumberEveningAccessesWeekends() {
		return numberEveningAccessesWeekends;
	}

	public int getNumberNightAccessesWeekends() {
		return numberNightAccessesWeekends;
	}
	
	public int getNumberMorningsWeekdays() {
		return numberMorningsWeekdays;
	}

	public int getNumberDaysWeekdays() {
		return numberDaysWeekdays;
	}

	public int getNumberEveningsWeekdays() {
		return numberEveningsWeekdays;
	}

	public int getNumberNightsWeekdays() {
		return numberNightsWeekdays;
	}

	public int getNumberMorningsWeekends() {
		return numberMorningsWeekends;
	}

	public int getNumberDaysWeekends() {
		return numberDaysWeekends;
	}

	public int getNumberEveningsWeekends() {
		return numberEveningsWeekends;
	}

	public int getNumberNightsWeekends() {
		return numberNightsWeekends;
	}
	public Interval getMorningIntervalWeekdays() {
		return morningIntervalWeekdays;
	}

	public Interval getDayIntervalWeekdays() {
		return dayIntervalWeekdays;
	}

	public Interval getEveningIntervalWeekdays() {
		return eveningIntervalWeekdays;
	}

	public Interval getNightIntervalWeekdays() {
		return nightIntervalWeekdays;
	}

	public Interval getMorningIntervalWeekends() {
		return morningIntervalWeekends;
	}

	public Interval getDayIntervalWeekends() {
		return dayIntervalWeekends;
	}

	public Interval getEveningIntervalWeekends() {
		return eveningIntervalWeekends;
	}

	public Interval getNightIntervalWeekends() {
		return nightIntervalWeekends;
	}
}
