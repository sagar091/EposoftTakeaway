package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Fragments.DriverScreenFragment;
import Fragments.OrdersFragment;
import Models.DriverSndResponse;
import Models.HiddenResponse;
import Models.TableResponse;
import Models.allDataAtOne.Driver;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Admin768 on 11/7/2016.
 */

public class DriverAdaper extends ArrayAdapter {

    private Context context;
    public String[] allColors;
    private int layoutResourceId;
    private List<Driver> mGridData;
    private String TAG = DriverAdaper.class.getSimpleName();
    private String oid;
    private MyProgressBar progressBar;

    public DriverAdaper(Context context, int layoutResourceId, List<Driver> mGridData, String strtext) {
        super(context, layoutResourceId, mGridData);
        this.context = context;
        this.mGridData = mGridData;
        this.layoutResourceId = layoutResourceId;
        oid = strtext;
        progressBar = new MyProgressBar(context);

    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        TableGridViewAdapter.ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new TableGridViewAdapter.ViewHolder();
            holder.imageTitle = (TextView) view.findViewById(R.id.gridview_text);
            holder.layout = (LinearLayout) view.findViewById(R.id.linearLayout);
            view.setTag(holder);
        } else {
            holder = (TableGridViewAdapter.ViewHolder) view.getTag();
        }
        try {
            allColors = context.getResources().getStringArray(R.array.rainbow);
            final Driver item = mGridData.get(position);

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setText(item.getName());
            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendDriver(oid, item.getId());
                    Toast.makeText(context, item.getName() + " clicked", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void sendDriver(final String oid, String did) {
        try {
            progressBar.showDialog();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            String appid = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String appkey = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<DriverSndResponse> responseCall = apiInterface.sendDriver(appid, appkey, "senddriver", oid, did);
            responseCall.enqueue(new Callback<DriverSndResponse>() {
                @Override
                public void onResponse(Call<DriverSndResponse> call, Response<DriverSndResponse> response) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onResponse: " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        changeStatus(oid, "3");
                    }
                }

                @Override
                public void onFailure(Call<DriverSndResponse> call, Throwable t) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "sendDriver: " + e.getLocalizedMessage());
        }
    }

    private void changeStatus(String oid, String status) {
        try {
            progressBar.showDialog();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            String appid = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String appkey = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<HiddenResponse> responseCall = apiInterface.changeStatus(appid, appkey, "orderstatus", oid, status);
            responseCall.enqueue(new Callback<HiddenResponse>() {
                @Override
                public void onResponse(Call<HiddenResponse> call, Response<HiddenResponse> response) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onResponse: " + response.isSuccessful());

                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    Fragment fragment = new OrdersFragment();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                }

                @Override
                public void onFailure(Call<HiddenResponse> call, Throwable t) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "hideOrder: " + e.getLocalizedMessage());
        }
    }

}
