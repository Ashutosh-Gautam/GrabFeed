package grab.com.newsfeed.data;

import android.content.SharedPreferences;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import grab.com.newsfeed.base.BaseContract;
import grab.com.newsfeed.constants.StringConstants;
import grab.com.newsfeed.data.model.FeedItem;

@Singleton
public class Repository implements BaseContract.RepoContract.Feed {

    private SharedPreferences sharedPrefs;

    @Inject
    public Repository(SharedPreferences sharedPreferences) {
        this.sharedPrefs = sharedPreferences;
    }

    @Override
    public List<FeedItem> getCachedFeed() {
        return (List<FeedItem>) getSharedPrefData(StringConstants.FEED);
    }

    @Override
    public void saveFeed(List<FeedItem> feedItems) {
        saveSharedPrefData(StringConstants.FEED, feedItems, false);
    }

    @Override
    public void saveSharedPrefData(String key, Object aObject, boolean isPrimitive) {
            if (aObject == null) {
                deleteSharedPrefData(key);
            } else {
                if (sharedPrefs != null) {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    String json;
                    try {
                        if (!isPrimitive) {
                            json = LoganSquare.serialize(aObject);
                        } else {
                            json = (String) aObject;
                        }
                        editor.putString(key, json);
                        editor.apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    @Override
    public Object getSharedPrefData(String key) {
        try {
            if (key != null) {

                if (sharedPrefs != null) {
                    String json = sharedPrefs.getString(key, null);
                    if (json == null) {
                        return null;
                    } else if (key.equals(StringConstants.FEED)) {
                        return LoganSquare.parseList(json, FeedItem.class);
                    }
                    return json;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteSharedPrefData(String key) {
        sharedPrefs.edit().remove(key).apply();
    }
}
