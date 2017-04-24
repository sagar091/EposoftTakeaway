package uk.co.eposoft.eposofttakeaway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import Models.FieldsSetupResponse;
import Models.ModeResponse;
import Utils.Constants;

import static uk.co.eposoft.eposofttakeaway.R.id.cardRadioButton;
import static uk.co.eposoft.eposofttakeaway.R.id.cashRadioButton;
import static uk.co.eposoft.eposofttakeaway.R.id.cashatshopRadioButton;
import static uk.co.eposoft.eposofttakeaway.R.id.collectionRadioButton;
import static uk.co.eposoft.eposofttakeaway.R.id.deliveryRadioButton;
import static uk.co.eposoft.eposofttakeaway.R.id.instoreRadioButton;

public class ChoosePaymentTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ChoosePaymentTypeActivity.class.getSimpleName();
    LinearLayout for_delivery_layout, for_collection_layout;
    EditText houseflatno, town, phone, postcode, streetname, fname, lname, email;
//    CheckBox deliveryRadioButton, collectionRadioButton, instoreRadioButton, cashatshopRadioButton, cashRadioButton, cardRadioButton;

    LinearLayout instore_layoutbtn,collection_layoutbtn,delivery_layoutbtn;
    LinearLayout cash_layoutbtn,cardatshop_layoutbtn,card_layoutbtn;

    Button proceedButton;
    Double total, sub_total;
    String bagcharge, bankcharge, info = "";
    String otype, ptype = "";
    Toolbar toolbar;
    LinearLayout fnamelayout, lnamelayout, phonelayout, emaillayout, houselayout, postcodelayout, streetlayout, townlayout;

    Handler handler = new Handler();
    Boolean flag = true;
    ProgressDialog loading;
    FieldsSetupResponse details;
    ModeResponse modeResponse;
    SharedPreferences sharedPreferences;
    ImageView delivery_image,collection_image,instore_image;
    ImageView card_image,cardatshop_image,cash_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_type);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Order");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePaymentTypeActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        for_collection_layout = (LinearLayout) findViewById(R.id.for_collection_layout);
        for_delivery_layout = (LinearLayout) findViewById(R.id.for_delivery_layout);

        houseflatno = (EditText) findViewById(R.id.houseflatno);
        town = (EditText) findViewById(R.id.town);
        phone = (EditText) findViewById(R.id.phone);
        postcode = (EditText) findViewById(R.id.postcode);
        streetname = (EditText) findViewById(R.id.streetname);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);

        fnamelayout = (LinearLayout) findViewById(R.id.fname_layout);
        lnamelayout = (LinearLayout) findViewById(R.id.lname_layout);
        phonelayout = (LinearLayout) findViewById(R.id.phone_layout);
        emaillayout = (LinearLayout) findViewById(R.id.email_layout);
        houselayout = (LinearLayout) findViewById(R.id.houseflatno_layout);
        postcodelayout = (LinearLayout) findViewById(R.id.postcode_layout);
        streetlayout = (LinearLayout) findViewById(R.id.streetname_layout);
        townlayout = (LinearLayout) findViewById(R.id.town_layout);


        proceedButton = (Button) findViewById(R.id.proceedButton);

        card_layoutbtn = (LinearLayout) findViewById(R.id.card_layoutbtn);
        cardatshop_layoutbtn = (LinearLayout) findViewById(R.id.cardatshop_layoutbtn);
        cash_layoutbtn = (LinearLayout) findViewById(R.id.cash_layoutbtn);

        delivery_layoutbtn = (LinearLayout) findViewById(R.id.delivery_layoutbtn);
        collection_layoutbtn = (LinearLayout) findViewById(R.id.collection_layoutbtn);
        instore_layoutbtn = (LinearLayout) findViewById(R.id.instore_layoutbtn);

        instore_image= (ImageView) findViewById(R.id.instore_image);
        collection_image = (ImageView) findViewById(R.id.collection_image);
        delivery_image = (ImageView) findViewById(R.id.delivery_image);

        cash_image = (ImageView) findViewById(R.id.cash_image);
        cardatshop_image = (ImageView) findViewById(R.id.cardatshop_image);
        card_image = (ImageView) findViewById(R.id.card_image);


        instore_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otype = "instore";
                instore_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) instore_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                instore_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                collection_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                delivery_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                for_collection_layout.setVisibility(View.GONE);
                for_delivery_layout.setVisibility(View.GONE);

                instore_image.setImageResource(R.drawable.man_logo_white);
                collection_image.setImageResource(R.drawable.collection_logo);
                delivery_image.setImageResource((R.drawable.delivery_truck));
            }
        });

        collection_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otype = "collection";
                collection_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) collection_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                instore_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                delivery_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                for_collection_layout.setVisibility(View.GONE);
                for_delivery_layout.setVisibility(View.VISIBLE);
                setupButtonsC();

                instore_image.setImageResource(R.drawable.man_logo);
                collection_image.setImageResource(R.drawable.colloection_logo_white);
                delivery_image.setImageResource((R.drawable.delivery_truck));

            }
        });

        delivery_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otype = "delivery";
                delivery_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) delivery_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                instore_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                collection_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                for_collection_layout.setVisibility(View.VISIBLE);
                for_delivery_layout.setVisibility(View.VISIBLE);
                setupButtonsD();

                instore_image.setImageResource(R.drawable.man_logo);
                collection_image.setImageResource(R.drawable.collection_logo);
                delivery_image.setImageResource((R.drawable.delivery_truck_white));
            }
        });

        cash_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptype = "cash";
                cash_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) cash_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                card_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                cardatshop_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                cash_image.setImageResource(R.drawable.cash_logo_white);
                card_image.setImageResource(R.drawable.credit_card);
                cardatshop_image.setImageResource((R.drawable.visa_logo));
            }
        });

        card_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptype = "card";
                card_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) card_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                cash_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                cardatshop_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                cash_image.setImageResource(R.drawable.cash_logo);
                card_image.setImageResource(R.drawable.credit_card_white);
                cardatshop_image.setImageResource((R.drawable.visa_logo));
            }
        });

        cardatshop_layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptype = "cardatshop";
                cardatshop_layoutbtn.setBackgroundResource(R.drawable.round_padding);
                GradientDrawable drawable = (GradientDrawable) cardatshop_layoutbtn.getBackground();
                drawable.setColor(Color.parseColor("#FF4081"));

                cash_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));
                card_layoutbtn.setBackgroundColor(Color.parseColor("#aaaaaa"));

                cash_image.setImageResource(R.drawable.cash_logo);
                card_image.setImageResource(R.drawable.credit_card);
                cardatshop_image.setImageResource((R.drawable.visa_logo_white));
            }
        });


        modeResponse = new ModeResponse();
        details = new FieldsSetupResponse();

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("mcollection", false)) {
            modeResponse.setCard(sharedPreferences.getBoolean("mcard", false));
            modeResponse.setCollection(sharedPreferences.getBoolean("mcollection", false));
            modeResponse.setDelivery(sharedPreferences.getBoolean("mdelivery", false));
            modeResponse.setInside(sharedPreferences.getBoolean("minside", false));
            modeResponse.setTable(sharedPreferences.getBoolean("mtable", false));
            modeResponse.setWholesale(sharedPreferences.getBoolean("mwholesale", false));
            modeResponse.setCash(sharedPreferences.getBoolean("mcash", false));
            modeResponse.setCardatshop(sharedPreferences.getBoolean("mcardatshop", false));

            buttonSettings();
        }

        if (!sharedPreferences.getString("cph", "").equals("")) {

            details.setCph(sharedPreferences.getString("cph", ""));
            details.setCemail(sharedPreferences.getString("cemail", ""));
            details.setCfname(sharedPreferences.getString("cfname", ""));
            details.setClname(sharedPreferences.getString("clname", ""));

            details.setDemail(sharedPreferences.getString("demail", ""));
            details.setDfname(sharedPreferences.getString("dfname", ""));
            details.setDho(sharedPreferences.getString("dho", ""));
            details.setDlname(sharedPreferences.getString("dlname", ""));
            details.setDpostcode(sharedPreferences.getString("dpostcode", ""));
            details.setDph(sharedPreferences.getString("dph", ""));
            details.setDstreet(sharedPreferences.getString("dstreet", ""));
            details.setDtown(sharedPreferences.getString("dtown", ""));

            details.setOallergy(sharedPreferences.getString("oallergy", ""));
            details.setEallergy(sharedPreferences.getString("eallergy", ""));

            setupButtonsD();
        } else {
            Toast.makeText(getApplicationContext(), "Please Sync the data", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = getIntent();
        total = intent.getDoubleExtra("total", 0.0);
        sub_total = intent.getDoubleExtra("subtotal", 0.0);
        bankcharge = intent.getStringExtra("bankcharge");
        bagcharge = intent.getStringExtra("bagcharge");
        info = intent.getStringExtra("note");

        Log.e(TAG, "onCreate: subtot" + sub_total + " " + total + " " + bankcharge + " " + bagcharge);
        proceedButton.setOnClickListener(this);

        otype = "delivery";
        for_collection_layout.setVisibility(View.VISIBLE);
        for_delivery_layout.setVisibility(View.VISIBLE);

//        handler.post(runnable);


    }

    private void buttonSettings() {
        if (modeResponse.getCollection()) {
            collection_layoutbtn.setVisibility(View.VISIBLE);
        } else if (!modeResponse.getCollection()) {
            collection_layoutbtn.setVisibility(View.GONE);
        }
        if (modeResponse.getDelivery()) {
            delivery_layoutbtn.setVisibility(View.VISIBLE);
        } else {
            delivery_layoutbtn.setVisibility(View.GONE);
        }
        if (modeResponse.getInside()) {
            instore_layoutbtn.setVisibility(View.VISIBLE);
        } else {
            instore_layoutbtn.setVisibility(View.GONE);
        }
        if (modeResponse.getCard()) {
            card_layoutbtn.setVisibility(View.VISIBLE);
        } else {
            card_layoutbtn.setVisibility(View.GONE);
        }
        if (modeResponse.getCardatshop())
            cardatshop_layoutbtn.setVisibility(View.VISIBLE);
        else cardatshop_layoutbtn.setVisibility(View.GONE);

        if (modeResponse.getCash())
            cash_layoutbtn.setVisibility(View.VISIBLE);
        else cash_layoutbtn.setVisibility(View.GONE);
    }

    private void setupButtonsC() {
        Log.e(TAG, "setupButtonsC: " + details.getCfname() + "  " + details.getClname() + "  " + details.getCemail() + "  " + details.getCph());
        if (details.getCfname().equals("2")) {
            fnamelayout.setVisibility(View.GONE);
        } else if (details.getCfname().equals("1")) {
            fnamelayout.setVisibility(View.VISIBLE);
        } else if (details.getCfname().equals("0")) {
            fnamelayout.setVisibility(View.VISIBLE);
        }

        if (details.getCemail().equals("2")) {
            emaillayout.setVisibility(View.GONE);
        } else if (details.getCemail().equals("1")) {
            emaillayout.setVisibility(View.VISIBLE);
        } else if (details.getCemail().equals("0")) {
            emaillayout.setVisibility(View.VISIBLE);
        }

        if (details.getClname().equals("2")) {
            lnamelayout.setVisibility(View.GONE);
        } else if (details.getClname().equals("1")) {
            lnamelayout.setVisibility(View.VISIBLE);
        } else if (details.getClname().equals("0")) {
            lnamelayout.setVisibility(View.VISIBLE);
        }


        if (details.getCph().equals("2")) {
            phonelayout.setVisibility(View.GONE);
        } else if (details.getCph().equals("1")) {
            phonelayout.setVisibility(View.VISIBLE);
        } else if (details.getCph().equals("0")) {
            phonelayout.setVisibility(View.VISIBLE);
        }
    }

    private void setupButtonsD() {

        Log.e(TAG, "setupButtonsD: " + details.getDfname() + "  " + details.getDlname() + "  " + details.getDemail() + "  " + details.getDph());
        Log.e(TAG, "setupButtonsD: " + details.getDstreet() + " " + details.getDpostcode() + "  " + details.getDtown());
        if (details.getDfname().equals("2")) {
            fnamelayout.setVisibility(View.GONE);
        } else if (details.getDfname().equals("1")) {
            fnamelayout.setVisibility(View.VISIBLE);
        } else if (details.getDfname().equals("0")) {
            fnamelayout.setVisibility(View.VISIBLE);
        }

        if (details.getDemail().equals("2")) {
            emaillayout.setVisibility(View.GONE);
        } else if (details.getDemail().equals("1")) {
            emaillayout.setVisibility(View.VISIBLE);
        } else if (details.getDemail().equals("0")) {
            emaillayout.setVisibility(View.VISIBLE);
        }

        if (details.getDlname().equals("2")) {
            lnamelayout.setVisibility(View.GONE);
        } else if (details.getDlname().equals("1")) {
            lnamelayout.setVisibility(View.VISIBLE);
        } else if (details.getDlname().equals("0")) {
            lnamelayout.setVisibility(View.VISIBLE);
        }


        if (details.getDph().equals("2")) {
            phonelayout.setVisibility(View.GONE);
        } else if (details.getDph().equals("1")) {
            phonelayout.setVisibility(View.VISIBLE);
        } else if (details.getDph().equals("0")) {
            phonelayout.setVisibility(View.VISIBLE);
        }

        if (details.getDph().equals("2")) {
            houselayout.setVisibility(View.GONE);
        } else if (details.getDph().equals("1")) {
            houselayout.setVisibility(View.VISIBLE);
        } else if (details.getDph().equals("0")) {
            houselayout.setVisibility(View.VISIBLE);
        }

        if (details.getDph().equals("2")) {
            postcodelayout.setVisibility(View.GONE);
        } else if (details.getCph().equals("1")) {
            postcodelayout.setVisibility(View.VISIBLE);
        } else if (details.getCph().equals("0")) {
            postcodelayout.setVisibility(View.VISIBLE);
        }

        if (details.getDph().equals("2")) {
            streetlayout.setVisibility(View.GONE);
        } else if (details.getDph().equals("1")) {
            streetlayout.setVisibility(View.VISIBLE);
        } else if (details.getDph().equals("0")) {
            streetlayout.setVisibility(View.VISIBLE);
        }

        if (details.getDph().equals("2")) {
            townlayout.setVisibility(View.GONE);
        } else if (details.getDph().equals("1")) {
            townlayout.setVisibility(View.VISIBLE);
        } else if (details.getDph().equals("0")) {
            townlayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.proceedButton:

                Log.e(TAG, "ptype otype: " + ptype + "  " + otype);
                if (checkEmptyForDelivery())
                    if (!ptype.equals("") && !otype.equals("")) {
                        Intent intent = new Intent(ChoosePaymentTypeActivity.this, OrderReview.class);
                        intent.putExtra("total", total);
                        intent.putExtra("bagcharge", bagcharge);
                        intent.putExtra("bankcharge", bankcharge);
                        intent.putExtra("subtotal", sub_total);
                        intent.putExtra("otype", otype);
                        intent.putExtra("ptype", ptype);
                        intent.putExtra("note", info);
                        if (otype.equals("delivery")) {
                            intent.putExtra("fname", fname.getText().toString());
                            intent.putExtra("lname", lname.getText().toString());
                            intent.putExtra("phone", phone.getText().toString());
                            intent.putExtra("email", email.getText().toString());
                            intent.putExtra("house", houseflatno.getText().toString());
                            intent.putExtra("postcode", postcode.getText().toString());
                            intent.putExtra("street", streetname.getText().toString());
                            intent.putExtra("town", town.getText().toString());

                        } else if (otype.equals("collection")) {
                            intent.putExtra("fname", fname.getText().toString());
                            intent.putExtra("lname", lname.getText().toString());
                            intent.putExtra("phone", phone.getText().toString());
                            intent.putExtra("email", email.getText().toString());
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select the payment mode!", Toast.LENGTH_LONG).show();
                    }
                break;
        }
    }


    public boolean checkEmptyForDelivery() {
        Boolean flag = false;
        if (otype.equals("delivery")) {
            if (fname.isShown() && details.getDfname().equals("1") && (fname.getText().length() < 1)) {
                fname.setError("Field must be non-empty");
                flag = false;
            } else if (lname.isShown() && details.getDlname().equals("1") && (lname.getText().length() < 1)) {
                lname.setError("Field must be non-empty");
                flag = false;
            } else if (phone.isShown() && details.getDph().equals("1") && (phone.getText().length() < 1)) {
                phone.setError("Field must be non-empty");
                flag = false;
            } else if (email.isShown() && details.getDemail().equals("1") && (email.getText().length() < 1)) {
                email.setError("Field must be non-empty");
                flag = false;
            } else if (houseflatno.isShown() && details.getDho().equals("1") && (houseflatno.getText().length() < 1)) {
                houseflatno.setError("Field must be non-empty");
                flag = false;
            } else if (postcode.isShown() && details.getDpostcode().equals("1") && (postcode.getText().length() < 1)) {
                postcode.setError("Field must be non-empty");
                flag = false;
            } else if (streetname.isShown() && details.getDstreet().equals("1") && (streetname.getText().length() < 1)) {
                streetname.setError("Field must be non-empty");
                flag = false;
            } else if (town.isShown() && details.getDtown().equals("1") && (town.getText().length() < 1)) {
                town.setError("Field must be non-empty");
                flag = false;
            } else {
                flag = true;
            }
        } else if (otype.equals("collection")) {
            Log.e(TAG, "checkEmptyForDelivery: 1");
            if (fname.isShown() && details.getCfname().equals("1") && (fname.getText().length() < 1)) {
                fname.setError("Field must be non-empty");
                flag = false;
            } else if (lname.isShown() && details.getClname().equals("1") && (lname.getText().length() < 1)) {
                lname.setError("Field must be non-empty");
                flag = false;
            } else if (phone.isShown() && details.getCph().equals("1") && (phone.getText().length() < 1)) {
                phone.setError("Field must be non-empty");
                flag = false;
            } else if (email.isShown() && details.getCemail().equals("1") && (email.getText().length() < 1)) {
                email.setError("Field must be non-empty");
                flag = false;
            } else {
                flag = true;
            }
        } else if (otype.equals("instore")) {
            flag = true;
        }


        return flag;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChoosePaymentTypeActivity.this, CartActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }
}
