package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import Utils.Constants;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 8/1/2016.
 */
public class AddonRecycleviewAdapter extends RecyclerView.Adapter<AddonRecycleviewAdapter.CustomViewHolder> {
    private static final String TAG = AddonRecycleviewAdapter.class.getSimpleName();
    Context context;
    private LayoutInflater inflater;
    private List<String> data, price, addon;
    private String numb;
    private static Double total_price=0.0;
    private CartAdapter cartAdapter;
    private SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    public AddonRecycleviewAdapter(Context context, List<String> data, List<String> price, List<String> addon, String numb) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.price = price;
        this.addon = addon;
        this.numb = numb;
        cartAdapter = new CartAdapter(context);
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.addon_items, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        Log.e(TAG, "onBindViewHolder: "+position+" "+data.size()+" "+price.size() );
        if (data.get(position).length() > 1) {
            holder.params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.items_addons_layout.setLayoutParams(holder.params);
            holder.items_addons_layout.setVisibility(View.VISIBLE);
            holder.addOnNameTextView.setText(String.format("%s %s", addon.get(position), data.get(position)));
            String total = new DecimalFormat("###.##").format(Double.valueOf(price.get(position)) * Double.valueOf(numb));

            total_price = total_price + Double.valueOf(total);

            sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            if (total_price > 0) {
                editor = sharedPreferences.edit();
                editor.putString("subtotal", String.valueOf(total_price));
                editor.commit();

                if (position == price.size() - 1) {
//                    String s = sharedPreferences.getString("subtotal","");
                    cartAdapter.adapterTotal(new DecimalFormat("###.##").format(total_price));
                    total_price=0.0;
                }
            }
//            Log.e(TAG, "onBindViewHolder: " + total_price);
            holder.addOnPriceTextView.setText(String.format("£  %s", total));
        } else {

            holder.params.height = 1;
            holder.params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.items_addons_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    public interface totalUpdate {
        void adapterTotal(String tot);
    }

}
