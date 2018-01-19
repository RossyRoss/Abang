package capstone.abang.com.Car_Owner.Endorse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import capstone.abang.com.Models.CategoryFile;
import capstone.abang.com.R;
import capstone.abang.com.Utils.CustomAdapter;
import capstone.abang.com.Utils.FirebaseHelper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vlk.multimager.adapters.GalleryImagesAdapter;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;

public class SelectCategory extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_title;

    ArrayList<String> list = new ArrayList<>();
    ListView listview;
    DatabaseReference dref;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Intent intent = this.getIntent();
        ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(4, GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        GalleryImagesAdapter imageAdapter = new GalleryImagesAdapter(this, imagesList, 4, new Params());
        recyclerView.setAdapter(imageAdapter);


        Utils.initToolBar(this, toolbar, true);
        toolbar_title.setText("Gallery");


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dref = firebaseDatabase.getReference();
        listview = findViewById(R.id.ListView);
        FirebaseHelper helper = new FirebaseHelper();
        CustomAdapter adapter = new CustomAdapter(this, helper.retrieve());
        listview.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_category_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return true;
    }


}
