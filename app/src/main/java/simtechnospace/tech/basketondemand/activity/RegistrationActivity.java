package simtechnospace.tech.basketondemand.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
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
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class RegistrationActivity extends AppCompatActivity {


    android.support.design.widget.TextInputLayout mTextInputLayoutUserName,mTextInputLayoutUserPassword, mTextInputLayoutUserAddress,mTextInputLayoutUseFirstName,mTextInputLayoutUserLastName,mTextInputLayoutUserMobileNo;
    android.support.design.widget.TextInputEditText mEdtUserName,mEdtUserPassword, mEdtUserAddress,mEdtUserFirstName,mEdtUserLastName,mEdtUserMobileNo;
    Button mBtnSignup;
    String mUserName, mPassword,mUserFirstName,mUserLastName,mMobileNo, mAddress;
    Vibrator mVibrator;
    LoginUserDetails mLoginUserDetails;

    TextView mTextViewNewUserRegistration;
    ProgressWheel mProgressWheelPreview;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mProgressWheelPreview =  findViewById(R.id.progress_wheel);


        mLoginUserDetails = new LoginUserDetails(this);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (mLoginUserDetails.getEmail() != "")
        {
            Intent loginIntent = new Intent(RegistrationActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }

        mTextInputLayoutUserName = findViewById(R.id.textInputLayoutUserNameRegistration);
        mTextInputLayoutUserPassword = findViewById(R.id.textInputLayoutUserPasswordRegistration);
        mTextInputLayoutUserAddress = findViewById(R.id.textInputLayoutUserAddress);
        mTextInputLayoutUseFirstName = findViewById(R.id.textInputLayoutUserFirstName);
        mTextInputLayoutUserLastName = findViewById(R.id.textInputLayoutUserLastName);
        mTextInputLayoutUserMobileNo = findViewById(R.id.textInputLayoutUserMobileNo);

        mEdtUserName = findViewById(R.id.edtUserNameRegistration);
        mEdtUserPassword = findViewById(R.id.edtUserPasswordRegistration);
        mEdtUserMobileNo = findViewById(R.id.edtUserMobileNo);
        mEdtUserFirstName = findViewById(R.id.edtUserFirstName);
        mEdtUserLastName = findViewById(R.id.edtUserLastName);
        mEdtUserAddress = findViewById(R.id.edtUserAddress);

        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        mBtnSignup = findViewById(R.id.btnSignUp);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mUserName = mEdtUserName.getText().toString();
                mPassword = mEdtUserPassword.getText().toString();
                mAddress = mEdtUserAddress.getText().toString();
                mUserFirstName = mEdtUserFirstName.getText().toString();
                mUserLastName = mEdtUserLastName.getText().toString();
                mMobileNo = mEdtUserMobileNo.getText().toString();
                submitForm(mUserFirstName, mUserLastName, mMobileNo, mUserName, mPassword, mAddress);
            }
        });

    }


    private boolean checkFirstName(String userName) {

        userName = userName.trim();

        if(userName.trim().isEmpty() || (userName.length() <= 1)){
            mTextInputLayoutUseFirstName.setErrorEnabled( true );
            mTextInputLayoutUseFirstName.setError( "Please Enter Valid First Name" );
            return false;
        }
        mTextInputLayoutUseFirstName.setErrorEnabled( false );
        return true;
    }


    private boolean checkLastName(String userName) {

        userName = userName.trim();

        if(userName.trim().isEmpty() || (userName.length() <= 1)){
            mTextInputLayoutUserLastName.setErrorEnabled( true );
            mTextInputLayoutUserLastName.setError( "Please Enter Valid Last Name" );
            return false;
        }
        mTextInputLayoutUserLastName.setErrorEnabled( false );
        return true;
    }

    private boolean checkAddress(String address) {

        address = address.trim();

        if(address.trim().isEmpty() || (address.length() <= 8)){
            mTextInputLayoutUserAddress.setErrorEnabled( true );
            mTextInputLayoutUserAddress.setError( "Please Enter Valid Address" );
            return false;
        }
        mTextInputLayoutUserAddress.setErrorEnabled( false );
        return true;
    }


    private boolean checkUserName(String userName) {

        userName = userName.trim();

        if (userName.trim().isEmpty() || !isValidEmail(userName)){
            mTextInputLayoutUserName.setErrorEnabled(true);
            mTextInputLayoutUserName.setError("Please Enter Valid Email ID");
            return false;
        }
        mTextInputLayoutUserName.setErrorEnabled(false);
        return true;
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean chkPass(String pass)
    {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";

        return pass.matches(pattern);

    }

    private boolean checkPassword(String password) {
        if (password.trim().isEmpty() || (password.length() < 7) ){
            mTextInputLayoutUserPassword.setErrorEnabled(true);
            mTextInputLayoutUserPassword.setError("Please enter password length greater than 6");
            return false;
        }
        mTextInputLayoutUserPassword.setErrorEnabled(false);
        return true;

    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 10 || phone.length() > 14) {
                System.out.println(phone.length());
                // if(phone.length() != 10) {
                check = false;
                //mTextInputLayoutUserMobileNo.setErrorEnabled(true);
                //mTextInputLayoutUserMobileNo.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }


    private boolean checkMobileNo(String mobile) {
        if (mobile.trim().isEmpty() || !isValidMobile(mobile) ){
            mTextInputLayoutUserMobileNo.setErrorEnabled(true);
            mTextInputLayoutUserMobileNo.setError("Please enter valid mobile no");
            return false;
        }
        mTextInputLayoutUserMobileNo.setErrorEnabled(false);
        return true;

    }



    public void submitForm(String firstname,String lastname,String mobileNo,String userName, String password, String address) {

        if (!checkFirstName(firstname)) {
            mVibrator.vibrate(1000);
            return;

        } else if (!checkLastName(lastname)) {
            mVibrator.vibrate(1000);
            return;

        } else if (!checkMobileNo(mobileNo)) {
            mVibrator.vibrate(1000);
            return;

        } else if (!checkUserName(userName)) {
            mVibrator.vibrate(1000);
            return;
        } else if (!checkPassword(password)) {
            mVibrator.vibrate(1000);
            return;
        }
        else if (!checkAddress(address)) {
            mVibrator.vibrate(1000);
            return;
        }
        else {
            mTextInputLayoutUserName.setErrorEnabled(false);
            mTextInputLayoutUserPassword.setErrorEnabled(false);
            mTextInputLayoutUserMobileNo.setErrorEnabled(false);
            mTextInputLayoutUseFirstName.setErrorEnabled(false);
            mTextInputLayoutUserLastName.setErrorEnabled(false);
            mTextInputLayoutUserAddress.setErrorEnabled(false);

            address = address.trim();
            progressDialog.show();
            JSONObject params = new JSONObject();
            try {

                params.put("email", userName);
                params.put("password", password);
                params.put("name", firstname+" "+lastname);
                params.put("address", address);
                params.put("mobile", mobileNo);



            } catch (JSONException e) {
                e.printStackTrace();

            }

            System.out.println(params.toString());


            final RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);


            String url = URLClass.registrationUrl;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {


                    try {
                        if (response.getString("message").equals("Registration successful")) {

                            progressDialog.dismiss();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } else {
                            System.out.println(response.toString());
                            Toast.makeText(RegistrationActivity.this, "User with this email already Exist", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RegistrationActivity.this, "Please Check Credentials", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

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
