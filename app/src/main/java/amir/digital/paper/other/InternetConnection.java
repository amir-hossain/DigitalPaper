package amir.digital.paper.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class InternetConnection {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static void showError(Context context){
        Toast.makeText(context,"Not connected to internet",Toast.LENGTH_SHORT).show();
    }
}
