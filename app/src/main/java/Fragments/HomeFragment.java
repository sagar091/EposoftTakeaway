package Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import Adapters.HomeGridViewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.CategoryResponse;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Mobo on 7/21/2016.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    FloatingActionMenu fabMenu;
    FloatingActionButton bill, checkout, expresscheckout;
    FragmentManager fragmentManager;
    Fragment fragment;
    List<CategoryResponse> liststring;
    GridView gridView;
    HomeGridViewAdapter gridAdapter;
    CartDatabaseHelper databaseHelper;
    TextView syncText;
    LinearLayout home_frag_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);
        syncText = (TextView) view.findViewById(R.id.syncText);

        fabMenu = (FloatingActionMenu) view.findViewById(R.id.menu);
        fabMenu.setVisibility(View.GONE);
        expresscheckout = (FloatingActionButton) view.findViewById(R.id.menu_item2);
        checkout = (FloatingActionButton) view.findViewById(R.id.menu_item1);
        bill = (FloatingActionButton) view.findViewById(R.id.menu_item);
        home_frag_layout = (LinearLayout) view.findViewById(R.id.home_frag_layout);
//        Log.e(TAG, "onCreateView: ");
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeMainActivity.syncText.setVisibility(View.VISIBLE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WAKE_LOCK,
                        Manifest.permission.CHANGE_NETWORK_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
            }else {
                databaseHelper = new CartDatabaseHelper(getContext());
                liststring = new ArrayList<>();
                getAdapter();
            }
        }else {
            databaseHelper = new CartDatabaseHelper(getContext());
            liststring = new ArrayList<>();
            getAdapter();
        }


        expresscheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabMenu.close(true);
            }
        });

        fabMenu.setClosedOnTouchOutside(true);

        liststring = new ArrayList<>();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    databaseHelper = new CartDatabaseHelper(getContext());
                    liststring = new ArrayList<>();
                    getAdapter();
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void getAdapter() {
        databaseHelper = new CartDatabaseHelper(getContext());
        Cursor cursor = databaseHelper.getCategory();

        if (cursor.moveToFirst()) {
            HomeMainActivity.syncText.setVisibility(View.GONE);
            do {
                CategoryResponse res = new CategoryResponse();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                res.setName(name);
                res.setId(id);
                liststring.add(res);
            } while (cursor.moveToNext());
        }
//        Log.e(TAG, "getAdapter: " + databaseHelper.getCategotyCountCheck());
        try {
            gridAdapter = new HomeGridViewAdapter(getContext(), R.layout.grid_custom, liststring);
            gridView.setAdapter(gridAdapter);
        } catch (Exception e) {
            Log.e(TAG, "getAdapter: excp " + e.getLocalizedMessage());
        }
    }

}

