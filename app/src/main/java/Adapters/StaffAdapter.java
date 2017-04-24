package Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Fragments.ViewBillPageFragment;
import Models.PlaceOrder.MakeOrder;
import Models.PlaceOrder.OrderResponse;
import Models.staff.StaffList;
import Utils.Constants;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;
import uk.co.eposoft.eposofttakeaway.StaffListActivity;

/**
 * Created by Aman Singh on 9/16/2016.
 */

public class StaffAdapter extends ArrayAdapter {

    private static final String TAG = HomeGridViewAdapter.class.getSimpleName();
    public String[] allColors;
    public StaffListActivity listActivity;
    public NestedScrollView contentHolder;
    public Fragment fragment;
    public FrameLayout cart_frame;
    public SharedPreferences sharedPreferences;
    private Context context;
    private int layoutResourceId;
    private String name;
    private List<StaffList> mGridData;
    private CartDatabaseHelper databaseHelper;
    private FragmentManager fragmentManager;
    private MakeOrder makeOrder;
    private SharedPreferences.Editor editor;


    public StaffAdapter(Context context, int layoutResourceId, List<StaffList> mGridData, MakeOrder makeOrder, FrameLayout cart_frame) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;
        this.makeOrder = makeOrder;
        this.cart_frame = cart_frame;
        listActivity = new StaffListActivity();
        databaseHelper = new CartDatabaseHelper(getContext());
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
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
            final StaffList item = mGridData.get(position);
            holder.imageTitle.setText(item.getName());

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = item.getName();
                    MaketheOrder(name);

                }
            });


            holder.layout.setBackgroundColor(Color.parseColor(allColors[position]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    private void MaketheOrder(String name) {
        try {
            databaseHelper = new CartDatabaseHelper(getContext());
            makeOrder.setStaff(name);
            Log.e(TAG, "MaketheOrder: ");
            final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading ", "Please wait...", false, false);
            Constants constants = new Constants();
            if (constants.isOnline(getContext())) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
                Call<OrderResponse> orderResponseCall = eposoftApiInterface.postOrder(makeOrder);
                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        loading.dismiss();
                        try {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    databaseHelper.deleteCart();
                                    showDialog("You placed order successfully!", response.body().getOrderId());
                                } else {
                                    Toast.makeText(getContext(), " Error: Unknown error occured", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), " Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getContext(), " Error: Please check your internet connection", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
            } else {
                loading.dismiss();
                Toast.makeText(getContext(), " You've place your order successfully!", Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                String json = gson.toJson(makeOrder);
                databaseHelper.setMyOrder(json);
                Log.e(TAG, "MaketheOrder: " + databaseHelper.countMyOrders());
                databaseHelper.deleteCart();
                Intent intent = new Intent(getContext(), HomeMainActivity.class);
                getContext().startActivity(intent);
                ((Activity) context).finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "MaketheOrder: exception" + e.getLocalizedMessage());
        }
    }

    private void showDialog(String msg, final Integer orderId) {
        AlertDialog alert = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme)
                //  .setTitle("Alert")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Print Bill", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cart_frame.setVisibility(View.VISIBLE);
//                        contentHolder.setVisibility(View.GONE);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragment = ViewBillPageFragment.newInstance(String.valueOf(orderId));
                        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                        ((Activity) context).finish();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), HomeMainActivity.class);
                        getContext().startActivity(intent);
                        ((Activity) context).finish();
//                        StaffListActivity.finish();
                    }
                })
                .create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
