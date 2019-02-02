package amir.digital.paper.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import amir.digital.paper.adapter.NewsAdapter;
import amir.digital.paper.factory.NewsDataSourceFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import amir.digital.paper.DetailsActivity;
import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.R;
import amir.digital.paper.model.NewsModel;
import amir.digital.paper.other.InternetConnection;

public class HomeFragment extends Fragment implements NewsAdapter.NewsClickListener,NewsAdapter.SaveClickListener,NewsAdapter.ShareClickListener {
    private RecyclerView recyclerView_vertical;
    private String currentNewsSource = StaticDataManager.google_news;
    private SwipeRefreshLayout swipe_refresh_layout;
    private GridLayoutManager layoutManager;
    private NewsAdapter newsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        int columnCount=getArguments().getInt(StaticDataManager.column_count_key);
        layoutManager=new GridLayoutManager(getContext(), columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());
        recyclerView_vertical.setAdapter(newsAdapter);

        newsAdapter=new NewsAdapter(this,this,this);

        recyclerView_vertical.setAdapter(newsAdapter);

        showNews(StaticDataManager.google_news);


        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                showNews(currentNewsSource);

            }
        });
//        showNews(StaticDataManager.google_news);
        return view;
    }



    private void initializeView(View view) {
        recyclerView_vertical = view.findViewById(R.id.all_news_vertical_recyclerView);
        swipe_refresh_layout = view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
    }

    public void showNews(String newsSourceName) {
        swipe_refresh_layout.setRefreshing(true);
        LivePagedListBuilder<Long,NewsModel.Article> builder=new LivePagedListBuilder(new NewsDataSourceFactory(newsSourceName),10);
        builder.build().observe(this, new Observer<PagedList<NewsModel.Article>>() {
            @Override
            public void onChanged(PagedList<NewsModel.Article> articles) {

                newsAdapter.submitList(articles);
                swipe_refresh_layout.setRefreshing(false);
            }
        });
    }




    @Override
    public void onNewsClick(NewsModel.Article article) {
        showAlertDialog(article.getUrl(),article.getTitle());
    }

    public void onListTypeChange(int columnCount){
        layoutManager.setSpanCount(columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
    }

    private void showAlertDialog(final String url, final String title) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Load content")
                .setMessage("Where do you want to load content ?")
                .setPositiveButton("Browser", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        runActivity(browserIntent);

                    }
                })
                .setNegativeButton("WebView", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getContext(),DetailsActivity.class);
                        intent.putExtra(StaticDataManager.url_key,url);
                        intent.putExtra(StaticDataManager.title_key,title);
                        runActivity(intent);
                    }
                })
                .setIcon(R.drawable.ic_about)
                .show();

    }

    private void runActivity(Intent intent) {
        if(InternetConnection.isNetworkConnected(getContext())){
            startActivity(intent);
        }else {
            InternetConnection.showError(getContext());
        }
    }


    @Override
    public void onSaveClick(NewsModel.Article article) {
        Toast.makeText(getContext(),"save",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(NewsModel.Article article) {
        Toast.makeText(getContext(),"Share",Toast.LENGTH_SHORT).show();
    }
}
