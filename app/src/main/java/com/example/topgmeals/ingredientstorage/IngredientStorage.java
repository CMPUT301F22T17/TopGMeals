package com.example.topgmeals.ingredientstorage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class is an Activity that handles the Ingredient Storage menu. The user will be able to see
 * a list of ingredients and their information, add a new ingredient, and click on an ingredient to
 * modify or delete it.
 */
public class IngredientStorage extends AppCompatActivity {

    /**
     * An {@link ArrayList} that holds {@link Ingredient} objects.
     */
    public ArrayList<Ingredient> ingredientList;

    /**
     * A custom {@link RecyclerView.Adapter} of type {@link IngredientAdapter} that handles the view
     * of the list of ingredients.
     */
    private IngredientAdapter adapter;

    /**
     * A {@link FirebaseApp} object to be used for connecting to Firestore and getting user ID
     */
    private FirebaseApp app;

    /**
     * Unique authentication ID for each user generated by Firebase, used to identify documents by user
     */
    private String id;

    /**
     * An {@link ArrayList} of unique {@link DocumentReference} objects that belong to a user and point to
     * that user's ingredients
     */
    private ArrayList<String> refList = new ArrayList<>();

    /**
     * {@link RecyclerView} to hold ingredientList
     */
    public RecyclerView ingredientView;

    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_storage);

        // Connect to the Firestore database and get the Reference to the ingredients collection
        app = FirebaseApp.initializeApp(IngredientStorage.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ingredientsDb = db.collection("ingredients");

        // Get the user ID with FirebaseAuth
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setTitle("Ingredient Storage");

        ingredientList = new ArrayList<>();
        ingredientView = findViewById(R.id.ingredient_list);
        adapter = new IngredientAdapter(ingredientList);
        ingredientView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ingredientView.getContext(),
                layoutManager.getOrientation());
        ingredientView.addItemDecoration(dividerItemDecoration);


        // Set Sort Spinner
        Spinner sortSpinner = findViewById(R.id.sort_by_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.ingredient_sort, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sortAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Comparator<Ingredient> comparator = null;
                switch (pos) {
                    case 0:
                        comparator = Comparator.comparing(Ingredient::getDescription);
                        break;
                    case 1:
                        comparator = Comparator.comparing(Ingredient::getBestBefore);
                        break;
                    case 2:
                        comparator = Comparator.comparing(Ingredient::getLocation);
                        break;
                    case 3:
                        comparator = Comparator.comparing(Ingredient::getCategory);
                        break;
                }

                ingredientList.sort(comparator);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        // ActivityResultLauncher to launch AddEditIngredientActivity when the user clicks on an
        // item in the list
        ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // When DELETE is chosen
                    if (result.getResultCode() == 2) {
                        // Get Intent from child Activity and the position of the item to delete
                        Intent deleteIntent = result.getData();

                        // Get DocumentReference of the item in the database according to its position
                        // in refList and remove it
                        String deleteRef = deleteIntent.getStringExtra("deleted_ref");
                        ingredientsDb.document(deleteRef).delete()
                                .addOnSuccessListener(unused -> Log.d("Delete item", "Delete success"))
                                .addOnFailureListener(e -> Log.d("Delete item", "Delete failed"));
                    }
                    // When EDIT is chosen
                    else if (result.getResultCode() == Activity.RESULT_OK){
                        Intent editIntent = result.getData();
                        Ingredient ingredient = editIntent.getParcelableExtra("edited_ingredient");

                        // Get DocumentReference of the item in the database according to its position
                        // in refList and replace it with the edited object
                        HashMap<String,Object> edited = toHashMap(ingredient);
                        ingredientsDb.document(ingredient.getDocumentID()).update(edited)
                                .addOnSuccessListener(unused -> Log.d("Edit item", "Edit success"))
                                .addOnFailureListener(e -> Log.d("Edit item", "Edit failed with Exception"+ e.getMessage()));
                    }
                });

        // onClickListener object for the adapter that listens to when an item in the RecyclerView is clicked on
        View.OnClickListener onItemClickListener = view -> {
            // Get the position of the clicked item
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int pos = viewHolder.getAbsoluteAdapterPosition();
            Ingredient ingredient = ingredientList.get(pos);

            // Launch AddEditIngredientActivity so that the user can edit the clicked Ingredient
            Intent intent = new Intent(getBaseContext(), AddEditIngredientActivity.class);
            intent.putExtra("purpose", "EDIT");
            intent.putExtra("ingredient_object",ingredient);
//            intent.putExtra("position", pos);
            editActivityResultLauncher.launch(intent);
        };
        adapter.setOnItemClickListener(onItemClickListener);

        ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Get Ingredient from child Activity
                        Intent data = result.getData();
                        assert data != null;
                        Ingredient ingredient = data.getParcelableExtra("added_ingredient");
                        HashMap<String,Object> added = toHashMap(ingredient);

                        // Add Ingredient object as HashMap to database and put its DocumentReference
                        // in refList
                        DocumentReference addedRef = ingredientsDb.document();
                        addedRef.set(added)
                                .addOnSuccessListener(unused -> Log.d("success", "Added successfully"))
                                .addOnFailureListener(e -> Log.d("failure", "failed"));
                    }
                });

        // Launch addActivityResultLauncher to allow the user to add an Ingredient
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(IngredientStorage.this, AddEditIngredientActivity.class);
            intent.putExtra("purpose", "ADD");
            addActivityResultLauncher.launch(intent);
        });


        // SnapshotListener to update the list everytime the documents with the user's ID are changed
        ingredientsDb.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        ingredientList.clear();
                        assert value != null;
                        for (QueryDocumentSnapshot doc : value){
                            Ingredient ingredient = doc.toObject(Ingredient.class);
                            ingredient.setDocumentID(doc.getId());
                            ingredientList.add(ingredient);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        // region buttonswapping
        Button btnIngredientStorage = (Button) findViewById(R.id.switchToIngredientStorage);
        btnIngredientStorage.setOnClickListener(view -> {
            startActivity(new Intent(IngredientStorage.this, IngredientStorage.class));
        });

        Button btnShoppingList = (Button) findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(view -> {
            startActivity(new Intent(IngredientStorage.this, ShoppingList.class));
            finish();
        });

        Button btnMealPlanner = (Button) findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(view -> {
            startActivity(new Intent(IngredientStorage.this, MealPlan.class));
            finish();
        });

        Button btnRecipesBook = (Button) findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IngredientStorage.this, RecipeBook.class));
                finish();
            }
        });
        // endregion
    }

    /**
     * This is a helper function to put an {@link Ingredient} object to a {@link HashMap} to send to
     * Firestore database
     * @param ingredient {@link Ingredient} object to send
     * @return {@link HashMap} containing with the content of the Ingredient as well as the ID of the
     * user
     */
    private HashMap<String, Object> toHashMap(Ingredient ingredient){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("description", ingredient.getDescription());
        map.put("bestBefore", ingredient.getBestBefore());
        map.put("amount", ingredient.getAmount());
        map.put("unit", ingredient.getUnit());
        map.put("category", ingredient.getCategory());
        map.put("location", ingredient.getLocation());

        return map;
    }
}