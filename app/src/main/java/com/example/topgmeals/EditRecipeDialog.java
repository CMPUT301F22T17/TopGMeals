package com.example.topgmeals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditRecipeDialog extends AppCompatDialogFragment {

    private EditText title;
    private EditText prep_time;
    private EditText servings;
    private EditText category;
    private EditText comments;

    private DialogListner listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle("Edit Recipe")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String new_title = title.getText().toString();
                        String new_prep_time = prep_time.getText().toString();
                        String new_servings = servings.getText().toString();
                        String new_category = category.getText().toString();
                        String new_comments = comments.getText().toString();
                        listener.applyText(new_title,new_prep_time, new_servings, new_category, new_comments);
                    }
                });
        title = view.findViewById(R.id.new_title);
        prep_time = view.findViewById(R.id.new_prep_time);
        servings = view.findViewById(R.id.new_servings);
        category = view.findViewById(R.id.new_category);
        comments = view.findViewById(R.id.new_comments);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (DialogListner) context;
    }

    public interface DialogListner{
        void applyText(String title, String prep_time, String servings, String category, String comments);
    }
}
