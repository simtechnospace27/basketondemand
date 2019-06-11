package simtechnospace.tech.basketondemand.adapter;

import android.content.Context;
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
import simtechnospace.tech.basketondemand.pojoclass.ShopByCategoryModel;

public class ShopByCategoryAdapter extends RecyclerView.Adapter<ShopByCategoryAdapter.MyViewHolder> {

    private ArrayList<ShopByCategoryModel> homeRecyclerModels; // this data structure carries our title and description

    int mPosition;


    public ShopByCategoryAdapter(ArrayList<ShopByCategoryModel> homeRecyclerModels) {
        this.homeRecyclerModels = homeRecyclerModels;
    }

    @Override
    public ShopByCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_layout_shop_by_categories, parent, false);

        final MyViewHolder mViewHolder = new MyViewHolder(view);




        // inflate your custom row layout here
        return mViewHolder;
    }
    @Override
    public void onBindViewHolder(final ShopByCategoryAdapter.MyViewHolder holder, int position) {

        mPosition = position;


    }


    @Override
    public int getItemCount() {
        return homeRecyclerModels.size();
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



