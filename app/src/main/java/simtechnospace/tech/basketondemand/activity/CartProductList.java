package simtechnospace.tech.basketondemand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.CartListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.CardDetailsModel;
import simtechnospace.tech.basketondemand.pojoclass.Cart;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.PriceDisplay;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class CartProductList extends AppCompatActivity {

    RecyclerView mRecyclerViewCartList;
    List<Cart> mCartProductList;
    CartListAdapter mCartListAdapter;

    ArrayList<CardDetailsModel> mProductListModelArrayList;

    DBHelper mDbHelper;

    LinearLayoutManager gridLayoutManager;

    String productQuantity;

    public static Button mBtnSubmitDetails;

    public static double mTotalPricePay;

    LoginUserDetails mLoginUserDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        mTotalPricePay = 0.0;

        mRecyclerViewCartList = findViewById(R.id.cart_display_recyclerview);

        mProductListModelArrayList = new ArrayList<>();

        mCartListAdapter = new CartListAdapter(mProductListModelArrayList);
        mDbHelper = new DBHelper(this);
        mCartProductList = mDbHelper.getAllProductsFromCart();

        gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerViewCartList.setLayoutManager(gridLayoutManager);
        mRecyclerViewCartList.setHasFixedSize(true);

        mRecyclerViewCartList.setAdapter(mCartListAdapter);

        mBtnSubmitDetails = findViewById(R.id.btnSubmitCartForOrder);
        mBtnSubmitDetails.setText("Place Order: Rs."+mTotalPricePay+" /-");

        mLoginUserDetails = new LoginUserDetails(this);


        for (int i=0; i<mCartProductList.size(); i++)
        {


            Cart cart = mCartProductList.get(i);

            String productIdFromCart = cart.getProduct_id();
            int productId = Integer.parseInt(productIdFromCart);
            productQuantity = cart.getProduct_quantity();
            String productDetailsURL = URLClass.productDetailsURL+productId+"&proQuant="+productQuantity;

            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, productDetailsURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String msg = response.getString("msg");

                        if (msg.equalsIgnoreCase("success"))
                        {


                            JSONArray jsonArrayList = response.getJSONArray("result");

                            for (int i=0; i<jsonArrayList.length(); i++)
                            {
                                JSONObject js = jsonArrayList.getJSONObject(i);

                                int id = js.getInt("id");
                                String imgUrl = js.getString("img");
                                String name = js.getString("name");
                                String quantity = js.getString("quantity");
                                String mrp = js.getString("mrp");
                                int disacount = js.getInt("disacount");

                                String tax = js.getString("tax");

                                //   int dmrp = Integer.parseInt(mrp);

                                double dmrp = Double.parseDouble(mrp);

                                double quantityD = Double.parseDouble(quantity);

                                double price = ((dmrp * (100 - disacount)) / 100) * quantityD *((100+Integer.parseInt(tax))) / 100;

                                mTotalPricePay = mTotalPricePay + price;

                                System.out.println(mTotalPricePay);

                                CardDetailsModel cardDetailsModel = new CardDetailsModel(String.valueOf(id), imgUrl,name,quantity,disacount,dmrp,quantityD,tax);

                                mProductListModelArrayList.add(cardDetailsModel);
                                mCartListAdapter.notifyDataSetChanged();

                            }

                            mBtnSubmitDetails.setText("Place Order: Rs."+mTotalPricePay+" /-");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);





        }




        mBtnSubmitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTotalPricePay == 0)
                {
                    Toast.makeText(CartProductList.this, "Please Add Atleast One Product in Cart...", Toast.LENGTH_SHORT).show();
                }
                else{

                    System.out.println(mLoginUserDetails.getEmail());
                    if (mLoginUserDetails.getEmail() != "") {

                        PriceDisplay priceDisplay = new PriceDisplay(String.valueOf(mTotalPricePay));

                        Intent loginIntent = new Intent(CartProductList.this, PaymentConfirmationActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                    }
                    else{
                        Intent loginIntent = new Intent(CartProductList.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }
                }
            }
        });



    }
}
