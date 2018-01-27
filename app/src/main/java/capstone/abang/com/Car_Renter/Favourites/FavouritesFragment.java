package capstone.abang.com.Car_Renter.Favourites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import capstone.abang.com.Models.CDFile;
import capstone.abang.com.Models.Like;
import capstone.abang.com.R;
import capstone.abang.com.Utils.Heart;
import capstone.abang.com.Utils.UniversalImageLoader;

/**
 * Created by Pc-user on 26/01/2018.
 */

public class FavouritesFragment extends Fragment {
    //widgets
    private RecyclerView recyclerView;

    //firebasedatabase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<CDFile, ShowHolderView> firebaseRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renter_favourites, container, false);

        initImageLoader();
        setupFirebase();
        setupRecyclerView(view);
        populateRecyclerView();
        return view;
    }

    private void populateRecyclerView() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CDFile, ShowHolderView>
                (CDFile.class, R.layout.layout_model_cars, ShowHolderView.class, databaseReference) {
            @Override
            protected void populateViewHolder(final ShowHolderView viewHolder, final CDFile model, int position) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference
                        .child("CDFile")
                        .child(model.getCDCode())
                        .child("likes");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()) {
                            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(singleDataSnapshot.getValue(Like.class).getUHuserCode())) {
                                viewHolder.setCarName(model.getCDMaker() + " " + model.getCDModel() + " " + model.getCdcaryear());
                                viewHolder.setCarImage(model.getCDPhoto());
                            }
                        }
                        if(!dataSnapshot.exists()) {
                            viewHolder.cardView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.show_recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CDFile");
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public static class ShowHolderView extends RecyclerView.ViewHolder {
        private final TextView carName;
        private final ImageView carPhoto;
        private final CardView cardView;
        private final ImageView heartWhite;
        private final ImageView heartRed;
        private final TextView carLikes;
        private Heart mHeart;
        public ShowHolderView(View itemView) {
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
