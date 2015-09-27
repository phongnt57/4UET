package adapter;

/**
 * Created by phong on 4/2/2015.
 */
public class ClassInDay {
    private String address_class;
    private String name_class;
    private String time_class;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getAddress_class() {
        return address_class;
    }

    public String getName_class() {
        return name_class;
    }

    public String getTime_class() {
        return time_class;
    }

    public ClassInDay(String address_class, String name_class, String time_class) {
        this.address_class = address_class;
        this.name_class = name_class;
        this.time_class = time_class;

    }
    public ClassInDay (String address_class, String name_class){
        this.address_class = address_class;
        this.name_class = name_class;
        //this.time_class = time_class;
    }

    public void setAddress_class(String address_class) {
        this.address_class = address_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public void setTime_class(String time_class) {
        this.time_class = time_class;
    }
}
