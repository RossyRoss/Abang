package capstone.abang.com.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import capstone.abang.com.Models.CategoryFile;
import capstone.abang.com.R;

/**
 * Created by Rylai on 1/19/2018.
 */

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CategoryFile> categories;

    public CustomAdapter(Context mContext, ArrayList<CategoryFile> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.snippet_listview_category,viewGroup, false);

        TextView textView = view.findViewById(R.id.txtcategoryname);
        final CategoryFile cat = (CategoryFile) this.getItem(i);
        textView.setText(cat.getCatDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, cat.getCatCode(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
