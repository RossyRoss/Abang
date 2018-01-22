package capstone.abang.com.Car_Renter.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import capstone.abang.com.Models.CDFile;
import capstone.abang.com.R;
import capstone.abang.com.Utils.UniversalImageLoader;

public class CarsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseRecyclerAdapter<CDFile, ShowViewHolder> firebaseRecyclerAdapter;
    private String catCode;
    private LinearLayout linearLayout;

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
            protected void populateViewHolder(ShowViewHolder viewHolder, CDFile model, int position) {
                Log.d("TAG", "Model ni siya" + model.getCDModel());
                if(catCode.equals(model.getCdcatcode())) {
                    viewHolder.setCarName(model.getCDMaker() + " " + model.getCDModel() + " " + model.getCdcaryear());
                    viewHolder.setCarImage(model.getCDPhoto());
                }
                else {
                    viewHolder.cardView.setVisibility(View.GONE);
                }
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
        public ShowViewHolder(View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.textviewcarname);
            carPhoto = itemView.findViewById(R.id.imageviewcar);
            cardView = itemView.findViewById(R.id.cardviewcars);
        }

        private void setCarName(String title) {
            carName.setText(title);
        }
        private void setCarImage(String title) {
            UniversalImageLoader.setImage(title, carPhoto, null,"");
        }
    }
}
