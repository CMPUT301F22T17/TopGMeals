package com.example.topgmeals;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<IndividualRecipe> {

    private static final String TAG = "RecipeListAdapter";
    private Context context;
    private int resource;

    public RecipeListAdapter(@NonNull Context context, int resource, @NonNull List<IndividualRecipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String title = getItem(position).getRecipe_title();
        String prep_time = getItem(position).getPrep_time();
        String servings = getItem(position).getServing_count();
        String category = getItem(position).getCategory();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView title_display = (TextView) convertView.findViewById(R.id.title_display);
        TextView prep_time_display = (TextView) convertView.findViewById(R.id.prep_time_display);
        TextView servings_display = (TextView) convertView.findViewById(R.id.servings_display);
        TextView category_display = (TextView) convertView.findViewById(R.id.category_display);

        title_display.setText(title);
        prep_time_display.setText(prep_time);
        servings_display.setText(servings);
        category_display.setText(category);

        return convertView;
    }
}
