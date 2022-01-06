package com.example.noteshub.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.noteshub.CoreApp;
import com.example.noteshub.R;
import com.example.noteshub.databinding.ActivityAuthBinding;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.ui.dashboard.DashboardActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {


    ActivityAuthBinding binding;
    NavController navController;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            new ActivitySwitchManager(this, DashboardActivity.class).openActivity();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        setContentView(binding.getRoot());
//        FirebaseApp.initializeApp(this);
        initComponents();


    }

    void initComponents() {
        navController = Navigation.findNavController(this, R.id.fragment);
        CoreApp.getInstance().setActivity(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        CoreApp.getInstance().setActivity(this);
    }

    @Override
    public void onBackPressed() {
//        if (navController.getPreviousBackStackEntry() == null) {
//            callFinish();
//        } else super.onBackPressed();

        if (navController.getGraph().getStartDestination() == navController.getCurrentDestination().getId()) {
            callFinish();
        } else {
            navController.popBackStack();
        }

    }

    private void callFinish() {

        if (doubleBackToExitPressedOnce) {
            super.finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Hit back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }
}