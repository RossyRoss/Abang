package capstone.abang.com.Car_Owner.Endorse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;

import capstone.abang.com.R;

public class AddDents extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dents);
        toolbar =  findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);




        Utils.initToolBar(this, toolbar, true);
        toolbar_title.setText("Add Dents");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.vlk.multimager.R.menu.add_dents_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        else{

            Intent intent = getIntent();
            final ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
            final String CatCodeHolder = intent.getStringExtra("KEY");

             intent = new Intent(AddDents.this,BasicDetails.class);
            intent.putParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST, imagesList);
            intent.putExtra("KEY",CatCodeHolder);

            startActivity(intent);
            return true;
        }


    }


}
