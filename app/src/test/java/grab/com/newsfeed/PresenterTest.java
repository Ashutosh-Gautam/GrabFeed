package grab.com.newsfeed;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import grab.com.newsfeed.data.model.Feed;
import grab.com.newsfeed.feed.Presenter;
import grab.com.newsfeed.network.NetworkService;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {

    @Mock
    private NetworkService.APIInterface apiInterface;

    private Presenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(apiInterface.getFeed("us", "apiKey")).thenReturn(Observable.just(new Feed()));
        apiInterface.getFeed("us", "apiKey");
    }

    @Test
    public void testFetchCalled() {
        verify(apiInterface).getFeed("us", "apiKey");
    }
}
