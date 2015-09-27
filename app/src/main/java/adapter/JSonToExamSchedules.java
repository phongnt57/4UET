package adapter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phong on 4/29/2015.
 */
public class JSonToExamSchedules {
    private JSONArray jsons;
    private JSONObject jsonObj;
    private List<JSONArray> array;

    List<FinalExamInfo> listExam;
    public JSonToExamSchedules(JSONObject j)
    {
        this.jsonObj = j;
    }
    public List<FinalExamInfo> getListExam()
    {

        listExam = new ArrayList<FinalExamInfo>();
        array = new ArrayList<JSONArray>();
        try {
            String type = jsonObj.getString("type");
            if(type.equals("success"))

            {
                jsons = jsonObj.getJSONArray("content");


                for (int i = 0; i < jsons.length(); i++)
                {


                    JSONArray subjson_arr = jsons.getJSONArray(i);
                    for (int j = 0; j < subjson_arr.length(); j++) {
                        Log.e("i va j co size :", String.valueOf(subjson_arr.length()));
                        Log.e("lan lap thu  :", String.valueOf(j));
                        JSONObject obj = subjson_arr.getJSONObject(j);
                        String sbd = obj.getString("sbd");
                        String ten_mon = obj.getString("ten_mon_hoc");
                        String ngay_thi = obj.getString("ngay_thi");
                        String gio_thi = obj.getString("gio_thi");
                        String ca_thi = obj.getString("ca_thi");
                        String phong_thi = obj.getString("phong_thi");
                        String hinh_thuc_thi = obj.getString("hinh_thuc_thi");

                        FinalExamInfo f = new FinalExamInfo(sbd, ten_mon, ngay_thi, gio_thi, ca_thi, phong_thi, hinh_thuc_thi);
                        listExam.add(f);


                    }

                }
            }

        }    catch (JSONException e) {
            e.printStackTrace();
        }



        return listExam;
    }
}
