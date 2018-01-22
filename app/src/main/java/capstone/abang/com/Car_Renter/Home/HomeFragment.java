package capstone.abang.com.Car_Renter.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import capstone.abang.com.Models.CategoryFile;
import capstone.abang.com.R;
import capstone.abang.com.Utils.UniversalImageLoader;

/**
 * Created by Pc-user on 20/01/2018.
 */

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseRecyclerAdapter<CategoryFile, ShowDataViewHolder> mFirebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renter_home, container, false);

        initImageLoader();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("CategoryFile");
        recyclerView = view.findViewById(R.id.show_data_recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(mLayoutManager);

        //populate
        mFirebaseDatabase = new FirebaseRecyclerAdapter<CategoryFile, ShowDataViewHolder>
                (CategoryFile.class, R.layout.layout_model_categories, ShowDataViewHolder.class, myRef) {

            public void populateViewHolder(final ShowDataViewHolder viewHolder, final CategoryFile model, final int position) {
                viewHolder.setImage(model.getCatImage());
                viewHolder.setDescription(model.getCatDesc());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CarsActivity.class);
                        intent.putExtra("code", model.getCatCode());
                        startActivity(intent);
                    }
                });
            }

        };
        recyclerView.setAdapter(mFirebaseDatabase);
        return view;
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView image_title;
        private final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = itemView.findViewById(R.id.fetch_image);
            image_title = itemView.findViewById(R.id.fetch_image_title);
        }

        private void setDescription(String title) {
            image_title.setText(title);
        }

        private void setImage(String title) {
            // image_url.setImageResource(R.drawable.loading);
            UniversalImageLoader.setImage(title, image_url, null, "");
        }
    }
}
