package com.example.topgmeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDisplay extends AppCompatActivity implements EditRecipeDialog.DialogListner {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        Button delete_recipe = (Button) findViewById(R.id.del_recipe_button);

        Intent intent = getIntent();
        IndividualRecipe recipe_object = (IndividualRecipe) intent.getSerializableExtra("OBJECT");

        String title = recipe_object.getRecipe_title();
        String prep_time = recipe_object.getPrep_time();
        String servings = recipe_object.getServing_count();
        String category= recipe_object.getCategory();
        String comments = recipe_object.getComments();

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

        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        delete_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public void openDialog(){
        EditRecipeDialog dialog = new EditRecipeDialog();
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void applyText(String title, String prep_time, String servings, String category, String comments) {

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
