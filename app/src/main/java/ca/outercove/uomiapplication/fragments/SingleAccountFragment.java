package ca.outercove.uomiapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.fabiomsr.moneytextview.MoneyTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.navigation.Navigation;
import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;
import ca.outercove.uomiapplication.listAdapters.TransactionsListAdapter;
import ca.outercove.uomiapplication.appObjects.SingleAccountViewContent;
import ca.outercove.uomiapplication.appObjects.SingleAccountViewContent.TransactionItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleAccountFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCOUNT_ID = "accountId";
    private static final String TITLE = "Transactions";
    private FloatingActionButton fab;
    private TransactionsListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer mAccountId;

    private MoneyTextView mBalanceAmountTV;
    private RecyclerView recyclerView;

    private SharedPreferences pref;

    private OnListFragmentInteractionListener mListener;

    public SingleAccountFragment() {
        // Required empty public constructor
    }


    // TODO: Customize parameter initialization
    public static SingleAccountFragment newInstance(int columnCount) {
        SingleAccountFragment fragment = new SingleAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_single_account, container, false);
        fab = view.findViewById(R.id.fabAddTransaction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("accountId", getArguments().getInt("accountId"));
                Navigation.findNavController(v).navigate(R.id.actionCreateTransaction, bundle);
            }
        });
        // Set the adapter
        Context context = view.getContext();
        this.pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.mBalanceAmountTV = view.findViewById(R.id.accountBalanceAmount);
        recyclerView = view.findViewById(R.id.transactionsRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getSingleAccountInformation();
//        mAdapter = new TransactionsListAdapter(SingleAccountViewContent.ITEMS, mListener);
//        recyclerView.setAdapter(mAdapter);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO:Delete Transaction
                DialogFragment newFrag = DeleteTransactionDialogFragment.newInstance(
                        R.string.delete_transaction);
                newFrag.show(getFragmentManager(), "dialog");
                ((DeleteTransactionDialogFragment) newFrag).setAdapter(mAdapter);
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    private void getSingleAccountInformation() {
        Integer accountId = getArguments().getInt("accountId", -1);
        Integer userId = this.pref.getInt("userId", -1);
        String url = getString(R.string.base_url) + "/transactions/" + userId.toString() + "/" + accountId.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SingleAccountViewContent.ITEMS.clear();
                try {
                    Double accountBalance = response.getDouble("account_balance");
                    mBalanceAmountTV.setAmount(accountBalance.floatValue());
                    if (accountBalance < 0) {
                        mBalanceAmountTV.setBaseColor(ContextCompat.getColor(getContext(), R.color.owingRed));
                        mBalanceAmountTV.setDecimalsColor(ContextCompat.getColor(getContext(), R.color.owingRed));
                        mBalanceAmountTV.setSymbolColor(ContextCompat.getColor(getContext(), R.color.owingRed));
                    }
                    JSONArray transactionItems = response.getJSONArray("transactions_list");
                    for (int i = 0; i < transactionItems.length(); i++) {
                        JSONObject transItem = transactionItems.getJSONObject(i);
                        // TODO: more meaningful payer info than just the user ID
                        SingleAccountViewContent.ITEMS.add(new TransactionItem(
                                transItem.getInt("transaction_id"), transItem.getString("trans_label"),
                                String.valueOf(transItem.getInt("user_owed")) + " paid",
                                transItem.getDouble("amount")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter = new TransactionsListAdapter(SingleAccountViewContent.ITEMS, mListener, getContext());
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: error response
            }
        }
        );

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TransactionItem item);
    }

}
