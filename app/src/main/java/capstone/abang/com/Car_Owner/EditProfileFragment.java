package capstone.abang.com.Car_Owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import capstone.abang.com.R;

public class EditProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container,false);

        //Setup Toolbar
        setupToolbar(view);


        return view;
    }
    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.editprofiletoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ImageView backImageView = view.findViewById(R.id.backarrow);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
