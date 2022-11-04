package com.example.topgmeals;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeBook extends AppCompatActivity  {

    ListView recipeList;
    ArrayList<Recipe> recipeBook;
    Boolean check=Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        recipeList = (ListView) findViewById(R.id.recipe_book);
        recipeBook = new ArrayList<>();
        Recipe burger = new Recipe("Burger","Half Hour", 2 , "Fast Food", "Follow the instruction as is");
        Recipe pizza = new Recipe("Pizza","15 mins", 3, "fastfood", "Follow instructions");
        recipeBook.add(burger);
        recipeBook.add(pizza);

        RecipeAdapter recipeListAdapter = new RecipeAdapter(this, R.layout.recipee_book_content, recipeBook);
        recipeList.setAdapter(recipeListAdapter);

        RecipeBook currentClass = RecipeBook.this;


<<<<<<< HEAD
        ActivityResultLauncher<Intent> viewRecipe = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 2){
                        Intent intent = result.getData();
                        int position = intent.getIntExtra("POSITION", -1);
                        assert (position != -1);
                        recipeBook.remove(position);
                        recipeListAdapter.notifyDataSetChanged();
                    }

                }

        );

=======

>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265

        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent new_intent=getIntent();


<<<<<<< HEAD
=======

>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265
                Intent intent = new Intent(currentClass, RecipeDisplay.class);
                String title = recipeBook.get(i).getTitle();
                String prep_time = recipeBook.get(i).getPrepTime();
                Integer servings = recipeBook.get(i).getServings();
                String s_servings=servings.toString();
                String category = recipeBook.get(i).getCategory();
                String comments = recipeBook.get(i).getComments();


                intent.putExtra("TITLE",title);
                intent.putExtra("PREP_TIME",prep_time);
                intent.putExtra("SERVINGS",s_servings);
                intent.putExtra("CATEGORY",category);
                intent.putExtra("COMMENTS",comments);
                intent.putExtra("POSITION",i);



                //startActivity(intent);
                viewRecipe.launch(intent);

            }
        });

        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                startActivity(intent);
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        Button add_recipe=(Button) findViewById(R.id.AddRecipe);
=======
        Button add_recipe=(Button) findViewById(R.id.add_button);

        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, addEditRecipe.class);
                startActivity(intent);
            }
        });

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);
>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265

        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                Intent intent = new Intent(currentClass, AddRecipe.class);
=======
                Intent intent = new Intent(currentClass, RecipeBook.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                check=Boolean.TRUE;
>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265
                startActivity(intent);

            }
        });
<<<<<<< HEAD

        Button RecipesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                check=Boolean.TRUE;
                startActivity(intent);


            }
        });
        //System.out.println(check);



        Recipe new_recipe= (Recipe) getIntent().getSerializableExtra("NEW");
        if (new_recipe!=null){
            recipeBook.add(new_recipe);

=======
        System.out.println(check);
        Recipe new_recipe=(Recipe) getIntent().getSerializableExtra("NEW");
        if (new_recipe!=null){
            recipeBook.add(new_recipe);
>>>>>>> 9fa0be008b0f77ce5012e4ae2af7daa3f9ec5265
        }
        //endregion

    }


}