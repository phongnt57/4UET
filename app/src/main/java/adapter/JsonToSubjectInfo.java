package adapter;

/**
 * Created by phong on 4/10/2015.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.SubjectInfor;

public class JsonToSubjectInfo {
    private JSONArray jsons;
    private List<JSONArray> array;
    private JSONObject jsonObj;

    List<SubjectInfor> listSubject;
    public JsonToSubjectInfo(JSONObject jsonOb)
    {
        this.jsonObj = jsonOb;
    }
    public List<SubjectInfor> getListSubject()
    {

        listSubject = new ArrayList<SubjectInfor>();
        array = new ArrayList<JSONArray>();
        try {
            String type = jsonObj.getString("type");
            if(type.equals("success"))
            {
                jsons = jsonObj.getJSONArray("content");


                for (int i = 0; i < jsons.length(); i++) {

                    JSONArray subjson_arr = jsons.getJSONArray(i);
                    for (int j = 0; j < subjson_arr.length(); j++) {
                        Log.e("i va j co size :", String.valueOf(subjson_arr.length()));
                        Log.e("lan lap thu  :", String.valueOf(j));
                        JSONObject obj = subjson_arr.getJSONObject(j);
                        String ma_lop = obj.getString("ma_lop");
                        String mssv = obj.getString("ma_sinh_vien");
                        String ghi_chu = obj.getString("ghi_chu");
                        JSONObject thong_tin_lop = obj.getJSONObject("thong_tin_lop");
                        String tiet_bat_dau = thong_tin_lop.getString("tiet_bat_dau");
                        String tiet_ket_thuc = thong_tin_lop.getString("tiet_ket_thuc");
                        String giang_duong = thong_tin_lop.getString("giang_duong");
                        String thu = thong_tin_lop.getString("thu");
                        JSONObject thong_tin_mon = thong_tin_lop.getJSONObject("thong_tin_mon");
                        String ma_mon = thong_tin_mon.getString("ma_mon");
                        String ten_mon = thong_tin_mon.getString("ten_mon");
                        String ma_danh_gia = thong_tin_mon.optString("ma_danh_gia", "");
                        //String ma_danh_gia = thong_tin_mon.getString("001");
                        SubjectInfor sub = new SubjectInfor(ma_lop, tiet_bat_dau, tiet_ket_thuc, giang_duong, thu, ma_mon, ten_mon, ma_danh_gia,
                                mssv, ghi_chu);
                        Log.e("ten:", sub.getTenmon());
                        listSubject.add(sub);

                    }

                }
            }

        }    catch (JSONException e) {
            e.printStackTrace();
        }



        return listSubject;
    }
}