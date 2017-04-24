package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/27/2016.
 */
public class ViewBillPageFragment extends Fragment {

    private static final String TAG = ViewBillPageFragment.class.getSimpleName();
    static String mCode;
    SharedPreferences sharedPreferences;
    private WebView mWebView;

    public static ViewBillPageFragment newInstance(String code) {
        ViewBillPageFragment fragment = new ViewBillPageFragment();
        mCode = code;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewbill, container, false);
        mWebView = (WebView) view.findViewById(R.id.bill_webview);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        print();
    }

    private void print() {
        mWebView = new WebView(getActivity());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                doPrint();

            }
        });

        String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
        String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

        mWebView.loadUrl("http://android.eposapi.co.uk/index.php?app_id=" + id + "&app_key=" + key + "&request=viewbill&oid=" + mCode);
    }

    private void doPrint() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            PrintManager printManager = (PrintManager) getActivity().getSystemService(
                    Context.PRINT_SERVICE);
            PrintDocumentAdapter adapter;
            adapter = new PrintDocumentAdapter() {
                private final PrintDocumentAdapter mWrappedInstance =
                        mWebView.createPrintDocumentAdapter();

                @SuppressLint("NewApi")
                @Override
                public void onStart() {
                    mWrappedInstance.onStart();
                }

                @SuppressLint("NewApi")
                @Override
                public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                                     CancellationSignal cancellationSignal, LayoutResultCallback callback,
                                     Bundle extras) {
                    mWrappedInstance.onLayout(oldAttributes, newAttributes, cancellationSignal,
                            callback, extras);
                }

                @SuppressLint("NewApi")
                @Override
                public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                                    CancellationSignal cancellationSignal, WriteResultCallback callback) {
                    mWrappedInstance.onWrite(pages, destination, cancellationSignal, callback);
                }

                @SuppressLint("NewApi")
                @Override
                public void onFinish() {
                    mWrappedInstance.onFinish();
                    // Intercept the finish call to know when printing is done
                    // and destroy the WebView as it is expensive to keep around.
                    mWebView.destroy();
                    mWebView = null;
                    Fragment fragment = new ViewBillPageFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(fragment);
//                    fragment = new HomeFragment();
//                    transaction.replace(R.id.main_frame,fragment);
//                    transaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getContext(), HomeMainActivity.class);
                    startActivity(intent);
                    getActivity().finish();


                }
            };
            printManager.print(String.valueOf(R.string.app_name), adapter, null);
        } else {
            Toast.makeText(getContext(), "Your device is not supported for printing", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), HomeMainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        // Pass in the ViewView's document adapter.

    }


}
