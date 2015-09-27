package adapter;

/**
 * Created by phong on 4/2/2015.
 */
public class Day {
    private String days;
    private String numberclass;

    public Day(String days, String numberclass) {
        this.days = days;
        this.numberclass = numberclass;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {

        return days;
    }
    public void setNumberclass(String nums) {
        this.numberclass = nums;
    }

    public String getNumberclass() {
        return numberclass;
    }
}

