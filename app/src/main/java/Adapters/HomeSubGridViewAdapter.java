package Adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import FragmentDialogs.ItemDialogFragment;
import Models.SubcategoryResponse;
import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Mobo on 7/26/2016.
 */
public class HomeSubGridViewAdapter extends ArrayAdapter {
    private static final String TAG = HomeGridViewAdapter.class.getSimpleName();
    String[] allColors;
    private Context context;
    private int layoutResourceId;
    private List<SubcategoryResponse> mGridData;// = new ArrayList<>();
    private android.support.v4.app.FragmentManager fragmentManager;


    public HomeSubGridViewAdapter(Context context, int layoutResourceId, List<SubcategoryResponse> mGridData) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;



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
//            holder.layout = (LinearLayout) view.findViewById(R.id.linearLayout);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        try {
            allColors = context.getResources().getStringArray(R.array.rainbow);
//        Log.e(TAG, "getView: "+allColors[position] );
            final SubcategoryResponse item = mGridData.get(position);
            holder.imageTitle.setText(item.getName());
            final ViewHolder finalHolder = holder;

            holder.imageTitle.setBackgroundResource(R.drawable.round_padding);
            GradientDrawable drawable = (GradientDrawable) holder.imageTitle.getBackground();
            drawable.setColor(Color.parseColor(allColors[position]));


            holder.imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "" + position, Toast.LENGTH_LONG).show();
                    Log.e(TAG, "item name: " + item.getName() + "  " + item.getId());
                    try {
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        dismissAllDialogs(fragmentManager);
                        ItemDialogFragment postalFragment = ItemDialogFragment.newInstance(Integer.valueOf(item.getId()), item.getName());
                        postalFragment.show(fragmentManager, "SubcategoryFragment");

                    } catch (Exception e) {
                        Log.e(TAG, "onClick:excep " + e.getLocalizedMessage());
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
//        LinearLayout layout;
    }
}