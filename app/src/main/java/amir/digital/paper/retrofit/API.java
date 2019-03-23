package amir.digital.paper.retrofit;

import amir.digital.paper.modelAndSchema.NewsModelAndSchema;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("everything")
    Call<NewsModelAndSchema> getNewsBySource(@Query("sources") String sourceName, @Query("apiKey") String apiKey, @Query("page") long page);

}
