package FragmentDialogs;


import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.HomeSubGridViewAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.SubcategoryResponse;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/26/2016.
 */
public class SubcategoryFragment extends DialogFragment {

    private static final String TAG = SubcategoryFragment.class.getSimpleName();
    static Integer mCode = 0;
    static String mTitle;
    List<SubcategoryResponse> liststring;
    GridView gridView;
    HomeSubGridViewAdapter gridAdapter;
    ImageView closeDialog;
    TextView title;
    CartDatabaseHelper databaseHelper;

    public SubcategoryFragment() {

    }

    public static SubcategoryFragment newInstance(Integer code, String name) {
        SubcategoryFragment fragment = new SubcategoryFragment();

        mCode = code;
        mTitle = name;
        return fragment;
    }

    private static void dismissAllDialogs(FragmentManager manager) {
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liststring = new ArrayList<>();
        databaseHelper = new CartDatabaseHelper(getContext());
        getAdapter();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subcategory, container, false);
        gridView = (GridView) view.findViewById(R.id.sub_grid);
        closeDialog = (ImageView) view.findViewById(R.id.closeDialog);
        title = (TextView) view.findViewById(R.id.dialogTitle);

        title.setText(String.format("1.Choose %s", mTitle));
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

//        gridView = (GridView) dialog.findViewById(R.id.sub_grid);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //  closeDialog = (ImageView) dialog.findViewById(R.id.closeDialog);

        return dialog;

    }

    public void getAdapter() {
        Cursor cursor = databaseHelper.getSubCategory(String.valueOf(mCode));
        if (cursor.moveToFirst()) {
            do {
                SubcategoryResponse res = new SubcategoryResponse();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                res.setName(name);
                res.setId(id);
                liststring.add(res);
            } while (cursor.moveToNext());
        }
//        Log.e(TAG, "getAdapter: " + databaseHelper.getCategotyCountCheck());
        try {
            gridAdapter = new HomeSubGridViewAdapter(getContext(), R.layout.gridview_custom_layout, liststring);
            gridView.setAdapter(gridAdapter);

        } catch (Exception e) {
            Log.e(TAG, "getAdapter: " + e.getMessage() + "  " + gridAdapter + "  " + gridView);
        }
    }


}

