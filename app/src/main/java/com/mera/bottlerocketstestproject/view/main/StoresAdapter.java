package com.mera.bottlerocketstestproject.view.main;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mera.bottlerocketstestproject.R;
import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.mera.bottlerocketstestproject.view.detail.StoreViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying data of the stores list
 */
class StoresAdapter extends RecyclerView.Adapter  {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<JStore> mItems = new ArrayList<>();

    public StoresAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<JStore> data) {
        if (data != null) {
            addAll(data);
        }
    }

    /**
     * Clearing adapter
     */
    private void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    /**
     * Adding items to adapter
     */
    private void addAll(List<JStore> list) {
        clear();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_store, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JStore store = mItems.get(position);
        fillStore((ViewHolder)holder, store);
    }

    private void fillStore(final ViewHolder holder, final JStore store) {
        holder.logoView.setImageBitmap(null);
        Picasso.with(mContext)
                .load(store.getStoreLogoURL())
                .into(holder.logoView);
        holder.phoneView.setText(store.getPhone());
        holder.addressView.setText(store.getAddress());
        holder.addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%s,%s", store.getLatitude(), store.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                try {
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(mContext, R.string.no_application, Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.cardlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store.getStoreID() == null){
                    Snackbar.make(v, R.string.no_storeid_error, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Opening a new screen that displays all of the details of the selected store
                Intent intent = new Intent(mContext, StoreViewActivity.class);
                intent.putExtra(mContext.getString(R.string.store_id), store.getStoreID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final CardView cardlistItem;
        final ImageView logoView;
        final TextView phoneView;
        final TextView addressView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardlistItem = (CardView) itemView.findViewById(R.id.cardlist_item);
            logoView = (ImageView) itemView.findViewById(R.id.logo_view);
            phoneView = (TextView) itemView.findViewById(R.id.phone_view);
            addressView = (TextView) itemView.findViewById(R.id.address_view);
        }
    }
}
