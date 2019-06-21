package simtechnospace.tech.basketondemand.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.ShopByCategoryAdapter;
import simtechnospace.tech.basketondemand.pojoclass.Cart;
import simtechnospace.tech.basketondemand.pojoclass.ShopByCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;
import simtechnospace.tech.basketondemand.pojoclass.Utility;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mImageSliderLayout;

    BaseSliderView.OnSliderClickListener referenceData = this;
    ViewPagerEx.OnPageChangeListener refernceOnPagechange = this;
    HashMap<String,String> url_maps;
    RecyclerView mCategoryListRecycelrView;
    public static ShopByCategoryAdapter mShopByCategoryAdapter;
    private ArrayList<ShopByCategoryModel> mShopByCategoryModelArrayList;
    GridLayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        mImageSliderLayout = findViewById(R.id.slider);

        mCategoryListRecycelrView = findViewById(R.id.category_list_recycler_view);
        mShopByCategoryModelArrayList = new ArrayList<>();

        mShopByCategoryAdapter = new ShopByCategoryAdapter(mShopByCategoryModelArrayList);

        int mNoOfColumns = Utility.calculateNoOfColumns(this, 140);

        mLayoutManager  = new GridLayoutManager(getApplicationContext(),mNoOfColumns);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCategoryListRecycelrView.setLayoutManager(mLayoutManager);
        mCategoryListRecycelrView.setHasFixedSize(true);



        //we can now set adapter to recyclerView;
        mCategoryListRecycelrView.setAdapter( mShopByCategoryAdapter );



        final String banner_image_url = URLClass.sliderImageUrl;

        url_maps = new HashMap<>();

        final RequestQueue requestQueue = Volley.newRequestQueue( this );

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, banner_image_url, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("msg"  ).equalsIgnoreCase( "success" ))
                    {

                        JSONArray jsonArray = response.getJSONArray( "result" );

                        for (int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject( i );

                            int id = jsonObject.getInt( "id" );

                            String idd = String.valueOf( id );

                            String url = jsonObject.getString( "img" );

                            url_maps.put(idd, url);
                        }


                        for(String name : url_maps.keySet()){
                            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
                            // initialize a SliderLayout
                            //  System.out.println(url_maps.size());
                            textSliderView
                                    .image(url_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(referenceData);

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra",name);

                            mImageSliderLayout.addSlider(textSliderView);

                        }
                        mImageSliderLayout.setPresetTransformer(SliderLayout.Transformer.Tablet);
                        mImageSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        mImageSliderLayout.setCustomAnimation(new DescriptionAnimation());
                        mImageSliderLayout.setDuration(4000);
                        mImageSliderLayout.addOnPageChangeListener(refernceOnPagechange);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( MainActivity.this, "Please check internet connection", Toast.LENGTH_SHORT ).show();

            }
        });
        requestQueue.add( jsonObjectRequest );












        final String categoryImageUrl = URLClass.categoryImageUrl;


        final RequestQueue requestQueueM = Volley.newRequestQueue( this );

        final JsonObjectRequest jsonObjectRequestM = new JsonObjectRequest( Request.Method.GET, categoryImageUrl, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("msg"  ).equalsIgnoreCase( "success" ))
                    {

                        JSONArray jsonArray = response.getJSONArray( "result" );

                        for (int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject( i );

                            int id = jsonObject.getInt( "id" );
                            String url = jsonObject.getString( "img" );
                            String name = jsonObject.getString("name");
                            int containsSubCat = jsonObject.getInt("containsSubCat");


                            ShopByCategoryModel shopByCategoryModel = new ShopByCategoryModel(id,name,url, containsSubCat);
                            mShopByCategoryModelArrayList.add(shopByCategoryModel);
                            mShopByCategoryAdapter.notifyDataSetChanged();

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( MainActivity.this, "Please check internet connection", Toast.LENGTH_SHORT ).show();

            }
        });


        requestQueueM.add( jsonObjectRequestM );






    }





    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
        FrameLayout notifCount = (FrameLayout)   MenuItemCompat.getActionView(item);


        DBHelper dbUtils = new DBHelper(MainActivity.this);

        long quant = dbUtils.getCartsProductCount();


        TextView tv = (TextView) notifCount.findViewById(R.id.cart_badge);
        tv.setText(quant+"");
        //tv.setText("12");


        final MenuItem itemNotification = menu.findItem(R.id.cart);
        View actionViewNotification = MenuItemCompat.getActionView(itemNotification);
        actionViewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(itemNotification);
            }
        });

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.cart) {

            Toast.makeText(this, "Cart Details", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, CartProductList.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.user)
        {
            Toast.makeText(this, "Profile Details", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
