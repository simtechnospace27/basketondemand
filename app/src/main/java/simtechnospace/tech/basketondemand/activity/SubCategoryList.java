package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
}
