package ca.outercove.uomiapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ca.outercove.uomiapplication.fragments.AccountsViewFragment.OnListFragmentInteractionListener;
import ca.outercove.uomiapplication.dummy.DummyContent.DummyItem;
import ca.outercove.uomiapplication.listClasses.AccountsViewContent.AccountsViewItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AccountsViewListAdapter extends RecyclerView.Adapter<AccountsViewListAdapter.ViewHolder> {

    private final List<AccountsViewItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AccountsViewListAdapter(List<AccountsViewItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_accounts_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAvatarView.setImageResource(R.drawable.uomi3);
        holder.mContactView.setText(mValues.get(position).contactName);
        holder.mBalanceView.setText(mValues.get(position).balance);

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
        public final ImageView mAvatarView;
        public final TextView mContactView;
        public final TextView mBalanceView;
        public AccountsViewItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = view.findViewById(R.id.account_contact_avatar_id);
            mContactView = view.findViewById(R.id.account_contact_id);
            mBalanceView = view.findViewById(R.id.account_balance_id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContactView.getText() + "'";
        }
    }
}
