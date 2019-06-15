package simtechnospace.tech.basketondemand.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;


import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import simtechnospace.tech.basketondemand.Dialogs.CustomAlertDialogForProductAddToCart;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.activity.CategoryWiseProductList;
import simtechnospace.tech.basketondemand.activity.SubCategoryList;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryDetails;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;
import simtechnospace.tech.basketondemand.pojoclass.ShopByCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.SubCategoryModel;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private ArrayList<ProductListModel> mProductArrayList; // this data structure carries our title and description

    int mPosition;


    public ProductListAdapter(ArrayList<ProductListModel> productArrayList) {
        System.out.println(productArrayList.size());
        this.mProductArrayList = productArrayList;
    }

    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {


        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subcategory_recyclerview, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(view);


        mViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialogForProductAddToCart customAlertDialogForProductAddToCart = new CustomAlertDialogForProductAddToCart(mViewHolder.context,mProductArrayList.get(mViewHolder.getPosition()).getmProductId(),mProductArrayList.get(mViewHolder.getPosition()).getmProductName(),mProductArrayList.get(mViewHolder.getPosition()).getmImgUrl(),mProductArrayList.get(mViewHolder.getPosition()).getmOriginalMrp(),mProductArrayList.get(mViewHolder.getPosition()).getmDiscountPrice());
                customAlertDialogForProductAddToCart.show();
            }
        });

        // inflate your custom row layout here
        return mViewHolder;

    }
    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, int position) {

        mPosition = position;

             Picasso.with(holder.context).load(mProductArrayList.get(position).getmImgUrl()).into(holder.mImg);
             holder.mdiscountPercentage.setText(mProductArrayList.get(position).getmDiscountPrice() + "% off");
             holder.mProductName.setText(mProductArrayList.get(position).getmProductName());
             holder.mProductOrgMrp.setText("MRP: Rs. :  "+mProductArrayList.get(position).getmOriginalMrp()+" /-");

             double originalPrice = mProductArrayList.get(position).getmOriginalMrp();
             int discount = mProductArrayList.get(position).getmDiscountPrice();

        double discountePrice = (originalPrice * (100 - discount)) / 100;

        holder.mDiscountedPrice.setText("New MRP : Rs. "+discountePrice + " /-");
        holder.mProductContent.setText(mProductArrayList.get(position).getmProductContent());



        if (discount == 0)
             {
                 holder.mdiscountPercentage.setVisibility(View.GONE);
                 holder.mDiscountedPrice.setVisibility(View.GONE);
                 holder.mDiscountedPercentCardView.setVisibility(View.GONE);

             }
             else{
            holder.mProductOrgMrp.setPaintFlags(holder.mProductOrgMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }




    }


    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }


class MyViewHolder extends RecyclerView.ViewHolder {

        Context context;
        CardView mCardView;
        CardView mDiscountedPercentCardView;
        ImageView mImg;
        TextView mProductName,mProductContent,mProductOrgMrp,mDiscountedPrice,mdiscountPercentage;
        Double mDoubleOrgMrp,mDoubleDiscountPrice;

        MyViewHolder(View view) {
            super(view);


            context = view.getContext();
            mCardView = view.findViewById(R.id.cardview_productadapter);
            mImg = view.findViewById(R.id.imgsingle_sc_recyclerview);
            mdiscountPercentage = view.findViewById(R.id.textviewSingleCateggoryName);
            mProductContent = view.findViewById(R.id.category_content);
            mProductName = view.findViewById(R.id.product_name);
            mProductOrgMrp = view.findViewById(R.id.original_mrp);
            mDiscountedPrice = view.findViewById(R.id.discounted_price);
            mDiscountedPercentCardView = view.findViewById(R.id.discountedPercentCardView);

            //mDoubleDiscountPrice = Double.parseDouble(mDiscountedPrice.getText().toString());
           // mDoubleOrgMrp = Double.parseDouble(mProductOrgMrp.getText().toString());



        }
    }
}
