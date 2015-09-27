package customAutocompleteTextView;

/**
 * Created by phong on 4/26/2015.
 */

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import io.codly.Uetface.controller.AddSubjectToTimeTable;

public class CustomAutoCompleteTextChangedListener implements TextWatcher{

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        //AddSubjectToTimeTable mainActivity = ((AddSubjectToTimeTable) context);

        //mainActivity.list_subject = mainActivity.getList_Subject(s.toString());
        //mainActivity.hint = mainActivity.getItemsFromDb(s.toString());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        // if you want to see in the logcat what the user types



        AddSubjectToTimeTable mainActivity = ((AddSubjectToTimeTable) context);

        // query the database based on the user input
        mainActivity.list_subject = mainActivity.getList_Subject(userInput.toString());
        mainActivity.hint = mainActivity.getItemsFromDb(userInput.toString());

        Log.e(TAG, "User input: " + userInput);

        // update the adapater
        mainActivity.adapter.notifyDataSetChanged();
        mainActivity.adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, mainActivity.hint);
        mainActivity.tenmon.setAdapter(mainActivity.adapter);
        //mainActivity.phong.setText(mainActivity.list_subject.get(0).getGiangduong());
        Log.e("size of list_su",String.valueOf(mainActivity.list_subject.size())+ " key word: "+userInput.toString()  );
       // Log.e("size of hint",String.valueOf(mainActivity.hint.size()));

    }

}
