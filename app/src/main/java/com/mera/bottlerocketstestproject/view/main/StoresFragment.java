package com.mera.bottlerocketstestproject.view.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mera.bottlerocketstestproject.R;
import com.mera.bottlerocketstestproject.base.di.ApplicationComponent;
import com.mera.bottlerocketstestproject.base.di.FragmentModule;
import com.mera.bottlerocketstestproject.base.mvp.view.BaseFragment;
import com.mera.bottlerocketstestproject.base.utils.Utils;
import com.mera.bottlerocketstestproject.data.entity.JStore;

import java.util.List;

/**
 * A placeholder fragment containing a view with stores list.
 */
public class StoresFragment extends BaseFragment<StoresFragmentContract.View, StoresFragmentContract.Presenter<JStore>>
        implements StoresFragmentContract.View<JStore> {

    private StoresAdapter adapter;

    private ViewSwitcher mErrorEmptyViewSwitcher;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private RefreshingSubtitleInterface mRefreshingSubtitleInterface;
    private FrameLayout mMapListRecyclerContainer;
    private FrameLayout mProgress;
    private WrappedLinearLayoutManager mLayoutManager;

    public StoresFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void injectDependencies(ApplicationComponent applicationComponent) {
        applicationComponent.plusStoresFragmentComponent(new StoresFragmentModule(), new FragmentModule(this)).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRefreshingSubtitleInterface = (RefreshingSubtitleInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        fillViews(view);
        return view;
    }

    /**
     * Initializing views
     */
    private void fillViews(View view) {
        mMapListRecyclerContainer = (FrameLayout) view.findViewById(R.id.map_list_recycler_container);
        mProgress = (FrameLayout) view.findViewById(android.R.id.progress);
        mErrorEmptyViewSwitcher = (ViewSwitcher) view.findViewById(R.id.empty_list_view_switcher);

        adapter = new StoresAdapter(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.map_list_recycler_view);
        mLayoutManager = new WrappedLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        initRefreshTextView();
        initializeSwipeRefreshLayout(view);
    }

    private void initializeSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefresh());

        //solution for scrolling up in recyclerview
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ?
                                0 : mRecyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstVisibleItemPosition() == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

    /**
     * Initializing refresh button under empty view
     */
    private void initRefreshTextView() {
        TextView updateAfterExc = (TextView) mErrorEmptyViewSwitcher.findViewById(R.id.update_after_exc);
        TextView updateAfterEmpty = (TextView) mErrorEmptyViewSwitcher.findViewById(R.id.update_after_empty);
        SpannableString content = new SpannableString(getString(R.string.refresh));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        updateAfterExc.setText(content);
        updateAfterExc.setOnClickListener(v -> reloadData());

        updateAfterEmpty.setText(content);
        updateAfterEmpty.setOnClickListener(v -> reloadData());

    }

    @Override
    public void onPresenterCreated(StoresFragmentContract.Presenter<JStore> presenter) {
        super.onPresenterCreated(presenter);
        presenter.loadData();
    }

    /**
     * Show list of items or empty view
     */
    @Override
    public void showErrorEmptyViewSwitcher(boolean show, Throwable e) {
        if (show) {
            mRecyclerView.setVisibility(View.GONE);
            mErrorEmptyViewSwitcher.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mErrorEmptyViewSwitcher.setVisibility(View.GONE);
        }
        if (e != null) {
            refreshListEmptyView(e.getMessage());
        }
    }

    /**
     * Show one of the views of ViewSwitcher (with error of without it)
     */
    private void refreshListEmptyView(String error) {
        if (error == null) {
            if (mErrorEmptyViewSwitcher.getDisplayedChild() != 0) {
                mErrorEmptyViewSwitcher.showNext();
            }
        } else {
            ((TextView) mErrorEmptyViewSwitcher.findViewById(R.id.empty_view_error)).setText(error);
            if (mErrorEmptyViewSwitcher.getDisplayedChild() != 1) {
                mErrorEmptyViewSwitcher.showNext();
            }
        }
    }

    @Override
    public void showStores(List<JStore> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();

        if (!Utils.isNetworkStateOnline(getContext())) {
            mRefreshingSubtitleInterface.setSubTitle("Cached data");
        } else {
            mRefreshingSubtitleInterface.setSubTitle("");
        }
    }

    private void reloadData() {
        presenter.onRefresh();
    }

    /**
     * Showing/hiding refreshing indicator of swipeRefreshLayout
     */
    @Override
    public void showProgress(boolean show) {
        if (mSwipeRefreshLayout == null)
            return;

        getActivity().runOnUiThread(() -> {
            if (show) {
                if (adapter.getItemCount() == 0) {
                    setVisibleList(false);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    setVisibleList(true);
                    mSwipeRefreshLayout.setRefreshing(true);
                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                setVisibleList(true);
            }
        });
    }

    @Override
    public void setVisibleList(boolean visible) {
        getActivity().runOnUiThread(() -> {
            if (visible) {
                mProgress.setVisibility(View.GONE);
                mMapListRecyclerContainer.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.VISIBLE);
                mMapListRecyclerContainer.setVisibility(View.GONE);
            }
        });
    }

    private static class WrappedLinearLayoutManager extends LinearLayoutManager {

        public WrappedLinearLayoutManager(Context context) {
            super(context);
        }

        public WrappedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrappedLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }


        /**
         * There is a bug in RecyclerView for some devices: it throws an exception when data size is decreased. This is a workaround.
         */
        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

    public interface RefreshingSubtitleInterface {
        void setSubTitle(String text);
    }
}
