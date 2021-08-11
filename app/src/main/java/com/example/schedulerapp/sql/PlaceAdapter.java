package com.example.schedulerapp.sql;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter {


    private Activity context;
    private List<Place> places;

    static class ViewHolder {
        public ImageView img;
        public TextView txtTitle;
        public TextView txtDescription;
    }

    public PlaceAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
