package Utils;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import DatabaseHelper.CartDatabaseHelper;
import Models.PlaceOrder.MakeOrder;
import Models.PlaceOrder.OrderResponse;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.NoNetworkActivity;

/**
 * Created by Aman Singh on 10/13/2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = MyBroadcastReceiver.class.getSimpleName();
    Context context;
    CartDatabaseHelper database;
    public AlertDialog alertDialog;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;


        database = new CartDatabaseHelper(context);

        if (isOnline(context)) {
            try {
                if (alertDialog != null && alertDialog.isShowing())
                    alertDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Toast.makeText(context, "Network Connected!!!", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            MakeOrder makeOrder = new MakeOrder();
            Log.e(TAG, "onReceive: " + database.countMyOrders());
            if (database.countMyOrders() > 0) {
                Cursor cursor = database.getMyOrder();
                if (cursor.moveToFirst()) {
                    do {
                        String json = cursor.getString(cursor.getColumnIndex("myorder"));
                        makeOrder = gson.fromJson(json, MakeOrder.class);
                        MaketheOrder(makeOrder);
                        database.deleteMyOrder(json);
                    } while (cursor.moveToNext());
                }
            }
        } else {
//            intent = new Intent(context, NoNetworkActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No internet connection");
            builder.setMessage("Please connect to internet for better performance.");
            builder.setCancelable(true);
            alertDialog = builder.create();
            alertDialog.show();
            Toast.makeText(context, "Please Connect With Internet!! ", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


    public void MaketheOrder(final MakeOrder name) {
        try {
            final CartDatabaseHelper databaseHelper = new CartDatabaseHelper(context);
            Log.e(TAG, "MaketheOrder: ");
            final ProgressDialog loading = ProgressDialog.show(context, "Loading ", "Please wait...", false, false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<OrderResponse> orderResponseCall = eposoftApiInterface.postOrder(name);
            orderResponseCall.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse>s,Response<OrderResponse> response) {
                    loading.dismiss();
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                Gson gson = new Gson();
                                String json = gson.toJson(name);
                                databaseHelper.deleteMyOrder(json);
                                Toast.makeText(context, " Your Order ID : " + response.body().getOrderId(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, " Error: Unknown error occured", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, " Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse>s,Throwable t) {
                    loading.dismiss();
                    Toast.makeText(context, " Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "MaketheOrder: exception" + e.getLocalizedMessage());
        }

    }

}

