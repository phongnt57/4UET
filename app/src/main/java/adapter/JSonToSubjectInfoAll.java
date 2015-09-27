package adapter;

/**
 * Created by phong on 4/25/2015.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



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

public class JSonToSubjectInfoAll {
    private JSONArray jsons;
    private JSONObject jsonObj;
    //private JSO jsons;
    String mssv;
    private List<JSONArray> array;

    List<SubjectInfor> listSubject;
    public JSonToSubjectInfoAll(JSONObject jsonObj,String mssv)
    {
        this.jsonObj = jsonObj;
        this.mssv = mssv;
    }

    public List<SubjectInfor> getListSubject()
    {


        listSubject = new ArrayList<SubjectInfor>();
        array = new ArrayList<JSONArray>();
        try {
            if(jsonObj.getString("type").equals("success"))
            {
                jsons = jsonObj.getJSONArray("content");


                for (int i = 0; i < jsons.length(); i++) {

                    JSONArray subjson_arr = jsons.getJSONArray(i);
                    for (int j = 0; j < subjson_arr.length(); j++) {
                        Log.e("i va j co size :", String.valueOf(subjson_arr.length()));
                        Log.e("lan lap thu  :", String.valueOf(j));
                        JSONObject obj = subjson_arr.getJSONObject(j);
                        String ma_lop = obj.getString("ma_lop");
                        //String mssv = obj.getString("ma_sinh_vien");
                        String ghi_chu = obj.getString("ghi_chu");
                        //JSONObject thong_tin_lop = obj.getJSONObject("thong_tin_lop");
                        String tiet_bat_dau = obj.getString("tiet_bat_dau");
                        String tiet_ket_thuc = obj.getString("tiet_ket_thuc");
                        String giang_duong = obj.getString("giang_duong");
                        String thu = obj.getString("thu");
                        JSONObject thong_tin_mon = obj.getJSONObject("thong_tin_mon");
                        String ma_mon = thong_tin_mon.getString("ma_mon");
                        String ten_mon = thong_tin_mon.getString("ten_mon");
                        String ma_danh_gia = thong_tin_mon.optString("tin_chi", "001");
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
