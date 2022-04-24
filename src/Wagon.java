public class Wagon {
    private int state = 0; //0=Functional; 1=Damaged; 2=Broken

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}