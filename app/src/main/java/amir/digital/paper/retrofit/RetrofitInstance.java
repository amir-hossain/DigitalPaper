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
    private static Retrofit newsApiRetrofit = null;
    private static Retrofit numberApiRetrofit = null;



    public static Retrofit getInstance(){
        if (newsApiRetrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            newsApiRetrofit = new Retrofit.Builder().baseUrl(StaticDataManager.news_api_base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient().build())
                    .build();

        }
        return newsApiRetrofit;
    }

    public static Retrofit getInstance(String baseUrl){
        if (numberApiRetrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            numberApiRetrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient().build())
                    .build();

        }
        return numberApiRetrofit;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager()  {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType){
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType){
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
