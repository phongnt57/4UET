package viewpager;

/**
 * Created by phong on 3/19/2015.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.DialogFragment;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

//import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
//import com.example.phong.myapplication.R;

import io.codly.Uetface.R;

public class ViewPage extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;
    public String stu_id;
    public String subject;
    public String cla_id;
    public String cla_code;
    public String tea_id;
    public String tea_name;
    public  int id;
    public String ma_danh_gia;
    public  String ma_gv;
    public String[] evaluate = new String[20];

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        //TextView name = (TextView)findViewById(R.id.name_class);
        //TextView address = (TextView)findViewById(R.id.add_class);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.viewpage_actionbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        getActionBar().setTitle("ĐÁNH GIÁ MÔN HỌC");
        Bundle bund = getIntent().getExtras();
        if(bund !=null) {
             stu_id = bund.getString("stu_id");
             subject = bund.getString("subject");
             cla_id = bund.getString("cla_id");
             cla_code = bund.getString("cla_code");
             tea_id = bund.getString("tea_id");
             tea_name = bund.getString("tea_name");

            //name.setText(name_class);
            //address.setText(add_class);
        }
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //mPager.setPageTransformer(true, new DefaultTransformer());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //return new SlidePageFragment();
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return SlidePageFragment.newInstance(0,subject,cla_id);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return SlidePageFragment.newInstance(1,subject,cla_id);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return SlidePageFragment.newInstance(2,subject,cla_id);
                case 3: // Fragment # 1 - This will show SecondFragment
                    return SlidePageFragment.newInstance(3,subject,cla_id);
                case 4: // Fragment # 1 - This will show SecondFragment
                    return SubmitFragment.newInstance(stu_id,subject,cla_id,cla_code,tea_id,tea_name);
                //case 5: // Fragment # 1 - This will show SecondFragment
                //    return SubmitFragment.newInstance(mon_hoc,dia_diem,ma_danh_gia,ma_gv);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
