package simtechnospace.tech.basketondemand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import simtechnospace.tech.basketondemand.BuildConfig;
import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.Dialogs.CustomAlertDialogForContact;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.ProductListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;
import simtechnospace.tech.basketondemand.pojoclass.Utility;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;


public class CategoryWiseProductList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

        Toolbar toolbar = findViewById(R.id.toolbarcat);
        setSupportActionBar(toolbar);

        //start of navigation drawer use implements NavigationView.OnNavigationItemSelectedListener
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //end of navigation drawer



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



    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(this, CategoryWiseProductList.class);
        startActivity(refresh);
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
        FrameLayout notifCount = (FrameLayout)   MenuItemCompat.getActionView(item);



        DBHelper dbUtils = new DBHelper(CategoryWiseProductList.this);

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

            Intent intent = new Intent(CategoryWiseProductList.this, CartProductList.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.search)
        {

            Intent searchIntent = new Intent(CategoryWiseProductList.this, SearchActivity.class);
            startActivity(searchIntent);

            return true;

        }


        return super.onOptionsItemSelected(item);
    }



    //for navigation drawer use this code upto end
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            LoginUserDetails loginUserDetails = new LoginUserDetails(CategoryWiseProductList.this);
            if (loginUserDetails.getEmail() != "") {
                Intent intentProfile = new Intent(CategoryWiseProductList.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else{
                Intent intentProfile = new Intent(CategoryWiseProductList.this, LoginUserDetails.class);
                startActivity(intentProfile);
            }
        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.orderStatus) {

        }

        else if(id == R.id.user)
        {
            LoginUserDetails loginUserDetails = new LoginUserDetails(CategoryWiseProductList.this);
            if (loginUserDetails.getEmail() != "")
            {
                loginUserDetails.removeUserInfo();
                item.setVisible(false);
            }
            else
            {
                item.setVisible(false);
            }

        }
        else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Basket On Demand");
            String shareMessage= "\nBasket On Demand best store who provides good quality products at your door step, I am strongly recommending to try this app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        } else if (id == R.id.nav_send) {

            CustomAlertDialogForContact customAlertDialogForContact = new CustomAlertDialogForContact(CategoryWiseProductList.this);
            customAlertDialogForContact.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}