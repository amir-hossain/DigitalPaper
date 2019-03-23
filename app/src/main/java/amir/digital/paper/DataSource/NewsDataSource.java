package amir.digital.paper.DataSource;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.modelAndSchema.NewsModelAndSchema;
import amir.digital.paper.retrofit.RetrofitInstance;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Long, NewsModelAndSchema.Article> {
    private String newsSource;

    public NewsDataSource(String newsSource) {
        this.newsSource = newsSource;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, NewsModelAndSchema.Article> callback) {
        RetrofitInstance.getAPI().getNewsBySource(newsSource, StaticDataManager.news_api_key,1l)
                .enqueue(new Callback<NewsModelAndSchema>() {
                    @Override
                    public void onResponse(Call<NewsModelAndSchema> call, Response<NewsModelAndSchema> response) {
                        callback.onResult(response.body().getArticles(),0,response.body().getTotal(),null,2l);
                    }

                    @Override
                    public void onFailure(Call<NewsModelAndSchema> call, Throwable t) {

                }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, NewsModelAndSchema.Article> callback) {
        RetrofitInstance.getAPI().getNewsBySource(newsSource, StaticDataManager.news_api_key,params.key)
                .enqueue(new Callback<NewsModelAndSchema>() {
                    @Override
                    public void onResponse(Call<NewsModelAndSchema> call, Response<NewsModelAndSchema> response) {
                        callback.onResult(response.body().getArticles(),params.key-1);
                    }

                    @Override
                    public void onFailure(Call<NewsModelAndSchema> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, NewsModelAndSchema.Article> callback) {
        RetrofitInstance.getAPI().getNewsBySource(newsSource, StaticDataManager.news_api_key,params.key)
                .enqueue(new Callback<NewsModelAndSchema>() {
                    @Override
                    public void onResponse(Call<NewsModelAndSchema> call, Response<NewsModelAndSchema> response) {
                        callback.onResult(response.body().getArticles(),params.key+1);
                    }

                    @Override
                    public void onFailure(Call<NewsModelAndSchema> call, Throwable t) {

                    }
                });
    }
}
