package com.example.noteshub.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentDashHomeBinding;
import com.example.noteshub.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;


public class DashHomeFragment extends BaseFragment {

    private FragmentDashHomeBinding binding;
    private FirestoreRepository repository = new FirestoreRepository();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//
//        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashHomeBinding.inflate(getLayoutInflater());

        initComponents();

        return binding.getRoot();
    }

    @Override
    public void initComponents() {

        activity = requireActivity();

        repository.getUserProfileCollection().document(Constants.UserAuthID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = documentSnapshot.getString("USER_NAME");
                        if (username != null && username.length() > 20) {
                            username = username.substring(0, 20);
                        }
                        username = getString(R.string.hi) + " " + username + ",";
                        int profileNumber = Objects.requireNonNull(documentSnapshot.getLong("PROFILE_NUMBER")).intValue();

                        binding.tvGreetUser.setText(username);
                        int imageRes;
                        switch (profileNumber) {
                            case 1:
                                imageRes = R.drawable.profile_1;
                                break;
                            case 2:
                                imageRes = R.drawable.profile_2;
                                break;
                            case 3:
                                imageRes = R.drawable.profile_3;
                                break;
                            case 4:
                                imageRes = R.drawable.profile_4;
                                break;
                            case 5:
                                imageRes = R.drawable.profile_5;
                                break;
                            default:
                                imageRes = R.drawable.profile_6;
                                break;

                        }
                        binding.profileImage.setImageResource(imageRes);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(activity, "Try again later.", Toast.LENGTH_SHORT).show());


    }

}