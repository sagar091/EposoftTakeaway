package Utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import Fragments.CallLogsFragment;
import Fragments.ContactusFragment;
import Fragments.HomeFragment;
import Fragments.ManageFragment;
import Fragments.OrdersFragment;
import Fragments.SyncFragment;
import Fragments.TablesFragment;
import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Mobo on 7/21/2016.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public static Drawer result;
    Toolbar toolbar;
    public AccountHeader headerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("Eposoft");


         headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
//        headerResult.addProfiles(new ProfileDrawerItem().withName("Eposoft").withEmail("contactus@eposoft.co.uk")
//                .withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .inflateMenu(R.menu.activity_main_drawer)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager fragmentManager;
                        Fragment fragment;
                        switch (position) {
                            case 1:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new HomeFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                toolbar.setTitle("Home");
                                break;

                            case 2:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new CallLogsFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                toolbar.setTitle("Call Logs");
                                break;

                            case 3:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new OrdersFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                toolbar.setTitle("Order");
                                break;

                            case 4:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new ManageFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                toolbar.setTitle("Manager");
                                break;

                            case 5:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new TablesFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                toolbar.setTitle("Tables");
                                break;

                            case 7:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new SyncFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                break;

                            case 8:
                                fragmentManager = getSupportFragmentManager();
                                fragment = new ContactusFragment();
                                fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                                break;

                            case 9:
                                rateUs();
                                break;

                        }
                       // Toast.makeText(getApplicationContext(), ((Nameable) drawerItem).getName().getText(BaseActivity.this), Toast.LENGTH_LONG).show();

                        return false;
                    }
                })
                .build();

        result.addItem(new DividerDrawerItem());
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("Powered By Eposoft").withIcon(R.mipmap.ic_launcher));

    }

    public void rateUs() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}
