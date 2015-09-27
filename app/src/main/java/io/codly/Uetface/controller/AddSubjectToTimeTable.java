package io.codly.Uetface.controller;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.CheckNetworkConnection;
import adapter.DatabaseHandler;
import adapter.JSONParser;
import adapter.JsonToSubjectInfo;
import adapter.SubjectInfor;
import adapter.Var;
import customAutocompleteTextView.CustomAutoCompleteTextChangedListener;
import customAutocompleteTextView.CustomAutoCompleteView;
import io.codly.Uetface.R;

public class AddSubjectToTimeTable extends ActionBarActivity {
    public CustomAutoCompleteView tenmon;
    public EditText thu;
    public EditText tiet;
    public EditText ma_lop;
    public EditText phong;
    public ArrayAdapter<String> adapter;
    public List<String> hint = new ArrayList<String>();
    public DatabaseHandler db ;
    public  List<SubjectInfor> list_subject = new ArrayList<SubjectInfor>();
    SubjectInfor addsubject = new SubjectInfor("","","","","","","","","","");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_add_subject_to_time_table);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getActionBar().setCustomView(R.layout.timetable_actionbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("THÊM MÔN HỌC ");

        thu = (EditText)findViewById(R.id.thu);
        tiet = (EditText)findViewById(R.id.tiet);
        ma_lop = (EditText)findViewById(R.id.malop);
        phong = (EditText)findViewById(R.id.phong);


        db = new DatabaseHandler(this);
        try{
        //hint.add("Tìm kiếm môn học");
        tenmon = (CustomAutoCompleteView)findViewById(R.id.tenmon);
        tenmon.setThreshold(1);
        tenmon.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

        // set our adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, hint);
        tenmon.setAdapter(adapter);

    } catch (NullPointerException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
       tenmon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Log.e("size of i:",String.valueOf(i));
               addsubject = list_subject.get(i);
               String a = list_subject.get(i).getTenmon();
               thu.setText("Thứ:"+addsubject.getThu());
               ma_lop.setText(addsubject.getMalop());

               tiet.setText("Tiết:"+addsubject.getTietbatdau()+" - "+addsubject.getTietketthuc());
               phong.setText("Giảng đường:"+addsubject.getGiangduong());
               tenmon.setText(addsubject.getTenmon());

           }
       });

        Button them = (Button)findViewById(R.id.them);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tenmon.getText().toString().equals("") || ma_lop.getText().toString().equals("")
                        || tiet.getText().toString().equals("") ||phong.getText().toString().equals("") ||
                        thu.getText().equals("") )
                {
                    Toast.makeText(getApplicationContext(),
                            "Chưa nhập thông tin môn ", Toast.LENGTH_LONG)
                            .show();
                }
                else
                {
                    validateAddSubjectToTimeTable(addsubject);

                }

            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_subject_to_time_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public List<SubjectInfor> getList_Subject(String searchTerm)
    {
        //list_subject.clear();

        return list_subject;
    }
    public List<String> getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        list_subject = db.searchSubjectInAllSubject(searchTerm);
        int rowCount = list_subject.size();
        //hint.clear();
        List<String> temp = new ArrayList<String>();

        for(int i = 0 ;i<rowCount;i++)
            temp.add(list_subject.get(i).getTenmon()+ "-T"+ list_subject.get(i).getThu());

        hint = temp;



        return hint;
    }

    public void validateAddSubjectToTimeTable(SubjectInfor sub)
    {
        boolean check = true;
        List<SubjectInfor> timetable = db.getTimeTable();
        for(int i = 0 ;i<timetable.size();i++)
        {
            String tenmon = timetable.get(i).getTenmon();
            Log.e("ten mon:",tenmon);
            String tietbatdau = timetable.get(i).getTietbatdau();
            String tietketthuc = timetable.get(i).getTietketthuc();
            String thu = timetable.get(i).getThu();
            String giangduong = timetable.get(i).getGiangduong();
            String mamon = timetable.get(i).getMamon();
            String ghichu = timetable.get(i).getGhichu();

            int start = Integer.parseInt(tietbatdau);
            int end = Integer.parseInt(tietketthuc);
            //int day = Integer.parseInt(thu);
            int addstart = Integer.parseInt(sub.getTietbatdau());
            int addend = Integer.parseInt(sub.getTietketthuc());
            if(tenmon.equals(sub.getTenmon()) && giangduong.equals(sub.getGiangduong()) )
            {
               check = false;
                break;
            }
            else if(thu.equals(sub.getThu()) && addstart>=start && addstart<end)
            {
               check = false;
                break;

            }
            else if(thu.equals(sub.getThu()) && addend>start && addend<=end)
            {
               check = false;
                break;

            }
            else  if(thu.equals(sub.getThu())&& addstart<start && addend>end)
            {
                check = false;
                break;

            }
            else if(mamon.equals(sub.getMamon()) && ghichu.equals(sub.getGhichu()))
            {
                check = false;
                break;
            }
            Log.e("check: ",tenmon + ":"+String.valueOf(check));



        }
        if(!check)
        {
            Toast.makeText(getApplicationContext(),
                    "Môn học đã bị trùng!",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            new updateSubjectToServer().execute(sub);
        }

    }
    class updateSubjectToServer extends AsyncTask<SubjectInfor,Void,Void>
    {
        JSONObject json;
        boolean ok = true;
        ProgressDialog dialog;
        String type;
        String content;
        public updateSubjectToServer() {
            super();
        }

        @Override
        protected Void doInBackground(SubjectInfor... voids) {
            SubjectInfor s = voids[0];
            CheckNetworkConnection check = new CheckNetworkConnection();
            if(check.isConnectedToServer(Var.host,1000)) {
                // Log.e("net",String.valueOf(check.isConnectionAvailable(getApplicationContext())));

                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("class_id", s.getMalop()));
                param.add(new BasicNameValuePair("ghi_chu", s.getGhichu()));
                param.add(new BasicNameValuePair("token", db.getToken()));
                Log.e("add subject",s.getMalop()+" "+s.getGhichu()+ " "+db.getToken());
                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/add_class";
                json = jsonParser.postJSONFromUrl(url, param);
                try {
                    type = json.getString("type");
                    content = json.getString("content");
                    if(type.equals("success"))
                    {
                        db.addTimeTable(s);
                    }
                    if(type.equals("error"))
                    {
                        reloadTimetable();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                ok = false;

            }

                return null;
        }
        public void reloadTimetable()
        {
            db.resetTimetable();
            String token = db.getToken();
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("tag", login_tag));
            param.add(new BasicNameValuePair("token", token));

            JSONParser jsonParser = new JSONParser();
            String url = Var.host + "/api/user/find_class";
            JSONObject jsons = jsonParser.postJSONFromUrl(url, param);
           /* try {
                type = jsons.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            JsonToSubjectInfo parse = new JsonToSubjectInfo(jsons);

            List<SubjectInfor> listsub ;
            listsub= new ArrayList<SubjectInfor>();
            listsub= parse.getListSubject();


            if (listsub.size() > 0) {
                for (int i = 0; i < listsub.size(); i++)
                    db.addTimeTable(listsub.get(i));
            }

        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(AddSubjectToTimeTable.this, "", "Loading...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            super.onPostExecute(aVoid);
            if(!ok)
            {
                Toast.makeText(getApplicationContext(),"Không thể kết nối tới server để đồng bộ",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(type.equals(Var.TYPE_SUCCESS))
                {
                    Toast.makeText(getApplicationContext(), "Thêm môn học thành công", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(AddSubjectToTimeTable.this, Timetable.class);
                    startActivity(in);
                    finish();
                }
                else if(type.equals(Var.TOKEN_ERROR))
                {
                    db.resetTables();
                    Intent intent = new Intent(AddSubjectToTimeTable.this, Login.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
