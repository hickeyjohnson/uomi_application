package ca.outercove.uomiapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import ca.outercove.uomiapplication.R;

public class DeleteAccountDialogFragment extends DialogFragment {

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
                            // TODO: Create the account
                            throw new UnsupportedOperationException("Delete account not implemented");
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeleteAccountDialogFragment.this.getDialog().cancel();
                        }
                    });

            return builder.create();
        }

        public static DeleteAccountDialogFragment newInstance(int title) {
            DeleteAccountDialogFragment frag = new DeleteAccountDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        /**
         * Empty constructor
         */
        public DeleteAccountDialogFragment() {

        }

}

