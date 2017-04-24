package uk.co.eposoft.eposofttakeaway;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.ArrayList;
import java.util.List;

import Adapters.SaucesAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Fragments.HomeFragment;
import Fragments.OrdersFragment;
import Fragments.TablesFragment;
import Models.allDataAtOne.Change;
import Utils.BaseActivity;
import Utils.MyBroadcastReceiver;

public class HomeMainActivity extends BaseActivity {

    private static final String TAG = HomeMainActivity.class.getSimpleName();
    private static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static TextView syncText;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    Toolbar toolbar;
    String email;
    Intent intent;
    CartDatabaseHelper databaseHelper;
    boolean doubleBackToExitPressedOnce = false;
    MyBroadcastReceiver broadcastReceiver;
    public static RelativeLayout home_main_layout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_home_main);
        super.onCreate(savedInstanceState);

        home_main_layout = (RelativeLayout) findViewById(R.id.home_main_layout);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.WAKE_LOCK,
                        android.Manifest.permission.CHANGE_NETWORK_STATE}, 1);
            }
        }

        syncText = (TextView) findViewById(R.id.syncText);
        databaseHelper = new CartDatabaseHelper(HomeMainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //   toolbar.setTitle("Eposoft");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.openDrawer();
            }
        });

        intent = getIntent();
        email = intent.getStringExtra("email");
        headerResult.addProfiles(new ProfileDrawerItem().withEmail(email).withIcon(R.mipmap.ic_launcher));


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();

        broadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(broadcastReceiver, filter);
        headerResult.updateProfile(new ProfileDrawerItem().withEmail(email).withIcon(R.mipmap.ic_launcher));
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        intent = getIntent();
        String id = " ";
        if (intent != null) {
            id = intent.getStringExtra("frag_id");
            id = id + "";
        }

        if (id.equals("order")) {
            fragment = new OrdersFragment();
        } else {
            fragment = new HomeFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commitAllowingStateLoss();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        final AlertDialog.Builder alertDialogBuilder;
        switch (item.getItemId()) {
            case R.id.mysearch_bar:
                intent = new Intent(HomeMainActivity.this, SearchViewActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.cart:
                intent = new Intent(HomeMainActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.table:
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new TablesFragment();
                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                break;
            case R.id.changes:
                List<Change> changeList = new ArrayList<>();
                Cursor cursor = databaseHelper.getChanges();
                if (cursor.moveToFirst()) {
                    do {
                        Change change = new Change();
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String price = cursor.getString(cursor.getColumnIndex("price"));
                        String status = cursor.getString(cursor.getColumnIndex("status"));
                        change.setId(id);
                        change.setName(name);
                        change.setPrice(price);
                        change.setStatus(status);
                        Log.e(TAG, "onOptionsItemSelected: " + id + " " + name);
                        changeList.add(change);
                    } while (cursor.moveToNext());
                }
                LayoutInflater lin = LayoutInflater.from(this);
                View prompts = lin.inflate(R.layout.sauces_prompt, null);

                alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                alertDialogBuilder.setView(prompts);

                GridView prompt_grid = (GridView) prompts.findViewById(R.id.prompt_grid);
                SaucesAdapter saucesAdapter = new SaucesAdapter(this, R.layout.custom_popup_grid, changeList);
                prompt_grid.setAdapter(saucesAdapter);


                alertDialogBuilder.setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = alertDialogBuilder.create();

                if (changeList.size() > 0)
                    dialog.show();
                else
                    Toast.makeText(getApplicationContext(), "Item(s) is not in changes", Toast.LENGTH_LONG).show();

                break;

            case R.id.sauces:
                List<Change> changeList1 = new ArrayList<>();
                Cursor cursor1 = databaseHelper.getExtra();
                if (cursor1.moveToFirst()) {
                    do {
                        Change change = new Change();
                        String id = cursor1.getString(cursor1.getColumnIndex("id"));
                        String name = cursor1.getString(cursor1.getColumnIndex("name"));
                        String price = cursor1.getString(cursor1.getColumnIndex("price"));
                        String status = cursor1.getString(cursor1.getColumnIndex("status"));
                        change.setId(id);
                        change.setName(name);
                        change.setPrice(price);
                        change.setStatus(status);
                        Log.e(TAG, "onOptionsItemSelected: " + id + " " + name);
                        changeList1.add(change);
                    } while (cursor1.moveToNext());
                }
                LayoutInflater line = LayoutInflater.from(this);
                View prompts1 = line.inflate(R.layout.sauces_prompt, null);

                alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                alertDialogBuilder.setView(prompts1);

                GridView prompt_grid1 = (GridView) prompts1.findViewById(R.id.prompt_grid);
                SaucesAdapter saucesAdapter1 = new SaucesAdapter(this, R.layout.custom_popup_grid, changeList1);
                prompt_grid1.setAdapter(saucesAdapter1);


                alertDialogBuilder.setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog1 = alertDialogBuilder.create();
                if (changeList1.size() > 0)
                    dialog1.show();
                else
                    Toast.makeText(getApplicationContext(), "Item(s) is not in Sauces", Toast.LENGTH_LONG).show();
                break;

            case R.id.manual:
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.manual_prompt, null);

                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptsView);

                final EditText itemname = (EditText) promptsView.findViewById(R.id.item_name);
                final EditText itemprice = (EditText) promptsView.findViewById(R.id.item_price);

                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (itemname.getText().toString().equals("")){
                                            itemname.setError("Field should not be empty!");
                                            return;
                                        }else  if (itemprice.getText().toString().equals("")){
                                            itemprice.setError("Field should not be empty!");
                                            return;
                                        }
                                        databaseHelper.setCartItems(itemname.getText().toString(), itemprice.getText().toString(), "0", "1");
                                        Log.e(TAG, "onClick: " + itemname.getText().toString() + "  " + itemprice.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;

            case R.id.reset:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setCancelable(true)
                        .setTitle("Are you sure want to reset?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.deleteCart();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            if (doubleBackToExitPressedOnce) {
//                System.exit(1);
//                finish();
                //finish();
                try {
                    unregisterReceiver(broadcastReceiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                super.onBackPressed();


                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}
