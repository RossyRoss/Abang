package capstone.abang.com.Car_Owner.Endorse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;

import capstone.abang.com.R;

public class BasicDetails extends AppCompatActivity {


    Toolbar toolbar;
    TextView toolbar_title;

    EditText etMaker, etModel, etYear, etPlateNo, etMaxCap, etChassisNo, etEngineNo,etAreaOfRestriction;
    RadioGroup Transmission, ServiceType;
    RadioButton rbSelfDrive, rbWithDriver, rbManual, rbAutomatic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);


        etMaker = findViewById(R.id.etMaker);
        etModel = findViewById(R.id.etModel);
        etYear  = findViewById(R.id.etYear);
        etPlateNo = findViewById(R.id.etPlateNumber);
        etMaxCap = findViewById(R.id.etCapacity);
        etChassisNo = findViewById(R.id.etChassisNo);
        etEngineNo = findViewById(R.id.etEngineNo);
        etAreaOfRestriction = findViewById(R.id.etAreaOfRestriction);
        Transmission = findViewById(R.id.Transmission);
        ServiceType = findViewById(R.id.ServiceType);
        rbSelfDrive = findViewById(R.id.SelfDrive);
        rbWithDriver = findViewById(R.id.WithDriver);
        rbManual = findViewById(R.id.radioManual);
        rbAutomatic = findViewById(R.id.radioAutomatic);


        etYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMaxCap.setInputType(InputType.TYPE_CLASS_NUMBER);
        etAreaOfRestriction.setInputType(InputType.TYPE_CLASS_NUMBER);


        Utils.initToolBar(this, toolbar, true);
        toolbar_title.setText("Add Basic Details");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.vlk.multimager.R.menu.add_dents_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        else {

            setInit();

            return true;
        }
    }


    private void setInit() {



       String HolderMaker = etMaker.getText().toString().trim();
        String HolderModel = etModel.getText().toString().trim();
        String HolderYear = etYear.getText().toString().trim();
       String HolderCapacity = etMaxCap.getText().toString().trim();
        String HolderPlateNumber = etPlateNo.getText().toString().trim();
        String HolderChassisNo = etChassisNo.getText().toString().trim();
        String HolderEngineNo = etEngineNo.getText().toString().trim();
        String HolderAreaOfRestriction = etAreaOfRestriction.getText().toString().trim();
        String str = null;
        if(!TextUtils.isEmpty(HolderMaker) && !TextUtils.isEmpty(HolderModel) && !TextUtils.isEmpty(HolderYear) &&
                !TextUtils.isEmpty(HolderCapacity) && !TextUtils.isEmpty(HolderPlateNumber) && !TextUtils.isEmpty(HolderChassisNo) &&
                !TextUtils.isEmpty(HolderEngineNo) && Transmission.getCheckedRadioButtonId() != -1 && ServiceType.getCheckedRadioButtonId() != -1)  {

            Intent oldIntent = getIntent();
            final ArrayList<Image> imagesList = oldIntent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
            final String CatCodeHolder = oldIntent.getStringExtra("KEY");



              Intent intent = new Intent(BasicDetails.this, AttachDocuments.class);
              intent.putExtra("Maker", HolderMaker);
            intent.putExtra("Model", HolderModel);
            intent.putExtra("Year", HolderYear);
            intent.putExtra("Capacity", HolderCapacity);
            intent.putExtra("PlateNumber", HolderPlateNumber);
            intent.putExtra("ChassisNo", HolderChassisNo);
            intent.putExtra("EngineNo", HolderEngineNo);
            intent.putParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST, imagesList);
            intent.putExtra("KEY",CatCodeHolder);
            intent.putExtra("AreaOfRestriction",HolderAreaOfRestriction);


            if(rbAutomatic.isChecked()) {
                str = "Automatic";
            } else if(rbManual.isChecked()) {
                str = "Manual";
            }

            intent.putExtra("Transmission", str);

            if(rbSelfDrive.isChecked()) {
                str = "Self Drive";
            } else if(rbWithDriver.isChecked()) {
                str = "With Driver";
            }
            intent.putExtra("ServiceType", str);
                startActivity(intent);
        }
        else {
           Toast.makeText(BasicDetails.this ,"Input all fields",Toast.LENGTH_SHORT).show();
        }
    }

}
