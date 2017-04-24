package uk.co.eposoft.eposofttakeaway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Adapters.NewOrderReviewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Fragments.ViewBillPageFragment;
import Models.Coupon;
import Models.MinorderResponse;
import Models.NewCartData;
import Models.PlaceOrder.Item;
import Models.PlaceOrder.MakeOrder;
import Models.PlaceOrder.OrderResponse;
import Models.PlaceOrder.Sub;
import Models.PlaceOrder.User;
import Models.SandageResponse;
import Utils.Constants;
import WebServices.EposoftApiInterface;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderReview extends AppCompatActivity {

    private static final String TAG = OrderReview.class.getSimpleName();
    public static TextView totalPriceTextView, subTotalPriceTextView;
    public List<String> data, subdata, addonlist, price, subdataprice;
    public NewOrderReviewAdapter newOrderReviewAdapter;
    RecyclerView recyclerView;
    CartDatabaseHelper databaseHelper;
    Cursor cursor;
    Toolbar toolbar;
    MinorderResponse minOrderResponse;
    Button checkOutButton, checkCancleButton;
    CheckBox allergyButton;
    TextView bagPriceTextView, yourOrder, bankPriceTextView, cartEmptyTxt, discountTextView, discountPriceTextView, couponTextView;
    Double total, sub_total, return_total;
    String bagcharge = "0.0", bankcharge = "0";
    LinearLayout bankHolder, onlinediscountHolder, couponLayout;
    EditText infoEditText;
    NestedScrollView contentHolder;
    FragmentManager fragmentManager;
    Fragment fragment;
    FrameLayout cart_frame;
    String otype, ptype, houseflatno, town, phone, postcode, streetname, fname, lname, email, info = "";
    SandageResponse sandage;
    Coupon coupon;
    User user;
    ArrayList<Item> orderItemList;
    Boolean flag = true;
    ProgressDialog loading;
    SharedPreferences sharedPreferences;
    String discount_details[];
    FButton couponApplyButton;
    MakeOrder makeOrder;
    TextView couponAppliedTextView;
    TextInputLayout couponInputLayout;
    TextView userOffer;
    FrameLayout offerLayout;
    List<String> stringList;
    private List<NewCartData> newCartDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Log.e(TAG, "onCreate: ");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Order Review");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderReview.this, ChoosePaymentTypeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        stringList = new ArrayList<>();
        discount_details = new String[30];
        databaseHelper = new CartDatabaseHelper(OrderReview.this);
        discountPriceTextView = (TextView) findViewById(R.id.discountPriceTextView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);
        onlinediscountHolder = (LinearLayout) findViewById(R.id.onlinediscountHolder);
        couponLayout = (LinearLayout) findViewById(R.id.couponLayout);
        couponTextView = (TextView) findViewById(R.id.couponTextView);
        couponApplyButton = (FButton) findViewById(R.id.couponApplyButton);
        couponAppliedTextView = (TextView) findViewById(R.id.couponAppliedTextView);
        couponInputLayout = (TextInputLayout) findViewById(R.id.couponInputLayout);

        userOffer = (TextView) findViewById(R.id.userOffer);
        offerLayout = (FrameLayout) findViewById(R.id.offerLayout);

        if (databaseHelper.getTotalCount() < 1) {
            finish();
        }
        totalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);
        subTotalPriceTextView = (TextView) findViewById(R.id.subTotalPriceTextView);
        bagPriceTextView = (TextView) findViewById(R.id.bagPriceTextView);
        bankPriceTextView = (TextView) findViewById(R.id.bankPriceTextView);

        cart_frame = (FrameLayout) findViewById(R.id.main_frame);
        cart_frame.setVisibility(View.GONE);

        contentHolder = (NestedScrollView) findViewById(R.id.contentHolder);
        cartEmptyTxt = (TextView) findViewById(R.id.cartEmptyTxt);

        contentHolder.setVisibility(View.VISIBLE);
        cartEmptyTxt.setVisibility(View.GONE);

        checkOutButton = (Button) findViewById(R.id.checkOutButton);
        checkCancleButton = (Button) findViewById(R.id.checkCancleButton);
        allergyButton = (CheckBox) findViewById(R.id.allergyButton);
        bankHolder = (LinearLayout) findViewById(R.id.bankHolder);

        yourOrder = (TextView) findViewById(R.id.yourOrder);
        infoEditText = (EditText) findViewById(R.id.infoEditText);
        infoEditText.setVisibility(View.GONE);

        yourOrder.setText("Order Review");

        makeOrder = new MakeOrder();
        minOrderResponse = new MinorderResponse();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        checkCancleButton.setVisibility(View.VISIBLE);
        checkCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderReview.this, HomeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        allergyButton.setVisibility(View.GONE);

        Log.e(TAG, "Constants: " + Constants.item_count[0] + " " + Constants.item_count[1]);

        coupon = new Coupon();
        init();
        getDataFromDB();
        setUpRecycleView();

        couponAppliedTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= couponAppliedTextView.getRight() - couponAppliedTextView.getTotalPaddingRight()) {
                        // your action for drawable click event
                        couponInputLayout.setVisibility(View.VISIBLE);
                        couponAppliedTextView.setVisibility(View.GONE);
                        couponApplyButton.setText("Apply");
                        couponApplyButton.setEnabled(true);
                        totalPriceTextView.setText(String.format("£ %s", total));
                        return true;
                    }
                }
                return true;
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItemsForOrder();
            }
        });
        couponApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllCoupons(couponTextView.getText().toString());
                if (cursor.moveToFirst()) {

                    final String couponcode = cursor.getString(cursor.getColumnIndex("code"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String dis_discount = cursor.getString(cursor.getColumnIndex("discount"));
                    String dis_type = cursor.getString(cursor.getColumnIndex("type"));
                    String dis_min = cursor.getString(cursor.getColumnIndex("minimum"));
                    String dis_from_date = cursor.getString(cursor.getColumnIndex("from_date"));
                    String dis_to_date = cursor.getString(cursor.getColumnIndex("to_date"));
                    String dis_working_days = cursor.getString(cursor.getColumnIndex("working_days"));
                    String dis_status = cursor.getString(cursor.getColumnIndex("status"));

                    Date from_date = new Date(Long.parseLong(dis_from_date) * 1000);
                    Date to_date = new Date(Long.parseLong(dis_to_date) * 1000);
                    Log.e(TAG, "onClick: " + to_date.after(new Date()) + "  " + from_date.before(new Date()));
                    if (to_date.after(new Date()) && from_date.before(new Date())) {
                        Log.e(TAG, "onClick: " + total);
                        Double temp_total = total;
                        if (temp_total > Double.valueOf(dis_min)) {

                            coupon.setName(name);
                            coupon.setCode(couponcode);
                            coupon.setDiscount(dis_discount);
                            coupon.setType(dis_type);

                            if (dis_type.equals("%")) {
                                discountPriceTextView.setText(String.format(" %s%% ", dis_discount));
                                temp_total = temp_total - ((temp_total * Double.valueOf(dis_discount)) / 100);
                                Log.e(TAG, "Discount:% " + temp_total);
                            } else {
                                discountPriceTextView.setText(String.format("Flat %s £", dis_discount));
                                temp_total -= Double.valueOf(dis_discount);
                            }

                            temp_total = Double.valueOf(new DecimalFormat("###.##").format(temp_total));
                            totalPriceTextView.setText(String.format("£ %s", temp_total));
//                            couponTextView.setVisibility(View.GONE);
                            couponAppliedTextView.setVisibility(View.VISIBLE);
                            couponInputLayout.setVisibility(View.GONE);
                            couponAppliedTextView.setTextSize(24);
                            couponAppliedTextView.setGravity(Gravity.CENTER);
                            couponAppliedTextView.setText(couponTextView.getText());
                            couponApplyButton.setText("Coupon Applied");
                            couponApplyButton.setEnabled(false);

                        } else {
                            Toast.makeText(getApplicationContext(), "Minimum amount for this code is " + dis_min, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Coupon Code", Toast.LENGTH_LONG).show();
                    }

                    Log.e(TAG, "onClick: " + couponcode);
                    if (couponTextView.getText().toString().equals(couponcode)) {
                        Log.e(TAG, "onClick: coupon matched");
                    } else {
                        Log.e(TAG, "onClick: mismatch");
                    }

                }

            }
        });

//        handler.post(runnable);


        //Todo - need to check these two method
        initPreferences();
//        checkDelivieyDiscount();

        Log.e(TAG, "onCreate: " + total + "  " + otype);
        if (sharedPreferences.getString("coupon_status", "").equals("1")) {
            couponLayout.setVisibility(View.VISIBLE);
        }

        if (sharedPreferences.getString("gift_status", "").equals("1")) {
            offerLayout.setVisibility(View.VISIBLE);
            String gift_min = sharedPreferences.getString("gift_min", "");
            String item1 = sharedPreferences.getString("item1", "");
            String item2 = sharedPreferences.getString("item2", "");
            String item3 = sharedPreferences.getString("item3", "");
            String item4 = sharedPreferences.getString("item4", "");
            String item5 = sharedPreferences.getString("item5", "");
            if (Double.parseDouble(gift_min) <= total) {
                stringList = new ArrayList<String>();
                stringList.add("None");
                if (!item1.equals(""))
                    stringList.add(item1);
                if (!item2.equals(""))
                    stringList.add(item2);
                if (!item3.equals(""))
                    stringList.add(item3);
                if (!item4.equals(""))
                    stringList.add(item4);
                if (!item5.equals(""))
                    stringList.add(item5);
            } else {
                offerLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "For free gift choose minimum amount of " + gift_min, Toast.LENGTH_LONG).show();
            }
        }
        userOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOffersDialog(stringList);
                if (!userOffer.getText().toString().equals("None") && !userOffer.getText().toString().equals("Click here to choose you offer")) {
                    makeOrder.setFgift(userOffer.getText().toString());
                }
            }
        });
        if (otype.equals("instore")) {
            if (Double.valueOf(discount_details[26]) <= total) {
                onlinediscountHolder.setVisibility(View.VISIBLE);
                discountTextView.setText("Online instore discount");
                checkInsideDiscount();
            } else {
                onlinediscountHolder.setVisibility(View.GONE);
            }
        } else if (otype.equals("collection")) {
            if (Double.valueOf(discount_details[25]) <= total) {
                onlinediscountHolder.setVisibility(View.VISIBLE);
                discountTextView.setText("Online collection discount");
                checkCollectionDiscount();
            } else {
                onlinediscountHolder.setVisibility(View.GONE);
            }
        } else if (otype.equals("delivery")) {
            if (Double.valueOf(discount_details[24]) <= total) {
                onlinediscountHolder.setVisibility(View.VISIBLE);
                discountTextView.setText("Online delivery discount");
                checkDelivieyDiscount();
            } else {
                onlinediscountHolder.setVisibility(View.GONE);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("mincollection", "").equals("")) {
            minOrderResponse.setCollection(sharedPreferences.getString("mincollection", ""));
            minOrderResponse.setDelivery(sharedPreferences.getString("mindelivery", ""));
        }


    }

    public void init() {
        Log.e(TAG, "init: ");
        data = new ArrayList<>();
        price = new ArrayList<>();
        subdata = new ArrayList<>();
        addonlist = new ArrayList<>();
        subdataprice = new ArrayList<>();

        Intent intent = getIntent();
        total = intent.getDoubleExtra("total", 0.0);
        sub_total = intent.getDoubleExtra("subtotal", 0.0);
        bankcharge = intent.getStringExtra("bankcharge");
        bagcharge = intent.getStringExtra("bagcharge");
        otype = intent.getStringExtra("otype");
        ptype = intent.getStringExtra("ptype");
        fname = intent.getStringExtra("fname");
        lname = intent.getStringExtra("lname");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        houseflatno = intent.getStringExtra("house");
        postcode = intent.getStringExtra("postcode");
        streetname = intent.getStringExtra("street");
        town = intent.getStringExtra("town");
        info = intent.getStringExtra("note");

        Log.e(TAG, otype + "onCreate: " + ptype);


        coupon.setCode("");
        coupon.setDiscount("");
        coupon.setName("");
        coupon.setType("");

        makeOrder.setDdesc("");
        makeOrder.setDiscount("");
        makeOrder.setFgift("");

        user = new User();
        user.setFname(fname);
        user.setLname(lname);
        user.setDno(streetname);
        user.setAdd1(houseflatno);
        user.setAdd2("");
        user.setUsername(email);
        user.setId("0");
        user.setStatus("1");
        user.setPostcode(postcode);
        user.setPhone(phone);

        sandage = new SandageResponse();
        sandage.setBag(bagcharge);

        orderItemList = new ArrayList<>();
        return_total = total;

        if (ptype.equals("cash")) {
            bankHolder.setVisibility(View.GONE);
            sandage.setBank("");
        } else if (ptype.equals("cardatshop")) {
            bankHolder.setVisibility(View.VISIBLE);
            total = total + Double.parseDouble(bankcharge);
            sandage.setBank(bankcharge);
        } else if (ptype.equals("card")) {
            bankHolder.setVisibility(View.VISIBLE);
            total = total + Double.parseDouble(bankcharge);
            sandage.setBank(bankcharge);
        }


        subTotalPriceTextView.setText(String.format("£ %s", sub_total));
        totalPriceTextView.setText(String.format("£ %s", total));

        bankPriceTextView.setText(bankcharge);
        bagPriceTextView.setText(bagcharge);

    }

    public void checkInsideDiscount() {
        Log.e(TAG, "checkInsideDiscount: " + discount_details[17] + "  " + discount_details[18]);
        if (sharedPreferences.getString("waiting_status", "").equals("1")) {
            String timecto = discount_details[17];
            String timecfrom = discount_details[18];

            Date STAMPCTO = new Date(Long.parseLong(timecto) * 1000);
            Date STAMCFROM = new Date(Long.parseLong(timecfrom) * 1000);

            Log.e(TAG, "checcollDiscount: " + STAMPCTO + "  " + new Date());
            Log.e(TAG, "checkcollDiscount: " + STAMPCTO.after(new Date()) + " , " + STAMCFROM.before(new Date()));

            if (STAMPCTO.after(new Date()) && STAMCFROM.before(new Date())) {
                if (discount_details[12].equals("%")) {
                    discountPriceTextView.setText(String.format(" %s%% ", discount_details[23]));
                    total = total - ((total * Double.valueOf(discount_details[23])) / 100);
                    Log.e(TAG, discount_details[23] + "checkcollDiscount:% " + total);
                } else {
                    discountPriceTextView.setText(String.format("Flat %s £", discount_details[23]));
                    total -= Double.valueOf(discount_details[23]);
                    Log.e(TAG, discount_details[23] + "checkcollDiscount:- " + total);
                }
                makeOrder.setDiscount(discount_details[23]);
                makeOrder.setDdesc("Online Inside discount");
                total = Double.valueOf(new DecimalFormat("###.##").format(total));
                totalPriceTextView.setText(String.format("£ %s", total));
            }
        }
    }

    public void checkCollectionDiscount() {
        Log.e(TAG, "checkcollDiscount: " + discount_details[13] + "  " + discount_details[14]);

        if (sharedPreferences.getString("col_status", "").equals("1")) {
            String timecto = discount_details[13];
            String timecfrom = discount_details[14];

            Date STAMPCTO = new Date(Long.parseLong(timecto) * 1000);
            Date STAMCFROM = new Date(Long.parseLong(timecfrom) * 1000);

            Log.e(TAG, "checcollDiscount: " + STAMPCTO + "  " + new Date());
            Log.e(TAG, "checkcollDiscount: " + STAMPCTO.after(new Date()) + " , " + STAMCFROM.before(new Date()));

            if (STAMPCTO.after(new Date()) && STAMCFROM.before(new Date())) {
                if (discount_details[11].equals("%")) {
                    discountPriceTextView.setText(String.format(" %s%%", discount_details[22]));
                    total = total - ((total * Double.valueOf(discount_details[22])) / 100);
                    Log.e(TAG, discount_details[22] + "checkcollDiscount:% " + total);
                } else {
                    discountPriceTextView.setText(String.format("Flat %s £", discount_details[22]));
                    total -= Double.valueOf(discount_details[22]);
                    Log.e(TAG, discount_details[22] + "checkcollDiscount:- " + total);
                }
                makeOrder.setDiscount(discount_details[22]);
                makeOrder.setDdesc("Online Collection discount");
                total = Double.valueOf(new DecimalFormat("###.##").format(total));
                totalPriceTextView.setText(String.format("£ %s", total));
            }
        }
    }

    public void checkDelivieyDiscount() {
        Log.e(TAG, "checkDelivieyDiscount: " + discount_details[15] + "  " + discount_details[16]);
        if (sharedPreferences.getString("del_status", "").equals("1")) {
            String timedto = discount_details[15];
            String timedfrom = discount_details[16];

            Date STAMPDTO = new Date(Long.parseLong(timedto) * 1000);
            Date STAMPFROM = new Date(Long.parseLong(timedfrom) * 1000);

            Log.e(TAG, "checkDelivieyDiscount: " + STAMPDTO + "  " + discount_details[10] + "  " + total);
            Log.e(TAG, "checkDelivieyDiscount: " + STAMPDTO.after(new Date()) + " , " + STAMPFROM.before(new Date()));

            if (STAMPDTO.after(new Date()) && STAMPFROM.before(new Date())) {
                if (discount_details[10].equals("%")) {
                    discountPriceTextView.setText(String.format(" %s%%", discount_details[21]));
                    total = total - ((total * Double.valueOf(discount_details[21])) / 100);
                    Log.e(TAG, discount_details[21] + "checkDelivieyDiscount:% " + total);
                } else {
                    discountPriceTextView.setText(String.format("Flat %s £", discount_details[21]));
                    total -= Double.valueOf(discount_details[21]);
                    Log.e(TAG, discount_details[21] + "checkDelivieyDiscount:- " + total);
                }
                makeOrder.setDiscount(discount_details[21]);
                makeOrder.setDdesc("Online Delivery discount");
                total = Double.valueOf(new DecimalFormat("###.##").format(total));
                totalPriceTextView.setText(String.format("£ %s", total));
            }
        }
    }

    public void initPreferences() {
//        for (int i = 0; i < 28; i++) {
//            discount_details[i] = sharedPreferences.getString(Constants.DISCOUNT_VALUES[i], "");
////            Log.e(TAG, "init: " + discount_details[i]);
//        }
        discount_details[0] = sharedPreferences.getString("item1", "");
        discount_details[1] = sharedPreferences.getString("item2", "");
        discount_details[2] = sharedPreferences.getString("item3", "");
        discount_details[3] = sharedPreferences.getString("item4", "");
        discount_details[4] = sharedPreferences.getString("item5", "");
        discount_details[5] = sharedPreferences.getString("gift_min", "");
        discount_details[6] = sharedPreferences.getString("gift_status", "");
        discount_details[7] = sharedPreferences.getString("del_status", "");
        discount_details[8] = sharedPreferences.getString("col_status", "");
        discount_details[9] = sharedPreferences.getString("waiting_status", "");
        discount_details[10] = sharedPreferences.getString("type_del", "");
        discount_details[11] = sharedPreferences.getString("type_coll", "");
        discount_details[12] = sharedPreferences.getString("type_wait", "");
        discount_details[13] = sharedPreferences.getString("cto", "");
        discount_details[14] = sharedPreferences.getString("cfrom", "");
        discount_details[15] = sharedPreferences.getString("dto", "");
        discount_details[16] = sharedPreferences.getString("dfrom", "");
        discount_details[17] = sharedPreferences.getString("wto", "");
        discount_details[18] = sharedPreferences.getString("wfrom", "");
        discount_details[19] = sharedPreferences.getString("gto", "");
        discount_details[20] = sharedPreferences.getString("gfrom", "");
        discount_details[21] = sharedPreferences.getString("delivery_discount", "");
        discount_details[22] = sharedPreferences.getString("collection_discount", "");
        discount_details[23] = sharedPreferences.getString("waiting_discount", "");
        discount_details[24] = sharedPreferences.getString("del_dis_min", "");
        discount_details[25] = sharedPreferences.getString("coll_dis_min", "");
        discount_details[26] = sharedPreferences.getString("wait_dis_min", "");
        discount_details[27] = sharedPreferences.getString("coupon_status", "");
    }


    private void getOffersDialog(final List<String> stringList) {

        final AlertDialog levelDialog;
        final CharSequence[] items = stringList.toArray(new CharSequence[stringList.size()]);

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your offer");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        userOffer.setText(stringList.get(0));
                        break;
                    case 1:
                        userOffer.setText(stringList.get(1));
                        break;
                    case 2:
                        userOffer.setText(stringList.get(2));
                        Log.e(TAG, "2nd selected");
                        break;
                    case 3:
                        userOffer.setText(stringList.get(3));
                        Log.e(TAG, "3rd selected");
                        break;
                    case 4:
                        userOffer.setText(stringList.get(4));
                        Log.e(TAG, "4th selected");
                        break;
                    case 5:
                        userOffer.setText(stringList.get(5));
                        break;
                }
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void getItemsForOrder() {
        Cursor cursor = databaseHelper.getCartItems();


        if (cursor.moveToFirst()) {
            do {
                Item orderItem = new Item();
                List<Sub> sublist = new ArrayList<>();
                String name = cursor.getString(cursor.getColumnIndex("itemname"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String id = cursor.getString(cursor.getColumnIndex("itemid"));
                String qty = cursor.getString(cursor.getColumnIndex("qty"));
                Log.e(TAG, "MainItem: " + name + " " + price + " " + id);
                Cursor cursor1 = databaseHelper.getCartSubItems(id);
                if (cursor1.moveToFirst()) {
                    do {
                        Sub subItem = new Sub();
                        String ext;
                        String itemid = cursor1.getString(cursor1.getColumnIndex("itemid"));
                        String subname = cursor1.getString(cursor1.getColumnIndex("subname"));
                        String subprice = cursor1.getString(cursor1.getColumnIndex("subprice"));
                        String subid = cursor1.getString(cursor1.getColumnIndex("subid"));
                        String subqty = cursor1.getString(cursor1.getColumnIndex("subqty"));
                        String subtype = cursor1.getString(cursor1.getColumnIndex("subtype"));
                        String subext = cursor1.getString(cursor1.getColumnIndex("subext"));


                        subItem.setType(subtype);
                        subItem.setExt(subext);
                        subItem.setItem(subid);
                        subItem.setQty(subqty);
                        sublist.add(subItem);
                        Log.e(TAG, "MainSUBItem: " + itemid + "  " + subext + " " + subtype);
                    } while (cursor1.moveToNext());
                }
                orderItem.setQty(qty);
                orderItem.setItemid(id);
                orderItem.setSub(sublist);
                orderItemList.add(orderItem);
            } while (cursor.moveToNext());
        }
        Order();
    }


    public void subListConverter(List<String> itemhead) {

        Log.e(TAG, "size itemhead " + itemhead.size());

        for (int i = 0; i < itemhead.size(); i++) {
            Item orderItem = new Item();

            Log.e(TAG, "itemhead" + itemhead.get(i));

            List<String> subitems = new ArrayList<>();
            List<String> subaddon = new ArrayList<>();
            Cursor cursor = databaseHelper.getCartSubdata(itemhead.get(i));
            if (cursor.moveToFirst()) {
                do {
                    subaddon.add(cursor.getString(cursor.getColumnIndex("addontype")));
                    subitems.add(cursor.getString(cursor.getColumnIndex("itemid")));
                } while (cursor.moveToNext());
            }
            Log.e(TAG, subitems.size() + " subheadid " + subitems);

            for (int k = 0; k < subitems.size(); k++) {

                List<Sub> subList = new ArrayList<>();
                String str = subaddon.get(k).substring(1, subaddon.get(k).length() - 1);
                List<String> stringList = new ArrayList<>(Arrays.asList(str.split(",")));

                String string = subitems.get(k).substring(1, subitems.get(k).length() - 1);
                List<String> singleList = new ArrayList<String>(Arrays.asList(string.split(",")));
                Log.e(TAG, singleList.size() + " : listitem : " + singleList.toString());
                if (!singleList.toString().equals("[]")) {
                    for (int l = 0; l < singleList.size(); l++) {
                        Sub sub = new Sub();
                        String addon;
                        sub.setType("addon");
                        sub.setQty("1");
                        sub.setItem(singleList.get(l));
                        if (stringList.get(l).contains("No"))
                            addon = "0";
                        else if (stringList.get(l).contains("Less"))
                            addon = "1";
                        else if (stringList.get(l).contains("Half"))
                            addon = "2";
                        else if (stringList.get(l).contains("On"))
                            addon = "3";
                        else if (stringList.get(l).contains("With"))
                            addon = "4";
                        else if (stringList.get(l).contains("On Burger"))
                            addon = "5";
                        else if (stringList.get(l).contains("On Chips"))
                            addon = "6";
                        else addon = "";

                        sub.setExt(addon);
                        Log.e(TAG, stringList.get(l) + " sub id value" + singleList.get(l));
                        subList.add(sub);
                    }
//                    Log.e(TAG, "ext : "+subList.get(1).getExt() );
                }
                orderItem.setSub(subList);
                orderItem.setItemid(itemhead.get(i));
                orderItem.setQty("1");

            }
            orderItemList.add(orderItem);
        }
//        Log.e(TAG, "subListConverter: "+orderItemList.get(0).getSub().get(1).getExt());
        Order();
    }

    public void setUpRecycleView() {
        Log.e(TAG, "setUpRecycleView: ");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderReview.this);
        recyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.VISIBLE);
        getNewCartData();

        newOrderReviewAdapter = new NewOrderReviewAdapter(OrderReview.this, newCartDatas);
        recyclerView.setAdapter(newOrderReviewAdapter);

//        OrderReviewAdapter cartAdapter = new OrderReviewAdapter(OrderReview.this, data, price, subdata, subdataprice, addonlist);
//        recyclerView.setAdapter(cartAdapter);
    }


    public void getDataFromDB() {
        Log.e(TAG, "getDataFromDB: ");
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
    }


    public void Order() {
        Log.e(TAG, "Order: ");
        final ProgressDialog loading = ProgressDialog.show(OrderReview.this, "Loading Data", "Please wait...", false, false);
        String typ, ptyp;
        if (otype.equals("collection"))
            typ = "1";
        else if (otype.equals("delivery")) {
            typ = "0";
        } else {
            typ = "2";
        }

        if (ptype.equals("card")) {
            ptyp = "1";
        } else if (ptype.equals("cardatshop")) {
            ptyp = "2";
        } else {
            ptyp = "0";
        }

//        makeOrder = new MakeOrder();
        String id1 = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key1 = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

        String tablename = sharedPreferences.getString("tablename", "");

        makeOrder.setAppId(id1);
        makeOrder.setAppKey(key1);
        makeOrder.setRequest("placeorder");

        makeOrder.setPtype(ptyp);
        makeOrder.setOtype(typ);
        makeOrder.setUser(user);
        makeOrder.setDelivery("0");
        makeOrder.setItems(orderItemList);
        makeOrder.setCoupon(coupon);
        makeOrder.setInst(info);
        makeOrder.setSandage(sandage);
        makeOrder.setFree("0");
        makeOrder.setRefno("");
        makeOrder.setSgift("");
        makeOrder.setTablename(tablename);
        makeOrder.setStaff("");

        try {
            Log.e(TAG, "Order: staff");

            Cursor cursor = databaseHelper.getStaffItems();
            if (cursor.moveToFirst()) {
                Log.e(TAG, "Order: cursor");
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String action = cursor.getString(cursor.getColumnIndex("action"));
                Log.e(TAG, "staff Details: id " + action);
                if (action.equals("1")) {
                    loading.dismiss();
                    Intent intent = new Intent(OrderReview.this, StaffListActivity.class);
                    intent.putExtra("makeorder", makeOrder);
                    startActivity(intent);
                    finish();
                    return;
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Order: exception in staff");
        }

        try {
            Constants constants = new Constants();
            if (constants.isOnline(OrderReview.this)) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
                Call<OrderResponse> orderResponseCall = eposoftApiInterface.postOrder(makeOrder);
                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        loading.dismiss();
                        try {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    databaseHelper.deleteCart();
                                    showDialog("You placed order successfully!", response.body().getOrderId());
                                } else {
                                    Toast.makeText(getApplicationContext(), " Error: ", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), " Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        loading.dismiss();
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
            } else {
                loading.dismiss();
                Toast.makeText(OrderReview.this, " You've place your order successfully!", Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                String json = gson.toJson(makeOrder);
                databaseHelper.setMyOrder(json);
                Log.e(TAG, "MaketheOrder: " + databaseHelper.countMyOrders());
                databaseHelper.deleteCart();
                Intent intent = new Intent(OrderReview.this, HomeMainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            loading.dismiss();
            Log.e(TAG, "Order: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderReview.this, ChoosePaymentTypeActivity.class);
        intent.putExtra("total", return_total);
        intent.putExtra("subtotal", sub_total);
        intent.putExtra("bankcharge", bankcharge);
        intent.putExtra("bagcharge", bagcharge);
        intent.putExtra("note", info);
        startActivity(intent);
        finish();
    }

    private void showDialog(String msg, final Integer orderId) {
        AlertDialog alert = new AlertDialog.Builder(OrderReview.this, R.style.MyDialogTheme)
                //  .setTitle("Alert")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Print Bill", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cart_frame.setVisibility(View.VISIBLE);
                        contentHolder.setVisibility(View.GONE);
                        cartEmptyTxt.setVisibility(View.GONE);
                        fragmentManager = getSupportFragmentManager();
                        fragment = ViewBillPageFragment.newInstance(String.valueOf(orderId));
                        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();

//                        Intent intent = new Intent(OrderReview.this, HomeMainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(OrderReview.this, HomeMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
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

}
