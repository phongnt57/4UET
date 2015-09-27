package slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.codly.Uetface.R;

/**
 * Created by phong on 5/2/2015.
 */
public class FeedBackFragment  extends Fragment {
    public FeedBackFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.feedback_fragment, container, false);
            final EditText hovaten = (EditText)rootView.findViewById(R.id.fullname);
            final EditText feed = (EditText)rootView.findViewById(R.id.editTextMessage);
            Button send = (Button)rootView.findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String hoten = hovaten.getText().toString();
                    String noidung = feed.getText().toString();
                    if(hoten.equals("") || noidung.equals(""))
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Bạn chưa nhập thông tin",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        sendEmail(hoten + " phản hồi",noidung);

                    }
                }
            });

            return rootView;
        }
    private void sendEmail(String subject ,String body){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String to = "admin@4uet.com";
        emailIntent.setType("text/html");
        //emailIntent.setData(Uri.parse("mailto:admin@4uet,com"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }


}
