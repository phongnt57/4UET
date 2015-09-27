package io.codly.Uetface.controller;

/**
 * Created by phong on 4/20/2015.
 */
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
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
import adapter.EvaluateResultDialogAdapter;
import adapter.JSONParser;
import adapter.JsonToEvaluateResult;
import adapter.SubjectEvaluateListAdapter;
import adapter.SubjectInfor;
import adapter.Var;
import io.codly.Uetface.R;
import viewpager.ViewPage;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddEvaluate extends Activity implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView listView;
    List<ClassInDay> rowItems;
    List<EvaluateResult> list;
    SubjectEvaluateListAdapter adapter;
    DatabaseHandler db;
    Dialog alertDialogBuilder;
    ListView dialoglist;
    Runnable run;
    EvaluateResult evaluateRe;
    TextView me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_evaluate_view);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.add_evaluate_actionbar);
        getActionBar().setTitle("MÔN HỌC ĐÃ ĐÁNH GIÁ");
        me = (TextView)findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.list_subjectevaluated);
        run = new Runnable() {
            public void run() {

                //reload content

                rowItems.clear();
                initData();
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();

            }
        };
        //initData();
        rowItems = new ArrayList<ClassInDay>();

        db = new DatabaseHandler(this);
        initData();



    }

    private void initData(){
        int numRow = db.getEvaluateCount();
        if(numRow>0)
        {
            list = db.getEvaluateResults();
            for(int i = 0 ; i< list.size();i++)
            {
                EvaluateResult e = list.get(i);
                ClassInDay c = new ClassInDay(e.getMalop(),e.getTenmon());
                rowItems.add(c);
            }

            adapter = new SubjectEvaluateListAdapter(this, rowItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);
        }
        else {
            me.setText("Bạn chưa có đánh giá nào");
        }


    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Tên GV " + ": " + list.get(position).getTengiangvien()+"\n"
                +"Mã GV" +  ":"  + list.get(position).getMagiangvien()
                ,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        showdialog(position);

        /*Intent i = new Intent(getApplication(),ViewPage.class);
        i.putExtra("subject", rowItems.get(position).getName_class());
        i.putExtra("class", rowItems.get(position).getAddress_class());
        i.putExtra("id", rowItems.get(position).getId());
        startActivity(i);*/

    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        evaluateRe = list.get(i);
        AlertDialog a = new AlertDialog.Builder(AddEvaluate.this).setIcon(R.drawable.appicon).setTitle("Xóa môn học")
                .setMessage("Xóa môn học "+ evaluateRe.getTenmon())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        new DeleteEvaluate().execute(evaluateRe);



                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

        return false;
    }

    private void showdialog(int pos)
    {
         alertDialogBuilder = new Dialog(AddEvaluate.this);
         alertDialogBuilder.setContentView(R.layout.dialog_result_evaluate);
         final EvaluateResult item = list.get(pos);
         alertDialogBuilder.setTitle(item.getTengiangvien());
         alertDialogBuilder.setCancelable(true);
         alertDialogBuilder.setCanceledOnTouchOutside(true);


         dialoglist = (ListView)alertDialogBuilder.findViewById(R.id.dialoglist);

         String kq = item.getKetqua();
         String[] splits = kq.split("-");
         List<String> reslut =  Arrays.asList(splits);

        final EvaluateResultDialogAdapter adaptertoDialog = new EvaluateResultDialogAdapter(this,reslut);
        dialoglist.setAdapter(adaptertoDialog);

        Button postSubmit = (Button)alertDialogBuilder.findViewById(R.id.postsubmit);
        postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> selections = new ArrayList<String>();
                Log.e("size log",String.valueOf(dialoglist.getCount()));
                for (int i = 0; i < dialoglist.getCount(); i++) {

                    // Get row's spinner
                   View listItem = getViewByPosition(i,dialoglist);

                    Spinner spinner = (Spinner) listItem.findViewById(R.id.select);


                    // Get selection
                    int selectionInt =  spinner.getSelectedItemPosition();
                    String selection = String.valueOf(selectionInt+1);


                    Log.e("edit",String.valueOf(i)+":"+selection);
                    selections.add(selection);
                }
                editEvaluateParams par = new editEvaluateParams(item,selections);
                editEvaluate myTask = new editEvaluate();
                myTask.execute(par);
            }
        });
        Button cancel = (Button)alertDialogBuilder.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder.dismiss();
            }
        });
        alertDialogBuilder.show();


    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addevaluate_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.action_addevaluate)
        {
            if(db.getSubjectCount()>0) {

                Intent i = new Intent(AddEvaluate.this, Evaluate.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Cập nhập TKB trước khi đánh giá",Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private static class editEvaluateParams {
        EvaluateResult e;
        List<String> selections;

        private editEvaluateParams(EvaluateResult e, List<String> selections) {
            this.e = e;
            this.selections = selections;
        }
    }
    class editEvaluate extends AsyncTask<editEvaluateParams,Void,Void>
    {
        JSONObject json = new JSONObject();
        boolean done = true;
        ProgressDialog dialog;
        String type;

        @Override
        protected Void doInBackground(editEvaluateParams... pass) {
            CheckNetworkConnection check = new CheckNetworkConnection();
            if(check.isConnectedToServer(Var.host,Var.ping)) {
                editEvaluateParams vars = pass[0];
                List<String> selections = vars.selections;
                EvaluateResult e = vars.e;


                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("token", db.getToken()));
                param.add(new BasicNameValuePair("ma_lop", e.getMalop()));
                param.add(new BasicNameValuePair("ma_giang_vien", e.getMagiangvien()));
                Log.e("edit post mgv - ml",e.getMalop()+e.getMagiangvien());
                for (int i = 0; i < 19; i++) {
                    param.add(new BasicNameValuePair("q" + String.valueOf(i + 1), selections.get(i)));
                    Log.e("post edit",selections.get(i));

                }
                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/update_evaluate";
                json = jsonParser.postJSONFromUrl(url, param);
                try {
                    type = json.getString("type");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                Log.e("eidit evaluate",json.toString());
                if(type.equals(Var.TYPE_SUCCESS))
                {
                    getEvaluatedResulttoDB();
                }
                done = true;
                alertDialogBuilder.dismiss();

            }
            else
            {
                done = false;
            }
            return  null;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(AddEvaluate.this,"","Đang đồng bộ với server");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            if(!done)
            {
                Log.e("check net","false");
                Toast.makeText(getApplicationContext(),"Kiểm tra  kết nối mạng của bạn",Toast.LENGTH_LONG).show();
            }
            else
            {
               if(type.equals(Var.TOKEN_ERROR))
               {
                   db.resetTables();
                   Intent intent = new Intent(AddEvaluate.this, Login.class);
                   startActivity(intent);
                   finish();
               }
                else if(type.equals(Var.TYPE_SUCCESS))
               {
                   Toast.makeText(getApplicationContext(), "Bạn đã chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                   finish();
                   startActivity(getIntent());
               }
                else
               {
                   Toast.makeText(getApplicationContext(), "Không thể thực thi yêu cầu này", Toast.LENGTH_SHORT).show();
               }
            }

            super.onPostExecute(aVoid);
        }
    }

    public void getEvaluatedResulttoDB() {

        String url = Var.host + "/api/user/find_evaluate";

        db.resetEvaluateTable();
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("token", db.getToken()));
        JSONParser jsonParser = new JSONParser();

        JSONObject resultArray = jsonParser.postJSONFromUrl(url, param);
        JsonToEvaluateResult it = new JsonToEvaluateResult(resultArray);
        List<EvaluateResult> results = it.getListSubject();
        Log.e("Size evaluate:", String.valueOf(results.size()));

        for (int k = 0; k < results.size(); k++)
        {
            db.addEvaluateResult(results.get(k));
            Log.e("cmt", results.get(k).getCommment());
            Log.e("ten gv", results.get(k).getTengiangvien());
            Log.e("kq", results.get(k).getKetqua());
            Log.e("mgv", results.get(k).getMagiangvien());
            Log.e("tenmon", results.get(k).getTenmon());
        }




    }
    public class DeleteEvaluate extends AsyncTask<EvaluateResult,Void,Void>
    {
        public boolean ok = true;
        EvaluateResult e;
        JSONObject json;
        String types;
        public DeleteEvaluate() {
            super();
        }

        @Override
        protected Void doInBackground(EvaluateResult... voids) {
            CheckNetworkConnection check = new CheckNetworkConnection();
            if(check.isConnectedToServer(Var.host,1000)) {
                // Log.e("net",String.valueOf(check.isConnectionAvailable(getApplicationContext())));

                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("tag", login_tag));
                 e = voids[0];
                param.add(new BasicNameValuePair("token", db.getToken()));
                param.add(new BasicNameValuePair("ma_lop", e.getMalop()));
                param.add(new BasicNameValuePair("ma_giang_vien", e.getMagiangvien()));

                Log.e("ml-mgv-to",e.getMalop()+" "+e.getMagiangvien()+" "+db.getToken());
                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/delete_evaluate";
                json = jsonParser.postJSONFromUrl(url, param);
                try {
                    types = json.getString("type");
                } catch (JSONException e1) {
                    e1.printStackTrace();
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!ok)
            {
                Toast.makeText(getApplicationContext(),Var.ERROR_SYNC,Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(types.equals(Var.TOKEN_ERROR))
                {
                    db.resetTables();
                    Intent in = new Intent(AddEvaluate.this,Login.class);
                    startActivity(in);
                    finish();
                }
                else if(types.equals(Var.TYPE_SUCCESS))
                {
                    db.deleteSubjectFromEvaluate(e.getId());
                    runOnUiThread(run);
                    Toast.makeText(getApplicationContext(), "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Dữ liệu xóa không hợp lệ",Toast.LENGTH_SHORT).show();
                }




            }
        }
    }



}