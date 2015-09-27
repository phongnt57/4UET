package io.codly.Uetface.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import adapter.DatabaseHandler;
import io.codly.Uetface.R;

public class Splash extends Activity {
     int isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_splash);
        DatabaseHandler db = new DatabaseHandler(this);
        isLogin = db.getRowCount();

            new Handler().postDelayed(new Runnable() {

                // Using handler with postDelayed called runnable run method
                @
                        Override
                public void run() {
                    if(isLogin>0 )
                    {
                        Intent i = new Intent(Splash.this, Home.class);
                        startActivity(i);
                        // close this activity
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(Splash.this, Login.class);
                        startActivity(i);
                        // close this activity
                        finish();
                    }
                }
            }, 1000); // wait for 1 seconds
        }



}
