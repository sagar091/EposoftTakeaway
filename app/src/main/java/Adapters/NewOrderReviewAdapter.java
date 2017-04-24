package Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Aman Singh on 10/11/2016.
 */

public class NewOrderReviewAdapter extends RecyclerView.Adapter<NewOrderReviewAdapter.CustomViewHolder> {

    private Context context;
    public int no = 1;
    public List<NewCartData> cartDatas;
    private List<NewCartData> cartDataList;
    private CartDatabaseHelper databaseHelper;
    public List<NewCartSubData> newCartsubDatas;
    public NewAddonRecyclerAdapter addonRecyclerAdapter;
    private String TAG = NewOrderReviewAdapter.class.getSimpleName();

    public NewOrderReviewAdapter(Context context, List<NewCartData> newCartDatas) {
        this.context = context;
        cartDatas = newCartDatas;
        databaseHelper = new CartDatabaseHelper(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder cartViewHolder = new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.itemName.setText(String.format("%s", cartDatas.get(position).getItemname()));
        Double total = Double.valueOf(cartDatas.get(position).getPrice()) * Double.valueOf(cartDatas.get(position).getQty());
        holder.itemPrice.setText(String.format("£  %s", new DecimalFormat("###.##").format(total)));
        holder.totalNumber.setText(String.format("%s", cartDatas.get(position).getQty()));
        holder.item_minus.setVisibility(View.INVISIBLE);
        holder.item_plus.setVisibility(View.INVISIBLE);

        newCartsubDatas = getNewCartData(cartDatas.get(position).getItemid());
        addonRecyclerAdapter = new NewAddonRecyclerAdapter(context, newCartsubDatas, holder.totalNumber.getText().toString());
        holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
        holder.addon_recycleview.setAdapter(addonRecyclerAdapter);

        CartActivity.subTotalPriceTextView.setText(String.format("£  %s",databaseHelper.getTotalPrice()));
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
