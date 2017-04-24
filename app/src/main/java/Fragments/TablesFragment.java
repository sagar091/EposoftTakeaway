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

import java.util.ArrayList;
import java.util.List;

import Adapters.TableGridViewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.TableResponse;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 8/5/2016.
 */
public class TablesFragment extends Fragment {

    private static final String TAG = TablesFragment.class.getSimpleName();
    GridView tableGrid;
    TableGridViewAdapter tableAdapter;
    CartDatabaseHelper databaseHelper;
    List<TableResponse> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        tableGrid = (GridView) view.findViewById(R.id.table_grid);

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
        getTableDetails();
    }

    public void getTableDetails() {

        list = new ArrayList<>();
        try {
            Cursor cursor = databaseHelper.getTableItems();
            if (cursor.moveToFirst()) {
                HomeMainActivity.syncText.setVisibility(View.GONE);
                do {
                    TableResponse tab = new TableResponse();
                    String name = cursor.getString(cursor.getColumnIndex("tablename"));
                    Log.e(TAG, "getTableDetails: " + name);
                    tab.setTablename(name);

                    list.add(tab);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
        }

        try {
            tableAdapter = new TableGridViewAdapter(getActivity(), R.layout.gridview_custom_layout, list);
            tableGrid.setAdapter(tableAdapter);
        } catch (Exception e) {
        }

    }


}

