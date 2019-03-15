package grab.com.newsfeed.data.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Feed {

    @JsonField
    String status;

    @JsonField
    int totalResults;

    @JsonField
    List<FeedItem> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<FeedItem> getArticles() {
        return articles;
    }

    public void setArticles(List<FeedItem> articles) {
        this.articles = articles;
    }

}
