package ca.outercove.uomiapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.listAdapters.TransactionsListAdapter;

/**
 * Dialog Fragment for deleting a transaction. This is shown when you swipe right on an transaction.
 *
 */
public class DeleteTransactionDialogFragment extends DialogFragment {

    private TransactionsListAdapter adapter;

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
                        // TODO: Delete the transaction
                        throw new UnsupportedOperationException("Delete transaction not implemented");
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

    public static DeleteTransactionDialogFragment newInstance(int title) {
        DeleteTransactionDialogFragment frag = new DeleteTransactionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
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
}
