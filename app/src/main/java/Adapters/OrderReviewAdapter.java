package Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Arrays;
import java.util.List;

import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Aman Singh on 9/29/2016.
 */

public class OrderReviewAdapter extends RecyclerView.Adapter<OrderReviewAdapter.CustomViewHolder> {

    private static final String TAG = CartAdapter.class.getSimpleName();
    public static Double sub_total = 0.0;
    public List<String> cartItemName, cartItemPrice;
    public List<List<String>> listsubdata, listsubprice, listsubAddon;
    Context context;
    private List<String> subdata, subdataprice, addonlist;
    public SharedPreferences sharedPreferences;
    public int no = 1;
    AddonRecycleviewAdapter recycleViewAdapter;
    public Double total_price;

    public OrderReviewAdapter(Context context, List<String> cartItemName, List<String> cartItemPrice,
                              List<String> subdata, List<String> subdataprice, List<String> addonlist) {
        this.context = context;
        this.cartItemPrice = cartItemPrice;
        this.cartItemName = cartItemName;
        this.subdata = subdata;
        this.subdataprice = subdataprice;
        this.addonlist = addonlist;
        listsubdata = new ArrayList<>();
        listsubprice = new ArrayList<>();
        listsubAddon = new ArrayList<>();
        subListConverter(cartItemName.size());
        total_price = 0.0;

    }

    public void subListConverter(int position) {
//        Log.e(TAG, "subdata size :" + subdata.size() + " price size:" + subdataprice.size());
        for (int i = 0; i < position; i++) {
//            Log.e(TAG, "subprice : " + subdataprice.toString() + " subdata " + subdata.toString());
            List<String> stringList = new ArrayList<>();
            List<String> strings = new ArrayList<>();
            List<String> tempaddon = new ArrayList<>();

            String string1 = subdataprice.get(i).substring(1, subdataprice.get(i).length() - 1);
            List<String> singleList1 = new ArrayList<String>(Arrays.asList(string1.split(",")));
//            Log.e(TAG, "subListConverter:up " + singleList1);
            if (!singleList1.isEmpty()) {
                for (String s1 : singleList1) {
                    strings.add(s1);
//                    Log.e(TAG, "subListConverter:in " + s1);
                }
            }
            listsubprice.add(strings);

            String string = subdata.get(i).substring(1, subdata.get(i).length() - 1);
            List<String> singleList = new ArrayList<String>(Arrays.asList(string.split(",")));
            for (String s : singleList) {
                stringList.add(s);
            }
            listsubdata.add(stringList);

            String string2 = addonlist.get(i).substring(1, addonlist.get(i).length() - 1);
            List<String> list = new ArrayList<>(Arrays.asList(string2.split(",")));
            for (String s : list) {
                tempaddon.add(s);
            }
            listsubAddon.add(tempaddon);
        }
        printSubPrice();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder cartViewHolder = new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        try {
//            Log.e(TAG, "onBindViewHolder: " + listsubdata.get(position).size());
            holder.itemName.setText(String.format("%s", cartItemName.get(position)));
            holder.totalNumber.setText(Constants.item_count[position]);

            String price = new DecimalFormat("###.##").format(Double.valueOf(cartItemPrice.get(position))
                    * Double.valueOf(holder.totalNumber.getText().toString()));
            holder.itemPrice.setText(String.format("£  %s", price));


            Log.e(TAG, "total Num : " + holder.totalNumber.getText().toString());
            recycleViewAdapter = new AddonRecycleviewAdapter(context, listsubdata.get(position),
                    listsubprice.get(position), listsubAddon.get(position), holder.totalNumber.getText().toString());
            holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
            holder.addon_recycleview.setAdapter(recycleViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return cartItemName.size();
    }


    public void printSubPrice() {
        Double total = 0.0;
        for (List<String> s : listsubprice) {
            for (String t : s) {
                if (!t.equals(""))
                    total = total + Double.parseDouble(t);
            }
        }
        total = Double.valueOf(new DecimalFormat("###.##").format(total));
        total = CartActivity.items_total + total;
        sub_total = Double.valueOf(new DecimalFormat("###.###").format(total));
        CartActivity.subTotalPriceTextView.setText(String.format("£  %s", sub_total));
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Double temp = Double.valueOf(sharedPreferences.getString("bagprice", "0"));
        total = Double.valueOf(new DecimalFormat("###.###").format(sub_total + temp));

        CartActivity.totalPriceTextView.setText(String.format("£  %s", total));

//        CartActivity.totalPriceTextView.setText("");
        Log.e(TAG, "printSubPrice: " + total);
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
}
