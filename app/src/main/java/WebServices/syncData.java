package WebServices;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Models.Addons.Addon;
import Models.Addons.AddonItem;
import Models.CategoryResponse;
import Models.CouponItemsList;
import Models.DiscountResponse;
import Models.ItemResponse;
import Models.OrdersList.Table;
import Models.SubcategoryResponse;
import Models.TableResponse;
import Models.staff.StaffList;
import Models.staff.StaffResponse;
import Utils.Constants;
import Utils.MyProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aman Singh on 9/1/2016.
 */

public class syncData {
    public String TAG = syncData.class.getSimpleName();
    Context context;
    CartDatabaseHelper databaseHelper;
    MyProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public syncData(Context context) {
        this.context = context;
        databaseHelper = new CartDatabaseHelper(context);
        progressBar = new MyProgressBar(context);

        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void syncCategory() {
        try {
            progressBar.showDialog();
            databaseHelper.deleteCategory();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<CategoryResponse>> calllist = apiInterface.getCategory(id, key, "category");
            calllist.enqueue(new Callback<List<CategoryResponse>>() {
                @Override
                public void onResponse(Call<List<CategoryResponse>> v ,Response<List<CategoryResponse>> response) {
                    progressBar.hideDialog();
                    try {
                        int x = 0;
                        for (CategoryResponse val : response.body()) {
                            databaseHelper.setCategory(val.getName(), val.getId());
                        }
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onFailure(Call<List<CategoryResponse>> v, Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getCategory: " + e.getMessage());
        }
    }

    public void syncSubcategory(final String code) {
        try {
            progressBar.showDialog();
            databaseHelper.deleteSubCategory();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<SubcategoryResponse>> calllist = apiInterface.getSubCategory(id, key, "subcategory", code);
            calllist.enqueue(new Callback<List<SubcategoryResponse>>() {
                @Override
                public void onResponse( Call<List<SubcategoryResponse>> v,Response<List<SubcategoryResponse>> response) {
                    progressBar.hideDialog();
//                    Log.e(TAG, "onResponse: " + response.body().get(0).getName());
                    try {
                        for (SubcategoryResponse val : response.body()) {
                            databaseHelper.setSubCategory(val.getName(), val.getId(), code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure( Call<List<SubcategoryResponse>>v ,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getCategory: " + e.getMessage());
        }
    }

    public void syncItem(final String mCode) {
        try {
            progressBar.showDialog();
            databaseHelper.deleteItems();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<ItemResponse>> calllist = apiInterface.getItem(id, key, "item", mCode);
            calllist.enqueue(new Callback<List<ItemResponse>>() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onResponse( Call<List<ItemResponse>> v,Response<List<ItemResponse>> response) {
                    progressBar.hideDialog();

                    try {
                        for (ItemResponse val : response.body()) {
                            databaseHelper.setItems(val.getName(), val.getId(), val.getPrice(), val.getAddon(), mCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<ItemResponse>> v,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getCategory: " + e.getMessage());
        }
    }

    public void syncAddons() {
        try {
            progressBar.showDialog();
            databaseHelper.deleteAddonsItemList();
            databaseHelper.deleteAddonsItem();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<Addon>> calllist = apiInterface.getAllAddon(id, key, "addonlist");
            calllist.enqueue(new Callback<List<Addon>>() {
                @Override
                public void onResponse(Call<List<Addon>> v,Response<List<Addon>> response) {
                    //    List<AddonResponse> itemsList = response.body();
                    for (Addon items : response.body()) {

                        try {
//                            Log.e(TAG, "onResponse:items " + items.getAddon().getSpecialAddon());
                            Addon addon = items;
                            String adid = addon.getId();
                            String adli = addon.getLimit();
                            String adne = addon.getNext();
                            String addec = addon.getDescription();
                            String adsp = addon.getSpecialAddon();
                            databaseHelper.setAddonsItems(adid, adli, adne, addec, adsp);
                            Log.e(TAG, "onResponse: " + items.getId() + "  " + items.getLimit());
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
                    progressBar.hideDialog();
                }

                @Override
                public void onFailure(Call<List<Addon>> v,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getCategory: " + e.getMessage());
        }
    }

    public void syncTableDetails() {
        try {
            progressBar.showDialog();
            databaseHelper.deleteTables();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<TableResponse>> responseCall = eposoftApiInterface.getTable(id, key, "tables");
            responseCall.enqueue(new Callback<List<TableResponse>>() {
                @Override
                public void onResponse(Call<List<TableResponse>> v, Response<List<TableResponse>> response) {
                    Log.e(TAG, "onResponse: " + response.body().size());
                    if (response.isSuccessful()) {
                        try {
                            for (TableResponse tableResponse : response.body()) {
                                databaseHelper.setTables(tableResponse.getTablename());
                            }
                        } catch (Exception e) {

                        }
                    }
                    progressBar.hideDialog();
                }

                @Override
                public void onFailure(Call<List<TableResponse>> v,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "getOrders: " + e.getLocalizedMessage());
        }
    }

    public void syncStaff() {
        try {
            progressBar.showDialog();
            Log.e(TAG, "sync Staff: del staff");
            databaseHelper.deleteStaff();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<StaffResponse> responseCall = eposoftApiInterface.getStaff(id, key, "staff");
            responseCall.enqueue(new Callback<StaffResponse>() {
                @Override
                public void onResponse(Call<StaffResponse> v,Response<StaffResponse> response) {
                    Log.e(TAG, "staff onResponse: " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        try {
                            String action = response.body().getAction();
                            Log.e(TAG, "staff onResponse: " + action);
                            for (StaffList list : response.body().getList()) {
                                databaseHelper.setStaff(action, list.getId(), list.getName(), list.getDisplay());
                                Log.e(TAG, " staff onResponse: " + list.getId());
                            }
                        } catch (Exception e) {

                        }
                    }
                    progressBar.hideDialog();
                }

                @Override
                public void onFailure(Call<StaffResponse> v,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "syncStaffExcep: " + e.getLocalizedMessage());
        }
    }

    public void syncDiscount() {
        try {
            progressBar.showDialog();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<DiscountResponse> responseCall = eposoftApiInterface.getDiscount(id, key, "discount");
            responseCall.enqueue(new Callback<DiscountResponse>() {
                @Override
                public void onResponse(Call<DiscountResponse> v,Response<DiscountResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            editor = sharedPreferences.edit();
                            editor.putString("item1", response.body().getItem1());
                            editor.putString("item2", response.body().getItem2());
                            editor.putString("item3", response.body().getItem3());
                            editor.putString("item4", response.body().getItem4());
                            editor.putString("item5", response.body().getItem5());
                            editor.putString("gift_min", response.body().getGiftMin());
                            editor.putString("gift_status", response.body().getGiftStatus());
                            editor.putString("del_status", response.body().getDelStatus());
                            editor.putString("col_status", response.body().getColStatus());
                            editor.putString("waiting_status", response.body().getWaitingStatus());
                            editor.putString("type_del", response.body().getTypeDel());
                            editor.putString("type_coll", response.body().getTypeColl());
                            editor.putString("type_wait", response.body().getTypeWait());
                            editor.putString("cto", response.body().getCto());
                            editor.putString("cfrom", response.body().getCfrom());
                            editor.putString("dto", response.body().getDto());
                            editor.putString("dfrom", response.body().getDfrom());
                            editor.putString("wto", response.body().getWto());
                            editor.putString("wfrom", response.body().getWfrom());
                            editor.putString("gto", response.body().getGto());
                            editor.putString("gfrom", response.body().getGfrom());
                            editor.putString("delivery_discount", response.body().getDeliveryDiscount());
                            editor.putString("collection_discount", response.body().getCollectionDiscount());
                            editor.putString("waiting_discount", response.body().getWaitingDiscount());
                            editor.putString("del_dis_min", response.body().getDelDisMin());
                            editor.putString("coll_dis_min", response.body().getCollDisMin());
                            editor.putString("wait_dis_min", response.body().getWaitDisMin());
                            editor.putString("coupon_status", response.body().getCouponStatus());
                            editor.commit();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
                        }
                    }
                    progressBar.hideDialog();
                }

                @Override
                public void onFailure(Call<DiscountResponse> v,Throwable t) {
                    progressBar.hideDialog();
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "syncStaffExcep: " + e.getLocalizedMessage());
        }
    }

    public void syncAllCoupon() {
        try {
            progressBar.showDialog();
            databaseHelper.deleteCoupon();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<List<CouponItemsList>> itemsListCall = eposoftApiInterface.getCouponList(id, key, "couponlist");
            itemsListCall.enqueue(new Callback<List<CouponItemsList>>() {
                @Override
                public void onResponse(Call<List<CouponItemsList>>call,Response<List<CouponItemsList>> response) {
                    if (response.isSuccessful()) {
                        for (CouponItemsList list : response.body()) {
                            try {
                                databaseHelper.setCoupon(list.getId(), list.getName(), list.getCode(), list.getDiscount(), list.getType(),
                                        list.getMinimum(), list.getFrom(), list.getTo(), list.getWorkingDays(), list.getStatus());
                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CouponItemsList>>call,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {

        }
    }
}
