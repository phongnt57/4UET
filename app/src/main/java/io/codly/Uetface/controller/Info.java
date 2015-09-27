package io.codly.Uetface.controller;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import io.codly.Uetface.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_info);
		initView();
	}

    private void initView(){
        

        //TODO add extra view initialization code here
    }

    
    public void onDoneClicked(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


}
