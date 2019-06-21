package simtechnospace.tech.basketondemand.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import simtechnospace.tech.basketondemand.Dialogs.CustomAlertDialogForProductAddToCart;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private ArrayList<ProductListModel> mProductArrayList; // this data structure carries our title and description

    int mPosition;


    public CartListAdapter(ArrayList<ProductListModel> productArrayList) {
        System.out.println(productArrayList.size());
        this.mProductArrayList = productArrayList;
        System.out.println(mProductArrayList.size());
    }

    @Override
    public CartListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {


        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart_display, parent, false);
        final CartListAdapter.MyViewHolder mViewHolder = new CartListAdapter.MyViewHolder(view);


        // inflate your custom row layout here
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(final CartListAdapter.MyViewHolder holder, int position) {

        mPosition = position;

        Picasso.with(holder.context).load(mProductArrayList.get(position).getmImgUrl()).into(holder.mImg);
//        holder.mdiscountPercentage.setText(mProductArrayList.get(position).getmDiscountPrice() + "% off");
        holder.mProductName.setText(mProductArrayList.get(position).getmProductName());
        holder.mProductOrgMrp.setText("MRP: Rs. :  "+mProductArrayList.get(position).getmOriginalMrp()+" /-");

        double originalPrice = mProductArrayList.get(position).getmOriginalMrp();
        int discount = mProductArrayList.get(position).getmDiscountPrice();

        double discountePrice = (originalPrice * (100 - discount)) / 100;

        holder.mDiscountedPrice.setText("New MRP : Rs. "+discountePrice + " /-");
        //holder.mProductContent.setText(mProductArrayList.get(position).getmProductContent());


        if (discount == 0)
        {
          //  holder.mdiscountPercentage.setVisibility(View.GONE);
            holder.mDiscountedPrice.setVisibility(View.GONE);
           // holder.mDiscountedPercentCardView.setVisibility(View.GONE);

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
        TextView mCardAmount;
        ImageView mImg;
        TextView mProductName,mProductOrgMrp,mDiscountedPrice;
        Double mDoubleOrgMrp,mDoubleDiscountPrice;
        Button mBtnPlusCart, mBtnminusCart;
        EditText mEdtCountCart;

        MyViewHolder(View view) {
            super(view);


            context = view.getContext();
            mCardView = view.findViewById(R.id.cardview_of_cart);
            mImg = view.findViewById(R.id.imgcart_display);

         //   mdiscountPercentage = view.findViewById(R.id.textviewSingleCateggoryName);
           // mProductContent = view.findViewById(R.id.category_content);

            mProductName = view.findViewById(R.id.txtCartProductName);
            mProductOrgMrp = view.findViewById(R.id.ttcart_original_amt);
            mDiscountedPrice = view.findViewById(R.id.ttcart_MRP);
            mCardAmount = view.findViewById(R.id.cart_Amount);

            mBtnminusCart = view.findViewById(R.id.btnminus);
            mBtnPlusCart = view.findViewById(R.id.btnPlus);
            mEdtCountCart = view.findViewById(R.id.edtCount_Cart);

            //mDoubleDiscountPrice = Double.parseDouble(mDiscountedPrice.getText().toString());
            // mDoubleOrgMrp = Double.parseDouble(mProductOrgMrp.getText().toString());



        }
    }
}
