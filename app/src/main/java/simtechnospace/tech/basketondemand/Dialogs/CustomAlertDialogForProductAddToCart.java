package simtechnospace.tech.basketondemand.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.activity.CategoryWiseProductList;
import simtechnospace.tech.basketondemand.activity.SubCategoryList;
import simtechnospace.tech.basketondemand.pojoclass.Cart;

public class CustomAlertDialogForProductAddToCart extends Dialog{

    public Context c;
    public Button yes, no;
    EditText mEditTextCount;
    TextView mTextViewProductRate,mTextViewProductActualMRP,mTextViewProductDesc,mTextViewProductTitleSelected,mTextViewPrductMRP,mTextViewProductName;
    Button mBtnPlus,mBtnMinus;
  //  ImageView mImageView;

    Double mProductRateOriginal,mCountValue,mFinalRate;
    int mDiscountRate;
    String mStrCount,mStrProductPrice,mStrProductName,mImgUrl,mProductId,mProductSKU;

    public CustomAlertDialogForProductAddToCart(Context a, String mProductId, String mProductName,String mImgUrl,Double mOriginalMrp ,int mDiscountedPrice) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.mProductRateOriginal = mOriginalMrp;
        this.mDiscountRate = mDiscountedPrice;
        this.mStrProductName = mProductName;
        this.mImgUrl  = mImgUrl;
        this.mProductId = mProductId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_for_add_to_cart);

        yes =  findViewById(R.id.btnYes);
        no =  findViewById(R.id.btnNo);

        mTextViewPrductMRP = findViewById(R.id.textPrductMRP);
        mTextViewProductDesc = findViewById(R.id.txtProductDesc);
        mTextViewProductRate = findViewById(R.id.txtProductRate);
        mTextViewProductTitleSelected = findViewById(R.id.textProductTitleSelected);
        mTextViewProductName = findViewById(R.id.textPrductName);
        mTextViewProductActualMRP = findViewById(R.id.textActualMRP);
     //   mImageView = findViewById(R.id.ProductImage);

        double productNewRate = (mProductRateOriginal * (100 - mDiscountRate)) / 100;

        mTextViewProductName.setText(mStrProductName);
        mTextViewProductActualMRP.setText(mProductRateOriginal.toString());
        mTextViewPrductMRP.setText(productNewRate+"");
       // Picasso.with(c).load(mImgUrl).into(mImageView);
        mTextViewProductRate.setText(productNewRate+"");



        mBtnMinus = findViewById(R.id.btnminus);
        mBtnPlus = findViewById(R.id.btnPlus);

        mEditTextCount = findViewById(R.id.edtCount);



        mStrProductName = mTextViewProductName.toString();
        mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProductRateOriginal = Double.parseDouble(mTextViewPrductMRP.getText().toString());
                mCountValue = Double.parseDouble(mEditTextCount.getText().toString());
                mCountValue += 1.0d;
                mStrCount = mEditTextCount.getText().toString();
                mFinalRate = mProductRateOriginal * mCountValue;
                mEditTextCount.setText(Double.toString(mCountValue));
                mTextViewProductRate.setText(Double.toString(mFinalRate));
                mStrProductPrice = mTextViewProductRate.getText().toString();
            }
        });


        mBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProductRateOriginal = Double.parseDouble(mTextViewPrductMRP.getText().toString());
                mCountValue = Double.parseDouble(mEditTextCount.getText().toString());
                mCountValue -= 1.0d;
                if (mCountValue != 0.0d) {
                    mStrCount = mEditTextCount.getText().toString();
                    mFinalRate = mProductRateOriginal * mCountValue;
                    mEditTextCount.setText(Double.toString(mCountValue));
                    mTextViewProductRate.setText(Double.toString(mFinalRate));
                    mStrProductPrice = mTextViewProductRate.getText().toString();
                }
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProductSKU = "ii";
                DBHelper dbHelper = new DBHelper(c);
                dbHelper.insertProductIntoCart(mProductId,mEditTextCount.getText().toString(),mProductSKU);

                Toast.makeText(c, "Product Added To Cart Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(c, CategoryWiseProductList.class);
                c.startActivity(i);
                ((Activity) c).finish();
                dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }



}