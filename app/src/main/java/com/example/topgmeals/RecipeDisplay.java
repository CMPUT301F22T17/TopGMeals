package com.example.topgmeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDisplay extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
<<<<<<< Updated upstream
        IndividualRecipe recipe_object = (IndividualRecipe) intent.getSerializableExtra("OBJECT");
        setContentView(R.layout.activity_recipe_view);
        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        Button delete_recipe = (Button) findViewById(R.id.del_recipe_button);

        String title = recipe_object.getRecipe_title();
        String prep_time = recipe_object.getPrep_time();
        String servings = recipe_object.getServing_count();
        String category= recipe_object.getCategory();
        String comments = recipe_object.getComments();

//        TextView title_text = findViewById(R.id.title_content);
//        TextView prep_time_text= findViewById(R.id.preparation_content);
//        TextView servings_text= findViewById(R.id.servings_content);
//        TextView category_text = findViewById(R.id.category_content);
//        TextView comments_text= findViewById(R.id.comments_content);

        EditText title_text =  findViewById(R.id.title_content);
        EditText prep_time_text= findViewById(R.id.preparation_content);
        EditText servings_text= findViewById(R.id.servings_content);
        EditText category_text = findViewById(R.id.category_content);
        EditText comments_text= findViewById(R.id.comments_content);

        title_text.setText(title);
        prep_time_text.setText(prep_time);
        servings_text.setText(servings);
        category_text.setText(category);
        comments_text.setText(comments);

        edit_recipe.setOnClickListener(new View.OnClickListener() {
=======
        Recipe recipe_object = (Recipe) intent.getSerializableExtra("OBJECT");

        int position = intent.getIntExtra("POSITION",-1);

        title.setText(recipe_object.getTitle());
        prepTime.setText(recipe_object.getPrepTime());
        servings.setText(recipe_object.getServings().toString()); // Not displaying right servings value
        category.setText(recipe_object.getCategory());
        comments.setText(recipe_object.getComments());

        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_object.setTitle(title.getText().toString());
                recipe_object.setPrepTime(prepTime.getText().toString());
                recipe_object.setServings(Integer.valueOf(servings.getText().toString()));
                recipe_object.setCategory(category.getText().toString());
                recipe_object.setComments(comments.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                intent.putExtra("UPDATED OBJECT",recipe_object);
                setResult(3,intent);
                finish();
            }
        });

        Button ingredients = (Button) findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
>>>>>>> Stashed changes
            @Override
            public void onClick(View view) {
                String new_title = title_text.getText().toString();
                String new_prep_time = prep_time_text.getText().toString();
                String new_servings = servings_text.getText().toString();
                String new_category = category_text.getText().toString();
                String new_comments = comments_text.getText().toString();

                recipe_object.setRecipe_title(new_title);
                recipe_object.setPrep_time(new_prep_time);
                recipe_object.setCategory(new_category);
                recipe_object.setServing_count(new_servings);
                recipe_object.setComments(new_comments);

//                title_text.setText(new_title);
//                prep_time_text.setText(new_prep_time);
//                servings_text.setText(new_servings);
//                category_text.setText(new_category);
//                comments_text.setText(new_comments);
            }
        });

        delete_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< Updated upstream
=======
                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                setResult(2,intent);
                finish();
            }
        });
>>>>>>> Stashed changes

            }
        });

    }
}
