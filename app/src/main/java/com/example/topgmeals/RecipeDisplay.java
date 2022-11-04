package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
=======
import android.widget.EditText;
import android.widget.TextView;
>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);
<<<<<<< HEAD
        RecipeDisplay currentClass = RecipeDisplay.this;

        EditText title = (EditText) findViewById(R.id.title_editText);
        EditText prepTime = (EditText) findViewById(R.id.PreparationTime_editText);
        EditText servings = (EditText) findViewById(R.id.servings_editText);
        EditText category = (EditText) findViewById(R.id.Category_editText);
        EditText comments = (EditText) findViewById(R.id.Comments_editText);


        Intent intent = getIntent();
        String titleToDisplay = intent.getExtras().getString("TITLE");
        String prepTimeToDisplay = intent.getExtras().getString("PREP_TIME");
        Integer servingsToDisplay = intent.getExtras().getInt("SERVINGS");
        String categoryToDisplay = intent.getExtras().getString("CATEGORY");
        String commentsToDisplay = intent.getExtras().getString("COMMENTS");
        int position = intent.getIntExtra("POSITION",-1);

        title.setText(titleToDisplay);
        prepTime.setText(prepTimeToDisplay);
        servings.setText(servingsToDisplay.toString()); // Not displaying right servings value
        category.setText(categoryToDisplay);
        comments.setText(commentsToDisplay);



        Button ingredients = (Button) findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.delete_recipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                setResult(2,intent);
                finish();


            }
        });


        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

=======
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String prep_time = intent.getStringExtra("PREP_TIME");
        String servings = intent.getStringExtra("SERVINGS");
        String category= intent.getStringExtra("CATEGORY");
        String comments = intent.getStringExtra("COMMENTS");

        EditText title_text = findViewById(R.id.title_editText);
        EditText prep_time_text= findViewById(R.id.PreparationTime_editText);
        EditText servings_text= findViewById(R.id.servings_editText);
        EditText category_text = findViewById(R.id.Category_editText);
        EditText comments_text= findViewById(R.id.Comments_editText);

        title_text.setText(title);
        prep_time_text.setText(prep_time);
        servings_text.setText(servings);
        category_text.setText(category);
        comments_text.setText(comments);
>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265
    }
}