package com.example.noteshub.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentDashManageBinding;


public class DashManageFragment extends BaseFragment {

    private FragmentDashManageBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashManageBinding.inflate(getLayoutInflater());

        initComponents();

        return binding.getRoot();
    }

    @Override
    public void initComponents() {

    }
}