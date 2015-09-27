package io.codly.Uetface.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;

import adapter.CheckNetworkConnection;
import adapter.DatabaseHandler;
import adapter.JSONParser;
import adapter.Var;
import io.codly.Uetface.R;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;


public class Register extends Activity {
    EditText mssv;
    EditText taikhoan;
    EditText email;
    EditText matkhau;
    Button hoanthanh;
    //ProgressDialog dialog;
    //RegisterTask registertask = new RegisterTask();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_register);
        getActionBar().hide();
        mssv = (EditText)findViewById(R.id.mssv);
        taikhoan = (EditText)findViewById(R.id.taikhoan);
        email = (EditText)findViewById(R.id.email);
        matkhau = (EditText)findViewById(R.id.matkhau);
        hoanthanh = (Button)findViewById(R.id.Register);
        hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(registertask.isCancelled()))
                String u = taikhoan.getText().toString();
                String m  = mssv.getText().toString();
                String p = matkhau.getText().toString();
                String e = email.getText().toString();
                if(u.equals("") || m.equals("") ||p.equals("")|| e.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Nhập đầy đủ thông tin bạn nhé", Toast.LENGTH_LONG).show();
                }
                else {
                    new RegisterTask().execute();
                }






            }
        });


    	}



    class RegisterTask extends AsyncTask<Void,Void,Void>
   {
       JSONObject json;
       String reg_user;
       String std_id;
       String reg_pass;
       String reg_email;
       ProgressDialog dialog;



       @Override
       protected void onPreExecute() {
          dialog = ProgressDialog.show(Register.this,
                   null,"Loading...");
           super.onPreExecute();
       }

       @Override
       protected Void doInBackground(Void... voids) {
            reg_user = taikhoan.getText().toString();
            std_id = mssv.getText().toString();
            reg_pass = matkhau.getText().toString();
            reg_email = email.getText().toString();


           //params.add(new BasicNameValuePair("tag", login_tag));
           CheckNetworkConnection c = new CheckNetworkConnection();
           if(c.isConnectedToServer(Var.host,Var.ping)) {
               Log.e("net","true");
               List<NameValuePair> param = new ArrayList<NameValuePair>();
               param.add(new BasicNameValuePair("reg_user", reg_user));
               param.add(new BasicNameValuePair("reg_email", reg_email));
               param.add(new BasicNameValuePair("reg_pass", reg_pass));
               param.add(new BasicNameValuePair("std_id", std_id));
               Log.e("tai khoan",reg_user);
               Log.e("a msv",std_id);
               Log.e("a email",reg_email);
               Log.e("a pass",reg_pass);
               JSONParser jsonParser = new JSONParser();
               String url = Var.host + "/api/user/new";

                   json = jsonParser.postJSONFromUrl(url, param);


               // return json
               Log.e("JSON register", json.toString());
           }
           return null;
       }

       @Override
       protected void onPostExecute(Void aVoid) {
           String type = "";
           String content = "";
           if(dialog.isShowing())
           {
               dialog.dismiss();

           }
           if(true) {
               try {
                   type = json.getString("type");

                   if (type.equals("success")) {
                       content = json.getString("content");
                       Toast.makeText(getApplicationContext(), "Bạn đã đăng ký thành công. Vào email để xác thực tài khoản", Toast.LENGTH_LONG).show();
                   }
                   if (type.equals("error")) {
                       content = json.getString("content");
                       Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();


                   }

               } catch (JSONException e) {
                   e.printStackTrace();

               }
           }

           super.onPostExecute(aVoid);
       }
   }


}
