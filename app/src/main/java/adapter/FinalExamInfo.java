package adapter;

/**
 * Created by phong on 4/29/2015.
 */
public class FinalExamInfo {
            String sbd;
            String mon_hoc;
            String ngay_thi;
            String gio_thi;
            String ca_thi;
            String phong_thi;
            String hinh_thuc_thi;

    public FinalExamInfo(String sbd, String mon_hoc, String ngay_thi, String gio_thi, String ca_thi, String phong_thi,  String hinh_thuc_thi) {
        this.sbd = sbd;
        this.mon_hoc = mon_hoc;
        this.ngay_thi = ngay_thi;
        this.gio_thi = gio_thi;
        this.ca_thi = ca_thi;
        this.phong_thi = phong_thi;

        this.hinh_thuc_thi = hinh_thuc_thi;
    }

    public String getNgay_thi() {
        return ngay_thi;
    }

    public void setNgay_thi(String ngay_thi) {
        this.ngay_thi = ngay_thi;
    }

    public String getMon_hoc() {
        return mon_hoc;
    }

    public void setMon_hoc(String mon_hoc) {
        this.mon_hoc = mon_hoc;
    }

    public String getSbd() {
        return sbd;
    }

    public void setSbd(String sbd) {
        this.sbd = sbd;
    }

    public String getCa_thi() {
        return ca_thi;
    }

    public void setCa_thi(String ca_thi) {
        this.ca_thi = ca_thi;
    }

    public String getGio_thi() {
        return gio_thi;
    }

    public void setGio_thi(String gio_thi) {
        this.gio_thi = gio_thi;
    }

    public String getPhong_thi() {
        return phong_thi;
    }

    public void setPhong_thi(String phong_thi) {
        this.phong_thi = phong_thi;
    }



    public String getHinh_thuc_thi() {
        return hinh_thuc_thi;
    }

    public void setHinh_thuc_thi(String hinh_thuc_thi) {
        this.hinh_thuc_thi = hinh_thuc_thi;
    }
}
