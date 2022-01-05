package com.example.noteshub.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.noteshub.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}