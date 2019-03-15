package grab.com.newsfeed.dInjection.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import grab.com.newsfeed.dInjection.module.AppModule;
import grab.com.newsfeed.data.Repository;
import grab.com.newsfeed.feed.Presenter;

@Singleton
@Component(modules={AppModule.class})
public interface ActivityComponent {

    void inject(Presenter presenter);

    Application getApplication();

    Repository getRepository();
}
