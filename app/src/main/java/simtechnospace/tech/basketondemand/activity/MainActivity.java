package simtechnospace.tech.basketondemand.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mImageSliderLayout;

    BaseSliderView.OnSliderClickListener referenceData = this;
    ViewPagerEx.OnPageChangeListener refernceOnPagechange = this;
    HashMap<String,String> url_maps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageSliderLayout = findViewById(R.id.slider);

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
}
