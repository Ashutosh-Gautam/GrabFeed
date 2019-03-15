package grab.com.newsfeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import grab.com.newsfeed.base.BaseContract;
import grab.com.newsfeed.base.Eventlistener;
import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.feed.FeedDetailFragment;
import grab.com.newsfeed.feed.FeedFragment;

public class FeedActivity extends AppCompatActivity implements Eventlistener {

    private Fragment currFragment;
    private List<Fragment> fragList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        fragList = new ArrayList<>();
        addFragment(IntegerConstants.FEED_LIST, true, true, true);
    }

    private void addFragment(final int fragmentType, boolean isFirst, boolean shouldReplace, Object aObject) {

        currFragment = getActiveFragment();
        if (currFragment != null) {
            ((BaseContract.BaseView) currFragment).fragIsInvisible();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case IntegerConstants.FEED_LIST:
                currFragment = FeedFragment.newInstance();
                break;
            case IntegerConstants.FEED_DETAIL:
                currFragment = FeedDetailFragment.newInstance((String) aObject);
                break;
        }

        try {
            if (!currFragment.isAdded()) {
                if (isFirst) {
                    clearBackStack();
                    transaction.replace(R.id.fragment_container, currFragment);
                } else {
                    if (shouldReplace) {
                        transaction.replace(R.id.fragment_container, currFragment);
                    } else {
                        transaction.add(R.id.fragment_container, currFragment);
                    }
                    transaction.addToBackStack(null);
                }
                transaction.commitAllowingStateLoss();
                fragList.add(currFragment);
            }
        } catch (Exception e) {

        }
    }

    public Fragment getActiveFragment() {
        if (fragList.size() > 0) {
            return fragList.get(fragList.size() - 1);
        }
        return null;
    }

    private void clearBackStack() {
        if (fragList != null && fragList.size() > 0) {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            if (count < 1) {
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.remove(fragList.get(0));
                try {
                    transaction.commitAllowingStateLoss();
                    fm.executePendingTransactions();
                } catch (Exception ignored) {

                }
            } else {
                for (int i = 0; i < count; i++) {
                    fm.popBackStack();
                }
            }
            fragList = new ArrayList<>();
        }
    }

    @Override
    public void onEvent(int eventType, Object aObject) {
        switch (eventType) {
            case IntegerConstants.FEED_DETAIL:
                addFragment(IntegerConstants.FEED_DETAIL, false, false, aObject);
                break;
        }
    }
}
