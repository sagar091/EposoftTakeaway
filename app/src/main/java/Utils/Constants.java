package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mobo on 7/25/2016.
 */
public class Constants {
//    public static final String APP_ID = "d1d2d3d4";
//    public static final String APP_KEY = "d1d2d3d4";

    public static final String SHARE_APPID = "appid";
    public static final String SHARE_APPKEY = "appkey";
    public static final String SHARE_STATUS = "status";
    public static final String SHARE_MSG = "msg";

    public static String item_count[]= new String[100];

    public static Double total_price_count;
    public static Double sub_total_price_count;

    public static String SHARED_PREFERENCES = "share_prerferences";

    public static final String BASE_URL = "http://epos.eposapi.co.uk";

    public static String DISCOUNT_VALUES[] = {"item1", "item2", "item3", "item4", "item5", "gift_min", "gift_status",
            "del_status", "col_status", "waiting_status", "type_del", "type_coll", "type_wait", "cto",
            "cfrom", "dto", "dfrom", "wto", "wfrom", "gto", "gfrom", "delivery_discount",
            "collection_discount", "waiting_discount", "del_dis_min", "coll_dis_min", "wait_dis_min", "coupon_status"};

    @Override
    public String toString() {
        item_count.toString();
        return super.toString();
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
