package amir.digital.paper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import amir.digital.paper.Mnanger.StaticDataManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences preferences=getSharedPreferences(StaticDataManager.preference_name,MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//                    if(preferences.contains(StaticDataManager.logged_in_key)){
//                        runActivity(MainActivity.class);
//                    }else {
//                        runActivity(LoginActivity.class);
//                    }
                    runActivity(MainActivity.class);
                    finish();
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }

    private void runActivity(Class<?> targetActivity) {
        startActivity(new Intent(SplashScreen.this,targetActivity));
    }
}
