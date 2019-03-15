package grab.com.newsfeed.network;

public interface NetworkResponseListener {
    void onError(int reqType, Throwable e);

    void onSuccess(int reqType, Object aObject);
}
