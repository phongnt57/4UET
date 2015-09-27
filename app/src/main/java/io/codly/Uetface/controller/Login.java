package io.codly.Uetface.controller;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;


import adapter.*;
import adapter.Var;
import io.codly.Uetface.R;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {

    EditText user;
    EditText pass;
    Button login;
    TextView loginErrorMsg;
    //ProgressDialog dialog;




    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_login);
        getActionBar().hide();
		initView();
        Button register = (Button)findViewById(R.id.btnLinkToRegisterScreen);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(getApplicationContext(), Register.class);
                startActivity(registerActivity);

            }
        });
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        if(db.getRowCount()>0)
        {
            Intent dashboard = new Intent(getApplicationContext(), Home.class);
            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(dashboard);
        }
        user = (EditText)findViewById(R.id.use);
        pass = (EditText)findViewById(R.id.pass);
        login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().length()==0|| pass.getText().toString().length()==0)
                    Toast.makeText(getApplicationContext(),
                            "Nhập đầy đủ thông tin nhé bạn!",
                            Toast.LENGTH_SHORT).show();
                else
                {

                   new Post().execute();

                }



            }
        });

	}



    private void initView(){
        

        //TODO add extra view initialization code here
    }

    class Post extends AsyncTask<Void, Void, Void> {

        JSONObject json;
        String res="";
        String error_msg;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(Login.this,"","Loading...");
            //super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String username = user.getText().toString();
            String password = pass.getText().toString();
            CheckNetworkConnection check = new CheckNetworkConnection();
        if(check.isConnectedToServer(Var.host,1000)) {
           // Log.e("net",String.valueOf(check.isConnectionAvailable(getApplicationContext())));

                List<NameValuePair> param = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("tag", login_tag));
                param.add(new BasicNameValuePair("action", "login"));
                param.add(new BasicNameValuePair("username", username));
                param.add(new BasicNameValuePair("password", password));
                JSONParser jsonParser = new JSONParser();
                String url = Var.host + "/api/session/new";
                json = jsonParser.postJSONFromUrl(url, param);

                // return json
                //Log.e("JSON", json.toString());


                try {
                    if (json.getString("type") != null) {

                        Log.e("type", json.getString(Var.KEY_TYPE));
                        res = json.getString(Var.KEY_TYPE);
                        if (res.equals("success")) {
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            String token = json.getString("token");

                            // Clear all previous data in database

                            db.resetTables();
                            db.addUser(username, token);
                            Log.e("Number of row:", String.valueOf(db.getRowCount()));


                        } else {
                            // Error in login

                            error_msg = json.getString(Var.KEY_CONTENT);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else
        {
            error_msg = "Không thể kết nối đên server";
            Log.e("net","no");
        }

            return null;


        }
        @Override
        protected void onPostExecute(Void result) {

                dialog.dismiss();
            if (res.equals("success")) {
                Intent dashboard = new Intent(getApplicationContext(), Home.class);
                // Close all views before launching Dashboard
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashboard);
                finish();


            } else {

                Toast.makeText(getApplicationContext(),
                        error_msg, Toast.LENGTH_LONG)
                        .show();
            }



            super.onPostExecute(result);
        }


    }






}
