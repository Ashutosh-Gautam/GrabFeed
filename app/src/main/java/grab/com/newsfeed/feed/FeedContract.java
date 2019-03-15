package grab.com.newsfeed.feed;

import java.util.List;

import grab.com.newsfeed.base.BaseContract;
import grab.com.newsfeed.data.model.FeedItem;

public class FeedContract {
    public interface IPresenter extends BaseContract.BasePresenter {
        void fetchFeed();
    }

    public interface IView extends BaseContract.BaseView {

        void updateFeed(List<FeedItem> feedItems);
    }
}
