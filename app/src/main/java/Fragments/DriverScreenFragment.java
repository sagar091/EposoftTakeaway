package Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.DriverAdaper;
import Adapters.TableGridViewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.TableResponse;
import Models.allDataAtOne.Driver;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Admin768 on 11/7/2016.
 */

public class DriverScreenFragment extends Fragment {

    private static final String TAG = DriverScreenFragment.class.getSimpleName();
    GridView mGrid;
    CartDatabaseHelper databaseHelper;
    List<Driver> list;
    DriverAdaper adaper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        mGrid = (GridView) view.findViewById(R.id.table_grid);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HomeMainActivity.syncText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String strtext = "";
        if (getArguments() != null) {
            strtext = getArguments().getString("oid");
        }
        databaseHelper = new CartDatabaseHelper(getContext());
        getDriverDetails(strtext);

    }

    public void getDriverDetails(String strtext) {

        list = new ArrayList<>();
        try {
            Cursor cursor = databaseHelper.getDriver();
            if (cursor.moveToFirst()) {
                HomeMainActivity.syncText.setVisibility(View.GONE);
                do {
                    Driver tab = new Driver();
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String empid = cursor.getString(cursor.getColumnIndex("empid"));
                    String vtype = cursor.getString(cursor.getColumnIndex("vtype"));
                    String vno = cursor.getString(cursor.getColumnIndex("vno"));
                    String cno = cursor.getString(cursor.getColumnIndex("cno"));
                    String status = cursor.getString(cursor.getColumnIndex("status"));
                    Log.e(TAG, "getTableDetails: " + name);

                    tab.setName(name);
                    tab.setId(id);
                    tab.setStatus(status);
                    tab.setCno(cno);
                    tab.setEmpid(empid);
                    tab.setVtype(vtype);
                    tab.setVno(vno);
                    list.add(tab);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getDriverDetails: " + e.getLocalizedMessage());
        }

        try {
            adaper = new DriverAdaper(getActivity(), R.layout.gridview_custom_layout, list, strtext);
            mGrid.setAdapter(adaper);
        } catch (Exception e) {
            Log.e(TAG, "AdapterDetails: " + e.getLocalizedMessage());
        }

    }

    static class ViewHolder {
        TextView imageTitle;
    }

}
