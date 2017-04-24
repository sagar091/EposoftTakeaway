package uk.co.eposoft.eposofttakeaway;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Models.KeyActivationResponse;
import Models.KeyGenerationResponse;
import Models.PrefManager;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KeyGenarationActivity extends AppCompatActivity {

    private static final String TAG = KeyGenarationActivity.class.getSimpleName();
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public String appid, appkey, msg;
    TextView keytxt, contacttxt;
    Button generate, activateBtn;
    private PrefManager prefManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    MyProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_genaration);

        keytxt = (TextView) findViewById(R.id.keytxt);
        contacttxt = (TextView) findViewById(R.id.contacttxt);
        generate = (Button) findViewById(R.id.generatebtn);
        activateBtn = (Button) findViewById(R.id.activatebtn);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
            return;
        }


        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
        String msg = sharedPreferences.getString(Constants.SHARE_MSG, "");
        if (!id.equals("") && !key.equals("")) {
            generate.setVisibility(View.GONE);
            activateBtn.setVisibility(View.VISIBLE);
            keytxt.setText(id);
            contacttxt.setText(msg);
            String status = sharedPreferences.getString(Constants.SHARE_STATUS, "0");
            if (status.equals("1")) {
                launchHomeScreen();
                return;
            }
        }
        progressBar = new MyProgressBar(KeyGenarationActivity.this);

        activateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
                String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
                String msg = sharedPreferences.getString(Constants.SHARE_MSG, "");
                setActivation(id, key, msg);
            }
        });

        if (id.equals(""))
            generateKey();

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(KeyGenarationActivity.this, WelcomeSyncActivity.class));
        finish();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            Toast.makeText(this, "Permission Must be granted!!!", Toast.LENGTH_LONG).show();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
    }


    public void generateKey() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<KeyGenerationResponse> responseCall = apiInterface.keyGeneration("serialkey");
            responseCall.enqueue(new Callback<KeyGenerationResponse>() {
                @Override
                public void onResponse(Call<KeyGenerationResponse>s,Response<KeyGenerationResponse> response) {
                    if (response.isSuccessful()) {
                        generate.setVisibility(View.GONE);
                        activateBtn.setVisibility(View.VISIBLE);
                        appid = response.body().getAppid();
                        appkey = response.body().getAppkey();
                        msg = response.body().getMsg();
                        contacttxt.setText(response.body().getMsg());
                        keytxt.setText(response.body().getAppid());

                        editor = sharedPreferences.edit();
                        editor.putString(Constants.SHARE_APPID, response.body().getAppid());
                        editor.putString(Constants.SHARE_APPKEY, response.body().getAppkey());
                        editor.putString(Constants.SHARE_MSG, response.body().getMsg());
                        editor.commit();

                        Log.e(TAG, "appid: " + response.body().getAppid());
                        Log.e(TAG, "appkey: " + response.body().getAppkey());
                    }
                }

                @Override
                public void onFailure(Call<KeyGenerationResponse>s,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "generateKey: " + e.getLocalizedMessage());
        }
    }

    public void setActivation(String appid, String appkey, final String msg) {
        try {
            progressBar.showDialog();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<KeyActivationResponse> responseCall = apiInterface.keyActivation(appid, appkey, "activate");
            responseCall.enqueue(new Callback<KeyActivationResponse>() {
                @Override
                public void onResponse(Call<KeyActivationResponse>s,Response<KeyActivationResponse> response) {
                    progressBar.hideDialog();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + response.body().getStatus());
                        if (response.body().getStatus().equals("1")) {
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.SHARE_STATUS, response.body().getStatus());
                            editor.commit();
                            launchHomeScreen();
                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<KeyActivationResponse>s,Throwable t) {
                    progressBar.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            progressBar.hideDialog();
            Log.e(TAG, "generateKey: " + e.getLocalizedMessage());
        }
    }
}
