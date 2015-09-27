package adapter;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import adapter.ClassInDay;
import io.codly.Uetface.R;


public class SubjectEvaluateListAdapter extends BaseAdapter {
    Context context;
    List<ClassInDay> rowItems;

    public SubjectEvaluateListAdapter(Context context, List<ClassInDay> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    /*private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }*/

    public View getView(int position, View convertView, ViewGroup parent) {
       // ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_subjectevaluate, null);
        }
            TextView name_class = (TextView) convertView.findViewById(R.id.name_class);
            TextView address_class = (TextView) convertView.findViewById(R.id.add_class);


            ClassInDay row = (ClassInDay) getItem(position);
            name_class.setText(row.getName_class());
            address_class.setText(row.getAddress_class());


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