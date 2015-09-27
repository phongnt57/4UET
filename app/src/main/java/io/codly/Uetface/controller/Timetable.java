//When I wrote this, only God and I understood what I was doing
//Now, God only knows.  code dai vcl
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
import adapter.Day;
import adapter.ExpandableListAdapter;
import adapter.JSONParser;
import adapter.JSonToSubjectInfoAll;
import adapter.JsonToSubjectInfo;
import adapter.SubjectInfor;
import adapter.Var;
import io.codly.Uetface.R;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Timetable extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    public List<Day> listDataHeader;
    public HashMap<Day, List<SubjectInfor>> listDataChild;
    List<SubjectInfor> list;
    int numberclass2=0;
    int numberclass3=0;
    int numberclass4=0;
    int numberclass5=0;
    int numberclass6=0;
    int numberclass7=0;
    DatabaseHandler db;
    Runnable run;
    getAddSubject getAdd;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_timetable);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.timetable_actionbar);
        db = new DatabaseHandler(this);
        listDataHeader = new ArrayList<Day>();
        listDataChild = new HashMap<Day, List<SubjectInfor>>();
        // update list khi co thay doi
        run = new Runnable() {
            public void run() {
                 numberclass2=0;
                 numberclass3=0;
                 numberclass4=0;
                 numberclass5=0;
                 numberclass6=0;
                 numberclass7=0;
                //reload content
                listDataHeader.clear();
                listDataChild.clear();
                initView();
                expListView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                expListView.invalidateViews();
                expListView.refreshDrawableState();
                //
            }
        };
        initView();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        expListView.setAdapter(listAdapter);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //Toast.makeText(getApplicationContext(),
                 //"Group Clicked " + listDataHeader.get(groupPosition),
                 //Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ExpandableListView.getPackedPositionType(l) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(l);
                    int childPosition = ExpandableListView.getPackedPositionChild(l);
                    final SubjectInfor dayclick = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition);
                    AlertDialog a = new AlertDialog.Builder(Timetable.this).setIcon(R.drawable.appicon).setTitle("Xóa môn học")
                            .setMessage("Xóa môn học "+ dayclick.getTenmon())
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   new removeSubjectToServer().execute(dayclick);


                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();




                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.

                    // Return true as we are handling the event.
                    return true;
                }
                return false;
            }
        });
    }

    private void initView(){
        //List<SubjectInfor> list = new ArrayList<SubjectInfor>();

        Log.e("size of list database",String.valueOf(db.getSubjectCount()));
        if(db.getSubjectCount() > 0 )
        {
            list = db.getTimeTable();
        }
        else
        {
            list = new ArrayList<SubjectInfor>();



        }


        Day mon = new Day("Thứ 2","2");
        Day tues = new Day("Thứ 3", "1");
        Day wed = new Day("Thứ 4","2");
        Day thus = new Day("Thứ 5", "1");
        Day fri = new Day("Thứ 6","1");
        Day sat = new Day("Thứ 7", "0");
        listDataHeader.add(mon);
        listDataHeader.add(tues);
        listDataHeader.add(wed);
        listDataHeader.add(thus);
        listDataHeader.add(fri);
        listDataHeader.add(sat);





        List<SubjectInfor> monclass = new ArrayList<SubjectInfor>();


        List<SubjectInfor>tuesclass = new ArrayList<SubjectInfor>();


        List<SubjectInfor> wedclass = new ArrayList<SubjectInfor>();


        List<SubjectInfor> thusclass = new ArrayList<SubjectInfor>();


        List<SubjectInfor> friclass = new ArrayList<SubjectInfor>();


        List<SubjectInfor> satclass = new ArrayList<SubjectInfor>();
        Log.e("size of list",String.valueOf(list.size()));
        for(int i= 0;i<list.size();i++)
        {
            String address_class = list.get(i).getGiangduong();
            String name_class = list.get(i).getTenmon();
            String time_class = list.get(i).getTietbatdau() + "-"+list.get(i).getTietketthuc();
            ClassInDay a = new ClassInDay(address_class,name_class,time_class);
            SubjectInfor subToadd = list.get(i);
            if(list.get(i).getThu().equals("2"))
            {
                monclass.add(subToadd);
                numberclass2++;
            }
            else if(list.get(i).getThu().equals("3"))
            {
                tuesclass.add(subToadd);
                numberclass3++;
            }
            else if(list.get(i).getThu().equals("4"))
            {
                wedclass.add(subToadd);
                numberclass4++;
            }
            else if(list.get(i).getThu().equals("5"))
            {
                thusclass.add(subToadd);
                numberclass5++;
            }
            else if(list.get(i).getThu().equals("6"))
            {
                friclass.add(subToadd);
                numberclass6++;
            }
            else if(list.get(i).getThu().equals("7"))
            {
                satclass.add(subToadd);
                numberclass7++;
            }
        }

        mon.setNumberclass(String.valueOf(numberclass2));
        tues.setNumberclass(String.valueOf(numberclass3));
        wed.setNumberclass(String.valueOf(numberclass4));
        thus.setNumberclass(String.valueOf(numberclass5));
        fri.setNumberclass(String.valueOf(numberclass6));
        sat.setNumberclass(String.valueOf(numberclass7));



        listDataChild.put(listDataHeader.get(0), monclass); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tuesclass);
        listDataChild.put(listDataHeader.get(2), wedclass);
        listDataChild.put(listDataHeader.get(3), thusclass);
        listDataChild.put(listDataHeader.get(4), friclass);
        listDataChild.put(listDataHeader.get(5), satclass);


        //expListView.setAdapter(listAdapter);






        //TODO add extra view initialization code here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.time_table_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.action_add)
        {
            if(db.getAllSubjectCount()>0)
            {
                Intent intent = new Intent(Timetable.this,AddSubjectToTimeTable.class);
                startActivity(intent);
                finish();
            }
            else
            {

                    getAdd = new getAddSubject();
                    getAdd.execute();
                    return true;

            }
        }

                return super.onOptionsItemSelected(item);

    }
    class getAddSubject extends AsyncTask<Void,Void,Void>
    {   ProgressDialog dialog;
        List<SubjectInfor> allsubject;
        boolean isload = true;
        String type;
        @Override
        protected Void doInBackground(Void... voids) {
            CheckNetworkConnection c = new CheckNetworkConnection();
            if(c.isConnectedToServer(Var.host,Var.ping)) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                String token = db.getToken();
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("tag", login_tag));
                param.add(new BasicNameValuePair("token", token));

                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/get_class";
                JSONObject json = jsonParser.postJSONFromUrl(url, param);
                try
                {

                    type = json.getString("type");

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                String  mssv = db.getMssv();
                JSonToSubjectInfoAll parse = new JSonToSubjectInfoAll(json,mssv);
                allsubject = new ArrayList<SubjectInfor>();
                allsubject = parse.getListSubject();

                Log.e("JSON all sub :", json.toString());
                Log.e("size all:", String.valueOf(allsubject.size()));

            }
            else
            {
                isload= false;

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(Timetable.this,"","Chờ chút nhé.....");
            //dialog.setCancelable(true);
            dialog.setCancelable(true);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    getAdd.cancel(true);
                    Log.e("log add",String.valueOf(getAdd.isCancelled()));
                }
            });
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(dialog.isShowing())
                dialog.dismiss();
            if(isload)
            {
                if(type.equals(Var.TOKEN_ERROR))
                {
                    db.resetTables();
                    Intent intent = new Intent(Timetable.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else if(type.equals(Var.TYPE_SUCCESS))
                {
                    for (int k = 0; k < allsubject.size(); k++)
                        db.addSubjectToAllSubjectTable(allsubject.get(k));



                    Log.e("size all table:", String.valueOf(db.getAllSubjectCount()));

                    Intent intent = new Intent(Timetable.this, AddSubjectToTimeTable.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Timetable.this,"Lỗi",
                            Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(Timetable.this,"Không thể kết nối mạng để lấy danh sách môn học",
                        Toast.LENGTH_LONG).show();
            }
            finish();
            super.onPostExecute(aVoid);
        }
    }


    class removeSubjectToServer extends AsyncTask<SubjectInfor,Void,Void>
    {
        JSONObject json;
        boolean ok = true;
        ProgressDialog dialog;
        String type;
        String content;
        SubjectInfor s ;
        public removeSubjectToServer() {
            super();
        }

        @Override
        protected Void doInBackground(SubjectInfor... voids) {
            s = voids[0];
            CheckNetworkConnection check = new CheckNetworkConnection();
            if(check.isConnectedToServer(Var.host,1000)) {
                // Log.e("net",String.valueOf(check.isConnectionAvailable(getApplicationContext())));

                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("class_id", s.getMalop()));
                param.add(new BasicNameValuePair("ghi_chu", s.getGhichu()));
                param.add(new BasicNameValuePair("token", db.getToken()));
                Log.e("remove subject",s.getMalop()+" "+s.getGhichu()+ " "+db.getToken());
                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/remove_class";
                json = jsonParser.postJSONFromUrl(url, param);
                try {
                    if(json!=null)
                    {
                        type = json.getString("type");
                        content = json.getString("content");

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

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(Timetable.this, "", "Loading...");
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
                    db.deleteSubjectFromTiemtable(s.getMalop(),s.getGhichu());
                    runOnUiThread(run);
                    Toast.makeText(getApplicationContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                }
                else if(type.equals(Var.TOKEN_ERROR))
                {
                    db.resetTables();
                    Intent in = new Intent(Timetable.this,Login.class);
                    startActivity(in);
                    finish();

                   // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}

    



