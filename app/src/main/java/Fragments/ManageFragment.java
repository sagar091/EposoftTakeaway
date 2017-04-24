package Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import Models.AutoLoginResponse;
import Utils.Constants;
import Utils.MyProgressBar;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.eposoft.eposofttakeaway.R;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mobo on 8/5/2016.
 */
public class ManageFragment extends Fragment {

    private static final String TAG = ManageFragment.class.getSimpleName();
    WebView webView;
    String APP_ID, APP_KEY;
    SharedPreferences sharedPreferences;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private MyProgressBar progressBar;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progress.setVisibility(View.GONE);
        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE);
        String id = sharedPreferences.getString(Constants.SHARE_APPID,"");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY,"");
        getAutologinUrl(id,key);


    }

    public void getAutologinUrl(String id, String key){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EposoftApiInterface apiInterface = retrofit.create(EposoftApiInterface.class);
            Call<AutoLoginResponse> responseCall = apiInterface.autoLoginUrl(id,key,"autologin");

            responseCall.enqueue(new Callback<AutoLoginResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(Call<AutoLoginResponse> call,Response<AutoLoginResponse> response) {
                    Log.e(TAG, "onResponse: "+response.isSuccessful() );
                    if (response.isSuccessful()){
                        Log.e(TAG, "onResponse: "+response.body().getStatus() );
                        if (response.body().getStatus()){
                            WebSettings mSettings = webView.getSettings();
                            mSettings.setJavaScriptEnabled(true);
                            mSettings.setDomStorageEnabled(true);
                            mSettings.setAllowFileAccess(true);
                            mSettings.setGeolocationEnabled(true);
                            mSettings.setAllowContentAccess(true);

                            webView.setWebViewClient(new MyWebViewClient());
                            webView.getSettings().setDisplayZoomControls(true);
                            webView.loadUrl(response.body().getUrl());
                            Log.e(TAG, "onResponse: "+response.body().getUrl() );
                        }
                    }
                }

                @Override
                public void onFailure(Call<AutoLoginResponse> call,Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                }
            });
        }catch (Exception e){
            Log.e(TAG, "getAutologinUrl: "+e.getLocalizedMessage() );
        }
    }


    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            ManageFragment.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            ManageFragment.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

}

