package adapter;

/**
 * Created by phong on 5/6/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.codly.Uetface.R;


import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import adapter.ClassInDay;
import io.codly.Uetface.R;
import io.codly.Uetface.controller.AddEvaluate;


public class EvaluateResultDialogAdapter extends BaseAdapter {
    Context context;
    List<String> rowItems;
    int value;
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

    public EvaluateResultDialogAdapter(Context context, List<String> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    /*private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }*/

    public View getView(final int position, View convertView, ViewGroup parent) {
        // ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dialog_result_evaluate_item, null);
        }
        TextView question_tag = (TextView) convertView.findViewById(R.id.question_tag);
        TextView answer_tag = (TextView) convertView.findViewById(R.id.answer_tag);
        final Spinner spinner = (Spinner)convertView.findViewById(R.id.select);
        final List<String> categories = new ArrayList<String>();

        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        ArrayAdapter<String> adapterSpin = new ArrayAdapter<String>(parent.getContext(),
                android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(adapterSpin);



        question_tag.setText(Var.questionlist[position]);

        answer_tag.setText(rowItems.get(position));

         value = Integer.parseInt(rowItems.get(position)) -1;

        spinner.setSelection(value);
        if (myMap.containsKey(position)) {
            spinner.setSelection(myMap.get(position));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int i, long id) {
                Log.e("select",String.valueOf(i));
                value = i;
                spinner.setSelection(i);
                myMap.put(position, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //spinner.setSelection(position);
                // your code here
            }

        });


        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
