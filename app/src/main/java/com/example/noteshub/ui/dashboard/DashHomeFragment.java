package com.example.noteshub.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentDashHomeBinding;


public class DashHomeFragment extends BaseFragment {

    private FragmentDashHomeBinding binding;


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



    }

}