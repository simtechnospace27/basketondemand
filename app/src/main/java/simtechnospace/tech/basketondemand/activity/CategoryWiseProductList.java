package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.ShopByCategoryAdapter;
import simtechnospace.tech.basketondemand.adapter.SubCategoryListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryDetails;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.ShopByCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.SubCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

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

        int catId = ClickedCategoryForProducts.getCatId();
        int subCatId = ClickedCategoryForProducts.getSubCatId();

        System.out.println("Clicked Category Id = "+catId);
        System.out.println("Clicked SubCategory Id = "+subCatId);

    }
}