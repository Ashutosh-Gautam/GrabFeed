package grab.com.newsfeed.feed;

import javax.inject.Inject;

import grab.com.newsfeed.App;
import grab.com.newsfeed.base.BaseContract;
import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.data.Repository;
import grab.com.newsfeed.data.model.Feed;
import grab.com.newsfeed.network.NetworkHelper;
import grab.com.newsfeed.network.NetworkResponseListener;
import grab.com.newsfeed.utils.Utility;

public class Presenter implements FeedContract.IPresenter, NetworkResponseListener {

    private final NetworkHelper networkHelper;
    private FeedContract.IView view;

    @Inject
    Repository repo;

    public Presenter() {
        networkHelper = new NetworkHelper(this);
        App.getComponent().inject(this);
    }

    @Override
    public void setView(BaseContract.BaseView iView) {
        this.view = (FeedContract.IView) iView;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void fetchFeed() {
        if(Utility.isOnline()) {
            networkHelper.getFeed(IntegerConstants.FEED_LIST);
        } else {
            view.updateFeed(repo.getCachedFeed());
            view.showErrMsg("Running app in offline mode");
        }
    }

    @Override
    public void onError(int reqType, Throwable e) {

    }

    @Override
    public void onSuccess(int reqType, Object aObject) {

        if(reqType == IntegerConstants.FEED_LIST) {
            Feed feed = (Feed) aObject;
            repo.saveFeed(feed.getArticles());
            view.updateFeed(feed.getArticles());
        }
    }
}
