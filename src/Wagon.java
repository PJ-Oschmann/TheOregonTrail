public class Wagon {
    private int state = 0; //0=Functional; 1=Damaged; 2=Broken
    private int daysNoRepair = 0;

    public int getState() {
        return state;
    }

    /**
     * Creates a string based on the state of the wagon
     * State of 0 = Functional
     * State of 1 = Damaged (Forced to travel at the lowest speed)
     * State of 2 = Broken. (Lost game)
     * @return the state of the wagon as a String
     */
    public String toString() {
        String strState;
        if (state == 0) {
            strState = "Functional";
        }
        else if (state == 1) {
            strState = "Damaged";
        }
        else {
            strState = "Broken";
        }
        return strState;
    }

    /**
     * Setter for the wagon's state.
     * @param state - The state to set the wagon's state to.
     */
    public void setState(int state) {
        this.state = state;
    }


    /**
     * Setter for daysNoRepair
     * @param days - The number of days without repairing the wagon.
     */
    public void setDaysNoRepair(int days) {
        this.daysNoRepair = days;
    }

    /**
     * Getter for daysNoRepair
     * @return the days without repairing the wagon.
     */
    public int getDaysNoRepair() {
        return daysNoRepair;
    }

    /**
     * Checks if the wagon has been damaged for at least 7 days.
     * If it has, the wagon breaks and the game is lost.
     */
    public void checkIfBroken() {
        if (daysNoRepair >= 7) {
            state = 2;
        }
    }
}