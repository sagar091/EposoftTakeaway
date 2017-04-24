package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import Models.NewCartSubData;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Aman Singh on 10/6/2016.
 */

public class NewAddonRecyclerAdapter extends RecyclerView.Adapter<NewAddonRecyclerAdapter.CustomViewHolder> {

    public Context context;
    private LayoutInflater inflater;
    private List<NewCartSubData> cartSubDatas;
    private int num;
    private String TAG = NewAddonRecyclerAdapter.class.getSimpleName();
    public CartDatabaseHelper databaseHelper;


    public NewAddonRecyclerAdapter(Context context, List<NewCartSubData> newCartsubDatas, String num) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        cartSubDatas = newCartsubDatas;
        this.num = Integer.parseInt(num);
        databaseHelper = new CartDatabaseHelper(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.addon_items, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        if (!cartSubDatas.get(position).getItemid().equals("")){
            holder.params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.items_addons_layout.setLayoutParams(holder.params);
            holder.items_addons_layout.setVisibility(View.VISIBLE);

            String s = cartSubDatas.get(position).getSubext();
            if (s.contains("0"))
                s = "No";
            else if (s.contains("1"))
                s = "Less";
            else if (s.contains("2"))
                s = "Half";
            else if (s.contains("3"))
                s = "On";
            else if (s.contains("4"))
                s = "With";
            else if (s.contains("5"))
                s = "On Burger";
            else if (s.contains("6"))
                s = "On Chips";

            holder.addOnNameTextView.setText(String.format("%s %s", s, cartSubDatas.get(position).getSubname()));
            Double price = Double.valueOf(cartSubDatas.get(position).getSubprice()) * num;
            holder.addOnPriceTextView.setText(String.format("£ %s", new DecimalFormat("###.##").format(price)));
//            databaseHelper.updateCartSubItemsQty(String.valueOf(num), cartSubDatas.get(position).getItemid());
        }

    }

    @Override
    public int getItemCount() {
        return cartSubDatas.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView addOnNameTextView, addOnPriceTextView;
        LinearLayout items_addons_layout;
        ViewGroup.LayoutParams params;

        public CustomViewHolder(View itemView) {
            super(itemView);
            addOnNameTextView = (TextView) itemView.findViewById(R.id.addonNameTextView);
            addOnPriceTextView = (TextView) itemView.findViewById(R.id.addOnPriceTextView);
            items_addons_layout = (LinearLayout) itemView.findViewById(R.id.items_addons);
            items_addons_layout.setVisibility(View.GONE);
            params = items_addons_layout.getLayoutParams();
        }
    }
}
