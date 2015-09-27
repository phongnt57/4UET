package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import io.codly.Uetface.R;

/**
 * Created by phong on 4/30/2015.
 */
public class FinalExamAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Day> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Day, List<ClassInDay>> _listDataChild;

    public FinalExamAdapter(Context context, List<Day> listDataHeader,
                                 HashMap<Day, List<ClassInDay>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public ClassInDay getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ClassInDay childText =  getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_timetable, null);
        }

        TextView address = (TextView) convertView
                .findViewById(R.id.address_class);
        TextView name = (TextView)convertView
                .findViewById(R.id.name_class);
        TextView time = (TextView)convertView
                .findViewById(R.id.time_class);

        address.setText(childText.getAddress_class());
        name.setText(childText.getName_class());
        time.setText(childText.getTime_class());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Day getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Day headerTitle =  getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_subjectevaluate, null);
        }

        TextView dayHeader = (TextView) convertView
                .findViewById(R.id.name_class);
        TextView numberHeader = (TextView)convertView
                .findViewById(R.id.add_class);
        dayHeader.setTypeface(null, Typeface.BOLD);
        dayHeader.setText(headerTitle.getDays());
        numberHeader.setText(headerTitle.getNumberclass());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

