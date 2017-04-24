package Fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import Adapters.OrderRecycleAdapter;
import Adapters.TableGridViewAdapter;
import Adapters.TableOrderGridViewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.OrdersList.GetOrderResponse;
import Models.TableResponse;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin768 on 11/11/2016.
 */

public class TableOrderFragment extends Fragment {

    private static final String TAG = TablesFragment.class.getSimpleName();
    GridView tableGrid;
    TableOrderGridViewAdapter tableAdapter;
    CartDatabaseHelper databaseHelper;
    List<TableResponse> list;
    Button viewTable;
    MyProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tables_orders, container, false);
        tableGrid = (GridView) view.findViewById(R.id.table_grid);
        viewTable = (Button) view.findViewById(R.id.viewTable);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeMainActivity.syncText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new CartDatabaseHelper(getContext());
        progressBar = new MyProgressBar(getContext());



        SharedPreferences  sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
        getOrders(id,key);

        viewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new OrdersFragment();
                fragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit();
            }
        });
    }

    public void getTableDetails(List<GetOrderResponse> listResponse) {

        list = new ArrayList<>();
        try {
            Cursor cursor = databaseHelper.getTableItems();
            if (cursor.moveToFirst()) {
                HomeMainActivity.syncText.setVisibility(View.GONE);
                do {
                    TableResponse tab = new TableResponse();
                    String name = cursor.getString(cursor.getColumnIndex("tablename"));
                    Log.e(TAG, "getTableOrdr : " + name);
                    tab.setTablename(name);

                    list.add(tab);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
        }

        try {
            tableAdapter = new TableOrderGridViewAdapter(getActivity(), R.layout.gridview_custom_layout, list, listResponse);
            tableGrid.setAdapter(tableAdapter);
        } catch (Exception e) {
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
                    List<GetOrderResponse> listResponse = new ArrayList<>();

                    progressBar.hideDialog();
                    try {

                        for (GetOrderResponse orderResponse : response.body()) {
                            if (orderResponse.getTable().getStatus().equals("0")) {
                                listResponse.add(orderResponse);
                            }
                        }
                        getTableDetails(listResponse);
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
