package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import java.util.List;

import FragmentDialogs.AddonDialogFragment;
import Models.Addons.AddonItem;
import Models.CartItem;
import uk.co.eposoft.eposofttakeaway.R;

/**
 * Created by Mobo on 7/26/2016.
 */
public class AddonAdapter extends ArrayAdapter {
    private static final String TAG = AddonAdapter.class.getSimpleName();
    public LayoutInflater inflater;
    private List<AddonItem> liststring;
    public FragmentManager fragmentManager;
    String[] allColors;
    Context context;
    private int layoutResourceId;

    public AddonAdapter(Context context, int gridview_custom_layout, List<AddonItem> liststring) {
        super(context, gridview_custom_layout, liststring);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.liststring = liststring;
        layoutResourceId = gridview_custom_layout;
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

    @NonNull
    @Override
    public View getView(final int position, final View convertView,@NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) view.findViewById(R.id.gridview_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        try {
            allColors = context.getResources().getStringArray(R.array.rainbow);
            final AddonItem item = liststring.get(position);
            holder.imageTitle.setText(item.getName());
            final ViewHolder finalHolder = holder;
            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));

            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finalHolder.imageTitle.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_primary_trans));
                        finalHolder.imageTitle.setEnabled(false);
                        Log.e(TAG, "onClick: " + item.getName());
                        if (!item.getNext().equals("0")) {
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            dismissAllDialogs(fragmentManager);
                            AddonDialogFragment postalFragment = AddonDialogFragment.newInstance(Integer.valueOf(item.getNext()));
                            postalFragment.show(fragmentManager, "AddonDialogFragment");
                        }
                        AddonDialogFragment dialogFragment = new AddonDialogFragment();
                        dialogFragment.getItemFromAddon(item.getName(), item.getId(), item.getPrice());

                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getLocalizedMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public interface addItemsInCart {
        void getItemFromAddon(String addonName, String addonId, String addonPrice);
    }

    static class ViewHolder {
        TextView imageTitle;
//        LinearLayout layout;
    }
}