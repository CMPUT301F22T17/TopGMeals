package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.topgmeals.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is an Activity that handles the ADD functionality of the Recipe Book menu.
 */
public class AddEditRecipe extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    private ImageView mImageView;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipee);
        AddEditRecipe currentClass = AddEditRecipe.this;

        mImageView = findViewById(R.id.recipeImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Button add_new = findViewById(R.id.add_recipe);
        add_new.setOnClickListener(view -> {
            Intent intent_add = new Intent(currentClass, RecipeBook.class);
            EditText title = (EditText) findViewById(R.id.title_editText);
            EditText prep_time = (EditText) findViewById(R.id.prep_time_editText);
            EditText serving = (EditText) findViewById(R.id.serving_editText);
            EditText category = (EditText) findViewById(R.id.Category_editText);
            EditText comments = (EditText) findViewById(R.id.Comments_editText);

            String title_text = title.getText().toString();
            String prep_time_text = prep_time.getText().toString();
            Integer serving_text = Integer.parseInt(serving.getText().toString());
            String category_text = category.getText().toString();
            String comments_text = comments.getText().toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("title", title_text);
            data.put("prepTime", prep_time_text);
            data.put("servings", serving_text);
            data.put("category", category_text);
            data.put("comments", comments_text);
            data.put("id", uid);

            db.collection("recipes")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                        StorageReference uploadRef = mStorageRef.child("uploads/" + documentReference.getId());
                        UploadTask uploadTask = uploadRef.putFile(mImageUri);

                        // Register observers to listen for when the download is done or if it fails
                        uploadTask.addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

            // Recipe new_recipe =new Recipe(title_text,prep_time_text,3,category_text,comments_text, "si");

            startActivity(intent_add);
        });

        Button ImportImage = findViewById(R.id.import_button);
        ImportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            mImageView.setImageURI(mImageUri);
            Log.e("TT", "IMAGE");

        }
    }
}