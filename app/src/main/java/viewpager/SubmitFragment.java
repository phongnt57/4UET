package viewpager;

/**
 * Created by phong on 3/19/2015.
 */
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.CheckNetworkConnection;
import adapter.DatabaseHandler;
import adapter.EvaluateResult;
import adapter.JSONParser;
import adapter.JsonToEvaluateResult;
import adapter.SubjectInfor;
import adapter.Var;
import io.codly.Uetface.R;
import io.codly.Uetface.controller.AddEvaluate;
import io.codly.Uetface.controller.Login;

public class SubmitFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    //private int position;
    //private int id;
    private String subject;
    private String cla_id;
    private String cla_code;
    private String tea_id;
    private String tea_name;
    private String stu_id;
    ViewPage act;
    int progess = 0 ;
    TextView text16;
    TextView text17;
    TextView text18;

    public static SubmitFragment newInstance(String stu_id, String subject,String cla_id,String cla_code,
                                             String tea_id,String tea_name){
        SubmitFragment page = new SubmitFragment();
        Bundle args = new Bundle();
        //args.putInt("position", pos);
        //args.putInt("id", id);
        args.putString("stu_id",stu_id);
        args.putString("subject",subject);
        args.putString("cla_id",cla_id);
        args.putString("cla_code",cla_code);
        args.putString("tea_id",tea_id);
        args.putString("tea_name",tea_name);


        page.setArguments(args);
        return page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //position = getArguments().getInt("position", 0);
        stu_id = getArguments().getString("stu_id");
        subject = getArguments().getString("subject");
        cla_id = getArguments().getString("cla_id");
        cla_code = getArguments().getString("cla_code");
        tea_id = getArguments().getString("tea_id");
        tea_name = getArguments().getString("tea_name");



        //id = getArguments().getInt("id", 0);
        act = (ViewPage)getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ViewGroup rootView = (ViewGroup) inflater.inflate(
        //      R.layout.fragment_slide_page, container, false);
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        //title
        TextView monhoc = (TextView)view.findViewById(R.id.monhoc);
        TextView lop = (TextView)view.findViewById(R.id.Class);

        //DatabaseHandler db = new DatabaseHandler(getActivity());
        //List<SubjectInfor> list = db.getTimeTable();
        monhoc.setText(subject);
        lop.setText(cla_id);



        //question
        final TextView question16 = (TextView)view.findViewById(R.id.question16);
        final TextView question17 = (TextView)view.findViewById(R.id.question17);
        final TextView question18 = (TextView)view.findViewById(R.id.question18);

        text16 = (TextView)view.findViewById(R.id.text16);
        text17 = (TextView)view.findViewById(R.id.text17);
        text18 = (TextView)view.findViewById(R.id.text18);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),Var.questionFont);
        question16.setTypeface(type);
        question17.setTypeface(type);
        question16.setText(Var.questionlist[16]);
        question17.setText(Var.questionlist[17]);
        question18.setText(Var.questionlist[18]);







        //rate
        SeekBar seek = (SeekBar)view.findViewById(R.id.vote);
        SeekBar seek2 = (SeekBar)view.findViewById(R.id.vote2);
        SeekBar seek3 = (SeekBar)view.findViewById(R.id.vote3);

        seek.setOnSeekBarChangeListener(this);
        seek2.setOnSeekBarChangeListener(this);
        seek3.setOnSeekBarChangeListener(this);

        Button submit = (Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAnswer()==20)
                 new PostEvaluate().execute();
                else
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Bạn chưa trả lời câu hỏi thứ   "+(checkAnswer()+1),Toast.LENGTH_SHORT).show();
            }
        });







        return view;
    }
    public  int checkAnswer()
    {
        int check = 20;
        for(int i = 0;i<Var.numberQuestion;i++)
        {
            if(act.evaluate[i]==null) {
                check = i;
                break;


            }
        }
        return check;
    }

    public SubmitFragment() {
        super();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        progess = i;

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        int evaluate = progess*5/seekBar.getMax()+1;
        if(evaluate==6)
            evaluate = 5;
        seekBar.setProgress(evaluate*20);

        //Toast toast = Toast.makeText(getActivity().getApplicationContext(),String.valueOf(evaluate),Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        //toast.show();
        if(seekBar.getId()==R.id.vote)
        {
            act.evaluate[16] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(16)+String.valueOf(act.evaluate[16]));
            text16.setText(String.valueOf(act.evaluate[16]));
        }

        if(seekBar.getId()==R.id.vote2)
        {
            act.evaluate[17] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(17)+String.valueOf(act.evaluate[17]));
            text17.setText(String.valueOf(act.evaluate[17]));
        }


        if(seekBar.getId()==R.id.vote3)
        {
            act.evaluate[18] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(18)+String.valueOf(act.evaluate[18]));
            text18.setText(String.valueOf(act.evaluate[18]));
        }



    }



    class PostEvaluate extends AsyncTask<Void,Void,Void> {
        ProgressDialog dialog;
        JSONObject json = new JSONObject();
        JSONObject resultArray;
        DatabaseHandler db;
        boolean ok = true;
        String type;
        String content = "";

        @Override
        protected Void doInBackground(Void... voids) {

             db = new DatabaseHandler(getActivity());
            //List<SubjectInfor> list = db.getTimeTable();
            //SubjectInfor sub = list.get(id);

            CheckNetworkConnection ch = new CheckNetworkConnection();
            Log.e("net",String.valueOf(ch.isConnectedToServer(Var.host,Var.ping)));
            //if(ch.isConnectedToServer(Var.host,Var.ping)) {
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //param.add(new BasicNameValuePair("token", db.getToken()));
                param.add(new BasicNameValuePair("stu_id", stu_id));
                param.add(new BasicNameValuePair("subject", subject));
                param.add(new BasicNameValuePair("cla_id", cla_id));
                param.add(new BasicNameValuePair("cla_code",cla_code));
                param.add(new BasicNameValuePair("tea_id",tea_id));
                param.add(new BasicNameValuePair("tea_name",tea_name));
                Log.e("thong tin dang gia:", stu_id + " - " + subject + "- "+cla_id+ "- "+cla_code + "- "+ tea_id +"- "+ db.getToken());
                for (int i = 0; i < 19; i++) {
                    String kq = String.valueOf(act.evaluate[i]);
                    param.add(new BasicNameValuePair(Var.answerlist[i], kq));
                }
                param.add(new BasicNameValuePair("token", db.getToken()));

                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/user/create_evaluate";
                json = jsonParser.postJSONFromUrl(url, param);
            try {
                if(json!=null)
                {
                    type = json.getString("type");
                    content = json.getString("content");
                    Log.e("type evaluate", json.getString(Var.KEY_TYPE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(type.equals(Var.TYPE_SUCCESS))
            {
                getEvaluatedResulttoDB();
            }

            //}            else
            //{
              //  ok= false;
            //}

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "", "Loading...");
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing())
                dialog.dismiss();
            if(!ok)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
            }
            else {

                    if (type != null) {
                        //loginErrorMsg.setText("");


                        if (type.equals("success")) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Gửi mẫu đánh giá thành công", Toast.LENGTH_LONG)
                                    .show();
                            Intent in = new Intent(getActivity(), AddEvaluate.class);
                            startActivity(in);
                            getActivity().finish();


                        } else  if(type.equals(Var.TOKEN_ERROR)){
                            db.resetTables();
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);
                            getActivity().finish();



                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    content, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

            }

            super.onPostExecute(aVoid);
        }

        public void getEvaluatedResulttoDB() {
            /*try {
               if (json.getString("type") != null ) {

                    //Log.e("type", json.getString(Var.KEY_TYPE));
                    String res = json.getString(Var.KEY_TYPE);
                     if (res.equals("success")) {
                        // ghi ket qua vao database
                        */
                        String url = Var.host + "/api/user/find_evaluate";
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        db.resetEvaluateTable();
                        List<NameValuePair> param = new ArrayList<NameValuePair>();
                        param.add(new BasicNameValuePair("token", db.getToken()));
                        JSONParser jsonParser = new JSONParser();

                        resultArray = jsonParser.postJSONFromUrl(url, param);
                        JsonToEvaluateResult it = new JsonToEvaluateResult(resultArray);
                        List<EvaluateResult> results = it.getListSubject();
                        Log.e("Size evaluate:", String.valueOf(results.size()));
                        DatabaseHandler data = new DatabaseHandler(getActivity());
                        for (int k = 0; k < results.size(); k++) {
                            data.addEvaluateResult(results.get(k));
                            Log.e("cmt", results.get(k).getCommment());
                            Log.e("ten gv", results.get(k).getTengiangvien());
                            Log.e("kq", results.get(k).getKetqua());
                            Log.e("mgv", results.get(k).getMagiangvien());
                            Log.e("tenmon", results.get(k).getTenmon());
                        }


                    /*}
                }


        } catch (Exception e) {//} catch (JSONException e) {
                e.printStackTrace();

            }*/

        }
    }



}
