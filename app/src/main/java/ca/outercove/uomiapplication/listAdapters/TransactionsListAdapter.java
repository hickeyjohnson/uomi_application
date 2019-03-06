package ca.outercove.uomiapplication.listAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.fabiomsr.moneytextview.MoneyTextView;

import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.appObjects.SingleAccountViewContent.TransactionItem;
import ca.outercove.uomiapplication.fragments.SingleAccountFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 *{@link RecyclerView.Adapter} that can display a {@link TransactionItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {

    private final List<TransactionItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TransactionsListAdapter(List<TransactionItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTransPayerView.setText(mValues.get(position).transactionPayer);
        holder.mTransItemView.setText(mValues.get(position).transactionName);
        holder.mValueView.setAmount(mValues.get(position).value.floatValue());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTransPayerView;
        public final TextView mTransItemView;
        public final MoneyTextView mValueView;
        public TransactionItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTransPayerView = view.findViewById(R.id.transaction_userPaid_id);
            mTransItemView = view.findViewById(R.id.transaction_item_id);
            mValueView = view.findViewById(R.id.transaction_value_id);
        }
    }
}
