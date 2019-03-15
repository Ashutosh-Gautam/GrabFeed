package grab.com.newsfeed.data.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.BitmapTransitionFactory;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.NoTransition;

import java.io.InputStream;

import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.network.volley.VolleyUrlLoader;
import grab.com.newsfeed.utils.Utility;

import static android.content.Context.ACTIVITY_SERVICE;

@GlideModule
public class LimitCacheSizeGlideModule extends AppGlideModule {
    private static final int DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 10 * 1024 * 1024;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

        double totalGiB = getTotalBytesOfInternalStorage() / 1024.0 / 1024.0 / 1024.0;

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions = requestOptions.encodeFormat(Bitmap.CompressFormat.PNG);
        requestOptions = requestOptions.encodeQuality(100);
        requestOptions = requestOptions.priority(Priority.HIGH);
        builder.setDefaultRequestOptions(requestOptions);
        builder.setDefaultTransitionOptions(Bitmap.class, BitmapTransitionOptions.with((dataSource, isFirstResource) -> {
            if (dataSource == DataSource.RESOURCE_DISK_CACHE)
                return NoTransition.get();
            return new BitmapTransitionFactory(new DrawableCrossFadeFactory.Builder().build()).build(dataSource, isFirstResource);
        }));

        InternalCacheDiskCacheFactory factory = new InternalCacheDiskCacheFactory(context, Utility.getEmptyFilePath(IntegerConstants.IMAGE), DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB);
        builder.setDiskCache(factory);
        setMemoryCache(builder, context);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
    }

    private void setMemoryCache(GlideBuilder builder, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
            double availableMemory = memoryInfo.availMem;
            int cache =  (int)(10 * availableMemory/100);

            LruResourceCache lruMemCache = new LruResourceCache(cache);
            builder.setMemoryCache(lruMemCache);

            LruBitmapPool bitmapPool = new LruBitmapPool(cache/10);
            builder.setBitmapPool(bitmapPool);
        }
    }

    private long getTotalBytesOfInternalStorage() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return getTotalBytesOfInternalStorageWithStatFs(stat);
        } else {
            return getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private long getTotalBytesOfInternalStorageWithStatFs(StatFs stat) {
        return stat.getTotalBytes();
    }

    @SuppressWarnings("deprecation")
    private long getTotalBytesOfInternalStorageWithStatFsPreJBMR2(StatFs stat) {
        return (long) stat.getBlockSize() * stat.getBlockCount();
    }
}