package ca.outercove.uomiapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTransactionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText mTransactionTitle;
    private EditText mTransactionValue;
    private Button mCreateTransactionBtn;

    private SharedPreferences pref;

    private OnFragmentInteractionListener mListener;

    public CreateTransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTransactionFragment newInstance(String param1, String param2) {
        CreateTransactionFragment fragment = new CreateTransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_transaction, container, false);
        this.pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.mTransactionTitle = view.findViewById(R.id.transDesc);
        this.mTransactionValue = view.findViewById(R.id.transVal);
        this.mCreateTransactionBtn = view.findViewById(R.id.createTransButton);
        this.mCreateTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard and add transaction
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                onCreateTransaction(getArguments().getInt("accountId"),
                        mTransactionTitle.getText().toString(), Double.valueOf(mTransactionValue.getText().toString()));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onCreateTransaction(final Integer accountId, String title, Double value) {
        String url = getString(R.string.base_url) + "/transactions/" + Integer.toString(pref.getInt("userId", 0)) + "/" + accountId.toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", pref.getInt("userId", -1));
            jsonObject.put("trans_title", title);
            jsonObject.put("trans_value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest createTransactionRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (mListener != null) {
                    mListener.onFragmentInteraction(accountId, getArguments().getString("accUsers"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO error handling
            }
        }
        );

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(createTransactionRequest);


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
        void onFragmentInteraction(Integer accountId, String otherAccountUsers);
    }
}
