package ex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AnomalyDetector {
	
	List<WebPage> webpages = new ArrayList<>();
	List<WebPage> attackedWebpages = new ArrayList<>();
	
	public AnomalyDetector(String filename, String filenameAttacked, String coeff) {
		int coefficient = Integer.parseInt(coeff);
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    String line;
		    if ((line = br.readLine()) == null) {
		    	System.out.println("Invalid input file.");
				System.exit(-1);
		    }
		    
		    // extract names of webpages from training_log.txt
		    extractWebpages(br,webpages);
		    br = new BufferedReader(new FileReader(filename));
		    
		    // collects statistics from training_log.txt
		    collectStatistics("training_log",webpages);
		    
		    // extract names of webpages from test_log.txt
		    br = new BufferedReader(new FileReader(filenameAttacked));
		    extractWebpages(br,attackedWebpages);
		    
		    // calculates the intervals for each day/type of day
		    calculateIntervals(webpages,coefficient);
		    printResults(webpages);
		    
//		    // analyses 
//		    analyse(filenameAttacked);
		} catch (FileNotFoundException e) {
			System.out.println("No such file: " + filename);
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("Invalid input file.");
			System.out.println(e.getMessage());
			
			System.exit(-1);
		}
	}
	// analyse on cyber attacks
	public void analyse(String filenameAttacked) throws IOException, ParseException {
		System.out.println("==========================RESULTS OF ANALYSIS===========================");
		NumberFormat formatter = new DecimalFormat("#0.00"); 
		
		BufferedReader buff = new BufferedReader(new FileReader(filenameAttacked));
		String firstLine = buff.readLine();
		
		for(WebPage webpage : attackedWebpages) {
			System.out.println("\nWebPage name: "+webpage.getName());
	    	String curTime ="00:00:00";
	    	int numberAttacks=0;
	    	String start_date=getDate(firstLine);

	    	BufferedReader br = new BufferedReader(new FileReader(filenameAttacked));
	    	String line;
	    	 while ((line = br.readLine()) != null) {
				    String[] vals = line.split("\"");
			    	String webpage_name=vals[1];
			    	if(webpage.getName().equals(webpage_name)) {
			    		String date = getDate(line);		
			    		String time = getTime(line);
			    		//System.out.println(" ");
			    		if(!(time.subSequence(0, 2)).equals(curTime.subSequence(0, 2))) {
			    		//	System.out.println("Date "+start_date+" "+curTime+" was "+numberAttacks);
			    			for(WebPage cleanWebpage : webpages) {
			    				if(webpage_name.equals(cleanWebpage.getName())) {	
		    						String modifiedTime = curTime.substring(0, 2)+":00:00";
			    					if(isWeekend(start_date)) {
			    						if(isTimeInBetween("05:59:59","08:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getMorningIntervalWeekends().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getMorningIntervalWeekends().getMin())+
					    								" , "+formatter.format(cleanWebpage.getMorningIntervalWeekends().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}	
			    						
			    						if(isTimeInBetween("07:59:59","18:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getDayIntervalWeekends().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getDayIntervalWeekends().getMin())+
					    								" , "+formatter.format(cleanWebpage.getDayIntervalWeekends().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("17:59:59","22:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getEveningIntervalWeekends().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getEveningIntervalWeekends().getMin())+
					    								" , "+formatter.format(cleanWebpage.getEveningIntervalWeekends().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("21:59:59","24:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getNightIntervalWeekends().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getNightIntervalWeekends().getMin())+
					    								" , "+formatter.format(cleanWebpage.getNightIntervalWeekends().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("00:00:00","06:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getNightIntervalWeekends().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getNightIntervalWeekends().getMin())+
					    								" , "+formatter.format(cleanWebpage.getNightIntervalWeekends().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}	
			    					}
			    					else {
			    						
			    						if(isTimeInBetween("05:59:59","08:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getMorningIntervalWeekdays().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getMorningIntervalWeekdays().getMin())+
					    								" , "+formatter.format(cleanWebpage.getMorningIntervalWeekdays().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}	
			    						
			    						if(isTimeInBetween("07:59:59","18:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getDayIntervalWeekdays().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getDayIntervalWeekdays().getMin())+
					    								" , "+formatter.format(cleanWebpage.getDayIntervalWeekdays().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("17:59:59","22:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getEveningIntervalWeekdays().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getEveningIntervalWeekdays().getMin())+
					    								" , "+formatter.format(cleanWebpage.getEveningIntervalWeekdays().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("21:59:59","24:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getNightIntervalWeekdays().getMax()) {
					    						String attackedPage ="["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getNightIntervalWeekdays().getMin())+
					    								" , "+formatter.format(cleanWebpage.getNightIntervalWeekdays().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    						if(isTimeInBetween("00:00:00","06:00:00",curTime))
			    						{
			    							if(numberAttacks>cleanWebpage.getNightIntervalWeekdays().getMax()) {
					    						String attackedPage = "["+start_date+":"+modifiedTime+"] \""+webpage_name+"\" "+numberAttacks+
					    								" ["+formatter.format(cleanWebpage.getNightIntervalWeekdays().getMin())+
					    								" , "+formatter.format(cleanWebpage.getNightIntervalWeekdays().getMax())+"]";
					    						System.out.println(attackedPage);
					    					}
			    						}
			    					}	
			    				}

				    		}
			    			numberAttacks = 0;
			    			curTime = time;
			    			start_date = date;
			    			numberAttacks++;
			    		}
			    		else {
			    			numberAttacks++;
			    		}
			    	}
			 }
	    }
	}
	// extract names of webpages from a file
	public void extractWebpages(BufferedReader br,List<WebPage> array) throws IOException {
		String line;
		 while ((line = br.readLine()) != null) {
			    String[] vals = line.split("\"");
		    	String webpage_name=vals[1];
		    	WebPage webpage = new WebPage(webpage_name);
		    	if(!containsName(array,webpage_name)) {
		    		array.add(webpage);
		    	}
		    }
	}
	
	// calculates number of accesses per each day and per each type of a day
	public void collectStatistics(String filename,List<WebPage> array) throws IOException, ParseException {
		
		BufferedReader buff = new BufferedReader(new FileReader(filename));
		String firstLine = buff.readLine();
		
		String line; 
		for(WebPage webpage : webpages) {
			String currentDateMorningWeekday = getDate(firstLine);
			String currentDateDayWeekday =  getDate(firstLine);
			String currentDateEveningWeekday =  getDate(firstLine);
			String currentDateNightWeekday = getDate(firstLine);
			
			String currentDateMorningWeekends = "06/Oct/2018";
			String currentDateDayWeekends = "06/Oct/2018";
			String currentDateEveningWeekends = "06/Oct/2018";
			String currentDateNightWeekends = "06/Oct/2018";
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
	    	 while ((line = br.readLine()) != null) {
				    String[] vals = line.split("\"");
			    	String webpage_name=vals[1];
			    	if(webpage.getName().equals(webpage_name)) {
			    		String date = getDate(line);		
			    		String time = getTime(line);
			    		
				    	if(isWeekend(date)) {
				    		if(isTimeInBetween("05:59:59","08:00:00",time)) { //weekend morning
					    		webpage.increaseNumberMorningAccessesWeekends();
					    		if(!date.equals(currentDateMorningWeekends)) {
					    			webpage.increaseNumberMorningsWeekends();
					    			webpage.addDateNumberAccessesPerEachDayMoWeekends();
					    			webpage.increaseNumberAccessesPerEachDayMoWeekends();
					    			currentDateMorningWeekends = date;
					    		} else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayMoWeekends();   
				    		}
				    		if(isTimeInBetween("07:59:59","18:00:00",time)) { //weekend day
				    			webpage.increaseNumberDayAccessesWeekends();
					    		if(!date.equals(currentDateDayWeekends)) {
					    			webpage.increaseNumberDaysWeekends();
					    			webpage.addDateNumberAccessesPerEachDayDayWeekends();
					    			webpage.increaseNumberAccessesPerEachDayDayWeekends();
					    			currentDateDayWeekends = date;
					    		} else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayDayWeekends();	
				    		}
					    	
				    		if(isTimeInBetween("17:59:59","22:00:00",time)) {//weekend evening
					    		webpage.increaseNumberEveningAccessesWeekends();
					    		if(!date.equals(currentDateEveningWeekends)) {
					    			webpage.increaseNumberEveningsWeekends();
					    			webpage.addDateNumberAccessesPerEachDayEveWeekends();
					    			webpage.increaseNumberAccessesPerEachDayEveWeekends();
					    			currentDateEveningWeekends = date;
					    		}else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayEveWeekends();	
				    		}

				    		if(isTimeInBetween("21:59:59","24:00:00",time)) {  //weekend night
					    		webpage.increaseNumberNightAccessesWeekends();
					    		if(!date.equals(currentDateNightWeekends)) {
					    			webpage.increaseNumberNightsWeekends();
					    			webpage.addDateNumberAccessesPerEachDayNightWeekends();
					    			webpage.increaseNumberAccessesPerEachDayNightWeekends();
					    			currentDateNightWeekends = date;
					    		}else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayNightWeekends();
				    		}

				    		if(isTimeInBetween("00:00:00","06:00:00",time)) { //weekend night
					    		webpage.increaseNumberNightAccessesWeekends();
					    		if(!date.equals(currentDateNightWeekends)) {
					    			webpage.increaseNumberNightsWeekends();
					    			webpage.addDateNumberAccessesPerEachDayNightWeekends();
					    			webpage.increaseNumberAccessesPerEachDayNightWeekends();
					    			currentDateNightWeekends = date;
					    		}else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayNightWeekends();
				    		}

				    	} else {
				    		if(isTimeInBetween("05:59:59","08:00:00",time)) {  //weekday morning
					    		webpage.increaseNumberMorningAccessesWeekdays();
					    		if(!date.equals(currentDateMorningWeekday)) {
					    			webpage.increaseNumberMorningsWeekdays();
					    			webpage.addDateNumberAccessesPerEachDayMoWeekdays();
					    			webpage.increaseNumberAccessesPerEachDayMoWeekdays();
					    			currentDateMorningWeekday = date;
					    		} else	 					    	
					    			webpage.increaseNumberAccessesPerEachDayMoWeekdays();
				    		}

				    		if(isTimeInBetween("07:59:59","18:00:00",time)) { //weekday day
					    		webpage.increaseNumberDayAccessesWeekdays();
					    		if(!date.equals(currentDateDayWeekday)) {
					    			webpage.increaseNumberDaysWeekdays();
					    			webpage.addDateNumberAccessesPerEachDayDayWeekdays();
					    			webpage.increaseNumberAccessesPerEachDayDayWeekdays();
					    			currentDateDayWeekday = date;
					    		} else
					    			webpage.increaseNumberAccessesPerEachDayDayWeekdays();
				    		}

				    		if(isTimeInBetween("17:59:59","22:00:00",time))  {//weekday evening
					    		webpage.increaseNumberEveningAccessesWeekdays();
					    		if(!date.equals(currentDateEveningWeekday)) {
					    			webpage.increaseNumberEveningsWeekdays();
					    			webpage.addDateNumberAccessesPerEachDayEveWeekdays();
					    			webpage.increaseNumberAccessesPerEachDayEveWeekdays();
					    			currentDateEveningWeekday = date;
					    		} else
					    			webpage.increaseNumberAccessesPerEachDayEveWeekdays();
				    		}

				    		if(isTimeInBetween("21:59:59","24:00:00",time)) {//weekday night
					    		webpage.increaseNumberNightAccessesWeekdays();
					    		if(!date.equals(currentDateNightWeekday)) {
					    			webpage.increaseNumberNightsWeekdays();
					    			webpage.addDateNumberAccessesPerEachDayNightWeekdays();
					    			webpage.increaseNumberAccessesPerEachDayNightWeekdays();
					    			currentDateNightWeekday = date;
					    		}else
					    			webpage.increaseNumberAccessesPerEachDayNightWeekdays();
				    		}
	
				    		if(isTimeInBetween("00:00:00","06:00:00",time)) { //weekday night
				    			webpage.increaseNumberNightAccessesWeekdays();
					    		if(!date.equals(currentDateNightWeekday)) {
					    			webpage.increaseNumberNightsWeekdays();
					    			webpage.addDateNumberAccessesPerEachDayNightWeekdays();
					    			webpage.increaseNumberAccessesPerEachDayNightWeekdays();
					    			currentDateNightWeekday = date;
					    		}else
					    			webpage.increaseNumberAccessesPerEachDayNightWeekdays();
				    		}
				    	}
			    	}
			    }
	        }
	}
	
	// print the results of statistical analysis
	public static void printResults(List<WebPage> webpages) {
		for(WebPage webpage : webpages) {
			 System.out.println("=====================================");
			 System.out.println("Name of Webpage: "+ webpage.getName()+"\n");
			 
			 NumberFormat formatter = new DecimalFormat("#0.00");     
			 
			 System.out.println("Morning Weekdays: ["+ formatter.format(webpage.getMorningIntervalWeekdays().getMin())+" ; "+formatter.format(webpage.getMorningIntervalWeekdays().getMax())+"]");
			 System.out.println("Day Weekdays: ["+ formatter.format(webpage.getDayIntervalWeekdays().getMin())+" ; "+formatter.format(webpage.getDayIntervalWeekdays().getMax())+"]");
			 System.out.println("Evening Weekdays: ["+ formatter.format(webpage.getEveningIntervalWeekdays().getMin())+" ; "+formatter.format(webpage.getEveningIntervalWeekdays().getMax())+"]");
			 System.out.println("Night Weekdays: ["+ formatter.format(webpage.getNightIntervalWeekdays().getMin())+" ; "+formatter.format(webpage.getNightIntervalWeekdays().getMax())+"]");

			 System.out.println("-------------------------------------");

			 System.out.println("Morning Weekends: ["+ formatter.format(webpage.getMorningIntervalWeekends().getMin())+" ; "+formatter.format(webpage.getMorningIntervalWeekends().getMax())+"]");
			 System.out.println("Day Weekends: ["+ formatter.format(webpage.getDayIntervalWeekends().getMin())+" ; "+formatter.format(webpage.getDayIntervalWeekends().getMax())+"]");
			 System.out.println("Evening Weekends: ["+ formatter.format(webpage.getEveningIntervalWeekends().getMin())+" ; "+formatter.format(webpage.getEveningIntervalWeekends().getMax())+"]");
			 System.out.println("Night Weekends: ["+ formatter.format(webpage.getNightIntervalWeekends().getMin())+" ; "+formatter.format(webpage.getNightIntervalWeekends().getMax())+"]\n");

			 System.out.println("Number Morning Accesses Weekdays: "+webpage.getNumberMorningAccessesWeekdays());
			 System.out.println("Number Day Accesses Weekdays: "+webpage.getNumberDayAccessesWeekdays());
			 System.out.println("Number Evening Accesses Weekdays: "+webpage.getNumberEveningAccessesWeekdays());
			 System.out.println("Number Night Accesses Weekdays: "+webpage.getNumberNightAccessesWeekdays()+"\n");
			 
			 System.out.println("Number Mornings: "+webpage.getNumberMorningsWeekdays());
			 System.out.println("Number Days: "+webpage.getNumberDaysWeekdays());
			 System.out.println("Number Evenings: "+webpage.getNumberEveningsWeekdays());
			 System.out.println("Number Nights: "+webpage.getNumberNightsWeekdays()+"\n");
			 
			 System.out.println("Number Moringn Accesses Per Each Day: "+Arrays.toString(webpage.numberAccessesPerEachDayMoWeekdays.toArray()));
			 System.out.println("Number Day Accesses Per Each Day: "+Arrays.toString(webpage.numberAccessesPerEachDayDayWeekdays.toArray()));
			 System.out.println("Number Evening Accesses Per Each Evening: "+Arrays.toString(webpage.numberAccessesPerEachDayEveWeekdays.toArray()));
			 System.out.println("Number Night Accesses Per Each Night: "+Arrays.toString(webpage.numberAccessesPerEachDayNightWeekdays.toArray())+"\n");

			 System.out.println("MEAN Weekdays");
			 System.out.println("Mean Mornings: "+formatter.format(webpage.getMorningIntervalWeekdays().getM()));
			 System.out.println("Mean Days: "+formatter.format(webpage.getDayIntervalWeekdays().getM()));
			 System.out.println("Mean Evenings: "+formatter.format(webpage.getEveningIntervalWeekdays().getM()));
			 System.out.println("Mean Nights: "+formatter.format(webpage.getNightIntervalWeekdays().getM())+"\n");
			 
			 System.out.println("SD Weekdays");
			 System.out.println("S Mornings: "+formatter.format(webpage.getMorningIntervalWeekdays().getS()));
			 System.out.println("S Days: "+formatter.format(webpage.getDayIntervalWeekdays().getS()));
			 System.out.println("S Evenings: "+formatter.format(webpage.getEveningIntervalWeekdays().getS()));
			 System.out.println("S Nights: "+formatter.format(webpage.getNightIntervalWeekdays().getS())+"\n");
			 

			 System.out.println("-------------------------------------");
			 
			 System.out.println("Number Morning Accesses Weekends: "+webpage.getNumberMorningAccessesWeekends());
			 System.out.println("Number Day Accesses Weekends: "+webpage.getNumberDayAccessesWeekends());
			 System.out.println("Number Evening Accesses Weekends: "+webpage.getNumberEveningAccessesWeekends());
			 System.out.println("Number Night Accesses Weekends: "+webpage.getNumberNightAccessesWeekends()+"\n");
			 
			 System.out.println("Number Mornings: "+webpage.getNumberMorningsWeekends());
			 System.out.println("Number Days: "+webpage.getNumberDaysWeekends());
			 System.out.println("Number Evenings: "+webpage.getNumberEveningsWeekends());
			 System.out.println("Number Nights: "+webpage.getNumberNightsWeekends()+"\n");
			 
			 System.out.println("Number Moringn Accesses Per Each Day: "+Arrays.toString(webpage.numberAccessesPerEachDayMoWeekends.toArray()));
			 System.out.println("Number Day Accesses Per Each Day: "+Arrays.toString(webpage.numberAccessesPerEachDayDayWeekends.toArray()));
			 System.out.println("Number Moringn Accesses Per Each Evening: "+Arrays.toString(webpage.numberAccessesPerEachDayEveWeekends.toArray()));
			 System.out.println("Number Moringn Accesses Per Each Night: "+Arrays.toString(webpage.numberAccessesPerEachDayNightWeekends.toArray())+"\n");
			 
			 System.out.println("MEAN Weekends");
			 System.out.println("Mean Mornings: "+formatter.format(webpage.getMorningIntervalWeekends().getM()));
			 System.out.println("Mean Days: "+formatter.format(webpage.getDayIntervalWeekends().getM()));
			 System.out.println("Mean Evenings: "+formatter.format(webpage.getEveningIntervalWeekends().getM()));
			 System.out.println("Mean Nights: "+formatter.format(webpage.getNightIntervalWeekends().getM())+"\n");
			
			 System.out.println("SD Weekends");
			 System.out.println("S Mornings: "+formatter.format(webpage.getMorningIntervalWeekends().getS()));
			 System.out.println("S Days: "+formatter.format(webpage.getDayIntervalWeekends().getS()));
			 System.out.println("S Evenings: "+formatter.format(webpage.getEveningIntervalWeekends().getS()));
			 System.out.println("S Nights: "+formatter.format(webpage.getNightIntervalWeekends().getS())+"\n");
			 
			 System.out.println("=====================================");
		}	
	}
	
	// calculates min and max intervals for each webpage
	public static void calculateIntervals(List<WebPage> webpages, int coefficient) {
		for(WebPage webpage : webpages) {
			 webpage.calculateMeans();
			 webpage.calculateS();
			 webpage.calculateIntervals(coefficient);
		}
	}
	// returns time in format hh:mm:ss
	public static String getTime(String line) {
		String [] vals = line.split("\\[");
    	String[] date_vals = vals[1].split("\\]");
    	String [] date = date_vals[0].split(":");
    	return date[1] + ":" + date[2]+ ":" + date[3].split(" ")[0];
	}
	// returns date in format DD/Mmm/YYYY
	public static String getDate(String line) {
		String [] vals = line.split("\\[");
    	String[] date_vals = vals[1].split("\\]");
    	String [] date = date_vals[0].split(":");
    	return date[0];
	}
	// returns month number from a short month name
	public static int getMonthNumber(String monthName) {
		Calendar cal =Calendar.getInstance();
	    Map<String, Integer> months= cal.getDisplayNames(Calendar.MONTH, Calendar.SHORT, Locale.US);
    	return months.get(monthName).intValue()+1;
	}
	
	// returns true if the date is weekend
	public static boolean isWeekend(String date) throws ParseException
	{
	    int year = Integer.parseInt(date.substring(7, 11));
	    int month =getMonthNumber(date.substring(3, 6));
	    int day = Integer.parseInt(date.substring(0, 2));
	    Calendar cal = new GregorianCalendar(year, month - 1, day);
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    return (Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek);
	}
	
	// returns true if the time of webpage's access was between startPeriod and endPeriod
	public boolean isTimeInBetween(String startPeriod, String endPeriod, String timeOfAccess) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
		Date start = parser.parse(startPeriod);
		Date end = parser.parse(endPeriod);

		try {
		    Date date = parser.parse(timeOfAccess);
		    if (date.after(start) && date.before(end)) {
		    	return true;
		    }
		} catch (ParseException e) {
		    // Invalid date was entered
			System.out.println(e);
		}
		return false;
	}

	// returns true if the List<WebPage> contains a WebPage with the name name
	public boolean containsName(final List<WebPage> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	public static void currentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
	 }
	// Main method 
	public static void main(String[] args) throws IOException, ParseException {
		currentTime();
		// Validate arguments
		if (args.length != 3) {
			System.out.println("Provide input file names as well as outlier coefficient as the three command-line parameters.");
			System.exit(-1);
		}
		// Instantiate detector
		AnomalyDetector detector = new AnomalyDetector(args[0], args[1], args[2]);
		
		// Perform analysis
		detector.analyse(args[1]);
		currentTime();
	}
}

