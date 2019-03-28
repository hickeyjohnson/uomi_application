package ca.outercove.uomiapplication.listAdapters;

import android.support.annotation.NonNull;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public List<TransactionItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public TransactionsListAdapter(List<TransactionItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTransPayerView.setText(mValues.get(position).transactionPayer);
        holder.mTransItemView.setText(mValues.get(position).transactionName);
        holder.mValueView.setAmount(mValues.get(position).value.floatValue());

        if (mValues.get(position).value.floatValue() < 0) {
            holder.mValueView.setBaseColor(ContextCompat.getColor(mContext, R.color.owingRed));
            holder.mValueView.setDecimalsColor(ContextCompat.getColor(mContext, R.color.owingRed));
            holder.mValueView.setSymbolColor(ContextCompat.getColor(mContext, R.color.owingRed));
        }

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

    public void remove(int position) {
        mValues.remove(position);
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTransPayerView;
        final TextView mTransItemView;
        final MoneyTextView mValueView;
        public TransactionItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTransPayerView = view.findViewById(R.id.transaction_userPaid_id);
            mTransItemView = view.findViewById(R.id.transaction_item_id);
            mValueView = view.findViewById(R.id.transaction_value_id);
        }
    }



}
