package com.mera.bottlerocketstestproject.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mera.bottlerocketstestproject.R;
import com.mera.bottlerocketstestproject.base.di.ActivityModule;
import com.mera.bottlerocketstestproject.base.di.ApplicationComponent;
import com.mera.bottlerocketstestproject.base.mvp.view.BaseActivity;

/**
 * Main activity of the application, starts on application start.
 * Contains a fragment with list of stores.
 */
public class MainActivity extends BaseActivity<MainActivityContract.View, MainActivityContract.Presenter>
        implements MainActivityContract.View, StoresFragment.RefreshingSubtitleInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMainContent();
    }

    @Override
    protected void injectDependencies(ApplicationComponent applicationComponent) {
        applicationComponent.plusMainActivityComponent(new MainActivityModule(), new ActivityModule(this)).inject(this);
    }

    /**
     * Adding fragment with main content
     */
    private void initMainContent() {
        StoresFragment storesFragment = (StoresFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        if (storesFragment == null) {
            storesFragment = new StoresFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, storesFragment).commit();
        }
    }

    @Override
    public void setSubTitle(String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
