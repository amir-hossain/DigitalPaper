package amir.digital.paper.retrofit;

import amir.digital.paper.model.NewsModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("everything")
    Call<NewsModel> getNewsBySource(@Query("sources") String sourceName, @Query("apiKey") String apiKey, @Query("page") long page);

}