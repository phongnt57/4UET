package slidingmenu;

/**
 * Created by phong on 2/5/2015.
 */

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import adapter.CheckNetworkConnection;
import adapter.DatabaseHandler;
import adapter.EvaluateResult;
import adapter.FinalExamInfo;
import adapter.JSONParser;
import adapter.JSonToExamSchedules;
import adapter.JsonToEvaluateResult;
import adapter.JsonToSubjectInfo;
import adapter.JsonToTeacherInfo;
import adapter.SubjectInfor;
import adapter.TeacherInfo;
import adapter.Var;
import io.codly.Uetface.R;
import io.codly.Uetface.controller.AddEvaluate;
import io.codly.Uetface.controller.AddSubjectToTimeTable;
import io.codly.Uetface.controller.Evaluate;
import io.codly.Uetface.controller.Login;
import io.codly.Uetface.controller.ScheduleExam;
import io.codly.Uetface.controller.Timetable;
import io.codly.Uetface.controller.Search;
import io.codly.Uetface.controller.Home;


public class HomeFragment extends Fragment {
    Button but;
    ProgressDialog dialog;
    DatabaseHandler db;
    getFinalExamSchedule getFinal;
    getSubject getSub;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment_sliding, container, false);
        db = new DatabaseHandler(getActivity());

        final ImageButton time_table = (ImageButton)rootView.findViewById(R.id.timetable_button);
        //button.setText("Start Your Game");
        time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                int count = db.getSubjectCount();
                if(count > 0)
                {
                    Log.e("count",String.valueOf(count));
                    Intent i = new Intent(getActivity(), Timetable.class);
                    startActivity(i);
                }
                else
                {
                    //CheckNetworkConnection check = new CheckNetworkConnection();

                   //if(check.isConnectedToServer(Var.host,Var.ping)){


                    getSub = new getSubject();
                    getSub.execute();
                   /*}
                    else
                   {
                       Log.e("co mang","ko");
                       Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
                   }*/


                }
            }
        });
        final ImageButton evaluate = (ImageButton)rootView.findViewById(R.id.evaluate_button);
        //button.setText("Start Your Game");
        evaluate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View InputFragmentView)
            {
                Intent i = new Intent(getActivity(), AddEvaluate.class) ;
                startActivity(i);
            }
        });

        final  ImageButton search = (ImageButton)rootView.findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(getActivity(), Search.class) ;
                //startActivity(i);
                Toast.makeText(getActivity().getApplicationContext(),"Chức năng đang được hoàn thiện",Toast.LENGTH_SHORT).show();

            }
        });

        final ImageButton schedule = (ImageButton)rootView.findViewById(R.id.friend_button);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getExamCount()>0)
                {
                    Intent intent = new Intent(getActivity(), ScheduleExam.class);
                    startActivity(intent);

                }
                else
                {
                    //CheckNetworkConnection check = new CheckNetworkConnection();
                    //if(check.isConnectedToServer(Var.host,Var.ping)){


                        getFinal =  new getFinalExamSchedule();
                        getFinal.execute();
                    /*}
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
                    }*/
                }

            }
        });
        final ImageButton group = (ImageButton)rootView.findViewById(R.id.chat_button);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Chức năng đang được hoàn thiện!Mời bạn quay lại sau",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    class getSubject extends AsyncTask<Void, Objects, List<SubjectInfor>> {

        JSONObject json;
        ProgressDialog dg;
        String res;
        String type;
        List<SubjectInfor> listSubject;
        List<TeacherInfo> listTeacher;
        List<EvaluateResult> results;
        boolean ok = true;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = ProgressDialog.show(getActivity(),"","Loading...");
            dg.setCancelable(true);
            dg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog)
                {

                    //dialog.dismiss();
                    getSub.cancel(true);
                    Log.e("log sub",String.valueOf(getSub.isCancelled()));
                }
            });
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
                   try {
                       type = json.getString("type");
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   JsonToSubjectInfo parse = new JsonToSubjectInfo(json);


                   listSubject = parse.getListSubject();

                   for (int k = 0; k < listSubject.size(); k++)
                       Log.e("list:", k + listSubject.get(k).getTenmon() + " " + listSubject.get(k).getGiangduong());

                   if(type.equals("success"))
                   {

                       pushTeacherInfoToDB();
                       getEvaluatedResulttoDB();
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
            if(dg.isShowing())
            {
                dg.dismiss();
            }
            if(ok)
                {
                   if(type.equals(Var.TOKEN_ERROR))
                   {
                       db.resetTables();
                       Intent intent = new Intent(getActivity(), Login.class);
                       startActivity(intent);
                       getActivity().finish();


                   }
                    else
                   {

                       if (listSubject.size() > 0)
                       {
                           for (int i = 0; i < listSubject.size(); i++)
                               db.addTimeTable(listSubject.get(i));
                       }
                       if(results.size()>0)
                       {
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
                       if(listTeacher.size()>0)
                       {

                           for (int i = 0; i < listTeacher.size(); i++) {
                               db.addTeacher(listTeacher.get(i));
                               Log.e("mgv", listTeacher.get(i).getMagv());
                               Log.e("tgv", listTeacher.get(i).getTengv());

                           }
                       }
                       Intent i = new Intent(getActivity(), Timetable.class);
                       startActivity(i);

                   }
                }
            else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
                }


            super.onPostExecute(result);
            // dialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public void getEvaluatedResulttoDB() {

            String url = Var.host + "/api/user/find_evaluate";

            db.resetEvaluateTable();
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("token", db.getToken()));
            JSONParser jsonParser = new JSONParser();

            JSONObject resultArray = jsonParser.postJSONFromUrl(url, param);
            JsonToEvaluateResult it = new JsonToEvaluateResult(resultArray);
            results = it.getListSubject();
            Log.e("Size evaluate:", String.valueOf(results.size()));

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




        }
    }


    class getFinalExamSchedule extends AsyncTask<Void,Void,Void>
    {
        List<FinalExamInfo> listExam ;
        ProgressDialog dialog;
        boolean ok = true;
        JSONObject json;
        String type;


        @Override
        protected Void doInBackground(Void... voids) {

            CheckNetworkConnection check = new CheckNetworkConnection();
            listExam = new ArrayList<FinalExamInfo>();
            if(check.isConnectedToServer(Var.host,Var.ping)) {

                //db = new DatabaseHandler(getActivity().getApplicationContext());
                String token = db.getToken();
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("tag", login_tag));
                param.add(new BasicNameValuePair("token", token));

                JSONParser jsonParser = new JSONParser();
                String urls = Var.host + "/api/user/lich_thi";
                json = jsonParser.postJSONFromUrl(urls, param);
                try {
                    type = json.getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSonToExamSchedules parse = new JSonToExamSchedules(json);

                listExam = parse.getListExam();

                //for (int k = 0; k < listExam.size(); k++)
                Log.e("list exam:",String.valueOf(listExam.size()));




                Log.e("JSON object lich thi :", json.toString());

            }
            else
            {
                ok = false;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            dialog  = ProgressDialog.show(getActivity(),"","Đang tải lịch thi ");
            dialog.setCancelable(true);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    getFinal.cancel(true);
                    Log.e("log",String.valueOf(getFinal.isCancelled()));
                }
            });
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //if(dg.isShowing())
                dialog.dismiss();
            if(ok)
            {
                if(type.equals(Var.TOKEN_ERROR) )
                {
                    db.resetTables();
                    Intent in = new Intent(getActivity(),Login.class);
                    startActivity(in);
                    getActivity().finish();


                }
                else
                {
                    if (listExam.size() > 0) {
                        for (int i = 0; i < listExam.size(); i++)
                            db.addSchedule(listExam.get(i));
                    }
                    Intent i = new Intent(getActivity(), ScheduleExam.class);
                    startActivity(i);
                }
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra kết nối mạng của bạn",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }



}
