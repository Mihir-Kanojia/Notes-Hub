package com.example.noteshub.ui.curd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.databinding.ActivityCreateEditNotesBinding;
import com.example.noteshub.model.NotesModel;
import com.example.noteshub.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Transaction;

import java.util.Date;

public class CreateEditNotesActivity extends AppCompatActivity {

    private ActivityCreateEditNotesBinding binding;
    FirestoreRepository repository = new FirestoreRepository();
    String headingText;
    String descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_notes);
        setContentView(binding.getRoot());

        initComponents();

    }

    private void initComponents() {

        binding.ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ibSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingText = binding.etHeading.getText().toString().trim();
                descriptionText = binding.etDescription.getText().toString().trim();

                if (!headingText.isEmpty()) {
                    if (!descriptionText.isEmpty()) {
                        addOrUpdateNoteToDatabase();
                    } else {
                        Toast.makeText(CreateEditNotesActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateEditNotesActivity.this, "Heading cannot me empty", Toast.LENGTH_SHORT).show();
                }
            }

            private void addOrUpdateNoteToDatabase() {


//                repository.getNotesCollection(Constants.UserAuthID).document().add
                NotesModel notesModel = new NotesModel(headingText, descriptionText, new Date());
                repository.getNotesCollection(Constants.UserAuthID).document().set(notesModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreateEditNotesActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateEditNotesActivity.this, "Try again later", Toast.LENGTH_SHORT).show();

                            }
                        });

            }

        });


    }
}