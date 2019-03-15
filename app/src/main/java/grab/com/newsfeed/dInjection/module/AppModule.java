package grab.com.newsfeed.dInjection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import grab.com.newsfeed.dInjection.ApplicationContext;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }
}
