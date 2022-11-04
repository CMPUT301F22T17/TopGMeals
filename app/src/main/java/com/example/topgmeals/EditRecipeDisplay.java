package com.example.topgmeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_layout);

        Intent intent = getIntent();
        IndividualRecipe recipe_object = (IndividualRecipe) intent.getSerializableExtra("OBJECT");

//        String title = recipe_object.getRecipe_title();
//        String prep_time = recipe_object.getPrep_time();
//        String servings = recipe_object.getServing_count();
//        String category = recipe_object.getCategory();
//        String comments = recipe_object.getComments();

        EditText new_title = findViewById(R.id.new_title);
        EditText new_prep_time = findViewById(R.id.new_prep_time);
        EditText new_servings = findViewById(R.id.new_servings);
        EditText new_category = findViewById(R.id.new_category);
        EditText new_comments= findViewById(R.id.new_comments);
        Button cancel_button = (Button) findViewById(R.id.cancel);

//        new_title.setHint(title);
//        new_prep_time.setHint(prep_time);
//        new_servings.setHint(servings);
//        new_category.setHint(category);
//        new_comments.setHint(comments);



    }
}
