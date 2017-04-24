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

import DatabaseHelper.CartDatabaseHelper;
import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.CartActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/29/2016.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CustomViewHolder> implements CartActivity.notifyRecycler, AddonRecycleviewAdapter.totalUpdate {

    private static final String TAG = CartAdapter.class.getSimpleName();
    public static Double sub_total;
    public List<String> cartItemName, cartItemPrice;
    public List<List<String>> listsubdata, listsubprice, listsubAddon;
    Context context;
    private List<String> subdata, subdataprice, addonlist;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public int no = 1;
    AddonRecycleviewAdapter recycleViewAdapter;
    private CartDatabaseHelper databaseHelper;
    private static Double total_price;
    private static Double addon_subtotal;
    public static Double temp;

    public CartAdapter(Context context, List<String> cartItemName, List<String> cartItemPrice,
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
        addon_subtotal = 0.0;

    }

    public CartAdapter(Context context) {
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
//        Log.e(TAG, "subListConverter: " + listsubAddon + "  " + listsubdata + "  " + listsubprice);
        printSubPrice();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder cartViewHolder = new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        try {
            holder.itemName.setText(String.format("%s", cartItemName.get(position)));
            holder.itemPrice.setText(String.format("£  %s", cartItemPrice.get(position)));
            total_price = total_price + Double.valueOf(cartItemPrice.get(position));
            Log.e(TAG, "onBind:total " + total_price + " " + addon_subtotal);
            Constants.total_price_count = total_price;
            Constants.item_count[position] = holder.totalNumber.getText().toString();
            holder.item_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    no = Integer.parseInt(holder.totalNumber.getText().toString());
                    no += 1;

                    holder.totalNumber.setText(String.format("%s", no));
                    Constants.item_count[holder.getAdapterPosition()] = holder.totalNumber.getText().toString();
                    String price = new DecimalFormat("###.##").format(Double.valueOf(cartItemPrice.get(holder.getAdapterPosition())) * no);
                    total_price = total_price + Double.valueOf(cartItemPrice.get(holder.getAdapterPosition()));

                    Constants.total_price_count = total_price;

                    Log.e(TAG, "onClick:in cart " + total_price + "  " + Constants.total_price_count);
                    holder.itemPrice.setText(String.format("£  %s", price));

                    sub_total = Double.valueOf(new DecimalFormat("###.###").format(total_price));
                    CartActivity.subTotalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.###").format(sub_total)));
                    Double total = Double.valueOf(new DecimalFormat("###.###").format(total_price + temp));
                    CartActivity.totalPriceTextView.setText(String.format("£  %s", total));

                    sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    Log.e(TAG, "onClick:in cart " + total_price + "  " + Constants.total_price_count + "  " + sharedPreferences.getString("subtotal", ""));


                    //  adapterTotal(String.valueOf(addon_subtotal));
                    recycleViewAdapter.notifyDataSetChanged();
                    synchronized (holder.addon_recycleview) {
                        recycleViewAdapter = new AddonRecycleviewAdapter(context, listsubdata.get(holder.getAdapterPosition()),
                                listsubprice.get(holder.getAdapterPosition()), listsubAddon.get(holder.getAdapterPosition()), holder.totalNumber.getText().toString());
                        holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
                        holder.addon_recycleview.setAdapter(recycleViewAdapter);
                    }
                }
            });

            holder.item_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    no = Integer.parseInt(holder.totalNumber.getText().toString());
                    no -= 1;
                    if (no <= 0) {
                        no = 1;
                    }
                    Constants.item_count[holder.getAdapterPosition()] = holder.totalNumber.getText().toString();
                    holder.totalNumber.setText(String.format("%s", no));
                    String price = new DecimalFormat("###.##").format(Double.valueOf(cartItemPrice.get(holder.getAdapterPosition())) * no);
                    total_price = total_price - (Double.valueOf(cartItemPrice.get(holder.getAdapterPosition())));
                    Log.e(TAG, "onClick:in cart " + total_price + " " + Constants.total_price_count);

                    Constants.total_price_count = total_price;
                    sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    Log.e(TAG, "onClick:in cart " + total_price + "  " + Constants.total_price_count + "  " + sharedPreferences.getString("subtotal", ""));


                    sub_total = Double.valueOf(new DecimalFormat("###.###").format(total_price));
                    CartActivity.subTotalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.###").format(sub_total)));
                    Double total = Double.valueOf(new DecimalFormat("###.###").format(total_price + temp));
                    CartActivity.totalPriceTextView.setText(String.format("£  %s", total));

                    //      adapterTotal(String.valueOf(addon_subtotal));

                    holder.itemPrice.setText(String.format("£  %s", price));
                    recycleViewAdapter.notifyDataSetChanged();
                    synchronized (holder.addon_recycleview) {
                        recycleViewAdapter = new AddonRecycleviewAdapter(context, listsubdata.get(holder.getAdapterPosition()),
                                listsubprice.get(holder.getAdapterPosition()), listsubAddon.get(holder.getAdapterPosition()), holder.totalNumber.getText().toString());
                        holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
                        holder.addon_recycleview.setAdapter(recycleViewAdapter);
                    }
                }
            });

            recycleViewAdapter = new AddonRecycleviewAdapter(context, listsubdata.get(position),
                    listsubprice.get(position), listsubAddon.get(position), holder.totalNumber.getText().toString());
            holder.addon_recycleview.setLayoutManager(new LinearLayoutManager(context));
            holder.addon_recycleview.setAdapter(recycleViewAdapter);

            sub_total = Double.valueOf(new DecimalFormat("###.###").format(total_price));
            CartActivity.subTotalPriceTextView.setText(String.format("£  %s", new DecimalFormat("###.###").format(sub_total)));
            Double total = Double.valueOf(new DecimalFormat("###.###").format(total_price + temp));
            CartActivity.totalPriceTextView.setText(String.format("£  %s", total));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartItemName.size();
    }

    @Override
    public void updateRecycler(int id) {
        printSubPrice();
        notifyDataSetChanged();
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
        temp = Double.valueOf(sharedPreferences.getString("bagprice", "0"));
        total = Double.valueOf(new DecimalFormat("###.###").format(sub_total + temp));
        CartActivity.totalPriceTextView.setText(String.format("£  %s", total));

        Log.e(TAG, "printSubPrice: " + total);
    }

    @Override
    public void adapterTotal(String tot) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,Context.MODE_PRIVATE);

        Constants.sub_total_price_count = addon_subtotal + Double.valueOf(tot);
        addon_subtotal = addon_subtotal + Double.valueOf(tot);
        Log.e(TAG, "adapterTotal: " + Double.valueOf(tot) + "  " + addon_subtotal + "  " + Constants.sub_total_price_count);
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
