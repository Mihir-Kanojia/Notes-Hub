package com.example.noteshub.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentNameProfileBinding;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.repository.FirestoreRepository;
import com.example.noteshub.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class NameProfileFragment extends BaseFragment {


    private FragmentNameProfileBinding binding;
    int selectedProfileNumber = -1;
    private FirestoreRepository repository = new FirestoreRepository();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNameProfileBinding.inflate(getLayoutInflater());
        initComponents();
        return binding.getRoot();
    }

    @Override
    public void initComponents() {

        activity = requireActivity();

        binding.btnSave.setOnClickListener(view -> {

            if (binding.etName.getText() != null) {
                if (selectedProfileNumber != -1) {
                    updateNameAndProfile();
                } else {
                    Toast.makeText(activity, "Choose a avatar", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity, "Name is required", Toast.LENGTH_SHORT).show();
            }
        });

        binding.profile1.setOnClickListener(view -> updateImageInUi(binding.profile1, R.id.profile1, 1));
        binding.profile2.setOnClickListener(view -> updateImageInUi(binding.profile2, R.id.profile2, 2));
        binding.profile3.setOnClickListener(view -> updateImageInUi(binding.profile3, R.id.profile3, 3));
        binding.profile4.setOnClickListener(view -> updateImageInUi(binding.profile4, R.id.profile4, 4));
        binding.profile5.setOnClickListener(view -> updateImageInUi(binding.profile5, R.id.profile5, 5));
        binding.profile6.setOnClickListener(view -> updateImageInUi(binding.profile6, R.id.profile6, 6));
    }

    private void updateNameAndProfile() {

        String name = Objects.requireNonNull(binding.etName.getText()).toString().trim();
        Map<String, Object> userNameProf = new HashMap<>();
        userNameProf.put("USER_NAME", name);
        userNameProf.put("PROFILE_NUMBER", selectedProfileNumber);

        repository.getUserProfileCollection().document(Constants.UserAuthID).set(userNameProf)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(activity, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    new ActivitySwitchManager(activity, DashboardActivity.class).openActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(activity, "Try again later.", Toast.LENGTH_SHORT).show());

    }

    private void updateImageInUi(ImageButton selectedProfileImageButton, int profileId, int position) {

        selectedProfileNumber = position;

        binding.profile1.setImageAlpha(255);
        binding.profile2.setImageAlpha(255);
        binding.profile3.setImageAlpha(255);
        binding.profile4.setImageAlpha(255);
        binding.profile5.setImageAlpha(255);
        binding.profile6.setImageAlpha(255);
        selectedProfileImageButton.setImageAlpha(75);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.constraintLayout);
        constraintSet.connect(R.id.tvSelected, ConstraintSet.START, profileId, ConstraintSet.START);
        constraintSet.connect(R.id.tvSelected, ConstraintSet.END, profileId, ConstraintSet.END);
        constraintSet.connect(R.id.tvSelected, ConstraintSet.TOP, profileId, ConstraintSet.TOP);
        constraintSet.connect(R.id.tvSelected, ConstraintSet.BOTTOM, profileId, ConstraintSet.BOTTOM);
        constraintSet.applyTo(binding.constraintLayout);
        binding.tvSelected.setVisibility(View.VISIBLE);
    }
}