package slidingmenu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.DatabaseHandler;
import io.codly.Uetface.R;
import io.codly.Uetface.controller.Home;
import io.codly.Uetface.controller.Login;

/**
 * Created by phong on 2/5/2015.
 */
public class LogoutFragment extends Fragment {
    public LogoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_logout, container, false);


        AlertDialog a = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.appicon).setTitle("Log Out")
                .setMessage("Bạn muốn đăng xuất?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        db.resetTables();
                        Intent login = new Intent(getActivity().getApplication(), Login.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                getActivity().setTitle("HOME PAGE");*/
                Home activity = (Home)getActivity();
                activity.displayView(0);
            }
        }).show();
        a.setCanceledOnTouchOutside(false);





        return rootView;
    }

}
