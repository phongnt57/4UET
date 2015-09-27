package viewpager;

/**
 * Created by phong on 3/19/2015.
 */
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import adapter.DatabaseHandler;
import adapter.SubjectInfor;
import adapter.Var;
import io.codly.Uetface.R;

public class SlidePageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private int position;
    private int id;
    private String mon_hoc;
    private String dia_diem;
    ViewPage act;
    int progess = 0 ;
    TextView text;
    TextView text2;
    TextView text3;
    TextView text4;

    public static SlidePageFragment newInstance(int pos,String mon_hoc, String dia_diem){
        SlidePageFragment page = new SlidePageFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        //args.putInt("id", id);
        args.putString("mon_hoc",mon_hoc);
        args.putString("dia_diem",dia_diem);

        page.setArguments(args);
        return page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);
        mon_hoc = getArguments().getString("mon_hoc");
        dia_diem = getArguments().getString("dia_diem");


        //id = getArguments().getInt("id", 0);
        act = (ViewPage)getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ViewGroup rootView = (ViewGroup) inflater.inflate(
          //      R.layout.fragment_slide_page, container, false);
        View view = inflater.inflate(R.layout.fragment_slide_page, container, false);
        //title
        TextView monhoc = (TextView)view.findViewById(R.id.monhoc);
        TextView lop = (TextView)view.findViewById(R.id.Class);
        //DatabaseHandler db = new DatabaseHandler(getActivity());
        //List<SubjectInfor> list = db.getTimeTable();
        monhoc.setText(mon_hoc);
        lop.setText(dia_diem);

        //question
        final TextView question = (TextView)view.findViewById(R.id.question);
        final TextView question2 = (TextView)view.findViewById(R.id.question2);
        final TextView question3 = (TextView)view.findViewById(R.id.question3);
        final TextView question4 = (TextView)view.findViewById(R.id.question4);
        //if (position < 4)

        text = (TextView)view.findViewById(R.id.text);
        text2 = (TextView)view.findViewById(R.id.text2);
        text3 = (TextView)view.findViewById(R.id.text3);
        text4 = (TextView)view.findViewById(R.id.text4);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),Var.questionFont);
        question.setTypeface(type);
        question2.setTypeface(type);
        question3.setTypeface(type);
        question4.setTypeface(type);


        question.setText(Var.questionlist[position*4]);
        question2.setText(Var.questionlist[position*4+1]);
        question3.setText(Var.questionlist[position*4+2]);
        question4.setText(Var.questionlist[position*4+3]);



        /*if(position==4)
        {
            //view.setBackgroundColor(Color.BLUE);
            question.setText(Var.questionlist[16]);
            question2.setText(Var.questionlist[17]);

        }*/

        //rate
        SeekBar seek = (SeekBar)view.findViewById(R.id.vote);
        SeekBar seek2 = (SeekBar)view.findViewById(R.id.vote2);
        SeekBar seek3 = (SeekBar)view.findViewById(R.id.vote3);
        SeekBar seek4 = (SeekBar)view.findViewById(R.id.vote4);
        seek.setOnSeekBarChangeListener(this);
        seek2.setOnSeekBarChangeListener(this);
        seek3.setOnSeekBarChangeListener(this);
        seek4.setOnSeekBarChangeListener(this);






        return view;
    }

    public SlidePageFragment() {
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
    public void onStopTrackingTouch(SeekBar seekBar) {
        int evaluate = progess*5/seekBar.getMax()+1;
        if(evaluate==6)
            evaluate = 5;
        seekBar.setProgress(evaluate*20);

        //Toast toast = Toast.makeText(getActivity().getApplicationContext(),String.valueOf(evaluate),Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        //toast.show();
        if(seekBar.getId()==R.id.vote)
        {
            act.evaluate[position*4] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(position*4)+String.valueOf(act.evaluate[position*4]));
            text.setText(String.valueOf(act.evaluate[position*4]));

        }

        if(seekBar.getId()==R.id.vote2)
        {
            act.evaluate[position*4+1] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(position*4+1)+String.valueOf(act.evaluate[position*4+1]));
            text2.setText(String.valueOf(act.evaluate[position*4+1]));
        }

        if(seekBar.getId()==R.id.vote3)
        {

            act.evaluate[position*4+2] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(position*4+2)+ " "+String.valueOf(act.evaluate[position*4+2]));
            text3.setText(String.valueOf(act.evaluate[position*4+2]));
        }

        if(seekBar.getId()==R.id.vote4)
        {

            act.evaluate[position*4+3] = String.valueOf(evaluate);
            Log.e("evaluate",String.valueOf(position*4+3)+String.valueOf(act.evaluate[position*4+3]));
            text4.setText(String.valueOf(act.evaluate[position*4+3]));
        }

    }
}
