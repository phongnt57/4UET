package adapter;

/**
 * Created by phong on 4/20/2015.
 */
public class EvaluateResult {
    private String id;
    private String ketqua;
    private String commment;
    private String magiangvien;
    private String tengiangvien;
    private String tenmon;
    //private String giangduong;
    private String malop;
    private String ma_sinh_vien;
    private String ma_danh_gia_mon;
    private String ma_danh_gia_lop;

    public EvaluateResult(String id, String ketqua, String commment, String magiangvien, String tengiangvien, String tenmon, String malop, String ma_sinh_vien, String ma_danh_gia_mon, String ma_danh_gia_lop) {
        this.id = id;
        this.ketqua = ketqua;
        this.commment = commment;
        this.magiangvien = magiangvien;
        this.tengiangvien = tengiangvien;
        this.tenmon = tenmon;
        this.malop = malop;
        this.ma_sinh_vien = ma_sinh_vien;
        this.ma_danh_gia_mon = ma_danh_gia_mon;
        this.ma_danh_gia_lop = ma_danh_gia_lop;
    }

    public EvaluateResult( String ketqua, String commment, String magiangvien, String tengiangvien, String tenmon, String malop, String ma_sinh_vien, String ma_danh_gia_mon, String ma_danh_gia_lop) {

        this.ketqua = ketqua;
        this.commment = commment;
        this.magiangvien = magiangvien;
        this.tengiangvien = tengiangvien;
        this.tenmon = tenmon;
        this.malop = malop;
        this.ma_sinh_vien = ma_sinh_vien;
        this.ma_danh_gia_mon = ma_danh_gia_mon;
        this.ma_danh_gia_lop = ma_danh_gia_lop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKetqua() {
        return ketqua;
    }

    public void setKetqua(String ketqua) {
        this.ketqua = ketqua;
    }

    public String getCommment() {
        return commment;
    }

    public void setCommment(String commment) {
        this.commment = commment;
    }

    public String getMagiangvien() {
        return magiangvien;
    }

    public void setMagiangvien(String magiangvien) {
        this.magiangvien = magiangvien;
    }

    public String getTengiangvien() {
        return tengiangvien;
    }

    public void setTengiangvien(String tengiangvien) {
        this.tengiangvien = tengiangvien;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getMa_sinh_vien() {
        return ma_sinh_vien;
    }

    public void setMa_sinh_vien(String ma_sinh_vien) {
        this.ma_sinh_vien = ma_sinh_vien;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getMa_danh_gia_mon() {
        return ma_danh_gia_mon;
    }

    public void setMa_danh_gia_mon(String ma_danh_gia_mon) {
        this.ma_danh_gia_mon = ma_danh_gia_mon;
    }

    public String getMa_danh_gia_lop() {
        return ma_danh_gia_lop;
    }

    public void setMa_danh_gia_lop(String ma_danh_gia_lop) {
        this.ma_danh_gia_lop = ma_danh_gia_lop;
    }
}
