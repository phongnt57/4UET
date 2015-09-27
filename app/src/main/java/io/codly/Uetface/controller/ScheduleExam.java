package io.codly.Uetface.controller;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ClassInDay;
import adapter.DatabaseHandler;
import adapter.Day;
import adapter.ExpandableListAdapter;
import adapter.FinalExamAdapter;
import adapter.FinalExamInfo;
import adapter.SortTimeFinalExam;
import adapter.SubjectInfor;
import io.codly.Uetface.R;

public class ScheduleExam extends ActionBarActivity {
    FinalExamAdapter listAdapter;
    ExpandableListView expListView;
    public List<Day> listDataHeader;
    public HashMap<Day, List<ClassInDay>> listDataChild;
    List<FinalExamInfo> list = new ArrayList<FinalExamInfo>();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule_exam);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.schedule_actionbar);
        //getActionBar().setTitle("LỊCH THI");
        expListView = (ExpandableListView)findViewById(R.id.lvExpExam);
        db = new DatabaseHandler(this);
        list = db.getAllFinalExam();
        SortTimeFinalExam sort = new SortTimeFinalExam(list);
        list = sort.getSort();
        listDataHeader = new ArrayList<Day>();
        listDataChild = new HashMap<Day, List<ClassInDay>>();
        for(int i = 0;i<list.size();i++)
        {
            String sbd = list.get(i).getSbd();
            String ten_mon = list.get(i).getMon_hoc();
            String ngay_thi = list.get(i).getNgay_thi();
            String gio_thi = list.get(i).getGio_thi();
            String ca_thi = list.get(i).getCa_thi();
            String  phong_thi = list.get(i).getPhong_thi();
            String hinh_thuc_thi = list.get(i).getHinh_thuc_thi();
            Day mon = new Day(ten_mon,ngay_thi);
            listDataHeader.add(mon);
            List<ClassInDay> thong_tin_thi = new ArrayList<ClassInDay>();
            ClassInDay info = new ClassInDay(phong_thi,"SBD:"+sbd,gio_thi);
            thong_tin_thi.add(info);
            listDataChild.put(listDataHeader.get(i), thong_tin_thi);
        }

        listAdapter = new FinalExamAdapter(this, listDataHeader, listDataChild);


        expListView.setAdapter(listAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_exam, menu);
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
}
