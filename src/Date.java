/**
 * This class is built around the date object. This class is used to create a date
 * object in the main program to allow for tangible advancement in time/date
 * as the party/character travels.
 */
public class Date {
    private int currentYear; //Starts in 1848.
    private int currentMonth;
    private int currentDay;
    private final String[] MONTHS = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String date;

    /**
     * Empty default constructor for a date object
     */
    public Date() {
    currentDay = 0;
    currentMonth = 0;
    currentYear = 0;
    }

    /**
     * currentYear getter
     * @return int currentYear - returns the value of currentYear
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /**
     * currentMonth getter
     * @return int currentMonth - returns the value of currentMonth
     */
    public int getCurrentMonth() {
        return currentMonth;
    }

    /**
     * currentDay getter
     * @return int currentDay - returns the value of currentDay
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * currentYear setter
     * @param currentYear - used as the new value of currentYear
     */
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    /**
     * currentMonth setter
     * @param currentMonth - used as the new value of currentMonth
     */
    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    /**
     * currentDay setter
     * @param currentDay - used as the new value of currentDay
     */
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    /**
     * Date object toString method
     * @return String date - prints out the date as Month Day, Year format
     */
    public String toString() {
        date = MONTHS[currentMonth-1] + " " + currentDay + ", " + currentYear;
        return date;
    }

    /**
     * sets all fields of the date object to new parameter values
     * @param month - the new value of currentMonth
     * @param day - the new value of currentDay
     * @param year - the new value of currentYear
     */
    public void setDate(int month, int day, int year) {
        currentMonth = month;
        currentDay = day;
        currentYear = year;
    }


    public void advanceDate() {
        boolean advanceMonth = false;
        boolean advanceYear = false;
        //Advance the days
        //Jan, Mar, May, Jul, Aug, Oct, Dec:
        if (currentMonth == 1 || currentMonth == 3 || currentMonth == 5 || currentMonth == 7 || currentMonth == 8 || currentMonth == 10 || currentMonth == 12) {
            if (currentDay == 31) {
                currentDay = 1;
                advanceMonth = true;
            }
            else {
                currentDay++;
            }
        }
        else if (currentMonth == 4 || currentMonth == 6 || currentMonth == 9 || currentMonth == 11) {
            if (currentDay==30) {
                currentDay=1;
                advanceMonth = true;
            }
            else {
                currentDay++;
            }
        }
        else if (currentMonth == 2) { //In final, implement check for leap year. (Fun fact: It's not just every 4 years! Oh boy!!)
            if (currentDay==28) {
                currentDay =1;
                advanceMonth = true;
            }
            else {
                currentDay++;
            }
        }

        if (advanceMonth == true) {
            //Advance the months
            if (currentMonth == 12) {
                // System.out.println("Date.java: month is " + currentMonth + " and day is " + currentDay);
                advanceYear = true;
            }
            else {
                currentMonth++;
            }
        }

        if (advanceYear == true) {
            currentDay=1;
            currentMonth=1;
            currentYear++;
        }



    }
}

