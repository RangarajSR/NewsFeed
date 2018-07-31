package feed.news.com.newsfeed.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {

    public static boolean isInternetConnectionAvailable(Context mContext) {
        if (null == mContext) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = null;
        if (null != connectivityManager) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (null != netInfo && netInfo.isAvailable() && netInfo.isConnected());
    }
}
