package feed.news.com.newsfeed.service;

import feed.news.com.newsfeed.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ReqeustInterface {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    Call<FeedResponse> getFeeds();
}
