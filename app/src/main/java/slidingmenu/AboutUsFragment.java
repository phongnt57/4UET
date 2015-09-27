package slidingmenu;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.codly.Uetface.R;


/**
 * Created by phong on 2/5/2015.
 */
public class AboutUsFragment extends Fragment {
        public AboutUsFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
            TextView about = (TextView)rootView.findViewById(R.id.about);
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/SegoeUI.ttf");
            about.setTypeface(type);
            about.setText("Fail Team \n -....... \n - ....... \n  -......");

            return rootView;
        }
}