package com.mera.bottlerocketstestproject.view.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mera.bottlerocketstestproject.R;
import com.mera.bottlerocketstestproject.base.utils.Utils;
import com.mera.bottlerocketstestproject.data.entity.JStore;

/**
 * Activity, containing fragments with data of the selected store.
 * It starts when user click to item in stores list
 */
public class StoreViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String storeID;
    private JStore store;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);

        storeID = getIntent().getStringExtra(getString(R.string.store_id));
        store = JStore.findStoreById(storeID);
        if (store == null){
            Toast.makeText(getApplicationContext(), "Error, store not initialized", Toast.LENGTH_SHORT).show();
            finish();
        }

        initActionBar();

        initMap();
        initMainContainer();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(store.getName());

            if (!Utils.isNetworkStateOnline(this)){
                getSupportActionBar().setSubtitle(R.string.cached_data);
            } else getSupportActionBar().setSubtitle(null);
        }
    }

    /**
     * Initializing fragment with data
     */
    private void initMainContainer() {
        StoreViewFragment storeViewFragment = (StoreViewFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (storeViewFragment == null){
            storeViewFragment = new StoreViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.store_id), storeID);
            storeViewFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, storeViewFragment).commit();
        }
    }

    /**
     * Initializing fragment with map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            mapFragment = new SupportMapFragment();
            mapFragment.setRetainInstance(true);
            mapFragment.getMapAsync(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (store.getLatitude() == null || store.getLongitude() == null) return;

        LatLng point = new LatLng(Double.valueOf(store.getLatitude()), Double.valueOf(store.getLongitude()));
        googleMap.addMarker(new MarkerOptions().position(point).title(store.getAddress()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
