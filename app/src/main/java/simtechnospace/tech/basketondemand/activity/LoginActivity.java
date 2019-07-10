package simtechnospace.tech.basketondemand.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class LoginActivity extends AppCompatActivity {

    Button mbtnSignUp, mBtnLogin;
    TextInputLayout mTextInputEmailId, mTextInputPassword;
    TextInputEditText mTextInputEditTextEmail, mTextInputEditTextPasowrd;
    TextView mTextViewForgetPassword;

    String mUserName, mPassword;

    Vibrator mVibrator;

    LoginUserDetails mLoginUserDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);



        mbtnSignUp = findViewById(R.id.btnSignUp);
        mBtnLogin = findViewById(R.id.btnLogin);

        mTextInputEmailId = findViewById(R.id.textInputLayoutUserName);
        mTextInputEditTextEmail = findViewById(R.id.edtUserName);

        mTextInputPassword = findViewById(R.id.textInputLayoutUserPassword);
        mTextInputEditTextPasowrd = findViewById(R.id.edtUserPassword);

        mTextViewForgetPassword = findViewById(R.id.textforgetPassword);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mLoginUserDetails = new LoginUserDetails(this);

        if (mLoginUserDetails.getEmail() != "")
        {
            Intent loginIntent = new Intent(LoginActivity.this,CartProductList.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }

        mbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
               // finish();
            }
        });


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = mTextInputEditTextEmail.getText().toString();
                mPassword = mTextInputEditTextPasowrd.getText().toString();

                submitForm(mUserName,mPassword);


            }
        });

    }


    private boolean checkUserName(String userName) {

        userName = userName.trim();

        if (userName.trim().isEmpty() || !isValidEmail(userName)){
            mTextInputEmailId.setErrorEnabled(true);
            mTextInputEmailId.setError("Please Enter Valid Email ID");
            return false;
        }
        mTextInputEmailId.setErrorEnabled(false);
        return true;
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean checkPassword(String password) {
        if (password.trim().isEmpty() || (password.length() < 4)){
            mTextInputPassword.setErrorEnabled(true);
            mTextInputPassword.setError("Please Enter Password length greater than 3");
            return false;
        }
        mTextInputPassword.setErrorEnabled(false);
        return true;

    }






    public void submitForm(String userName, String password) {

        if (!checkUserName(userName)) {
            mVibrator.vibrate(1000);
            return;
        } else if (!checkPassword(password)) {
            mVibrator.vibrate(1000);
            return;

        } else {
            mTextInputEmailId.setErrorEnabled(false);
            mTextInputPassword.setErrorEnabled(false);


            JSONObject params = new JSONObject();
            try {


                params.put("email", userName);
                params.put("password", password);


            } catch (JSONException e) {
                e.printStackTrace();

            }

            String url = URLClass.loginUrl;

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getString("message").equalsIgnoreCase("Login success")) {

                            System.out.println(response.getString("mobile"));
                            System.out.println(response.getString("name"));
                            System.out.println(response.getString("address"));

                            mLoginUserDetails.setEmail(response.getString("email"));
                            mLoginUserDetails.setUserId(response.getString("userid"));
                            mLoginUserDetails.setAddress(response.getString("address"));
                            mLoginUserDetails.setMobileNo(response.getString("mobile"));
                            mLoginUserDetails.setNameDisplay(response.getString("name"));

                            Intent loginIntent = new Intent(LoginActivity.this, CartProductList.class);
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginIntent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(LoginActivity.this, "Please check login details", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);


        }
    }



    }
