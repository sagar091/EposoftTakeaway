package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import FragmentDialogs.SubcategoryFragment;
import Models.CategoryResponse;
import Models.Coupon;
import Models.PlaceOrder.Item;
import Models.PlaceOrder.MakeOrder;
import Models.PlaceOrder.Sub;
import Models.PlaceOrder.User;
import Models.SandageResponse;
import Models.TableResponse;
import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

import static android.content.Intent.ACTION_CALL;

/**
 * Created by Mobo on 8/19/2016.
 */

public class TableGridViewAdapter  extends ArrayAdapter{

    private static final String TAG = TableGridViewAdapter.class.getSimpleName();
    public String[] allColors;
    public Context context;
    private int layoutResourceId;
    private List<TableResponse> mGridData;
    private CartDatabaseHelper databaseHelper;
    ArrayList<Item> orderItemList;



    public TableGridViewAdapter(Context context, int layoutResourceId, List<TableResponse> mGridData) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;
        databaseHelper = new CartDatabaseHelper(context);
        orderItemList = new ArrayList<>();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        TableGridViewAdapter.ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new TableGridViewAdapter.ViewHolder();
            holder.imageTitle = (TextView) view.findViewById(R.id.gridview_text);
            holder.layout = (LinearLayout) view.findViewById(R.id.linearLayout);
            view.setTag(holder);
        } else {

            holder = (TableGridViewAdapter.ViewHolder) view.getTag();
        }
        try {
            allColors = context.getResources().getStringArray(R.array.rainbow);
            final TableResponse item = mGridData.get(position);

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setText(item.getTablename());
            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CartActivity.class);
                    intent.setFlags(1);
                    intent.putExtra("table",item.getTablename());
                    context.startActivity(intent);
//                    getItemsForOrder(item.getTablename());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    static class ViewHolder {
        TextView imageTitle;
        LinearLayout layout;
    }


}

