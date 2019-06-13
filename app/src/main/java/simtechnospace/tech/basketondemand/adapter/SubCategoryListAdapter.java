package simtechnospace.tech.basketondemand.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.SubCategoryModel;

public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.MyViewHolder> {

private ArrayList<SubCategoryModel> subCategoryModelArrayList; // this data structure carries our title and description




public SubCategoryListAdapter(ArrayList<SubCategoryModel> subCategoryModelArrayList) {
        this.subCategoryModelArrayList = subCategoryModelArrayList;
        }



@Override
public SubCategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subcategory_design, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(view);


        mViewHolder.cardViewSingleSubcategorydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int subCatId = subCategoryModelArrayList.get(mViewHolder.getPosition()).getSubcategoryId();
                int catId = subCategoryModelArrayList.get(mViewHolder.getPosition()).getCatId();

                ClickedCategoryForProducts clickedCategoryForProducts = new ClickedCategoryForProducts(subCatId,catId);

                Intent intent = new Intent(mViewHolder.context, CategoryWiseProductList.class);
                mViewHolder.context.startActivity(intent);
            }
        });


        // inflate your custom row layout here
        return mViewHolder;
        }


@Override
public void onBindViewHolder(final SubCategoryListAdapter.MyViewHolder holder, int position) {


    holder.textViewSubCategoryName.setText(subCategoryModelArrayList.get(position).getSubCategoryName());

    System.out.println(subCategoryModelArrayList.get(position).getSubCategoryImageUrl());
    Picasso.with(holder.context).load(subCategoryModelArrayList.get(position).getSubCategoryImageUrl()).into(holder.imageViewSubCategoryImage);

        }




@Override
public int getItemCount() {
        return subCategoryModelArrayList.size();
        }



class MyViewHolder extends RecyclerView.ViewHolder {

    Context context;

    TextView textViewSubCategoryName;
    ImageView imageViewSubCategoryImage;
    CardView cardViewSingleSubcategorydetails;

    MyViewHolder(View view) {
        super(view);


        context = view.getContext();

        textViewSubCategoryName = view.findViewById(R.id.textViewSubCategoryName);
        imageViewSubCategoryImage = view.findViewById(R.id.imageViewSingleSubCategoryImage);
        cardViewSingleSubcategorydetails = view.findViewById(R.id.singleSubCategoryCard);


    }


}
}




