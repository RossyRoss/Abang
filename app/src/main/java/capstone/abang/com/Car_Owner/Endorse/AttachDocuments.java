package capstone.abang.com.Car_Owner.Endorse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vlk.multimager.activities.SinglePickActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import capstone.abang.com.R;

public class AttachDocuments extends AppCompatActivity {


    ImageButton btnGarage;
    ImageButton btnORCR,btnAddDoc1,btnAddDoc2;
    Button Enroll;

    int selectedColor;

    private int OR_CR = 0;
    private int GARAGE = 1;
    private int SUPP_IMG_1 = 2;
    private int SUPP_IMG_2 = 3;
    private DatabaseReference mDatabaseCarDetail;

    Toolbar toolbar;
    TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_documents);

        mDatabaseCarDetail = FirebaseDatabase.getInstance().getReference("CDFile");

        toolbar =  findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        btnGarage = findViewById(R.id.btnGARAGE);
        btnORCR = findViewById(R.id.btnORCR);
        btnAddDoc1 = findViewById(R.id.btnAddDoc1);
        btnAddDoc2 = findViewById(R.id.btnAddDoc2);
        Enroll = findViewById(R.id.btnEnroll);


        btnORCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              intentORCR();
            }
        });
        btnGarage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intentGarage();
            }
        });
        btnAddDoc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAddDoc1();
            }
        });

        btnAddDoc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAddDoc2();
            }
        });
        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hasImage(btnORCR) || !hasImage(btnGarage)) {
                  Toast.makeText(AttachDocuments.this,"Fill in the Required Documents",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = getIntent();
                    final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                    final String CatCodeHolder = intent.getStringExtra("KEY");
                    String Maker = intent.getStringExtra("Maker");
                    String Model = intent.getStringExtra("Model");
                    String Year = intent.getStringExtra("Year");
                    String Capacity = intent.getStringExtra("Capacity");
                    String PlateNumber = intent.getStringExtra("PlateNumber");
                    String ChassisNo= intent.getStringExtra("ChassisNo");
                    String EngineNo= intent.getStringExtra("EngineNo");
                    String Transmission = intent.getStringExtra("Transmission");
                    String ServiceType = intent.getStringExtra("ServiceType");
                    String AreaOfRestriction = intent.getStringExtra("AreaOfRestriction");

                   String id = mDatabaseCarDetail.push().getKey();
                }
            }
        });

        Utils.initToolBar(this, toolbar, true);
        toolbar_title.setText("Attach Documents");




    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable!=null);

        if(hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();

        }
        return  true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OR_CR) {
                onSelectFromOR_CRresult(intent);
            }
            else if(requestCode == GARAGE){
                onSelectFromGarage_Result(intent);
            }
            else if(requestCode == SUPP_IMG_1){
                onResultFromIMG1(intent);
            }
            else if(requestCode == SUPP_IMG_2){
                onResultFromIMG2(intent);
            }
        }
    }

    private void intentORCR() {

        ButterKnife.bind(this);
        selectedColor = 4475389;
        Intent intent = new Intent(this, SinglePickActivity.class);
        Params params = new Params();
        params.setCaptureLimit(1);
        params.setPickerLimit(1);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent,OR_CR);
    }
    private void intentGarage() {

        ButterKnife.bind(this);
        selectedColor = 4475389;
        Intent intent = new Intent(this, SinglePickActivity.class);
        Params params = new Params();
        params.setCaptureLimit(1);
        params.setPickerLimit(1);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent,GARAGE);
    }
    private void intentAddDoc1() {

        ButterKnife.bind(this);
        selectedColor = 4475389;
        Intent intent = new Intent(this, SinglePickActivity.class);
        Params params = new Params();
        params.setCaptureLimit(1);
        params.setPickerLimit(1);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent,SUPP_IMG_1);
    }

    private void intentAddDoc2() {

        ButterKnife.bind(this);
        selectedColor = 4475389;
        Intent intent = new Intent(this, SinglePickActivity.class);
        Params params = new Params();
        params.setCaptureLimit(1);
        params.setPickerLimit(1);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent,SUPP_IMG_2);
    }

    private void onSelectFromOR_CRresult(Intent intent){


        final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);

        final Image entity = imagesList.get(0);
        Glide.with(AttachDocuments.this)
                .load(entity.uri)
                .apply(new RequestOptions()
                        .placeholder(com.vlk.multimager.R.drawable.image_processing)
                        .centerCrop()
                       )
                .into(btnORCR);


    }

    private void onSelectFromGarage_Result(Intent intent){

        final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);

        final Image entity = imagesList.get(0);
        Glide.with(AttachDocuments.this)
                .load(entity.uri)
                .apply(new RequestOptions()
                        .placeholder(com.vlk.multimager.R.drawable.image_processing)
                        .centerCrop()
                )
                .into(btnGarage);


    }
    private void  onResultFromIMG1(Intent intent){


        final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);

        final Image entity = imagesList.get(0);
        Glide.with(AttachDocuments.this)
                .load(entity.uri)
                .apply(new RequestOptions()
                        .placeholder(com.vlk.multimager.R.drawable.image_processing)
                        .centerCrop()
                )
                .into(btnAddDoc1);


    }


    private void  onResultFromIMG2(Intent intent){


        final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);

        final Image entity = imagesList.get(0);
        Glide.with(AttachDocuments.this)
                .load(entity.uri)
                .apply(new RequestOptions()
                        .placeholder(com.vlk.multimager.R.drawable.image_processing)
                        .centerCrop()
                )
                .into(btnAddDoc2);


    }


}
