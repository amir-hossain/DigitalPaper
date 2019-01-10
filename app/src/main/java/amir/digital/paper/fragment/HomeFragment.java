package amir.digital.paper.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import amir.digital.paper.DetailsActivity;
import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.R;
import amir.digital.paper.adapter.HomeAdapter;
import amir.digital.paper.model.NewsModel;
import amir.digital.paper.other.InternetConnection;
import amir.digital.paper.retrofit.Client;
import amir.digital.paper.retrofit.RetrofitInstance;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeAdapter.NewsClickListener,HomeAdapter.SaveClickListener,HomeAdapter.ShareClickListener {
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
                                            .getArticles(), HomeFragment.this,HomeFragment.this,HomeFragment.this));
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
        showAlertDialog(article.getUrl(),article.getTitle());
    }

    public void onListTypeChange(int columnCount){
        layoutManager.setSpanCount(columnCount);
        recyclerView_vertical.setLayoutManager(layoutManager);
    }

    private void showAlertDialog(final String url, final String title) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Load content")
                .setMessage("Do you want to load content in browser?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        runActivity(browserIntent);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getContext(),DetailsActivity.class);
                        intent.putExtra(StaticDataManager.url_key,url);
                        intent.putExtra(StaticDataManager.title_key,title);
                        runActivity(intent);
                    }
                })
                .setIcon(R.drawable.ic_about)
                .setCancelable(false)
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
