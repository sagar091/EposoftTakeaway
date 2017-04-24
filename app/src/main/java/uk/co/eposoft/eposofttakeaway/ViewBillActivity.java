package uk.co.eposoft.eposofttakeaway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import Models.CloseTableResponse;
import Models.ModeResponse;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewBillActivity extends AppCompatActivity {

    WebView webView;
    String billid;
    SharedPreferences sharedPreferences;
    Button closetable,lateorderbtn,editbtn;
    String pmode = "", tablename = "";
    ModeResponse modeResponse;
    CheckBox cardatshopmode, cardmode, cashmode;
    MyProgressBar progressBar;
    private String TAG = ViewBillActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        closetable = (Button) findViewById(R.id.closetable);
        closetable.setVisibility(View.GONE);

        lateorderbtn = (Button) findViewById(R.id.lateorderbtn);
        lateorderbtn.setVisibility(View.GONE);

        editbtn = (Button) findViewById(R.id.editbtn);
        editbtn.setVisibility(View.GONE);

        cashmode = (CheckBox) findViewById(R.id.cashmode);
        cardatshopmode = (CheckBox) findViewById(R.id.cardatshopmode);
        cardmode = (CheckBox) findViewById(R.id.cardmode);

        progressBar = new MyProgressBar(ViewBillActivity.this);
        Intent intent = getIntent();
        billid = intent.getStringExtra("billid");

        if (intent.getFlags() == 2) {
            tablename = intent.getStringExtra("tablename");
            closetable.setVisibility(View.VISIBLE);
        }

        Log.e(TAG, "onCreate: " + billid);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        final String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

        Log.e(TAG, "onCreate: \n " + id + " \n " + key);

        webView = (WebView) findViewById(R.id.webview_view_bill);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDisplayZoomControls(true);
        webView.loadUrl("http://epos.eposapi.co.uk/index.php?app_id=" + id + "&app_key=" + key + "&request=viewbill&oid=" + billid);
        closetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pmode.equals(""))
                    closeTable(tablename, pmode);
                else
                    Toast.makeText(getBaseContext(), "Please select payment mode", Toast.LENGTH_LONG).show();
            }
        });

        modeResponse = new ModeResponse();

        if (sharedPreferences.getBoolean("mcollection", false)) {
            modeResponse.setCard(sharedPreferences.getBoolean("mcard", false));
            modeResponse.setCollection(sharedPreferences.getBoolean("mcollection", false));
            modeResponse.setDelivery(sharedPreferences.getBoolean("mdelivery", false));
            modeResponse.setInside(sharedPreferences.getBoolean("minside", false));
            modeResponse.setTable(sharedPreferences.getBoolean("mtable", false));
            modeResponse.setWholesale(sharedPreferences.getBoolean("mwholesale", false));
            modeResponse.setCash(sharedPreferences.getBoolean("mcash", false));
            modeResponse.setCardatshop(sharedPreferences.getBoolean("mcardatshop", false));
        }

        if (modeResponse.getCard()) {
            cardmode.setVisibility(View.VISIBLE);
        } else cardmode.setVisibility(View.GONE);
        if (modeResponse.getCardatshop())
            cardatshopmode.setVisibility(View.VISIBLE);
        else cardatshopmode.setVisibility(View.GONE);
        if (modeResponse.getCash())
            cashmode.setVisibility(View.VISIBLE);
        else cashmode.setVisibility(View.GONE);

        cardatshopmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pmode = "2";
                    cashmode.setChecked(false);
                    cardmode.setChecked(false);
                }
            }
        });
        cashmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pmode = "0";
                    cardatshopmode.setChecked(false);
                    cardmode.setChecked(false);
                }
            }
        });
        cardmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pmode = "1";
                    cardatshopmode.setChecked(false);
                    cashmode.setChecked(false);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewBillActivity.this, HomeMainActivity.class);
        intent.putExtra("frag_id", "order");
        startActivity(intent);
        finish();
    }

    public void closeTable(String tablename, String pmode) {
        try {
            sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            progressBar.showDialog();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<CloseTableResponse> responseCall = apiInterface.closeTable(id, key, "closetable", tablename, pmode);
            responseCall.enqueue(new Callback<CloseTableResponse>() {
                @Override
                public void onResponse(Call<CloseTableResponse> call, Response<CloseTableResponse> response) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onResponse: " + response.isSuccessful());
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<CloseTableResponse> call, Throwable t) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "closeTable: " + e.getLocalizedMessage());
        }
    }

}
