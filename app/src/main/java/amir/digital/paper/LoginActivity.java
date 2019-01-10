package amir.digital.paper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.other.InternetConnection;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton fbButton;
    private GoogleSignInClient mGoogleSignInClient;
    private CheckBox rember;
    private boolean isChecked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rember=findViewById(R.id.remember);
        rember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoginActivity.this.isChecked=isChecked;
            }
        });



        googleLogin();

        facebookLogin();

    }

    private void facebookLogin() {
        fbButton =findViewById(R.id.fb_button);

        fbButton.setReadPermissions(Arrays.asList("email"));

        callbackManager = CallbackManager.Factory.create();

        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Facebook Login Success.Do what ever you want
                runActivity();

                String accessToken = loginResult.getAccessToken().getToken();
                Log.w("Facebook Access Token",accessToken);

            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Facebook Login Canceled",
                        Toast.LENGTH_LONG).show();//Cancel Toast
            }
            @Override
            public void onError(FacebookException exception) {


                Log.w("Facebook Login Failed",exception.toString());//Error message will be printed on console

                Toast.makeText(LoginActivity.this, exception.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void googleLogin() {
        SignInButton googleButton = findViewById(R.id.google_button);
        googleButton.setSize(SignInButton.SIZE_WIDE);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                if(InternetConnection.isNetworkConnected(getApplicationContext())){
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, StaticDataManager.google_request_code);
                }else {
                    InternetConnection.showError(getApplicationContext());
                }

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void runActivity() {
        if(isChecked){
            SharedPreferences preferences=getSharedPreferences(StaticDataManager.preference_name,MODE_PRIVATE);
            preferences.edit().putBoolean(StaticDataManager.logged_in_key,true).commit();
        }
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == StaticDataManager.google_request_code) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account){
        if(account != null){
            runActivity();
        }else{
            Toast.makeText(this,"You are not authenticated!",Toast.LENGTH_SHORT).show();
        }
    }

}

