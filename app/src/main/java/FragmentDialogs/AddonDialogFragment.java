package FragmentDialogs;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapters.AddonAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.AddonStack;
import Models.Addons.Addon;
import Models.Addons.AddonItem;
import Models.CartItem;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/26/2016.
 */
public class AddonDialogFragment extends DialogFragment implements View.OnClickListener, AddonAdapter.addItemsInCart {
    private static final String TAG = AddonDialogFragment.class.getSimpleName();
    public static AddonStack addonStack;
    public static CartItem item;
    static Integer mCode = 0;
    static String mTitle;
    static String hashmapHead = "";
    static String mainItemId, mainItemPrice;
    static CartDatabaseHelper helper;
    public static List<CartItem> cartItemList;
    private List<AddonItem> liststring;
    GridView gridView;
    AddonAdapter gridAdapter;
    ImageView closeDialog;
    TextView title;
    Addon addon;
    Button popupbtn;
    HashMap<String, List<String>> hashMap;
    CartDatabaseHelper databaseHelper;
    private com.rey.material.widget.CheckBox onchips, no, less, half, with, onburger, on;
    public List<String> singleList;

    public AddonDialogFragment() {

    }

    public static AddonDialogFragment newInstance(Integer code, String name, String itemId, String itemPrice) {
        AddonDialogFragment fragment = new AddonDialogFragment();
        mCode = code;
        mTitle = name;
        mainItemId = itemId;
        mainItemPrice = itemPrice;
        return fragment;
    }

    public static AddonDialogFragment newInstance(Integer code) {
        AddonDialogFragment fragment = new AddonDialogFragment();
        mCode = code;
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_addon_layout, container, false);
        gridView = (GridView) view.findViewById(R.id.addon_sub_grid);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        closeDialog = (ImageView) getDialog().findViewById(R.id.closeDialog);
        title = (TextView) getDialog().findViewById(R.id.dialogTitle);

        title.setText(String.format("Choose %s ", mTitle));
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        no = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b1);
        less = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b2);
        half = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b3);
        on = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b4);
        with = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b5);
        onburger = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b6);
        onchips = (com.rey.material.widget.CheckBox) getDialog().findViewById(R.id.b7);
        addon = new Addon();
//        Log.e(TAG, "onActivityCreated: " + mTitle);

        hashmapHead = "";
        item = new CartItem();
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    half.setChecked(false);
                    on.setChecked(false);
                    with.setChecked(false);
                    onburger.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "No";
                } else
                    hashmapHead = "";
            }
        });

        less.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    no.setChecked(false);
                    half.setChecked(false);
                    on.setChecked(false);
                    with.setChecked(false);
                    onburger.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "Less";
                } else
                    hashmapHead = "";
            }
        });
        half.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    no.setChecked(false);
                    on.setChecked(false);
                    with.setChecked(false);
                    onburger.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "Half";
                } else hashmapHead = "";
            }
        });
        on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    half.setChecked(false);
                    no.setChecked(false);
                    with.setChecked(false);
                    onburger.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "On";
                } else hashmapHead = "";
            }
        });
        with.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    half.setChecked(false);
                    on.setChecked(false);
                    no.setChecked(false);
                    onburger.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "With";
                } else hashmapHead = "";
            }
        });
        onburger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    half.setChecked(false);
                    on.setChecked(false);
                    with.setChecked(false);
                    no.setChecked(false);
                    onchips.setChecked(false);
                    hashmapHead = "On Burger";
                } else hashmapHead = "";
            }
        });
        onchips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    less.setChecked(false);
                    half.setChecked(false);
                    on.setChecked(false);
                    with.setChecked(false);
                    onburger.setChecked(false);
                    no.setChecked(false);
                    hashmapHead = "On Chips";
                } else hashmapHead = "";
            }
        });
        popupbtn = (Button) getDialog().findViewById(R.id.pupupcontinue);
        popupbtn.setOnClickListener(this);

        liststring = new ArrayList<>();
        addonStack = new AddonStack();
        hashMap = new HashMap<>();
        singleList = new ArrayList<>();
        cartItemList = new ArrayList<>();
        helper = new CartDatabaseHelper(getContext());
        databaseHelper = new CartDatabaseHelper(getContext());
        getAdapter();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        liststring = new ArrayList<>();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    public void getAdapter() {

        Cursor cursor = databaseHelper.getAddonItems(String.valueOf(mCode));
        Log.e(TAG, "getAdapter: " + mCode);
        if (cursor.moveToFirst()) {
            do {
                Addon res = new Addon();
                String spcaddon = cursor.getString(cursor.getColumnIndex("specialAddon"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                String next = cursor.getString(cursor.getColumnIndex("next"));
                String limitaddon = cursor.getString(cursor.getColumnIndex("limitaddon"));
                res.setLimit(limitaddon);
                res.setDescription(desc);
                res.setNext(next);
                res.setSpecialAddon(spcaddon);
                res.setId(id);
                addon = res;
//                Log.e(TAG, mCode + "getAdapter: addon" + addon);
            } while (cursor.moveToNext());
        }

        cursor = databaseHelper.getAddonListById(String.valueOf(mCode));
        Log.e(TAG, "getAdapter:addonitem " + mCode);
        if (cursor.moveToFirst()) {
            do {
                AddonItem res = new AddonItem();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String next = cursor.getString(cursor.getColumnIndex("next"));
                String aid = cursor.getString(cursor.getColumnIndex("aid"));
//                Log.e(TAG, "getAdapter:id " + id);
                res.setAid(aid);
                res.setPrice(price);
                res.setNext(next);
                res.setName(name);
                res.setId(id);
                liststring.add(res);
            } while (cursor.moveToNext());
        }
        Log.e(TAG, " getAdapter:next " + addon.getNext());
        if (addon.getNext() != null) {
            if (!addon.getNext().equals("") && !addon.getNext().equals("0")) {
                addonStack.push(addon.getNext());
            }
        }

        on.setChecked(false);
        no.setChecked(false);
        half.setChecked(false);
        less.setChecked(false);
        with.setChecked(false);
        onburger.setChecked(false);
        onchips.setChecked(false);
        no.setVisibility(View.GONE);
        on.setVisibility(View.GONE);
        half.setVisibility(View.GONE);
        less.setVisibility(View.GONE);
        with.setVisibility(View.GONE);
        onburger.setVisibility(View.GONE);
        onchips.setVisibility(View.GONE);

        try {
            if (addon != null && addon.getSpecialAddon() != null) {
                if (!addon.getSpecialAddon().equals("")) {
                    getButton(addon.getSpecialAddon());
                }
            }
            Log.e(TAG, "getaddon: " + addon.getSpecialAddon());
            gridAdapter = new AddonAdapter(getContext(), R.layout.gridview_custom_layout, liststring);
            gridView.setAdapter(gridAdapter);
        } catch (Exception e) {
            Log.e(TAG, "getAdapter:exception " + e.getLocalizedMessage());
        }
    }

    public void getButton(String val) {

        char ch[] = val.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            if (Integer.parseInt(String.valueOf(ch[i])) == 0) {
                no.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 1) {
                less.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 2) {
                half.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 3) {
                on.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 4) {
                with.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 5) {
                onburger.setVisibility(View.VISIBLE);
            }
            if (Integer.parseInt(String.valueOf(ch[i])) == 6) {
                onchips.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pupupcontinue:

                if (!addonStack.isEmpty()) {
                    mCode = Integer.valueOf(addonStack.pop());
                    Log.e(TAG, " addon pop " + mCode);
                    liststring.clear();
                    getAdapter();
                } else {
                    Snackbar.make(v,"Item(s) added in your cart", Snackbar.LENGTH_LONG).show();

                    dismiss();
                    //ToDo  - changes here look up
                    try {
                        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        dismissAllDialogs(fragmentManager);
                    } catch (Exception e) {

                    }
                    Cursor cursor = databaseHelper.getAddons();
                    List<String> name, price, id, head;
                    name = new ArrayList<>();
                    price = new ArrayList<>();
                    id = new ArrayList<>();
                    head = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            name.add(cursor.getString(cursor.getColumnIndex("addonName")));
                            price.add(cursor.getString(cursor.getColumnIndex("addonPrice")));
                            id.add(cursor.getString(cursor.getColumnIndex("addonId")));
                            head.add(cursor.getString(cursor.getColumnIndex("hashmapHead")));
                            String adname = cursor.getString(cursor.getColumnIndex("addonName"));
                            String adprice = cursor.getString(cursor.getColumnIndex("addonPrice"));
                            String adid = cursor.getString(cursor.getColumnIndex("addonId"));
                            Log.e(TAG, "addons: " + cursor.getString(cursor.getColumnIndex("hashmapHead")) + "  " + adname + "  " + adid + "  " + adprice);
                            String addons = "";
                            if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("No"))
                                addons = "0";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("Less"))
                                addons = "1";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("Half"))
                                addons = "2";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("On"))
                                addons = "3";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("With"))
                                addons = "4";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("On Burger"))
                                addons = "5";
                            else if (cursor.getString(cursor.getColumnIndex("hashmapHead")).contains("On Chips"))
                                addons = "6";

                            databaseHelper.setSubAddonsCartItems(mainItemId, adname, adprice, adid, "1", "addon", addons);

                        } while (cursor.moveToNext());
                    }
//                    Log.e(TAG, "onClick: " + name + " " + price + " " + id + " " + head);
                    if (!name.isEmpty()) {
                        hashMap.put("itemName", name);
                        hashMap.put("price", price);
                        hashMap.put("itemid", id);
                        hashMap.put("addontype", head);
                        Log.e(TAG, " Adding: " + mTitle + " " + mainItemId + " " + mainItemPrice + " " + head);
//                        databaseHelper.setSubAddonsCartItems()
                        if (databaseHelper.searchCartItems(mainItemId) > 0) {
                            Log.e(TAG, "search total " + databaseHelper.searchCartItems(mainItemId));
                            int i= databaseHelper.searchCartItems(mainItemId);
                            databaseHelper.updateCartItemsQty(String.valueOf(i+1),mainItemId);
                        } else
                            databaseHelper.setCartItems(mTitle, mainItemPrice, mainItemId, "1");
//                        databaseHelper.setCartItem(mTitle, mainItemId, mainItemPrice, hashMap);
                        databaseHelper.deleteAddons();
                        Snackbar.make(HomeMainActivity.home_main_layout,"Item(s) added in your cart.",Snackbar.LENGTH_LONG).show();
                    }
                }
//                item = new CartItem();
                break;

        }
    }

    @Override
    public void getItemFromAddon(String addonName, String addonId, String addonPrice) {
        dbInsert(addonName, addonPrice, addonId, hashmapHead);
//        item.push(addonName, addonPrice, addonId, hashmapHead);
//        Log.e(TAG, "getItemFromAddon: " + addonName + "  " + addonId + "  " + addonPrice + "  " + hashmapHead);
    }

    public void dbInsert(String addonName, String addonPrice, String addonId, String hashmapHead) {
//        Log.e(TAG, addonPrice + "dbInsert: " + hashmapHead);
        if (hashmapHead.equals("Half")) {
            addonPrice = String.valueOf(Double.parseDouble(addonPrice) / 2);
//            Log.e(TAG, "price: " + addonPrice);
        }
        if (hashmapHead.equals("No")) {
            addonPrice = String.valueOf(0.0);
//            Log.e(TAG, "price: " + addonPrice);
        }
        helper.setAddons(addonName, addonPrice, addonId, hashmapHead);
//        Log.e(TAG, "getItemFromAddon: " + addonName + "  " + addonId + "  " + addonPrice + "  " + hashmapHead);

    }

    public static void dismissAllDialogs(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismissAllowingStateLoss();
            }

            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            if (childFragmentManager != null)
                dismissAllDialogs(childFragmentManager);
        }
    }
}
