package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
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
import Models.OrdersList.GetOrderResponse;
import Models.PlaceOrder.Item;
import Models.TableResponse;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;
import uk.co.eposoft.eposofttakeaway.ViewBillActivity;

/**
 * Created by Admin768 on 11/14/2016.
 */

public class TableOrderGridViewAdapter  extends ArrayAdapter {

    private static final String TAG = TableOrderGridViewAdapter.class.getSimpleName();
    public String[] allColors;
    public Context context;
    private int layoutResourceId;
    private List<TableResponse> mGridData;
    CartDatabaseHelper databaseHelper;
    ArrayList<Item> orderItemList;
    List<GetOrderResponse> listResponse;

    public TableOrderGridViewAdapter(Context context, int layoutResourceId, List<TableResponse> mGridData,
                                     List<GetOrderResponse> listResponse) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;
        databaseHelper = new CartDatabaseHelper(context);
        orderItemList = new ArrayList<>();
        this.listResponse = listResponse;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView,@NonNull ViewGroup parent) {
        View view = convertView;
        final TableGridViewAdapter.ViewHolder holder;

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
            holder.imageTitle.setText(item.getTablename());



            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));


          for (int i=0;i<listResponse.size();i++) {
              if (listResponse.get(i).getTable().getTablename().equals(item.getTablename())) {
                  holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
                  drawable = (GradientDrawable) holder.imageTitle.getBackground();
                  drawable.setColor(Color.parseColor("#65727f"));
//                  holder.imageTitle.setText(listResponse.get(i).getTable().getTablename()+" "+listResponse.get(i).getOrder().getId());
                  final int x=i;
                  holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent = new Intent(context, ViewBillActivity.class);
                          intent.setFlags(2);
                          intent.putExtra("billid", listResponse.get(x).getOrder().getId());
                          intent.putExtra("tablename",item.getTablename());
                          context.startActivity(intent);
                          Toast.makeText(context, item.getTablename()+ listResponse.get(x).getOrder().getId()+" clicked ",Toast.LENGTH_LONG).show();
                      }
                  });

              }
          }
//            holder.imageTitle.setText(item.getTablename());

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
