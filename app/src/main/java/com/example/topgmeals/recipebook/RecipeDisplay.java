package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.topgmeals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Activity that handles the Recipe Display menu. The user will be able to see
 * the information of each recipe, and edit and delete a recipe.
 */
public class RecipeDisplay extends AppCompatActivity {

    private StorageReference mStorageRef;
    private Context context;

    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for displaying a {@link Recipe}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);
        RecipeDisplay currentClass = RecipeDisplay.this;

        mStorageRef = FirebaseStorage.getInstance().getReference();

        EditText title = (EditText) findViewById(R.id.title_editText);
        EditText prepTime = (EditText) findViewById(R.id.PreparationTime_editText);
        EditText servings = (EditText) findViewById(R.id.servings_editText);
        EditText category = (EditText) findViewById(R.id.Category_editText);
        EditText comments = (EditText) findViewById(R.id.Comments_editText);

        Intent dataIntent = getIntent();

        String titleToDisplay = dataIntent.getExtras().getString("TITLE");
        String prepTimeToDisplay = dataIntent.getExtras().getString("PREP_TIME");
        String servingsToDisplay = dataIntent.getStringExtra("SERVINGS");
        String categoryToDisplay = dataIntent.getExtras().getString("CATEGORY");
        String commentsToDisplay = dataIntent.getExtras().getString("COMMENTS");
        String recipeID = dataIntent.getExtras().getString("RecipeID");
        int position = dataIntent.getIntExtra("POSITION",-1);

        title.setText(titleToDisplay);
        prepTime.setText(prepTimeToDisplay);
        servings.setText(servingsToDisplay);
        category.setText(categoryToDisplay);
        comments.setText(commentsToDisplay);

        ImageView recImg = (ImageView) findViewById(R.id.recImage);
        mStorageRef.child("uploads/" + recipeID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {
                    Glide.with(RecipeDisplay.this).load(uri.toString()).into(recImg);
                } catch (Exception e){
                    Log.e("Error", e.toString());
                }
            }
        }).addOnFailureListener(exception -> {
            if (recImg.getDrawable() == null){
                recImg.setImageResource(R.drawable.mealplan);
            }
        });


        Button ingredients = (Button) findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);

                intent.putExtra("RECIPE_ID", recipeID);
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        /* Performs the edit recipe button functionality */
        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();
                data.put("title", title.getText().toString());
                if (title.getText().toString().isEmpty()) {
                    title.setError("Title is required!");
                    title.requestFocus();
                    return;
                }

                data.put("prepTime", prepTime.getText().toString());
                if (prepTime.getText().toString().isEmpty()){
                    prepTime.setError("Preparation time is required!");
                    prepTime.requestFocus();
                    return;
                }
                if (prepTime.getText().toString().compareTo("0")==0){
                    prepTime.setError("Preparation time Cannot be 0!");
                    prepTime.requestFocus();
                    return;
                }
                if (prepTime.getText().toString().compareTo("00")==0){
                    prepTime.setError("Preparation time Cannot be 0!");
                    prepTime.requestFocus();
                    return;
                }
                if (prepTime.getText().toString().compareTo("000")==0){
                    prepTime.setError("Preparation time Cannot be 0!");
                    prepTime.requestFocus();
                    return;
                }

                data.put("servings", Integer.valueOf(servings.getText().toString()));
                if (Integer.valueOf(servings.getText().toString()).equals("")) {
                    servings.setError("Servings is required!");
                    servings.requestFocus();
                    return;
                }
                if (Integer.valueOf(servings.getText().toString()).equals(0) || Integer.valueOf(servings.getText().toString()).equals(00) || Integer.valueOf(servings.getText().toString()).equals(000)) {
                    servings.setError("Servings Cannot be 0!");
                    servings.requestFocus();
                    return;
                }

                data.put("category", category.getText().toString());
                if (category.getText().toString().isEmpty()) {
                    category.setError("Category is required!");
                    category.requestFocus();
                    return;
                }

                data.put("comments", comments.getText().toString());
                if (comments.getText().toString().isEmpty()) {
                    comments.setError("Category is required!");
                    comments.requestFocus();
                    return;
                }
                data.put("id", uid);

                db.collection("recipes").document(recipeID)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);


            }
        });


        /* Performs the delete recipe button functionality */
        Button delete = (Button) findViewById(R.id.delete_recipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("recipes").document(recipeID)
                        .delete()
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                db.collection("mealplan").whereEqualTo("ref", recipeID)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("mealplan").document(document.getId()).delete();
                                    Log.d("DELETE MEAL", "Meal deleted after ingredient deleted");
                                }
                            } else {
                                Log.d("DELETE MEAL", "Error getting documents: ", task.getException());
                            }
                        });


                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
                currentClass.finish();

            }
        });

        /* Performs the back button functionality */
        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
                currentClass.finish();
            }
        });



    }
}