package br.com.catijr.manualdobixo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marcelo on 16/03/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private static List<IndexItem> index;

    public CustomAdapter(Context context, List<IndexItem> index) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.index = index;
    }

    public static void setIndex(List<IndexItem> index) {
        CustomAdapter.index = index;
    }

    @Override
    public int getCount() {
        return index.size();
    }

    @Override
    public IndexItem getItem(int position) {
        return index.get(position);
    }

    @Override
    public long getItemId(int position) {
        return index.get(position).getId();
    }


    public class Holder {
        TextView txt;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.index_list_item, null);

        holder.txt = (TextView) rowView.findViewById(R.id.index_list_item_text);

        holder.txt.setText(index.get(position).getTitle());


        return rowView;
    }

}
