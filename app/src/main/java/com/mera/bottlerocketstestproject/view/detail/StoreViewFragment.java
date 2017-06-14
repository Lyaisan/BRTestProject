package com.mera.bottlerocketstestproject.view.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mera.bottlerocketstestproject.R;
import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a view with selected store's data.
 */
public class StoreViewFragment extends Fragment {
    private JStore store;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        String storeID = bundle.getString(getString(R.string.store_id));
        store = JStore.findStoreById(storeID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_info_layout, container, false);

        ImageView logoView = (ImageView) view.findViewById(R.id.logo_view);

        TextView phoneView = (TextView) view.findViewById(R.id.phone_view);
        TextView cityView = (TextView) view.findViewById(R.id.city_view);
        TextView stateView = (TextView) view.findViewById(R.id.state_view);
        TextView zipcodeView = (TextView) view.findViewById(R.id.zipcode_view);

        Picasso.with(getContext())
                .load(store.getStoreLogoURL())
                .into(logoView);

        initPhoneView(phoneView);
        cityView.setText(store.getCity());
        stateView.setText(store.getState());
        zipcodeView.setText(store.getZipcode());

        return view;
    }

    private void initPhoneView(TextView phoneView) {
        Spannable spannedPhoneNumber = new SpannableString(store.getPhone());
        spannedPhoneNumber.setSpan(new UnderlineSpan(), 0, store.getPhone().length(), 0);
        phoneView.setText(spannedPhoneNumber);
        phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(store.getPhone().trim());
            }
        });
    }

    /**
     * Make call via installed application
     */
    private void makeCall(String number) {
        number = getString(R.string.tel) + number;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.device_cannot_make_call), Toast.LENGTH_LONG).show();
        }
    }
}
