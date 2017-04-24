package Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Models.NewCartData;
import Models.NewCartSubData;
import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Aman Singh on 10/6/2016.
 */

public class NewCartAdapter extends RecyclerView.Adapter<NewCartAdapter.CustomViewHolder> {

    private Context context;
    public int no = 1;
    public List<NewCartData> cartDatas;
    private List<NewCartData> cartDataList;
    private CartDatabaseHelper databaseHelper;
    private List<NewCartSubData> newCartsubDatas;
    public NewAddonRecyclerAdapter addonRecyclerAdapter;
    private String TAG = NewCartAdapter.class.getSimpleName();
    public SharedPreferences sharedPreferences;
    Double bagcharge;
    Double total_getprice;


    public NewCartAdapter(Context context, List<NewCartData> newCartDatas) {
        this.context = context;
        cartDatas = newCartDatas;
        databaseHelper = new CartDatabaseHelper(context);
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        bagcharge = Double.valueOf(sharedPreferences.getString("bagprice", "0.0"));
        total_getprice = databaseHelper.getTotalPrice();
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        holder.itemName.setText(String.format("%s", cartDatas.get(position).getItemname()));
        Double total = Double.valueOf(cartDatas.get(position).getPrice()) * Double.valueOf(cartDatas.get(position).getQty());
        holder.itemPrice.setText(String.format("£  %s", new DecimalFormat("###.##").format(total)));
        holder.totalNumber.setText(String.format("%s", cartDatas.get(position).getQty()));

        newCartsubDatas = getNewCartData(cartDatas.get(position).getItemid());
        addonRecyclerAdapter = new NewAddonRecyclerAdapter(context, newCartsubDatas, holder.totalNumber.getText().toString());
        holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
        holder.addon_recycleview.setAdapter(addonRecyclerAdapter);

        String tot = new DecimalFormat("###.##").format(total_getprice);
        CartActivity.subTotalPriceTextView.setText(String.format("£  %s", tot));
        Double total1 = Double.valueOf(tot) + bagcharge;
        CartActivity.totalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.##").format(total1)));

        final CustomViewHolder newHolder = holder;
        final int newPosition = position;
        holder.item_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no = Integer.parseInt(newHolder.totalNumber.getText().toString());
                no += 1;
                newHolder.totalNumber.setText(String.format("%s", no));
                //Todo - update total in cartItems

                databaseHelper.updateCartItemsQty(String.valueOf(no), cartDatas.get(newPosition).getItemid());

                cartDataList = getMainCartData();
                Double total = Double.valueOf(cartDatas.get(newPosition).getPrice()) * Double.valueOf(cartDataList.get(newPosition).getQty());
                newHolder.itemPrice.setText(String.format("£  %s", new DecimalFormat("###.##").format(total)));

                newCartsubDatas = getNewCartData(cartDataList.get(holder.getAdapterPosition()).getItemid());

                synchronized (newHolder.addon_recycleview) {
                    NewAddonRecyclerAdapter addonRecyclerAdapter = new NewAddonRecyclerAdapter(context,
                            newCartsubDatas, newHolder.totalNumber.getText().toString());
                    newHolder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
                    newHolder.addon_recycleview.setAdapter(addonRecyclerAdapter);
                }

                total_getprice = databaseHelper.getTotalPrice();
                String tota = new DecimalFormat("###.##").format(total_getprice);
                CartActivity.subTotalPriceTextView.setText(String.format("£  %s", tota));
                Double total1 = Double.valueOf(tota) + bagcharge;
                CartActivity.totalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.##").format(total1)));
            }
        });

        holder.item_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no = Integer.parseInt(newHolder.totalNumber.getText().toString());
                no -= 1;
                if (no <= 0) {
                    no = 1;
                }
                //Todo - update total in cartItems
                newHolder.totalNumber.setText(String.format("%s", no));

                databaseHelper.updateCartItemsQty(String.valueOf(no), cartDatas.get(newPosition).getItemid());

                cartDataList = getMainCartData();
                Double total = Double.valueOf(cartDatas.get(newPosition).getPrice()) * Double.valueOf(cartDataList.get(newPosition).getQty());

                newHolder.itemPrice.setText(String.format("£  %s", new DecimalFormat("###.##").format(total)));


                newCartsubDatas = getNewCartData(cartDataList.get(holder.getAdapterPosition()).getItemid());


                synchronized (newHolder.addon_recycleview) {
                    NewAddonRecyclerAdapter addonRecyclerAdapter = new NewAddonRecyclerAdapter(context,
                            newCartsubDatas, newHolder.totalNumber.getText().toString());
                    newHolder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
                    newHolder.addon_recycleview.setAdapter(addonRecyclerAdapter);
                }

                total_getprice = databaseHelper.getTotalPrice();
                String tota = new DecimalFormat("###.##").format(total_getprice);
                CartActivity.subTotalPriceTextView.setText(String.format("£  %s", tota));
                Double total1 = Double.valueOf(tota) + bagcharge;
                CartActivity.totalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.##").format(total1)));
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartDatas.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        public TextView totalNumber;
        ImageButton btnremove, item_minus, item_plus;
        CardView menuHolder;
        LinearLayout addOnItemHolder;
        RecyclerView addon_recycleview;

        public CustomViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            btnremove = (ImageButton) itemView.findViewById(R.id.btnremove);
            menuHolder = (CardView) itemView.findViewById(R.id.menuHolder);
            addOnItemHolder = (LinearLayout) itemView.findViewById(R.id.addOnHolder);
            addon_recycleview = (RecyclerView) itemView.findViewById(R.id.addon_recycleview);
            item_minus = (ImageButton) itemView.findViewById(R.id.item_minus);
            item_plus = (ImageButton) itemView.findViewById(R.id.item_plus);
            totalNumber = (TextView) itemView.findViewById(R.id.totalNumber);
        }
    }

    public List<NewCartSubData> getNewCartData(String pos) {
        List<NewCartSubData> newCartData = new ArrayList<>();
        Cursor cursor = databaseHelper.getCartSubItems(pos);
        if (cursor.moveToFirst()) {
            do {
                NewCartSubData cartData = new NewCartSubData();
                String itemid = cursor.getString(cursor.getColumnIndex("itemid"));
                String subname = cursor.getString(cursor.getColumnIndex("subname"));
                String subprice = cursor.getString(cursor.getColumnIndex("subprice"));
                String subid = cursor.getString(cursor.getColumnIndex("subid"));
                String subqty = cursor.getString(cursor.getColumnIndex("subqty"));
                String subtype = cursor.getString(cursor.getColumnIndex("subtype"));
                String subext = cursor.getString(cursor.getColumnIndex("subext"));
                cartData.setItemid(itemid);
                cartData.setSubext(subext);
                cartData.setSubid(subid);
                cartData.setSubname(subname);
                cartData.setSubprice(subprice);
                cartData.setSubqty(subqty);
                cartData.setSubtype(subtype);
                newCartData.add(cartData);
                Log.e(TAG, "getNewCartData: " + subqty + "  " + subprice);
            } while (cursor.moveToNext());
        }
        return newCartData;
    }

    public List<NewCartData> getMainCartData() {
        List<NewCartData> newCartDatas = new ArrayList<>();
        Cursor cursor = databaseHelper.getCartItems();
        if (cursor.moveToFirst()) {
            do {
                NewCartData cartData = new NewCartData();
                String name = cursor.getString(cursor.getColumnIndex("itemname"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String id = cursor.getString(cursor.getColumnIndex("itemid"));
                String qty = cursor.getString(cursor.getColumnIndex("qty"));
                cartData.setItemid(id);
                cartData.setItemname(name);
                cartData.setPrice(price);
                cartData.setQty(qty);
                newCartDatas.add(cartData);
            } while (cursor.moveToNext());
        }
        return newCartDatas;
    }
}
