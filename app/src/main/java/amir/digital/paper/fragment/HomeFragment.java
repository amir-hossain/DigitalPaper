package amir.digital.paper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.R;
import amir.digital.paper.TempDetailsActivity;
import amir.digital.paper.adapter.HomeAdapter;
import amir.digital.paper.model.NewsModel;
import amir.digital.paper.other.InternetConnection;
import amir.digital.paper.retrofit.Client;
import amir.digital.paper.retrofit.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeAdapter.NewsClickListener {
    private static Retrofit retrofit = RetrofitInstance.getInstance();
    private static Client client = retrofit.create(Client.class);
    private RecyclerView recyclerView_vertical;
    private String currentNewsSource = StaticDataManager.google_news;
    private SwipeRefreshLayout swipe_refresh_layout;
    private GridLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        int columnCount=getArguments().getInt(StaticDataManager.column_count_key);
        layoutManager=new GridLayoutManager(getContext(), columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());



        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                showNews(currentNewsSource);

            }
        });
        showNews(StaticDataManager.google_news);
        return view;
    }


    private void initializeView(View view) {
        recyclerView_vertical = view.findViewById(R.id.all_news_vertical_recyclerView);
        swipe_refresh_layout = view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
    }

    public void showNews(String newsSourceName) {
        this.currentNewsSource=newsSourceName;
        swipe_refresh_layout.setRefreshing(true);
        if(InternetConnection.isNetworkConnected(getContext())){
            client.getTopNewsBySource(newsSourceName, StaticDataManager.news_api_key)
                    .enqueue(new Callback<NewsModel>() {
                        @Override
                        public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                            Log.i("res", response.body() + "");
                            recyclerView_vertical
                                    .setAdapter(new HomeAdapter(getContext(), response.body()
                                            .getArticles(), HomeFragment.this));
                            swipe_refresh_layout.setRefreshing(false);

                        }

                        @Override
                        public void onFailure(Call<NewsModel> call, Throwable t) {
                            Log.i("res", "error");
                            swipe_refresh_layout.setRefreshing(false);
                        }
                    });
        }else {
            swipe_refresh_layout.setRefreshing(false);
            InternetConnection.showError(getContext());
        }

    }




    @Override
    public void onNewsClick(NewsModel.Article article) {
        Intent intent = new Intent(getContext(), TempDetailsActivity.class);
        intent.putExtra(StaticDataManager.article_key, article);
        startActivity(intent);
    }

    public void onListTypeChange(int columnCount){
        layoutManager.setSpanCount(columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
    }
}
