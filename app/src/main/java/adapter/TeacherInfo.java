package adapter;

/**
 * Created by phong on 4/23/2015.
 */
public class TeacherInfo {
    private  String magv;
    private  String tengv;

    public TeacherInfo(String magv, String tengv) {
        this.magv = magv;
        this.tengv = tengv;
    }

    public void setMagv(String magv) {
        this.magv = magv;
    }

    public void setTengv(String tengv) {
        this.tengv = tengv;
    }

    public String getMagv() {
        return magv;
    }

    public String getTengv() {
        return tengv;
    }
}
