package grab.com.newsfeed.base;

public interface Eventlistener<T> {
    void onEvent(int eventType, T aObject);
}
