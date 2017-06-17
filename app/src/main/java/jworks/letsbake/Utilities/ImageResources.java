package jworks.letsbake.Utilities;

import android.net.Uri;

/**
 * Created by CHUKWU JOHNPAUL on 15/06/17.
 */

public class ImageResources {
    public static final String BASE_PATH = "android.resource://jworks.letsbake/drawable/";
    public static final String IMAGE1   = "nutpie";
    public static final String IMAGE2 = "brownee";
    public static final String IMAGE3   = "yellow";
    public static final String IMAGE4   = "cheese";

    public static String[] foodPic = {
            BASE_PATH+  IMAGE1,BASE_PATH+  IMAGE2,BASE_PATH+  IMAGE3,BASE_PATH+  IMAGE4
    };

    public static Uri convertUri(String url){
        Uri uri = Uri.parse(url);
        return uri;
    }
}
