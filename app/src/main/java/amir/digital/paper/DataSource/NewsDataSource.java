package amir.digital.paper.DataSource;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.model.NewsModel;
import amir.digital.paper.retrofit.RetrofitInstance;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Long,NewsModel.Article> {
    private String newsSource;

    public NewsDataSource(String newsSource) {
        this.newsSource = newsSource;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, NewsModel.Article> callback) {
        RetrofitInstance.getAPI().getNewsBySource(newsSource, StaticDataManager.news_api_key,1l)
                .enqueue(new Callback<NewsModel>() {
                    @Override
                    public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                        callback.onResult(response.body().getArticles(),null,2l);
                    }

                    @Override
                    public void onFailure(Call<NewsModel> call, Throwable t) {

                }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, NewsModel.Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, NewsModel.Article> callback) {
        RetrofitInstance.getAPI().getNewsBySource(newsSource, StaticDataManager.news_api_key,params.key)
                .enqueue(new Callback<NewsModel>() {
                    @Override
                    public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                        callback.onResult(response.body().getArticles(),params.key+1);
                    }

                    @Override
                    public void onFailure(Call<NewsModel> call, Throwable t) {

                    }
                });
    }
}
