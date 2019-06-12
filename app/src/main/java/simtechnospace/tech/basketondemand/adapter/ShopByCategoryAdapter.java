package simtechnospace.tech.basketondemand.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.activity.CategoryWiseProductList;
import simtechnospace.tech.basketondemand.activity.SubCategoryList;
import simtechnospace.tech.basketondemand.pojoclass.ShopByCategoryModel;

public class ShopByCategoryAdapter extends RecyclerView.Adapter<ShopByCategoryAdapter.MyViewHolder> {

    private ArrayList<ShopByCategoryModel> shopByCategoryModelsArrayList; // this data structure carries our title and description

    int mPosition;


    public ShopByCategoryAdapter(ArrayList<ShopByCategoryModel> shopByCategoryModelsArrayList) {
        this.shopByCategoryModelsArrayList = shopByCategoryModelsArrayList;
    }

    @Override
    public ShopByCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_layout_shop_by_categories, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(view);



        mViewHolder.cardViewSingleCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(shopByCategoryModelsArrayList.get(mViewHolder.getPosition()).getContainsSubCat() == 0)
               {
                   Intent intent = new Intent(mViewHolder.context, SubCategoryList.class);
                   mViewHolder.context.startActivity(intent);
               }
               else {
                   Intent intent = new Intent(mViewHolder.context, CategoryWiseProductList.class);
                   mViewHolder.context.startActivity(intent);
               }

            }
        });
        // inflate your custom row layout here
        return mViewHolder;
    }
    @Override
    public void onBindViewHolder(final ShopByCategoryAdapter.MyViewHolder holder, int position) {

        mPosition = position;

        holder.textViewSingleCategoryName.setText(shopByCategoryModelsArrayList.get(position).getCategoryName());
        Picasso.with(holder.context).load(shopByCategoryModelsArrayList.get(position).getCategoryImageUrl()).into(holder.imageViewSingleCategory);


    }


    @Override
    public int getItemCount() {
        return shopByCategoryModelsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView imageViewSingleCategory;
        TextView textViewSingleCategoryName;
        CardView cardViewSingleCategoryCard;

        MyViewHolder(View view) {
            super(view);


            context = view.getContext();

            cardViewSingleCategoryCard = view.findViewById(R.id.cardViewCategory);
            imageViewSingleCategory = view.findViewById(R.id.imageViewSingleCategory);
            textViewSingleCategoryName = view.findViewById(R.id.textviewSingleCateggoryName);


        }


    }
}



