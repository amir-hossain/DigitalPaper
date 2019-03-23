package amir.digital.paper.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import amir.digital.paper.DetailsActivity;
import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.R;
import amir.digital.paper.adapter.NewsAdapter;
import amir.digital.paper.database.NewsDao;
import amir.digital.paper.database.NewsDatabase;
import amir.digital.paper.factory.NewsDataSourceFactory;
import amir.digital.paper.modelAndSchema.NewsModelAndSchema;
import amir.digital.paper.other.InternetConnection;
import amir.digital.paper.schema.Article;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements NewsAdapter.NewsClickListener,NewsAdapter.SaveClickListener,NewsAdapter.ShareClickListener {
    private RecyclerView recyclerView_vertical;
    private String currentNewsSource = StaticDataManager.google_news;
    private SwipeRefreshLayout swipe_refresh_layout;
    private GridLayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private NewsDao newsDao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsDao= NewsDatabase.getNewsDao(getContext());
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
        return view;
    }



    private void initializeView(View view) {
        recyclerView_vertical = view.findViewById(R.id.all_news_vertical_recyclerView);
        swipe_refresh_layout = view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
    }

    public void showNews(String newsSourceName) {
        swipe_refresh_layout.setRefreshing(true);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(3)
                .build();
        LivePagedListBuilder<Long, NewsModelAndSchema.Article> builder=new LivePagedListBuilder(new NewsDataSourceFactory(newsSourceName),config);
        builder.build().observe(this, new Observer<PagedList<NewsModelAndSchema.Article>>() {
            @Override
            public void onChanged(PagedList<NewsModelAndSchema.Article> articles) {

                newsAdapter.submitList(articles);
                swipe_refresh_layout.setRefreshing(false);
            }
        });
    }




    @Override
    public void onNewsClick(NewsModelAndSchema.Article article) {
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
    public void onSaveClick(Article article) {

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                NewsDatabase.getNewsDao(getContext()).insertNews(article);
                emitter.onNext("saved");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onShareClick(NewsModelAndSchema.Article article) {
        Observable.create(new ObservableOnSubscribe<List<Article>>() {

            @Override
            public void subscribe(ObservableEmitter<List<Article>> emitter) throws Exception {
                emitter.onNext(NewsDatabase.getNewsDao(getContext()).getAllNews());
            }
        }).subscribeOn(Schedulers.newThread() )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Article>>() {
            @Override
            public void accept(List<Article> articles) throws Exception {
                Toast.makeText(getContext(),articles.get(0).getArticle(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
