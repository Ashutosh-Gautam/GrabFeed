package grab.com.newsfeed.scheduler;

import io.reactivex.Scheduler;

public interface IScheduler {
    Scheduler mainThread();

    Scheduler backgroundThread();
}
