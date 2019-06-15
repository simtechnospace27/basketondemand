package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.ProductListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;
import simtechnospace.tech.basketondemand.pojoclass.Utility;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;


public class CategoryWiseProductList extends AppCompatActivity {

    ImageView mCategoryBannerImage;
    TextView mCategoryName;
    RecyclerView mCategoryListRecyclerView;


    public ProductListAdapter mProductAdapter;
    public ArrayList<ProductListModel> mProductModelArrayList;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_displaylist);

        mCategoryBannerImage = findViewById(R.id.bannerCateogryImage);
        mCategoryName = findViewById(R.id.categoryName);
        mCategoryListRecyclerView = findViewById(R.id.categoryListRecyclerView);

        int catId = ClickedCategoryForProducts.getCatId();
        int subCatId = ClickedCategoryForProducts.getSubCatId();
        int mNoOfColumns = Utility.calculateNoOfColumns(this, 180);

        mProductModelArrayList = new ArrayList<>();

        mProductAdapter = new ProductListAdapter(mProductModelArrayList);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), mNoOfColumns);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCategoryListRecyclerView.setLayoutManager(gridLayoutManager);
        mCategoryListRecyclerView.setHasFixedSize(true);

        mCategoryListRecyclerView.setAdapter(mProductAdapter);

        String productList_Url = URLClass.productListUrl+catId+"&subcatid="+subCatId;


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, productList_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String msg = response.getString("msg");

                    if (msg.equalsIgnoreCase("success"))
                    {

                        System.out.println(response.toString());
                        String displayName = response.getString("displayName");

                        mCategoryName.setText(displayName);


                        String imageDisplayUrl = response.getString("imageURL");
                        Picasso.with(CategoryWiseProductList.this).load(imageDisplayUrl).into(mCategoryBannerImage);


                        JSONArray jsonArrayList = response.getJSONArray("result");

                        for (int i=0; i<jsonArrayList.length(); i++)
                        {
                            JSONObject js = jsonArrayList.getJSONObject(i);

                            int id = js.getInt("id");
                            String imgUrl = js.getString("img");
                            String name = js.getString("name");
                            String quantity = js.getString("quantity");
                            String mrp = js.getString("mrp");
                            int disacount = js.getInt("disacount");

                            System.out.println(imgUrl);

                         //   int dmrp = Integer.parseInt(mrp);

                           double dmrp = Double.parseDouble(mrp);

                            ProductListModel productListModel = new ProductListModel(String.valueOf(id), imgUrl,name,quantity,disacount,dmrp);

                            mProductModelArrayList.add(productListModel);
                            mProductAdapter.notifyDataSetChanged();

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}