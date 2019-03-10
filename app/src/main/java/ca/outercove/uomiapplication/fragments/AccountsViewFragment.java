package ca.outercove.uomiapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.navigation.Navigation;
import ca.outercove.uomiapplication.MainActivity;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;
import ca.outercove.uomiapplication.listAdapters.AccountsViewListAdapter;
import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.appObjects.AccountsViewContent;
import ca.outercove.uomiapplication.appObjects.AccountsViewContent.AccountsViewItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AccountsViewFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TITLE = "Accounts";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private AccountsViewListAdapter mAdapter;
    private SharedPreferences pref;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AccountsViewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AccountsViewFragment newInstance(int columnCount) {
        AccountsViewFragment fragment = new AccountsViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_accounts_view_list, container, false);
        fab = view.findViewById(R.id.fabAddAccount);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFrag = CreateAccountDialogFragment.newInstance(
                        R.string.create_account);
                newFrag.show(getFragmentManager(), "dialog");
            }
        });
        // Set the adapter
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.accounts_listed);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        // TODO: exchange AccountsViewContent.ITEMS for the content from Web API
        getUserAccounts();
        //mAdapter = new AccountsViewListAdapter(AccountsViewContent.ITEMS, mListener);
        //recyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getUserAccounts() {
        String url = getString(R.string.base_url) + "/accounts/" + Integer.toString(this.pref.getInt("userId", -1));

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                AccountsViewContent.ITEMS.clear();
                for (int i = 0; i < response.length(); i++) {
                    // Get the JSON object representing the current account
                    try {
                        JSONObject acc = response.getJSONObject(i);
                        // TODO: switch the account users array to a more meaningful name
                        AccountsViewContent.ITEMS.add(new AccountsViewItem(acc.getInt("account_id"),
                                acc.get("account_users").toString(), acc.getDouble("acc_balance")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                mAdapter = new AccountsViewListAdapter(AccountsViewContent.ITEMS, mListener, getContext());
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO
            }
        }
        );

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(AccountsViewItem item);
    }

}
