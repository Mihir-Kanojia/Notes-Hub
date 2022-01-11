package com.example.noteshub.ui.curd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.noteshub.databinding.ActivityNotesDetailsBinding;
import com.example.noteshub.model.NotesModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NotesDetailsActivity extends AppCompatActivity {

    private ActivityNotesDetailsBinding binding;
    private NotesModel originalNoteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityNotesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        originalNoteModel = (NotesModel) intent.getSerializableExtra("NOTES_DETAILS");


        initComponents();

    }

    private void initComponents() {

        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());

        binding.tvHeading.setText(originalNoteModel.heading);
        binding.tvDescription.setText(originalNoteModel.description);
        binding.tvCreatedDate.setText(new SimpleDateFormat("dd, MMMM, yyyy", Locale.ENGLISH).format(originalNoteModel.createdDate));
//        binding.tvLastUpdatedTime.setText((CharSequence) originalNotesModel.lastUpdatedDate);


    }
}