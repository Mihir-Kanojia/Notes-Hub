package com.example.noteshub.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.noteshub.CoreApp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public Activity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = CoreApp.getInstance().getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CoreApp.getInstance().getActivity() != null)
            activity = CoreApp.getInstance().getActivity();
    }

    public abstract void initComponents();
}
