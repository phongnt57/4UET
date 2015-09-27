package io.codly.Uetface.controller;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import adapter.DatabaseHandler;
import adapter.JSONParser;
import adapter.JsonToTeacherInfo;
import adapter.TeacherInfo;
import adapter.Var;
import io.codly.Uetface.R;
import viewpager.ViewPage;

public class TeacherInput extends Activity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {
    DatabaseHandler db;
    TextView log;
    SearchView searchView;
    ListView mListView;
    String stu_id;
    String subject;
    String cla_id;
    String cla_code;
    String tea_id;
    String tea_name;
    List<TeacherInfo> lteacher =  new ArrayList<TeacherInfo>();
    boolean chose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teacher_input);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.teacherinput_actionbar);
        //log = (TextView)findViewById(R.id.show);
        db = new DatabaseHandler(this);
        EditText ten_mon_hoc = (EditText) findViewById(R.id.ten_mon_hoc);
        EditText ma_dg = (EditText)findViewById(R.id.mdg);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        //searchView.setIconifiedByDefault(false);
        /*try {
            Field searchField = SearchView.class
                    .getDeclaredField("mSearchButton");
            searchField.setAccessible(true);
            ImageView searchBtn = (ImageView) searchField.get(searchView);
            searchBtn.setImageResource(R.drawable.file_plus);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }*/

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(R.id.listteacher);

        Bundle bund = getIntent().getExtras();
        if(bund !=null) {
           stu_id = bund.getString("stu_id");
           subject = bund.getString("subject");
           cla_id= bund.getString("cla_id");
           cla_code= bund.getString("cla_code");
           ten_mon_hoc.setText("Môn học:"+subject);
           ma_dg.setText("Mã đánh giá:"+cla_code);


            }

        Button load = (Button)findViewById(R.id.tiep_tuc);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chose) {
                    Intent intent = new Intent(getApplication(), ViewPage.class);
                    intent.putExtra("stu_id", stu_id);
                    intent.putExtra("subject", subject);
                    intent.putExtra("cla_id", cla_id);
                    intent.putExtra("cla_code",cla_code);
                    intent.putExtra("tea_id", tea_id);
                    intent.putExtra("tea_name", tea_name);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Nhập thông tin GV trước khi tiếp tục", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        showResult(s+"*");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        showResult(s + "*");
        return false;
    }
    public boolean onClose() {
        showResult("");
        return false;
    }
    public void showResult(String query)
    {

              lteacher =   db.searchCustomer(query);
        if(lteacher.size()==0)
        {
            List<String> noresult = new ArrayList<String>();

            //TeacherInfo no = new TeacherInfo("","");
            //lteacher.add(no);
            noresult.add("");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, noresult);
            mListView.setAdapter(adapter);

        }
        if(lteacher.size()>0)
        {
            List<String> texthint = new ArrayList<String>();
            for(int i =0 ; i<lteacher.size();i++)
                texthint.add(lteacher.get(i).getMagv() + "-"+lteacher.get(i).getTengv());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, texthint);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(lteacher.size()>0)
                    {
                        chose = true;
                        Log.e("click in :", String.valueOf(i));
                        tea_id = lteacher.get(i).getMagv();
                        tea_name = lteacher.get(i).getTengv();
                        if (lteacher.get(i).getTengv().equals("")) {
                            Log.e("erorr", "erorr");
                            //chose = false;
                        }
                        else
                        {
                            String ten = lteacher.get(i).getTengv();
                            searchView.setQuery(tea_id + " - " + ten, true);
                            searchView.clearFocus();

                        }
                    }


                    /*Intent intent = new Intent(getApplication(), ViewPage.class);
                    intent.putExtra("mon_hoc", mon_hoc);
                    intent.putExtra("dia_diem", dia_diem);
                    intent.putExtra("ma_danh_gia",ma_danh_gia);
                    intent.putExtra("ma_gv",lteacher.get(i).getMagv());
                    startActivity(intent);*/
                }
            });

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teacher_input, menu);
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

    class GetTechers extends AsyncTask<Void,Void,Void>
    {
        List<TeacherInfo> listTeacher = new ArrayList<TeacherInfo>();
        ProgressDialog dialog;

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
        @Override
        protected Void doInBackground(Void... voids) {

            pushTeacherInfoToDB();

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(TeacherInput.this, "", "Loading...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            log.setText(listTeacher.get(0).getTengv());
            if(dialog.isShowing())
                dialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
