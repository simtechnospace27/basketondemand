package simtechnospace.tech.basketondemand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.CardDetailsModel;
import simtechnospace.tech.basketondemand.pojoclass.Cart;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.PriceDisplay;
import simtechnospace.tech.basketondemand.pojoclass.RandomString;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class PaymentConfirmationActivity extends AppCompatActivity {

    android.support.design.widget.TextInputLayout mTextInputLayoutUserEmail, mTextInputLayoutUserAddress,mTextInputLayoutUseName,mTextInputLayoutUserMobileNo, mTextInputLayoutUserPinCode, mTextInputLayoutCoupenCode;
    android.support.design.widget.TextInputEditText mEdtUserEmail, mEdtUserAddress,mEdtUserName,mEdtUserMobileNo, mEdtUserPinCode, mEdtTextCoupenCode;
    Button mBtnConfirmOrder;
    TextView mTextViewPaymentDisplay;
    LoginUserDetails mLoginUserDetails;
    ImageButton mBtnApplyCoupenCode;

    int mApplyCoupenCodeStatus;


    String url;

    List<Cart> mCartProductList;
    DBHelper mDbHelper;
    String productQuantity;
    int productId;

    int im;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        mApplyCoupenCodeStatus = 0;


        mTextInputLayoutUserAddress = findViewById(R.id.textInputLayoutUserAddress);
        mTextInputLayoutUseName = findViewById(R.id.textInputLayoutUserFirstName);
        mTextInputLayoutUserMobileNo = findViewById(R.id.textInputLayoutUserMobileNo);
        mTextInputLayoutUserPinCode = findViewById(R.id.textInputLayoutUserPin);
        mTextInputLayoutCoupenCode = findViewById(R.id.textInputLayoutCoupenCode);

        mEdtUserMobileNo = findViewById(R.id.edtUserMobileNo);
        mEdtUserName = findViewById(R.id.edtUserFirstName);
        mEdtUserAddress = findViewById(R.id.edtUserAddress);
        mEdtUserPinCode = findViewById(R.id.edtUserPin);

        mEdtTextCoupenCode = findViewById(R.id.edtCoupenCode);
        mBtnApplyCoupenCode = findViewById(R.id.applyCoupenCodeButton);

        mBtnConfirmOrder = findViewById(R.id.btnPlaceOrder);

        mTextViewPaymentDisplay = findViewById(R.id.textPriceDiplay);

        mLoginUserDetails = new LoginUserDetails(this);

        mEdtUserAddress.setText(mLoginUserDetails.getAddress());
        mEdtUserName.setText(mLoginUserDetails.getNameDisplay());
        mEdtUserMobileNo.setText(mLoginUserDetails.getMobileNo());

        mTextViewPaymentDisplay.setText("Total cost: Rs. "+PriceDisplay.getmPriceDisplay()+"/-");

        url = URLClass.placeOrder;


        mBtnApplyCoupenCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupenCode = mEdtTextCoupenCode.getText().toString();

                if (coupenCode != null && mApplyCoupenCodeStatus == 0)
                {
                    String mApplyCoupenCodeUrl = URLClass.applyCoupenCode+coupenCode;

                    final RequestQueue requestQueue = Volley.newRequestQueue(PaymentConfirmationActivity.this);

                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mApplyCoupenCodeUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println(response.toString());
                            try {
                                if (response.getInt("status") == 1)
                                {
                                    mTextInputLayoutCoupenCode.setErrorEnabled(false);

                                    String discount = response.getString("disc");

                                    double d = Double.parseDouble(discount);

                                    double dis = (Double.parseDouble(PriceDisplay.getmPriceDisplay()) * (100 - d)) /100;
                                    mTextViewPaymentDisplay.setText("Total cost: Rs. "+dis+" /-");

                                    PriceDisplay priceDisplay = new PriceDisplay(dis+"");
                                    mApplyCoupenCodeStatus = 1;

                                }
                                else{
                                    mTextInputLayoutCoupenCode.setErrorEnabled(true);
                                    mTextInputLayoutCoupenCode.setError(response.getString("msg"));
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
                else{
                    if(mApplyCoupenCodeStatus > 0) {
                        mTextInputLayoutCoupenCode.setErrorEnabled(true);
                        mTextInputLayoutCoupenCode.setError("You Have Already Applied Coupen...");
                    }
                }


            }
        });


        mBtnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im = 0;

                String pincode = mEdtUserPinCode.getText().toString();

                if (pincode != "") {

                    mTextInputLayoutUserPinCode.setErrorEnabled(false);

                    mDbHelper = new DBHelper(PaymentConfirmationActivity.this);
                    mCartProductList = mDbHelper.getAllProductsFromCart();

                    RandomString randomString = new RandomString();
                    String str = randomString.randomString(10);

                    for (int i = 0; i < mCartProductList.size(); i++) {

                        Cart cart = mCartProductList.get(i);

                        String productIdFromCart = cart.getProduct_id();
                        productId = Integer.parseInt(productIdFromCart);
                        productQuantity = cart.getProduct_quantity();
                        String coupen = mEdtTextCoupenCode.getText().toString();
                        String placeOrderUrl = url + productId + "&proQuant=" + productQuantity + "&rid=" + str + "&userEmail=" + mLoginUserDetails.getEmail() + "&pin=" + pincode+"&cost="+PriceDisplay.getmPriceDisplay()+"&coupen="+coupen;


                        System.out.println(placeOrderUrl);
                        mDbHelper.deleteProductFromCart(productIdFromCart);
                        final RequestQueue requestQueue = Volley.newRequestQueue(PaymentConfirmationActivity.this);

                        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, placeOrderUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                System.out.println(response.toString());
                                try {
                                    if (response.getInt("status") == 1)
                                    {
                                        im = im + 1;
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

                }
                else{
                    mTextInputLayoutUserPinCode.setErrorEnabled(true);
                    mTextInputLayoutUserPinCode.setError("Please Enter Password length greater than 3");
                }


                   Intent intent = new Intent(PaymentConfirmationActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


            }



        });

    }
}
