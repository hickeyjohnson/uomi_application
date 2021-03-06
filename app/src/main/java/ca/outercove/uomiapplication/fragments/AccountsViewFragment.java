package ca.outercove.uomiapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.navigation.Navigation;
import ca.outercove.uomiapplication.FormattingHelper;
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
public class AccountsViewFragment extends Fragment
                implements DeleteAccountDialogFragment.DeleteAccountDialogListener {

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts_view_list, container, false);
        fab = view.findViewById(R.id.fabAddAccount);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_accounts_to_createAccountFragment);
            }
        });


        // Set the adapter
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.accounts_listed);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        getUserAccounts();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO:Delete Account
                showAccountDeleteWarningDialog(viewHolder);
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }

    /**
     * Will display a dialog box prompting the user if they truly wish to delete the account
     * @param viewHolder
     */
    void showAccountDeleteWarningDialog(@NonNull RecyclerView.ViewHolder viewHolder) {
        AccountsViewListAdapter.ViewHolder viewHolder2 = (AccountsViewListAdapter.ViewHolder) viewHolder;
        DialogFragment newFrag = DeleteAccountDialogFragment.newInstance(
                R.string.delete_account, viewHolder2);
        newFrag.show(getFragmentManager(), "dialog");
        ((DeleteAccountDialogFragment) newFrag).setAdapter(mAdapter);
        ((DeleteAccountDialogFragment) newFrag).setListener(this);

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
                        JSONArray realNames = acc.getJSONArray("real_names");
                        // Form string for real names
                        String realNamesFormatted = FormattingHelper.commaSeparate(realNames);
                        // TODO: switch the account users array to a more meaningful name
                        AccountsViewContent.ITEMS.add(new AccountsViewItem(acc.getInt("account_id"),
                                realNamesFormatted, acc.getDouble("acc_balance")));
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
     * Callback method from the dialog asking to confirm account deletion
     */
    @Override
    public void onAccountDelete() {

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
