package adapter;

/**
 * Created by phong on 4/10/2015.
 */
public class SubjectInfor {
    private String malop;
    private String tietbatdau;
    private String tietketthuc;
    private String giangduong;
    private String thu;
    private String mamon;
    private String tenmon;
    private String madanhgia;
    private String mssv;
    private String ghichu;

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public SubjectInfor(String malop, String tietbatdau, String tietketthuc, String giangduong, String thu, String mamon, String tenmon, String madanhgia,
                        String mssv, String ghichu) {
        this.malop = malop;
        this.tietbatdau = tietbatdau;
        this.tietketthuc = tietketthuc;
        this.giangduong = giangduong;
        this.thu = thu;
        this.mamon = mamon;
        this.tenmon = tenmon;
        this.madanhgia = madanhgia;
        this.mssv = mssv;
        this.ghichu = ghichu;
    }

    public String getMalop() {
        return malop;
    }

    public String getTietbatdau() {
        return tietbatdau;
    }

    public String getTietketthuc() {
        return tietketthuc;
    }

    public String getGiangduong() {
        return giangduong;
    }

    public String getThu() {
        return thu;
    }

    public String getMamon() {
        return mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public String getMadanhgia() {
        return madanhgia;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public void setTietbatdau(String tietbatdau) {
        this.tietbatdau = tietbatdau;
    }

    public void setTietketthuc(String tietketthuc) {
        this.tietketthuc = tietketthuc;
    }

    public void setGiangduong(String giangduong) {
        this.giangduong = giangduong;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public void setMadanhgia(String madanhgia) {
        this.madanhgia = madanhgia;
    }
}
