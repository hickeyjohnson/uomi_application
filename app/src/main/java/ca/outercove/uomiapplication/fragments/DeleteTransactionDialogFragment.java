package ca.outercove.uomiapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;

import ca.outercove.uomiapplication.appObjects.SingleAccountViewContent;
import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;
import ca.outercove.uomiapplication.listAdapters.TransactionsListAdapter;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import ca.outercove.uomiapplication.R;

/**
 * Dialog Fragment for deleting a transaction. This is shown when you swipe right on an transaction.
 *
 */
public class DeleteTransactionDialogFragment extends DialogFragment {

    private TransactionsListAdapter adapter;
    static DeleteTransactionDialogListener mCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate and set layout for dialog
        // null as parent view because going in dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_delete_transaction, null))
                .setPositiveButton(R.string.delete_transaction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTransaction(getArguments().getInt("transactionId"), getArguments().getInt("listIndex"));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteTransactionDialogFragment.this.getDialog().cancel();
                        adapter.notifyDataSetChanged();
                    }
                });

        return builder.create();
    }

    public static DeleteTransactionDialogFragment newInstance(int title, TransactionsListAdapter.ViewHolder viewHolder) {
        DeleteTransactionDialogFragment frag = new DeleteTransactionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("transactionId", viewHolder.mItem.id);
        args.putInt("listIndex", viewHolder.getAdapterPosition());
        args.putDouble("transAmount", viewHolder.mItem.value);
        frag.setArguments(args);
        return frag;
    }

    /**
     * Empty constructor
     */
    public DeleteTransactionDialogFragment() {

    }

    //Takes the adapter for SingleAccountFragment
    public void setAdapter(TransactionsListAdapter adapter) {
        this.adapter = adapter;
    }

    private void deleteTransaction(Integer accountId, final Integer listIndex) {
        String url = getString(R.string.base_url) + "/transactions/remove/" + accountId.toString();

        JsonObjectRequest deleteTransactionRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                adapter.remove(listIndex);
                mCallback.onTransactionDelete(getArguments().getDouble("transAmount"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Delete did not occur! VOLLEYERROR!");
                System.out.println(error.toString());
                DeleteTransactionDialogFragment.this.getDialog().cancel();
                adapter.notifyDataSetChanged();
            }
        }
        );

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(deleteTransactionRequest);
    }

    public void setListener(DeleteTransactionDialogListener callback) {
        try {
            mCallback = callback;
        } catch (ClassCastException e) {
            throw new ClassCastException(callback.toString() + " must implement DeleteTransactionDialogListener");
        }
    }


    public interface DeleteTransactionDialogListener {
        void onTransactionDelete(Double amount);
    }
}
