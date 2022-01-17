package com.example.noteshub.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.CoreApp;
import com.example.noteshub.R;
import com.example.noteshub.databinding.ActivityDashboardBinding;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.repository.FirestoreRepository;
import com.example.noteshub.ui.curd.CreateEditNotesActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity implements DashHomeFragment.FragmentDashHomeListener {

    private ActivityDashboardBinding binding;
    boolean doubleBackToExitPressedOnce = false;
    NavController navController;
    private FirestoreRepository repository = new FirestoreRepository();

    DashHomeFragment dashHomeFragment = new DashHomeFragment();
    DashCameraFragment dashCameraFragment = new DashCameraFragment();
    DashManageFragment dashManageFragment = new DashManageFragment();
    DashBinFragment dashBinFragment = new DashBinFragment();
    private TapTargetView tapTargetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initComponents();
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    if (id == R.id.dashHomeFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashFragment, dashHomeFragment).commit();
                        return true;
                    } else if (id == R.id.dashCameraFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashFragment, dashCameraFragment).commit();
                        return true;
                    } else if (id == R.id.dashManageFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashFragment, dashManageFragment).commit();
                        return true;
                    } else if (id == R.id.dashBinFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashFragment, dashBinFragment).commit();
                        return true;
                    }

                    return true;
                }
            };

    private void initComponents() {
        CoreApp.getInstance().setActivity(this);


        navController = Navigation.findNavController(this, R.id.dashFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemSelectedListener(navListener);

        binding.fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ActivitySwitchManager(DashboardActivity.this, CreateEditNotesActivity.class).openActivityWithoutFinish();
            }
        });
    }

    public void showTapTargetView() {

//        TapTargetView.showFor(this, TapTarget.forView(binding.fabCreateNote, "Let's get started", "Click here, To add your first note")
//                        .outerCircleColor(R.color.mango_tango)
//                        .outerCircleAlpha(0.86f)
//                        .targetCircleColor(R.color.black)
//                        .titleTextSize(14)
//                        .titleTextColor(R.color.white)
//                        .descriptionTextColor(R.color.white)
//                        .textColor(R.color.white)
//                        .textTypeface(Typeface.SANS_SERIF)
//                        .dimColor(R.color.black)
//                        .drawShadow(true)
//                        .cancelable(false)
//                        .tintTarget(true)
//                        .transparentTarget(true)
//                        .targetRadius(70),
//                new TapTargetView.Listener() {
//
//                    @Override
//                    public void onTargetClick(TapTargetView view) {
//                        super.onTargetClick(view);
//                        new ActivitySwitchManager(DashboardActivity.this, CreateEditNotesActivity.class).openActivityWithoutFinish();
//
//                    }
//                });


        tapTargetView = TapTargetView.showFor(this, TapTarget.forView(binding.fabCreateNote, "Let's get started", "Click here, To add your first note")
                        .outerCircleColor(R.color.mango_tango)
                        .outerCircleAlpha(0.86f)
                        .targetCircleColor(R.color.black)
                        .titleTextSize(14)
                        .titleTextColor(R.color.white)
                        .descriptionTextColor(R.color.white)
                        .textColor(R.color.white)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(70),
                new TapTargetView.Listener() {

                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        view.dismiss(true);
                        new ActivitySwitchManager(DashboardActivity.this, CreateEditNotesActivity.class).openActivityWithoutFinish();

                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (tapTargetView != null) {
//            if (tapTargetView.isShown())
                tapTargetView.dismiss(false);

            if(tapTargetView.isVisible()){
                Log.d("TAG", "onResume: asdlf");
            }
        }


    }

    @Override
    public void onBackPressed() {
        if (navController.getGraph().getStartDestination() == navController.getCurrentDestination().getId())
            callFinish();
        else
            navController.popBackStack();
    }

    public void callFinish() {

        if (doubleBackToExitPressedOnce) {
            super.finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Hit back to exit", Toast.LENGTH_SHORT).show();
        new Handler(getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }

    @Override
    public void noNotesCreatedYet() {
        showTapTargetView();
    }
}