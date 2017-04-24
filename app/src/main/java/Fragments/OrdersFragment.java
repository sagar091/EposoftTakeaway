package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Adapters.OrderRecycleAdapter;
import Models.OrdersList.GetOrderResponse;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mobo on 8/5/2016.
 */
public class OrdersFragment extends Fragment {

    private static final String TAG = OrdersFragment.class.getSimpleName();
    OrderRecycleAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    MyProgressBar progressBar;
    Button viewTable;
    SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.orders_recycleview);
        viewTable = (Button) view.findViewById(R.id.viewTable);
        swipeContainer  = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        progressBar = new MyProgressBar(getContext());

        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
        getOrders(id, key);

        viewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new TableOrderFragment();
                fragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
                String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
                getRefreshed(id, key);
            }
        });
    }

    public void getRefreshed(String id,String key){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<GetOrderResponse>> responseCall = eposoftApiInterface.getOrderList(id, key, "getorderlist");

            responseCall.enqueue(new Callback<List<GetOrderResponse>>() {
                @Override
                public void onResponse(Call<List<GetOrderResponse>> call, Response<List<GetOrderResponse>> response) {
                    Log.e(TAG, "onResponse: " + response.body().size());
                    List<GetOrderResponse> listResponse = new ArrayList<GetOrderResponse>();
                    swipeContainer.setRefreshing(false);
                    try {

                        for (GetOrderResponse orderResponse : response.body()) {
                            if (!orderResponse.getTable().getStatus().equals("0")) {
                                listResponse.add(orderResponse);
                            }
                        }
                        adapter = new OrderRecycleAdapter(getContext(), listResponse);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        swipeContainer.setRefreshing(false);
                        Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<GetOrderResponse>> call, Throwable t) {
                    swipeContainer.setRefreshing(false);
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            swipeContainer.setRefreshing(false);
            Log.e(TAG, "getOrders: " + e.getLocalizedMessage());
        }
    }

    public void getOrders(String id, String key) {
        try {
            progressBar.showDialog();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<GetOrderResponse>> responseCall = eposoftApiInterface.getOrderList(id, key, "getorderlist");

            responseCall.enqueue(new Callback<List<GetOrderResponse>>() {
                @Override
                public void onResponse(Call<List<GetOrderResponse>> call, Response<List<GetOrderResponse>> response) {
                    Log.e(TAG, "onResponse: " + response.body().size());
                    List<GetOrderResponse> listResponse = new ArrayList<GetOrderResponse>();

                    progressBar.hideDialog();
                    swipeContainer.setRefreshing(false);
                    try {

                        for (GetOrderResponse orderResponse : response.body()) {
                            if (!orderResponse.getTable().getStatus().equals("0")) {
                                listResponse.add(orderResponse);
                            }
                        }
                        adapter = new OrderRecycleAdapter(getContext(), listResponse);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<GetOrderResponse>> call, Throwable t) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getOrders: " + e.getLocalizedMessage());
        }
    }
}

