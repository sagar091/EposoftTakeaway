package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Models.allDataAtOne.Change;
import Models.allDataAtOne.Extra;
import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Admin768 on 11/7/2016.
 */

public class SaucesAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    List<Change> changeList;
    String[] allColors;
    private static final String TAG = SaucesAdapter.class.getSimpleName();
    CartDatabaseHelper databaseHelper;


    public SaucesAdapter(Context context, int resource, List<Change> changeList) {
        super(context, resource, changeList);
        this.context = context;
        this.layoutResourceId = resource;
        this.changeList = changeList;
        databaseHelper = new CartDatabaseHelper(context);

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SaucesAdapter.ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new SaucesAdapter.ViewHolder();
            holder.imageTitle = (TextView) view.findViewById(R.id.gridview_text);
            view.setTag(holder);
        } else {
            holder = (SaucesAdapter.ViewHolder) view.getTag();
        }

        try {

            allColors = context.getResources().getStringArray(R.array.rainbow);
            final Change item = changeList.get(position);

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setText(item.getName());
            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.setCartItems(item.getName(), item.getPrice(), item.getId() , "1");
                    Toast.makeText(context, item.getName() + " Added in cart", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "getView: " + e.getLocalizedMessage());
        }


        return view;
    }

    static class ViewHolder {
        TextView imageTitle;
    }
}
