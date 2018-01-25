package capstone.abang.com.Car_Renter.Home;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capstone.abang.com.Models.CDFile;
import capstone.abang.com.Models.Like;
import capstone.abang.com.Models.UHFile;
import capstone.abang.com.R;
import capstone.abang.com.Utils.Heart;
import capstone.abang.com.Utils.UniversalImageLoader;

public class CarsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private UHFile uhFile;
    private FirebaseRecyclerAdapter<CDFile, ShowViewHolder> firebaseRecyclerAdapter;
    private String catCode;
    private CDFile cdFile;
    private LinearLayout linearLayout;
    private Boolean mLikedByCurrentUser;
    private StringBuilder mUsers;
    private int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        initImageLoader();
        setupFirebase();
        setupRecyclerView();
        populateRecyclerView();
        Bundle bundle = getIntent().getExtras();
        catCode = bundle.getString("code");
    }

    private void populateRecyclerView() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CDFile, ShowViewHolder>
                (CDFile.class, R.layout.layout_model_cars, ShowViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(final ShowViewHolder viewHolder, final CDFile model, int position) {
                if(catCode.equals(model.getCdcatcode())) {
                    viewHolder.setCarName(model.getCDMaker() + " " + model.getCDModel() + " " + model.getCdcaryear());
                    viewHolder.setCarImage(model.getCDPhoto());
                    final Heart mHeart = viewHolder.viewHeart();
                    Log.d("getFavouriteCount", "Getting the mLikeCurrentUser");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference
                            .child("CDFile")
                            .child(model.getCDCode())
                            .child("likes");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("getFavouriteCount", "Retrieving OnDataChange");
                            mUsers = new StringBuilder();
                            for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()){
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query query1 = ref.child("UHFile").orderByChild("uhusercode").equalTo(singleDataSnapshot.getValue(Like.class).getUHuserCode());
                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.d("getFavouriteCount", "Retrieving From Users");
                                        for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()){
                                            mUsers.append(singleDataSnapshot.getValue(UHFile.class).getUHUsercode());
                                            mUsers.append(",");
                                            Log.d("APPEND", "Retrieving From Users");
                                        }
                                        String[] splitUsers = mUsers.toString().split(",");
                                        length = splitUsers.length;
                                        if(mUsers.toString().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                            Log.d("LIKED", "BY USER");
                                            mLikedByCurrentUser = true;
                                            viewHolder.heartWhite.setVisibility(View.GONE);
                                            viewHolder.heartRed.setVisibility(View.VISIBLE);
                                            viewHolder.heartRed.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                                    Query query = reference
                                                            .child("CDFile")
                                                            .child(model.getCDCode())
                                                            .child("likes");
                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()){
                                                                String keyID = singleDataSnapshot.getKey();
                                                                //case 1: the user already liked the car
                                                                if(singleDataSnapshot.getValue(Like.class).getUHuserCode()
                                                                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                                    ref.child("CDFile").child(model.getCDCode()).child("likes")
                                                                            .child(keyID).removeValue();

                                                                    //mHeart.toggleLike();
                                                                }
                                                            }
                                                            if(!dataSnapshot.exists()) {
                                                                addNewLike(mHeart, model);
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        else {
                                            mLikedByCurrentUser = false;
                                            Log.d("NOT LIKED", "BY USER");
                                            viewHolder.heartWhite.setVisibility(View.VISIBLE);
                                            viewHolder.heartRed.setVisibility(View.GONE);
                                            viewHolder.heartWhite.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    addNewLike(mHeart, model);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            if(!dataSnapshot.exists()) {
                                Log.d("getFavouriteCount", "0 LIKES");
                                mLikedByCurrentUser = false;
                                length = 0;
                                viewHolder.heartWhite.setVisibility(View.VISIBLE);
                                viewHolder.heartRed.setVisibility(View.GONE);
                                viewHolder.heartWhite.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        addNewLike(mHeart, model);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    viewHolder.cardView.setVisibility(View.GONE);
                }
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void addNewLike(Heart mHeart, CDFile model) {
        String newLikeID = myRef.push().getKey();
        Like like = new Like();
        like.setUHuserCode(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.child(model.getCDCode()).child("likes").child(newLikeID)
                .setValue(like);
        mHeart.toggleLike();
        getFavouriteCount(model);
    }

    private void getFavouriteCount(CDFile model) {
        Log.d("getFavouriteCount", "Getting the mLikeCurrentUser");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("CDFile")
                .child(model.getCDCode())
                .child("likes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("getFavouriteCount", "Retrieving OnDataChange");
                mUsers = new StringBuilder();
                for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference
                            .child("UHFile")
                            .child("uhuserCode")
                            .equalTo(singleDataSnapshot.getValue(Like.class).getUHuserCode());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("getFavouriteCount", "Retrieving From Users");
                            for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()){
                                mUsers.append(singleDataSnapshot.getValue(UHFile.class).getUHUsercode());
                                mUsers.append(",");
                            }
                            String[] splitUsers = mUsers.toString().split(",");
                            if(mUsers.toString().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                mLikedByCurrentUser = true;
                            }
                            else {
                                mLikedByCurrentUser = false;
                            }
                            length = splitUsers.length;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(!dataSnapshot.exists()) {
                    Log.d("getFavouriteCount", "0 LIKES");
                    mLikedByCurrentUser = false;
                    length = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.show_data_recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("CDFile");
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        private final TextView carName;
        private final ImageView carPhoto;
        private final CardView cardView;
        private final ImageView heartWhite;
        private final ImageView heartRed;
        private final TextView carLikes;
        private Heart mHeart;
        public ShowViewHolder(View itemView) {
            super(itemView);
            heartWhite = itemView.findViewById(R.id.imgviewwhiteheart);
            heartRed = itemView.findViewById(R.id.imgviewredheart);
            carName = itemView.findViewById(R.id.textviewcarname);
            carPhoto = itemView.findViewById(R.id.imageviewcar);
            cardView = itemView.findViewById(R.id.cardviewcars);
            mHeart = new Heart(heartWhite, heartRed);
            carLikes = itemView.findViewById(R.id.textviewfavourites);
        }
        private void setLikes(int holder) {
            carLikes.setText("" + holder);
        }
        private Heart viewHeart() {
            return mHeart;
        }
        private void setCarName(String title) {
            carName.setText(title);
        }
        private void setCarImage(String title) {
            UniversalImageLoader.setImage(title, carPhoto, null,"");
        }
    }
}
