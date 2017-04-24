package FragmentDialogs;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

import Adapters.ItemGridAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.ItemResponse;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/26/2016.
 */
public class ItemDialogFragment extends DialogFragment {
    private static final String TAG = ItemDialogFragment.class.getSimpleName();
    static Integer mCode = 0;
    static String mTitle;
    List<ItemResponse> liststring;
    GridView gridView;
    ItemGridAdapter gridAdapter;
    ImageView closeDialog;
    TextView title;
    CartDatabaseHelper databaseHelper;


    public ItemDialogFragment() {

    }

    public static ItemDialogFragment newInstance(Integer code, String name) {
        ItemDialogFragment fragment = new ItemDialogFragment();
        mCode = code;
        mTitle = name;
        return fragment;
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

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        closeDialog = (ImageView) getDialog().findViewById(R.id.closeDialog);
        title = (TextView) getDialog().findViewById(R.id.dialogTitle);
        title.setText("Choose " + mTitle);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;

    }


    public void getAdapter() {
        Cursor cursor = databaseHelper.getItems(String.valueOf(mCode));
        if (cursor.moveToFirst()) {
            do {
                ItemResponse res = new ItemResponse();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String addon = cursor.getString(cursor.getColumnIndex("addon"));
                res.setPrice(price);
                res.setAddon(addon);
                res.setName(name);
                res.setId(id);
                liststring.add(res);
            } while (cursor.moveToNext());
        }

        try {

            gridAdapter = new ItemGridAdapter(getContext(), R.layout.gridview_custom_layout, liststring);
            gridView.setAdapter(gridAdapter);

        } catch (Exception e) {
            Log.e(TAG, "onResponse: " + e.getLocalizedMessage());
        }
    }
}
