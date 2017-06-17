package jworks.letsbake.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by CHUKWU JOHNPAUL on 17/06/17.
 */

public class NetworkUtilities {

    //check for wifi Connection
    public static boolean CheckForWifiNetwork(Context ctx){
        ConnectivityManager manager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }
    //check for Mobile Data Connection
    public static boolean CheckForMobileNetwork(Context ctx){
        ConnectivityManager manager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return mobile;

    }
}
