package ca.outercove.uomiapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SampleSettingsFragment extends Fragment {


    public SampleSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample_settings, container, false);

        ArrayList<String> data = new ArrayList<>();
        data.add("About");
        data.add("User information");
        data.add("Log out");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, data);

        ListView lvData = view.findViewById(R.id.lvSettings);
        lvData.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
