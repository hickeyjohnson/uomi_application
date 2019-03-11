package ca.outercove.uomiapplication.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;

import ca.outercove.uomiapplication.R;


public class CreateAccountFragment extends Fragment {

    private Switch groupAccountToggle;
    private TextView emailTextView1;
    private TextView emailTextView2;
    private TextView emailTextView3;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TITLE = "Create Account";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    // TODO: Customize parameter initialization
    public static CreateAccountFragment newInstance(int columnCount) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_creation, container, false);
        groupAccountToggle = view.findViewById(R.id.groupToggle);
        emailTextView1 = view.findViewById(R.id.email1);
        emailTextView2 = view.findViewById(R.id.email2);
        emailTextView3 = view.findViewById(R.id.email3);
        groupAccountToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.groupToggle:
                        if (isChecked == true) {
                            emailTextView1.setVisibility(View.VISIBLE);
                            emailTextView2.setVisibility(View.VISIBLE);
                            emailTextView3.setVisibility(View.VISIBLE);
                        } else {
                            emailTextView2.setVisibility(View.INVISIBLE);
                            emailTextView3.setVisibility(View.INVISIBLE);
                        }
                        break;
                }

            }
        });

        return view;
    }
}
