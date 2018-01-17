package capstone.abang.com.Car_Owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import capstone.abang.com.Car_Renter.RenterProfileFragment;
import capstone.abang.com.Models.UDFile;
import capstone.abang.com.Models.UHFile;
import capstone.abang.com.Models.USettings;
import capstone.abang.com.R;

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    //firebase
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    //widgets
    private EditText editTextUserName;
    private EditText editTextUserFullName;
    private EditText editTextUserAddress;
    private EditText editTextUserContactNumber;
    private EditText editTextUserEmail;

    //variables
    private USettings mUSettings;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container,false);
        //cast widgets
        editTextUserName = view.findViewById(R.id.eteditusername);
        editTextUserFullName = view.findViewById(R.id.eteditfullname);
        editTextUserAddress = view.findViewById(R.id.eteditaddress);
        editTextUserContactNumber = view.findViewById(R.id.eteditcontactnumber);
        editTextUserEmail = view.findViewById(R.id.eteditcontactemail);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        //variables
        userid = mAuth.getCurrentUser().getUid();

        //Setup Toolbar
        setupToolbar(view);

        //retrieve
        retrieveData();

        return view;
    }

    private void setEditProfileWidgets(USettings uSettings) {
        UDFile udFile = uSettings.getUdFile();
        UHFile uhFile = uSettings.getUhFile();
        mUSettings = uSettings;
        editTextUserName.setText(uhFile.getUHUsername());
        editTextUserFullName.setText(udFile.getUDFullname());
        editTextUserAddress.setText(udFile.getUDAddr());
        editTextUserContactNumber.setText(udFile.getUDContact());
        editTextUserEmail.setText(udFile.getUDEmail());
    }
    //charles
    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.editprofiletoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ImageView backImageView = view.findViewById(R.id.backarrow);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                startActivity(intent);
            }
        });

        ImageView saveImageView = view.findViewById(R.id.savechanges);
        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileSettings();
            }
        });
    }

    public void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setEditProfileWidgets(getUserSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private USettings getUserSettings(DataSnapshot dataSnapshot) {
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        UDFile udFile = new UDFile();
        UHFile uhFile = new UHFile();

        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            if(ds.getKey().equals("UDFile")) {
                udFile = ds.child(userID).getValue(UDFile.class);
            }
            if(ds.getKey().equals("UHFile")) {
                uhFile = ds.child(userID).getValue(UHFile.class);
            }
        }
        return new USettings(udFile, uhFile);
    }

    private void saveProfileSettings() {
        final String userName = editTextUserName.getText().toString();
        final String fullName = editTextUserFullName.getText().toString();
        final String address = editTextUserAddress.getText().toString();
        final String contactNumber = editTextUserContactNumber.getText().toString();


        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(contactNumber)) {
            Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        } else {
            if(!mUSettings.getUhFile().getUHUsername().equals(userName)) {
                checkUserNameExistence(userName);
            }
            if(!mUSettings.getUdFile().getUDFullname().equals(fullName) || !mUSettings.getUdFile().getUDAddr().equals(address)) {
                myRef.child("UDFile").child(userid).child("udfullname").setValue(fullName);
                myRef.child("UDFile").child(userid).child("udaddr").setValue(address);
                Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
            }
            if(!mUSettings.getUdFile().getUDContact().equals(contactNumber)) {
                checkContactNumber(contactNumber);
            }
        }
    }

    private void checkContactNumber(final String contact) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("UDFile")
                .orderByChild("udcontact")
                .equalTo(contact);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //add the username
                    Log.d(TAG, "CONTACT NUMBER DOES NOT EXIST!");
                    myRef.child("UDFile")
                            .child(userid)
                            .child("udcontact")
                            .setValue(contact);
                    Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                }
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    if(singleSnapshot.exists()) {
                        Log.d(TAG, "checkUserNameExistence: FOUND A MATCH: " + singleSnapshot.getValue(UHFile.class).getUHUsername());
                        Toast.makeText(getActivity(), "Contact number already exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkUserNameExistence(final String userName) {
        Log.d(TAG,"checkUserNameExistence: checking");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("UHFile")
                .orderByChild("uhusername")
                .equalTo(userName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //add the username
                    Log.d(TAG, "USER DOES NOT EXIST!");
                    myRef.child("UHFile")
                            .child(userid)
                            .child("uhusername")
                            .setValue(userName);
                    Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                }
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    if(singleSnapshot.exists()) {
                        Log.d(TAG, "checkUserNameExistence: FOUND A MATCH: " + singleSnapshot.getValue(UHFile.class).getUHUsername());
                        Toast.makeText(getActivity(), "That username already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
