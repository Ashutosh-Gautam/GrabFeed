package grab.com.newsfeed.network;

import io.reactivex.Observable;

public class ObservableService {

    public <T> Observable getFeed(int wibeType) {
        return NetworkService.API.getFeed("us", "25a7454db2cc4999bcd7f60f4cf3fa27");
    }
}