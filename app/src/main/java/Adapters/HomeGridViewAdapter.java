package Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import FragmentDialogs.ItemDialogFragment;
import FragmentDialogs.SubcategoryFragment;
import Models.CategoryResponse;
import Models.SubcategoryResponse;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/25/2016.
 */
public class HomeGridViewAdapter extends ArrayAdapter {
    private static final String TAG = HomeGridViewAdapter.class.getSimpleName();
    public String[] allColors;
    public FragmentManager fragmentManager;
    CartDatabaseHelper databaseHelper;
    List<SubcategoryResponse> liststring;
    private Context context;
    private int layoutResourceId;
    private List<CategoryResponse> mGridData;// = new ArrayList<>();

    public HomeGridViewAdapter(Context context, int layoutResourceId, List<CategoryResponse> mGridData) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;
        databaseHelper = new CartDatabaseHelper(context);
        liststring = new ArrayList<>();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        //  int[] rainbow = context.getResources().getIntArray(R.array.rainbow);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) view.findViewById(R.id.gridview_text);
            holder.layout = (LinearLayout) view.findViewById(R.id.linearLayout);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        try {
            allColors = context.getResources().getStringArray(R.array.rainbow);
//        Log.e(TAG, "getView: "+allColors[position] );
            final CategoryResponse item = mGridData.get(position);
            holder.imageTitle.setText(item.getName());

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick:id " + item.getId());
//                    Toast.makeText(context, item.getId()+ " " + position, Toast.LENGTH_LONG).show();

                    getAdapter(item);


                }
            });

//            holder.imageTitle.setBackgroundColor(Color.parseColor(allColors[position]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void getAdapter(CategoryResponse item) {
        Cursor cursor = databaseHelper.getSubCategory(item.getId());
        liststring = new ArrayList<>();
        liststring.clear();
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

        if (liststring.size() == 1) {
            FragmentManager fragmentManager =((FragmentActivity) context).getSupportFragmentManager();
            ItemDialogFragment postalFragment = ItemDialogFragment.newInstance(Integer.valueOf(liststring.get(0).getId()), liststring.get(0).getName());
            postalFragment.show(fragmentManager, "SubcategoryFragment");
        } else {
            try {
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                SubcategoryFragment postalFragment = SubcategoryFragment.newInstance(Integer.valueOf(item.getId()), item.getName());
                postalFragment.show(fragmentManager, "SubcategoryFragment");
            } catch (Exception e) {

            }
        }

//        Log.e(TAG, "getAdapter: " + databaseHelper.getCategotyCountCheck());

    }



    static class ViewHolder {
        TextView imageTitle;
        LinearLayout layout;
    }

}