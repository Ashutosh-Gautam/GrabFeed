package grab.com.newsfeed.feed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import grab.com.newsfeed.R;
import grab.com.newsfeed.data.model.FeedItem;

public class FeedDetailFragment extends Fragment implements FeedContract.IView{


    private Activity activity;
    private String newsUrl;

    public static FeedDetailFragment newInstance(String url) {
        FeedDetailFragment fragment = new FeedDetailFragment();
        fragment.newsUrl = url;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isActivityNotNull()) {
            initViews(view);
        }
    }

    private void initViews(View view) {
        WebView webView = view.findViewById(R.id.feed_web_view);
        ProgressBar progressBar = view.findViewById(R.id.progress_circular);
        webView.loadUrl(newsUrl);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(activity, "Some error occurred. " + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

        });

    }

    private boolean isActivityNotNull() {
        if (activity == null) {
            activity = getActivity();
        }
        return !(null == activity || activity.isFinishing());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateFeed(List<FeedItem> feedItems) {

    }

    @Override
    public void fragIsVisible() {

    }

    @Override
    public void fragIsInvisible() {

    }

    @Override
    public void onEvent(int eventType, Object aObject) {

    }

    @Override
    public void showErrMsg(String message) {

    }
}
