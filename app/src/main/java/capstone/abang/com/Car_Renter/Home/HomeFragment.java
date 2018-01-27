package capstone.abang.com.Car_Renter.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.TimerTask;

import capstone.abang.com.Models.AdsFile;
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

    private RecyclerView recyclerView2;
    private FirebaseDatabase firebaseDatabase2;
    private DatabaseReference myRef2;
    private FirebaseRecyclerAdapter<AdsFile, ShowAdsDataViewHolder> mfirebaseDatabase2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_renter_home, container, false);

        initImageLoader();

//        RECYCLERVIEW OF ADS
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        myRef2 = firebaseDatabase2.getReference("AdsFile");
        recyclerView2 = view.findViewById(R.id.show_ads_rv);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mfirebaseDatabase2 = new FirebaseRecyclerAdapter<AdsFile, ShowAdsDataViewHolder>
                (AdsFile.class, R.layout.layout_model_ads, ShowAdsDataViewHolder.class, myRef2) {
            //            @Override
            public void populateViewHolder(ShowAdsDataViewHolder viewHolder, AdsFile model, int position) {
                viewHolder.setImage(model.getAdImage());
            }
        };

//        RECYCLER VIEW OF CAR CATEGORIES
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("CategoryFile");
        recyclerView = view.findViewById(R.id.show_data_recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = new FirebaseRecyclerAdapter<CategoryFile, ShowDataViewHolder>
                (CategoryFile.class, R.layout.layout_model_categories, ShowDataViewHolder.class, myRef) {
//POPULATE RECYCLER VIEW
            public void populateViewHolder(final ShowDataViewHolder viewHolder, CategoryFile model, final int position) {
                if(model.getCatStatus().equalsIgnoreCase("ac")) {
                    viewHolder.setImage(model.getCatImage());
                    viewHolder.setDescription(model.getCatDesc());
                    viewHolder.catCode = model.getCatCode();
                }
                else {
                    viewHolder.cardView.setVisibility(View.GONE);
                }
                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        //PUT EXTRA AND CATCODE FOR VERIFICATION SA SUNOD
                        String temp = viewHolder.catCode;
                        viewHolder.setDescription(viewHolder.catCode);

                    }
                });
            }
        };
        recyclerView.setAdapter(mFirebaseDatabase);
        recyclerView2.setAdapter(mfirebaseDatabase2);
        return view;
    }


    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView image_title;
        private final ImageView image_url;
        private final CardView cardView;
//        private final ViewPager viewPager;
        private String catCode="";

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = itemView.findViewById(R.id.fetch_image);
            image_title = itemView.findViewById(R.id.fetch_image_title);
            cardView = itemView.findViewById(R.id.card_view2);
        }

        private void setDescription(String title) {
            image_title.setText(title);
        }

        private void setImage(String title) {
            // image_url.setImageResource(R.drawable.loading);
            UniversalImageLoader.setImage(title, image_url, null, "");
        }
    }
    public static class ShowAdsDataViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image_ads;

        public ShowAdsDataViewHolder(final View itemView2){
            super(itemView2);
            image_ads = itemView2.findViewById(R.id.fetch_image_ads);
        }
        private void setImage(String title2){
            UniversalImageLoader.setImage(title2, image_ads, null, "");
        }
    }
}

