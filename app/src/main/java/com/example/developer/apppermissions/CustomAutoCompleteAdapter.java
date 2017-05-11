package com.example.developer.apppermissions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 8/5/17.
 */

public class CustomAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private List<String> names = new ArrayList<>();
    private Context mContext;

    public CustomAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public String getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        //((TextView) convertView.findViewById(R.id.text)).setText(getItem(position));
        textView.setText(getItem(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d("constraint",constraint.toString());
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = names();
                    filterResults.count = names.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private List<String> names() {
        names.add("name_1");
        names.add("name_2");
        names.add("name_3");
        names.add("name_4");
        return names;
    }

}
