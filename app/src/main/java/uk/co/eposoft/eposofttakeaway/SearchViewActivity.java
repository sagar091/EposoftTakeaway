package uk.co.eposoft.eposofttakeaway;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import Adapters.ItemGridAdapter;
import DatabaseHelper.CartDatabaseHelper;
import Models.ItemResponse;

public class SearchViewActivity extends AppCompatActivity {

    private static final String TAG = SearchViewActivity.class.getSimpleName();
    MaterialSearchView searchView;
    Toolbar toolbar;
    ItemGridAdapter gridAdapter;
    GridView gridView;
    List<ItemResponse> searchlist;
    CartDatabaseHelper databaseHelper;
    List<ItemResponse> itemResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("Search");

        itemResponses = new ArrayList<>();
        databaseHelper = new CartDatabaseHelper(getApplicationContext());
        gridView = (GridView) findViewById(R.id.search_sub_grid);
        searchlist = new ArrayList<>();


        searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.showSearch();
        searchView.showVoice(false);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchlist.clear();
                for (ItemResponse item : itemResponses) {
                    String name = item.getName();
                    if (name.toLowerCase().contains(query.toLowerCase())) {
                        searchlist.add(item);
                    }
                }
                gridAdapter = new ItemGridAdapter(SearchViewActivity.this, R.layout.gridview_custom_layout, searchlist);
                gridView.setAdapter(gridAdapter);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                searchlist.clear();
                for (ItemResponse item : itemResponses) {
                    String name = item.getName();
                    if (name.toLowerCase().contains(newText.toLowerCase())) {
                        searchlist.add(item);
                    }
                }
                gridAdapter = new ItemGridAdapter(SearchViewActivity.this, R.layout.gridview_custom_layout, searchlist);
                gridView.setAdapter(gridAdapter);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                getSearchItems();
            }

            @Override
            public void onSearchViewClosed() {
//                searchView.showSearch();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.mysearch_bar);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            Intent intent = new Intent(SearchViewActivity.this, HomeMainActivity.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }
    }

    public void getSearchItems() {
//      databaseHelper.getSearchItems();

        try {
            Cursor cursor = databaseHelper.getItems();
            if (cursor.moveToFirst()) {
                do {
                    ItemResponse response = new ItemResponse();
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    String addon = cursor.getString(cursor.getColumnIndex("addon"));
                    response.setAddon(addon);
                    response.setPrice(price);
                    response.setName(name);
                    response.setId(id);
                    itemResponses.add(response);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, "onActivityCreated:exc " + e.getLocalizedMessage());
        }
        gridAdapter = new ItemGridAdapter(SearchViewActivity.this, R.layout.gridview_custom_layout, itemResponses);
        gridView.setAdapter(gridAdapter);
    }
}
