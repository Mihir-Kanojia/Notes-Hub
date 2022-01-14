package com.example.noteshub.ui.curd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
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
    private FirestoreRepository repository = new FirestoreRepository();
    private String headingText;
    private String descriptionText;
    private NotesModel originalNoteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_notes);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null)
            originalNoteModel = (NotesModel) intent.getSerializableExtra("NOTES_DETAILS");


        initComponents();

    }

    private void initComponents() {

        if(originalNoteModel!=null){
            binding.etHeading.setText(originalNoteModel.heading);
            binding.etDescription.setText(originalNoteModel.description);
            binding.tvSelectedTag.setText(originalNoteModel.selectedTag);
            binding.etHeading.setSelection(originalNoteModel.heading.length());
        }

        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());

        binding.ibSaveBtn.setOnClickListener(view -> {
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
        });


    }

    private void addOrUpdateNoteToDatabase() {

        String selectedTagName = binding.tvSelectedTag.getText().toString().trim();

        NotesModel notesModel = new NotesModel(headingText, descriptionText, selectedTagName, false, new Date());
        repository.getNotesCollection(Constants.UserAuthID).document().set(notesModel)
                .addOnSuccessListener(unused -> {
//                            Toast.makeText(CreateEditNotesActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(CreateEditNotesActivity.this, "Try again later", Toast.LENGTH_SHORT).show());

    }
}