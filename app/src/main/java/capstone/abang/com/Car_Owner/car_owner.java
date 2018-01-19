package capstone.abang.com.Car_Owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import capstone.abang.com.Car_Owner.Home.HomeFragment;
import capstone.abang.com.Car_Owner.Profile.ProfileFragment;
import capstone.abang.com.Utils.BottomNavigationViewHelper;
import capstone.abang.com.R;

import com.vlk.multimager.activities.BaseActivity;
import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.activities.MultiCameraActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Params;


import butterknife.ButterKnife;

public class car_owner extends BaseActivity {
    private static final String TAG = car_owner.class.getSimpleName();
    int selectedColor;
  int lastPosition = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    lastPosition = 0;
                    transaction.replace(R.id.container,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    lastPosition = 1;

                    return true;

                case R.id.navigation_history:
                    lastPosition = 3;

                    return true;
                case R.id.navigation_profile:
                    lastPosition = 4;
                    transaction.replace(R.id.container, new ProfileFragment()).commit();
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_owner);


        ButterKnife.bind(this);
        selectedColor = 4475389;


        BottomNavigationView navigation = findViewById(R.id.navigation);

        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,new HomeFragment()).commit();

        FloatingActionButton endorse = findViewById(R.id.endorseFab);

        endorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateMultiPicker();
            }
        });
    }
    private void initiateMultiCapture() {
        Intent intent = new Intent(this, MultiCameraActivity.class);
        Params params = new Params();
        params.setCaptureLimit(4);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);
    }

    private void initiateMultiPicker() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(4);
        params.setPickerLimit(4);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }

}