package com.beauty.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beauty.myapplication.R;
import com.beauty.myapplication.fragment.HomeFragment;
import com.beauty.myapplication.utils.AppUtils;

public class MainActivity extends AppCompatActivity {


    private boolean clickAgainToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
    }

    private void loadFragment(HomeFragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fl_container, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // fragmentManager.popBackStackImmediate();
        if (fragmentManager.getBackStackEntryCount() == 1) {

            if (clickAgainToExit) {

                super.onBackPressed();
                finish();
                return;
            }
            clickAgainToExit = true;
            AppUtils.ShortToast(MainActivity.this, getResources().getString(R.string.app_backpress));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickAgainToExit = false;
                }
            }, 2000);
        } else {

            super.onBackPressed();
        }
    }

}
