package feed.news.com.newsfeed.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import feed.news.com.newsfeed.R;
import feed.news.com.newsfeed.service.RequestClient;
import feed.news.com.newsfeed.service.ReqeustInterface;
import feed.news.com.newsfeed.model.FeedResponse;
import feed.news.com.newsfeed.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_feeds) SwipeRefreshLayout refreshFeeds;
    @BindView(R.id.recycle_feeds) RecyclerView recFeed;
    @BindView(R.id.txt_error) TextView txtError;

    private ReqeustInterface requestService;

    private ProgressDialog progressDialog;

    private int dataSize = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initializeLayout();
        setLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void initializeLayout() {
        ButterKnife.bind(NewsActivity.this);
        requestService = RequestClient.getClient().create(ReqeustInterface.class);
    }

    public void setLayout() {
        refreshFeeds.setOnRefreshListener(NewsActivity.this);
        if(Utils.isInternetConnectionAvailable(NewsActivity.this)){
            displayFeeds(false);
        } else {
            showErrorMessage(true, getString(R.string.no_internet_error_message));
        }
    }

    @Override
    public void onRefresh() {
        displayFeeds(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh ) {
            displayFeeds(true);
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayFeeds(boolean isRefresh) {
        if(!isRefresh)
            showProgressDialog(getString(R.string.loading_progress));
        else
            refreshFeeds.setRefreshing(true);
        Call<FeedResponse> call = requestService.getFeeds();
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                dismissProgressDialog(progressDialog);
                if(refreshFeeds.isRefreshing()){
                    refreshFeeds.setRefreshing(false);
                }
                FeedResponse body = response.body();

                ActionBar actionBar = getSupportActionBar();
                if(actionBar!=null && body.getTitle() != null){
                    actionBar.setTitle(body.getTitle());
                }
                dataSize = body.getFeeds().size();
                NewsAdapter navigationDrawerAdapter = new NewsAdapter(NewsActivity.this, body.getFeeds());
                recFeed.setAdapter(navigationDrawerAdapter);
                recFeed.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                showErrorMessage(false, "");
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                dismissProgressDialog(progressDialog);
                if(refreshFeeds.isRefreshing()){
                    refreshFeeds.setRefreshing(false);
                }
                showErrorMessage(true, getString(R.string.server_error));
            }
        });
    }

    private void showErrorMessage(boolean isShown, String message){
        txtError.setText(message);
        txtError.setVisibility(isShown ? View.VISIBLE : View.GONE);
        recFeed.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    private void showProgressDialog(String progressMessage){
        progressDialog = new ProgressDialog(NewsActivity.this);
        progressDialog.setMessage(progressMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    private void dismissProgressDialog(ProgressDialog dialog){
        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();
        }
    }

    public int getDataSize() {
        return dataSize;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshFeeds.setOnRefreshListener(null);
        dismissProgressDialog(progressDialog);
        progressDialog = null;
    }
}
