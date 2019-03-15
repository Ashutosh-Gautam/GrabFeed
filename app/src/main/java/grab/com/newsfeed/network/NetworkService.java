package grab.com.newsfeed.network;

import grab.com.newsfeed.data.model.Feed;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NetworkService {

    public static APIInterface API;

    static {
        API = REST.createAPI("https://newsapi.org", APIInterface.class);
    }

    public interface APIInterface {

        @GET("/v2/top-headlines")
        Observable<Feed> getFeed(@Query("country") String country, @Query("apiKey") String apiKey);
    }
}
