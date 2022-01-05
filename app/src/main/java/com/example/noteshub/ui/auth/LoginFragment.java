package com.example.noteshub.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentLoginBinding;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.ui.dashboard.DashboardActivity;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends BaseFragment {


    private FragmentLoginBinding binding;
    private static final String TAG = "LoginAUTH";
    private FirebaseAuth firebaseAuth;

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

        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        initComponents();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.llContinueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInAnonymously()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String user = firebaseAuth.getCurrentUser().getUid();
                                updateUI(user);
                            } else {
                                Toast.makeText(activity, "Authentication failed/nTry again later", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d(TAG, "onClick: " + e);
                            Toast.makeText(activity, "Authentication failed/nTry again later", Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    private void updateUI(String userId) {
        Log.d(TAG, "updateUI: " + userId);
        Constants.UserAuthID = userId;
        Bundle bundle = new Bundle();
        bundle.putString("USERID", userId);
        Navigation.findNavController(activity, R.id.fragment).navigate(R.id.action_loginFragment_to_nameProfileFragment, bundle);
    }

    @Override
    public void initComponents() {

        activity = requireActivity();
        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseApp.initializeApp(activity);

//        binding.llContinueAsGuest.setOnClickListener(view -> Navigation.findNavController(activity, R.id.fragment).navigate(R.id.action_loginFragment_to_nameProfileFragment));


    }
}