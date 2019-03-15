package grab.com.newsfeed.network;


import grab.com.newsfeed.scheduler.AppScheduler;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetworkHelper {
    private final AppScheduler scheduler;
    private final NetworkResponseListener listener;
    private ObservableService observableService;

    public NetworkHelper(NetworkResponseListener listener) {
        this.listener = listener;
        observableService = new ObservableService();
        scheduler = new AppScheduler();
    }

    private Observer<Object> getSubscriber(final int reqType) {
        return new Observer<Object>() {

            @Override
            public void onError(Throwable e) {
                try {
                    listener.onError(reqType, e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object aObject) {
                listener.onSuccess(reqType, aObject);
                /*Response response = (Response) aObject;
                if (response != null) {
                    if (response.isSuccessful()) {
                        listener.onSuccess(reqType, response.body());

                    } else if (response.code() >= 400) {
                        listener.onError(reqType, new Throwable("Error"));
                    }
                } else {
                    listener.onError(reqType, new Throwable("Error"));
                }*/
            }
        };
    }

    public void getFeed(int wibeType) {
        observableService.getFeed(wibeType)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe(getSubscriber(wibeType));
    }
}
