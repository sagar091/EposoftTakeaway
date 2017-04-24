package uk.co.eposoft.eposofttakeaway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Adapters.CartAdapter;
import Adapters.NewCartAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.NewCartData;
import Models.SandageResponse;
import Utils.Constants;
import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = CartActivity.class.getSimpleName();
    public static TextView totalPriceTextView, subTotalPriceTextView;
    public static Double items_total;
    public List<String> data, subdata, addonlist, price, subdataprice;
    RecyclerView recyclerView;
    CartDatabaseHelper databaseHelper;
    Cursor cursor;
    Button checkOutButton, checkCancleButton;
    CheckBox allergyButton;
    TextView bagPriceTextView, cartEmptyTxt;
    SandageResponse sandageResponse;
    Double total;
    EditText infoEditText;
    Toolbar toolbar;
    NestedScrollView contentHolder;
    SharedPreferences sharedPreferences;
    Constants constants;

    List<NewCartData> newCartDatas;
    NewCartAdapter newCartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Order");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, HomeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        constants = new Constants();

        databaseHelper = new CartDatabaseHelper(CartActivity.this);
        totalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);
        subTotalPriceTextView = (TextView) findViewById(R.id.subTotalPriceTextView);
        bagPriceTextView = (TextView) findViewById(R.id.bagPriceTextView);

        contentHolder = (NestedScrollView) findViewById(R.id.contentHolder);
        cartEmptyTxt = (TextView) findViewById(R.id.cartEmptyTxt);
        if (databaseHelper.getTotalCount() > 0) {
            contentHolder.setVisibility(View.VISIBLE);
            cartEmptyTxt.setVisibility(View.GONE);
        } else {
            contentHolder.setVisibility(View.GONE);
            cartEmptyTxt.setVisibility(View.VISIBLE);
        }

        sandageResponse = new SandageResponse();
        sandageResponse.setBag("");
        sandageResponse.setBank("");
        checkOutButton = (Button) findViewById(R.id.checkOutButton);
        checkCancleButton = (Button) findViewById(R.id.checkCancleButton);
        allergyButton = (CheckBox) findViewById(R.id.allergyButton);

        infoEditText = (EditText) findViewById(R.id.infoEditText);

        allergyButton.setChecked(false);
        checkOutButton.setEnabled(true);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("bagprice", "").equals("")) {
            Log.e(TAG, "shared: " + sharedPreferences.getString("bagprice", "0") + "   " + CartAdapter.sub_total);
            sandageResponse.setBag(String.valueOf(sharedPreferences.getString("bagprice", "0")));
            sandageResponse.setBank(String.valueOf(sharedPreferences.getString("bankprice", "0")));
            bagPriceTextView.setText(sandageResponse.getBag());

//            Log.e(TAG, "run: " + temp + " " + total);
//            totalPriceTextView.setText(String.format("£ %s", total));
        }

        Intent intent1 = getIntent();
        if (intent1.getFlags()==1){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tablename",intent1.getStringExtra("table"));
            editor.apply();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tablename","");
            editor.apply();
        }


        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.e(TAG, "onClick: " + Constants.item_count[0]+" "+Constants.item_count[1]);
                Double temp = Double.valueOf(sharedPreferences.getString("bagprice", "0"));
//                Log.e(TAG, "price tot: " + databaseHelper.getTotalPrice());

                String subtot = new DecimalFormat("###.##").format(databaseHelper.getTotalPrice());
                Log.e(TAG, "price sub: " + subtot);
                total = Double.valueOf(new DecimalFormat("###.###").format(Double.valueOf(subtot) + temp));

                if (allergyButton.isChecked()) {
                    Intent intent = new Intent(CartActivity.this, ChoosePaymentTypeActivity.class);
                    intent.putExtra("total", total);
                    intent.putExtra("bagcharge", sandageResponse.getBag());
                    intent.putExtra("bankcharge", sandageResponse.getBank());
                    intent.putExtra("subtotal", Double.valueOf(new DecimalFormat("###.###").format(Double.valueOf(subtot))));
                    intent.putExtra("note", infoEditText.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please check for all allergies", Toast.LENGTH_LONG).show();
                }
            }
        });

        data = new ArrayList<>();
        price = new ArrayList<>();
        subdata = new ArrayList<>();
        addonlist = new ArrayList<>();
        subdataprice = new ArrayList<>();

        getDataFromDB();
        setUpRecycleView();


        String x = sandageResponse + "";

        if (!x.equals("null"))
            bagPriceTextView.setText(sandageResponse.getBag());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHelper.getTotalCount() > 0) {
            contentHolder.setVisibility(View.VISIBLE);
            cartEmptyTxt.setVisibility(View.GONE);
        } else {
            contentHolder.setVisibility(View.GONE);
            cartEmptyTxt.setVisibility(View.VISIBLE);
        }

    }


    public void setUpRecycleView() {
//        databaseHelper.deletePrice();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.VISIBLE);
        getNewCartData();
        newCartAdapter = new NewCartAdapter(CartActivity.this, newCartDatas);
        recyclerView.setAdapter(newCartAdapter);

//        cartAdapter = new CartAdapter(CartActivity.this, data, price, subdata, subdataprice, addonlist);
//        recyclerView.setAdapter(cartAdapter);

        SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
                recyclerView,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {

                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        // ToDo - what you want when dismiss


                        int id = recyclerView.getChildAdapterPosition(view);
//                        databaseHelper.deleteSingleItem(cartAdapter.cartItemName.get(id));
                        Log.e(TAG, "onDismiss: " + newCartAdapter.cartDatas.get(id).getItemname());
                        databaseHelper.deleteCartItems(newCartAdapter.cartDatas.get(id).getItemid());
                        synchronized (recyclerView) {
                            getNewCartData();
                            newCartAdapter = new NewCartAdapter(CartActivity.this, newCartDatas);
                            recyclerView.setAdapter(newCartAdapter);
//                            databaseHelper.deletePrice();
//                            cartAdapter = new CartAdapter(CartActivity.this, data, price, subdata, subdataprice, addonlist);
//                            recyclerView.setAdapter(cartAdapter);
                        }


                        if (databaseHelper.getTotalCount() > 0) {
                            contentHolder.setVisibility(View.VISIBLE);
                            cartEmptyTxt.setVisibility(View.GONE);
                        } else {
                            contentHolder.setVisibility(View.GONE);
                            cartEmptyTxt.setVisibility(View.VISIBLE);
                        }


//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);

//recreate();
                    }
                })
                .setIsVertical(false)
                .setItemClickCallback(new SwipeDismissRecyclerViewTouchListener.OnItemClickCallBack() {
                    @Override
                    public void onClick(int i) {
                        // Toast.makeText(getBaseContext(), String.format("Delete item"), Toast.LENGTH_LONG).show();

                    }
                })
                .create();
        recyclerView.setOnTouchListener(listener);

    }

    public Double getTotal() {
        Double total1 = 0.0;
        for (int x = 0; x < price.size(); x++) {
            total1 = total1 + Double.parseDouble(price.get(x));
//            Log.e(TAG, " prices : " + price.get(x));
        }
        total1 = Double.valueOf(new DecimalFormat("###.##").format(total1));
        return total1;
    }

    public void getDataFromDB() {
        data = new ArrayList<>();
        price = new ArrayList<>();
        subdata = new ArrayList<>();
        addonlist = new ArrayList<>();
        subdataprice = new ArrayList<>();

        cursor = databaseHelper.getCartData();

        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(cursor.getColumnIndex("headname")));
                price.add(cursor.getString(cursor.getColumnIndex("headprice")));
                subdataprice.add(cursor.getString(cursor.getColumnIndex("price")));
                subdata.add(cursor.getString(cursor.getColumnIndex("itemName")));
                addonlist.add(cursor.getString(cursor.getColumnIndex("addontype")));
            } while (cursor.moveToNext());
        }

        items_total = getTotal();
//        subTotalPriceTextView.setText(String.valueOf(items_total));

//        Double temp = Double.valueOf(sharedPreferences.getString("bagprice", "0"));
//        total = Double.valueOf(new DecimalFormat("###.###").format(items_total + temp));
//        Log.e(TAG, "run: " + temp + " " + total);
//        totalPriceTextView.setText(String.format("£ %s", total));


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CartActivity.this, HomeMainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void getNewCartData() {
        newCartDatas = new ArrayList<>();
        Cursor cursor = databaseHelper.getCartItems();
        if (cursor.moveToFirst()) {
            do {
                NewCartData cartData = new NewCartData();
                String name = cursor.getString(cursor.getColumnIndex("itemname"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String id = cursor.getString(cursor.getColumnIndex("itemid"));
                String qty = cursor.getString(cursor.getColumnIndex("qty"));
                cartData.setItemid(id);
                cartData.setItemname(name);
                cartData.setPrice(price);
                cartData.setQty(qty);
                newCartDatas.add(cartData);
            } while (cursor.moveToNext());
        }
    }

    public interface notifyRecycler {
        void updateRecycler(int id);
    }

}
