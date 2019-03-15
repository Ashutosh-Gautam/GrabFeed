package grab.com.newsfeed.feed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import grab.com.newsfeed.R;
import grab.com.newsfeed.base.Eventlistener;
import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.data.cache.GlideApp;
import grab.com.newsfeed.data.cache.GlideRequests;
import grab.com.newsfeed.data.model.FeedItem;
import grab.com.newsfeed.utils.Utility;

public class FeedFragment extends Fragment implements FeedContract.IView{

    private Activity activity;
    private FeedContract.IPresenter presenter;
    private GlideRequests requestManager;
    private FeedAdapter feedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Presenter();
        presenter.setView(this);

        return inflater.inflate(R.layout.feed_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isActivityNotNull()) {
            requestManager = GlideApp.with(activity);
            initViews(view);
            presenter.fetchFeed();
        }
    }

    private void initViews(View view) {

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            presenter.fetchFeed();
        });

        RecyclerView feedRv = view.findViewById(R.id.feed_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        feedRv.setLayoutManager(linearLayoutManager);

        Eventlistener eventlistener = (eventType, aObject) -> {
            if (eventType == IntegerConstants.FEED_DETAIL) {
                if(Utility.isOnline()) {
                    ((Eventlistener<Object>) activity).onEvent(IntegerConstants.FEED_DETAIL, aObject);
                } else {
                    showErrMsg("Running app in offline mode");
                }
            }
        };
        feedAdapter = new FeedAdapter(requestManager, eventlistener);
        feedRv.setAdapter(feedAdapter);
    }

    private boolean isActivityNotNull() {
        if (activity == null) {
            activity = getActivity();
        }
        return !(null == activity || activity.isFinishing());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void fragIsVisible() {

    }

    @Override
    public void fragIsInvisible() {

    }

    @Override
    public void onEvent(int eventType, Object aObject) {

    }

    @Override
    public void showErrMsg(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateFeed(List<FeedItem> feedItems) {
        swipeRefreshLayout.setRefreshing(false);
        feedAdapter.setFeedItems(feedItems);
        feedAdapter.notifyDataSetChanged();
    }
}
