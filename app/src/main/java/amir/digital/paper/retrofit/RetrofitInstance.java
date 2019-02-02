package amir.digital.paper.retrofit;

import amir.digital.paper.Mnanger.StaticDataManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static  Retrofit retrofit=new Retrofit.Builder().baseUrl(StaticDataManager.news_api_base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static API api = retrofit.create(API.class);



    public static API getAPI(){
        return api;
    }

}
