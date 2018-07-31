
package feed.news.com.newsfeed.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedResponse {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("rows")
    @Expose
    private List<Feed> feeds = null;

    public FeedResponse() {
    }


    public FeedResponse(String title, List<Feed> feeds) {
        super();
        this.title = title;
        this.feeds = feeds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

}
