package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity {

    ListView recipeList;
    ArrayList<IndividualRecipe> recipeBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        recipeList = (ListView) findViewById(R.id.recipeListView);
        recipeBook = new ArrayList<>();
        IndividualRecipe burger = new IndividualRecipe("Burger","Half Hour", 2 , "Fast Food", "Follow the instruction as is");
        IndividualRecipe pizza = new IndividualRecipe("Pizza","15 mins", 3, "fastfood", "Follow instructions");
        recipeBook.add(burger);
        recipeBook.add(pizza);

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, R.layout.list_format, recipeBook);
        recipeList.setAdapter(recipeListAdapter);

        Recipes currentClass = Recipes.this;

        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(currentClass, RecipeDisplay.class);
                IndividualRecipe recipe_object = recipeBook.get(i);
                intent.putExtra("OBJECT", recipe_object);
                startActivity(intent);
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

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, Recipes.class);
                startActivity(intent);
            }
        });
        //endregion

    }
}