package grab.com.newsfeed.base;

import java.util.List;

import grab.com.newsfeed.data.model.FeedItem;

public interface BaseContract {

    interface BasePresenter {

        void setView(BaseView iView);

        void onDestroy();

    }

    interface BaseView {

        void fragIsVisible();

        void fragIsInvisible();

        void onEvent(int eventType, Object aObject);

        void showErrMsg(String message);
    }

    interface RepoContract {

        interface Feed {
            List<FeedItem> getCachedFeed();

            void saveFeed(List<FeedItem> feedItems);

            Object getSharedPrefData(String key);

            void saveSharedPrefData(String key, Object aObject, boolean isPrimitive);
        }
    }
}
