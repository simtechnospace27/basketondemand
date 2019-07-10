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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import simtechnospace.tech.basketondemand.BuildConfig;
import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.Dialogs.CustomAlertDialogForContact;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.SubCategoryListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryDetails;
import simtechnospace.tech.basketondemand.pojoclass.LoginUserDetails;
import simtechnospace.tech.basketondemand.pojoclass.SubCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class SubCategoryList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView mRecyclerViewSubCategoryList;

    public SubCategoryListAdapter mSubCategoryListAdapter;
    public ArrayList<SubCategoryModel> mSubCategoryModelArrayList;
    LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbarsub);
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



        mRecyclerViewSubCategoryList = findViewById(R.id.recyclerViewSubCategoryList);

        mSubCategoryModelArrayList = new ArrayList<>();


        mSubCategoryListAdapter = new SubCategoryListAdapter(mSubCategoryModelArrayList);



        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerViewSubCategoryList.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewSubCategoryList.setHasFixedSize(true);


        //we can now set adapter to recyclerView;
        mRecyclerViewSubCategoryList.setAdapter( mSubCategoryListAdapter );



        int categoryIdClicked = ClickedCategoryDetails.getId();

        final String subCategoryDataUrl = URLClass.subCategoryDataUrl+categoryIdClicked;


        final RequestQueue requestQueue = Volley.newRequestQueue( this );

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, subCategoryDataUrl, null,  new Response.Listener<JSONObject>() {
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
                            int subCatId = jsonObject.getInt("subcatid");
                            int catId = jsonObject.getInt("catid");


                            String url = jsonObject.getString( "img" );
                            String subCategoryName = jsonObject.getString( "name" );

                            SubCategoryModel subCategoryModel = new SubCategoryModel(subCategoryName,url,subCatId,catId);

                            mSubCategoryModelArrayList.add(subCategoryModel);
                            mSubCategoryListAdapter.notifyDataSetChanged();

                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( SubCategoryList.this, "Please check internet connection", Toast.LENGTH_SHORT ).show();

            }
        });
        requestQueue.add( jsonObjectRequest );


    }




    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(this, SubCategoryList.class);
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



        DBHelper dbUtils = new DBHelper(SubCategoryList.this);

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

            Intent intent = new Intent(SubCategoryList.this, CartProductList.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.search)
        {

            Intent searchIntent = new Intent(SubCategoryList.this, SearchActivity.class);
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

            LoginUserDetails loginUserDetails = new LoginUserDetails(SubCategoryList.this);
            if (loginUserDetails.getEmail() != "") {
                Intent intentProfile = new Intent(SubCategoryList.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else{
                Intent intentProfile = new Intent(SubCategoryList.this, LoginUserDetails.class);
                startActivity(intentProfile);
            }
        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.orderStatus) {

        }

        else if(id == R.id.user)
        {
            LoginUserDetails loginUserDetails = new LoginUserDetails(SubCategoryList.this);
            if (loginUserDetails.getEmail() != "")
            {
                loginUserDetails.removeUserInfo();
                item.setVisible(false);
            }
            else
            {
                item.setVisible(false);
            }

            //   Toast.makeText(this, "Profile Details", Toast.LENGTH_SHORT).show();

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

            CustomAlertDialogForContact customAlertDialogForContact = new CustomAlertDialogForContact(SubCategoryList.this);
            customAlertDialogForContact.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
