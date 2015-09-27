package adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phong on 4/23/2015.
 */
public class JsonToTeacherInfo {

    private JSONArray jsons;


    List<TeacherInfo> listTeachers;

    public JsonToTeacherInfo(JSONArray jsons)

    {
        this.jsons = jsons;
    }
    public List<TeacherInfo> getListTeachers()
    {

        listTeachers = new ArrayList<TeacherInfo>();

        try {
            for(int i= 0 ; i<jsons.length();i++)
            {
                JSONObject teacher = jsons.getJSONObject(i);



                String ma_gv = teacher.optString("ma_giang_vien"," ");
                String ten_gv = teacher.getString("ho_va_ten");

                TeacherInfo t  = new TeacherInfo(ma_gv,ten_gv);
                listTeachers.add(t);



            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        return listTeachers;
    }
}
