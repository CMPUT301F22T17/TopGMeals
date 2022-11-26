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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Activity that handles the Recipe Display menu. The user will be able to see
 * the information of each recipe, and edit and delete a recipe
 */
public class RecipeDisplay extends AppCompatActivity {

    private StorageReference mStorageRef;
    private Context context;


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

                Log.e("madeit", uri.toString());
                //temp[0] = uri;
                Glide.with(RecipeDisplay.this).load(uri.toString()).into(recImg);
                //title_display.setText(title.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
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

        //  Edit a recipe
        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();
                data.put("title", title.getText().toString());
                data.put("prepTime", prepTime.getText().toString());
                data.put("servings", Integer.valueOf(servings.getText().toString()));
                data.put("category", category.getText().toString());
                data.put("comments", comments.getText().toString());
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


        // Delete a recipe
        Button delete = (Button) findViewById(R.id.delete_recipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("recipes").document(recipeID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });


                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
                currentClass.finish();

            }
        });


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