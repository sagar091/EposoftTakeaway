package uk.co.eposoft.eposofttakeaway;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Models.Addons.Addon;
import Models.Addons.AddonItem;
import Models.CouponItemsList;
import Models.DiscountResponse;
import Models.FieldsSetupResponse;
import Models.MinorderResponse;
import Models.ModeResponse;
import Models.SandageResponse;
import Models.TableResponse;
import Models.allDataAtOne.AllDataResponse;
import Models.allDataAtOne.Change;
import Models.allDataAtOne.Driver;
import Models.allDataAtOne.Extra;
import Models.allDataAtOne.Item;
import Models.allDataAtOne.Menu;
import Models.allDataAtOne.Orderlist;
import Models.allDataAtOne.Printersetting;
import Models.allDataAtOne.Subcategory;
import Models.staff.StaffList;
import Models.staff.StaffResponse;
import Utils.Constants;
import Utils.GetDetails;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class WelcomeSyncActivity extends AppCompatActivity {

    private static final String TAG = WelcomeSyncActivity.class.getSimpleName();
    private static final int REQUEST_READ_CONTACTS = 0;


    RelativeLayout activity_welcome_sync;

    CartDatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GetDetails getDetails;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_sync);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        textView = (TextView) findViewById(R.id.textView1);
        activity_welcome_sync = (RelativeLayout) findViewById(R.id.activity_welcome_sync);


        populateAutoComplete();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }

    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    private void launchHomeScreen() {
        if (databaseHelper.getCategoryCountCheck() < 1) {
//            startActivity(new Intent(WelcomeSyncActivity.this, HomeMainActivity.class));
//            finish();
        } else {
            startActivity(new Intent(WelcomeSyncActivity.this, HomeMainActivity.class));
            finish();
        }
    }


    private void displayAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection!");
        builder.setMessage("Re-connect to Internet and sync again!").setCancelable(
                false).setPositiveButton("Sync Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (isOnline(WelcomeSyncActivity.this)){
                            setAllSync();
                        }else {
                            displayAlert();
                        }
                    }
                }).setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        databaseHelper = new CartDatabaseHelper(WelcomeSyncActivity.this);

        if (databaseHelper.getCategoryCountCheck() > 0) {
            startActivity(new Intent(WelcomeSyncActivity.this, HomeMainActivity.class));
            finish();
        } else {

            getDetails = new GetDetails(WelcomeSyncActivity.this);
            setAllSync();

        }

    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(activity_welcome_sync, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    public void setAllSync() {
        try {
            if(!isOnline(WelcomeSyncActivity.this)){
                displayAlert();
                return;
            }

            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                textView.setText(progressStatus + "/" + progressBar.getMax());
                                if (progressStatus == 100) {
                                    launchHomeScreen();
                                }

                            }
                        });
                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            Log.e(TAG, "setAllSync: " + id + "  " + key);

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<AllDataResponse> responseCall = apiInterface.getAllSynced(id, key, "all");
            responseCall.enqueue(new Callback<AllDataResponse>() {
                @Override
                public void onResponse(Call<AllDataResponse> call, Response<AllDataResponse> response) {
                    Log.e(TAG, "onResponse:set all " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        saveMode(response.body().getMode());
                        saveOrderlist(response.body().getOrderlist());
                        saveFields(response.body().getFields());
                        saveTables(response.body().getTables());
                        savePrintersettions(response.body().getPrintersetting());
                        saveCoupon(response.body().getCouponList());
                        saveMenu(response.body().getMenu());
                        saveAddon(response.body().getAddon());
                        saveSandage(response.body().getSandage());
                        saveMinOrder(response.body().getMinorder());
                        saveDiscount(response.body().getDiscount());
                        saveStaff(response.body().getStaff());
                        saveChanges(response.body().getChanges());
                        saveExtra(response.body().getExtra());
                        saveDriver(response.body().getDriver());
                    }
                }

                @Override
                public void onFailure(Call<AllDataResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure:set all " + t.getLocalizedMessage());

                }
            });

        } catch (Exception e) {
            Log.e(TAG, "setAllSync: excep " + e.getLocalizedMessage());
        }
    }



    private void saveMode(ModeResponse mode) {
        editor = sharedPreferences.edit();
        editor.putBoolean("mcollection", mode.getCollection());
        editor.putBoolean("mdelivery", mode.getDelivery());
        editor.putBoolean("minside", mode.getInside());
        editor.putBoolean("mtable", mode.getTable());
        editor.putBoolean("mwholesale", mode.getWholesale());
        editor.putBoolean("mcash", mode.getCash());
        editor.putBoolean("mcard", mode.getCard());
        editor.putBoolean("mcardatshop", mode.getCardatshop());
        editor.commit();
    }

    private void saveOrderlist(List<Orderlist> orderlist) {

    }

    private void saveFields(FieldsSetupResponse fields) {
        editor = sharedPreferences.edit();
        editor.putString("cph", fields.getCph());
        editor.putString("cfname", fields.getCfname());
        editor.putString("clname", fields.getClname());
        editor.putString("cemail", fields.getCemail());

        editor.putString("dfname", fields.getDfname());
        editor.putString("dlname", fields.getDlname());
        editor.putString("dho", fields.getDho());
        editor.putString("dstreet", fields.getDstreet());
        editor.putString("dtown", fields.getDtown());
        editor.putString("dpostcode", fields.getDpostcode());
        editor.putString("demail", fields.getDemail());
        editor.putString("dph", fields.getDph());

        editor.putString("oallergy", fields.getOallergy());
        editor.putString("eallergy", fields.getEallergy());

        editor.commit();
    }

    private void saveTables(List<TableResponse> tables) {
        try {
            databaseHelper.deleteTables();
            for (TableResponse tableResponse : tables) {
                databaseHelper.setTables(tableResponse.getTablename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePrintersettions(Printersetting printersetting) {

    }

    private void saveCoupon(List<CouponItemsList> couponList) {
        databaseHelper.deleteCoupon();
        for (CouponItemsList list : couponList) {
            try {
                Log.e(TAG, "coupon success: ");
                databaseHelper.setCoupon(list.getId(), list.getName(), list.getCode(), list.getDiscount(), list.getType(),
                        list.getMinimum(), list.getFrom(), list.getTo(), list.getWorkingDays(), list.getStatus());
            } catch (Exception e) {
                Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
            }

        }
    }

    private void saveMenu(List<Menu> menu) {

        try {
            databaseHelper.deleteCategory();
            databaseHelper.deleteSubCategory();
            databaseHelper.deleteItems();

            for (int i = 0; i < menu.size(); i++) {
                databaseHelper.setCategory(menu.get(i).getName(), menu.get(i).getId());
                for (Subcategory category : menu.get(i).getSubcategory()) {
                    try {
                        databaseHelper.setSubCategory(category.getName(), category.getId(), menu.get(i).getId());
                        for (Item val : category.getItem()) {
                            try {
                                databaseHelper.setItems(val.getName(), val.getId(), val.getPrice(), val.getAddon(), category.getId());
                            } catch (Exception e) {
                            }
                        }

                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    private void saveAddon(List<Addon> addon) {
        databaseHelper.deleteAddonsItemList();
        databaseHelper.deleteAddonsItem();

        try {
            for (Addon items : addon) {
                try {
                    String adid = items.getId();
                    String adli = items.getLimit();
                    String adne = items.getNext();
                    String addec = items.getDescription();
                    String adsp = items.getSpecialAddon();
                    databaseHelper.setAddonsItems(adid, adli, adne, addec, adsp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    for (AddonItem addonItem : items.getAddonItems()) {
                        databaseHelper.setAddonsList(addonItem.getId(), addonItem.getAid(), addonItem.getName(),
                                addonItem.getPrice(), addonItem.getNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
    }

    private void saveSandage(SandageResponse sandage) {
        editor = sharedPreferences.edit();
        editor.putString("bagprice", sandage.getBag());
        editor.putString("bankprice", sandage.getBank());
        editor.commit();
    }

    private void saveMinOrder(MinorderResponse minorder) {
        editor = sharedPreferences.edit();
        editor.putString("mincollection", minorder.getCollection());
        editor.putString("mindelivery", minorder.getDelivery());
        editor.commit();
    }

    private void saveDiscount(DiscountResponse discount) {
        editor = sharedPreferences.edit();
        editor.putString("item1", discount.getItem1());
        editor.putString("item2", discount.getItem2());
        editor.putString("item3", discount.getItem3());
        editor.putString("item4", discount.getItem4());
        editor.putString("item5", discount.getItem5());
        editor.putString("gift_min", discount.getGiftMin());
        editor.putString("gift_status", discount.getGiftStatus());
        editor.putString("del_status", discount.getDelStatus());
        editor.putString("col_status", discount.getColStatus());
        editor.putString("waiting_status", discount.getWaitingStatus());
        editor.putString("type_del", discount.getTypeDel());
        editor.putString("type_coll", discount.getTypeColl());
        editor.putString("type_wait", discount.getTypeWait());
        editor.putString("cto", discount.getCto());
        editor.putString("cfrom", discount.getCfrom());
        editor.putString("dto", discount.getDto());
        editor.putString("dfrom", discount.getDfrom());
        editor.putString("wto", discount.getWto());
        editor.putString("wfrom", discount.getWfrom());
        editor.putString("gto", discount.getGto());
        editor.putString("gfrom", discount.getGfrom());
        editor.putString("delivery_discount", discount.getDeliveryDiscount());
        editor.putString("collection_discount", discount.getCollectionDiscount());
        editor.putString("waiting_discount", discount.getWaitingDiscount());
        editor.putString("del_dis_min", discount.getDelDisMin());
        editor.putString("coll_dis_min", discount.getCollDisMin());
        editor.putString("wait_dis_min", discount.getWaitDisMin());
        editor.putString("coupon_status", discount.getCouponStatus());
        editor.commit();
    }

    private void saveStaff(StaffResponse staff) {
        try {
            databaseHelper.deleteStaff();
            String action = staff.getAction();
            Log.e(TAG, "staff onResponse: " + action);
            for (StaffList list : staff.getList()) {
                databaseHelper.setStaff(action, list.getId(), list.getName(), list.getDisplay());
                Log.e(TAG, " staff success: " + list.getId());
            }
        } catch (Exception e) {
            Log.e(TAG, "staff excep: " + e.getLocalizedMessage());
        }
    }


    private void saveChanges(List<Change> changes) {
        try {
            databaseHelper.deleteChanges();
            for (Change change:changes){
                databaseHelper.setChanges(change.getId(),change.getName(),change.getPrice(),change.getStatus());
            }
        } catch (Exception e) {
            Log.e(TAG, "saveChanges: "+e.getLocalizedMessage() );
        }
    }

    private void saveExtra(List<Extra> extras) {
        try {
            databaseHelper.deleteExtra();
            for (Extra extra : extras){
                databaseHelper.setExtra(extra.getId(),extra.getName(),extra.getPrice(),extra.getStatus());
            }
        } catch (Exception e) {
            Log.e(TAG, "saveExtra: "+e.getLocalizedMessage());
        }
    }

    private void saveDriver(List<Driver> drivers) {
        try {
            databaseHelper.deleteDriver();
            for (Driver driver:drivers){
                 databaseHelper.setDriver(driver.getId(),driver.getName(),
                        driver.getEmpid(),driver.getVtype(),driver.getVno(),driver.getCno(),driver.getStatus());
            }
        }catch (Exception e){
            Log.e(TAG, "saveDriver: "+e.getLocalizedMessage() );        }
    }

}
