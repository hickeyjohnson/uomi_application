package ca.outercove.uomiapplication.listAdapters;

import android.support.annotation.NonNull;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.fabiomsr.moneytextview.MoneyTextView;

import ca.outercove.uomiapplication.R;
import ca.outercove.uomiapplication.fragments.AccountsViewFragment.OnListFragmentInteractionListener;
import ca.outercove.uomiapplication.appObjects.AccountsViewContent.AccountsViewItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AccountsViewItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AccountsViewListAdapter extends RecyclerView.Adapter<AccountsViewListAdapter.ViewHolder> {

    private final List<AccountsViewItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public AccountsViewListAdapter(List<AccountsViewItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_accounts_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAvatarView.setImageResource(R.mipmap.ic_glossy_app_icon);
        holder.mContactView.setText(mValues.get(position).contactName);
        holder.mBalanceView.setAmount(mValues.get(position).balance.floatValue());

        if (mValues.get(position).balance.floatValue() < 0) {
            holder.mBalanceView.setBaseColor(ContextCompat.getColor(mContext, R.color.owingRed));
            holder.mBalanceView.setDecimalsColor(ContextCompat.getColor(mContext, R.color.owingRed));
            holder.mBalanceView.setSymbolColor(ContextCompat.getColor(mContext, R.color.owingRed));
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

    public void remove(int positon) {
        mValues.remove(positon);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mAvatarView;
        final TextView mContactView;
        final MoneyTextView mBalanceView;
        public AccountsViewItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = view.findViewById(R.id.account_contact_avatar);
            mContactView = view.findViewById(R.id.account_contact_id);
            mBalanceView = view.findViewById(R.id.account_balance_id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContactView.getText() + "'";
        }
    }

}
