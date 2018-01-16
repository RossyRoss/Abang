package capstone.abang.com.Register;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import capstone.abang.com.R;
import capstone.abang.com.Utils.UniversalImageLoader;
import capstone.abang.com.Utils.Utility;

public class RegisterActivity extends AppCompatActivity {
    //Declaring all widgets
    private ImageView imgViewProfile;
    private ImageView imgBtnProfile;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etFullname;
    private EditText etAddress;
    private EditText etConactNumber;
    private Button btnNext;

    //Declaring variables
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private Uri holderUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imgViewProfile = findViewById(R.id.imgViewProfile);
        imgBtnProfile = findViewById(R.id.btnCamera);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        etEmail = findViewById(R.id.etRegEmail);
        etFullname = findViewById(R.id.etRegFullname);
        etAddress = findViewById(R.id.etRegAddress);
        etConactNumber = findViewById(R.id.etRegContactNumber);
        btnNext = findViewById(R.id.btnNext);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Account");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Methods
        setInit();
        setImage();
        initImageLoader();
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setImage() {
        imgBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Choose Profile Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegisterActivity.this);

                if(items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if(result) {
                        cameraIntent();
                    }
                }
                else if(items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if(result) {
                        galleryIntent();
                    }
                } else if(items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //PERMISSION FOR CAMERA AND GALLERY
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo")) {
                        cameraIntent();
                    } else if(userChoosenTask.equals("Choose from Gallery")) {
                        galleryIntent();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if(requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Uri uri = data.getData();
        String holderUri = uri.toString();
        UniversalImageLoader.setImage(holderUri, imgViewProfile, null, "");
        imgViewProfile.setBackgroundResource(0);

    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri uri = data.getData();
        String holderUri = uri.toString();
        UniversalImageLoader.setImage(holderUri, imgViewProfile, null, "");
        imgViewProfile.setBackgroundResource(0);
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable!=null);

        if(hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }

    private void setInit() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String holderUsername = etUsername.getText().toString().trim();
                String holderPassword = etPassword.getText().toString().trim();
                String holderEmail = etEmail.getText().toString().trim();
                String holderFullname = etFullname.getText().toString().trim();
                String holderAddress = etAddress.getText().toString().trim();
                String holderContactNumber = etConactNumber.getText().toString().trim();
                if(!TextUtils.isEmpty(holderUsername) && !TextUtils.isEmpty(holderPassword) && !TextUtils.isEmpty(holderEmail)
                        && !TextUtils.isEmpty(holderFullname) && !TextUtils.isEmpty(holderAddress) && !TextUtils.isEmpty(holderContactNumber)) {
                    if(hasImage(imgViewProfile)) {
                        moveToContinuation();
                    }
                    else {
                        toastMethod("Pick a photo");
                    }
                }
                else {
                    toastMethod("Input all fields!");
                }
            }
        });
    }

    private void moveToContinuation() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String name = etFullname.getText().toString().trim();
        String addr = etAddress.getText().toString().trim();
        String contact = etConactNumber.getText().toString().trim();

        Intent moveToNext = new Intent(getApplicationContext(), RegisterContinuationActivity.class);
        moveToNext.putExtra("username", username);
        moveToNext.putExtra("password", password);
        moveToNext.putExtra("email", email);
        moveToNext.putExtra("name", name);
        moveToNext.putExtra("addr", addr);
        moveToNext.putExtra("contact", contact);
        startActivity(moveToNext);
    }

    //TOAST METHOD
    private void toastMethod(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
