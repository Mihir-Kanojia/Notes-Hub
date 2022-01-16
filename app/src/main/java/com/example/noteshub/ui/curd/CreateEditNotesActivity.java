package com.example.noteshub.ui.curd;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class CreateEditNotesActivity extends AppCompatActivity {

    private ActivityCreateEditNotesBinding binding;
    private final FirestoreRepository repository = new FirestoreRepository();
    private String headingText;
    private String descriptionText;
    private NotesModel originalNoteModel;
    private boolean isForEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_notes);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            originalNoteModel = (NotesModel) intent.getSerializableExtra("NOTES_DETAILS");
            isForEditing = true;
        }


        initComponents();

    }

    @Override
    public void onBackPressed() {

//        if(isForEditing){
//            Intent intent = new Intent();
//            intent.putExtra("NOTES_DETAILS", originalNoteModel);
//            setResult(RESULT_OK, intent);
//            finish();
//        }

        super.onBackPressed();
    }

    private void initComponents() {

        if (isForEditing) {
            binding.etHeading.setText(originalNoteModel.heading);
            binding.etDescription.setText(originalNoteModel.description);
            binding.tvSelectedTag.setText(originalNoteModel.selectedTag);
            binding.etHeading.setSelection(originalNoteModel.heading.length());
            binding.etDescription.setSelection(originalNoteModel.description.length());
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


        if (isForEditing) {

            boolean isHeadingEdited = !headingText.equals(originalNoteModel.heading);
            boolean isDescriptionEdited = !descriptionText.equals(originalNoteModel.description);

            if (!isHeadingEdited && !isDescriptionEdited) {
                Toast.makeText(CreateEditNotesActivity.this, "No changes made in document.", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> updatedMap = new HashMap<>();

            updatedMap.put("lastUpdatedDate", new Date());

            if (isHeadingEdited)
                updatedMap.put("heading", headingText);

            if (isDescriptionEdited)
                updatedMap.put("description", descriptionText);

            NotesModel modell =  new NotesModel(headingText,descriptionText, originalNoteModel.selectedTag, originalNoteModel.isDeleted, new Date());

            repository.getNotesCollection(Constants.UserAuthID)
                    .document(originalNoteModel.id)
                    .update(updatedMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(CreateEditNotesActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("NOTES_DETAILS", modell);
                        setResult(RESULT_OK, intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(CreateEditNotesActivity.this, "Unable to save changes\nTry again", Toast.LENGTH_SHORT).show());


        } else {


            NotesModel notesModel = new NotesModel(headingText, descriptionText, selectedTagName, false, new Date());
            repository.getNotesCollection(Constants.UserAuthID).document().set(notesModel)
                    .addOnSuccessListener(unused -> {
//                            Toast.makeText(CreateEditNotesActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(CreateEditNotesActivity.this, "Try again later", Toast.LENGTH_SHORT).show());

        }
    }
}