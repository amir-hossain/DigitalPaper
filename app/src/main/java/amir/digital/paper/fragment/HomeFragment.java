package amir.digital.paper.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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

public class HomeFragment extends Fragment implements View.OnClickListener, HomeAdapter.NewsClickListener {
    private static Retrofit retrofit = RetrofitInstance.getInstance();
    private static Client client = retrofit.create(Client.class);
    private RecyclerView recyclerView_vertical;
    private CardView abcNews, abcNewsAu, alJazeeraEnglish, arsTechnica, associatedPress, australianFinancialReview, axios, bbcNews, bbcSport, bleacherReport;
    private View previousSelectedView;
    private String currentNewsSource = StaticDataManager.abc_news;
    private SwipeRefreshLayout swipe_refresh_layout;
    private GridLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        initializeEventListener();
        int columnCount=getArguments().getInt(StaticDataManager.column_count_key);
        layoutManager=new GridLayoutManager(getContext(), columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());



        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                showNews(client, currentNewsSource);

            }
        });
        showNews(client, StaticDataManager.abc_news);
        highlightSelected(abcNews);


        return view;
    }


    private void highlightSelected(View view) {
        if (previousSelectedView != null) {
            previousSelectedView.setBackgroundColor(Color.WHITE);
        }
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        previousSelectedView = view;
    }

    private void initializeEventListener() {
        abcNews.setOnClickListener(this);
        abcNewsAu.setOnClickListener(this);
        alJazeeraEnglish.setOnClickListener(this);
        arsTechnica.setOnClickListener(this);
        associatedPress.setOnClickListener(this);
        australianFinancialReview.setOnClickListener(this);
        axios.setOnClickListener(this);
        bbcNews.setOnClickListener(this);
        bbcSport.setOnClickListener(this);
        bleacherReport.setOnClickListener(this);

    }

    private void initializeView(View view) {
        recyclerView_vertical = view.findViewById(R.id.all_news_vertical_recyclerView);
        abcNews = view.findViewById(R.id.abc_news);
        abcNewsAu = view.findViewById(R.id.abc_news_au);
        alJazeeraEnglish = view.findViewById(R.id.al_jazeera_english);
        arsTechnica = view.findViewById(R.id.ars_technica);
        associatedPress = view.findViewById(R.id.associated_press);
        australianFinancialReview = view.findViewById(R.id.australian_financial_review);
        axios = view.findViewById(R.id.axios);
        bbcNews = view.findViewById(R.id.bbc_news);
        bbcSport = view.findViewById(R.id.bbc_sport);
        bleacherReport = view.findViewById(R.id.bleacher_report);
        swipe_refresh_layout = view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
    }

    private void showNews(Client client, String newsSourceName) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.abc_news:
                showNews(client, StaticDataManager.abc_news);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.abc_news;
                break;
            case R.id.abc_news_au:
                showNews(client, StaticDataManager.abc_news_au);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.abc_news_au;
                break;
            case R.id.al_jazeera_english:
                showNews(client, StaticDataManager.al_jazeera_english);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.al_jazeera_english;
                break;
            case R.id.ars_technica:
                showNews(client, StaticDataManager.ars_technica);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.ars_technica;
                break;
            case R.id.australian_financial_review:
                showNews(client, StaticDataManager.australian_financial_review);
                highlightSelected(australianFinancialReview);
                this.currentNewsSource = StaticDataManager.australian_financial_review;
                break;
            case R.id.axios:
                showNews(client, StaticDataManager.axios);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.axios;
                break;
            case R.id.bbc_news:
                showNews(client, StaticDataManager.bbc_news);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.bbc_news;
                break;
            case R.id.bbc_sport:
                showNews(client, StaticDataManager.bbc_sport);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.bbc_sport;
                break;
            case R.id.bleacher_report:
                showNews(client, StaticDataManager.bleacher_report);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.bleacher_report;
                break;
            case R.id.associated_press:
                showNews(client, StaticDataManager.associated_press);
                highlightSelected(v);
                this.currentNewsSource = StaticDataManager.associated_press;
                break;
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
