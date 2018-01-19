package capstone.abang.com.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import capstone.abang.com.Models.CategoryFile;

/**
 * Created by Rylai on 1/19/2018.
 */

public class FirebaseHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private ArrayList<CategoryFile> categories = new ArrayList<>();

    public FirebaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("CategoryFile");
    }

    public void fetchData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()) {

                CategoryFile category = ds.getValue(CategoryFile.class);
                if(category.getCatStatus().equals("AC")) {
                    categories.add(category);
                }

        }
    }

    public ArrayList<CategoryFile> retrieve() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return categories;
    }


}
