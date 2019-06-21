package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
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

import simtechnospace.tech.basketondemand.Database.DBHelper;
import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.adapter.SubCategoryListAdapter;
import simtechnospace.tech.basketondemand.pojoclass.ClickedCategoryDetails;
import simtechnospace.tech.basketondemand.pojoclass.SubCategoryModel;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class SubCategoryList extends AppCompatActivity {

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
