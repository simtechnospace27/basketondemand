package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

import simtechnospace.tech.basketondemand.R;

public class CategoryWiseProductList extends AppCompatActivity {

    ImageView mCategoryBannerImage;
    TextView  mCategoryName;
    RecyclerView mCategoryListRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_displaylist);

        mCategoryBannerImage = findViewById(R.id.bannerCateogryImage);
        mCategoryName = findViewById(R.id.categoryName);
        mCategoryListRecyclerView = findViewById(R.id.categoryListRecyclerView);





    }
}