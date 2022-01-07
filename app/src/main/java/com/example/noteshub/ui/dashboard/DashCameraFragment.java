package com.example.noteshub.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentDashCameraBinding;


public class DashCameraFragment extends BaseFragment {

    private FragmentDashCameraBinding binding;

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
        binding = FragmentDashCameraBinding.inflate(getLayoutInflater());





        return binding.getRoot();
    }

    @Override
    public void initComponents() {

    }

}