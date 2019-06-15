package simtechnospace.tech.basketondemand.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import simtechnospace.tech.basketondemand.pojoclass.Cart;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cart_details_db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create carts table
        //System.out.println(Cart.CREATE_TABLE);
        db.execSQL(Cart.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    public long insertProductIntoCart(String mProductIdInCard, String mQuantityOfProduct, String mProductSKU) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Cart.COLUMN_PRODUCT_ID, mProductIdInCard);
        values.put(Cart.COLUMN_PRODUCT_QUANTITY, mQuantityOfProduct);
        values.put(Cart.COLUMN_PRODUCT_SKU, mProductSKU);

        // insert row
        long id = db.insert(Cart.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Cart getSingleProductFromCart(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Cart.TABLE_NAME,
                new String[]{Cart.COLUMN_ID, Cart.COLUMN_PRODUCT_ID, Cart.COLUMN_TIMESTAMP, Cart.COLUMN_PRODUCT_QUANTITY, Cart.COLUMN_PRODUCT_SKU},
                Cart.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Cart cart = new Cart(
                cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_SKU)),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return cart;
    }

    public List<Cart> getAllProductsFromCart() {
        List<Cart> carts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Cart.TABLE_NAME + " ORDER BY " +
                Cart.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_ID)));
                cart.setProduct_id(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_ID)));
                cart.setTimestamp(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_TIMESTAMP)));
                cart.setProduct_quantity(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_QUANTITY)));
                cart.setProduct_sku(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_SKU)));

                carts.add(cart);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return carts;
    }

    public int getCartsProductCount() {
        String countQuery = "SELECT  * FROM " + Cart.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateProductFromCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Cart.COLUMN_PRODUCT_QUANTITY, cart.getProduct_quantity());

        // updating row
        return db.update(Cart.TABLE_NAME, values, Cart.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cart.getId())});
    }

    public void deleteProductFromCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Cart.TABLE_NAME, Cart.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cart.getId())});
        db.close();
    }


}

