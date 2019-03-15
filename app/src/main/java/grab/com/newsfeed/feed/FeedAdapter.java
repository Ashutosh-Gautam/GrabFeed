package grab.com.newsfeed.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import grab.com.newsfeed.R;
import grab.com.newsfeed.base.Eventlistener;
import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.data.cache.GlideRequests;
import grab.com.newsfeed.data.model.FeedItem;
import grab.com.newsfeed.utils.Utility;

class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedItemHolder> {

    private List<FeedItem> feedItems;
    private GlideRequests glideRequests;
    private Eventlistener eventlistener;

    public FeedAdapter(GlideRequests glideRequests, Eventlistener eventlistener){
        this.glideRequests = glideRequests;
        this.eventlistener = eventlistener;
    }

    @NonNull
    @Override
    public FeedItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item_layout, viewGroup, false);
        return new FeedItemHolder(v);
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedItemHolder feedHolder, int i) {
        if (feedHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            bindViewHolder(feedHolder);
        }
    }

    private void bindViewHolder(FeedItemHolder feedHolder) {
        FeedItem feedItem = feedItems.get(feedHolder.getAdapterPosition());
        glideRequests.load(feedItem.getUrlToImage())
                .placeholder(R.color.light_blue)
                .into(feedHolder.thumb);
        feedHolder.header.setText(feedItem.getTitle());
        feedHolder.date.setText(Utility.dateFormatter(feedItem.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return feedItems != null ? feedItems.size() : 0;
    }

    class FeedItemHolder extends RecyclerView.ViewHolder{

        ImageView thumb;
        TextView header, date;

        public FeedItemHolder(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumbnail);
            header = itemView.findViewById(R.id.header);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(view -> {
                eventlistener.onEvent(IntegerConstants.FEED_DETAIL, feedItems.get(getAdapterPosition()).getUrl());
            });
        }
    }
}
