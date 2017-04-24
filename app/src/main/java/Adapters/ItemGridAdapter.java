package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import DatabaseHelper.CartDatabaseHelper;
import FragmentDialogs.AddonDialogFragment;
import Models.ItemResponse;
import uk.co.eposoft.eposofttakeaway.HomeMainActivity;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/26/2016.
 */
public class ItemGridAdapter extends ArrayAdapter {
    private static final String TAG = ItemGridAdapter.class.getSimpleName();
    public LayoutInflater inflater;
    public List<ItemResponse> listItemResponse;
    public FragmentManager fragmentManager;
    public String[] allColors;
    public HashMap<String, List<String>> hashMap;
    public CartDatabaseHelper databaseHelper;
    Context context;
    String itemId;
    List<String> listItems;
    private int layoutResourceId;


    public ItemGridAdapter(Context context, int gridview_custom_layout, List<ItemResponse> liststring) {
        super(context, gridview_custom_layout, liststring);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listItemResponse = liststring;
        layoutResourceId = gridview_custom_layout;
        hashMap = new HashMap<>();
        databaseHelper = new CartDatabaseHelper(getContext());
    }

    private static void dismissAllDialogs(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismissAllowingStateLoss();
            }
            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            if (childFragmentManager != null)
                dismissAllDialogs(childFragmentManager);
        }
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
            final ItemResponse item = listItemResponse.get(position);
            holder.imageTitle.setText(item.getName());
            final ViewHolder finalHolder = holder;

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        Toast.makeText(context, item.getId() + " item " + item.getName(), Toast.LENGTH_LONG).show();
                        finalHolder.imageTitle.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_primary_trans));
                        finalHolder.imageTitle.setEnabled(false);
                        Log.e(TAG, "onClick:item " + item.getName() + " " + item.getId());
                        if (!item.getAddon().equals("0")) {
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            try {
                                dismissAllDialogs(fragmentManager); // Todo : changes occured here
                            } catch (Exception e) {
                            }
                            AddonDialogFragment postalFragment = AddonDialogFragment.newInstance(
                                    Integer.valueOf(item.getAddon()), item.getName(), item.getId(), item.getPrice());
                            postalFragment.show(fragmentManager, "AddonDialogFragment");
                        }
                        if (item.getAddon().equals("0")) {
                            if (databaseHelper.searchCartItems(item.getId()) > 0) {
                                Log.e(TAG, "search total: " + databaseHelper.searchCartItems(item.getId()));
                                int i = databaseHelper.searchCartItems(item.getId());
                                databaseHelper.updateCartItemsQty(String.valueOf(i + 1), item.getId());
                            } else
                                databaseHelper.setCartItems(item.getName(), item.getPrice(), item.getId(), "1");
                            try {
                                dismissAllDialogs(fragmentManager); // Todo : changes occured here
                            } catch (Exception e) {
                            }
                            Snackbar.make(HomeMainActivity.home_main_layout, "Item(s) added in your cart", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: " + e.getLocalizedMessage());
                    }
                }
            });


//            holder.imageTitle.setBackgroundColor(Color.parseColor(allColors[position]));
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
