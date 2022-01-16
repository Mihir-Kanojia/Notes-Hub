package com.example.noteshub.ui.curd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.databinding.ActivityNotesDetailsBinding;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.model.NotesModel;
import com.example.noteshub.repository.FirestoreRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NotesDetailsActivity extends AppCompatActivity {

    private ActivityNotesDetailsBinding binding;
    private NotesModel originalNoteModel;
    private final FirestoreRepository repository = new FirestoreRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityNotesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentGET = getIntent();
        originalNoteModel = (NotesModel) intentGET.getSerializableExtra("NOTES_DETAILS");

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        originalNoteModel = (NotesModel) Objects.requireNonNull(data).getSerializableExtra("NOTES_DETAILS");
//                        Toast.makeText(NotesDetailsActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
                        initComponents();
                    }
                });

        binding.tvEditNote.setOnClickListener(view -> {
//            new ActivitySwitchManager(NotesDetailsActivity.this, CreateEditNotesActivity.class)
//                    .openActivityWithoutFinish("NOTES_DETAILS", originalNoteModel);

            Intent intent = new Intent(NotesDetailsActivity.this, CreateEditNotesActivity.class);
            intent.putExtra("NOTES_DETAILS", originalNoteModel);
            activityResultLauncher.launch(intent);


        });

        initComponents();

    }

    private void initComponents() {

        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());

        binding.tvHeading.setText(originalNoteModel.heading);
        binding.tvDescription.setText(originalNoteModel.description);
        binding.tvCreatedDate.setText(new SimpleDateFormat("dd, MMMM, yyyy", Locale.ENGLISH).format(originalNoteModel.createdDate));
        binding.tvLastUpdatedTime.setText(getTimeAgo(originalNoteModel.lastUpdatedDate));



        binding.ibDeleteNotes.setOnClickListener(view -> new AlertDialog.Builder(NotesDetailsActivity.this, R.style.DeleteDialogTheme)
                .setTitle("Delete note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> deleteCurrentNoteDocument())
                .setNegativeButton(android.R.string.no, null)
                .show());

    }

    private void deleteCurrentNoteDocument() {

        repository.getNotesCollection(Constants.UserAuthID)
                .document(originalNoteModel.id)
                .update("isDeleted", true, "lastUpdatedDate", new Date())
                .addOnSuccessListener(unused -> {
                    Toast.makeText(NotesDetailsActivity.this, "Delete successful.", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(NotesDetailsActivity.this, "Unable to delete\nCheck internet connection", Toast.LENGTH_SHORT).show());

    }


    public Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public String getTimeAgo(Date date) {

        if (date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo = null;
        Context ctx = NotesDetailsActivity.this;

        if (dim == 0) {
            timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " + ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim == 1) {
            return "Updated " + "1 " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim >= 2 && dim <= 44) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_term_an) + " " + ctx.getResources().getString(R.string.date_util_unit_hour);
        } else if (dim >= 90 && dim <= 1439) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hours);
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_day);
        } else if (dim >= 2520 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_days);
        } else if (dim >= 43200 && dim <= 86399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_month);
        } else if (dim >= 86400 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_months);
        } else if (dim >= 525600 && dim <= 655199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 655200 && dim <= 914399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " " + ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 914400 && dim <= 1051199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " + ctx.getResources().getString(R.string.date_util_unit_years);
        } else {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 525600)) + " " + ctx.getResources().getString(R.string.date_util_unit_years);
        }

        return "Updated " + timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix);
    }

    private int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }
}