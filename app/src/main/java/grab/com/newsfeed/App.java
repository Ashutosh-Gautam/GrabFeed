package grab.com.newsfeed;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import grab.com.newsfeed.dInjection.component.ActivityComponent;
import grab.com.newsfeed.dInjection.component.DaggerActivityComponent;
import grab.com.newsfeed.dInjection.module.AppModule;

public class App extends Application {

    private static ActivityComponent component;

    private static App app;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onCreate() {

        app = App.this;
        super.onCreate();
        component = DaggerActivityComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static ActivityComponent getComponent() {
        return component;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static Application getApp() {
        return app;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}
