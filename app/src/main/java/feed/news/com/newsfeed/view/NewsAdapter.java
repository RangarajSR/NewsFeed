package feed.news.com.newsfeed.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import feed.news.com.newsfeed.R;
import feed.news.com.newsfeed.model.Feed;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.FeedViewHolder> {


    private List<Feed> feeds = Collections.emptyList();

    private LayoutInflater inflater;

    private Activity activityContext;

    NewsAdapter(Activity activityContext, List<Feed> feeds) {
        inflater = LayoutInflater.from(activityContext);
        this.activityContext = activityContext;
        this.feeds = feeds;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_row, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        Feed feed = feeds.get(position);
        if(feed.getTitle()!=null)
            holder.txtTitle.setText(feed.getTitle());

        if(feed.getImageHref()!=null)
            Glide.with(activityContext).load(feed.getImageHref()).into(holder.imgFeed);

        if(feed.getDescription()!=null)
            holder.txtDescription.setText(feed.getDescription());
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_description) TextView txtDescription;
        @BindView(R.id.img_feed) ImageView imgFeed;

        private FeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}