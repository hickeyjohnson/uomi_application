package ca.outercove.uomiapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;
import ca.outercove.uomiapplication.listAdapters.AccountsViewListAdapter;

/**
 * Dialog Fragment for deleting an account. This is shown when you swipe right on an account.
 */
public class DeleteAccountDialogFragment extends DialogFragment {


    private AccountsViewListAdapter adapter;
    private DeleteAccountDialogListener mCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate and set layout for dialog
        // null as parent view because going in dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_delete_account, null))
                .setPositiveButton(R.string.delete_account, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount(getArguments().getInt("accountId"), getArguments().getInt("listIndex"));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteAccountDialogFragment.this.getDialog().cancel();
                        adapter.notifyDataSetChanged();
                    }
                });

        return builder.create();
    }

    public static DeleteAccountDialogFragment newInstance(int title, AccountsViewListAdapter.ViewHolder vh) {
        DeleteAccountDialogFragment frag = new DeleteAccountDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("accountId", vh.mItem.id);
        args.putInt("listIndex", vh.getAdapterPosition());
        frag.setArguments(args);
        return frag;
    }

    /**
     * Method to handle deleting the account.
     */
    private void deleteAccount(Integer accountId, final Integer listIndex) {
        String url = getString(R.string.base_url) + "/accounts/remove/" + Integer.toString(accountId);

        JsonObjectRequest deleteAccountRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                adapter.remove(listIndex);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Error handling
            }
        }
        );

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(deleteAccountRequest);
    }

    /**
     * Empty constructor
     */
    public DeleteAccountDialogFragment() {

    }

    public void setListener(DeleteAccountDialogListener callback) {
        try {
            mCallback = callback;
        } catch (ClassCastException e) {
            throw new ClassCastException(callback.toString() + " must implement DeleteAccountDialogListener!");
        }
    }

    //Takes the adapter for Accounts view Fragment
    public void setAdapter(AccountsViewListAdapter adapter) {
        this.adapter = adapter;
    }

    public interface DeleteAccountDialogListener {
        void onAccountDelete();
    }

}

