package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import Fragments.DriverScreenFragment;
import Fragments.ViewBillPageFragment;
import Models.HiddenResponse;
import Models.OrdersList.GetOrderResponse;
import Utils.Constants;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.R;
import uk.co.eposoft.eposofttakeaway.ViewBillActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mobo on 8/12/2016.
 */

public class OrderRecycleAdapter extends RecyclerView.Adapter<OrderRecycleAdapter.CustomViewHolder> {

    private static String TAG = OrderRecycleAdapter.class.getSimpleName();
    Context context;
    LayoutInflater inflater;
    String id, key;
    private List<GetOrderResponse> body;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private SharedPreferences sharedPreferences;

    public OrderRecycleAdapter(Context context, List<GetOrderResponse> body) {
        this.context = context;
        this.body = body;
        inflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
    }

    @Override
    public OrderRecycleAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custome_orders_fragment_recycleview, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final OrderRecycleAdapter.CustomViewHolder holder, final int position) {
        String status = body.get(position).getTable().getStatus();
        Log.e(TAG, "status : " + status);


        holder.no.setText(body.get(position).getOrder().getId());
        holder.customer.setText(body.get(position).getUser().getFname());
        holder.amount.setText(body.get(position).getOrder().getTotal());

        if (body.get(position).getOrder().getStatus().equals("1")) {
            holder.status.setText(String.valueOf("Order in Progress"));
            holder.options.setText(String.valueOf("Done"));
            holder.linear_view_orders.setBackgroundColor(Color.parseColor("#ffffff"));
        } else if (body.get(position).getOrder().getStatus().equals("3")) {
            holder.options.setText(String.valueOf("Hide"));
            holder.status.setText(String.valueOf("Delivered"));
            holder.linear_view_orders.setBackgroundColor(Color.parseColor("#49CA72"));
        } else if (body.get(position).getOrder().getStatus().equals("2")) {
            holder.options.setText(String.valueOf("Hide"));
            holder.status.setText(String.valueOf("Rejected"));
            holder.linear_view_orders.setBackgroundColor(Color.parseColor("#5CA0EB"));
        } else if (body.get(position).getOrder().getStatus().equals("0")) {
            holder.options.setText(String.valueOf("New Order"));
            holder.status.setText(String.valueOf("New Order"));
            holder.linear_view_orders.setBackgroundColor(Color.parseColor("#740000"));
        }
        Log.e(TAG, position+ " onBindView: " + body.get(position).getTable().getTablename());


        if (body.get(position).getTable().getTablename() != null) {
            holder.options.setText(String.valueOf("Hide"));
            holder.status.setText(String.valueOf("Delivered"));
            holder.customer.setText(body.get(position).getTable().getTablename());
            holder.linear_view_orders.setBackgroundColor(Color.parseColor("#49CA72"));
        }


        holder.address.setText(body.get(position).getOrder().getOtype());

        if (body.get(position).getOrder().getOtype().equals("0")) {
            holder.address.setText(body.get(position).getUser().getAdd1());
            if (body.get(position).getOrder().getStatus().equals("3")) {
                holder.options.setText(String.valueOf("Hide"));
                holder.status.setText(String.valueOf("Delivered"));
                holder.linear_view_orders.setBackgroundColor(Color.parseColor("#49CA72"));
            } else {
                holder.options.setText(String.valueOf("Send"));
            }
        } else if (body.get(position).getOrder().getOtype().equals("2")) {
            holder.address.setText(String.valueOf("Inside"));
        } else if (body.get(position).getOrder().getOtype().equals("1")) {
            holder.address.setText(String.valueOf("Collection"));
        }

//        holder.address.setText(body.get(position).getOrder().getOtype());

        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragment = ViewBillPageFragment.newInstance(String.valueOf(body.get(holder.getAdapterPosition()).getOrder().getId()));
                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
            }
        });

        holder.linear_view_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewBillActivity.class);
                intent.putExtra("billid", body.get(holder.getAdapterPosition()).getOrder().getId());
                context.startActivity(intent);
            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.options.getText().toString().equals("Done")) {
                    changeStatus(body.get(holder.getAdapterPosition()).getOrder().getId(), "3");
                    holder.options.setText(String.valueOf("Hide"));
                    holder.linear_view_orders.setBackgroundColor(Color.parseColor("#49CA72"));
                } else if (holder.options.getText().toString().equals("Send")) {
                    fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragment = new DriverScreenFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("oid", body.get(holder.getAdapterPosition()).getOrder().getId());
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
//                    Toast.makeText(context, "Driver present", Toast.LENGTH_LONG).show();
                } else {
                    hideOrder(body.get(holder.getAdapterPosition()).getOrder().getId());
                    holder.linear_view_orders.setVisibility(View.GONE);
                    hideUpdate(holder.getAdapterPosition());
                }
            }
        });

    }

    public void hideUpdate(int pos) {
        body.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return body.size();
    }

    public void changeStatus(String oid, String status) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<HiddenResponse> responseCall = apiInterface.changeStatus(id, key, "orderstatus", oid, status);
            responseCall.enqueue(new Callback<HiddenResponse>() {
                @Override
                public void onResponse(Call<HiddenResponse> call, Response<HiddenResponse> response) {
                    Log.e(TAG, "onResponse: " + response.isSuccessful() + " " + response.body().getInfo());

                }

                @Override
                public void onFailure(Call<HiddenResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());

                }
            });

        } catch (Exception e) {
            Log.e(TAG, "hideOrder: " + e.getLocalizedMessage());
        }
    }

    public void hideOrder(String oid) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<HiddenResponse> responseCall = apiInterface.hideOrder(id, key, "hideorder", oid);
            responseCall.enqueue(new Callback<HiddenResponse>() {
                @Override
                public void onResponse(Call<HiddenResponse> call, Response<HiddenResponse> response) {
                    Log.e(TAG, "onResponse: " + response.isSuccessful() + " " + response.body().getInfo());

                }

                @Override
                public void onFailure(Call<HiddenResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());

                }
            });

        } catch (Exception e) {
            Log.e(TAG, "hideOrder: " + e.getLocalizedMessage());
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView no, customer, amount, address, status;
        ImageButton print;
        Button options;
        LinearLayout linear_view_orders;


        public CustomViewHolder(View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.no);
            customer = (TextView) itemView.findViewById(R.id.customer);
            amount = (TextView) itemView.findViewById(R.id.amount);
            address = (TextView) itemView.findViewById(R.id.address);
            status = (TextView) itemView.findViewById(R.id.status);
            options = (Button) itemView.findViewById(R.id.options);
            print = (ImageButton) itemView.findViewById(R.id.print);
            linear_view_orders = (LinearLayout) itemView.findViewById(R.id.linear_view_orders);
        }
    }
}
