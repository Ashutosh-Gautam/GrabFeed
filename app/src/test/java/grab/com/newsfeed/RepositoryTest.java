package grab.com.newsfeed;

import android.content.Context;
import android.content.SharedPreferences;

import com.bluelinelabs.logansquare.LoganSquare;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import grab.com.newsfeed.constants.StringConstants;
import grab.com.newsfeed.data.Repository;
import grab.com.newsfeed.data.model.FeedItem;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    private Repository repository;

    @Mock
    private SharedPreferences mMockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mMockEditor;

    private List<FeedItem> feedItems = new ArrayList<>();

    @Before
    public void initMocks() {

        FeedItem feedItem = new FeedItem();
        feedItem.setAuthor("CNN");
        feedItem.setContent("News");
        feedItem.setTitle("Blah");
        feedItems.add(feedItem);

        repository = createMockRepository();
    }

    @Test
    public void repository_SaveAndReadFeed() {

        repository.saveFeed(feedItems);

        List<FeedItem> feedItemList = repository.getCachedFeed();

        Assert.assertNotNull(feedItemList);
        Assert.assertEquals("Checking that FeedItems has been persisted and read correctly",
                feedItemList.get(0).getAuthor(),
                feedItems.get(0).getAuthor());
    }

    private Repository createMockRepository() {

        try {
            when(mMockSharedPreferences.getString(eq(StringConstants.FEED), anyString()))
                    .thenReturn(LoganSquare.serialize(feedItems));
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(mMockEditor.commit()).thenReturn(true);
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new Repository(mMockSharedPreferences);

    }
}
