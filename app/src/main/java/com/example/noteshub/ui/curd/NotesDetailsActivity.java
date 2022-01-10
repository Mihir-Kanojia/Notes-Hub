package com.example.noteshub.ui.curd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.noteshub.R;
import com.example.noteshub.databinding.ActivityCreateEditNotesBinding;

public class NotesDetailsActivity extends AppCompatActivity {

    private ActivityCreateEditNotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_details);
        setContentView(binding.getRoot());




    }
}