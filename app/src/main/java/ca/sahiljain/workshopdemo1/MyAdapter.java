package ca.sahiljain.workshopdemo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).hashCode();
    }

    public void setData(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.text_title);
        titleView.setText(getItem(position).getTitle());
        return convertView;
    }
}