package uk.co.eposoft.eposofttakeaway;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import Adapters.StaffAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.PlaceOrder.MakeOrder;
import Models.staff.StaffList;

public class StaffListActivity extends AppCompatActivity {

    private static final String TAG = StaffListActivity.class.getSimpleName();
    List<StaffList> list;
    StaffAdapter staffAdapter;
    GridView tableGrid;
    MakeOrder makeOrder;
    FrameLayout cart_frame;
    private CartDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        tableGrid = (GridView) findViewById(R.id.staff_grid);

        cart_frame = (FrameLayout) findViewById(R.id.main_frame);
        cart_frame.setVisibility(View.GONE);

        databaseHelper = new CartDatabaseHelper(StaffListActivity.this);
        HomeMainActivity.syncText.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        makeOrder = intent.getParcelableExtra("makeorder");
        getStaffDetails();
    }

    public void getStaffDetails() {
        list = new ArrayList<>();
        try {
            Cursor cursor = databaseHelper.getStaffItems();
            if (cursor.moveToFirst()) {
                HomeMainActivity.syncText.setVisibility(View.GONE);
                do {
                    StaffList tab = new StaffList();
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    Log.e(TAG, "getTableDetails: " + name);
                    tab.setId(id);
                    tab.setName(name);
                    list.add(tab);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            Log.e(TAG, "getStaffDetails: " + e.getLocalizedMessage());
        }

        try {
            staffAdapter = new StaffAdapter(StaffListActivity.this, R.layout.gridview_custom_layout, list, makeOrder, cart_frame);
            tableGrid.setAdapter(staffAdapter);
        } catch (Exception e) {
            Log.e(TAG, "getStaffDetails:excep " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StaffListActivity.this, CartActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
