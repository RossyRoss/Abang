package capstone.abang.com.Car_Owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import capstone.abang.com.Models.UDFile;
import capstone.abang.com.Models.USettings;
import capstone.abang.com.R;
import capstone.abang.com.Models.UHFile;


public class ProfileFragment extends Fragment {
    private static final String TAG ="ProfileFragment";
    //lists
    List<UHFile> uhFiles = new ArrayList<UHFile>();

    //widgets
    private ImageView imgViewProfilePicture;
    private TextView textViewName;
    private TextView textViewDateJoined;
    private TextView textViewEmail;
    private TextView textViewContact;
    private TextView textViewAddress;
    private TextView textViewTransactions;

    //firebase
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //casting widgets
        imgViewProfilePicture = view.findViewById(R.id.imgviewprofilepic);
        textViewName = view.findViewById(R.id.txtuserfullname);
        textViewTransactions = view.findViewById(R.id.txtprofileusertransactions);
        textViewAddress = view.findViewById(R.id.txtprofileuseraddress);
        textViewEmail = view.findViewById(R.id.txtprofileuseremail);
        textViewContact = view.findViewById(R.id.txtprofileusercontact);
        textViewDateJoined = view.findViewById(R.id.txtprofileuserdatejoined);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        //retrieve
        retrieveData();

        // Inflate the layout for this fragment
        return view;
    }

    private void setProfileWidgets(USettings uSettings) {
        UDFile udFile = uSettings.getUdFile();
        UHFile uhFile = uSettings.getUhFile();

        textViewName.setText(udFile.getUDFullname());
        textViewAddress.setText(udFile.getUDAddr());
        textViewContact.setText(udFile.getUDContact());
        textViewEmail.setText(udFile.getUDEmail());
    }


    public void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setProfileWidgets(getUserSettings(dataSnapshot));
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
