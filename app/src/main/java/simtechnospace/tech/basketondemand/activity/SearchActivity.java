package simtechnospace.tech.basketondemand.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simtechnospace.tech.basketondemand.BuildConfig;
import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.Dialogs.CustomAlertDialogForContact;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.ProductListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryForProducts;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.ProductListModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;
import simtechnospace.tech.basketondemand.pojoclass.Utility;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener{

    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private DrawerLayout drawer;


    RecyclerView mCategoryListRecyclerView;


    public ProductListAdapter mProductAdapter;
    public ArrayList<ProductListModel> mProductModelArrayList;
    GridLayoutManager gridLayoutManager;

    public ArrayList<ProductListModel> mProductModelArraySearchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        searchBar =  findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(true);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.my_menu);
        searchBar.setHint("Search Item");

        searchBar.setSelected(true);

        searchBar.setSpeechMode(false);

        mCategoryListRecyclerView = findViewById(R.id.search_list_display_recyclerview);

        int mNoOfColumns = Utility.calculateNoOfColumns(this, 180);

        mProductModelArrayList = new ArrayList<>();
        mProductModelArraySearchList = new ArrayList<>();

        mProductAdapter = new ProductListAdapter(mProductModelArrayList);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), mNoOfColumns);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCategoryListRecyclerView.setLayoutManager(gridLayoutManager);
        mCategoryListRecyclerView.setHasFixedSize(true);

        mCategoryListRecyclerView.setAdapter(mProductAdapter);

        loadAllProducts();

        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchBar.getText() != "") {
                    mProductModelArraySearchList = new ArrayList<>();
                    mProductAdapter = new ProductListAdapter(mProductModelArraySearchList);
                    mCategoryListRecyclerView.setAdapter(mProductAdapter);


                    for (int j=0; j<mProductModelArrayList.size(); j++)
                    {
                        ProductListModel p = mProductModelArrayList.get(j);

                        if (p.getmProductName().toLowerCase().contains(searchBar.getText().toLowerCase()) || p.getmSubcatName().toLowerCase().contains(searchBar.getText().toLowerCase()) || p.getmCatName().toLowerCase().contains(searchBar.getText().toLowerCase()))
                        {
                            System.out.println(p.getmProductName());
                            mProductModelArraySearchList.add(p);
                        }
                        mProductAdapter.notifyDataSetChanged();


                    }

                }
                else{
                    mProductAdapter = new ProductListAdapter(mProductModelArrayList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

    }

    private void loadAllProducts() {

        String productList_Url = URLClass.searchProductUrl;


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, productList_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String msg = response.getString("msg");

                    if (msg.equalsIgnoreCase("success"))
                    {

                        System.out.println(response.toString());


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

                            String catname = js.getString("catname");
                            String subcatname = js.getString("subcatname");

                            System.out.println(imgUrl);

                            //   int dmrp = Integer.parseInt(mrp);

                            double dmrp = Double.parseDouble(mrp);

                            ProductListModel productListModel = new ProductListModel(String.valueOf(id), imgUrl,name,quantity,disacount,dmrp, subcatname, catname);

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

            LoginUserDetails loginUserDetails = new LoginUserDetails(SearchActivity.this);
            if (loginUserDetails.getEmail() != "") {
                Intent intentProfile = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else{
                Intent intentProfile = new Intent(SearchActivity.this, LoginActivity.class);
                startActivity(intentProfile);
            }
        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.orderStatus) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Basket On Demand");
            String shareMessage= "\nBasket On Demand best store who provides good quality products at your door step, I am strongly recommending to try this app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        } else if (id == R.id.nav_send) {

            CustomAlertDialogForContact customAlertDialogForContact = new CustomAlertDialogForContact(SearchActivity.this);
            customAlertDialogForContact.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }



}
