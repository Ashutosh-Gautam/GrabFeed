package grab.com.newsfeed.network;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Ashutosh on 27/11/17.
 */

public class REST {

    private static Retrofit.Builder builder;
    public static String endPoint;

    public REST() {
    }

    public static <T> T createAPI(String endpoint, Class<T> APIInterface) {
        endPoint = endpoint;
        return builder.baseUrl(endpoint).build().create(APIInterface);
    }

    static {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {

                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request;

                                request = chain.request().newBuilder()
                                        .build();

                                return chain.proceed(request);
                            }
                        }
                )
                .writeTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();

        builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(okHttpClient);
    }
}
