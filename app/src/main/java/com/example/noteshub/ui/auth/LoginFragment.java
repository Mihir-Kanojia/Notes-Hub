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

import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends BaseFragment {


    private static final String TAG = "LoginAUTH";
    private FragmentLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

        }
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
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(activity, "Authentication failed/nTry again later", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d(TAG, "onClick: "+e);
                            Toast.makeText(activity, "Authentication failed/nTry again later", Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        Log.d(TAG, "updateUI: " + user);
        Navigation.findNavController(activity, R.id.fragment).navigate(R.id.action_loginFragment_to_nameProfileFragment);
    }

    @Override
    public void initComponents() {

        activity = requireActivity();
        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseApp.initializeApp(activity);

//        binding.llContinueAsGuest.setOnClickListener(view -> Navigation.findNavController(activity, R.id.fragment).navigate(R.id.action_loginFragment_to_nameProfileFragment));


    }
}