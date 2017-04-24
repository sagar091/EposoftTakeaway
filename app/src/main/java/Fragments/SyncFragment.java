package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import WebServices.syncData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Aman Singh on 9/6/2016.
 */

public class SyncFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SyncFragment.class.getSimpleName();
    CartDatabaseHelper databaseHelper;
    TextView synccategory, syncsubcat, syncitems, syncaddons, sync_all, synctable, syncstaff;

    MyProgressBar progressBar;
    syncData dataSync;
    SandageResponse sandageResponse;
    Boolean flag = true;
    ProgressDialog loading;
    Handler handler = new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FieldsSetupResponse details;
    ModeResponse modeResponse;
    MinorderResponse minOrderResponse;

    GetDetails getDetails;

// instantiate it within the onCreate method

    Runnable runnable = new Runnable() {
        public void run() {
            try {
                if (flag) {
                    loading = ProgressDialog.show(getActivity(), "Loading Data", "Please wait...", false, false);
                    flag = false;
                }
                sandageResponse = getDetails.getSandage();
                details = getDetails.getFieldButtons();
                modeResponse = getDetails.getModeDetails();
                minOrderResponse = getDetails.getMinOrder();
                dataSync.syncDiscount();
                dataSync.syncAllCoupon();

                String y = modeResponse + "";
                String z = details + "";
                String x = sandageResponse + "";
                String w = minOrderResponse + "";
                Log.e(TAG, "run: ");
                if (!x.equals("null") && !y.equals("null") && !z.equals("null") && !w.equals("null")) {
                    loading.dismiss();
                    handler.removeCallbacks(this);
                    editor = sharedPreferences.edit();
                    editor.putString("bagprice", sandageResponse.getBag());
                    editor.putString("bankprice", sandageResponse.getBank());

                    editor.putBoolean("mcollection", modeResponse.getCollection());
                    editor.putBoolean("mdelivery", modeResponse.getDelivery());
                    editor.putBoolean("minside", modeResponse.getInside());
                    editor.putBoolean("mtable", modeResponse.getTable());
                    editor.putBoolean("mwholesale", modeResponse.getWholesale());
                    editor.putBoolean("mcash", modeResponse.getCash());
                    editor.putBoolean("mcard", modeResponse.getCard());
                    editor.putBoolean("mcardatshop", modeResponse.getCardatshop());

                    editor.putString("cph", details.getCph());
                    editor.putString("cfname", details.getCfname());
                    editor.putString("clname", details.getClname());
                    editor.putString("cemail", details.getCemail());

                    editor.putString("dfname", details.getDfname());
                    editor.putString("dlname", details.getDlname());
                    editor.putString("dho", details.getDho());
                    editor.putString("dstreet", details.getDstreet());
                    editor.putString("dtown", details.getDtown());
                    editor.putString("dpostcode", details.getDpostcode());
                    editor.putString("demail", details.getDemail());
                    editor.putString("dph", details.getDph());

                    editor.putString("oallergy", details.getOallergy());
                    editor.putString("eallergy", details.getEallergy());

                    editor.putString("mincollection", minOrderResponse.getCollection());
                    editor.putString("mindelivery", minOrderResponse.getDelivery());


                    editor.commit();
                } else {
                    handler.postDelayed(runnable, 100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sync, container, false);
        syncaddons = (TextView) view.findViewById(R.id.sync_item_addons);
        syncitems = (TextView) view.findViewById(R.id.sync_item_items);
        synccategory = (TextView) view.findViewById(R.id.sync_item_cat);
        syncsubcat = (TextView) view.findViewById(R.id.sync_item_subcat);
        synctable = (TextView) view.findViewById(R.id.sync_tables);
        sync_all = (TextView) view.findViewById(R.id.sync_all);
        syncstaff = (TextView) view.findViewById(R.id.sync_staff);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new CartDatabaseHelper(getContext());
        HomeMainActivity.syncText.setVisibility(View.GONE);
        synctable.setOnClickListener(this);
        synccategory.setOnClickListener(this);
        syncaddons.setOnClickListener(this);
        syncsubcat.setOnClickListener(this);
        sync_all.setOnClickListener(this);
        syncitems.setOnClickListener(this);
        syncstaff.setOnClickListener(this);
        progressBar = new MyProgressBar(getContext());
        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        getDetails = new GetDetails(getActivity());
        handler.post(runnable);
        dataSync = new syncData(getContext());

    }


    public void syncCategory() {
        try {
            progressBar.showDialog();
            dataSync.syncCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressBar.hideDialog();
    }

    public void syncSubcategory() {
        try {
            progressBar.showDialog();
            Cursor cursor = databaseHelper.getCategory();
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    dataSync.syncSubcategory(id);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
        }
        progressBar.hideDialog();
    }

    public void syncItems() {
        progressBar.showDialog();
        databaseHelper = new CartDatabaseHelper(getContext());
        try {
            Cursor cursor = databaseHelper.getSubCategory();
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    Log.e(TAG, "onActivityCreated: " + id);
                    try {
                        dataSync.syncItem(id);
                    } catch (Exception e) {
                        Log.e(TAG, "onActivityCreated: 1" + e.getLocalizedMessage());
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, "onActivityCreated: " + e.getLocalizedMessage());
        }
        progressBar.hideDialog();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sync_item_addons:
                syncAddons();
                break;
            case R.id.sync_tables:
                setSynctable();
                break;

            case R.id.sync_all:
                setAllSync();
                break;

            case R.id.sync_item_cat:
                syncCategory();
                break;
            case R.id.sync_item_items:
                syncItems();
                break;
            case R.id.sync_item_subcat:
                syncSubcategory();
                break;
            case R.id.sync_staff:
                syncStaff();
                break;
        }
    }

    public void syncAddons() {
        progressBar.showDialog();
        dataSync.syncAddons();
        progressBar.hideDialog();
    }

    public void setSynctable() {
        progressBar.showDialog();
        dataSync.syncTableDetails();
        progressBar.hideDialog();
    }


    public void syncStaff() {
        dataSync.syncStaff();
    }


    public void setAllSync() {
        try {
            progressBar.showDialog();
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
                    progressBar.hideDialog();
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
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure:set all " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
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

    }

    private void saveAddon(List<Addon> addon) {
        databaseHelper.deleteAddonsItemList();
        databaseHelper.deleteAddonsItem();

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