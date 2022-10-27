package com.example.topgmeals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String prep_time = intent.getStringExtra("PREP_TIME");
        String servings = intent.getStringExtra("SERVINGS");
        String category= intent.getStringExtra("CATEGORY");
        String comments = intent.getStringExtra("COMMENTS");

        TextView title_text = findViewById(R.id.title_content);
        TextView prep_time_text= findViewById(R.id.preparation_content);
        TextView servings_text= findViewById(R.id.servings_content);
        TextView category_text = findViewById(R.id.category_content);
        TextView comments_text= findViewById(R.id.comments_content);

        title_text.setText(title);
        prep_time_text.setText(prep_time);
        servings_text.setText(servings);
        category_text.setText(category);
        comments_text.setText(comments);

    }
}
