package adapter;

/**
 * Created by phong on 4/20/2015.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.SubjectInfor;

public class JsonToEvaluateResult {
    private JSONArray jsons;
    private JSONObject jsonresult;
    private List<JSONArray> array;

    List<EvaluateResult> listSubject;

    public JsonToEvaluateResult(JSONObject jsonresult)
    {
        this.jsonresult = jsonresult;
    }
    public List<EvaluateResult> getListSubject()
    {

        listSubject = new ArrayList<EvaluateResult>();
        String ketqua = "";

       try {
           if(jsonresult.get("type").equals("success"))
           {
               jsons = jsonresult.getJSONArray("content");
               for (int i = 0; i < jsons.length(); i++) {
                   JSONObject result = jsons.getJSONObject(i);
                   for (int j = 0; j < 19; j++) {
                       int traloi = j + 1;
                       if (j == 18) {
                           ketqua = ketqua + result.get("q" + String.valueOf(traloi));
                       } else {
                           ketqua = ketqua + result.get("q" + String.valueOf(traloi)) + "-";
                       }
                   }
                   String cmt = result.optString("comment");


                   String ma_gv = result.getString("ma_giang_vien");
                   String ten_gv = result.getString("ho_ten_giang_vien");
                   String ten_mon = result.getString("ten_mon_hoc");
                   String ma_lop = result.getString("ma_lop");
                   String mssv = result.getString("ma_sinh_vien");
                   String ma_danh_gia_mon = result.getString("ma_danh_gia_mon");
                   String ma_danh_gia_lop = result.getString("ma_danh_gia_lop");
                   EvaluateResult s = new EvaluateResult(ketqua, cmt, ma_gv, ten_gv, ten_mon, ma_lop, mssv, ma_danh_gia_mon, ma_danh_gia_lop);
                   listSubject.add(s);
                   ketqua = "";


               }
           }

       }catch (JSONException e)
       {
           e.printStackTrace();
       }

        return listSubject;
    }
}
