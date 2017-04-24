package DatabaseHelper;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mobo on 5/30/2016.
 */
public class CartDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = CartDatabaseHelper.class.getSimpleName();
    private static final String FILE_DIR = "Eposoft System";
    private static String DATABASE_NAME = "eposoft.db";
    private static int DATABASE_VERSION = 3;

    private SQLiteDatabase sqLiteDatabase;

    public CartDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);

        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS cartItem(headname varchar, headprice varchar, itemName varchar, price varchar, " +
                "itemid varchar,addontype varchar, subheadid varchar)";

        String cart = "CREATE TABLE IF NOT EXISTS cartitems (itemname varchar, price varchar, itemid varchar, qty varchar)";
        String cartaddons = "CREATE TABLE IF NOT EXISTS cartsubitems (itemid varchar,subname varchar, subprice varchar," +
                " subid varchar, subqty varchar, subtype varchar, subext varchar)";

        //Todo fot order details fragment tab

        String orders = "CREATE TABLE IF NOT EXISTS orderresponse(orderitem varchar, " +
                "orderuser varchar, orderitems varchar, stafftable varchar,orderuserlist varchar)";

//        String query1 = "CREATE TABLE IF NOT EXISTS totalprice(price INTEGER)";
        String query6 = "CREATE TABLE IF NOT EXISTS addons(addonName varchar, addonPrice varchar, addonId varchar, hashmapHead varchar)";


//        String query4 = "CREATE TABLE IF NOT EXISTS table2(item varchar, price varchar)";
//        String query5 = "CREATE TABLE IF NOT EXISTS table3(subheader varchar, subheaderitems varchar)";

        String query4 = "CREATE TABLE IF NOT EXISTS category(name varchar, id varchar primary key)";
        String query5 = "CREATE TABLE IF NOT EXISTS subcategory(name varchar, id varchar primary key, code varchar)";

        String query7 = "CREATE TABLE IF NOT EXISTS items(name varchar, id varchar primary key, price varchar," +
                " addon varchar, code varchar)";

        String query8 = "CREATE TABLE IF NOT EXISTS addonsItems(id varchar primary key," +
                " limitaddon varchar, next varchar, desc varchar, specialAddon varchar)";

        String query9 = "CREATE TABLE IF NOT EXISTS addonItemsList(id varchar," +
                " aid varchar, name varchar, price varchar, next varchar, PRIMARY KEY (id, aid, next))";

        String tables = "CREATE TABLE IF NOT EXISTS tables (tablename varchar primary key)";
        String staff = "CREATE TABLE IF NOT EXISTS stafftable (action varchar, id varchar primary key, name varchar, display varchar)";
        String coupon = "CREATE TABLE IF NOT EXISTS couponlist (id varchar, name varchar, code varchar, discount varchar, type varchar," +
                " minimum varchar, from_date varchar, to_date varchar, working_days varchar, status varchar)";

        String offorder = "CREATE TABLE IF NOT EXISTS maketheorder(myorder varchar)";

        String extra = "CREATE TABLE IF NOT EXISTS extra(id varchar, name varchar, price varchar, status varchar)";
        String changes = "CREATE TABLE IF NOT EXISTS changes(id varchar, name varchar, price varchar, status varchar)";

        String driver = "CREATE TABLE IF NOT EXISTS driver(id varchar," +
                " name varchar, empid varchar, vtype varchar, vno varchar, cno varchar, status varchar)";

        db.execSQL(driver);
        db.execSQL(extra);
        db.execSQL(changes);
        db.execSQL(orders);
        db.execSQL(offorder);
        db.execSQL(cart);
        db.execSQL(cartaddons);
        db.execSQL(coupon);
        db.execSQL(staff);
        db.execSQL(tables);
//        db.execSQL(search);
        db.execSQL(query6);
        db.execSQL(query7);
        db.execSQL(query8);
        db.execSQL(query9);
        db.execSQL(query);
//        db.execSQL(query1);
        db.execSQL(query4);
        db.execSQL(query5);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS cartItem";
//        String query1 = "DROP TABLE IF EXISTS totalprice";

//        String query4 = "DROP TABLE IF EXISTS table2";

        String query4 = "DROP TABLE IF EXISTS category";
        String query5 = "DROP TABLE IF EXISTS subcategory";
        String query7 = "DROP TABLE IF EXISTS items";
        String query8 = "DROP TABLE IF EXISTS addonsItems";
        String query9 = "DROP TABLE IF EXISTS addonItemsList";
//        String query5 = "DROP TABLE IF EXISTS table3";
        String query6 = "DROP TABLE IF EXISTS addons";
        String tables = "DROP TABLE IF EXISTS tables";
        String staff = "DROP TABLE IF EXISTS stafftable";
        String coupon = "DROP TABLE IF EXISTS couponlist";

        String cart = "DROP TABLE IF EXISTS cartitems";
        String cartaddons = "DROP TABLE IF EXISTS cartsubitems";
        String oforder = "DROP TABLE IF EXISTS maketheorder";

        String extra = "DROP TABLE IF EXISTS extra";
        String changes = "DROP TABLE IF EXISTS changes";
        String driver = "DROP TABLE IF EXISTS driver";


        db.execSQL(driver);

        db.execSQL(extra);
        db.execSQL(changes);

        db.execSQL(oforder);
        db.execSQL(cart);
        db.execSQL(cartaddons);
        db.execSQL(coupon);
        db.execSQL(staff);
        db.execSQL(tables);
        db.execSQL(query7);
        db.execSQL(query6);
        db.execSQL(query8);
        db.execSQL(query9);

        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query);
        onCreate(db);
    }

    public long setDriver(String id, String name, String empid, String vtype, String vno, String cno, String status){
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("empid",empid);
        values.put("vtype",vtype);
        values.put("vno",vno);
        values.put("cno",cno);
        values.put("status",status);
        return sqLiteDatabase.insert("driver",null,values);
    }

    public Cursor getDriver(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM driver", null);
        return cursor;
    }

    public void deleteDriver(){
        String query = "DELETE FROM driver";
        sqLiteDatabase.execSQL(query);
    }

    public long setExtra(String id,String name,String price,String status){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("price", price);
        values.put("status", status);
        return sqLiteDatabase.insert("extra", null, values);
    }

    public long setChanges(String id,String name,String price,String status){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("price", price);
        values.put("status", status);
        return sqLiteDatabase.insert("changes", null, values);
    }

    public Cursor getExtra(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM extra", null);
        return cursor;
    }

    public Cursor getChanges(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM changes", null);
        return cursor;
    }

    public void deleteChanges(){
        String query = "DELETE FROM changes";
        sqLiteDatabase.execSQL(query);
    }
    public void deleteExtra(){
        String query = "DELETE FROM extra";
        sqLiteDatabase.execSQL(query);
    }

    public long setMyOrder(String order) {
        ContentValues values = new ContentValues();
        values.put("myorder", order);
        Log.e(TAG, "setMyOrder: " + order);
        return sqLiteDatabase.insert("maketheorder", null, values);
    }

    public Cursor getMyOrder() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM maketheorder", null);
        return cursor;
    }

    public void deleteMyOrder(String oid) {
        String query = "DELETE FROM maketheorder where myorder='" + oid + "'";
        sqLiteDatabase.execSQL(query);
    }

    public int countMyOrders() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM maketheorder", null);
        cursor.moveToFirst();
        int x = Integer.parseInt(cursor.getString(0));
        cursor.close();
        return x;
    }

    public long setCartItems(String name, String price, String itemid, String qty) {
        ContentValues values = new ContentValues();
        values.put("itemname", name);
        values.put("price", price);
        values.put("itemid", itemid);
        values.put("qty", qty);
        return sqLiteDatabase.insert("cartitems", null, values);
    }

    public int searchCartItems(String itemid) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*),qty FROM cartitems WHERE itemid='" + itemid + "'", null);
        cursor.moveToFirst();
        // Log.e(TAG, "searchCartItems: " + cursor.getString(0) + " " + cursor.getString(1));
        int x = Integer.parseInt(cursor.getString(0));

        if (x == 0)
            return 0;
        else
            return Integer.parseInt(cursor.getString(1));
    }

    public void updateCartItemsQty(String qty, String itemid) {
        String q = "UPDATE cartitems SET qty='" + qty + "' WHERE itemid='" + itemid + "'";
        sqLiteDatabase.execSQL(q);
        updateCartSubItemsQty(qty, itemid);
    }

    public void updateCartSubItemsQty(String qty, String itemid) {
        String q = "UPDATE cartsubitems SET subqty='" + qty + "' WHERE itemid='" + itemid + "'";
        sqLiteDatabase.execSQL(q);
    }

    public void deleteCartItems(String name) {
        String query = "DELETE FROM cartitems where itemid='" + name + "'";
        String q = "DELETE FROM cartsubitems where itemid='" + name + "'";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(q);
    }


    public long setSubAddonsCartItems(String itemid, String name, String price, String subid, String subqty, String subtype, String ext) {
        ContentValues values = new ContentValues();
        values.put("itemid", itemid);
        values.put("subname", name);
        values.put("subprice", price);
        values.put("subid", subid);
        values.put("subqty", subqty);
        values.put("subtype", subtype);
        values.put("subext", ext);

        return sqLiteDatabase.insert("cartsubitems", null, values);
    }


    public long setCoupon(String id, String name, String code, String discount, String type, String minimum, String fromdate, String todate
            , String working, String status) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("code", code);
        values.put("discount", discount);
        values.put("type", type);
        values.put("minimum", minimum);
        values.put("from_date", fromdate);
        values.put("to_date", todate);
        values.put("working_days", working);
        values.put("status", status);
        return sqLiteDatabase.insert("couponlist", null, values);
    }

    public void deleteCoupon() {
        String query = "DELETE FROM couponlist";
        sqLiteDatabase.execSQL(query);
    }

//    public long addPrice(String price) {
//        ContentValues values = new ContentValues();
//        values.put("price", price);
//        Log.e(TAG, "added price" + price);
//        return sqLiteDatabase.insert("totalprice", null, values);
//    }

    public double getTotalPrice() {
        Double sum = 0.0;
        String q = "SELECT price, qty FROM cartitems";
        String q1 = "SELECT subprice, subqty FROM cartsubitems";
        Cursor cursor = sqLiteDatabase.rawQuery(q, null);
        Cursor cursor1 = sqLiteDatabase.rawQuery(q1, null);

        if (cursor.moveToFirst()) {
            do {
                String qt = cursor.getString(cursor.getColumnIndex("qty"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Double tot = Double.valueOf(qt) * Double.valueOf(price);
                sum = sum + tot;
                Log.e(TAG, "getTotal: " + qt + "  " + price + "  " + sum);

            } while (cursor.moveToNext());
        }

        if (cursor1.moveToFirst()) {
            do {
                String qt = cursor1.getString(cursor1.getColumnIndex("subqty"));
                String price = cursor1.getString(cursor1.getColumnIndex("subprice"));
                Double tot = Double.valueOf(qt) * Double.valueOf(price);
                sum = sum + tot;
//                Log.e(TAG, "getSub: " + qt + "  " + price + "  " + sum);
            } while (cursor1.moveToNext());
        }
        cursor.close();
        cursor1.close();
        Log.e(TAG, "getTotalPrice: " + sum);
        getPrice();
        return sum;
    }

    public double getPrice() {
        Double sum = 0.0;
        String query = "SELECT SUM(price), qty FROM cartitems";
        String q = "SELECT SUM(subprice), subqty FROM cartsubitems";
        Cursor cur = sqLiteDatabase.rawQuery(q, null);
        if (cur.moveToFirst()) {
            do {
                String c = cur.getString(0);
                String s = cur.getString(1);
                Log.e(TAG, "getsubPrice: " + c + "  " + s);
            } while (cur.moveToNext());
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String qt = cursor.getString(0);
                String s = cursor.getString(1);
                Log.e(TAG, "list price " + qt + "  " + s);
            } while (cursor.moveToNext());
        }

        cur.close();
        cursor.close();
        return sum;
    }

//    public void deletePrice() {
//        String query = "DELETE FROM totalprice";
//        sqLiteDatabase.execSQL(query);
//    }


    public void deleteStaff() {
        String q = "DELETE FROM stafftable";
        sqLiteDatabase.execSQL(q);
    }

    public long setStaff(String action, String id, String name, String display) {
        ContentValues values = new ContentValues();
        values.put("action", action);
        values.put("id", id);
        values.put("name", name);
        values.put("display", display);
//        Log.e(TAG, "setStaff: "+id+" "+name+" "+display );
        return sqLiteDatabase.insert("stafftable", null, values);
    }

    public void deleteTables() {
        String q = "DELETE FROM tables";
        sqLiteDatabase.execSQL(q);
    }

    public long setTables(String name) {
        ContentValues values = new ContentValues();
        values.put("tablename", name);
        return sqLiteDatabase.insert("tables", null, values);
    }




    public void deleteAddonsItem() {
        String q = "DELETE FROM addonsItems";
        sqLiteDatabase.execSQL(q);
    }

    public long setAddonsItems(String addonId, String addonLimit, String adNext, String desc, String spc) {
        ContentValues values = new ContentValues();
        values.put("id", addonId);
        values.put("limitaddon", addonLimit);
        values.put("next", adNext);
        values.put("desc", desc);
        values.put("specialAddon", spc);
        Log.e(TAG, "setAddonSpc : "+spc );
        return sqLiteDatabase.insert("addonsItems", null, values);
    }

    public void deleteAddonsItemList() {
        String q = "DELETE FROM addonItemsList";
        sqLiteDatabase.execSQL(q);
    }

    public long setAddonsList(String itmid, String itaid, String name, String price, String itmNext) {
        ContentValues values = new ContentValues();
        values.put("id", itmid);
        values.put("aid", itaid);
        values.put("name", name);
        values.put("price", price);
        values.put("next", itmNext);
        return sqLiteDatabase.insert("addonItemsList", null, values);
    }

    public void deleteItems() {
        String q = "DELETE FROM items";
        sqLiteDatabase.execSQL(q);
    }

    public long setItems(String name, String id, String price, String addon, String code) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("id", id);
        values.put("price", price);
        values.put("addon", addon);
        values.put("code", code);
        return sqLiteDatabase.insert("items", null, values);
    }

    public void deleteCategory() {
        String q = "DELETE FROM category";
        sqLiteDatabase.execSQL(q);
    }

    public long setCategory(String name, String id) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("id", id);
        return sqLiteDatabase.insert("category", null, values);
    }

    public void deleteSubCategory() {
        String q = "DELETE FROM subcategory";
        sqLiteDatabase.execSQL(q);
    }

    public long setSubCategory(String name, String id, String code) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("id", id);
        values.put("code", code);
        return sqLiteDatabase.insert("subcategory", null, values);
    }

    public void deleteAddons() {
        String q = "DELETE FROM addons";
        sqLiteDatabase.execSQL(q);
    }

    public long setAddons(String name, String price, String id, String speicaladdon) {
        ContentValues values = new ContentValues();
        values.put("addonName", name);
        values.put("addonPrice", price);
        values.put("addonId", id);
        values.put("hashmapHead", speicaladdon);
        Log.e(TAG, "setAddons: " + name + " " + speicaladdon + " " + price + "  " + id);
        return sqLiteDatabase.insert("addons", null, values);
    }

    public long setCartItem(String head, String subid, String itemPrice, HashMap<String, List<String>> hashMap) {
        ContentValues values = new ContentValues();
        values.put("headname", head);
        values.put("headprice", itemPrice);
        values.put("itemName", hashMap.get("itemName").toString());
        values.put("price", hashMap.get("price").toString());
        values.put("itemid", hashMap.get("itemid").toString());
        values.put("addontype", hashMap.get("addontype").toString());
        values.put("subheadid", subid);
        Log.e(TAG, head + "  " + itemPrice);
        Log.e(TAG, hashMap.get("price").toString());
        Log.e(TAG, head);
        return sqLiteDatabase.insert("cartItem", null, values);
    }

    public Cursor getCartSubItems(String id) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cartsubitems where itemid='" + id + "'", null);
        return cursor;
    }


    public Cursor getCartItems() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cartitems", null);
        return cursor;
    }


    public void deleteSingleItem(String name) {
        String query = "DELETE FROM cartItem where headname='" + name + "'";
        sqLiteDatabase.execSQL(query);
    }

    public Cursor getStaffItems() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM stafftable", null);
        return cursor;
    }

    public Cursor getAllCoupons(String code) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM couponlist where code='" + code + "'", null);
        return cursor;
    }

    public Cursor getTableItems() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tables", null);
        return cursor;
    }


    public Cursor getAddonListById(String aid) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM addonItemsList where aid='" + aid + "'", null);
        return cursor;
    }

    public Cursor getAddonList() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM addonItemsList", null);
        return cursor;
    }

    public Cursor getAddonItems(String code) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM addonsItems where id='" + code + "'", null);
        return cursor;
    }

    public Cursor getAddonItems() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM addonsItems", null);
        return cursor;
    }

    public Cursor getItems(String code) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM items  where code='" + code + "'", null);
        return cursor;
    }

    public Cursor getItems() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM items", null);
        return cursor;
    }

    public Cursor getCategory() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM category", null);
        return cursor;
    }

    public Cursor getSubCategory() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM subcategory", null);
        return cursor;
    }

    public Cursor getSubCategory(String code) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM subcategory where code='" + code + "'", null);
        return cursor;
    }

    public Cursor getCartData() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cartItem", null);
        return cursor;
    }

    public Cursor getAddons() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM addons", null);
        return cursor;
    }

    public Cursor getCartSubdata(String data) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cartItem WHERE subheadid='" + data + "'", null);
        return cursor;
    }

    public void deleteCart() {
        String query;
        query = "DELETE FROM cartItem";
//        String query1 = "DELETE FROM totalprice";

        String q = "DELETE FROM cartsubitems";
        String q1 = "DELETE FROM cartitems";
//        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(q);
        sqLiteDatabase.execSQL(q1);

    }

    public int getCategoryCountCheck() {
        String query;
        query = "SELECT COUNT(*) FROM category";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        String s = cursor.getString(0);
        cursor.close();
        return Integer.parseInt(s);
    }

    public int getTotalCount() {
        String query;
        query = "SELECT COUNT(*) FROM cartitems";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        String s = cursor.getString(0);
        cursor.close();
        return Integer.parseInt(s);
    }


}
