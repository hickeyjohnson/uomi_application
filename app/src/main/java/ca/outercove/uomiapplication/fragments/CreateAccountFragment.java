package ca.outercove.uomiapplication.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;

/**
 * A fragment used for creating accounts for single users and groups.
 *
 */
public class CreateAccountFragment extends Fragment {

    private Switch groupAccountToggle;
    private TextView emailTextView1;
    private TextView emailTextView2;
    private TextView emailTextView3;
    private Button createButton;
    private SharedPreferences pref;

    private OnFragmentInteractionListener mListener;

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
        emailTextView1 = view.findViewById(R.id.transDesc);
        emailTextView2 = view.findViewById(R.id.email2);
        emailTextView3 = view.findViewById(R.id.transVal);
        createButton = view.findViewById(R.id.createButton);
        this.pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        // Hide extra email fields if not in group mode
        groupAccountToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.groupToggle:
                        if (isChecked == true) {
                            groupAccountVisible();
                        } else {
                            singleAccountVisible();
                        }
                        break;
                }

            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Method to create account.
     */
    private void createAccount(){
        // check if group account
        JSONArray otherUserEmails = new JSONArray();
        otherUserEmails.put(emailTextView1.getText().toString());
        if (groupAccountToggle.isChecked()) {
            // Group account
            otherUserEmails.put(emailTextView2.getText().toString());
            if (! emailTextView3.getText().toString().equals("")) {
                // If the text box isn't empty then add the third email
                otherUserEmails.put(emailTextView3.getText().toString());
            }
        }

        // otherUserEmails will contain all necessary information at this point, group or single account.
        // Form the JSONObject that will be the body of the request
        JSONObject reqBody = new JSONObject();

        try {
            reqBody.put("current_user_id", pref.getInt("userId", -1));
            reqBody.put("user_emails", otherUserEmails);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.base_url) + "/accounts";
        JsonObjectRequest createAccountRequest = new JsonObjectRequest(
                Request.Method.POST, url, reqBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO: Once the response comes back action.
                if (mListener != null) {
                    mListener.onAccountCreated();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: If an error occurs.
            }
        }
        );

        // Add the request to the Request Queue
        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(createAccountRequest);

    }

    //Sets all but one text field to invisible
    private void singleAccountVisible() {
        emailTextView1.setVisibility(View.VISIBLE);
        emailTextView2.setVisibility(View.INVISIBLE);
        emailTextView3.setVisibility(View.INVISIBLE);
    }

    //Sets all text fields to visible
    private void groupAccountVisible() {
        emailTextView1.setVisibility(View.VISIBLE);
        emailTextView2.setVisibility(View.VISIBLE);
        emailTextView3.setVisibility(View.VISIBLE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAccountCreated();
    }
}
