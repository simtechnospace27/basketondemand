package simtechnospace.tech.basketondemand.pojoclass;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginUserDetails {

    SharedPreferences mSharedPreferences;
    String mUserName;
    String mUserId;
    String mNameDisplay;
    String mAddress;
    String mMobileNo;
    Context mContext;

    public LoginUserDetails(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }


    public void removeUserInfo(){
        mSharedPreferences.edit().clear().commit();
    }

    public String getNameDisplay() {
        mNameDisplay = mSharedPreferences.getString("name","");
        return mNameDisplay;
    }

    public void setNameDisplay(String mName) {
        this.mNameDisplay = mName;
        mSharedPreferences.edit().putString("name",mName).commit();
    }

    public String getMobileNo() {
        mMobileNo = mSharedPreferences.getString("mobile","");
        return mMobileNo;
    }

    public void setMobileNo(String mMobileNo) {
        this.mMobileNo = mMobileNo;
        mSharedPreferences.edit().putString("mobile",mMobileNo).commit();
    }

    public String getAddress() {
        mAddress = mSharedPreferences.getString("address","");
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
        mSharedPreferences.edit().putString("address",mAddress).commit();
    }

    public String getEmail() {
        mUserName = mSharedPreferences.getString("email","");
        return mUserName;
    }

    public void setEmail(String email) {
        this.mUserName = email;
        mSharedPreferences.edit().putString("email",mUserName).commit();
    }


    public String getUserId() {
        mUserId = mSharedPreferences.getString("userid","");
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
        mSharedPreferences.edit().putString("userid",mUserId).commit();
    }
}
