package io.codly.Uetface.controller;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;

import adapter.CheckNetworkConnection;
import adapter.ClassInDay;
import adapter.DatabaseHandler;
import adapter.EvaluateResult;
import adapter.JSONParser;
import adapter.JsonToEvaluateResult;
import adapter.JsonToSubjectInfo;
import adapter.JsonToTeacherInfo;
import adapter.SubjectEvaluateListAdapter;
import adapter.SubjectInfor;
import adapter.TeacherInfo;
import adapter.Var;
import io.codly.Uetface.R;
import viewpager.ViewPage;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Evaluate extends Activity implements
        AdapterView.OnItemClickListener {
    ListView listView;
    List<ClassInDay> rowItems;
    List<SubjectInfor> list = new ArrayList<SubjectInfor>();
    DatabaseHandler db;
    getSubject getSubjectTask = new getSubject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_evaluate);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.evaluate_actionbar);
		//initData();
        rowItems = new ArrayList<ClassInDay>();
        /*ClassInDay class1 = new ClassInDay("3-G3","Nguyên lý cơ bản chủ nghĩa Mác - Lê Hói");
        ClassInDay class2 = new ClassInDay("233-GD3","Cơ sở dữ liệu phân tán");
        ClassInDay class3 = new ClassInDay("303-GD2","Hệ quản trị cơ sở dữ liệu");
        rowItems.add(class1);
        rowItems.add(class2);
        rowItems.add(class3);*/
        initData();
        listView = (ListView) findViewById(R.id.list_subjectevaluate);
        SubjectEvaluateListAdapter adapter = new SubjectEvaluateListAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        /*Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ViewPage.class);
                startActivity(intent);

            }
        });*/
	}

    private void initData(){
        //TODO add extra view initialization code here
        db = new DatabaseHandler(this);

        list = db.getTimeTable();
        for(int i=0;i<list.size();i++)
        {
            ClassInDay class1 = new ClassInDay(list.get(i).getMalop(),list.get(i).getTenmon());
            class1.setId(i);
            rowItems.add(class1);

        }
       /*ClassInDay class1 = new ClassInDay("3G3","Nguyên lý cơ bản chủ nghĩa Mác - Lê Hói");
       ClassInDay class2 = new ClassInDay("233GD3","Cơ sở dữ liệu phân tán");
       ClassInDay class3 = new ClassInDay("303GD2","Hệ quản trị cơ sở dữ liệu");
       rowItems.add(class1);
       rowItems.add(class2);
       rowItems.add(class3);*/
    }

    
    public void onAdd_evaluateClicked(View v){
        Intent intent = new Intent(this, ViewPage.class);
        startActivity(intent);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String stu_ids = list.get(position).getMssv();
        String subjects = list.get(position).getTenmon();
        String cla_ids  =list.get(position).getMalop();
        String cla_codes  =list.get(position).getMadanhgia();
        if(cla_codes.equals(""))
        {

            AlertDialog a = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.appicon).setTitle("Chưa có dữ liệu về MĐG")
                    .setMessage("Tải lại dữ liệu?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if(getSubjectTask.getStatus() ==AsyncTask.Status.RUNNING)


                            db.resetTeacher();
                            db.resetTimetable();
                            getSubjectTask.execute();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                        }
                    }).show();
            a.setCanceledOnTouchOutside(true);
        }
        else {
            Intent i = new Intent(getApplication(), TeacherInput.class);
            i.putExtra("stu_id", list.get(position).getMssv());
            i.putExtra("subject", rowItems.get(position).getName_class());
            i.putExtra("cla_id", rowItems.get(position).getAddress_class());
            //i.putExtra("id", rowItems.get(position).getId());
            i.putExtra("cla_code", list.get(position).getMadanhgia());
            startActivity(i);
        }
    }




    class getSubject extends AsyncTask<Void, Objects, List<SubjectInfor>> {

        JSONObject json;
        ProgressDialog dialog;
        String res;
        String type;
        List<SubjectInfor> listSubject;
        List<TeacherInfo> listTeacher;
        boolean ok = true;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Evaluate.this,"","Loading...");
            // dialog = ProgressDialog.show(getApplication(), "Loading","Loading", true);
        }


        @Override
        protected List<SubjectInfor> doInBackground(Void... params) {
            CheckNetworkConnection check = new CheckNetworkConnection();
            listSubject = new ArrayList<SubjectInfor>();
            if(check.isConnectedToServer(Var.host,Var.ping)) {


                //db = new DatabaseHandler(getActivity().getApplicationContext());
                String token = db.getToken();
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("tag", login_tag));
                param.add(new BasicNameValuePair("token", token));

                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/find_class";
                json = jsonParser.postJSONFromUrl(url, param);
                try
                {
                    type = json.getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonToSubjectInfo parse = new JsonToSubjectInfo(json);

                listSubject = parse.getListSubject();

                for (int k = 0; k < listSubject.size(); k++)
                    Log.e("list:", k + listSubject.get(k).getTenmon() + " " + listSubject.get(k).getGiangduong());
                if (listSubject.size() > 0) {
                    for (int i = 0; i < listSubject.size(); i++)
                        db.addTimeTable(listSubject.get(i));
                }
                if(type.equals("success"))
                {
                    pushTeacherInfoToDB();
                }



                //pushTeacherInfoToDB();
                // return json
                Log.e("JSON object :", json.toString());

            }
            else
            {
                ok = false;
            }







            return listSubject;


        }



        @Override
        protected void onPostExecute(List<SubjectInfor> result) {

            dialog.dismiss();
            if(ok)
            {
                if(type.equals(Var.TOKEN_ERROR))
                {
                    db.resetTables();
                    Intent intent = new Intent(Evaluate.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else if(type.equals(Var.TYPE_SUCCESS))
                {

                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(), "Đã refresh thành công", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Không thể refresh", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
            }


            super.onPostExecute(result);
            // dialog.dismiss();
        }


        public void   pushTeacherInfoToDB()
        {
            String url = Var.host + "/api/findteacher/";
            JSONParser j = new JSONParser();
            JSONArray jsons = j.getJSONFromUrlToArray(url);
            Log.e("teacher :", jsons.toString());
            listTeacher = new ArrayList<TeacherInfo>();
            JsonToTeacherInfo t = new JsonToTeacherInfo(jsons);
            listTeacher = t.getListTeachers();
            Log.e("so gv",String.valueOf(listTeacher.size()));
            for(int i = 0 ; i<listTeacher.size();i++)
            {
                db.addTeacher(listTeacher.get(i));
                Log.e("mgv",listTeacher.get(i).getMagv());
                Log.e("tgv",listTeacher.get(i).getTengv());

            }



        }
    }


}
